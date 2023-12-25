package simulation;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import comparator.CompGamePlayerPicker;
import comparator.CompPlayerPosition;
import comparator.CompPlayerReturners;
import comparator.CompPlayerSTSpeed;
import positions.Player;
import positions.PlayerCB;
import positions.PlayerDL;
import positions.PlayerK;
import positions.PlayerLB;
import positions.PlayerOL;
import positions.PlayerQB;
import positions.PlayerRB;
import positions.PlayerReturner;
import positions.PlayerS;
import positions.PlayerST;
import positions.PlayerTE;
import positions.PlayerWR;


public class Game implements Serializable {

    private final DecimalFormat df2 = new DecimalFormat("#.##");

    public final Team homeTeam;
    public final Team awayTeam;

    public boolean hasPlayed;
    private boolean QT1;
    private boolean QT2;
    private boolean QT3;

    public String gameName;
    public int week;

    public int homeScore;
    public final int[] homeQScore;
    public int awayScore;
    public final int[] awayQScore;
    private int homeYards;
    private int awayYards;

    public int numOT;
    private int homeTOs;
    private int awayTOs;

    private ArrayList<PlayerReturner> homeReturner;
    private ArrayList<PlayerReturner> awayReturner;
    private PlayerReturner homeKickReturner;
    private PlayerReturner awayKickReturner;
    private ArrayList<PlayerST> teamST;
    private PlayerST playerST;

    private ArrayList<String> homePassingStats;
    private ArrayList<String> homeRushingStats;
    private ArrayList<String> homeReceivingStats;
    private ArrayList<String> homeDefenseStats;
    private ArrayList<String> homeKickingStats;
    private ArrayList<String> awayPassingStats;
    private ArrayList<String> awayRushingStats;
    private ArrayList<String> awayReceivingStats;
    private ArrayList<String> awayDefenseStats;
    private ArrayList<String> awayKickingStats;

    private ArrayList<PlayerOL> teamOLs;
    private ArrayList<PlayerDL> teamDLs;

    private int homePassYards;
    private int awayPassYards;
    private int homeRushYards;
    private int awayRushYards;

    private String gameEventLog;
    private String tdInfo;

    //private variables used when simming games
    private int gameTime;
    private boolean gamePoss; //1 if home, 0 if away
    private int gameYardLine;
    private int gameYardLinePlay;
    private int gameDown;
    private int gameYardsNeed;
    private boolean playingOT;
    private boolean bottomOT;

    private final int timePerPlay = 22; //affects snaps per game!
    private final int intValue = 135; //higher less ints
    private final int sackValue = 200; //higher less sacks
    private final int escapeValue = 150;
    private final int compValue = 250; //higher more completions
    private final int fatigueDropSuper = 13;
    private final int fatigueDropHigh = 9;
    private final int fatigueDropMed = 6;
    private final int fatigueDropLow = 3;
    private final int fatigueGain = 3;
    private int snapCount = 0;
    private final int touchback = 25;

    private double hkReturnAvg = 0, akReturnAvg = 0, hpReturnAvg = 0, apReturnAvg = 0;

    private int returnYards;

    private int homeOffense, homeDefense, awayOffense, awayDefense;
    private int tacticalCoach = 83;

    //budget sales
    private int tickets = 10;
    private int merch = 2;
    private int winSales = 150;

    //GAME SETUP

    public Game(Team home, Team away, String name) {
        homeTeam = home;
        awayTeam = away;

        gameName = name;

        homeScore = 0;
        homeQScore = new int[10];
        awayScore = 0;
        awayQScore = new int[10];
        numOT = 0;

        homeTOs = 0;
        awayTOs = 0;

        hasPlayed = false;

        if (gameName == null) {
            gameName = "Game";
        }

    }

    public Game(Team home, Team away) {
        homeTeam = home;
        awayTeam = away;
        numOT = 0;
        homeTOs = 0;
        awayTOs = 0;

        gameName = "";

        homeScore = 0;
        homeQScore = new int[10];
        awayScore = 0;
        awayQScore = new int[10];

        hasPlayed = false;

    }

    private int getHFadv() {
        //home field advantage
        int footIQadv = (int) (homeTeam.getCompositeFootIQ() - awayTeam.getCompositeFootIQ()) / 5;
        if (footIQadv > 3) footIQadv = 3;
        if (footIQadv < -3) footIQadv = -3;
        if (gameName.contains("Bowl") || gameName.contains("NC") || gameName.contains("SF"))
            return 0;
        if (gamePoss) {
            return 3 + footIQadv;
        } else {
            return -footIQadv;
        }
    }

    private int getCoachAdv() {
        int adv = 0;
        coachingStrategyAdjustments();

        if (gamePoss) {
            adv = Math.round((homeTeam.HC.get(0).ratOff - awayTeam.HC.get(0).ratDef) / 5);
        } else {
            adv = Math.round((awayTeam.HC.get(0).ratOff - homeTeam.HC.get(0).ratDef) / 5);
        }
        adv += getTeamChemistryAdv();
        if (adv > 4) adv = 4;
        if (adv < -4) adv = -4;
        return adv;
    }

    private int getTeamChemistryAdv() {
        int adv = 0;

        if (gamePoss) {
            adv = (int) (Math.round((homeTeam.getTeamChemistry() - awayTeam.getTeamChemistry()) / 5));
        } else {
            adv = (int) (Math.round((awayTeam.getTeamChemistry() - homeTeam.getTeamChemistry()) / 5));
        }
        if (adv > 3) adv = 3;
        if (adv < -3) adv = -3;
        return adv;
    }

    private void coachingStrategyAdjustments() {
        //Set Default Team Playbooks to memory
        homeOffense = homeTeam.playbookOffNum;
        homeDefense = homeTeam.playbookDefNum;
        awayOffense = awayTeam.playbookOffNum;
        awayDefense = awayTeam.playbookDefNum;

        if (awayTeam.userControlled || homeTeam.userControlled) {

            //Counter Strategies - performed in snake order.
            if (!homeTeam.userControlled && homeTeam.getHC(0).ratDef > tacticalCoach && Math.random() < 0.35) {
                if (awayTeam.playbookOffNum == 0) homeTeam.playbookDefNum = 0;
                if (awayTeam.playbookOffNum == 1) homeTeam.playbookDefNum = 1;
                if (awayTeam.playbookOffNum == 2) {
                    if (Math.random() > 0.50) homeTeam.playbookDefNum = 2;
                    else homeTeam.playbookDefNum = 3;
                }
                if (awayTeam.playbookOffNum == 3) homeTeam.playbookDefNum = 4;
                if (awayTeam.playbookOffNum == 4) homeTeam.playbookDefNum = 1;
                if (awayTeam.playbookOffNum == 5) homeTeam.playbookDefNum = 3;
            }

            if (!awayTeam.userControlled && awayTeam.getHC(0).ratDef > tacticalCoach && Math.random() < 0.35) {
                if (homeTeam.playbookOffNum == 0) awayTeam.playbookDefNum = 0;
                if (homeTeam.playbookOffNum == 1) awayTeam.playbookDefNum = 1;
                if (homeTeam.playbookOffNum == 2) {
                    if (Math.random() > 0.50) awayTeam.playbookDefNum = 2;
                    else awayTeam.playbookDefNum = 3;
                }
                if (homeTeam.playbookOffNum == 3) awayTeam.playbookDefNum = 4;
                if (homeTeam.playbookOffNum == 4) awayTeam.playbookDefNum = 1;
                if (homeTeam.playbookOffNum == 5) awayTeam.playbookDefNum = 3;
            }

            if (!awayTeam.userControlled && awayTeam.getHC(0).ratOff > tacticalCoach && Math.random() < 0.35) {
                if (awayTeam.playbookOffNum == 1 && homeTeam.playbookDefNum == 1 || awayTeam.playbookOffNum == 4 && homeTeam.playbookDefNum == 1) {
                    awayTeam.playbookOffNum = (int) (Math.random() * 3) + 1;
                    if (awayTeam.playbookOffNum == 1) awayTeam.playbookOffNum = 0;
                }

                if (awayTeam.playbookOffNum == 3 && homeTeam.playbookDefNum == 4) {
                    awayTeam.playbookOffNum = (int) (Math.random() * 3);
                }
            }

            if (!homeTeam.userControlled && homeTeam.getHC(0).ratOff > tacticalCoach && Math.random() < 0.35) {
                if (homeTeam.playbookOffNum == 1 && awayTeam.playbookDefNum == 1 || homeTeam.playbookOffNum == 4 && awayTeam.playbookDefNum == 1) {
                    homeTeam.playbookOffNum = (int) (Math.random() * 3) + 1;
                    if (homeTeam.playbookOffNum == 1) homeTeam.playbookOffNum = 0;
                }

                if (homeTeam.playbookOffNum == 3 && awayTeam.playbookDefNum == 4) {
                    homeTeam.playbookOffNum = (int) (Math.random() * 3);
                }
            }

        } else {

            //Counter Strategies - performed in snake order.
            if (homeTeam.getHC(0).ratDef > tacticalCoach && Math.random() < 0.45) {
                if (awayTeam.playbookOffNum == 0) homeTeam.playbookDefNum = 0;
                if (awayTeam.playbookOffNum == 1) homeTeam.playbookDefNum = 1;
                if (awayTeam.playbookOffNum == 2) {
                    if (Math.random() > 0.50) homeTeam.playbookDefNum = 2;
                    else homeTeam.playbookDefNum = 3;
                }
                if (awayTeam.playbookOffNum == 3) homeTeam.playbookDefNum = 4;
                if (awayTeam.playbookOffNum == 4) homeTeam.playbookDefNum = 1;
                if (awayTeam.playbookOffNum == 5) homeTeam.playbookDefNum = 3;
            }

            if (awayTeam.getHC(0).ratDef > tacticalCoach && Math.random() < 0.45) {
                if (homeTeam.playbookOffNum == 0) awayTeam.playbookDefNum = 0;
                if (homeTeam.playbookOffNum == 1) awayTeam.playbookDefNum = 1;
                if (homeTeam.playbookOffNum == 2) {
                    if (Math.random() > 0.50) awayTeam.playbookDefNum = 2;
                    else awayTeam.playbookDefNum = 3;
                }
                if (homeTeam.playbookOffNum == 3) awayTeam.playbookDefNum = 4;
                if (homeTeam.playbookOffNum == 4) awayTeam.playbookDefNum = 1;
                if (homeTeam.playbookOffNum == 5) awayTeam.playbookDefNum = 3;
            }

            if (awayTeam.getHC(0).ratOff > tacticalCoach && Math.random() < 0.45) {
                if (awayTeam.playbookOffNum == 1 && homeTeam.playbookDefNum == 1 || awayTeam.playbookOffNum == 4 && homeTeam.playbookDefNum == 1) {
                    awayTeam.playbookOffNum = (int) (Math.random() * 3) + 1;
                    if (awayTeam.playbookOffNum == 1) awayTeam.playbookOffNum = 0;
                }

                if (awayTeam.playbookOffNum == 3 && homeTeam.playbookDefNum == 4) {
                    awayTeam.playbookOffNum = (int) (Math.random() * 3);
                }
            }

            if (homeTeam.getHC(0).ratOff > tacticalCoach && Math.random() < 0.45) {
                if (homeTeam.playbookOffNum == 1 && awayTeam.playbookDefNum == 1 || homeTeam.playbookOffNum == 4 && awayTeam.playbookDefNum == 1) {
                    homeTeam.playbookOffNum = (int) (Math.random() * 3) + 1;
                    if (homeTeam.playbookOffNum == 1) homeTeam.playbookOffNum = 0;
                }

                if (homeTeam.playbookOffNum == 3 && awayTeam.playbookDefNum == 4) {
                    homeTeam.playbookOffNum = (int) (Math.random() * 3);
                }
            }
        }
    }

    private void getReturner() {
        homeReturner = new ArrayList<>();
        awayReturner = new ArrayList<>();
        double starterPenalty = 0.85;
        //Choose Kickoff Returners
        for (int i = 0; i < homeTeam.startersWR; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamWRs.get(i).name, "WR", homeTeam.teamWRs.get(i).ratSpeed, (float) (starterPenalty * homeTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = 0; i < homeTeam.startersRB; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamRBs.get(i).name, "RB", homeTeam.teamRBs.get(i).ratSpeed, (float) (starterPenalty * homeTeam.teamRBs.get(i).ratSpeed * Math.random())));
        }
        for (int i = 0; i < homeTeam.startersCB; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamCBs.get(i).name, "CB", homeTeam.teamCBs.get(i).ratSpeed, (float) (starterPenalty * homeTeam.teamCBs.get(i).ratSpeed * Math.random())));
        }
        for (int i = 0; i < awayTeam.startersWR; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamWRs.get(i).name, "WR", awayTeam.teamWRs.get(i).ratSpeed, (float) (starterPenalty * awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = 0; i < awayTeam.startersRB; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamRBs.get(i).name, "RB", awayTeam.teamRBs.get(i).ratSpeed, (float) (starterPenalty * awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = 0; i < awayTeam.startersCB; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamCBs.get(i).name, "CB", awayTeam.teamCBs.get(i).ratSpeed, (float) (starterPenalty * awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }

        for (int i = homeTeam.startersWR; i < homeTeam.startersWR + homeTeam.subWR; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamWRs.get(i).name, "WR", homeTeam.teamWRs.get(i).ratSpeed, (float) (homeTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = homeTeam.startersRB; i < homeTeam.startersRB + homeTeam.subRB; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamRBs.get(i).name, "RB", homeTeam.teamRBs.get(i).ratSpeed, (float) (homeTeam.teamRBs.get(i).ratSpeed * Math.random())));
        }
        for (int i = homeTeam.startersCB; i < homeTeam.startersCB + homeTeam.subCB; i++) {
            homeReturner.add(new PlayerReturner(homeTeam.abbr, homeTeam.teamCBs.get(i).name, "CB", homeTeam.teamCBs.get(i).ratSpeed, (float) (homeTeam.teamCBs.get(i).ratSpeed * Math.random())));
        }
        for (int i = awayTeam.startersWR; i < awayTeam.startersWR + awayTeam.subWR; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamWRs.get(i).name, "WR", awayTeam.teamWRs.get(i).ratSpeed, (float) (awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = awayTeam.startersRB; i < awayTeam.startersRB + awayTeam.subRB; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamRBs.get(i).name, "RB", awayTeam.teamRBs.get(i).ratSpeed, (float) (awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }
        for (int i = awayTeam.startersCB; i < awayTeam.startersCB + awayTeam.subCB; i++) {
            awayReturner.add(new PlayerReturner(awayTeam.abbr, awayTeam.teamCBs.get(i).name, "CB", awayTeam.teamCBs.get(i).ratSpeed, (float) (awayTeam.teamWRs.get(i).ratSpeed * Math.random())));
        }

        Collections.sort(homeReturner, new CompPlayerReturners());
        Collections.sort(awayReturner, new CompPlayerReturners());

        homeReturner.get(0).gameSpeed = (float) (homeReturner.get(0).ratSpeed * Math.random());
        homeReturner.get(1).gameSpeed = (float) (homeReturner.get(1).ratSpeed * Math.random());
        awayReturner.get(0).gameSpeed = (float) (awayReturner.get(0).ratSpeed * Math.random());
        awayReturner.get(1).gameSpeed = (float) (awayReturner.get(1).ratSpeed * Math.random());

        if (homeReturner.get(0).gameSpeed > homeReturner.get(1).gameSpeed)
            homeKickReturner = homeReturner.get(0);
        else homeKickReturner = homeReturner.get(1);

        if (awayReturner.get(0).gameSpeed > awayReturner.get(1).gameSpeed)
            awayKickReturner = awayReturner.get(0);
        else awayKickReturner = awayReturner.get(1);

        homeKickReturner.kYards = 0;
        homeKickReturner.kReturns = 0;
        homeKickReturner.pYards = 0;
        homeKickReturner.pReturns = 0;
        homeKickReturner.kTD = 0;
        homeKickReturner.pTD = 0;
        awayKickReturner.kYards = 0;
        awayKickReturner.kReturns = 0;
        awayKickReturner.pYards = 0;
        awayKickReturner.pReturns = 0;
        awayKickReturner.kTD = 0;
        awayKickReturner.pTD = 0;

        homeKickReturner.startPos = 0;
        awayKickReturner.startPos = 0;
    }

    private int getSpecialTeamsD(Team specialTeams) {
        int ST = 0;
        teamST = new ArrayList<>();

        for (int i = specialTeams.startersLB; i < specialTeams.startersLB + specialTeams.subLB; i++) {
            ST += specialTeams.getLB(i).ratSpeed;
            teamST.add(new PlayerST(specialTeams.abbr, specialTeams.teamLBs.get(i).name, "LB", specialTeams.teamLBs.get(i).ratSpeed, specialTeams.teamLBs.get(i).ratTackle));
        }
        for (int i = specialTeams.startersCB; i < specialTeams.startersCB + specialTeams.subCB; i++) {
            ST += specialTeams.getCB(i).ratSpeed;
            teamST.add(new PlayerST(specialTeams.abbr, specialTeams.teamCBs.get(i).name, "CB", specialTeams.teamCBs.get(i).ratSpeed, specialTeams.teamCBs.get(i).ratTackle));
        }
        for (int i = specialTeams.startersS; i < specialTeams.startersS + specialTeams.subS; i++) {
            ST += specialTeams.getS(i).ratSpeed;
            teamST.add(new PlayerST(specialTeams.abbr, specialTeams.teamSs.get(i).name, "S", specialTeams.teamSs.get(i).ratSpeed, specialTeams.teamSs.get(i).ratTackle));
        }

        Collections.sort(teamST, new CompPlayerSTSpeed());
        playerST = teamST.get(0);

        ST = ST / (specialTeams.subLB + specialTeams.subCB + specialTeams.subS);

        return ST;
    }

    //GAME SIMULATION

    public void playGame() {
        if (gameName.equals("BYE WEEK") && !hasPlayed) {
            hasPlayed = true;
            homeTeam.gameWLSchedule.add("BYE");
            awayTeam.gameWLSchedule.add("BYE");
            homeTeam.postSeasonHealing(1);
            awayTeam.postSeasonHealing(1);
        }

        if (!hasPlayed) {
            gameEventLog = "LOG: #" + awayTeam.rankTeamPollScore + " " + awayTeam.abbr + " (" + awayTeam.wins + "-" + awayTeam.losses + ") @ #" +
                    homeTeam.rankTeamPollScore + " " + homeTeam.abbr + " (" + homeTeam.wins + "-" + homeTeam.losses + ")" + "\n" +
                    "---------------------------------------------------------\n\n" +
                    awayTeam.abbr + " Off Strategy: " + awayTeam.playbookOff.getStratName() + "\n" +
                    awayTeam.abbr + " Def Strategy: " + awayTeam.playbookDef.getStratName() + "\n" +
                    homeTeam.abbr + " Off Strategy: " + homeTeam.playbookOff.getStratName() + "\n" +
                    homeTeam.abbr + " Def Strategy: " + homeTeam.playbookDef.getStratName() + "\n" +
                    "\n\n-- 1st QUARTER --";
            //probably establish some home field advantage before playing
            gameTime = 3600;
            gameDown = 1;
            gamePoss = true;

            //Reset Game Stats & Fatigue
            for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
                Player player = homeTeam.getAllPlayers().get(i);
                player.gameFatigue = 100;
                player.gameSim = 0;
                player.posDepth = 0;
                player.gamePassAtempts = 0;
                player.gamePassComplete = 0;
                player.gamePassYards = 0;
                player.gamePassTDs = 0;
                player.gamePassInts = 0;
                player.gameRushAttempts = 0;
                player.gameRushYards = 0;
                player.gameRushTDs = 0;
                player.gameTargets = 0;
                player.gameReceptions = 0;
                player.gameRecYards = 0;
                player.gameRecTDs = 0;
                player.gameDrops = 0;
                player.gameFumbles = 0;
                player.gameTackles = 0;
                player.gameSacks = 0;
                player.gameInterceptions = 0;
                player.gameDefended = 0;
                player.gameIncomplete = 0;
                player.gameFGAttempts = 0;
                player.gameFGMade = 0;
                player.gameXPAttempts = 0;
                player.gameXPMade = 0;
            }
            for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
                Player player = awayTeam.getAllPlayers().get(i);
                player.gameFatigue = 100;
                player.gameSim = 0;
                player.posDepth = 0;
                player.gamePassAtempts = 0;
                player.gamePassComplete = 0;
                player.gamePassYards = 0;
                player.gamePassTDs = 0;
                player.gamePassInts = 0;
                player.gameRushAttempts = 0;
                player.gameRushYards = 0;
                player.gameRushTDs = 0;
                player.gameTargets = 0;
                player.gameReceptions = 0;
                player.gameRecYards = 0;
                player.gameRecTDs = 0;
                player.gameDrops = 0;
                player.gameFumbles = 0;
                player.gameTackles = 0;
                player.gameSacks = 0;
                player.gameInterceptions = 0;
                player.gameDefended = 0;
                player.gameIncomplete = 0;
                player.gameFGAttempts = 0;
                player.gameFGMade = 0;
                player.gameXPAttempts = 0;
                player.gameXPMade = 0;
            }

            getReturner();

            kickOff(homeTeam, awayTeam);

            // Regulation
            while (gameTime > 0) {
                //play ball!
                if (gamePoss) runPlay(homeTeam, awayTeam);
                else runPlay(awayTeam, homeTeam);
            }

            // Add last play
            if (homeScore != awayScore) {
                gameEventLog += getEventLogScore() + "\nTime has expired! The game is over.";
            } else {
                gameEventLog += getEventLogScore() + "\nOVERTIME!\nTie game at 0:00, overtime begins!";
            }

            //Overtime (if needed)
            if (gameTime <= 0 && homeScore == awayScore) {
                playingOT = true;
                gamePoss = false;
                gameYardLine = 75;
                numOT++;
                gameTime = -1;
                gameDown = 1;
                gameYardsNeed = 10;

                while (playingOT) {
                    if (gamePoss) runPlay(homeTeam, awayTeam);
                    else runPlay(awayTeam, homeTeam);
                }
            }

            //game over, add wins
            if (homeScore > awayScore) {
                homeTeam.wins++;
                homeTeam.gameWLSchedule.add("W");
                awayTeam.losses++;
                awayTeam.gameWLSchedule.add("L");
                homeTeam.gameWinsAgainst.add(awayTeam);
                awayTeam.gameLossesAgainst.add(homeTeam);
                homeTeam.winStreak.addWin(homeTeam.league.getYear());
                homeTeam.league.checkLongestWinStreak(homeTeam.winStreak);
                awayTeam.winStreak.resetStreak(awayTeam.league.getYear());
                homeTeam.getHC(0).teamWins++;
                awayTeam.getHC(0).teamLosses++;
            } else {
                homeTeam.losses++;
                homeTeam.gameWLSchedule.add("L");
                awayTeam.wins++;
                awayTeam.gameWLSchedule.add("W");
                awayTeam.gameWinsAgainst.add(homeTeam);
                homeTeam.gameLossesAgainst.add(awayTeam);
                awayTeam.winStreak.addWin(awayTeam.league.getYear());
                awayTeam.league.checkLongestWinStreak(awayTeam.winStreak);
                homeTeam.winStreak.resetStreak(homeTeam.league.getYear());
                homeTeam.getHC(0).teamLosses++;
                awayTeam.getHC(0).teamWins++;
            }

            // Add points/opp points
            homeTeam.addGamePlayedPlayers(homeScore > awayScore);
            awayTeam.addGamePlayedPlayers(awayScore > homeScore);

            homeTeam.teamPoints += homeScore;
            awayTeam.teamPoints += awayScore;

            homeTeam.teamOppPoints += awayScore;
            awayTeam.teamOppPoints += homeScore;

            homeYards = getPassYards(false) + getRushYards(false);
            awayYards = getPassYards(true) + getRushYards(true);

            homeTeam.teamYards += homeYards;
            awayTeam.teamYards += awayYards;

            homeTeam.teamOppYards += awayYards;
            awayTeam.teamOppYards += homeYards;

            homeTeam.teamOppPassYards += getPassYards(true);
            awayTeam.teamOppPassYards += getPassYards(false);
            homeTeam.teamOppRushYards += getRushYards(true);
            awayTeam.teamOppRushYards += getRushYards(false);
            homePassYards = getPassYards(false);
            awayPassYards = getPassYards(true);
            homeRushYards = getRushYards(false);
            awayRushYards = getRushYards(true);

            homeTeam.teamTODiff += awayTOs - homeTOs;
            awayTeam.teamTODiff += homeTOs - awayTOs;

            gameStatistics();

            //Reset Strategies

            homeOffense = homeTeam.playbookOffNum;
            homeDefense = homeTeam.playbookDefNum;
            awayOffense = awayTeam.playbookOffNum;
            awayDefense = awayTeam.playbookDefNum;

            hasPlayed = true;

            addNewsStory();

            homeTeam.checkForInjury();
            awayTeam.checkForInjury();

            if (!gameName.equals("Conference") || !gameName.equals("OOC")) {
                int attendance = ((homeTeam.teamPrestige * 2 + awayTeam.teamPrestige) / 3);
                homeTeam.teamBudget += (int) (tickets * .75 * attendance) + (int) (merch * Math.random() * homeTeam.teamPrestige);
                awayTeam.teamBudget += (int) (tickets * .25 * attendance);
            } else {
                int attendance = ((homeTeam.teamPrestige * 2 + awayTeam.teamPrestige) / 3);
                homeTeam.teamBudget += (int) (1.25 * tickets * .50 * attendance) + (int) (2 * merch * Math.random() * homeTeam.teamPrestige);
                awayTeam.teamBudget += (int) (1.25 * tickets * .50 * attendance) + (int) (2 * merch * Math.random() * awayTeam.teamPrestige);
            }

            if (homeScore > awayScore) homeTeam.teamBudget += winSales;
            if (awayScore > homeScore) awayTeam.teamBudget += winSales;


        }
    }

    // PRE-SNAP DECISIONS

    private void runPlay(Team offense, Team defense) {
        quarterCheck();
        recoup(false, 0);
        snapCount++;
        gameYardLinePlay = gameYardLine;

        if (gameDown > 4) {
            if (!playingOT) {
                //Log the turnover on downs, reset down and distance, give possession to the defense, exit this runPlay()
                gameEventLog += getEventLog() + "TURNOVER ON DOWNS!\n" + offense.abbr + " failed to convert on " + (gameDown - 1) + "th down. " + defense.abbr + " takes over possession on downs.";

                //Turn over on downs, change possession, set to first down and 10 yards to go
                gamePoss = !gamePoss;
                gameDown = 1;
                gameYardsNeed = 10;
                //and flip which direction the ball is moving in
                gameYardLine = 100 - gameYardLine;

            } else {
                //OT is over for the offense, log the turnover on downs, run resetForOT().
                gameEventLog += getEventLog() + "TURNOVER ON DOWNS!\n" + offense.abbr + " failed to convert on " + (gameDown - 1) + "th down in OT and their possession is over.";
                resetForOT();

            }
        } else {
            double preferPass = (offense.getPassProf() - defense.getPassDef()) / 100 + Math.random() * offense.playbookOff.getPassPref();       //STRATEGIES
            double preferRush = (offense.getRushProf() - defense.getRushDef()) / 90 + Math.random() * offense.playbookOff.getRunPref();

            // If it's 1st and Goal to go, adjust yards needed to reflect distance for a TD so that play selection reflects actual yards to go
            // If we don't do this, gameYardsNeed may be higher than the actually distance for a TD and suboptimal plays may be chosen
            if (gameDown == 1 && gameYardLine >= 91) gameYardsNeed = 100 - gameYardLine;

            //Under 30 seconds to play, check that the team with the ball is trailing or tied, do something based on the score difference
            if (gameTime <= 20 && !playingOT && ((gamePoss && (awayScore >= homeScore)) || (!gamePoss && (homeScore >= awayScore)))) {
                //Down by 3 or less, or tied, and you have the ball
                if (((gamePoss && (awayScore - homeScore) <= 3) || (!gamePoss && (homeScore - awayScore) <= 3)) && gameYardLine > 60) {
                    //last second FGA
                    fieldGoalAtt(offense, defense);
                } else {
                    //hail mary
                    passingPlay(offense, defense);
                }
            } else if (gameDown >= 4) {
                if (((gamePoss && (awayScore - homeScore) > 3) || (!gamePoss && (homeScore - awayScore) > 3)) && gameTime < 300) {
                    //go for it since we need 7 to win -- This also forces going for it if down by a TD in BOT OT
                    if (gameYardsNeed < 3 && preferRush * 3 > preferPass) {
                        rushingPlay(offense, defense);
                    } else {
                        passingPlay(offense, defense);
                    }
                } else {
                    //4th down
                    if (gameYardsNeed < 3) {
                        if (gameYardLine > 65) {
                            //fga
                            fieldGoalAtt(offense, defense);
                        } else if (gameYardLine > 55) {
                            // run play, go for it!
                            rushingPlay(offense, defense);
                        } else {
                            //punt
                            puntPlay(offense, defense);
                        }
                    } else if (gameYardLine > 60) {
                        //fga
                        fieldGoalAtt(offense, defense);
                    } else {
                        //punt
                        puntPlay(offense, defense);
                    }
                }
            } else if ((gameDown == 3 && gameYardsNeed > 4) || ((gameDown == 1 || gameDown == 2) && (preferPass >= preferRush))) {
                // pass play
                passingPlay(offense, defense);
            } else {
                //run play
                rushingPlay(offense, defense);
            }
        }


    }

    private void passingPlay(Team offense, Team defense) {
        int x = 0;
        if (gameTime < 900 && gamePoss && (homeScore - awayScore) >= 20 + gameTime / 60) {
            x = 1;
        } else if (gameTime < 900 && !gamePoss && (awayScore - homeScore) >= 20 + gameTime / 60) {
            x = 1;
        }

        PlayerQB selQB;
        PlayerRB selRB;
        PlayerWR selWR;
        PlayerWR selWR2;
        PlayerTE selTE;
        PlayerDL selDL;
        PlayerLB selLB;
        PlayerLB selLB2;
        PlayerCB selCB;
        PlayerS selS;
        PlayerS selS2;


        ArrayList<Player> receiver = new ArrayList<>();
        ArrayList<PlayerRB> RunningBack = new ArrayList<>();
        ArrayList<PlayerWR> WideReceiver = new ArrayList<>();
        ArrayList<PlayerTE> TightEnd = new ArrayList<>();
        ArrayList<PlayerDL> DLineman = new ArrayList<>();
        ArrayList<PlayerLB> Linebacker = new ArrayList<>();
        ArrayList<PlayerCB> Cornerback = new ArrayList<>();
        ArrayList<PlayerS> Safety = new ArrayList<>();

        //Receiver Options
        for (int i = 0 + x; i < offense.startersWR + x; ++i) {
            if (offense.getWR(i).gameFatigue > 0) {
                offense.getWR(i).gameSim = Math.pow(offense.getWR(i).ratOvr, 1) * Math.random();
                offense.getWR(i).posDepth = i;
                offense.getWR(i).gameSnaps++;
                receiver.add(offense.getWR(i));
                WideReceiver.add(offense.getWR(i));
            } else {
                offense.getWR(offense.startersWR).gameSim = Math.pow(offense.getWR(offense.startersWR).ratOvr, 1) * Math.random();
                offense.getWR(offense.startersWR).posDepth = i;
                offense.getWR(offense.startersWR).gameSnaps++;
                receiver.add(offense.getWR(offense.startersWR));
                WideReceiver.add(offense.getWR(offense.startersWR));
            }
        }

        double TEBonus;
        TEBonus = offense.playbookOff.getPassUsage() * 0.15;
        for (int i = 0 + x; i < offense.startersTE + x; ++i) {
            if (offense.getTE(i).gameFatigue > 0) {
                if (gameYardLine > 80) {
                    offense.getTE(i).gameSim = Math.pow(((offense.getTE(i).ratCatch + offense.getTE(0).ratSpeed) / 2), 1) * Math.random() * 1.25;
                } else {
                    offense.getTE(i).gameSim = Math.pow(((offense.getTE(i).ratCatch + offense.getTE(i).ratSpeed) / 2), 1) * Math.random() * (.70 + TEBonus);
                }
                offense.getTE(i).gameSnaps++;
                receiver.add(offense.getTE(i));
                TightEnd.add(offense.getTE(i));
            } else {
                if (gameYardLine > 80) {
                    offense.getTE(offense.startersTE).gameSim = Math.pow(((offense.getTE(offense.startersTE).ratCatch + offense.getTE(offense.startersTE).ratSpeed) / 2), 1) * Math.random() * 1.25;
                } else {
                    offense.getTE(offense.startersTE).gameSim = Math.pow(((offense.getTE(offense.startersTE).ratCatch + offense.getTE(offense.startersTE).ratSpeed) / 2), 1) * Math.random() * (.70 + TEBonus);
                }
                offense.getTE(offense.startersTE).gameSnaps++;
                receiver.add(offense.getTE(offense.startersTE));
                TightEnd.add(offense.getTE(offense.startersTE));
            }
        }
        double RBBonus;
        RBBonus = offense.playbookOff.getPassUsage() * 0.10;
        for (int i = 0 + x; i < offense.startersRB + x; ++i) {
            if (offense.getRB(i).gameFatigue > 0) {
                offense.getRB(i).gameSim = Math.pow(((offense.getRB(0).ratCatch + offense.getRB(i).ratSpeed) / 2), 1) * Math.random() * (.70 + RBBonus);
                offense.getRB(i).gameSnaps++;
                receiver.add(offense.getRB(i));
                RunningBack.add(offense.getRB(i));
            } else {
                offense.getRB(offense.startersRB).gameSim = Math.pow(((offense.getRB(offense.startersRB).ratCatch + offense.getRB(offense.startersRB).ratSpeed) / 2), 1) * Math.random() * (.70 + RBBonus);
                offense.getRB(offense.startersRB).gameSnaps++;
                receiver.add(offense.getRB(offense.startersRB));
                RunningBack.add(offense.getRB(offense.startersRB));
            }
        }

        int z = 0; //o-line counter
        teamOLs = new ArrayList<>();
        for (int i = 0 + x; i < offense.startersOL + x; ++i) {
            if (offense.getOL(i).gameFatigue > 0) {
                offense.getOL(i).gameSnaps++;
                teamOLs.add(offense.getOL(i));
            } else {
                z++;
                offense.getOL(offense.startersOL + z).gameSnaps++;
                teamOLs.add(offense.getOL(offense.startersOL + z));
            }
        }

        for (int i = 0 + x; i < defense.startersLB + x; ++i) {
            if (defense.getLB(i).gameFatigue > 0) {
                defense.getLB(i).gameSim = Math.pow(defense.getLB(i).ratCoverage, 1) * Math.random();
                defense.getLB(i).gameSnaps++;
                Linebacker.add(defense.getLB(i));
            } else {
                defense.getLB(defense.startersLB).gameSim = Math.pow(defense.getLB(defense.startersLB).ratCoverage, 1) * Math.random();
                defense.getLB(defense.startersLB + 1).gameSim = Math.pow(defense.getLB(defense.startersLB + 1).ratCoverage, 1) * Math.random();
                defense.getLB(defense.startersLB).gameSnaps++;
                defense.getLB(defense.startersLB + 1).gameSnaps++;
                Linebacker.add(defense.getLB(defense.startersLB));
                Linebacker.add(defense.getLB(defense.startersLB + 1));
            }
        }

        z = 0; //o-line counter
        teamDLs = new ArrayList<>();
        for (int i = 0 + x; i < defense.startersDL + x; ++i) {
            if (defense.getDL(i).gameFatigue > 0) {
                defense.getDL(i).gameSim = Math.pow(defense.getDL(i).ratPassRush, 1) * Math.random();
                defense.getDL(i).gameSnaps++;
                DLineman.add(defense.getDL(i));
                teamDLs.add(defense.getDL(i));
            } else {
                z++;
                defense.getDL(defense.startersDL + z).gameSim = Math.pow(defense.getDL(defense.startersDL + z).ratPassRush, 1) * Math.random();
                defense.getDL(defense.startersDL + 1 + z).gameSim = Math.pow(defense.getDL(defense.startersDL + 1 + z).ratPassRush, 1) * Math.random();
                defense.getDL(defense.startersDL + z).gameSnaps++;
                defense.getDL(defense.startersDL + z).gameSnaps++;
                DLineman.add(defense.getDL(defense.startersDL + z));
                DLineman.add(defense.getDL(defense.startersDL + 1 + z));
                teamDLs.add(defense.getDL(defense.startersDL + z));
            }
        }

        for (int i = 0 + x; i < defense.startersS + x; ++i) {
            if (defense.getS(i).gameFatigue > 0) {
                defense.getS(i).gameSim = Math.pow(defense.getS(i).ratCoverage, 1) * Math.random();
                defense.getS(i).gameSnaps++;
                Safety.add(defense.getS(i));
            } else {
                defense.getS(defense.startersS).gameSim = Math.pow(defense.getS(defense.startersS).ratCoverage, 1) * Math.random();
                defense.getS(defense.startersS).gameSnaps++;
                Safety.add(defense.getS(defense.startersS));
            }
        }
        //Rank Players
        Collections.sort(WideReceiver, new CompGamePlayerPicker());
        Collections.sort(TightEnd, new CompGamePlayerPicker());
        Collections.sort(RunningBack, new CompGamePlayerPicker());
        Collections.sort(receiver, new CompGamePlayerPicker());
        Collections.sort(Linebacker, new CompGamePlayerPicker());
        Collections.sort(DLineman, new CompGamePlayerPicker());
        Collections.sort(Safety, new CompGamePlayerPicker());


        //Choose Action Players
        selQB = offense.getQB(0 + x);
        selRB = RunningBack.get(0);
        selTE = TightEnd.get(0);
        selWR = WideReceiver.get(0);
        selWR2 = WideReceiver.get(1);
        selDL = DLineman.get(0);
        selLB = Linebacker.get(0);
        selLB2 = Linebacker.get(1);
        selCB = defense.getCB(WideReceiver.get(0).posDepth);
        selS = Safety.get(0);
        selS2 = Safety.get(1);

        if (selCB.gameFatigue <= 0) selCB = defense.getCB(defense.startersCB);

        selQB.gameSnaps++;


        //Fatigue selected Action Players
        selRB.gameFatigue -= Math.round((100 - selRB.ratDur) / 10);
        selWR.gameFatigue -= fatigueDropHigh + Math.round((100 - selWR.ratDur) / 10);
        selWR2.gameFatigue -= fatigueDropMed + Math.round((100 - selWR.ratDur) / 10);
        selTE.gameFatigue -= fatigueDropHigh + Math.round((100 - selTE.ratDur) / 10);
        selDL.gameFatigue -= fatigueDropHigh + Math.round((100 - selDL.ratDur) / 10);
        selLB.gameFatigue -= fatigueDropSuper + Math.round((100 - selLB.ratDur) / 10);
        selLB2.gameFatigue -= fatigueDropSuper + Math.round((100 - selLB2.ratDur) / 10);
        selCB.gameFatigue -= fatigueDropMed + Math.round((100 - selCB.ratDur) / 10);
        selS.gameFatigue -= fatigueDropMed + Math.round((100 - selS.ratDur) / 10);
        selS2.gameFatigue -= Math.round((100 - selS2.ratDur) / 10);

        String pos = receiver.get(0).position;

        passingPlay(offense, defense, selQB, selRB, selWR, selTE, selDL, selLB, selLB2, selCB, selS, selS2, pos);

    }

    private void rushingPlay(Team offense, Team defense) {
        int x = 0;
        if (gameTime < 900 && gamePoss && (homeScore - awayScore) >= 20 + gameTime / 60) {
            x = 1;
        } else if (gameTime < 900 && !gamePoss && (awayScore - homeScore) >= 20 + gameTime / 60) {
            x = 1;
        }

        PlayerQB selQB;
        PlayerRB selRB;
        PlayerTE selTE;
        PlayerDL selDL;
        PlayerLB selLB;
        PlayerCB selCB;
        PlayerS selS;
        PlayerS selS2;

        ArrayList<Player> rusher = new ArrayList<>();
        ArrayList<PlayerRB> RunningBack = new ArrayList<>();
        ArrayList<PlayerTE> TightEnd = new ArrayList<>();
        ArrayList<PlayerDL> DLineman = new ArrayList<>();
        ArrayList<PlayerLB> Linebacker = new ArrayList<>();
        ArrayList<PlayerCB> Cornerback = new ArrayList<>();
        ArrayList<PlayerS> Safety = new ArrayList<>();

        //Action Players

        for (int i = 0 + x; i < offense.startersRB + x; ++i) {
            if (offense.getRB(i).gameFatigue > 0) {
                offense.getRB(i).gameSim = Math.pow(offense.getRB(i).ratOvr, 1.5) * Math.random();
                offense.getRB(i).gameSnaps++;
                rusher.add(offense.getRB(i));
                RunningBack.add(offense.getRB(i));
            } else {
                offense.getRB(offense.startersRB).gameSim = Math.pow(offense.getRB(i).ratOvr, 1.5) * Math.random();
                offense.getRB(offense.startersRB).gameSnaps++;
                rusher.add(offense.getRB(offense.startersRB));
                RunningBack.add(offense.getRB(offense.startersRB));
            }
        }

        if (offense.playbookOffNum == 4 || offense.playbookOffNum == 5)
            offense.getQB(0 + x).gameSim = Math.pow(offense.getQB(0 + x).ratSpeed, 1.485) * Math.random();
        else
            offense.getQB(0 + x).gameSim = 0.25 * Math.pow(offense.getQB(0 + x).ratSpeed, 1.485) * Math.random();
        rusher.add(offense.getQB(0 + x));

        int z = 0; //o-line counter
        teamOLs = new ArrayList<>();
        for (int i = 0 + x; i < offense.startersOL + x; ++i) {
            if (offense.getOL(i).gameFatigue > 0) {
                offense.getOL(i).gameSnaps++;
                teamOLs.add(offense.getOL(i));
            } else {
                z++;
                offense.getOL(offense.startersOL + z).gameSnaps++;
                teamOLs.add(offense.getOL(offense.startersOL + z));
            }
        }

        z = 0; //d-line counter
        teamDLs = new ArrayList<>();
        for (int i = 0 + x; i < defense.startersDL + x; ++i) {
            if (defense.getDL(i).gameFatigue > 0) {
                defense.getDL(i).gameSim = Math.pow(defense.getDL(i).ratRunStop, 1) * Math.random();
                defense.getDL(i).gameSnaps++;
                DLineman.add(defense.getDL(i));
                teamDLs.add(defense.getDL(i));
            } else {
                defense.getDL(defense.startersDL + z).gameSim = Math.pow(defense.getDL(defense.startersDL + z).ratRunStop, 1) * Math.random();
                defense.getDL(defense.startersDL + 1 + z).gameSim = Math.pow(defense.getDL(defense.startersDL + 1 + z).ratRunStop, 1) * Math.random();
                defense.getDL(defense.startersDL + z).gameSnaps++;
                defense.getDL(defense.startersDL + 1 + z).gameSnaps++;
                DLineman.add(defense.getDL(defense.startersDL + z));
                DLineman.add(defense.getDL(defense.startersDL + 1 + z));
                teamDLs.add(defense.getDL(defense.startersDL + z));
            }
        }

        for (int i = 0 + x; i < defense.startersLB + x; ++i) {
            if (defense.getLB(i).gameFatigue > 0) {
                defense.getLB(i).gameSim = Math.pow(defense.getLB(i).ratRunStop, 1) * Math.random();
                defense.getLB(i).gameSnaps++;
                Linebacker.add(defense.getLB(i));
            } else {
                defense.getLB(defense.startersLB).gameSim = Math.pow(defense.getLB(defense.startersLB).ratRunStop, 1) * Math.random();
                defense.getLB(defense.startersLB + 1).gameSim = Math.pow(defense.getLB(defense.startersLB + 1).ratRunStop, 1) * Math.random();
                defense.getLB(defense.startersLB).gameSnaps++;
                defense.getLB(defense.startersLB + 1).gameSnaps++;
                Linebacker.add(defense.getLB(defense.startersLB));
                Linebacker.add(defense.getLB(defense.startersLB + 1));
            }
        }

        for (int i = 0 + x; i < defense.startersCB + x; ++i) {
            if (defense.getCB(i).gameFatigue > 0) {
                defense.getCB(i).gameSim = Math.pow(defense.getCB(i).ratTackle, 1) * Math.random();
                defense.getCB(i).gameSnaps++;
                Cornerback.add(defense.getCB(i));
            } else {
                defense.getCB(defense.startersCB).gameSim = Math.pow(defense.getCB(defense.startersCB).ratTackle, 1) * Math.random();
                defense.getCB(defense.startersCB).gameSnaps++;
                Cornerback.add(defense.getCB(defense.startersCB));
            }
        }

        for (int i = 0 + x; i < defense.startersS + x; ++i) {
            if (defense.getS(i).gameFatigue > 0) {
                defense.getS(i).gameSim = Math.pow(defense.getS(i).ratRunStop, 1) * Math.random();
                defense.getS(i).gameSnaps++;
                Safety.add(defense.getS(i));
            } else {
                defense.getS(defense.startersS).gameSim = Math.pow(defense.getS(defense.startersS).ratRunStop, 1) * Math.random();
                defense.getS(defense.startersS).gameSnaps++;
                Safety.add(defense.getS(defense.startersS));
            }
        }

        //Rank Players
        Collections.sort(TightEnd, new CompGamePlayerPicker());
        Collections.sort(RunningBack, new CompGamePlayerPicker());
        Collections.sort(rusher, new CompGamePlayerPicker());
        Collections.sort(Linebacker, new CompGamePlayerPicker());
        Collections.sort(DLineman, new CompGamePlayerPicker());
        Collections.sort(Safety, new CompGamePlayerPicker());


        //Choose Action Players
        selQB = offense.getQB(0 + x);
        selRB = RunningBack.get(0);
        selTE = offense.getTE(0 + x);
        selDL = DLineman.get(0);
        selLB = Linebacker.get(0);
        selCB = Cornerback.get(0);
        selS = Safety.get(0);
        selS2 = Safety.get(1);

        if (selTE.gameFatigue <= 0) selTE = defense.getTE(offense.startersTE);
        if (selS.gameFatigue <= 0) selS = defense.getS(defense.startersS);

        selQB.gameSnaps++;
        selTE.gameSnaps++;

        //Fatigue
        selRB.gameFatigue -= fatigueDropMed + Math.round((100 - selRB.ratDur) / 10);
        selTE.gameFatigue -= fatigueDropMed + Math.round((100 - selTE.ratDur) / 10);
        selDL.gameFatigue -= fatigueDropHigh + Math.round((100 - selDL.ratDur) / 10);
        selLB.gameFatigue -= fatigueDropSuper + Math.round((100 - selLB.ratDur) / 10);
        selCB.gameFatigue -= Math.round((100 - selCB.ratDur) / 10);
        selS.gameFatigue -= fatigueDropMed + Math.round((100 - selS.ratDur) / 10);
        selS2.gameFatigue -= Math.round((100 - selS2.ratDur) / 10);

        rushPlay(offense, defense, selQB, selRB, selTE, selDL, selLB, selCB, selS, selS2);

    }

    //PASSING PLAY - POST-SNAP

    private void passingPlay(Team offense, Team defense, PlayerQB selQB, PlayerRB selRB, PlayerWR selWR, PlayerTE selTE, PlayerDL selDL, PlayerLB selLB, PlayerLB selLB2, PlayerCB selCB, PlayerS selS, PlayerS selS2, String pos) {
        int yardsGain = 0;
        boolean gotTD = false;
        boolean gotFumble = false;

        int offProtection = getOffPassProtection(offense, selTE);
        int defPressure = getDefPassPressure(defense, selLB2);

        //get how much pressure there is on qb, check if sack
        int pressureOnQB = 2 * defPressure - offProtection + getHFadv() + getCoachAdv();
        // SACK OUTCOME
        if (Math.random() * sackValue < pressureOnQB / 8) {

            if (Math.random() * escapeValue < pressureOnQB / 8 && selQB.ratSpeed > selDL.ratPassRush) {
                //ESCAPE SACK
                selQB.gameSim = 1;
                selRB.gameSim = 0;
                rushPlay(offense, defense, selQB, selRB, selTE, selDL, selLB, selCB, selS2, selS);
            } else {
                //sacked!
                selDL.gameSim = selDL.ratTackle * Math.random() * 100;
                selLB2.gameSim = selLB2.ratTackle * Math.random() * 60;
                selS2.gameSim = selS2.ratTackle * Math.random() * 25;

                recordSack(offense, defense, selQB, selDL, selLB2, selCB, selS2);

                return;
            }

        } else {

            //Throw  Ball
            recordPassAttempt(selQB, selRB, selWR, selTE, selLB, selCB, pos);

            //check for int
            if (!pos.equals("RB")) {
                double intChance = (pressureOnQB + defense.getS(0).ratOvr - (2 * selQB.ratPassAcc + selQB.ratFootIQ + 100) / 4) / 18
                        - offense.playbookOff.getPassProtection() + defense.playbookDef.getPassRush();
                if (intChance < 0.015) intChance = 0.015;
                if (intValue * Math.random() < intChance) {
                    //Interception
                    if (pos.equals("WR")) {
                        selDL.gameSim = selDL.ratPassRush * Math.random() * 15;
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 100;
                        selS.gameSim = selS.ratCoverage * Math.random() * 50;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 30;
                    } else if (pos.equals("TE")) {
                        selDL.gameSim = selDL.ratPassRush * Math.random() * 15;
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 50;
                        selS2.gameSim = selS2.ratCoverage * Math.random() * 45;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 65;
                    } else {
                        selDL.gameSim = selDL.ratPassRush * Math.random() * 15;
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 80;
                        selS.gameSim = selS.ratCoverage * Math.random() * 50;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 65;
                    }

                    recordInterception(offense, selQB, selDL, selLB, selCB, selS);

                    return;
                }
            }

            //Check for completion
            double completion, coverage;

            if (pos.equals("WR")) {
                completion = getHFadv() + getCoachAdv() + 2 * offense.playbookOff.getPassProtection() + 4 * offense.playbookOff.getPassPref() +
                        1.5 * normalize(selQB.ratPassAcc) + normalize(selWR.ratCatch);

                coverage = 2 * defense.playbookDef.getPassRush() + 4 * defense.playbookDef.getPassCoverage() + normalize(selCB.ratCoverage) + pressureOnQB;

            } else if (pos.equals("TE")) {
                completion = getHFadv() + getCoachAdv() + 2 * offense.playbookOff.getPassProtection() + 4 * offense.playbookOff.getPassPref() +
                        1.5 * normalize(selQB.ratPassAcc) + normalize(selTE.ratCatch);

                coverage = 2 * defense.playbookDef.getPassRush() + 4 * defense.playbookDef.getPassCoverage() + normalize(selLB.ratCoverage) + pressureOnQB;

            } else {
                completion = getHFadv() + getCoachAdv() + 2 * offense.playbookOff.getPassProtection() + 4 * offense.playbookOff.getPassPref() +
                        1.5 * normalize(selQB.ratPassAcc) + normalize(selRB.ratCatch);

                coverage = 2 * defense.playbookDef.getPassRush() + 4 * defense.playbookDef.getPassCoverage() + normalize(selLB2.ratCoverage) + pressureOnQB;
            }

            if (coverage * Math.random() > completion * Math.random()) {
                if (pos.equals("WR")) {
                    if (100 * Math.random() < (100 - selWR.ratCatch) / 3) {
                        //drop
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += getEventLog() + offense.abbr + " WR " + selWR.name + " dropped the catch.";

                        gameDown++;
                        recordDrop(selRB, selTE, selWR, pos);
                        //Drop ball = inc pass, so run time for the play, stop clock until next play, move on (aka return;)

                        gameTime -= timePerPlay * Math.random();
                        return;
                    }
                }
                if (pos.equals("TE")) {
                    if (100 * Math.random() < (100 - selTE.ratCatch) / 3) {
                        //drop
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += getEventLog() + offense.abbr + "TE " + selTE.name + " dropped the catch.";

                        gameDown++;
                        recordDrop(selRB, selTE, selWR, pos);
                        //Drop ball = inc pass, so run time for the play, stop clock until next play, move on (aka return;)

                        gameTime -= timePerPlay * Math.random();
                        return;
                    }
                }
                if (pos.equals("RB")) {
                    if (100 * Math.random() < (100 - selTE.ratCatch) / 3) {
                        //drop
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += getEventLog() + offense.abbr + " RB " + selRB.name + " dropped the catch.";

                        gameDown++;
                        recordDrop(selRB, selTE, selWR, pos);
                        //Drop ball = inc pass, so run time for the play, stop clock until next play, move on (aka return;)

                        gameTime -= timePerPlay * Math.random();
                        return;
                    }
                }


                //no completion, advance downs
                if (homeTeam.league.fullGameLog)
                    gameEventLog += getEventLog() + offense.abbr + " QB " + selQB.name + " threw an incomplete pass to the " + pos + ".";
                gameDown++;
                //Incomplete pass stops the clock, so just run time for how long the play took, then move on


                gameTime -= timePerPlay * Math.random();
                if (pos.equals("WR")) {
                    recordDefended(selWR, selCB);
                }
                return;


            } else {

                //COMPLETED PASS
                double escapeChance;

                if (pos.equals("WR")) {

                    yardsGain = (int) ((normalize(selQB.ratPassPow) + normalize(selWR.ratSpeed) - normalize(selCB.ratSpeed)) * Math.random() / 4.8 //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage());
                    //see if receiver can get yards after catch
                    escapeChance = (normalize(selWR.ratEvasion) * 3 - selCB.ratTackle - selS.ratTackle) * Math.random()   //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage();
                } else if (pos.equals("TE")) {

                    yardsGain = (int) ((normalize(selQB.ratPassPow) + normalize(selTE.ratSpeed) - normalize(selLB.ratSpeed)) * Math.random() / 4.8 //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage());
                    //see if receiver can get yards after catch
                    escapeChance = (normalize(selTE.ratEvasion) * 3 - selLB.ratTackle - defense.getS(0).ratOvr) * Math.random()  //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage();
                } else {

                    yardsGain = (int) ((normalize(selQB.ratPassPow) + normalize(selRB.ratSpeed) - normalize(selLB.ratSpeed)) * Math.random() / 4.8 //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage()) - 2;  //subtract 2 for screen pass behind line of scrimmage
                    //see if receiver can get yards after catch
                    escapeChance = (normalize(selRB.ratEvasion) * 3 - selLB2.ratTackle - defense.getS(0).ratOvr) * Math.random()  //STRATEGIES
                            + offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage();
                }

                //BIG GAIN
                if (escapeChance > 92 || Math.random() > 0.95) {
                    if (pos.equals("WR")) {
                        yardsGain += 3 + (selWR.ratSpeed * Math.random() / 4);
                    } else if (pos.equals("TE")) {
                        yardsGain += 3 + (selTE.ratSpeed * Math.random() / 4);
                    } else {
                        yardsGain += 4 + (selRB.ratSpeed * Math.random() / 4);
                    }
                }

                //BREAK AWAY FOR TD
                if (escapeChance > 80 && Math.random() < (0.1 + (offense.playbookOff.getPassPotential() - defense.playbookDef.getPassCoverage()) / 200)) {
                    yardsGain += 100;
                }

                //add yardage
                gameYardLine += yardsGain;
                if (gameYardLine >= 100) { //TD!
                    yardsGain -= gameYardLine - 100;
                    gameYardLine = 100 - yardsGain;
                    addPointsQuarter(6);
                    recordPassingTD(offense, selQB, selRB, selWR, selTE, yardsGain, pos);
                    gotTD = true;
                } else {
                    //check for fumble
                    double fumChance = (selS.ratTackle + selCB.ratTackle + selLB.ratTackle) / 3;
                    if (100 * Math.random() < fumChance / 50) {
                        //Fumble!
                        gotFumble = true;
                    }
                }

                if (!gotTD && !gotFumble) {
                    //check downs if there wasnt fumble or TD
                    if (homeTeam.league.fullGameLog) {
                        if (pos.equals("WR")) {
                            gameEventLog += getEventLog() + offense.abbr + " WR " + selWR.name + " caught the pass for a gain of " + yardsGain + " yards.";
                        } else if (pos.equals("RB")) {
                            gameEventLog += getEventLog() + offense.abbr + " RB " + selRB.name + " caught the pass for a gain of " + yardsGain + " yards.";
                        } else if (pos.equals("TE")) {
                            gameEventLog += getEventLog() + offense.abbr + " TE " + selTE.name + " caught the pass for a gain of " + yardsGain + " yards.";
                        }
                    }

                    gameYardsNeed -= yardsGain;


                    if (pos.equals("WR")) {
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 100;
                        selS.gameSim = selS.ratCoverage * Math.random() * 60;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 40;
                    } else if (pos.equals("TE")) {
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 40;
                        selS.gameSim = selS.ratCoverage * Math.random() * 60;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 80;
                    } else {
                        selCB.gameSim = selCB.ratCoverage * Math.random() * 30;
                        selS.gameSim = selS.ratCoverage * Math.random() * 40;
                        selLB.gameSim = selLB.ratCoverage * Math.random() * 60;
                    }

                    if (gameYardsNeed <= 0) {
                        // Only set new down and distance if there wasn't a TD
                        gameDown = 1;
                        gameYardsNeed = 10;
                        if (homeTeam.league.fullGameLog) gameEventLog += "\nFIRST DOWN!";

                    } else gameDown++;
                }

                //stats management
                recordPassCompletion(offense, selQB, selRB, selWR, selTE, selLB, selCB, selS, yardsGain, pos, gotTD);
            }

        }


        if (gotFumble) {
            String defender;
            if (pos.equals("WR")) {
                selDL.gameSim = selDL.ratTackle * Math.random() * 0;
                selCB.gameSim = selCB.ratTackle * Math.random() * 100;
                selS.gameSim = selS.ratTackle * Math.random() * 60;
                selLB.gameSim = selLB.ratTackle * Math.random() * 40;
            } else if (pos.equals("TE")) {
                selDL.gameSim = selDL.ratTackle * Math.random() * 15;
                selCB.gameSim = selCB.ratTackle * Math.random() * 50;
                selS.gameSim = selS.ratTackle * Math.random() * 55;
                selLB.gameSim = selLB.ratTackle * Math.random() * 80;
            } else {
                selDL.gameSim = selDL.ratTackle * Math.random() * 40;
                selCB.gameSim = selCB.ratTackle * Math.random() * 30;
                selS.gameSim = selS.ratTackle * Math.random() * 35;
                selLB.gameSim = selLB.ratTackle * Math.random() * 65;
            }
            recordRecFumble(offense, selRB, selWR, selTE, selDL, selLB, selCB, selS, pos);

            if (gamePoss) { // home possession
                homeTOs++;
            } else {
                awayTOs++;
            }
            if (!playingOT) {
                gameDown = 1;
                gameYardsNeed = 10;
                gamePoss = !gamePoss;
                gameYardLine = 100 - gameYardLine;
                gameTime -= timePerPlay * Math.random();
                return;
            } else {
                resetForOT();
                return;
            }
        }

        if (gotTD) {
            gameTime -= timePerPlay * Math.random();
            kickXP(offense, defense);
            if (!playingOT) kickOff(offense, defense);
            else resetForOT();
            return;
        }

        gameTime -= timePerPlay + timePerPlay * Math.random();

    }

    //RUSHING PLAYS POST-SNAP **

    private void rushPlay(Team offense, Team defense, PlayerQB selQB, PlayerRB selRB, PlayerTE selTE, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS, Player selS2) {
        boolean gotTD;
        gotTD = false;
        int yardsGain;
        int blockAdv = getOffRunProtection(offense, selTE) - getDefRunStop(defense, selLB, selS) + (offense.playbookOff.getRunProtection() - defense.playbookDef.getRunStop());

        //Start Rush Play
        if (selRB.gameSim >= selQB.gameSim) {
            yardsGain = (int) ((selRB.ratSpeed + blockAdv + getHFadv() + (int) (Math.random() * getCoachAdv())) * Math.random() / 10 + (double) offense.playbookOff.getRunPotential() / 2 - (double) defense.playbookDef.getRunCoverage() / 2);
        } else {
            yardsGain = (int) ((selQB.ratSpeed + blockAdv + getHFadv() + (int) (Math.random() * getCoachAdv())) * Math.random() / 10 + (double) offense.playbookOff.getRunPotential() / 2 - (double) defense.playbookDef.getRunCoverage() / 2);
        }

        //Break past neutral zone
        if (selRB.gameSim >= selQB.gameSim) {
            if (yardsGain < 2) {
                yardsGain += selRB.ratRushPower / 20 - 3 - (double) defense.playbookDef.getRunCoverage() / 2;
            } else {
                //break free from tackles
                if (Math.random() < (0.28 + (offense.playbookOff.getRunPotential() - (double) defense.playbookDef.getRunCoverage() / 2) / 50)) {
                    yardsGain += (selRB.ratEvasion - blockAdv) / 5 * Math.random();
                }
            }
        } else {
            if (yardsGain < 2) {
                yardsGain += selQB.ratEvasion / 20 - 3 - (double) defense.playbookDef.getRunCoverage() / 2;
            } else {
                //break free from tackles
                if (Math.random() < (0.20 + (offense.playbookOff.getRunPotential() - (double) defense.playbookDef.getRunCoverage() / 2) / 50)) {
                    yardsGain += (selQB.ratEvasion - blockAdv) / 5 * Math.random();
                }
            }
        }

        //add yardage
        gameYardLine += yardsGain;

        if (gameYardLine >= 100) { //TD!
            addPointsQuarter(6);
            yardsGain -= gameYardLine - 100;
            gameYardLine = 100 - yardsGain;
            gotTD = true;
        }

        //stats management
        recordRushAttempt(offense, selQB, selRB, selDL, selLB, selCB, selS, yardsGain, gotTD);

        //check downs if there wasn't TD
        if (!gotTD) {
            //check downs
            gameYardsNeed -= yardsGain;
            if (gameYardsNeed <= 0) {
                // Only set new down and distance if there wasn't a TD
                gameDown = 1;
                gameYardsNeed = 10;
                if (homeTeam.league.fullGameLog) gameEventLog += "\nFIRST DOWN!";
            } else gameDown++;
        }


        if (gotTD) {
            gameTime -= 5 + timePerPlay * Math.random(); // Clock stops for the TD, just burn time for the play
            kickXP(offense, defense);
            if (!playingOT) kickOff(offense, defense);
            else resetForOT();
        } else {
            gameTime -= timePerPlay + timePerPlay * Math.random();
            //check for fumble
            double fumChance = ((defense.getS(0).ratTackle + selLB.ratTackle) / 2 + defense.getCompositeDLRush() - getHFadv()) / 2 + offense.playbookOff.getRunProtection();  //STRATEGIES
            if (100 * Math.random() < fumChance / 50) {
                //Fumble!

                if (yardsGain < 5) {
                    selDL.gameSim = selDL.ratTackle * Math.random() * 80;
                    selCB.gameSim = selCB.ratTackle * Math.random() * 20;
                    selS.gameSim = selS.ratTackle * Math.random() * 20;
                    selLB.gameSim = selLB.ratTackle * Math.random() * 60;
                } else {
                    selDL.gameSim = selDL.ratTackle * Math.random() * 20;
                    selCB.gameSim = selCB.ratTackle * Math.random() * 25;
                    selS.gameSim = selS.ratTackle * Math.random() * 50;
                    selLB.gameSim = selLB.ratTackle * Math.random() * 75;
                }

                recordRushFumble(offense, selQB, selRB, selDL, selLB, selCB, selS);

                if (!playingOT) {
                    gameDown = 1;
                    gameYardsNeed = 10;
                    gamePoss = !gamePoss;
                    gameYardLine = 100 - gameYardLine;
                } else resetForOT();
            }
        }
    }


    //PLAY CHARACTERISTICS

    //PASS PROTECTION
    private int getOffPassProtection(Team off, PlayerTE TE) {
        int OP = 0;
        if (off.playbookOff.getPassUsage() > 0) OP = getCompositeOLPassTE(TE);
        else OP = getCompositeOLPass();

        return OP;
    }

    private int getCompositeOLPass() {
        int compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratPassBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL = compositeOL / 5;

        return (compositeOL);
    }

    private int getCompositeOLPassTE(PlayerTE selTE) {
        int compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratPassBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL += selTE.ratRunBlock;
        compositeOL = (int) (compositeOL / 5.5);

        return (compositeOL);
    }

    //RUN BLOCKING
    private int getOffRunProtection(Team off, PlayerTE TE) {
        int OP = 0;
        if (off.playbookOff.getRunUsage() == 0) OP = getCompositeOLRush();
        else OP = getCompositeOLRushTE(TE);

        return OP;
    }

    private int getCompositeOLRush() {
        int compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratRunBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL = compositeOL / 5;

        return compositeOL;
    }

    private int getCompositeOLRushTE(PlayerTE selTE) {
        int compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratRunBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL += selTE.ratRunBlock;
        compositeOL = (int) (compositeOL / 5.5); //TE bonus

        return compositeOL;
    }

    //PASS PRESSURE
    private int getDefPassPressure(Team def, PlayerLB LB) {
        int DP = 0;
        if (def.playbookDef.getPassSpy() == 0) DP = getCompositeDLPass();
        else DP = getCompositeDLPassLB(LB);

        return DP;
    }

    private int getCompositeDLPass() {
        int compositeDL = 0;
        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratPassRush) / 2;
        }
        compositeDL = compositeDL / 4;

        return compositeDL;
    }

    private int getCompositeDLPassLB(PlayerLB selLB) {
        int compositeDL = 0;
        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratPassRush) / 2;
        }
        compositeDL += (selLB.ratSpeed + selLB.ratTackle) / 2;
        compositeDL = (int) (compositeDL / 4.58);  // 4.58 is equal to 5.5 for OL + TE

        return compositeDL;
    }

    //DEFENDING THE RUN
    private int getDefRunStop(Team def, PlayerLB LB, PlayerS S) {
        int DP = 0;
        if (def.playbookDef.getRunSpy() == 0) DP = getCompositeDLRush(LB);
        else DP = getCompositeDLRush(LB, S);

        return DP;
    }

    private int getCompositeDLRush(PlayerLB selLB) {
        int compositeDL = 0;
        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratRunStop) / 2;
        }
        compositeDL += selLB.ratRunStop;
        compositeDL = compositeDL / 5;

        return compositeDL;
    }

    private int getCompositeDLRush(PlayerLB selLB, PlayerS selS) {
        int compositeDL = 0;
        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratRunStop) / 2;
        }
        compositeDL += selLB.ratRunStop + selS.ratRunStop;
        compositeDL = (int) (compositeDL / 5.5);

        return compositeDL;
    }


    //KICKING PLAYS

    private void fieldGoalAtt(Team offense, Team defense) {
        PlayerK selK = offense.getK(0);
        selK.gameSnaps++;
        gameYardLine -= 7;


        double fgDistRatio = Math.pow((110 - gameYardLine) / 50, 2);
        double fgAccRatio = Math.pow((110 - gameYardLine) / 50, 1.25);
        double fgDistChance = (getHFadv() + offense.getK(0).ratKickPow - fgDistRatio * 80);
        double fgAccChance = (getHFadv() + offense.getK(0).ratKickAcc - fgAccRatio * 80);

        if (gameTime > 120 || playingOT == false) {
            if (fgDistChance > 20 && fgAccChance * Math.random() > 15) {
                // made the fg
                if (gamePoss) { // home possession
                    homeScore += 3;
                } else {
                    awayScore += 3;
                }
                gameEventLog += getEventLogScoring() + offense.abbr + " K " + offense.getK(0).name + " made the " + (110 - gameYardLine) + " yard FG.";
                addPointsQuarter(3);

                selK.statsFGMade++;
                selK.statsFGAtt++;
                selK.gameFGMade++;
                selK.gameFGAttempts++;

                if (!playingOT) kickOff(offense, defense);
                else resetForOT();

            } else {
                //miss
                gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " missed the " + (110 - gameYardLine) + " yard FG.";
                selK.statsFGAtt++;
                selK.gameFGAttempts++;
                if (!playingOT) {
                    gameYardLine = Math.max(100 - gameYardLine, 20); //Misses inside the 20 = defense takes over on the 20
                    gameDown = 1;
                    gameYardsNeed = 10;
                    if (gamePoss) { // home possession
                    } else {
                    }
                    gamePoss = !gamePoss;
                } else resetForOT();
            }
        } else {
            if (fgDistChance > 20 && fgAccChance * Math.random() > 15 && offense.getK(0).ratPressure > Math.random() * 95) {
                // made the fg
                if (gamePoss) { // home possession
                    homeScore += 3;
                } else {
                    awayScore += 3;
                }
                gameEventLog += getEventLogScoring() + offense.abbr + " K " + offense.getK(0).name + " made the " + (110 - gameYardLine) + " yard FG.";
                addPointsQuarter(3);
                selK.statsFGMade++;
                selK.statsFGAtt++;
                selK.gameFGMade++;
                selK.gameFGAttempts++;

                if (!playingOT) kickOff(offense, defense);
                else resetForOT();

            } else {
                //miss
                gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " missed the " + (110 - gameYardLine) + " yard FG.";
                offense.getK(0).statsFGAtt++;
                if (!playingOT) {
                    gameYardLine = Math.max(100 - gameYardLine, 20); //Misses inside the 20 = defense takes over on the 20
                    gameDown = 1;
                    gameYardsNeed = 10;
                    selK.gameFGAttempts++;
                    gamePoss = !gamePoss;
                } else resetForOT();
            }
        }

        gameTime -= 20;

    }

    private void kickXP(Team offense, Team defense) {
        PlayerK selK = offense.getK(0);
        selK.gameSnaps++;

        // No XP/2pt try if the TD puts the bottom OT offense ahead (aka wins the game)
        if (playingOT && bottomOT && (((numOT % 2 == 0) && awayScore > homeScore) || ((numOT % 2 != 0) && homeScore > awayScore))) {
            gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + "\n" + offense.abbr + " wins on a walk-off touchdown!";
        }
        // If a TD is scored as time expires, there is no XP/2pt if the score difference is greater than 2
        else if (!playingOT && gameTime <= 0 && ((homeScore - awayScore > 2) || (awayScore - homeScore > 2))) {
            //Walkoff TD!
            if ((Math.abs(homeScore - awayScore) < 7) && ((gamePoss && homeScore > awayScore) || (!gamePoss && awayScore > homeScore)))
                gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + "\n" + offense.abbr + " wins on a walk-off touchdown!";
                //Just rubbing in the win or saving some pride
            else gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo;
        } else {
            if ((numOT >= 3) || (((gamePoss && (awayScore - homeScore) == 2) || (!gamePoss && (homeScore - awayScore) == 2)) && gameTime < 300)) {
                //go for 2
                boolean successConversion = false;
                if (Math.random() <= 0.50) {
                    //rushing
                    int blockAdv = (int) offense.getCompositeOLRush() - (int) defense.getCompositeDLRush();
                    int yardsGain = (int) ((offense.getRB(0).ratSpeed + blockAdv) * Math.random() / 6);
                    if (yardsGain > 5) {
                        successConversion = true;
                        if (gamePoss) { // home possession
                            homeScore += 2;
                        } else {
                            awayScore += 2;
                        }
                        addPointsQuarter(2);
                        gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getRB(0).name + " rushed for the 2pt conversion.";
                    } else {
                        gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getRB(0).name + " stopped at the line of scrimmage, failed the 2pt conversion.";
                    }
                } else {
                    int pressureOnQB = (int) defense.getCompositeDLPass() * 2 - (int) offense.getCompositeOLPass();
                    double completion = (normalize(offense.getQB(0).ratPassAcc) + offense.getWR(0).ratCatch - defense.getCB(0).ratCoverage) / 2 + 25 - pressureOnQB / 20;
                    if (100 * Math.random() < completion) {
                        successConversion = true;
                        if (gamePoss) { // home possession
                            homeScore += 2;
                        } else {
                            awayScore += 2;
                        }
                        addPointsQuarter(2);
                        gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getQB(0).name + " completed the pass to " + offense.getWR(0).name + " for the 2pt conversion.";
                    } else {
                        gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getQB(0).name + "'s pass incomplete to " + offense.getWR(0).name + " for the failed 2pt conversion.";
                    }
                }

            } else {
                //kick XP
                if (Math.random() * 100 < 23 + offense.getK(0).ratKickAcc && Math.random() > 0.01) {
                    //made XP
                    if (gamePoss) { // home possession
                        homeScore += 1;
                    } else {
                        awayScore += 1;
                    }
                    gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getK(0).name + " made the XP.";
                    addPointsQuarter(1);
                    selK.statsXPMade++;
                    selK.gameXPMade++;
                } else {
                    gameEventLog += getEventLogScoring() + "TOUCHDOWN!\n" + tdInfo + " " + offense.getK(0).name + " missed the XP.";
                    // missed XP
                }
                selK.statsXPAtt++;
                selK.gameXPAttempts++;
            }
        }
    }

    private void kickOff(Team offense, Team defense) {
        PlayerReturner returner = selectReturner();
        int specialTeams = getSpecialTeamsD(offense);
        gameYardLine = 65;

        if (gameTime <= 0) return;
        else {
            //Decide whether to onside kick. Only if losing but within 8 points with < 3 min to go
            if (gameTime < 180 && ((gamePoss && (awayScore - homeScore) <= 8 && (awayScore - homeScore) > 0)
                    || (!gamePoss && (homeScore - awayScore) <= 8 && (homeScore - awayScore) > 0))) {
                // Yes, do onside
                if (offense.getK(0).ratKickFum * Math.random() > 60 || Math.random() < 0.1) {
                    //Success!
                    gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " successfully executes onside kick! " + offense.abbr + " has possession!";
                } else {
                    // Fail
                    gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " failed the onside kick and lost possession.";
                    gamePoss = !gamePoss;
                }
                gameYardLine = (gameYardLine - 10) - (int) (10 * Math.random());
                gameDown = 1;
                gameYardsNeed = 10;

                gameTime -= 4 + 5 * Math.random(); //Onside kicks are very fast, unless there's a weird fight for the ball. Chance to burn a lot of time, odds are you'll burn a little time.
            } else {
                // Just regular kick off

                gameYardLine = returnPlay(gameYardLine, offense.getK(0), returner, specialTeams, true);

                gameDown = 1;
                gameYardsNeed = 10;
                gamePoss = !gamePoss;

                //Touchdown...
                if (gameYardLine >= 100) {
                    addPointsQuarter(6);
                    if (gamePoss) { // home possession
                        homeScore += 6;
                    } else {
                        awayScore += 6;
                    }
                    tdInfo = returner.team + " " + returner.position + " " + returner.name + " returns the kick " + returnYards + " yards for a TOUCHDOWN!";
                    returner.kTD++;
                    kickXP(defense, offense);
                    if (!playingOT) kickOff(defense, offense);
                    else resetForOT();
                } else {
                    if (gameYardLine <= 0) {
                        gameYardLine = touchback;
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += "\n\nKick-off!\n" + returner.team + " " + returner.name + " lets it go for a touchback.";
                    } else {
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += "\n\nKick-off!\n" + returner.team + " " + returner.name + " returns the kickoff to the " + gameYardLine + " yard line.";
                    }
                }
            }

            gameTime -= timePerPlay * Math.random();
        }
    }

    private void freeKick(Team offense, Team defense) {
        if (gameTime <= 0) return;
        else {
            PlayerReturner returner = selectReturner();
            int specialTeams = getSpecialTeamsD(offense);

            //Decide whether to onside kick. Only if losing but within 8 points with < 3 min to go
            if (gameTime < 180 && ((gamePoss && (awayScore - homeScore) <= 8 && (awayScore - homeScore) > 0)
                    || (!gamePoss && (homeScore - awayScore) <= 8 && (homeScore - awayScore) > 0))) {
                // Yes, do onside
                if (offense.getK(0).ratKickFum * Math.random() > 60 || Math.random() < 0.1) {
                    //Success!
                    gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " successfully executes onside kick! " + offense.abbr + " has possession!";
                    gameYardLine = 35;
                    gameDown = 1;
                    gameYardsNeed = 10;
                } else {
                    // Fail
                    gameEventLog += getEventLog() + offense.abbr + " K " + offense.getK(0).name + " failed the onside kick and lost possession.";
                    gamePoss = !gamePoss;
                    gameYardLine = 65;
                    gameDown = 1;
                    gameYardsNeed = 10;
                }

                gameTime -= 4 + 4 * Math.random(); //Onside kicks are very fast, unless there's a weird fight for the ball. Chance to burn a lot of time, odds are you'll burn a little time.
            } else {
                // Just regular kick off
                //gameYardLine = (int) (115 - (offense.getK(0).ratKickPow + 20 - 40 * Math.random()));
                //if (gameYardLine <= 0) gameYardLine = 25;
                gameYardLine = 80;
                gameYardLine = returnPlay(gameYardLine, offense.getK(0), returner, specialTeams, true);

                gameDown = 1;
                gameYardsNeed = 10;
                gamePoss = !gamePoss;

                //Touchdown...
                if (gameYardLine >= 100) {
                    addPointsQuarter(6);
                    if (gamePoss) { // home possession
                        homeScore += 6;
                    } else {
                        awayScore += 6;
                    }
                    tdInfo = returner.team + " " + returner.position + " " + returner.name + " returns the kick " + returnYards + " yards for a TOUCHDOWN!";
                    returner.kTD++;
                    kickXP(defense, offense);
                    if (!playingOT) kickOff(defense, offense);
                    else resetForOT();
                } else {
                    if (gameYardLine <= 0) {
                        gameYardLine = touchback;
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += "\n\nFree-Kick!\n" + returner.team + " " + returner.name + " lets it go for a touchback.";
                    } else {
                        if (homeTeam.league.fullGameLog)
                            gameEventLog += "\n\nFree-Kick!\n" + returner.team + " " + returner.name + " returns the free-kick to the " + gameYardLine + " yard line.";
                    }
                }

                gameTime -= timePerPlay * Math.random();

            }
        }
    }

    private void puntPlay(Team offense, Team defense) {
        PlayerReturner returner = selectReturner();
        int specialTeams = getSpecialTeamsD(offense);

        gameYardLine = returnPlay(gameYardLine, offense.getK(0), returner, specialTeams, false);
        gamePoss = !gamePoss;

        //Touchdown...
        if (gameYardLine >= 100) {
            addPointsQuarter(6);
            if (gamePoss) { // home possession
                homeScore += 6;
            } else {
                awayScore += 6;
            }
            tdInfo = returner.team + " " + returner.position + " " + returner.name + " returns the punt " + returnYards + " yards for a TOUCHDOWN!";
            returner.pTD++;
            kickXP(defense, offense);
            if (!playingOT) kickOff(defense, offense);
            else resetForOT();
        } else {
            if (gameYardLine <= 0) {
                gameYardLine = touchback;
                if (homeTeam.league.fullGameLog)
                    gameEventLog += "\n\nPunt!\n" + returner.team + " " + returner.name + " lets it go for a touchback.";
            } else {
                if (homeTeam.league.fullGameLog)
                    gameEventLog += "\n\nPunt!\n" + returner.team + " " + returner.name + " returns the punt to the " + gameYardLine + " yard line.";
            }
        }

        gameDown = 1;
        gameYardsNeed = 10;
        gameTime -= timePerPlay + timePerPlay * Math.random();
    }

    private PlayerReturner selectReturner() {
        if (gamePoss) return awayKickReturner;
        else return homeKickReturner;
    }

    private int returnPlay(int startYards, PlayerK kicker, PlayerReturner returner, int ST, boolean kickoff) {
        int yards;
        returnYards = 0;

        //Kicker kicks the ball
        if (kickoff) yards = startYards - (kicker.ratKickPow / 2) - (int) (25 * Math.random());
        else yards = startYards - (kicker.ratKickPow - (25 + (int) (20 * Math.random())));

        if (yards < -3) {
            //touchback
            return yards;
        } else {
            //Returner receives ball and runs at defense

            int ret = (int) (returner.ratSpeed * Math.random());
            int def = (int) (ST * Math.random());

            //Returner tackled by playerST?
            if (def >= ret) returnYards = (int) (Math.random() * 10) + 1;
            else if (ret > def + 80) returnYards += 100 - yards;
            else if (ret > def + 50) returnYards = (int) Math.random() * 40 + 30;
            else if (ret > def + 35) returnYards = (int) Math.random() * 20 + 20;
            else returnYards = ret - def;

            if (kickoff) {
                returner.kYards += returnYards;
                returner.kReturns++;
            } else {
                returner.pYards += returnYards;
                returner.pReturns++;
            }
            yards += returnYards;
            return yards;
        }
    }

    //STATISTICS MANAGEMENT

    private void recordRushAttempt(Team offense, PlayerQB selQB, PlayerRB selRB, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS, int yardsGain, boolean gotTD) {
        String defender = "";
        if (selRB.gameSim >= selQB.gameSim) {
            selRB.statsRushAtt++;
            selRB.statsRushYards += yardsGain;
            selRB.gameRushAttempts++;
            selRB.gameRushYards += yardsGain;
        } else {
            selQB.statsRushAtt++;
            selQB.statsRushYards += yardsGain;
            selQB.gameRushAttempts++;
            selQB.gameRushYards += yardsGain;
        }

        for (int i = 0; i < 5; i++) {
            teamOLs.get(i).statsRushYards += yardsGain;
            teamOLs.get(i).statsRushSnaps++;
        }

        if (yardsGain < 2 && !gotTD) {
            selDL.gameTackles++;
            selDL.statsTackles++;
            defender = "DL " + selDL.name;
        } else if (yardsGain >= 2 && yardsGain < 12 && !gotTD) {
            selLB.gameTackles++;
            selLB.statsTackles++;
            defender = "LB " + selLB.name;
        } else if (yardsGain >= 12 && !gotTD) {
            if (selCB.ratTackle * Math.random() * 50 >= selS.ratTackle * Math.random() * 100) {
                selCB.gameTackles++;
                selCB.statsTackles++;
                defender = "CB " + selCB.name;
            } else {
                selS.gameTackles++;
                selS.statsTackles++;
                defender = "S " + selS.name;
            }
        }

        if (gamePoss) { // home possession
            homeTeam.teamRushYards += yardsGain;
        } else {
            awayTeam.teamRushYards += yardsGain;
        }

        //RECORD TOUCHDOWN
        if (gotTD) {
            if (selRB.gameSim >= selQB.gameSim) {
                selRB.gameRushTDs++;
                selRB.statsRushTD++;
                tdInfo = offense.abbr + " RB " + selRB.name + " rushed " + yardsGain + " yards for a TD.";
            } else {
                selQB.gameRushTDs++;
                selQB.statsRushTD++;
                tdInfo = offense.abbr + " QB " + selQB.name + " rushed " + yardsGain + " yards for a TD.";
            }

            if (gamePoss) { // home possession
                homeScore += 6;
            } else {
                awayScore += 6;
            }
        } else {
            if (homeTeam.league.fullGameLog)
                if (selRB.gameSim >= selQB.gameSim) {
                    gameEventLog += getEventLog() + offense.abbr + " RB " + selRB.name + " rushed for " + yardsGain + " yards, and was tackled by " + defender + ".";
                } else {
                    gameEventLog += getEventLog() + offense.abbr + " QB " + selQB.name + " scrambled for " + yardsGain + " yards, and was tackled by " + defender + ".";
                }

        }
    }

    private void recordRushFumble(Team offense, PlayerQB selQB, PlayerRB selRB, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS) {
        String defender;
        ArrayList<Player> def = new ArrayList<>();
        def.add(selDL);
        def.add(selCB);
        def.add(selLB);
        def.add(selS);
        Collections.sort(def, new CompGamePlayerPicker());
        String pos = def.get(0).position;

        if (selRB.gameSim >= selQB.gameSim) {
            selRB.gameFumbles++;
            selRB.statsFumbles++;
        } else {
            selQB.gameFumbles++;
            selQB.statsFumbles++;
        }

        if (gamePoss) {
            homeTOs++;
        } else {
            awayTOs++;
        }

        if (pos.equals("DL")) {
            selDL.gameTackles++;
            selDL.statsTackles++;
            selDL.gameFumbles++;
            selDL.statsFumbles++;
            defender = ("DL " + selDL.name);
        } else if (pos.equals("CB")) {
            selCB.gameTackles++;
            selCB.statsTackles++;
            selCB.gameFumbles++;
            selCB.statsFumbles++;
            defender = ("CB " + selCB.name);
        } else if (pos.equals("S")) {
            selS.gameTackles++;
            selS.statsTackles++;
            selS.gameFumbles++;
            selS.statsFumbles++;
            defender = ("S " + selS.name);
        } else {
            selLB.gameTackles++;
            selLB.statsTackles++;
            selLB.gameFumbles++;
            selLB.statsFumbles++;
            defender = ("LB " + selLB.name);
        }

        if (selRB.gameSim >= selQB.gameSim) {
            gameEventLog += getEventLog() + "FUMBLE!\n" + offense.abbr + " RB " + selRB.name + " fumbled the ball while rushing and recovered by " + defender + ".";
        } else {
            gameEventLog += getEventLog() + "FUMBLE!\n" + offense.abbr + " QB " + selQB.name + " fumbled the ball while rushing and recovered by " + defender + ".";
        }

    }

    private void recordPassingTD(Team offense, PlayerQB selQB, PlayerRB selRB, PlayerWR selWR, PlayerTE selTE, int yardsGain, String pos) {
        if (gamePoss) { // home possession
            homeScore += 6;
        } else {
            awayScore += 6;
        }

        selQB.gamePassTDs++;
        selQB.statsPassTD++;

        if (pos.equals("WR")) {
            selWR.gameRecTDs++;
            selWR.statsRecTD++;
            tdInfo = offense.abbr + " QB " + selQB.name + " threw a " + yardsGain + " yard TD to WR " + selWR.name + ".";
        } else if (pos.equals("TE")) {
            selTE.gameRecTDs++;
            selTE.statsRecTD++;
            tdInfo = offense.abbr + " QB " + selQB.name + " threw a " + yardsGain + " yard TD to TE " + selTE.name + ".";
        } else {
            selRB.gameRecTDs++;
            selRB.statsRecTD++;
            tdInfo = offense.abbr + " QB " + selQB.name + " threw a " + yardsGain + " yard TD to RB " + selRB.name + ".";
        }


    }

    private void recordPassCompletion(Team offense, PlayerQB selQB, PlayerRB selRB, PlayerWR selWR, PlayerTE selTE, PlayerLB selLB, PlayerCB selCB, PlayerS selS, int yardsGain, String pos, boolean gotTD) {
        String defender;
        ArrayList<Player> def = new ArrayList<>();
        def.add(selCB);
        def.add(selLB);
        def.add(selS);
        Collections.sort(def, new CompGamePlayerPicker());
        String tackler = def.get(0).position;

        selQB.statsPassComp++;
        selQB.statsPassYards += yardsGain;
        selQB.gamePassComplete++;
        selQB.gamePassYards += yardsGain;

        for (int i = 0; i < 5; i++) {
            teamOLs.get(i).statsPassYards += yardsGain;
        }

        if (pos.equals("WR")) {
            selWR.statsReceptions++;
            selWR.statsRecYards += yardsGain;
            selWR.gameReceptions++;
            selWR.gameRecYards += yardsGain;
        }
        if (pos.equals("TE")) {
            selTE.statsReceptions++;
            selTE.statsRecYards += yardsGain;
            selTE.gameReceptions++;
            selTE.gameRecYards += yardsGain;
        }
        if (pos.equals("RB")) {
            selRB.statsReceptions++;
            selRB.statsRecYards += yardsGain;
            selRB.gameReceptions++;
            selRB.gameRecYards += yardsGain;
        }

        if (!gotTD) {
            if (tackler.equals("CB")) {
                selCB.gameTackles++;
                selCB.statsTackles++;

            } else if (tackler.equals("S")) {
                selS.gameTackles++;
                selS.statsTackles++;

            } else {
                selLB.gameTackles++;
                selLB.statsTackles++;

            }
        }

        offense.teamPassYards += yardsGain;
    }

    private void recordPassAttempt(PlayerQB selQB, PlayerRB selRB, PlayerWR selWR, PlayerTE selTE, PlayerLB selLB, PlayerCB selCB, String pos) {
        selQB.statsPassAtt++;
        selQB.gamePassAtempts++;

        for (int i = 0; i < 5; i++) {
            teamOLs.get(i).statsPassSnaps++;
        }

        if (pos.equals("WR")) {
            selWR.statsTargets++;
            selWR.gameTargets++;
            selCB.statsTargets++;
            selCB.gameTargets++;
            selCB.statsIncomplete++;
            selCB.gameIncomplete++;
        }
        if (pos.equals("TE")) {
            selTE.statsTargets++;
            selTE.gameTargets++;
            selLB.gameTargets++;
            selLB.gameIncomplete++;
        }
        if (pos.equals("RB")) {
            selRB.gameTargets++;
        }
    }

    private void recordDrop(PlayerRB selRB, PlayerTE selTE, PlayerWR selWR, String pos) {
        if (pos.equals("WR")) {
            selWR.gameDrops++;
            selWR.statsDrops++;
        }
        if (pos.equals("TE")) {
            selTE.gameDrops++;
            selTE.statsDrops++;
        }
        if (pos.equals("RB")) {
            selRB.gameDrops++;
        }
    }

    private void recordDefended(PlayerWR selWR, PlayerCB selCB) {

        if ((selCB.ratJump * Math.random() + selCB.ratCoverage * Math.random()) > (selWR.ratJump * Math.random() + selWR.ratCatch * Math.random()) * 2) {
            selCB.statsDefended++;
            selCB.gameDefended++;
        }
    }

    private void recordInterception(Team offense, PlayerQB selQB, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS) {
        String defender;
        ArrayList<Player> def = new ArrayList<>();
        def.add(selDL);
        def.add(selCB);
        def.add(selLB);
        def.add(selS);
        Collections.sort(def, new CompGamePlayerPicker());
        String pos = def.get(0).position;

        if (pos.equals("DL")) {
            selDL.gameInterceptions++;
            selDL.statsInts++;
            defender = ("DL " + selDL.name);
        } else if (pos.equals("CB")) {
            selCB.gameInterceptions++;
            selCB.statsInts++;
            defender = ("CB " + selCB.name);
        } else if (pos.equals("S")) {
            selS.gameInterceptions++;
            selS.statsInts++;
            defender = ("S " + selS.name);
        } else {
            selLB.gameInterceptions++;
            selLB.statsInts++;
            defender = ("LB " + selLB.name);
        }

        if (gamePoss) { // home possession
            homeTOs++;
        } else {
            awayTOs++;
        }

        selQB.statsInt++;
        selQB.gamePassInts++;

        //Log the event before decreasing the time, in keeping with the standard of other logged plays (TD, Fumble, etc.)
        gameEventLog += getEventLog() + "INTERCEPTED!\n" + offense.abbr + " QB " + offense.getQB(0).name + " was intercepted by " + defender + ".";
        //Clock stops after a pick, so just run time off the clock for the play that occurred
        //NOTE: If the ability to run an interception back is ever added, this should be changed to be more time
        gameTime -= timePerPlay * Math.random();
        if (!playingOT) {
            gameDown = 1;
            gameYardsNeed = 10;
            gamePoss = !gamePoss;
            gameYardLine = 100 - gameYardLine;
        } else resetForOT();

    }

    private void recordSack(Team offense, Team defense, PlayerQB selQB, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS) {
        String defender = "";
        int sackloss = (3 + (int) (Math.random() * (normalize((int) defense.getCompositeDLPass()) - normalize((int) offense.getCompositeOLPass())) / 2));
        if (sackloss < 2) sackloss = 2;

        ArrayList<Player> def = new ArrayList<>();
        def.add(selDL);
        def.add(selCB);
        def.add(selLB);
        def.add(selS);
        Collections.sort(def, new CompGamePlayerPicker());
        String pos = def.get(0).position;

        selQB.statsSacked++;
        selQB.gameSacks++;
        selQB.statsRushYards -= sackloss;
        selQB.gameRushYards -= sackloss;


        if (pos.equals("DL")) {
            selDL.gameTackles++;
            selDL.gameSacks++;
            selDL.statsTackles++;
            selDL.statsSacks++;
            defender = ("DL " + selDL.name);
            for (int i = 0; i < 5; i++) {
                teamOLs.get(i).statsSacksAllowed++;
                teamOLs.get(i).statsPassSnaps++;
            }
        } else if (pos.equals("LB")) {
            selLB.gameTackles++;
            selLB.gameSacks++;
            selLB.statsTackles++;
            selLB.statsSacks++;
            defender = ("LB " + selLB.name);
        } else if (pos.equals("CB")) {
            selCB.gameTackles++;
            selCB.gameSacks++;
            selCB.statsTackles++;
            selCB.statsSacks++;
            defender = ("CB " + selCB.name);
        } else if (pos.equals("S")) {
            selS.gameTackles++;
            selS.gameSacks++;
            selS.statsTackles++;
            selS.statsSacks++;
            defender = ("S " + selS.name);
        }

        if (gameYardLine < 0) {
            // Safety!
            // Eat some time up for the play that was run, stop it once play is over
            gameTime -= 10 * Math.random();
            recordSafety(defender);
            return; // Run recordSafety then get out of recordSack (recordSafety() will take care of free kick)
        }

        if (homeTeam.league.fullGameLog)
            gameEventLog += getEventLog() + "SACK!\n" + " QB " + offense.getQB(0).name +
                    " was sacked for a loss of " + sackloss + " by " + defender + ".";

        gameDown++; // Advance gameDown after checking for Safety, otherwise game log reports Safety occurring one down later than it did
        gameYardsNeed += sackloss;
        gameYardLine -= sackloss;

        //Similar amount of time as rushing, minus some in-play time -- sacks are faster (usually)
        gameTime -= timePerPlay + timePerPlay * Math.random();
    }

    private void recordRecFumble(Team offense, PlayerRB selRB, PlayerWR selWR, PlayerTE selTE, PlayerDL selDL, PlayerLB selLB, PlayerCB selCB, PlayerS selS, String pos) {
        String defender;
        ArrayList<Player> def = new ArrayList<>();
        def.add(selDL);
        def.add(selCB);
        def.add(selLB);
        def.add(selS);
        Collections.sort(def, new CompGamePlayerPicker());
        String player = def.get(0).position;


        if (pos.equals("WR")) {
            selWR.gameFumbles++;
            selWR.statsFumbles++;
        }
        if (pos.equals("TE")) {
            selTE.gameFumbles++;
            selTE.statsFumbles++;
        }
        if (pos.equals("RB")) {
            selRB.gameFumbles++;
            selRB.statsFumbles++;
        }

        if (player.equals("DL")) {
            selDL.gameTackles++;
            selDL.statsTackles++;
            selDL.gameFumbles++;
            selDL.statsFumbles++;
            defender = ("DL " + selDL.name);
        } else if (player.equals("CB")) {
            selCB.gameTackles++;
            selCB.statsTackles++;
            selCB.gameFumbles++;
            selCB.statsFumbles++;
            defender = ("CB " + selCB.name);
        } else if (player.equals("S")) {
            selS.gameTackles++;
            selS.statsTackles++;
            selS.gameFumbles++;
            selS.statsFumbles++;
            defender = ("S " + selS.name);
        } else {
            selLB.gameTackles++;
            selLB.statsTackles++;
            selLB.gameFumbles++;
            selLB.statsFumbles++;
            defender = ("LB " + selLB.name);
        }

        gameEventLog += getEventLog() + "FUMBLE!\n" + offense.abbr + " receiver " + selWR.name + " fumbled the ball after a catch. It was recovered by " + defender + ".";
    }

    private void recordSafety(String defender) {
        if (gamePoss) {
            awayScore += 2;
            gameEventLog += getEventLogScoring() + "SAFETY!\n" + homeTeam.abbr + " QB " + homeTeam.getQB(0).name +
                    " was tackled in the endzone by " + defender + "! Result is a Safety and " + awayTeam.abbr + " will get possession.";
            freeKick(homeTeam, awayTeam);
        } else {
            homeScore += 2;
            gameEventLog += getEventLogScoring() + "SAFETY!\n" + awayTeam.abbr + " QB " + awayTeam.getQB(0).name +
                    " was tackled in the endzone by " + defender + "! Result is a Safety and " + homeTeam.abbr + " will get possession.";
            freeKick(awayTeam, homeTeam);
        }
    }

    private void recordReturnStats() {
        if (homeKickReturner.position == "RB") {
            for (int i = 0; i < homeTeam.startersRB + homeTeam.subRB; i++) {
                if (homeTeam.getRB(i).name.equals(homeKickReturner.name)) {
                    homeTeam.getRB(i).statsKickRets += homeKickReturner.kReturns;
                    homeTeam.getRB(i).statsKickRetYards += homeKickReturner.kYards;
                    homeTeam.getRB(i).statsKickRetTDs += homeKickReturner.kTD;
                    homeTeam.getRB(i).statsPuntRets += homeKickReturner.pReturns;
                    homeTeam.getRB(i).statsPuntRetYards += homeKickReturner.pYards;
                    homeTeam.getRB(i).statsPuntRetTDs += homeKickReturner.pTD;
                    homeTeam.getRB(i).statsRetGames++;
                }
            }
        } else if (homeKickReturner.position == "WR") {
            for (int i = 0; i < homeTeam.startersWR + homeTeam.subWR; i++) {
                if (homeTeam.getWR(i).name.equals(homeKickReturner.name)) {
                    homeTeam.getWR(i).statsKickRets += homeKickReturner.kReturns;
                    homeTeam.getWR(i).statsKickRetYards += homeKickReturner.kYards;
                    homeTeam.getWR(i).statsKickRetTDs += homeKickReturner.kTD;
                    homeTeam.getWR(i).statsPuntRets += homeKickReturner.pReturns;
                    homeTeam.getWR(i).statsPuntRetYards += homeKickReturner.pYards;
                    homeTeam.getWR(i).statsPuntRetTDs += homeKickReturner.pTD;
                    homeTeam.getWR(i).statsRetGames++;
                }
            }
        } else {
            for (int i = 0; i < homeTeam.startersCB + homeTeam.subCB; i++) {
                if (homeTeam.getCB(i).name.equals(homeKickReturner.name)) {
                    homeTeam.getCB(i).statsKickRets += homeKickReturner.kReturns;
                    homeTeam.getCB(i).statsKickRetYards += homeKickReturner.kYards;
                    homeTeam.getCB(i).statsKickRetTDs += homeKickReturner.kTD;
                    homeTeam.getCB(i).statsPuntRets += homeKickReturner.pReturns;
                    homeTeam.getCB(i).statsPuntRetYards += homeKickReturner.pYards;
                    homeTeam.getCB(i).statsPuntRetTDs += homeKickReturner.pTD;
                    homeTeam.getCB(i).statsRetGames++;
                }
            }
        }

        if (awayKickReturner.position == "RB") {
            for (int i = 0; i < awayTeam.startersRB + awayTeam.subRB; i++) {
                if (awayTeam.getRB(i).name.equals(awayKickReturner.name)) {
                    awayTeam.getRB(i).statsKickRets += awayKickReturner.kReturns;
                    awayTeam.getRB(i).statsKickRetYards += awayKickReturner.kYards;
                    awayTeam.getRB(i).statsKickRetTDs += awayKickReturner.kTD;
                    awayTeam.getRB(i).statsPuntRets += awayKickReturner.pReturns;
                    awayTeam.getRB(i).statsPuntRetYards += awayKickReturner.pYards;
                    awayTeam.getRB(i).statsPuntRetTDs += awayKickReturner.pTD;
                }
            }
        } else if (awayKickReturner.position == "WR") {
            for (int i = 0; i < awayTeam.startersWR + awayTeam.subWR; i++) {
                if (awayTeam.getWR(i).name.equals(awayKickReturner.name)) {
                    awayTeam.getWR(i).statsKickRets += awayKickReturner.kReturns;
                    awayTeam.getWR(i).statsKickRetYards += awayKickReturner.kYards;
                    awayTeam.getWR(i).statsKickRetTDs += awayKickReturner.kTD;
                    awayTeam.getWR(i).statsPuntRets += awayKickReturner.pReturns;
                    awayTeam.getWR(i).statsPuntRetYards += awayKickReturner.pYards;
                    awayTeam.getWR(i).statsPuntRetTDs += awayKickReturner.pTD;
                }
            }
        } else {
            for (int i = 0; i < awayTeam.startersCB + awayTeam.subCB; i++) {
                if (awayTeam.getCB(i).name.equals(awayKickReturner.name)) {
                    awayTeam.getCB(i).statsKickRets += awayKickReturner.kReturns;
                    awayTeam.getCB(i).statsKickRetYards += awayKickReturner.kYards;
                    awayTeam.getCB(i).statsKickRetTDs += awayKickReturner.kTD;
                    awayTeam.getCB(i).statsPuntRets += awayKickReturner.pReturns;
                    awayTeam.getCB(i).statsPuntRetYards += awayKickReturner.pYards;
                    awayTeam.getCB(i).statsPuntRetTDs += awayKickReturner.pTD;
                }
            }
        }

    }

    //CLOCK AND HEALTH MANAGEMENT

    private void addPointsQuarter(int points) {
        if (gamePoss) {
            //home poss
            if (gameTime > 2700) {
                homeQScore[0] += points;
            } else if (gameTime > 1800) {
                homeQScore[1] += points;
            } else if (gameTime > 900) {
                homeQScore[2] += points;
            } else if (numOT == 0) {
                homeQScore[3] += points;
            } else {
                if (3 + numOT < 10) homeQScore[3 + numOT] += points;
                else homeQScore[9] += points;
            }
        } else {
            //away
            if (gameTime > 2700) {
                awayQScore[0] += points;
            } else if (gameTime > 1800) {
                awayQScore[1] += points;
            } else if (gameTime > 900) {
                awayQScore[2] += points;
            } else if (numOT == 0) {
                awayQScore[3] += points;
            } else {
                if (3 + numOT < 10) awayQScore[3 + numOT] += points;
                else awayQScore[9] += points;
            }
        }
    }

    private void resetForOT() {
        if (bottomOT && homeScore == awayScore) {
            //Add some gameFatigue points
            for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
                homeTeam.getAllPlayers().get(i).gameFatigue += 50;
                if (homeTeam.getAllPlayers().get(i).gameFatigue > 100)
                    homeTeam.getAllPlayers().get(i).gameFatigue = 100;
            }
            for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
                awayTeam.getAllPlayers().get(i).gameFatigue += 50;
                if (awayTeam.getAllPlayers().get(i).gameFatigue > 100)
                    awayTeam.getAllPlayers().get(i).gameFatigue = 100;
            }

            gameYardLine = 75;
            gameYardsNeed = 10;
            gameDown = 1;
            numOT++;
            gamePoss = (numOT % 2) == 0;
            gameTime = -1;
            bottomOT = false;
            //runPlay( awayTeam, homeTeam );
        } else if (!bottomOT) {
            //Add some gameFatigue points
            for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
                homeTeam.getAllPlayers().get(i).gameFatigue += 50;
                if (homeTeam.getAllPlayers().get(i).gameFatigue > 100)
                    homeTeam.getAllPlayers().get(i).gameFatigue = 100;
            }
            for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
                awayTeam.getAllPlayers().get(i).gameFatigue += 50;
                if (awayTeam.getAllPlayers().get(i).gameFatigue > 100)
                    awayTeam.getAllPlayers().get(i).gameFatigue = 100;
            }

            gamePoss = !gamePoss;
            gameYardLine = 75;
            gameYardsNeed = 10;
            gameDown = 1;
            gameTime = -1;
            bottomOT = true;
            //runPlay( homeTeam, awayTeam );
        } else {
            // game is not tied after both teams had their chance
            playingOT = false;
        }
    }

    private String convGameTime() {
        if (!playingOT) {
            int qNum = (3600 - gameTime) / 900 + 1;
            int minTime;
            int secTime;
            String secStr;
            if (gameTime <= 0 && numOT <= 0) { // Prevent Q5 1X:XX from displaying in the game log
                return "0:00 Q4";
            } else {
                minTime = (gameTime - 900 * (4 - qNum)) / 60;
                secTime = (gameTime - 900 * (4 - qNum)) - 60 * minTime;
                if (secTime < 10) secStr = "0" + secTime;
                else secStr = "" + secTime;
                return minTime + ":" + secStr + " Q" + qNum;
            }
        } else {
            if (!bottomOT) {
                return "TOP OT" + numOT;
            } else {
                return "BOT OT" + numOT;
            }
        }
    }

    private void quarterCheck() {
        if (gameTime < 2700 && !QT1) {
            QT1 = true;
            //Set Player Fatigue +50
            recoup(true, 1);
            gameTime = 2700;
            gameEventLog += "\n\n-- 2nd QUARTER --";

        } else if (gameTime < 1800 && !QT2) {
            QT2 = true;
            //Set Player Fatigue to 100
            recoup(true, 2);
            gameTime = 1800;
            gameEventLog += "\n\n-- 3rd QUARTER --";
            gamePoss = false;
            kickOff(awayTeam, homeTeam);

        } else if (gameTime < 900 && !QT3) {
            QT3 = true;
            //Set Player Fatigue +50
            recoup(true, 3);
            gameTime = 900;
            gameEventLog += "\n\n-- 4th QUARTER --";
        }

    }

    private void recoup(boolean endQT, int qt) {
        int gain = fatigueGain;
        if (endQT && qt != 2) gain = (int) Math.random() * 35 + 35;
        if (endQT && qt == 2) gain = 100;
        //recoup v2.0
        for (int i = 0; i < homeTeam.startersRB; ++i) {
            homeTeam.getRB(i).gameFatigue += gain;
            if (homeTeam.getRB(i).gameFatigue > 100) homeTeam.getRB(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersWR; ++i) {
            homeTeam.getWR(i).gameFatigue += gain;
            if (homeTeam.getWR(i).gameFatigue > 100) homeTeam.getWR(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersTE; ++i) {
            homeTeam.getTE(i).gameFatigue += gain;
            if (homeTeam.getTE(i).gameFatigue > 100) homeTeam.getTE(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersOL; ++i) {
            homeTeam.getOL(i).gameFatigue += gain;
            if (homeTeam.getOL(i).gameFatigue > 100) homeTeam.getOL(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersDL; ++i) {
            homeTeam.getDL(i).gameFatigue += gain;
            if (homeTeam.getDL(i).gameFatigue > 100) homeTeam.getDL(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersLB; ++i) {
            homeTeam.getLB(i).gameFatigue += gain;
            if (homeTeam.getLB(i).gameFatigue > 100) homeTeam.getLB(i).gameFatigue = 100;
        }
        for (int i = 0; i < homeTeam.startersS; ++i) {
            homeTeam.getS(i).gameFatigue += gain;
            if (homeTeam.getS(i).gameFatigue > 100) homeTeam.getS(i).gameFatigue = 100;
        }

        //recoup v2.0
        for (int i = 0; i < awayTeam.startersRB; ++i) {
            awayTeam.getRB(i).gameFatigue += gain;
            if (awayTeam.getRB(i).gameFatigue > 100) awayTeam.getRB(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersWR; ++i) {
            awayTeam.getWR(i).gameFatigue += gain;
            if (awayTeam.getWR(i).gameFatigue > 100) awayTeam.getWR(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersTE; ++i) {
            awayTeam.getTE(i).gameFatigue += gain;
            if (awayTeam.getTE(i).gameFatigue > 100) awayTeam.getTE(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersOL; ++i) {
            awayTeam.getOL(i).gameFatigue += gain;
            if (awayTeam.getOL(i).gameFatigue > 100) awayTeam.getOL(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersDL; ++i) {
            awayTeam.getDL(i).gameFatigue += gain;
            if (awayTeam.getDL(i).gameFatigue > 100) awayTeam.getDL(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersLB; ++i) {
            awayTeam.getLB(i).gameFatigue += gain;
            if (awayTeam.getLB(i).gameFatigue > 100) awayTeam.getLB(i).gameFatigue = 100;
        }
        for (int i = 0; i < awayTeam.startersS; ++i) {
            awayTeam.getS(i).gameFatigue += gain;
            if (awayTeam.getS(i).gameFatigue > 100) awayTeam.getS(i).gameFatigue = 100;
        }

    }


    //PREVIEW & BOXSCORE

    private void gameStatistics() {
        Player player;
        homePassingStats = new ArrayList<>();
        awayPassingStats = new ArrayList<>();
        homeRushingStats = new ArrayList<>();
        awayRushingStats = new ArrayList<>();
        homeReceivingStats = new ArrayList<>();
        awayReceivingStats = new ArrayList<>();
        homeKickingStats = new ArrayList<>();
        awayKickingStats = new ArrayList<>();
        homeDefenseStats = new ArrayList<>();
        awayDefenseStats = new ArrayList<>();

        for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
            if (homeTeam.getAllPlayers().get(i).gamePassAtempts > 0) {
                player = homeTeam.getAllPlayers().get(i);
                homePassingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gamePassYards + "," + player.gamePassComplete + "," + player.gamePassAtempts + "," + player.gamePassTDs + "," + player.gamePassInts + "," + player.gameSacks);
            }
        }

        for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
            if (awayTeam.getAllPlayers().get(i).gamePassAtempts > 0) {
                player = awayTeam.getAllPlayers().get(i);
                awayPassingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gamePassYards + "," + player.gamePassComplete + "," + player.gamePassAtempts + "," + player.gamePassTDs + "," + player.gamePassInts + "," + player.gameSacks);
            }
        }

        for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
            if (homeTeam.getAllPlayers().get(i).gameRushAttempts > 0) {
                player = homeTeam.getAllPlayers().get(i);
                homeRushingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameRushYards + "," + player.gameRushAttempts + "," + player.gameRushTDs + "," + player.gameFumbles);
            }
        }

        for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
            if (awayTeam.getAllPlayers().get(i).gameRushAttempts > 0) {
                player = awayTeam.getAllPlayers().get(i);
                awayRushingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameRushYards + "," + player.gameRushAttempts + "," + player.gameRushTDs + "," + player.gameFumbles);
            }
        }

        for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
            if (homeTeam.getAllPlayers().get(i).gameReceptions > 0) {
                player = homeTeam.getAllPlayers().get(i);
                homeReceivingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameRecYards + "," + player.gameReceptions + "," + player.gameTargets + "," + player.gameRecTDs + "," + player.gameDrops);
            }
        }

        for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
            if (awayTeam.getAllPlayers().get(i).gameReceptions > 0) {
                player = awayTeam.getAllPlayers().get(i);
                awayReceivingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameRecYards + "," + player.gameReceptions + "," + player.gameTargets + "," + player.gameRecTDs + "," + player.gameDrops);
            }
        }

        player = homeTeam.getK(0);
        homeKickingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameFGMade + "," + player.gameFGAttempts + "," + player.gameXPMade + "," + player.gameXPAttempts);
        player = awayTeam.getK(0);
        awayKickingStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameFGMade + "," + player.gameFGAttempts + "," + player.gameXPMade + "," + player.gameXPAttempts);

        for (int i = 0; i < homeTeam.getAllPlayers().size(); ++i) {
            if (homeTeam.getAllPlayers().get(i).gameTackles > 0) {
                player = homeTeam.getAllPlayers().get(i);
                homeDefenseStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameTackles + "," + player.gameSacks + "," + player.gameFumbles + "," + player.gameInterceptions + "," + player.gameTargets + "," + player.gameDefended);
            }

        }

        for (int i = 0; i < awayTeam.getAllPlayers().size(); ++i) {
            if (awayTeam.getAllPlayers().get(i).gameTackles > 0) {
                player = awayTeam.getAllPlayers().get(i);
                awayDefenseStats.add(player.getInitialName() + "," + player.team.name + "," + player.position + "," + player.gameTackles + "," + player.gameSacks + "," + player.gameFumbles + "," + player.gameInterceptions + "," + player.gameTargets + "," + player.gameDefended);
            }
        }

        if (homeKickReturner.kReturns > 0)
            hkReturnAvg = (double) (homeKickReturner.kYards / homeKickReturner.kReturns);
        if (awayKickReturner.kReturns > 0)
            akReturnAvg = (double) (awayKickReturner.kYards / awayKickReturner.kReturns);

        if (homeKickReturner.pReturns > 0)
            hpReturnAvg = (double) (homeKickReturner.pYards / homeKickReturner.pReturns);
        if (awayKickReturner.pReturns > 0)
            apReturnAvg = (double) (awayKickReturner.pYards / awayKickReturner.pReturns);

        recordReturnStats();

    }


    public String[] getGameSummaryStrV2() {
        DecimalFormat df2 = new DecimalFormat(".##");

        String[] gameSum = new String[19];
        StringBuilder gameL = new StringBuilder();
        StringBuilder gameC = new StringBuilder();
        StringBuilder gameR = new StringBuilder();

        gameL.append("\nPoints\nYards\nPass Yards\nRush Yards\nTOs\n");
        gameC.append("#" + awayTeam.rankTeamPollScore + " " + awayTeam.abbr + "\n" + awayScore + "\n" + awayYards + " yds\n" +
                awayPassYards + " pyds\n" + awayRushYards + " ryds\n" + awayTOs + " TOs\n");
        gameR.append("#" + homeTeam.rankTeamPollScore + " " + homeTeam.abbr + "\n" + homeScore + "\n" + homeYards + " yds\n" +
                homePassYards + " pyds\n" + homeRushYards + " ryds\n" + homeTOs + " TOs\n");

        StringBuilder gamePL = new StringBuilder();
        StringBuilder gamePC = new StringBuilder();
        StringBuilder gamePR = new StringBuilder();

        gamePL.append("\n");
        gamePC.append("[PASSING]\n");
        gamePR.append("\n");
        gamePL.append("\n");
        gamePC.append("\n");
        gamePR.append("\n");

        if (homePassingStats.size() >= awayPassingStats.size()) {
            for (int i = 0; i < homePassingStats.size(); ++i) {
                gamePL.append("QB:" + "\nYards:" + "\nComp/Att:" + "\nPass TDs:" + "\nPass Ints:" + "\nSacks:" + "\nRating:" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayPassingStats.size(); ++i) {
                gamePL.append("QB:" + "\nYards:" + "\nComp/Att:" + "\nPass TDs:" + "\nPass Ints:" + "\nSacks:" + "\nRating:" + "\n\n");
            }
        }

        for (int i = 0; i < awayPassingStats.size(); ++i) {
            String[] stats = awayPassingStats.get(i).split(",");
            gamePC.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n" + stats[8] + "\n" + getPasserRating(Integer.parseInt(stats[3]), Integer.parseInt(stats[6]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[7])) + "\n\n");
        }
        for (int i = 0; i < homePassingStats.size(); ++i) {
            String[] stats = homePassingStats.get(i).split(",");
            gamePR.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n" + stats[8] + "\n" + getPasserRating(Integer.parseInt(stats[3]), Integer.parseInt(stats[6]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[7])) + "\n\n");
        }


        StringBuilder gameRL = new StringBuilder();
        StringBuilder gameRC = new StringBuilder();
        StringBuilder gameRR = new StringBuilder();

        gameRL.append("\n");
        gameRC.append("[RUSHING]\n");
        gameRR.append("\n");
        gameRL.append("\n");
        gameRC.append("\n");
        gameRR.append("\n");

        if (awayRushingStats.size() >= awayRushingStats.size()) {
            for (int i = 0; i < awayRushingStats.size(); ++i) {
                gameRL.append("Name:" + "\nPosition:" + "\nYards:" + "\nCarries:" + "\nYards/Carry:" + "\nTDs:" + "\nFumbles:" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayRushingStats.size(); ++i) {
                gameRL.append("Name:" + "\nPosition:" + "\nYards:" + "\nCarries:" + "\nYards/Carry:" + "\nTDs:" + "\nFumbles:" + "\n\n");
            }
        }

        for (int i = 0; i < awayRushingStats.size(); ++i) {
            String[] stats = awayRushingStats.get(i).split(",");
            gameRC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format((Double.parseDouble(stats[3]) / Double.parseDouble(stats[4]))) + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }
        for (int i = 0; i < homeRushingStats.size(); ++i) {
            String[] stats = homeRushingStats.get(i).split(",");
            gameRR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format((Double.parseDouble(stats[3]) / Double.parseDouble(stats[4]))) + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }


        StringBuilder gameWL = new StringBuilder();
        StringBuilder gameWC = new StringBuilder();
        StringBuilder gameWR = new StringBuilder();

        gameWL.append("\n");
        gameWC.append("[RECEIVING]\n");
        gameWR.append("\n");
        gameWL.append("\n");
        gameWC.append("\n");
        gameWR.append("\n");

        if (homeReceivingStats.size() >= awayReceivingStats.size()) {
            for (int i = 0; i < homeReceivingStats.size(); ++i) {
                gameWL.append("Name:" + "\nPosition:" + "\nYards:" + "\nReceptions:" + "\nYards/Rec:" + "\nRec/Targets:" + "\nTDs:" + "\nDrops:" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayReceivingStats.size(); ++i) {
                gameWL.append("Name:" + "\nPosition:" + "\nYards:" + "\nReceptions:" + "\nYards/Rec:" + "\nRec/Targets:" + "\nTDs:" + "\nDrops:" + "\n\n");
            }
        }

        for (int i = 0; i < awayReceivingStats.size(); ++i) {
            String[] stats = awayReceivingStats.get(i).split(",");
            gameWC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format(getRecYardsperCatch(Double.parseDouble(stats[3]), Double.parseDouble(stats[4]))) + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n\n");
        }
        for (int i = 0; i < homeReceivingStats.size(); ++i) {
            String[] stats = homeReceivingStats.get(i).split(",");
            gameWR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format(getRecYardsperCatch(Double.parseDouble(stats[3]), Double.parseDouble(stats[4]))) + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n\n");
        }

        StringBuilder gameDL = new StringBuilder();
        StringBuilder gameDC = new StringBuilder();
        StringBuilder gameDR = new StringBuilder();

        gameDL.append("\n");
        gameDC.append("[DEFENDING]\n");
        gameDR.append("\n");
        gameDL.append("\n");
        gameDC.append("\n");
        gameDR.append("\n");

        if (homeDefenseStats.size() >= awayDefenseStats.size()) {
            for (int i = 0; i < homeDefenseStats.size(); ++i) {
                gameDL.append("Name:" + "\nPosition:" + "\nTackles:" + "\nSacks:" + "\nFumbles:" + "\nInts:" + "\nDefended:" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayDefenseStats.size(); ++i) {
                gameDL.append("Name:" + "\nPosition:" + "\nTackles:" + "\nSacks:" + "\nFumbles:" + "\nInts:" + "\nDefended:" + "\n\n");
            }
        }

        for (int i = 0; i < awayDefenseStats.size(); ++i) {
            String[] stats = awayDefenseStats.get(i).split(",");
            gameDC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n" + stats[8] + "\n\n");
        }
        for (int i = 0; i < homeDefenseStats.size(); ++i) {
            String[] stats = homeDefenseStats.get(i).split(",");
            gameDR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n" + stats[8] + "\n\n");
        }

        StringBuilder gameKL = new StringBuilder();
        StringBuilder gameKC = new StringBuilder();
        StringBuilder gameKR = new StringBuilder();

        gameKL.append("\n");
        gameKC.append("[KICKING]\n");
        gameKR.append("\n");
        gameKL.append("\n");
        gameKC.append("\n");
        gameKR.append("\n");


        if (homeKickingStats.size() >= awayKickingStats.size()) {
            for (int i = 0; i < homeKickingStats.size(); ++i) {
                gameKL.append("Name:" + "\nFG Made:" + "\nFG Att:" + "\nXP Made:" + "\nXP Att:" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayKickingStats.size(); ++i) {
                gameKL.append("Name:" + "\nFG Made:" + "\nFG Att:" + "\nXP Made:" + "\nXP Att:" + "\n\n");
            }
        }

        for (int i = 0; i < awayKickingStats.size(); ++i) {
            String[] stats = awayKickingStats.get(i).split(",");
            gameKC.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }
        for (int i = 0; i < homeKickingStats.size(); ++i) {
            String[] stats = homeKickingStats.get(i).split(",");
            gameKR.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }

        gameKL.append("\n");
        gameKC.append("[RETURNS]\n");
        gameKR.append("\n");
        gameKL.append("\n");
        gameKC.append("\n");
        gameKR.append("\n");

        gameKL.append("Name:" + "\nKick Rets:" + "\nK Ret Yrds:" + "\nK Yrds/Ret:" + "\nK Ret TDs" + "\nPunt Rets:" + "\nP Ret Yrds:" + "\nP Yrds/Ret:" + "\nP Ret TDs" + "\n\n");
        gameKC.append(awayKickReturner.getInitialName() + "\n" + awayKickReturner.kReturns + "\n" + awayKickReturner.kYards + "\n" + akReturnAvg + "\n" + awayKickReturner.kTD + "\n" + awayKickReturner.pReturns + "\n" + awayKickReturner.pYards + "\n" + apReturnAvg + "\n" + awayKickReturner.pTD + "\n\n");
        gameKR.append(homeKickReturner.getInitialName() + "\n" + homeKickReturner.kReturns + "\n" + homeKickReturner.kYards + "\n" + hkReturnAvg + "\n" + homeKickReturner.kTD + "\n" + homeKickReturner.pReturns + "\n" + homeKickReturner.pYards + "\n" + hpReturnAvg + "\n" + homeKickReturner.pTD + "\n\n");


        //Send data

        gameSum[0] = gameL.toString();
        gameSum[1] = gameC.toString();
        gameSum[2] = gameR.toString();

        gameSum[3] = gamePL.toString();
        gameSum[4] = gamePC.toString();
        gameSum[5] = gamePR.toString();

        gameSum[6] = gameRL.toString();
        gameSum[7] = gameRC.toString();
        gameSum[8] = gameRR.toString();

        gameSum[9] = gameWL.toString();
        gameSum[10] = gameWC.toString();
        gameSum[11] = gameWR.toString();

        gameSum[12] = gameDL.toString();
        gameSum[13] = gameDC.toString();
        gameSum[14] = gameDR.toString();

        gameSum[15] = gameKL.toString();
        gameSum[16] = gameKC.toString();
        gameSum[17] = gameKR.toString();

        gameSum[18] = getPlaybyPlay();


        return gameSum;

    }

    public String[] getGameSummaryStr() {
        DecimalFormat df2 = new DecimalFormat(".##");

        String[] gameSum = new String[19];
        StringBuilder gameL = new StringBuilder();
        StringBuilder gameC = new StringBuilder();
        StringBuilder gameR = new StringBuilder();

        gameL.append("\nPoints\nYards\nPass Yards\nRush Yards\nTOs\n\nOffense\nDefense\n");
        gameC.append("#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + "\n" + awayScore + "\n" + awayYards + " yds\n" +
                awayPassYards + " pyds\n" + awayRushYards + " ryds\n" + awayTOs + " TOs\n\n" + awayTeam.playbookOff.getStratName() + "\n" + awayTeam.playbookDef.getStratName() + " \n");
        gameR.append("#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + "\n" + homeScore + "\n" + homeYards + " yds\n" +
                homePassYards + " pyds\n" + homeRushYards + " ryds\n" + homeTOs + " TOs\n\n" + homeTeam.playbookOff.getStratName() + "\n" + homeTeam.playbookDef.getStratName() + " \n");

        StringBuilder gamePL = new StringBuilder();
        StringBuilder gamePC = new StringBuilder();
        StringBuilder gamePR = new StringBuilder();

        gamePL.append("[PASSING]\n");
        gamePC.append("\n");
        gamePR.append("\n");
        gamePL.append("\n");
        gamePC.append("\n");
        gamePR.append("\n");

        if (homePassingStats.size() >= awayPassingStats.size()) {
            for (int i = 0; i < homePassingStats.size(); ++i) {
                gamePL.append("QB" + "\nYards" + "\nComp/Att" + "\nPass TDs" + "\nPass Ints" + "\nSacks" + "\nRating" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayPassingStats.size(); ++i) {
                gamePL.append("QB" + "\nYards" + "\nComp/Att" + "\nPass TDs" + "\nPass Ints" + "\nSacks" + "\nRating" + "\n\n");
            }
        }

        for (int i = 0; i < awayPassingStats.size(); ++i) {
            String[] stats = awayPassingStats.get(i).split(",");
            gamePC.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n" + stats[8] + "\n" + getPasserRating(Integer.parseInt(stats[3]), Integer.parseInt(stats[6]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[7])) + "\n\n");
        }
        for (int i = 0; i < homePassingStats.size(); ++i) {
            String[] stats = homePassingStats.get(i).split(",");
            gamePR.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n" + stats[8] + "\n" + getPasserRating(Integer.parseInt(stats[3]), Integer.parseInt(stats[6]), Integer.parseInt(stats[4]), Integer.parseInt(stats[5]), Integer.parseInt(stats[7])) + "\n\n");
        }


        StringBuilder gameRL = new StringBuilder();
        StringBuilder gameRC = new StringBuilder();
        StringBuilder gameRR = new StringBuilder();

        gameRL.append("[RUSHING]\n");
        gameRC.append("\n");
        gameRR.append("\n");
        gameRL.append("\n");
        gameRC.append("\n");
        gameRR.append("\n");

        if (homeRushingStats.size() >= awayRushingStats.size()) {
            for (int i = 0; i < awayRushingStats.size(); ++i) {
                gameRL.append("Name" + "\nPosition" + "\nYards" + "\nCarries" + "\nYards/Carry" + "\nTDs" + "\nFumbles" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayRushingStats.size(); ++i) {
                gameRL.append("Name" + "\nPosition" + "\nYards" + "\nCarries" + "\nYards/Carry" + "\nTDs" + "\nFumbles" + "\n\n");
            }
        }

        for (int i = 0; i < awayRushingStats.size(); ++i) {
            String[] stats = awayRushingStats.get(i).split(",");
            gameRC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format((Double.parseDouble(stats[3]) / Double.parseDouble(stats[4]))) + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }
        for (int i = 0; i < homeRushingStats.size(); ++i) {
            String[] stats = homeRushingStats.get(i).split(",");
            gameRR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format((Double.parseDouble(stats[3]) / Double.parseDouble(stats[4]))) + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }


        StringBuilder gameWL = new StringBuilder();
        StringBuilder gameWC = new StringBuilder();
        StringBuilder gameWR = new StringBuilder();

        gameWL.append("[RECEIVING]\n");
        gameWC.append("\n");
        gameWR.append("\n");
        gameWL.append("\n");
        gameWC.append("\n");
        gameWR.append("\n");

        if (homeReceivingStats.size() >= awayReceivingStats.size()) {
            for (int i = 0; i < homeReceivingStats.size(); ++i) {
                gameWL.append("Name" + "\nPosition" + "\nYards" + "\nReceptions" + "\nYards/Rec" + "\nRec/Targets" + "\nTDs" + "\nDrops" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayReceivingStats.size(); ++i) {
                gameWL.append("Name" + "\nPosition" + "\nYards" + "\nReceptions" + "\nYards/Rec" + "\nRec/Targets" + "\nTDs" + "\nDrops" + "\n\n");
            }
        }

        for (int i = 0; i < awayReceivingStats.size(); ++i) {
            String[] stats = awayReceivingStats.get(i).split(",");
            gameWC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format(getRecYardsperCatch(Double.parseDouble(stats[3]), Double.parseDouble(stats[4]))) + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n\n");
        }
        for (int i = 0; i < homeReceivingStats.size(); ++i) {
            String[] stats = homeReceivingStats.get(i).split(",");
            gameWR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + df2.format(getRecYardsperCatch(Double.parseDouble(stats[3]), Double.parseDouble(stats[4]))) + "\n" + stats[4] + "/" + stats[5] + "\n" + stats[6] + "\n" + stats[7] + "\n\n");
        }

        StringBuilder gameDL = new StringBuilder();
        StringBuilder gameDC = new StringBuilder();
        StringBuilder gameDR = new StringBuilder();

        gameDL.append("[DEFENDING]\n");
        gameDC.append("\n");
        gameDR.append("\n");
        gameDL.append("\n");
        gameDC.append("\n");
        gameDR.append("\n");

        if (homeDefenseStats.size() >= awayDefenseStats.size()) {
            for (int i = 0; i < homeDefenseStats.size(); ++i) {
                gameDL.append("Name" + "\nPosition" + "\nTackles" + "\nSacks" + "\nFumbles" + "\nInts" + "\nDefended" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayDefenseStats.size(); ++i) {
                gameDL.append("Name" + "\nPosition" + "\nTackles" + "\nSacks" + "\nFumbles" + "\nInts" + "\nDefended" + "\n\n");
            }
        }

        for (int i = 0; i < awayDefenseStats.size(); ++i) {
            String[] stats = awayDefenseStats.get(i).split(",");
            gameDC.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n" + stats[8] + "\n\n");
        }
        for (int i = 0; i < homeDefenseStats.size(); ++i) {
            String[] stats = homeDefenseStats.get(i).split(",");
            gameDR.append(stats[0] + "\n" + stats[2] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n" + stats[8] + "\n\n");
        }

        StringBuilder gameKL = new StringBuilder();
        StringBuilder gameKC = new StringBuilder();
        StringBuilder gameKR = new StringBuilder();

        gameKL.append("[KICKING]\n");
        gameKC.append("\n");
        gameKR.append("\n");
        gameKL.append("\n");
        gameKC.append("\n");
        gameKR.append("\n");


        if (homeKickingStats.size() >= awayKickingStats.size()) {
            for (int i = 0; i < homeKickingStats.size(); ++i) {
                gameKL.append("Name" + "\nFG Made" + "\nFG Att" + "\nXP Made" + "\nXP Att" + "\n\n");
            }
        } else {
            for (int i = 0; i < awayKickingStats.size(); ++i) {
                gameKL.append("Name" + "\nFG Made" + "\nFG Att" + "\nXP Made" + "\nXP Att" + "\n\n");
            }
        }

        for (int i = 0; i < awayKickingStats.size(); ++i) {
            String[] stats = awayKickingStats.get(i).split(",");
            gameKC.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }
        for (int i = 0; i < homeKickingStats.size(); ++i) {
            String[] stats = homeKickingStats.get(i).split(",");
            gameKR.append(stats[0] + "\n" + stats[3] + "\n" + stats[4] + "\n" + stats[5] + "\n" + stats[6] + "\n\n");
        }

        gameKL.append("[RETURNS]\n");
        gameKC.append("\n");
        gameKR.append("\n");
        gameKL.append("\n");
        gameKC.append("\n");
        gameKR.append("\n");

        gameKL.append("Name" + "\nKick Rets" + "\nK Ret Yrds" + "\nK Yrds/Ret" + "\nK Ret TDs" + "\nPunt Rets" + "\nP Ret Yrds" + "\nP Yrds/Ret" + "\nP Ret TDs" + "\n\n");
        gameKC.append(awayKickReturner.getInitialName() + "\n" + awayKickReturner.kReturns + "\n" + awayKickReturner.kYards + "\n" + akReturnAvg + "\n" + awayKickReturner.kTD + "\n" + awayKickReturner.pReturns + "\n" + awayKickReturner.pYards + "\n" + apReturnAvg + "\n" + awayKickReturner.pTD + "\n\n");
        gameKR.append(homeKickReturner.getInitialName() + "\n" + homeKickReturner.kReturns + "\n" + homeKickReturner.kYards + "\n" + hkReturnAvg + "\n" + homeKickReturner.kTD + "\n" + homeKickReturner.pReturns + "\n" + homeKickReturner.pYards + "\n" + hpReturnAvg + "\n" + homeKickReturner.pTD + "\n\n");


        //Send data

        gameSum[0] = gameL.toString();
        gameSum[1] = gameC.toString();
        gameSum[2] = gameR.toString();

        gameSum[3] = gamePL.toString();
        gameSum[4] = gamePC.toString();
        gameSum[5] = gamePR.toString();

        gameSum[6] = gameRL.toString();
        gameSum[7] = gameRC.toString();
        gameSum[8] = gameRR.toString();

        gameSum[9] = gameWL.toString();
        gameSum[10] = gameWC.toString();
        gameSum[11] = gameWR.toString();

        gameSum[12] = gameDL.toString();
        gameSum[13] = gameDC.toString();
        gameSum[14] = gameDR.toString();

        gameSum[15] = gameKL.toString();
        gameSum[16] = gameKC.toString();
        gameSum[17] = gameKR.toString();

        gameSum[18] = getPlaybyPlay();


        return gameSum;

    }


    private String getPlaybyPlay() {
        return "GAME PLAY-BY-PLAY LOG\n" + gameEventLog;
    }

    public String[] getGameScoutStr() {
        /*
          [0] is left side
          [1] is center
          [2] is right
          [3] is bottom (will be empty for scouting)
         */
        String[] gameSum = new String[4];
        StringBuilder gameL = new StringBuilder();
        StringBuilder gameC = new StringBuilder();
        StringBuilder gameR = new StringBuilder();

        int homeRating = (int) (homeTeam.HC.get(0).ratDef + homeTeam.HC.get(0).ratOff + 3*homeTeam.teamOffTalent + 3*homeTeam.teamDefTalent + 3)/8;
        int awayRating = (int) (awayTeam.HC.get(0).ratDef + awayTeam.HC.get(0).ratOff + 3*awayTeam.teamOffTalent + 3*awayTeam.teamDefTalent)/8;

        gameL.append("Ranking\nRecord\nPPG\nOpp PPG\nYPG\nOpp YPG\n" +
                "\nPass YPG\nRush YPG\nOpp PYPG\nOpp RYPG\n\nOff Talent\nDef Talent\nPrestige\n\nHC\nHC Ovr\nHC Off\nOffense\nHC Def\nDefense\n\nFavorite");
        int g = awayTeam.numGames();
        Team t = awayTeam;
        gameC.append("#" + t.rankTeamPollScore + " " + t.abbr + "\n" + t.wins + "-" + t.losses + "\n" +
                t.teamPoints / g + " (" + t.rankTeamPoints + ")\n" + t.teamOppPoints / g + " (" + t.rankTeamOppPoints + ")\n" +
                t.teamYards / g + " (" + t.rankTeamYards + ")\n" + t.teamOppYards / g + " (" + t.rankTeamOppYards + ")\n\n" +
                t.teamPassYards / g + " (" + t.rankTeamPassYards + ")\n" + t.teamRushYards / g + " (" + t.rankTeamRushYards + ")\n" +
                t.teamOppPassYards / g + " (" + t.rankTeamOppPassYards + ")\n" + t.teamOppRushYards / g + " (" + t.rankTeamOppRushYards + ")\n\n" +
                df2.format(t.teamOffTalent) + " (" + t.rankTeamOffTalent + ")\n" + df2.format(t.teamDefTalent) + " (" + t.rankTeamDefTalent + ")\n" +
                t.teamPrestige + " (" + t.rankTeamPrestige + ")\n\n"
                + t.HC.get(0).getInitialName() + "\n" +
                t.HC.get(0).ratOvr + "\n" + t.HC.get(0).ratOff + "\n" + t.playbookOff.getStratName() + "\n" + t.HC.get(0).ratDef + "\n" + t.playbookDef.getStratName() +
                "\n\n" + getFavorite(homeRating, awayRating, false));
        g = homeTeam.numGames();
        t = homeTeam;
        gameR.append("#" + t.rankTeamPollScore + " " + t.abbr + "\n" + t.wins + "-" + t.losses + "\n" +
                t.teamPoints / g + " (" + t.rankTeamPoints + ")\n" + t.teamOppPoints / g + " (" + t.rankTeamOppPoints + ")\n" +
                t.teamYards / g + " (" + t.rankTeamYards + ")\n" + t.teamOppYards / g + " (" + t.rankTeamOppYards + ")\n\n" +
                t.teamPassYards / g + " (" + t.rankTeamPassYards + ")\n" + t.teamRushYards / g + " (" + t.rankTeamRushYards + ")\n" +
                t.teamOppPassYards / g + " (" + t.rankTeamOppPassYards + ")\n" + t.teamOppRushYards / g + " (" + t.rankTeamOppRushYards + ")\n\n" +
                df2.format(t.teamOffTalent) + " (" + t.rankTeamOffTalent + ")\n" + df2.format(t.teamDefTalent) + " (" + t.rankTeamDefTalent + ")\n" +
                t.teamPrestige + " (" + t.rankTeamPrestige + ")\n\n" +
                t.HC.get(0).getInitialName() + "\n" +
                t.HC.get(0).ratOvr + "\n" + t.HC.get(0).ratOff + "\n" + t.playbookOff.getStratName() + "\n" + t.HC.get(0).ratDef + "\n" + t.playbookDef.getStratName() +
                "\n\n" + getFavorite(homeRating, awayRating, true));

        gameSum[0] = gameL.toString();
        gameSum[1] = gameC.toString();
        gameSum[2] = gameR.toString();

        StringBuilder gameScout = new StringBuilder();
        if (awayTeam.playersInjured != null && !awayTeam.playersInjured.isEmpty()) {
            Collections.sort(awayTeam.playersInjured, new CompPlayerPosition());
            gameScout.append("\n" + awayTeam.abbr + " Injury Report:\n");
            for (Player p : awayTeam.playersInjured) {
                gameScout.append(p.getPosNameYrOvrPot_OneLine() + "\n");
            }
        }
        if (homeTeam.playersInjured != null && !homeTeam.playersInjured.isEmpty()) {
            Collections.sort(homeTeam.playersInjured, new CompPlayerPosition());
            gameScout.append("\n" + homeTeam.abbr + " Injury Report:\n");
            for (Player p : homeTeam.playersInjured) {
                gameScout.append(p.getPosNameYrOvrPot_OneLine() + "\n");
            }
        }

        gameSum[3] = gameScout.toString();

        return gameSum;
    }

    private String getFavorite(int home, int away, boolean hometeam) {
        String fav = "";

        if (hometeam) {
            if (home > away) {
                fav = "by " + 2*(home-away);
            }
        } else {
            if (away > home) {
                fav = "by " + 2*(away-home);
            }
        }

        return fav;
    }

    private String getEventLogScoring() {
        String possStr;
        if (gamePoss) possStr = homeTeam.abbr;
        else possStr = awayTeam.abbr;
        String yardsNeedAdj = "" + gameYardsNeed;
        if (gameYardLine + gameYardsNeed >= 100) yardsNeedAdj = "Goal";
        int gameDownAdj;
        if (gameDown > 4) {
            gameDownAdj = 4;
        } else {
            gameDownAdj = gameDown;
        }
        return "\n\n[ " + homeTeam.abbr + " " + homeScore + " - " + awayScore + " " + awayTeam.abbr + " ]\n\t" + convGameTime() + " ";
    }

    private String getEventLog() {
        String possStr;
        if (gamePoss) possStr = homeTeam.abbr;
        else possStr = awayTeam.abbr;
        String yardsNeedAdj = "" + gameYardsNeed;
        if (gameYardLine + gameYardsNeed >= 100) yardsNeedAdj = "Goal";
        int gameDownAdj;
        if (gameDown > 4) {
            gameDownAdj = 4;
        } else {
            gameDownAdj = gameDown;
        }
        return "\n\n" + convGameTime() + " " + possStr + " " + gameDownAdj + " and " + yardsNeedAdj + " at " + gameYardLinePlay + " yard line." + "\n";
    }

    private String getEventLogScore() {
        String possStr;
        return "\n\n" + homeTeam.abbr + " " + homeScore + " - " + awayScore + " " + awayTeam.abbr;
    }

    private int getPassYards(boolean ha) {
        //ha = home/away, false for home, true for away
        int yards = 0;
        if (!ha) {
            for (int i = 0; i < homeTeam.teamQBs.size(); ++i) {
                yards += homeTeam.getQB(i).gamePassYards;
            }
            return yards;
        } else {
            for (int i = 0; i < awayTeam.teamQBs.size(); ++i) {
                yards += awayTeam.getQB(i).gamePassYards;
            }
            return yards;
        }
    }

    private int getRushYards(boolean ha) {
        //ha = home/away, false for home, true for away
        int yards = 0;
        if (!ha) {
            for (int i = 0; i < homeTeam.teamRBs.size(); ++i) {
                yards += homeTeam.getRB(i).gameRushYards;
            }
            for (int i = 0; i < homeTeam.teamQBs.size(); ++i) {
                yards += homeTeam.getQB(i).gameRushYards;
            }
            return yards;
        } else {
            for (int i = 0; i < awayTeam.teamRBs.size(); ++i) {
                yards += awayTeam.getRB(i).gameRushYards;
            }
            for (int i = 0; i < awayTeam.teamQBs.size(); ++i) {
                yards += awayTeam.getQB(i).gameRushYards;
            }
            return yards;
        }
    }

    private int getPasserRating(int yards, int td, int comp, int att, int ints) {
        int rating;
        if (att < 1) {
            return 0;
        } else {
            rating = (int) (((8.4 * yards + (300 * td) + (100 * comp) - (200 * ints)) / att));
            return rating;
        }
    }

    private double getRecYardsperCatch(double yards, double rec) {
        double rating;
        if (rec < 1) {
            return 0;
        } else {
            rating = yards / rec;
            return rating;
        }
    }

    private void addNewsStory() {

        //Weekly Scoreboard Update
        if (gameName.equals("Conference"))
            homeTeam.league.weeklyScores.get(homeTeam.league.currentWeek + 1).add(homeTeam.conference + " " + gameName + ">#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " " + awayScore + "\n" + "#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " " + homeScore);
        else
            homeTeam.league.weeklyScores.get(homeTeam.league.currentWeek + 1).add(gameName + ">#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " " + awayScore + "\n" + "#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " " + homeScore);


        if (numOT >= 3) {
            // Thriller in OT
            Team winner, loser;
            int winScore, loseScore;
            if (awayScore > homeScore) {
                winner = awayTeam;
                loser = homeTeam;
                winScore = awayScore;
                loseScore = homeScore;
            } else {
                winner = homeTeam;
                loser = awayTeam;
                winScore = homeScore;
                loseScore = awayScore;
            }

            homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add(
                    numOT + "OT Thriller!>" + winner.getStrAbbrWL() + " and " + loser.getStrAbbrWL() + " played an absolutely thrilling game " +
                            "that went to " + numOT + " overtimes, with " + winner.name + " finally emerging victorious " + winScore + " to " + loseScore + ".");
        } else if (homeScore > awayScore && awayTeam.losses == 1 && awayTeam.league.currentWeek > 5 && awayTeam.rankTeamPollScore < awayTeam.league.countTeam) {
            // 5-0 or better team given first loss
            awayTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add(
                    "Undefeated no more! " + awayTeam.name + " suffers first loss!" +
                            ">" + homeTeam.getStrAbbrWL() + " hands " + awayTeam.getStrAbbrWL() +
                            " their first loss of the season, winning " + homeScore + " to " + awayScore + ".");
        } else if (awayScore > homeScore && homeTeam.losses == 1 && homeTeam.league.currentWeek > 5 && homeTeam.rankTeamPollScore < homeTeam.league.countTeam) {
            // 5-0 or better team given first loss
            homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add(
                    "Undefeated no more! " + homeTeam.name + " suffers first loss!" +
                            ">" + awayTeam.getStrAbbrWL() + " hands " + homeTeam.getStrAbbrWL() +
                            " their first loss of the season, winning " + awayScore + " to " + homeScore + ".");
        } else if (awayScore > homeScore && homeTeam.rankTeamPollScore < 20 &&
                (awayTeam.rankTeamPollScore - homeTeam.rankTeamPollScore) > 20 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
            // Upset!
            awayTeam.league.newsStories.get(awayTeam.league.currentWeek + 1).add(
                    "Upset! " + awayTeam.getStrAbbrWL() + " beats " + homeTeam.getStrAbbrWL() +
                            ">#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " was able to pull off the upset on the road against #" +
                            homeTeam.rankTeamPollScore + " " + homeTeam.name + ", winning " + awayScore + " to " + homeScore + ".");
        } else if (homeScore > awayScore && awayTeam.rankTeamPollScore < 20 &&
                (homeTeam.rankTeamPollScore - awayTeam.rankTeamPollScore) > 20 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
            // Upset!
            homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add(
                    "Upset! " + homeTeam.getStrAbbrWL() + " beats " + awayTeam.getStrAbbrWL() +
                            ">#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " was able to pull off the upset at home against #" +
                            awayTeam.rankTeamPollScore + " " + awayTeam.name + ", winning " + homeScore + " to " + awayScore + ".");
        } else if (awayScore > homeScore && homeTeam.rankTeamPollScore < 40 && awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
            // Upset!
            awayTeam.league.newsStories.get(awayTeam.league.currentWeek + 1).add(
                    "Upset! " + awayTeam.getStrAbbrWL() + " beats " + homeTeam.getStrAbbrWL() +
                            ">#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " was able to pull off the upset on the road against #" +
                            homeTeam.rankTeamPollScore + " " + homeTeam.name + ", winning " + awayScore + " to " + homeScore + ".");
        } else if (homeScore > awayScore && awayTeam.rankTeamPollScore < 20 && awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
            // Upset!
            homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add(
                    "Upset! " + homeTeam.getStrAbbrWL() + " beats " + awayTeam.getStrAbbrWL() +
                            ">#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " was able to pull off the upset at home against #" +
                            awayTeam.rankTeamPollScore + " " + awayTeam.name + ", winning " + homeScore + " to " + awayScore + ".");
        }


        if (homeTeam.league.currentWeek < 12) {
            if (awayTeam.rankTeamPollScore < 11 && homeTeam.rankTeamPollScore < 11) {
                if (awayScore > homeScore) {
                    homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " defeats #" +
                            homeTeam.rankTeamPollScore + " " + homeTeam.name + ">" + awayTeam.getStrAbbrWL() + " went on the road and beat " + homeTeam.getStrAbbrWL() + " today, " + awayScore + " - " + homeScore + ", in the Game of the Week.");
                } else {
                    homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " defeats #" +
                            awayTeam.rankTeamPollScore + " " + awayTeam.name + ">" + homeTeam.getStrAbbrWL() + " defeated " + awayTeam.getStrAbbrWL() + " at home today, " + homeScore + " - " + awayScore + ", in an important game of the season between two Top 10 schools.");
                }
            } else if (awayTeam.rankTeamPollScore < 26 && homeTeam.rankTeamPollScore < 26) {
                if (awayScore > homeScore) {
                    homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("#" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " defeats #" +
                            homeTeam.rankTeamPollScore + " " + homeTeam.name + ">" + awayTeam.getStrAbbrWL() + " defeated " + homeTeam.getStrAbbrWL() + " today, " + awayScore + " - " + homeScore + ", in a battle of two Top 25 ranked teams.");
                } else {
                    homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("#" + homeTeam.rankTeamPollScore + " " + homeTeam.name + " defeats #" +
                            awayTeam.rankTeamPollScore + " " + awayTeam.name + ">" + homeTeam.getStrAbbrWL() + " defeated " + awayTeam.getStrAbbrWL() + " at home today, " + homeScore + " - " + awayScore + ", in one of the big match-ups of the week.");
                }
            }
        }
    }

    public void addUpcomingGames(Team name) {
        if (name == awayTeam) {
            if (awayTeam.rankTeamPollScore < 11 && homeTeam.rankTeamPollScore < 11 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
                homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("Upcoming Game: #" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " vs #" +
                        homeTeam.rankTeamPollScore + " " + homeTeam.name + ">The premier game of the week has " + awayTeam.getStrAbbrWL() + " visiting " + homeTeam.getStrAbbrWL() + ", as these two Top Ten teams fight for a crucial playoff spot. " +
                        awayTeam.name + " plays a " + awayTeam.playbookOff.getStratName() + " offense, which is averaging " + (awayTeam.teamYards / awayTeam.numGames()) + " yards per game. " + homeTeam.name + " plays a " +
                        homeTeam.playbookOff.getStratName() + " offense, averaging " + (homeTeam.teamYards / homeTeam.numGames()) + " yards per game.");
            } else if (awayTeam.rankTeamPollScore < 26 && homeTeam.rankTeamPollScore < 26 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
                homeTeam.league.newsStories.get(homeTeam.league.currentWeek + 1).add("Upcoming Game: #" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " vs #" +
                        homeTeam.rankTeamPollScore + " " + homeTeam.name + ">Next week, " + awayTeam.getStrAbbrWL() + " visits " + homeTeam.getStrAbbrWL() + " in a battle of two ranked schools. " +
                        awayTeam.name + " plays a " + awayTeam.playbookOff.getStratName() + " offense, which is averaging " + (awayTeam.teamYards / awayTeam.numGames()) + " yards per game. " + homeTeam.name + " plays a " +
                        homeTeam.playbookOff.getStratName() + " offense, averaging " + (homeTeam.teamYards / homeTeam.numGames()) + " yards per game.");
            }
            if (awayTeam.league.currentWeek + 2 < awayTeam.league.regSeasonWeeks + 5)
                awayTeam.league.weeklyScores.get(homeTeam.league.currentWeek + 2).add(gameName + ">" + awayTeam.strRankTeamRecord() + "\n" + homeTeam.strRankTeamRecord());;
        }
    }

    public void addNewSeasonGames(Team name) {
        if (name == awayTeam) {
            if (awayTeam.rankTeamPollScore < 11 && homeTeam.rankTeamPollScore < 11 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
                homeTeam.league.newsStories.get(0).add("Kick-Off: #" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " vs #" +
                        homeTeam.rankTeamPollScore + " " + homeTeam.name + ">The season kicks off with an exciting game between vistors " + awayTeam.name + " and home team, " + homeTeam.name + ". " +
                        "These two teams are in the pre-season Top Ten, and both are expected to have big seasons this year. " + awayTeam.name + " plays a " + awayTeam.playbookOff.getStratName() + " offense, while " + homeTeam.name + " plays a " +
                        homeTeam.playbookOff.getStratName() + " offense.");

            } else if (awayTeam.rankTeamPollScore < 26 && homeTeam.rankTeamPollScore < 26 && !awayTeam.name.contains("FCS") && !homeTeam.name.contains("FCS")) {
                homeTeam.league.newsStories.get(0).add("Kick-Off: #" + awayTeam.rankTeamPollScore + " " + awayTeam.name + " vs #" +
                        homeTeam.rankTeamPollScore + " " + homeTeam.name + ">The " + homeTeam.league.getYear() + " season starts off with " + awayTeam.name + " visiting " + homeTeam.name
                        + " in an interesting early season game pitting two ranked teams against each other. " + awayTeam.name + " plays a " + awayTeam.playbookOff.getStratName() + " offense, while " + homeTeam.name + " plays a " +
                        homeTeam.playbookOff.getStratName() + " offense.");
            }
        }
        awayTeam.league.weeklyScores.get(1).add(gameName + ">" + awayTeam.strRankTeamRecord() + "\n" + homeTeam.strRankTeamRecord());

    }

    private int normalize(int rating) {
        return rating;
    }


    public Game(Team t, String saveData) {
        String[] save = saveData.split("$$");
        hasPlayed = Boolean.parseBoolean(save[0]);
        homeTeam = t.league.findTeam(save[1]);
        awayTeam = t.league.findTeam(save[2]);
        gameName = save[3];
        gameEventLog = save[4];

        String[] x = save[5].split(",");
        homeScore = Integer.parseInt(x[0]);
        awayScore = Integer.parseInt(x[1]);
        homeYards = Integer.parseInt(x[2]);
        awayYards = Integer.parseInt(x[3]);
        homePassYards = Integer.parseInt(x[4]);
        awayPassYards = Integer.parseInt(x[5]);
        homeRushYards = Integer.parseInt(x[6]);
        awayRushYards = Integer.parseInt(x[7]);
        homeTOs = Integer.parseInt(x[8]);
        awayTOs = Integer.parseInt(x[9]);
        numOT = Integer.parseInt(x[10]);

        homeQScore = new int[10];
        x = save[6].split(",");
        for (int i = 0; i < homeQScore.length; i++) {
            homeQScore[i] = Integer.parseInt(x[i]);
        }
        awayQScore = new int[10];
        x = save[7].split(",");
        for (int i = 0; i < awayQScore.length; i++) {
            awayQScore[i] = Integer.parseInt(x[i]);
        }

        ArrayList<String> homePassingStats = new ArrayList<>();
        x = save[8].split("%");
        for (int i = 0; i < x.length; i++) {
            homePassingStats.add(x[i]);
        }

        ArrayList<String> awayPassingStats = new ArrayList();
        x = save[9].split("%");
        for (int i = 0; i < x.length; i++) {
            awayPassingStats.add(x[i]);
        }

        ArrayList<String> homeRushingStats = new ArrayList();
        x = save[10].split("%");
        for (int i = 0; i < x.length; i++) {
            homeRushingStats.add(x[i]);
        }

        ArrayList<String> awayRushingStats = new ArrayList();
        x = save[11].split("%");
        for (int i = 0; i < x.length; i++) {
            awayRushingStats.add(x[i]);
        }

        ArrayList<String> homeReceivingStats = new ArrayList();
        x = save[12].split("%");
        for (int i = 0; i < x.length; i++) {
            homeReceivingStats.add(x[i]);
        }

        ArrayList<String> awayReceivingStats = new ArrayList();
        x = save[13].split("%");
        for (int i = 0; i < x.length; i++) {
            awayReceivingStats.add(x[i]);
        }

        ArrayList<String> homeDefenseStats = new ArrayList();
        x = save[14].split("%");
        for (int i = 0; i < x.length; i++) {
            homeDefenseStats.add(x[i]);
        }

        ArrayList<String> awayDefenseStats = new ArrayList();
        x = save[15].split("%");
        for (int i = 0; i < x.length; i++) {
            awayDefenseStats.add(x[i]);
        }

        ArrayList<String> homeKickingStats = new ArrayList();
        x = save[16].split("%");
        for (int i = 0; i < x.length; i++) {
            homeKickingStats.add(x[i]);
        }


        ArrayList<String> awayKickingStats = new ArrayList();
        x = save[17].split("%");
        for (int i = 0; i < x.length; i++) {
            awayKickingStats.add(x[i]);
        }

        if (gameName == null) {
            gameName = "Game";
        }
    }
    

    public ArrayList<String> saveGameData() {
        ArrayList<String> gameData = new ArrayList<>();
        gameData.add(Boolean.toString(hasPlayed));
        gameData.add(homeTeam.name); //0
        gameData.add(awayTeam.name); //1
        gameData.add(gameName); //2
        gameData.add(gameEventLog); //3
        gameData.add(homeScore + "," + awayScore + "," + homeYards + "," + awayYards + "," + homePassYards + "," + awayPassYards+ "," + homeRushYards + "," + awayRushYards+ "," + homeTOs + "," + awayTOs + "," + numOT);
        
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x < homeQScore.length; x++) {
            sb.append(homeQScore[x]+ ",");
        }
        gameData.add(sb.toString());
        
        sb = new StringBuilder();
        for(int x = 0; x < awayQScore.length; x++) {
            sb.append(awayQScore[x]+ ",");
        }
        gameData.add(sb.toString());


        for(String x : homePassingStats) {
            gameData.add(x + "%");
        }

        for(String x : awayPassingStats) {
            gameData.add(x + "%");
        }

        for(String x : homeRushingStats) {
            gameData.add(x + "%");
        }

        for(String x : awayRushingStats) {
            gameData.add(x + "%");
        }

        for(String x : homeReceivingStats) {
            gameData.add(x + "%");
        }

        for(String x : awayReceivingStats) {
            gameData.add(x + "%");
        }

        for(String x : homeKickingStats) {
            gameData.add(x + "%");
        }

        for(String x : awayKickingStats) {
            gameData.add(x + "%");
        }

        for(String x : homeDefenseStats) {
            gameData.add(x + "%");
        }

        for(String x : awayDefenseStats) {
            gameData.add(x + "%");
        }

       return gameData;
    }

    
}
