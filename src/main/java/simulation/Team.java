package simulation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import antdroid.cfbcoach.MainActivity;
import comparator.CompPlayer;
import comparator.CompRecruit;
import comparator.CompTeamConfWins;
import comparator.CompTeamPrestige;
import positions.HeadCoach;
import positions.Player;
import positions.PlayerCB;
import positions.PlayerDL;
import positions.PlayerK;
import positions.PlayerLB;
import positions.PlayerOL;
import positions.PlayerQB;
import positions.PlayerRB;
import positions.PlayerS;
import positions.PlayerTE;
import positions.PlayerWR;


public class Team {
    private final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private final DecimalFormat df2 = new DecimalFormat("#.##", symbols);

    public final League league;
    public String name;
    public String abbr;
    public String conference;
    public String division;
    public int location;
    public ArrayList<String> teamHistory;
    public ArrayList<String> hallOfFame;
    public TeamRecords teamRecords;
    public boolean userControlled;
    public boolean showPopups;
    public boolean penalized;
    public boolean bowlBan;
    public boolean recentPenalty;
    public boolean facilityUpgrade;
    public boolean disciplineAction;


    public PlaybookOffense playbookOff;
    public PlaybookDefense playbookDef;
    public int playbookOffNum;
    public int playbookDefNum;

    //Future Implementation?
    public int teamBudget;
    public int teamRecruitBudget;
    public int teamDiscplineBudget;
    public int teamDisciplineScore;
    public int teamFacilities;
    public int teamStadium;

    //Game Log variables
    public ArrayList<Game> gameSchedule;
    public ArrayList<Team> oocTeams;
    public ArrayList<Integer> oocWeeks;
    public ArrayList<String> gameWLSchedule;
    public ArrayList<Team> gameWinsAgainst;
    public ArrayList<Team> gameLossesAgainst;
    public String confChampion;
    public String sweet16;
    public String qtFinalWL;
    public String semiFinalWL;
    public String natChampWL;

    //Team stats
    public int wins;
    public int losses;
    public int totalWins;
    public int totalLosses;
    public int totalCCs;
    public int totalNCs;
    public int totalCCLosses;
    public int totalNCLosses;
    public int totalBowls;
    public int totalBowlLosses;

    public TeamStreak winStreak;

    //Season Stats
    public int teamPoints;
    public int teamOppPoints;
    public int teamYards;
    public int teamOppYards;
    public int teamPassYards;
    public int teamRushYards;
    public int teamOppPassYards;
    public int teamOppRushYards;
    public int teamTODiff;
    public int teamPrestige;
    public int teamPrestigeStart;
    public int teamDiscipline;
    public double teamChemistry;

    //Calculated Stats
    public float teamOffTalent;
    public float teamDefTalent;
    private int prestigePts[];
    public float teamPollScore;
    public int teamStrengthOfWins;
    private int teamStrengthOfLosses;
    public int teamSOS;

    //Calculated Ranks
    public int rankTeamPoints;
    public int rankTeamOppPoints;
    public int rankTeamYards;
    public int rankTeamOppYards;
    public int rankTeamPassYards;
    public int rankTeamRushYards;
    public int rankTeamOppPassYards;
    public int rankTeamOppRushYards;
    public int rankTeamTODiff;
    public int rankTeamOffTalent;
    public int rankTeamDefTalent;
    public int rankTeamPrestige;
    private int rankTeamPrestigeStart;
    public int rankTeamRecruitClass;
    public int rankTeamPollScore;
    public int rankTeamStrengthOfWins;
    public int rankTeamSOS;
    public int rankTeamDisciplineScore;
    public int rankTeamBudget;
    public int rankTeamFacilities;
    public int rankTeamChemistry;

    //prestige/talent improvements
    public int confPrestige;
    public int disciplinePts;
    public int projectedWins;
    public int projectedPollRank;
    public float projectedPollScore;
    public float teamStartOffTal;
    public float teamStartDefTal;

    //Head Coach
    public ArrayList<HeadCoach> HC;
    public boolean fired;
    private boolean newContract;
    private boolean retired;
    public String contractString;

    //players on team
    //offense
    public ArrayList<PlayerQB> teamQBs;
    public ArrayList<PlayerRB> teamRBs;
    public ArrayList<PlayerWR> teamWRs;
    public ArrayList<PlayerTE> teamTEs;
    public ArrayList<PlayerK> teamKs;
    public ArrayList<PlayerOL> teamOLs;
    //defense
    public ArrayList<PlayerDL> teamDLs;
    public ArrayList<PlayerLB> teamLBs;
    public ArrayList<PlayerCB> teamCBs;
    public ArrayList<PlayerS> teamSs;

    public ArrayList<Player> playersLeaving;
    private ArrayList<Player> playersTransferring;

    private ArrayList<Player> playersRecovered;
    public ArrayList<Player> playersInjured;
    private ArrayList<Player> playersDis;

    public String suspensionNews;
    public boolean suspension;

    public int HoFCount = 0;
    //Defined Variables

    public final int startersQB = 1;
    public final int startersRB = 2;
    public final int startersWR = 3;
    public final int startersTE = 1;
    public final int startersOL = 5;
    public final int startersK = 1;
    public final int startersDL = 4;
    public final int startersLB = 3;
    public final int startersCB = 3;
    public final int startersS = 2;

    public final int subQB = 0;
    public final int subRB = 1;
    public final int subWR = 2;
    public final int subTE = 1;
    public final int subOL = 2;
    public final int subK = 0;
    public final int subDL = 2;
    public final int subLB = 2;
    public final int subCB = 1;
    public final int subS = 1;

    public final int minQBs = 3;
    public final int minRBs = 5;
    public final int minWRs = 8;
    public final int minTEs = 3;
    public final int minOLs = 11;
    public final int minKs = 2;
    public final int minDLs = 9;
    public final int minLBs = 7;
    public final int minCBs = 7;
    public final int minSs = 5;

    public int maxPlayers = 70;
    private final int recruitExtras = 15;
    private final int minPlayers = 65;
    private final int minRecruitStar = 4;
    private final int maxStarRating = 10;
    private final int numRecruits = 40;
    private boolean walkon;


    private final int ratTransfer = 70;
    private final int promotionNum = 0;

    public final int five = 84;
    public final int four = 78;
    public final int three = 68;
    public final int two = 58;

    private final int disciplineStart = 60;

    private int dismissalChance = 3;
    private final int gradTransferMinGames = 6;
    private final int dismissalRat = 63;
    private final int gradTransferRat = 77;


    private static final int NFL_OVR = 93;
    private static final int sophNFL = 2;
    private static final double NFL_CHANCE = 0.66;
    private static final double NFL_CHANCE_SOPH = 0.330;

    public static double knockdownRet = 0.925;
    private static double knockdownFired = 0.975;

    String[] issue = {"Skipping Practice", "Skipping Class", "Excessive Partying", "Academics", "Fighting", "Drugs", "DUI", "PED"};

    /**
     * Creates new team, recruiting needed players and setting team stats to 0.
     *
     * @param name       name of the team
     * @param abbr       abbreviation of the team, 3 letters
     * @param conference conference the team is in
     * @param league     reference to the league object all must obey
     * @param prestige   prestige of that team, between 0-100
     */
    public Team(String name, String abbr, String conference, int prestige, String div, int loc, League league) {
        this.name = name;
        this.abbr = abbr;
        this.conference = conference;
        this.league = league;
        location = loc;
        teamPrestige = prestige;
        division = div;
        commonInitializer();

        newRoster(minQBs, minRBs, minWRs, minTEs, minOLs, minKs, minDLs, minLBs, minCBs, minSs, true);

        //set stats
        totalWins = 0;
        totalLosses = 0;
        totalCCs = 0;
        totalNCs = 0;
        totalCCLosses = 0;
        totalNCLosses = 0;
        totalBowls = 0;
        totalBowlLosses = 0;

        teamPoints = 0;
        teamOppPoints = 0;
        teamYards = 0;
        teamOppYards = 0;
        teamPassYards = 0;
        teamRushYards = 0;
        teamOppPassYards = 0;
        teamOppRushYards = 0;
        teamTODiff = 0;
        teamOffTalent = getOffTalent();
        teamDefTalent = getDefTalent();
        disciplinePts = 0;

        teamPollScore = teamPrestige + getOffTalent() + getDefTalent();

        playbookOffNum = getCPUOffense();
        playbookDefNum = getCPUDefense();
        playbookOff = getPlaybookOff()[playbookOffNum];
        playbookDef = getPlaybookDef()[playbookDefNum];

        hallOfFame.add("");

        teamBudget = 0;
        teamRecruitBudget = 0;
        teamFacilities = 0;
        teamStadium = 0;
        teamDiscplineBudget = 0;
        teamDisciplineScore = disciplineStart;
    }

    //Creates an FCS team
    public Team(String name, String abbr, String conference, int prestige, String div, int loc, League league, boolean FCS) {
        this.name = name;
        this.abbr = abbr;
        this.conference = conference;
        this.league = league;
        location = loc;
        teamPrestige = prestige;
        division = div;
        commonInitializer();

        newRoster(minQBs, minRBs, minWRs, minTEs, minOLs, minKs, minDLs, minLBs, minCBs, minSs, true);

        if(FCS) {
            ArrayList<Team> teams = league.teamList;
            Collections.sort(teams, new CompTeamPrestige());
            int avg = 0;
            for(int x = teams.size()-3; x < teams.size(); x++) {
                avg += teams.get(x).teamPrestige;
            }
            avg = avg/3;


            teamPrestige = avg;
            HC.get(0).contractYear = 0;
            HC.get(0).contractLength = 6;
            HC.get(0).baselinePrestige = teamPrestige;
        }

        //set stats
        totalWins = 0;
        totalLosses = 0;
        totalCCs = 0;
        totalNCs = 0;
        totalCCLosses = 0;
        totalNCLosses = 0;
        totalBowls = 0;
        totalBowlLosses = 0;

        teamPoints = 0;
        teamOppPoints = 0;
        teamYards = 0;
        teamOppYards = 0;
        teamPassYards = 0;
        teamRushYards = 0;
        teamOppPassYards = 0;
        teamOppRushYards = 0;
        teamTODiff = 0;
        teamOffTalent = getOffTalent();
        teamDefTalent = getDefTalent();
        disciplinePts = 0;

        teamPollScore = teamPrestige + getOffTalent() + getDefTalent();

        playbookOffNum = getCPUOffense();
        playbookDefNum = getCPUDefense();
        playbookOff = getPlaybookOff()[playbookOffNum];
        playbookDef = getPlaybookDef()[playbookDefNum];

        hallOfFame.add("");

        teamBudget = 0;
        teamRecruitBudget = 0;
        teamFacilities = 0;
        teamStadium = 0;
        teamDiscplineBudget = 0;
        teamDisciplineScore = disciplineStart;

        if(!FCS) rankTeamPollScore = league.teamList.size();
    }

    /**
     * Constructor for team that is being loaded from file.
     *
     * @param loadStr String containing the team info that can be loaded
     */
    public Team(String loadStr, League league) {
        this.league = league;
        commonInitializer();

        //set stats
        teamPoints = 0;
        teamOppPoints = 0;
        teamYards = 0;
        teamOppYards = 0;
        teamPassYards = 0;
        teamRushYards = 0;
        teamOppPassYards = 0;
        teamOppRushYards = 0;
        teamTODiff = 0;
        teamOffTalent = 0;
        teamDefTalent = 0;
        teamPollScore = 0;
        playbookOffNum = 0;
        playbookDefNum = 0;
        disciplinePts = 0;

        // Actually load the team from the string
        String[] lines = loadStr.split("%");

        // Lines 0 is team info
        String[] teamInfo = lines[0].split(",");
        if (teamInfo.length >= 9) {
            conference = teamInfo[0];
            name = teamInfo[1];
            abbr = teamInfo[2];
            teamPrestige = Integer.parseInt(teamInfo[3]);
            totalWins = Integer.parseInt(teamInfo[4]);
            totalLosses = Integer.parseInt(teamInfo[5]);
            totalCCs = Integer.parseInt(teamInfo[6]);
            totalNCs = Integer.parseInt(teamInfo[7]);
            division = teamInfo[8];
            location = Integer.parseInt(teamInfo[9]);
            totalNCLosses = Integer.parseInt(teamInfo[10]);
            totalCCLosses = Integer.parseInt(teamInfo[11]);
            totalBowls = Integer.parseInt(teamInfo[12]);
            totalBowlLosses = Integer.parseInt(teamInfo[13]);
            playbookOffNum = Integer.parseInt(teamInfo[14]);
            playbookDefNum = Integer.parseInt(teamInfo[15]);
            showPopups = (Integer.parseInt(teamInfo[16]) == 1);
            winStreak = new TeamStreak(Integer.parseInt(teamInfo[19]),
                        Integer.parseInt(teamInfo[20]),
                        Integer.parseInt(teamInfo[17]),
                        teamInfo[18]);
            if(teamInfo.length > 21 && teamInfo.length > 24) {
                teamBudget = Integer.parseInt(teamInfo[21]);
                teamDisciplineScore = Integer.parseInt((teamInfo[22]));
                recentPenalty = Boolean.parseBoolean(teamInfo[23]);
                teamFacilities = Integer.parseInt((teamInfo[24]));
                teamStadium = Integer.parseInt((teamInfo[25]));
            } else {
                teamBudget = 0;
                teamDisciplineScore = disciplineStart;
                teamFacilities = 0;
                teamStadium = 0;
            }
        }

        // Lines 1 is Team Home/Away Rotation
        int startOfPlayers =2;
        if (!lines[1].split(",")[0].equals("HC")) startOfPlayers = 2;
        else startOfPlayers = 1;

        // Rest of lines are player info
        for (int i = startOfPlayers; i < lines.length; ++i) {
            loadPlayerSaveData(lines[i], false);
        }
    }

    private void commonInitializer() {
        userControlled = false;
        showPopups = true;
        teamHistory = new ArrayList<>();
        hallOfFame = new ArrayList<>();
        teamRecords = new TeamRecords();
        playersInjured = new ArrayList<>();

        HC = new ArrayList<>();
        teamQBs = new ArrayList<>();
        teamRBs = new ArrayList<>();
        teamWRs = new ArrayList<>();
        teamTEs = new ArrayList<>();
        teamKs = new ArrayList<>();
        teamOLs = new ArrayList<>();
        teamDLs = new ArrayList<>();
        teamLBs = new ArrayList<>();
        teamCBs = new ArrayList<>();
        teamSs = new ArrayList<>();

        playbookOff = new PlaybookOffense(0);
        playbookDef = new PlaybookDefense(0);

        gameSchedule = new ArrayList<>();
        oocTeams = new ArrayList<>();
        oocWeeks = new ArrayList<>();
        gameWinsAgainst = new ArrayList<>();
        gameLossesAgainst = new ArrayList<>();
        gameWLSchedule = new ArrayList<>();
        confChampion = "";
        sweet16 = "";
        qtFinalWL = "";
        semiFinalWL = "";
        natChampWL = "";

        winStreak = new TeamStreak(league.getYear(), league.getYear(), 0, name);

        playersLeaving = new ArrayList<>();
        playersTransferring = new ArrayList<>();
    }

    public void setupTeamBenchmark() {
        sortPlayers();
        teamPrestigeStart = teamPrestige;
        rankTeamPrestigeStart = rankTeamPrestige;
        confPrestige = league.conferences.get(league.getConfNumber(conference)).confPrestige;
        if(league.conferences.get(league.getConfNumber(conference)).confTeams.size() < league.conferences.get(league.getConfNumber(conference)).minConfTeams) confPrestige = (int)(teamPrestige *.85);
        teamStartOffTal = getOffTalent();
        teamStartDefTal = getDefTalent();

        projectedPollScore = getPreseasonBiasScore();
    }

    //Future Implementation?
/*    public void setTeamBudgets() {

    }*/

    public void projectTeamWins() {
        //Projected Win-Loss Record
        projectedWins = 0;
        for (int i = 0; i < gameSchedule.size(); i++) {
            if (!gameSchedule.get(i).gameName.equals("BYE WEEK")) {
                if (gameSchedule.get(i).homeTeam == this) {
                    if ((projectedPollScore) > gameSchedule.get(i).awayTeam.projectedPollScore) {
                        projectedWins++;
                    }
                } else {
                    if ((projectedPollScore) > gameSchedule.get(i).homeTeam.projectedPollScore) {
                        projectedWins++;
                    }
                }
            }
        }
    }

    public void projectPollRank() {
        projectedPollScore = projectedPollScore + projectedWins * 10;
    }

    public double teamPowerRating() {
        double rating = 0;
        updateTalentRatings();
        rating = (teamPrestige + 2 * teamOffTalent + 2 * teamDefTalent) / 5;

        return rating;
    }

    public void setupUserCoach(String name) {
        HC.clear();
        HC.add(new HeadCoach(name, 35, 5, this));
        HC.get(0).name = name;
        HC.get(0).year = 0;
        HC.get(0).age = 30 + (int)(Math.random()*8);
        HC.get(0).contractYear = 0;
        HC.get(0).contractLength = 6;
        HC.get(0).ratPot = 65;
        HC.get(0).ratOff = league.getAvgCoachOff();
        HC.get(0).ratDef = league.getAvgCoachDef();
        HC.get(0).ratTalent = league.getAvgCoachTal();
        HC.get(0).ratDiscipline = league.getAvgCoachDis();
        HC.get(0).offStrat = 0;
        HC.get(0).defStrat = 0;
        HC.get(0).wins = 0;
        HC.get(0).losses = 0;
        HC.get(0).confchamp = 0;
        HC.get(0).natchamp = 0;
        HC.get(0).bowlwins = 0;
        HC.get(0).bowllosses = 0;
        HC.get(0).allamericans = 0;
        HC.get(0).allconference = 0;
        HC.get(0).confAward = 0;
        HC.get(0).awards = 0;
        HC.get(0).history.clear();
        HC.get(0).retired = false;
    }

    /**
     * Recruits the needed amount of players at each position.
     * Rating of each player based on team prestige.
     * This is used when first creating a team.
     *
     * @param qbNeeds
     * @param rbNeeds
     * @param wrNeeds
     * @param kNeeds
     * @param olNeeds
     * @param sNeeds
     * @param cbNeeds
     * @param dlNeeds
     * @param teNeeds
     * @param lbNeeds
     */
    public void newRoster(int qbNeeds, int rbNeeds, int wrNeeds, int teNeeds, int olNeeds, int kNeeds, int dlNeeds, int lbNeeds, int cbNeeds, int sNeeds, boolean coach) {
        //make team
        int stars = Math.round(teamPrestige / 10);
        int chance = 15;
        int num;

        for (int i = 0; i < qbNeeds; ++i) {
            //make QBs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamQBs.add(new PlayerQB(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamQBs.add(new PlayerQB(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamQBs.add(new PlayerQB(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < kNeeds; ++i) {
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamKs.add(new PlayerK(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num < (100 - chance)) {
                teamKs.add(new PlayerK(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamKs.add(new PlayerK(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < rbNeeds; ++i) {
            //make RBs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamRBs.add(new PlayerRB(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamRBs.add(new PlayerRB(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamRBs.add(new PlayerRB(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < wrNeeds; ++i) {
            //make WRs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamWRs.add(new PlayerWR(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamWRs.add(new PlayerWR(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamWRs.add(new PlayerWR(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < teNeeds; ++i) {
            //make TEs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamTEs.add(new PlayerTE(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamTEs.add(new PlayerTE(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamTEs.add(new PlayerTE(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < olNeeds; ++i) {
            //make OLs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamOLs.add(new PlayerOL(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamOLs.add(new PlayerOL(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamOLs.add(new PlayerOL(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < cbNeeds; ++i) {
            //make CBs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamCBs.add(new PlayerCB(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamCBs.add(new PlayerCB(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamCBs.add(new PlayerCB(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < dlNeeds; ++i) {
            //make DLs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamDLs.add(new PlayerDL(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamDLs.add(new PlayerDL(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamDLs.add(new PlayerDL(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < lbNeeds; ++i) {
            //make LBs
            num = (int) (Math.random() * 100);
            if (num < chance) {
                teamLBs.add(new PlayerLB(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamLBs.add(new PlayerLB(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamLBs.add(new PlayerLB(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        for (int i = 0; i < sNeeds; ++i) {
            //make Ss
            num = (int) (Math.random() * 100);
            if (100 * Math.random() < 5 * chance) {
                teamSs.add(new PlayerS(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (num > (100 - chance)) {
                teamSs.add(new PlayerS(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                teamSs.add(new PlayerS(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        //MAKE HEAD COACH
        if (coach) {
            int coachNum = 100 * (int) Math.random();
            if (coachNum < 20) {
                HC.add(new HeadCoach(league.getRandName(), (int) (4 * Math.random() + 1), stars - 2, this));
            } else if (coachNum > 80) {
                HC.add(new HeadCoach(league.getRandName(), (int) (4 * Math.random() + 1), stars + 2, this));
            } else {
                HC.add(new HeadCoach(league.getRandName(), (int) (4 * Math.random() + 1), stars, this));
            }
        }

        //done making players, sort them
        sortPlayers();
        recruitWalkOns();
    }


    public void redshirtCPUPlayers() {
        int redshirts = 0;
        int redshirtMax = HC.get(0).ratTalent / 9;
        sortPlayers();

        ArrayList<Player> teamFRs = getAllPlayers();
        for(int i = 0; i < teamFRs.size() ; i++) {
            if(teamFRs.get(i).year != 1 || teamFRs.get(i).wasRedshirt) {
                teamFRs.remove(i);
            }
        }
        Collections.sort(teamFRs, new CompPlayer());

        for (Player p : teamFRs) {
            if (p instanceof PlayerQB) {
                for (int i = 0; i < teamQBs.size(); ++i) {
                    if (teamQBs.get(i) == p) {
                        if (i > startersQB && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerRB) {
                for (int i = 0; i < teamRBs.size(); ++i) {
                    if (teamRBs.get(i) == p) {
                        if (i > 1.5 * startersRB && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerWR) {
                for (int i = 0; i < teamWRs.size(); ++i) {
                    if (teamWRs.get(i) == p) {
                        if (i > 1.5 * startersWR && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerTE) {
                for (int i = 0; i < teamTEs.size(); ++i) {
                    if (teamTEs.get(i) == p) {
                        if (i > 1.5 * startersTE && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerOL) {
                for (int i = 0; i < teamOLs.size(); ++i) {
                    if (teamOLs.get(i) == p) {
                        if (i > 1.5 * startersOL && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerK) {
                for (int i = 0; i < teamKs.size(); ++i) {
                    if (teamKs.get(i) == p) {
                        if (i > 1.5 * startersK && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerDL) {
                for (int i = 0; i < teamDLs.size(); ++i) {
                    if (teamDLs.get(i) == p) {
                        if (i > 1.5 * startersDL && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerLB) {
                for (int i = 0; i < teamLBs.size(); ++i) {
                    if (teamLBs.get(i) == p) {
                        if (i > 1.5 * startersLB && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerCB) {
                for (int i = 0; i < teamCBs.size(); ++i) {
                    if (teamCBs.get(i) == p) {
                        if (i > 1.5 * startersCB && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
            if (p instanceof PlayerS) {
                for (int i = 0; i < teamSs.size(); ++i) {
                    if (teamSs.get(i) == p) {
                        if (i > 1.5 * startersS && redshirts < redshirtMax && !p.wasRedshirt) {
                            p.isRedshirt = true;
                            redshirts++;
                        }
                    }
                }
            }
        }

        recruitWalkOns();
    }

    public void newCustomHeadCoach(String coachName, int stars) {
        HC.add(new HeadCoach(coachName, 1, stars, this));
    }

    /**
     * Sorts players so that best players are higher in depth chart.
     */
    public void sortPlayers() {
        //sort players based on overall ratings to assemble best starting lineup
        Collections.sort(teamQBs, new CompPlayer());
        Collections.sort(teamRBs, new CompPlayer());
        Collections.sort(teamWRs, new CompPlayer());
        Collections.sort(teamTEs, new CompPlayer());
        Collections.sort(teamKs, new CompPlayer());
        Collections.sort(teamOLs, new CompPlayer());
        Collections.sort(teamDLs, new CompPlayer());
        Collections.sort(teamLBs, new CompPlayer());
        Collections.sort(teamCBs, new CompPlayer());
        Collections.sort(teamSs, new CompPlayer());
    }

    /**
     * Updates poll score based on team stats.
     */
    public void updatePollScore() {
        updateStrengthOfWins();
        updateLossStrength();
        int offRating = offenseRating();
        int defRating = defenseRating();
        teamOffTalent = getOffTalent();
        teamDefTalent = getDefTalent();

        int univProRelBonus = 0;
        if (league.enableUnivProRel) {
            univProRelBonus = 20 * (10 - league.getConfNumber(conference));
        }

        double preseasonBias = 15 - (wins + losses);
        if (preseasonBias < 3) preseasonBias = 3;
        preseasonBias = preseasonBias / 15;
        teamPollScore =
                (float) (preseasonBias * getPreseasonBiasScore()) +
                        (float) (offRating + defRating + teamStrengthOfWins - teamStrengthOfLosses + 500 + univProRelBonus);

        if (league.currentWeek == 0) {
            teamPollScore = getPreseasonBiasScore();
        }


        if ("CC".equals(confChampion)) {
            //bonus for winning conference
            teamPollScore += 15;
        }
        if ("NCW".equals(natChampWL)) {
            //bonus for winning champ game
            teamPollScore += 1000;
        }
        if ("NCL".equals(natChampWL)) {
            //bonus for winning champ game
            teamPollScore += 500;
        }
        if ("SFW".equals(semiFinalWL)) {
            //bonus for winning champ game
            teamPollScore += 200;
        }
        if ("SFL".equals(semiFinalWL)) {
            //bonus for winning champ game
            teamPollScore += 150;
        }
        if ("QTW".equals(qtFinalWL)) {
            //bonus for winning champ game
            teamPollScore += 150;
        }
        if ("QTL".equals(qtFinalWL)) {
            //bonus for winning champ game
            teamPollScore += 50;
        }
        if ("S16W".equals(sweet16)) {
            //bonus for winning champ game
            teamPollScore += 50;
        }
        if ("S16L".equals(sweet16)) {
            //bonus for winning champ game
            teamPollScore += 25;
        }
    }

    private float  getPreseasonBiasScore() {
        float score = 0;

        if (league.currentWeek > 0) {
            score += league.teamList.size() - rankTeamOffTalent;
            score += league.teamList.size() - rankTeamDefTalent;
            score += 1.5 * (league.teamList.size() - rankTeamPrestige);
            if (league.conferences.get(league.getConfNumber(conference)).confTeams.size() < league.conferences.get(league.getConfNumber(conference)).minConfTeams) {
                score += teamPrestige / 2.25;
            } else {
                score += confPrestige / 2;
            }
        } else {
            score += 1 * getOffTalent();
            score += 1 * getDefTalent();
            score += 3 * teamPrestige;
            if (league.conferences.get(league.getConfNumber(conference)).confTeams.size() < league.conferences.get(league.getConfNumber(conference)).minConfTeams) {
                score += teamPrestige / 2.25;
            } else {
                score += confPrestige / 2;
            }
        }

        return score;
    }

    public void updateSOS() {
        teamSOS = 0;
        for (int i = 0; i < gameSchedule.size(); ++i) {
            Game g = gameSchedule.get(i);
            if (g.homeTeam == this) {
                teamSOS += league.teamList.size() - g.awayTeam.rankTeamPollScore;
            } else {
                teamSOS += league.teamList.size() - g.homeTeam.rankTeamPollScore;
            }
        }
    }

    /**
     * Updates strength of wins based on how opponents have fared.
     */
    private void updateStrengthOfWins() {
        teamStrengthOfWins = 0;
        for (int i = 0; i < gameWinsAgainst.size(); ++i) {
            teamStrengthOfWins += (league.countTeam - gameWinsAgainst.get(i).rankTeamPollScore);
        }
    }


    private void updateLossStrength() {
        teamStrengthOfLosses = 0;
        for (int i = 0; i < gameLossesAgainst.size(); ++i) {
            teamStrengthOfLosses += gameLossesAgainst.get(i).rankTeamPollScore;
        }
    }

    private int offenseRating() {
        int offRating = 0;
        offRating = (league.countTeam - rankTeamPoints) + (league.countTeam - rankTeamYards);
        return offRating;
    }

    private int defenseRating() {
        int defRating = 0;
        defRating = (league.countTeam - rankTeamOppPoints) + (league.countTeam - rankTeamOppYards);
        return defRating;
    }

    /**
     * Gets the OffTalent, DefTalent, poll score
     */
    public void updateTalentRatings() {
        teamOffTalent = getOffTalent();
        teamDefTalent = getDefTalent();
        teamPollScore = teamPrestige + teamOffTalent + teamDefTalent;
        teamChemistry = getTeamChemistry();
    }

    /**
     * Calculates offensive talent level of team.
     *
     * @return Offensive Talent Level
     */
    public float getOffTalent() {
        return (getQB(0).ratOvr * 5 +
                teamWRs.get(0).ratOvr + teamWRs.get(1).ratOvr + teamWRs.get(2).ratOvr +
                teamRBs.get(0).ratOvr + teamRBs.get(1).ratOvr + teamTEs.get(0).ratOvr +
                getCompositeOLPass() + getCompositeOLRush() + getOffSubTalent()) / 14;
    }

    private float getOffSubTalent() {
        return ((getQB(1).ratOvr + getRB(2).ratOvr + getWR(3).ratOvr + getWR(4).ratOvr + getTE(1).ratOvr + getOL(5).ratOvr + getOL(6).ratOvr) / 7);
    }

    /**
     * Calculates defensive talent level of team.
     *
     * @return Defensive Talent Level
     */
    public float getDefTalent() {

        return (getRushDef() + getPassDef()) / 2;

    }

    /**
     * Get the composite Football IQ of the team. Is used in game simulation.
     *
     * @return football iq of the team
     */
    public float getCompositeFootIQ() {
        float comp = 0;
        //Offense: 16
        comp += getQB(0).ratFootIQ * 5; //5
        comp += getRB(0).ratFootIQ + getRB(1).ratFootIQ; //2
        comp += getWR(0).ratFootIQ + getWR(1).ratFootIQ + getWR(2).ratFootIQ; //3
        comp += getTE(0).ratFootIQ; //1
        for (int i = 0; i < 5; ++i) { //5
            comp += getOL(i).ratFootIQ;
        }
        //Defense: 14
        comp += getS(0).ratFootIQ * 4 + getS(1).ratFootIQ * 4; //8
        comp += getCB(0).ratFootIQ + getCB(1).ratFootIQ + getCB(2).ratFootIQ; //3
        for (int i = 0; i < 4; ++i) { //4
            comp += getDL(i).ratFootIQ;
        }
        for (int i = 0; i < 3; ++i) { //3
            comp += getLB(i).ratFootIQ;
        }

        //coach: 8
        comp += HC.get(0).ratDef * 4 + HC.get(0).ratOff * 4; //8

        //subs: 1
        comp += (getRB(2).ratFootIQ + getWR(3).ratFootIQ + getWR(4).ratFootIQ + getTE(1).ratFootIQ + getOL(5).ratFootIQ + getOL(6).ratFootIQ +
                getDL(4).ratFootIQ + getDL(5).ratFootIQ + getLB(3).ratFootIQ + getCB(4).ratFootIQ + 2 * getS(2).ratFootIQ) / 12;

        return comp / 43;
    }

    /**
     * team disicipline rating
     */
    public int getTeamDiscipline() {
        int rating = 0;
        ArrayList<Player> roster = getAllPlayers();
        for (int i = 0; i < roster.size(); ++i) {
            rating += roster.get(i).personality;
        }
        return rating / roster.size();
    }

    /**
     * team disicipline rating
     */
    public double getTeamChemistry() {
        double rating = 0;
        ArrayList<Player> roster = getAllPlayers();
        for (int i = 0; i < roster.size(); ++i) {
            rating += roster.get(i).personality;
            rating += roster.get(i).ratFootIQ;
        }
        return rating / (2*roster.size());
    }


    /**
     * Get pass proficiency. The higher the more likely the team is to pass.
     *
     * @return integer of how good the team is at passing
     */
    public float getPassProf() {
        float avgWRs = (teamWRs.get(0).ratOvr + teamWRs.get(1).ratOvr + teamWRs.get(2).ratOvr + teamTEs.get(0).ratCatch) / 4;
        float avgSubs = (2 * getWR(3).ratCatch + getTE(1).ratCatch + getRB(0).ratCatch + getRB(1).ratCatch + getRB(2).ratCatch) / 6;

        return (2 * getCompositeOLPass() + getQB(0).ratOvr * 5 + avgWRs * 4 + HC.get(0).ratOff * 2 + avgSubs) / 14;
    }

    /**
     * Get run proficiency. The higher the more likely the team is to run.
     *
     * @return integer of how good the team is at rushing
     */
    public float getRushProf() {
        float avgRBs = (teamRBs.get(0).ratOvr + teamRBs.get(1).ratOvr) / 2;
        float QB = teamQBs.get(0).ratSpeed;
        float avgSub = getRB(2).ratOvr;

        return (3 * getCompositeOLRush() + 4 * avgRBs + QB + 2 * HC.get(0).ratOff + avgSub) / 11;
    }

    /**
     * Get how good the team is at defending the pass
     *
     * @return integer of how good
     */
    public float getPassDef() {
        float avgCBs = (teamCBs.get(0).ratOvr + teamCBs.get(1).ratOvr + teamCBs.get(2).ratOvr) / 3;
        float avgLBs = (teamLBs.get(0).ratCoverage + teamLBs.get(1).ratCoverage + teamLBs.get(2).ratCoverage) / 3;
        float S = (teamSs.get(0).ratCoverage + teamSs.get(1).ratCoverage) / 2;
        float def = (3 * avgCBs + avgLBs + S) / 5;
        float avgSub = (getLB(3).ratCoverage + getCB(3).ratOvr * 2 + getS(2).ratCoverage) / 4;


        return (def * 4 + teamSs.get(0).ratOvr + getCompositeDLPass() * 2 + 2 * HC.get(0).ratDef + avgSub) / 10;
    }

    /**
     * Get how good the team is at defending the rush
     *
     * @return integer of how good
     */
    public float getRushDef() {
        return getCompositeDLRush();
    }

    /**
     * Get how good the OL is at defending the pass
     * Is the average of power and pass blocking.
     *
     * @return how good they are at blocking the pass.
     */
    public float getCompositeOLPass() {
        float compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratPassBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL = compositeOL / 5;
        float avgSub = (getOL(5).ratOvr + getOL(6).ratOvr) / 2;

        return (9 * compositeOL + avgSub + 3 * HC.get(0).ratOff) / 13;
    }

    /**
     * Get how good the OL is at defending the rush
     * Is the average of power and rush blocking.
     *
     * @return how good they are at blocking the rush.
     */
    public float getCompositeOLRush() {
        float compositeOL = 0;
        for (int i = 0; i < 5; ++i) {
            compositeOL += (teamOLs.get(i).ratStrength * 2 + teamOLs.get(i).ratRunBlock * 2 + teamOLs.get(i).ratAwareness) / 5;
        }
        compositeOL = compositeOL / 5;
        float compositeTE = teamTEs.get(0).ratRunBlock;

        float avgSub = (2 * getOL(5).ratOvr + 2 * getOL(6).ratOvr + getTE(1).ratRunBlock) / 5;

        return (9 * compositeOL + 2 * compositeTE + 3 * HC.get(0).ratOff + avgSub) / 15;
    }

    /**
     * Get how good the DL is at defending the pass.
     * Is the average of power and pass pressure.
     *
     * @return how good they are at putting pressure on passer.
     */
    public float getCompositeDLPass() {
        float compositeDL = 0;
        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratPassRush) / 2;
        }
        compositeDL = compositeDL / 4;

        float avgSub = getDL(4).ratOvr + getDL(5).ratOvr;

        return (5 * compositeDL + 2 * HC.get(0).ratDef + avgSub) / 8;
    }

    /**
     * Get how good the DL is at defending the run.
     * Is the average of power and run stopping.
     *
     * @return how good they are at stopping the RB.
     */
    public float getCompositeDLRush() {
        float compositeDL = 0;
        float compositeLB = 0;
        float compositeS = 0;

        for (int i = 0; i < 4; ++i) {
            compositeDL += (teamDLs.get(i).ratStrength + teamDLs.get(i).ratRunStop) / 2;
        }
        compositeDL = compositeDL / 4;

        for (int i = 0; i < 3; ++i) {
            compositeLB += teamLBs.get(i).ratRunStop;
        }
        compositeLB = compositeLB / 3;

        compositeS += teamSs.get(0).ratRunStop + teamSs.get(1).ratRunStop;
        compositeS = compositeS / 2;

        float avgSub = (2 * getDL(4).ratOvr + 2 * getDL(5).ratOvr + getLB(3).ratRunStop + getS(2).ratRunStop) / 6;
        return (4 * compositeDL + 2 * compositeLB + 2 * compositeS + 2 * HC.get(0).ratDef + avgSub) / 11;
    }

    public void enterOffSeason() {
        prestigePts = calcSeasonPrestige();
        teamPrestige = prestigePts[0];
        totalWins += wins;
        totalLosses += losses;
        curePlayers();
        sortPlayers();
    }

    //Calculates Prestige Change at end of season
    public int[] calcSeasonPrestige() {

        int goal = projectedPollRank;
        if (goal > league.teamList.size()*.875) goal = (int)(league.teamList.size()*.875);
        if (goal <= league.teamList.size()*.125) goal = (int)(league.teamList.size()*.125);
        int diffExpected = goal - rankTeamPollScore;

        int newPrestige = teamPrestige;
        int prestigeChange = 0;

        int ccPts = 0;
        int ncwPts = 0;
        int nflPts = 0;

        int disPts = disciplinePts;

        // Don't add/subtract prestige if they are a penalized team from last season
        if (!bowlBan && !penalized) {
            prestigeChange = Math.round((float) (diffExpected / 7.5));
            int postSeasonGames = 0;
            for(Game g: gameSchedule) {
                if(!g.gameName.equals("Conference") && !g.gameName.equals("Division") && !g.gameName.equals("OOC") && !g.gameName.equals("BYE")) {
                    postSeasonGames++;
                }
            }

            if((postSeasonGames) > 0 && prestigeChange < 0) prestigeChange++;
            if (prestigeChange < (wins - projectedWins)) prestigeChange++;
            if(prestigeChange <= 0 && rankTeamPollScore < rankTeamPrestige) prestigeChange++;
        }

        //National Title Winner
        if (natChampWL.equals("NCW")) {
            if(rankTeamPrestige == 1) ncwPts += 1;
            if(rankTeamPrestige <= 5) ncwPts += 2;
            else ncwPts += 3;
        }

        if (natChampWL.equals("NCL") || semiFinalWL.equals("SFL")) {
            ncwPts += 1;
        }

        //bonus for winning conference
        if (confChampion.equals("CC")) {
            ccPts += 1;
        }

        if (rankTeamPrestige > (league.teamList.size()*.60)) {
            ArrayList<Player> teamAll = getAllPlayers();
            for (int i = 0; i < teamAll.size(); i++) {
                if (teamAll.get(i).year == 4 && teamAll.get(i).ratOvr >= 90) {
                    nflPts++;
                }
            }
            if (nflPts > 3) nflPts = 3;
        }

        newPrestige += prestigeChange + ccPts + ncwPts + nflPts + disPts;

        if(newPrestige < 0) newPrestige = 0;

        int PrestigeScore[] = {newPrestige, prestigeChange, ccPts, ncwPts, nflPts, disPts};
        return PrestigeScore;
    }

    /**
     * Get a summary of your team's season.
     * Tells how they finished, if they beat/fell short of expecations, and if they won rivalry game.
     *
     * @return String of season summary
     */
    public String seasonSummaryStr() {
        String summary = "Season Analysis:\n\nYour team, " + name + ", finished the season ranked #" + rankTeamPollScore + " with " + wins + " wins and " + losses + " losses.";

        summary += "\n\nYour team was projected to finish ranked #" + projectedPollRank + " with a record of " + projectedWins + " wins and " + (12 - projectedWins) + " losses.";
        if (confChampion.equals("CC"))
            summary += "\n\nCongratulations on winning the " + conference + " Championship!";

        int num = (int)(league.teamList.size()*.875);

        if (projectedPollRank > num) {
            summary += "\nDespite being projected at #" + projectedPollRank + ", your goal was to finish in the Top " + num + ".\n\n";
        }
        if (bowlBan || penalized) {
            summary += "\n\nYour team had penalties placed on it by the collegiate administration this season. Recruiting budgets were reduced due to this.";
        } else if ((prestigePts[1]) > 0) {
            summary += "\n\nYou met or exceeded expectations and gained " + prestigePts[1] + " prestige points!";
        } else if ((prestigePts[1]) < 0) {
            summary += "\n\nYou fell short of expectations and lost " + Math.abs(prestigePts[1]) + " prestige points.";
        } else {
            summary += "\n\nYour team performed exactly as expected.";
        }

        if (natChampWL.equals("NCW")) {
            summary += "\n\nYou won the National Championship! Recruits want to play for winners and you have proved that you are one. You gain " + prestigePts[3] + " prestige points!";
        }

        if (natChampWL.equals("NCL") || semiFinalWL.equals("SFL")) {
            summary += "\n\nYou made it to College Football Playoffs! You gain " + prestigePts[3] + " prestige points!";
        }

        if (prestigePts[2] > 0) {
            summary += "\n\nSince you won your conference championship, your team gained " + prestigePts[2] + " prestige points!";
        }
        if (prestigePts[4] > 0) {
            summary += "\n\nYou have Pro caliber talent going to the draft. Since your school isn't expected to have such talents, you will see a gain of " + prestigePts[4] + " prestige points in the off-season!";
        }

        if (disciplinePts < 0) {
            summary += "\n\nYour team had some trouble with disciplinary violations this season and dropped " + Math.abs(disciplinePts) + " prestige points.";
        } else if (disciplinePts > 0) {
            summary += "\n\nYour team stayed out of trouble this season and bonded together. Your team gained " + disciplinePts + " prestige points.";
        }

        summary += "\n\nYour team's current discipline score is " + teamDisciplineScore + "% Rating.";

        summary += "\n\nYour coach grade for this year was: " + HC.get(0).getSeasonGrade() + " (" + HC.get(0).getCoachScore() + " pts)\n";

        summary += "\n\nPRESTIGE SUMMARY:\n\n";
        summary += "Current Prestige:  " + teamPrestigeStart + " pts [" + getRankStr(rankTeamPrestigeStart) + "]\n";
        summary += "Performance Points:  " + prestigePts[1] + " pts\n";
        if (prestigePts[2] > 0) summary += "Conf Champ Bonus:  " + prestigePts[2] + " pts\n";
        if (natChampWL.equals("NCW")) summary += "Nat Title Bonus:  " + prestigePts[3] + " pts\n";
        if (natChampWL.equals("NCL") || semiFinalWL.equals("SFL") ) summary += "CFP Bonus:  " + prestigePts[3] + " pts\n";
        if (prestigePts[4] > 0) summary += "Pro Draft Bonus:  " + prestigePts[4] + " pts\n";
        summary += "Disciplinary Points:  " + prestigePts[5] + " pts\n";

        summary += "\n\nNEW PRESTIGE:  " + teamPrestige + " pts [" + getRankStr(rankTeamPrestige) + "]\n";

        if (newContract && league.isCareerMode()) {
            summary += "\n\nCongratulations! You've been awarded with a contract extension of " + HC.get(0).contractLength + " years.";
        } else if (fired) {
            summary += "\n\nDue to failing to raise the team prestige during your contract length, you've been terminated from this position. Your team may take an additional prestige hit due to the firing.";
        }
        return summary;
    }

    /**
     * Advance season, hiring new coach if needed and calculating new prestige level.
     */
    public void advanceTeamPlayers() {
        advanceSeasonPlayers();
        checkHallofFame();
        checkCareerRecords(league.leagueRecords);
        checkCareerTeamRecords(teamRecords);
    }

    // OFF-SEASON HEAD COACH PROGRESSION
    // CAN BE FIRED OR EXTENDED CONTRACT HERE
    public void advanceHC(LeagueRecords records, TeamRecords teamRecords) {
        newContract = false;
        fired = false;
        retired = false;
        int avgOff = league.getAverageYards();


        int totalPDiff = teamPrestige - HC.get(0).baselinePrestige;
        HC.get(0).advanceSeason(avgOff, league.leagueOffTal, league.leagueDefTal);

        teamRecords.checkRecord("Coach Year Score", HC.get(0).getCoachScore(), HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("Wins", HC.get(0).wins, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("National Championships", HC.get(0).natchamp, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("Conf Championships", HC.get(0).confchamp, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("Bowl Wins", HC.get(0).bowlwins, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("Coach Awards", HC.get(0).awards, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("All-Americans", HC.get(0).allamericans, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("All-Conferences", HC.get(0).allconference, HC.get(0).name + "%" + abbr, league.getYear());
        teamRecords.checkRecord("Bowl Appearances", (HC.get(0).bowlwins+HC.get(0).bowllosses), HC.get(0).name + "%" + abbr, league.getYear());

        records.checkRecord("Wins", HC.get(0).wins, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("National Championships", HC.get(0).natchamp, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("Conf Championships", HC.get(0).confchamp, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("Bowl Wins", HC.get(0).bowlwins, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("Coach Awards", HC.get(0).awards, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("Coach Year Score", HC.get(0).getCoachScore(), HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("All-Americans", HC.get(0).allamericans, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("All-Conferences", HC.get(0).allconference, HC.get(0).name + "%" + abbr, league.getYear());
        records.checkRecord("Bowl Appearances", (HC.get(0).bowlwins+HC.get(0).bowllosses), HC.get(0).name + "%" + abbr, league.getYear());
        coachContracts(totalPDiff, teamPrestige);

        if (!HC.isEmpty()) {
            if (userControlled && league.isCareerMode() && totalPDiff > promotionNum && teamPrestige >= HC.get(0).baselinePrestige) {
                HC.get(0).promotionCandidate = true;
            }
        }
    }

    public void checkFacilitiesUpgradeBonus() {
        if(facilityUpgrade) {
            teamPrestige += teamFacilities;
            if(HC.size() > 0) getHC(0).baselinePrestige += teamFacilities*.75;
        }
    }

    private void coachContracts(int totalPDiff, int newPrestige) {
        int max = 78;
        int min = 65;
        Random rand = new Random();
        int retire = rand.nextInt((max - min) + 1) + min;
        int age = HC.get(0).age;
        int wins = HC.get(0).wins;
        int losses = HC.get(0).losses;
        boolean proveIt = false;

        //RETIREMENT
        if (HC.get(0).age > retire && !userControlled) {
            retired = true;
            HC.get(0).retired = true;
            if(HC.get(0).cumulativePrestige >= 25) teamPrestige = (int)(teamPrestige*knockdownRet);
            else teamPrestige = (int)(teamPrestige*knockdownFired);
            league.coachFreeAgents.add(HC.get(0));
            String oldCoach = HC.get(0).name;
            fired = true;
            newCoachTeamChanges();
            HC.remove(0);
            league.newsStories.get(league.currentWeek + 1).add(name + " Coaching Retirement>" + oldCoach + " has announced his retirement at the age of " + age +
                    ". His former team, " + name + " have not announced a successor to replace the retired coach. Coach " + oldCoach + " had a career record of " + wins + "-" + losses + ".");
        }

        if (!retired) {
            if (teamPrestige > (HC.get(0).baselinePrestige + 9) && rankTeamPrestige > (int) (league.countTeam * 0.35) && !userControlled && HC.get(0).age < 50 || teamPrestige > (HC.get(0).baselinePrestige + 12) && confPrestige < league.confAvg && rankTeamPrestige < (int) (league.countTeam * 0.20) && !userControlled && HC.get(0).age < 48) {
                league.newsStories.get(league.currentWeek + 1).add("Head Coach Rumor Mill>After another successful season at " + name + ", " + age + " year old head coach " + HC.get(0).name + " has moved to the top of" +
                        " many of the schools looking for a replacement at that position. He has a career record of " + wins + "-" + losses + ". ");
                if (Math.random() > 0.50) {
                    league.coachStarList.add(HC.get(0));
                }
            }
            //New Contracts or Firing
            if ((HC.get(0).contractYear) >= HC.get(0).contractLength || natChampWL.equals("NCW") || natChampWL.equals("NCL") || (HC.get(0).contractYear + 1 == HC.get(0).contractLength && Math.random() < 0.38) || (HC.get(0).contractYear + 2 == HC.get(0).contractLength && Math.random() < 0.23)) {
                if (totalPDiff > 15 || (natChampWL.equals("NCW"))) {
                    HC.get(0).contractLength = 7;
                    HC.get(0).contractYear = 0;
                    HC.get(0).baselinePrestige = (HC.get(0).baselinePrestige + 2 * teamPrestige) / 3;
                    newContract = true;
                    league.newsStories.get(league.currentWeek + 1).add("Long-Term Extension!>" + name + " has extended their head coach, " + HC.get(0).name +
                            " for 7 additional seasons for his successful tenure at the university.");
                } else if (totalPDiff > 10) {
                    HC.get(0).contractLength = 6;
                    HC.get(0).contractYear = 0;
                    HC.get(0).baselinePrestige = (HC.get(0).baselinePrestige + 2 * teamPrestige) / 3;
                    newContract = true;
                    league.newsStories.get(league.currentWeek + 1).add("New 5-Year Contract Awarded!>" + name + " has extended their head coach, " + HC.get(0).name +
                            " for 6 additional seasons for his successful tenure at the university.");
                } else if (totalPDiff > 7) {
                    HC.get(0).contractLength = 4;
                    HC.get(0).contractYear = 0;
                    HC.get(0).baselinePrestige = (HC.get(0).baselinePrestige + 2 * teamPrestige) / 3;
                    newContract = true;
                } else if (totalPDiff > 5 || (natChampWL.equals("NCL"))) {
                    if ((natChampWL.equals("NCL")) && HC.get(0).contractLength - HC.get(0).contractYear > 2) {

                    } else {
                        HC.get(0).contractLength = 3;
                        HC.get(0).contractYear = 0;
                        HC.get(0).baselinePrestige = (HC.get(0).baselinePrestige + 2 * teamPrestige) / 3;
                        newContract = true;
                    }
                } else if (totalPDiff < 0 && (teamPrestige - teamPrestigeStart) > 2 || rankTeamPrestige > 15 && (teamPrestige - teamPrestigeStart) > 2) {
                    if (Math.random() > 0.40) {
                        HC.get(0).contractLength = 2;
                        HC.get(0).contractYear = 0;
                        HC.get(0).baselinePrestige = HC.get(0).baselinePrestige;
                        league.newsStories.get(league.currentWeek + 1).add("2-Year Prove-It Contract Given by " + name + ">" + name + " has an additional 2-year contract to " + HC.get(0).name +
                                " despite a disappointing tenure. He has a career record of " + wins + "-" + losses + ", however the recent success of the team this season has inspired some confidence with the head coach from the AD.");
                        newContract = true;
                        proveIt = true;
                    } else {
                        fired = true;
                        league.newsStories.get(league.currentWeek + 1).add("Polarizing Coach Firing at " + name + ">" + strRankTeamRecord() + " has fired their head coach, " + HC.get(0).name +
                                " despite finally getting the team on the right track. The team struggled during his first few seasons at the school, but had shown some promise this season." +
                                " He has a career record of " + wins + "-" + losses + ".  The team is now searching for a new head coach.");
                        newCoachTeamChanges();
                        if (!userControlled) {
                            league.coachList.add(HC.get(0));
                        }
                        HC.remove(0);
                    }
                } else if (totalPDiff < -2 && !league.isCareerMode() && !userControlled  && rankTeamPollScore > 15 || !userControlled && rankTeamPollScore > 25 && totalPDiff < -1) {
                    fired = true;
                    league.newsStories.get(league.currentWeek + 1).add("Coach Firing at " + name + ">" + strRankTeamRecord() + " has fired their head coach, " + HC.get(0).name +
                            " after a disappointing tenure. He has a career record of " + wins + "-" + losses + ". The team is now searching for a new head coach.");
                    newCoachTeamChanges();
                    league.coachList.add(HC.get(0));
                    HC.remove(0);
                } else if (totalPDiff < -2 && league.isCareerMode() && rankTeamPollScore > 15 || rankTeamPollScore > 25 && totalPDiff < -1) {
                    fired = true;
                    league.newsStories.get(league.currentWeek + 1).add("Coach Firing at " + name + ">" + strRankTeamRecord() + " has fired their head coach, " + HC.get(0).name +
                            " after a disappointing tenure. He has a career record of " + wins + "-" + losses + ".  The team is now searching for a new head coach.");
                    newCoachTeamChanges();
                    if (!userControlled) {
                        league.coachList.add(HC.get(0));
                    }
                    HC.remove(0);
                } else {
                    HC.get(0).contractLength = 2;
                    HC.get(0).contractYear = 0;
                    HC.get(0).baselinePrestige = (3 * HC.get(0).baselinePrestige + teamPrestige) / 4;
                    newContract = true;
                }
            }
        }
        if (userControlled) {
            if (newContract && proveIt)
                contractString = "You've been given an additional " + HC.get(0).contractLength + " year contract based on the recent success of your team and not the disappointing past history!";
            else if (newContract) {
                contractString = "Congratulations! You've been awarded with a new contract extension for " + HC.get(0).contractLength + " years!";
            } else if (fired) {
                contractString = "Due to your poor performance as head coach, the Athletic Director has terminated your contract and you are no longer Head Coach of this school.";
            } else {
                contractString = "You have " + (HC.get(0).contractLength - HC.get(0).contractYear)
                        + " years left on your contract. Your team prestige is currently at " + teamPrestige + " and your baseline " +
                        "prestige was " + HC.get(0).baselinePrestige;
            }
        }

    }

    public void midSeasonFiring() {
        final String hcName = HC.get(0).name;
        HC.get(0).team = null;
        league.coachFreeAgents.add(HC.get(0));
        HC.remove(0);

       promoteCoach();
       HC.get(0).contractLength = 1;
       HC.get(0).contractYear = 1;

        if(teamDisciplineScore < 60) {
            teamDisciplineScore = (60 + teamDisciplineScore)/2;
        }

        league.newsStories.get(league.currentWeek).add("FIRED! Acting Head Coach named at " + name + ">" + name + " has fired their head coach, " + hcName +
                " and has promoted his assistant coach, " + HC.get(0).name + ", as Acting Head Coach for the remainder of the season. After the season ends, the team will determine what to do for the Head Coach vacancy.");
    }

    private void newCoachTeamChanges() {

        if(HC.get(0).contractLength != 1) teamPrestige = (int)(teamPrestige * knockdownFired);

        if(teamDisciplineScore < 60) {
            teamDisciplineScore = (60 + teamDisciplineScore)/2;
        }

    }

    public void promoteCoach() {
        //make team
        boolean promote = true;
        int stars = (int)((league.teamList.size() - rankTeamPrestige) / (league.teamList.size()/10.5));
        stars = (int)(Math.random()*stars) + (stars/4);
        if(stars > 10) stars = 10;
        if(stars < 2) stars = 2;

        //MAKE HEAD COACH
        HC.add(new HeadCoach(league.getRandName(), 0, stars, this, true));

        //done making players, sort them
        sortPlayers();
    }

    //Provide the minimum overall rating for a new coach hire
    public int getMinCoachHireReq() {
        int req = (league.teamList.size() - rankTeamPrestige) / 2 + (int)Math.round(league.teamList.size()/3.6);
        if (req >= 88) req = 88;
        return req;
    }

    public void midSeasonProgression() {

        for (int i = 0; i < teamQBs.size(); i++) {
            teamQBs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamRBs.size(); i++) {
            teamRBs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamWRs.size(); i++) {
            teamWRs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamTEs.size(); i++) {
            teamTEs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamOLs.size(); i++) {
            teamOLs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamKs.size(); i++) {
            teamKs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamDLs.size(); i++) {
            teamDLs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamLBs.size(); i++) {
            teamLBs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamCBs.size(); i++) {
            teamCBs.get(i).midSeasonProgression();
        }

        for (int i = 0; i < teamSs.size(); i++) {
            teamSs.get(i).midSeasonProgression();
        }

        sortPlayers();
    }

    public String midseasonUserProgression() {

        StringBuilder string = new StringBuilder();
        ArrayList<Player> p = new ArrayList<>();
        p = getAllPlayers();

        for (int i = 0; i < getAllPlayers().size(); i++) {
            if (getAllPlayers().get(i).ratImprovement > 0) {
                string.append(p.get(i).position + " " + p.get(i).name + " [" + p.get(i).getYrStr() + "]   " + p.get(i).ratOvr + " (+" + p.get(i).ratImprovement + ")\n");
            }
        }

        return string.toString();
    }

    /**
     * Advance season for players. Removes seniors and develops underclassmen.
     */
    private void advanceSeasonPlayers() {

        ArrayList<Player> players = getAllPlayers();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isRedshirt && players.get(i).year >= 4) {
                players.get(i).year = 5;
            }
        }

        int i = 0;
        while (i < teamQBs.size()) {
            if (teamQBs.get(i).year == 4 && !teamQBs.get(i).isTransfer || (teamQBs.get(i).year == 3 && teamQBs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamQBs.get(i).year == 2 && teamQBs.get(i).wasRedshirt && teamQBs.get(i).ratOvr > NFL_OVR + sophNFL && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamQBs.get(i));
                teamQBs.remove(i);
            } else {
                teamQBs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamRBs.size()) {
            if (teamRBs.get(i).year == 4 && !teamRBs.get(i).isTransfer || (teamRBs.get(i).year == 3 && teamRBs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamRBs.get(i).year == 2 && teamRBs.get(i).wasRedshirt && teamRBs.get(i).ratOvr > NFL_OVR + sophNFL && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamRBs.get(i));
                teamRBs.remove(i);
            } else {
                teamRBs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamWRs.size()) {
            if (teamWRs.get(i).year == 4 && !teamWRs.get(i).isTransfer || (teamWRs.get(i).year == 3 && teamWRs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamWRs.get(i).year == 2 && teamWRs.get(i).wasRedshirt  && teamWRs.get(i).ratOvr > NFL_OVR + sophNFL && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamWRs.get(i));
                teamWRs.remove(i);
            } else {
                teamWRs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamTEs.size()) {
            if (teamTEs.get(i).year == 4 && !teamTEs.get(i).isTransfer || (teamTEs.get(i).year == 3 && teamTEs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE)) {
                playersLeaving.add(teamTEs.get(i));
                teamTEs.remove(i);
            } else {
                teamTEs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamOLs.size()) {
            if (teamOLs.get(i).year == 4 && !teamOLs.get(i).isTransfer || (teamOLs.get(i).year == 3 && teamOLs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE)) {
                playersLeaving.add(teamOLs.get(i));
                teamOLs.remove(i);
            } else {
                teamOLs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamKs.size()) {
            if (teamKs.get(i).year == 4 && !teamKs.get(i).isTransfer) {
                playersLeaving.add(teamKs.get(i));
                teamKs.remove(i);
            } else {
                teamKs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamDLs.size()) {
            if (teamDLs.get(i).year == 4 && !teamDLs.get(i).isTransfer || (teamDLs.get(i).year == 3 && teamDLs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamDLs.get(i).year == 2 && teamDLs.get(i).wasRedshirt  && teamDLs.get(i).ratOvr > NFL_OVR + sophNFL && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamDLs.get(i));
                teamDLs.remove(i);
            } else {
                teamDLs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamLBs.size()) {
            if (teamLBs.get(i).year == 4 && !teamLBs.get(i).isTransfer || (teamLBs.get(i).year == 3 && teamLBs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamLBs.get(i).year == 2 && teamLBs.get(i).wasRedshirt  && teamLBs.get(i).ratOvr > NFL_OVR + sophNFL && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamLBs.get(i));
                teamLBs.remove(i);
            } else {
                teamLBs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamCBs.size()) {
            if (teamCBs.get(i).year == 4 && !teamCBs.get(i).isTransfer || (teamCBs.get(i).year == 3 && teamCBs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE) || (teamCBs.get(i).year == 2 && teamCBs.get(i).wasRedshirt  && teamCBs.get(i).ratOvr > NFL_OVR + 5 && Math.random() < NFL_CHANCE_SOPH)) {
                playersLeaving.add(teamCBs.get(i));
                teamCBs.remove(i);
            } else {
                teamCBs.get(i).advanceSeason();
                i++;
            }
        }

        i = 0;
        while (i < teamSs.size()) {
            if (teamSs.get(i).year == 4 && !teamSs.get(i).isTransfer || (teamSs.get(i).year == 3 && teamSs.get(i).ratOvr > NFL_OVR && Math.random() < NFL_CHANCE)) {
                playersLeaving.add(teamSs.get(i));
                teamSs.remove(i);
            } else {
                teamSs.get(i).advanceSeason();
                i++;
            }
        }

        for (i = 0; i < players.size(); i++) {
            if (players.get(i).isRedshirt && players.get(i).year >= 4) {
                players.get(i).year = 4;
            }
        }

        sortPlayers();
        getPlayersTransferring();
    }

    private void getPlayersTransferring() {

        // PLAYER TRANSFERS
        // Juniors/Seniors - rated 75+ who have not played more than 4 games total and are not starters on teams > 60
        int i;
        dismissalChance = Math.round((100 - HC.get(0).ratDiscipline) / 10);
        int transferChance = 12;
        int transferYear = 2;  //2: Junior 1: Sophomore 0: Freshman

        sortPlayers();
        i = 0;
        while (i < teamQBs.size() && teamQBs.size() > 1) {
            int chance = 1;
            if (Math.abs(teamQBs.get(i).homeState - location) > transferYear) ++chance;
            if (teamQBs.get(i).personality < dismissalRat) ++chance;
            if (teamQBs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamQBs.get(i).year == 4) ++chance;
            if (teamQBs.get(i).personality > gradTransferRat && teamQBs.get(i).year == 4) ++chance;
            if (teamQBs.get(i).year < 2) --chance;
            chance += teamQBs.get(i).troubledTimes;

            if (teamQBs.get(i).year > (transferYear) && !teamQBs.get(i).isMedicalRS && teamQBs.get(i).ratOvr > ratTransfer && teamQBs.get(i) != teamQBs.get(0) && (int) (Math.random() * (transferChance - 2)) < chance && !teamQBs.get(i).isTransfer || teamQBs.get(i).troubledTimes > Math.random() * dismissalChance) {
                teamQBs.get(i).isTransfer = true;
                if (teamQBs.get(i).troubledTimes > 0) {
                    league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed QB " + teamQBs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                    teamQBs.get(i).personality += (int) Math.random() * 20;
                }
                if (teamQBs.get(i).personality > gradTransferRat && teamQBs.get(i).year == 4) {
                    teamQBs.get(i).isTransfer = false;
                    teamQBs.get(i).isGradTransfer = true;
                    league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " QB " + teamQBs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                }
                playersTransferring.add(teamQBs.get(i));
                league.transferQBs.add(teamQBs.get(i));
                teamQBs.remove(i);
            }
            ++i;
        }

        i = 0;
        while (i < teamRBs.size() && teamRBs.size() > 2) {
            int chance = 0;
            if (Math.abs(teamRBs.get(i).homeState - location) > transferYear) ++chance;
            if (teamRBs.get(i).personality < dismissalRat) ++chance;
            if (teamRBs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamRBs.get(i).year == 4) ++chance;
            chance += teamRBs.get(i).troubledTimes;
            if (teamRBs.get(i).personality > gradTransferRat && teamRBs.get(i).year == 4) ++chance;

            if (teamRBs.get(i).year > transferYear && !teamRBs.get(i).isMedicalRS && teamRBs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamRBs.get(i).isTransfer || teamRBs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamRBs.get(i) != teamRBs.get(0) && teamRBs.get(i) != teamRBs.get(1)) {
                    teamRBs.get(i).isTransfer = true;
                    if (teamRBs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed RB " + teamRBs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamRBs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamRBs.get(i).personality > gradTransferRat && teamRBs.get(i).year == 4) {
                        teamRBs.get(i).isTransfer = false;
                        teamRBs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " RB " + teamRBs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamRBs.get(i));
                    league.transferRBs.add(teamRBs.get(i));
                    teamRBs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamWRs.size() && teamWRs.size() > 3) {
            int chance = 0;
            if (Math.abs(teamWRs.get(i).homeState - location) > transferYear) ++chance;
            if (teamWRs.get(i).personality < dismissalRat) ++chance;
            if (teamWRs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamWRs.get(i).year == 4) ++chance;
            chance += teamWRs.get(i).troubledTimes;
            if (teamWRs.get(i).personality > gradTransferRat && teamWRs.get(i).year == 4) ++chance;

            if (teamWRs.get(i).year > transferYear && !teamWRs.get(i).isMedicalRS && teamWRs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamWRs.get(i).isTransfer || teamWRs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamWRs.get(i) != teamWRs.get(0) && teamWRs.get(i) != teamWRs.get(1) && teamWRs.get(i) != teamWRs.get(2)) {
                    teamWRs.get(i).isTransfer = true;
                    if (teamWRs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed WR " + teamWRs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamWRs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamWRs.get(i).personality > gradTransferRat && teamWRs.get(i).year == 4) {
                        teamWRs.get(i).isTransfer = false;
                        teamWRs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " WR " + teamWRs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamWRs.get(i));
                    league.transferWRs.add(teamWRs.get(i));
                    teamWRs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamTEs.size() && teamTEs.size() > 1) {
            int chance = 0;
            if (Math.abs(teamTEs.get(i).homeState - location) > transferYear) ++chance;
            if (teamTEs.get(i).personality < dismissalRat) ++chance;
            if (teamTEs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamTEs.get(i).year == 4) ++chance;
            chance += teamTEs.get(i).troubledTimes;
            if (teamTEs.get(i).personality > gradTransferRat && teamTEs.get(i).year == 4) ++chance;

            if (teamTEs.get(i).year > transferYear && !teamTEs.get(i).isMedicalRS && teamTEs.get(i).ratOvr > ratTransfer && teamTEs.get(i) != teamTEs.get(0) && (int) (Math.random() * transferChance) < chance && !teamTEs.get(i).isTransfer || teamTEs.get(i).troubledTimes > Math.random() * dismissalChance) {
                teamTEs.get(i).isTransfer = true;
                if (teamTEs.get(i).troubledTimes > 0) {
                    league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed TE " + teamTEs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                    teamTEs.get(i).personality += (int) Math.random() * 15;
                }
                if (teamTEs.get(i).personality > gradTransferRat && teamTEs.get(i).year == 4) {
                    teamTEs.get(i).isTransfer = false;
                    teamTEs.get(i).isGradTransfer = true;
                    league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " TE " + teamTEs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                }
                playersTransferring.add(teamTEs.get(i));
                league.transferTEs.add(teamTEs.get(i));
                teamTEs.remove(i);
            }
            ++i;
        }

        i = 0;
        while (i < teamOLs.size() && teamOLs.size() > 5) {
            int chance = 0;
            if (Math.abs(teamOLs.get(i).homeState - location) > transferYear) ++chance;
            if (teamOLs.get(i).personality < dismissalRat) ++chance;
            if (teamOLs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamOLs.get(i).year == 4) ++chance;
            chance += teamOLs.get(i).troubledTimes;
            if (teamOLs.get(i).personality > gradTransferRat && teamOLs.get(i).year == 4) ++chance;

            if (teamOLs.get(i).year > transferYear && !teamOLs.get(i).isMedicalRS && teamOLs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamOLs.get(i).isTransfer || teamOLs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamOLs.get(i) != teamOLs.get(0) && teamOLs.get(i) != teamOLs.get(1) && teamOLs.get(i) != teamOLs.get(2) && teamOLs.get(i) != teamOLs.get(3) && teamOLs.get(i) != teamOLs.get(4)) {
                    teamOLs.get(i).isTransfer = true;
                    if (teamOLs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed OL " + teamOLs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamOLs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamOLs.get(i).personality > gradTransferRat && teamOLs.get(i).year == 4) {
                        teamOLs.get(i).isTransfer = false;
                        teamOLs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " OL " + teamOLs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamOLs.get(i));
                    league.transferOLs.add(teamOLs.get(i));
                    teamOLs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamKs.size() && teamKs.size() > 1) {
            int chance = 0;
            if (Math.abs(teamKs.get(i).homeState - location) > transferYear) ++chance;
            if (teamKs.get(i).personality < dismissalRat) ++chance;
            if (teamKs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamKs.get(i).year == 4) ++chance;
            chance += teamKs.get(i).troubledTimes;
            if (teamKs.get(i).personality > gradTransferRat && teamKs.get(i).year == 4) ++chance;

            if (teamKs.get(i).year > transferYear && !teamKs.get(i).isMedicalRS && teamKs.get(i).ratOvr > ratTransfer && teamKs.get(i) != teamKs.get(0) && (int) (Math.random() * (transferChance + 2)) < chance && !teamKs.get(i).isTransfer || teamKs.get(i).troubledTimes > Math.random() * dismissalChance) {
                teamKs.get(i).isTransfer = true;
                if (teamKs.get(i).troubledTimes > 0) {
                    league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed K " + teamKs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                    teamKs.get(i).personality += (int) Math.random() * 15;
                }
                if (teamKs.get(i).personality > gradTransferRat && teamKs.get(i).year == 4) {
                    teamKs.get(i).isTransfer = false;
                    teamKs.get(i).isGradTransfer = true;
                    league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " K " + teamKs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                }
                playersTransferring.add(teamKs.get(i));
                league.transferKs.add(teamKs.get(i));
                teamKs.remove(i);
            }
            ++i;
        }

        i = 0;
        while (i < teamDLs.size() && teamDLs.size() > 4) {
            int chance = 0;
            if (Math.abs(teamDLs.get(i).homeState - location) > transferYear) ++chance;
            if (teamDLs.get(i).personality < dismissalRat) ++chance;
            if (teamDLs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamDLs.get(i).year == 4) ++chance;
            chance += teamDLs.get(i).troubledTimes;
            if (teamDLs.get(i).personality > gradTransferRat && teamDLs.get(i).year == 4) ++chance;

            if (teamDLs.get(i).year > transferYear && !teamDLs.get(i).isMedicalRS && teamDLs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamDLs.get(i).isTransfer || teamDLs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamDLs.get(i) != teamDLs.get(0) && teamDLs.get(i) != teamDLs.get(1) && teamDLs.get(i) != teamDLs.get(2) && teamDLs.get(i) != teamDLs.get(3)) {
                    teamDLs.get(i).isTransfer = true;
                    if (teamDLs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed DL " + teamDLs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamDLs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamDLs.get(i).personality > gradTransferRat && teamDLs.get(i).year == 4) {
                        teamDLs.get(i).isTransfer = false;
                        teamDLs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " DL " + teamDLs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamDLs.get(i));
                    league.transferDLs.add(teamDLs.get(i));
                    teamDLs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamLBs.size() && teamLBs.size() > 3) {
            int chance = 0;
            if (Math.abs(teamLBs.get(i).homeState - location) > transferYear) ++chance;
            if (teamLBs.get(i).personality < dismissalRat) ++chance;
            if (teamLBs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamLBs.get(i).year == 4) ++chance;
            chance += teamLBs.get(i).troubledTimes;
            if (teamLBs.get(i).personality > gradTransferRat && teamLBs.get(i).year == 4) ++chance;

            if (teamLBs.get(i).year > transferYear && !teamLBs.get(i).isMedicalRS && teamLBs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamLBs.get(i).isTransfer || teamLBs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamLBs.get(i) != teamLBs.get(0) && teamLBs.get(i) != teamLBs.get(1) && teamLBs.get(i) != teamLBs.get(2)) {
                    teamLBs.get(i).isTransfer = true;
                    if (teamLBs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed LB " + teamLBs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamLBs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamLBs.get(i).personality > gradTransferRat && teamLBs.get(i).year == 4) {
                        teamLBs.get(i).isTransfer = false;
                        teamLBs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " LB " + teamLBs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamLBs.get(i));
                    league.transferLBs.add(teamLBs.get(i));
                    teamLBs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamCBs.size() && teamCBs.size() > 3) {
            int chance = 0;
            if (Math.abs(teamCBs.get(i).homeState - location) > transferYear) ++chance;
            if (teamCBs.get(i).personality < dismissalRat) ++chance;
            if (teamCBs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamCBs.get(i).year == 4) ++chance;
            chance += teamCBs.get(i).troubledTimes;
            if (teamCBs.get(i).personality > gradTransferRat && teamCBs.get(i).year == 4) ++chance;

            if (teamCBs.get(i).year > transferYear && !teamCBs.get(i).isMedicalRS && teamCBs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamCBs.get(i).isTransfer || teamCBs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamCBs.get(i) != teamCBs.get(0) && teamCBs.get(i) != teamCBs.get(1) && teamCBs.get(i) != teamCBs.get(2) && teamCBs.get(i) != teamCBs.get(3)) {
                    teamCBs.get(i).isTransfer = true;
                    if (teamCBs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed CB " + teamCBs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamCBs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamCBs.get(i).personality > gradTransferRat && teamCBs.get(i).year == 4) {
                        teamCBs.get(i).isTransfer = false;
                        teamCBs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " CB " + teamCBs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamCBs.get(i));
                    league.transferCBs.add(teamCBs.get(i));
                    teamCBs.remove(i);
                }
            }
            ++i;
        }

        i = 0;
        while (i < teamSs.size() && teamSs.size() > 1) {
            int chance = 0;
            if (Math.abs(teamSs.get(i).homeState - location) > transferYear) ++chance;
            if (teamSs.get(i).personality < dismissalRat) ++chance;
            if (teamSs.get(i).gamesStarted < gradTransferMinGames) ++chance;
            if (teamSs.get(i).year == 4) ++chance;
            chance += teamSs.get(i).troubledTimes;
            if (teamSs.get(i).personality > gradTransferRat && teamSs.get(i).year == 4) ++chance;

            if (teamSs.get(i).year > transferYear && !teamSs.get(i).isMedicalRS && teamSs.get(i).ratOvr > ratTransfer && (int) (Math.random() * transferChance) < chance && !teamSs.get(i).isTransfer || teamSs.get(i).troubledTimes > Math.random() * dismissalChance) {
                if (teamSs.get(i) != teamSs.get(0) && teamSs.get(i) != teamSs.get(1)) {
                    teamSs.get(i).isTransfer = true;
                    if (teamSs.get(i).troubledTimes > 0) {
                        league.newsStories.get(league.currentWeek + 1).add(name + " Player Dismissed>Following several incidents, " + name + " has dismissed S " + teamSs.get(i).name + ". The player will have to sit out a year if he chooses to transfer to a new program.");
                        teamSs.get(i).personality += (int) Math.random() * 15;
                    }
                    if (teamSs.get(i).personality > gradTransferRat && teamSs.get(i).year == 4) {
                        teamSs.get(i).isTransfer = false;
                        teamSs.get(i).isGradTransfer = true;
                        league.newsStories.get(league.currentWeek + 1).add(name + " Grad Transfer>" + name + " S " + teamSs.get(i).name + " has announced that he will be leaving the school to attend Grad school elsewhere. He will start his search for a new school immediately.");
                    }
                    playersTransferring.add(teamSs.get(i));
                    league.transferSs.add(teamSs.get(i));
                    teamSs.remove(i);
                }
            }
            ++i;
        }

        ArrayList<Player> players = getAllPlayers();

        for (int p = 0; p < players.size(); p++) {
            if (players.get(p).year >= 4) {
                players.get(p).year = 4;
            }
        }
    }


    //GET FINAL RECRUITING NEEDS PRIOR TO RECRUITING
    public void CPUrecruiting() {

        int numTransfers = 0;
        if (!userControlled) {
            for (int i = 0; i < teamQBs.size(); ++i) {
                if (teamQBs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int qb = minQBs - (teamQBs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamRBs.size(); ++i) {
                if (teamRBs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int rb = minRBs - (teamRBs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamWRs.size(); ++i) {
                if (teamWRs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int wr = minWRs - (teamWRs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamTEs.size(); ++i) {
                if (teamTEs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int te = minTEs - (teamTEs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamOLs.size(); ++i) {
                if (teamOLs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int ol = minOLs - (teamOLs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamKs.size(); ++i) {
                if (teamKs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int k = minKs - (teamKs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamDLs.size(); ++i) {
                if (teamDLs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int dl = minDLs - (teamDLs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamLBs.size(); ++i) {
                if (teamLBs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int lb = minLBs - (teamLBs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamCBs.size(); ++i) {
                if (teamCBs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int cb = minCBs - (teamCBs.size() - numTransfers);

            numTransfers = 0;
            for (int i = 0; i < teamSs.size(); ++i) {
                if (teamSs.get(i).isTransfer) {
                    numTransfers++;
                }
            }
            int s = minSs - (teamSs.size() - numTransfers);

            int rosterSize = getTeamSize() + qb + rb + wr + te + ol + dl + lb + cb + s;

            for (int i = rosterSize; i < minPlayers; i++) {
                int x = (int) (Math.random() * 9) + 1;
                if (x == 1) qb++;
                if (x == 2) rb++;
                if (x == 3) wr++;
                if (x == 4) te++;
                if (x == 5) ol++;
                if (x == 6) dl++;
                if (x == 7) lb++;
                if (x == 8) cb++;
                if (x == 9) s++;
            }

            recruitPlayersFreshman(qb, rb, wr, te, ol, k, dl, lb, cb, s);
        }
    }

    private int getTeamSize() {
        int size = teamQBs.size() + teamRBs.size() + teamWRs.size() + teamTEs.size() + teamOLs.size() + teamKs.size() + teamDLs.size() + teamLBs.size() + teamCBs.size() + teamSs.size();
        return size;
    }

    private int getRecruitLevel() {
        float level = (league.teamList.size() - rankTeamPrestige) / (float)(league.teamList.size()/10.5);
        for (int i = 0; i < league.conferences.size(); ++i) {
            league.conferences.get(i).updateConfPrestige();
        }
        float confBias = confPrestige - league.getAverageConfPrestige();
        if (level < 4 && confBias < 0) level = 4;
        if (level > 7 && confBias < 0) level = 5;
        if (level < 6 && confBias > 0) level = 6;
        return Math.round(level);
    }

    public int getUserRecruitBudget() {
        float level = (league.teamList.size() - rankTeamPrestige) / (float)(league.teamList.size()/11);
        for (int i = 0; i < league.conferences.size(); ++i) {
            league.conferences.get(i).updateConfPrestige();
        }
        float confBias = confPrestige - league.getAverageConfPrestige();
        if (level < 4 && confBias < 0) level = 4;
        if (level > 7 && confBias < 0) level = 6;
        if (level < 6 && confBias > 0) level = 6;

        return (int) Math.round(level * 8.5);
    }

    public int getUserRecruitStars() {
        float level = (league.teamList.size() - rankTeamPrestige) / (float)(league.teamList.size()/11);
        for (int i = 0; i < league.conferences.size(); ++i) {
            league.conferences.get(i).updateConfPrestige();
        }
        float confBias = confPrestige - league.getAverageConfPrestige();
        if (level < 4 && confBias < 0) level = 4;
        if (level > 7 && confBias < 0) level = 6;
        if (level < 6 && confBias > 0) level = 6;
        return Math.round(level);
    }

    /**
     * Recruit freshmen at each position.
     * This is used after each season.
     *
     * @param qbNeeds
     * @param rbNeeds
     * @param wrNeeds
     * @param kNeeds
     * @param olNeeds
     * @param sNeeds
     * @param cbNeeds
     * @param dlNeeds
     * @param teNeeds
     * @param lbNeeds
     */
    private void recruitPlayersFreshman(int qbNeeds, int rbNeeds, int wrNeeds, int teNeeds, int olNeeds, int kNeeds, int dlNeeds, int lbNeeds, int cbNeeds, int sNeeds) {
        //make team
        int stars;
        int recruitChance;
        if (HC.size() > 0) {
            recruitChance = HC.get(0).ratTalent - 33;
        } else {
            recruitChance = teamPrestige - 50;
        }

        for (int i = 0; i < qbNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;
            //make QBs
            teamQBs.add(new PlayerQB(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < rbNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make RBs
            teamRBs.add(new PlayerRB(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < wrNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make WRs
            teamWRs.add(new PlayerWR(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < teNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make TEs
            teamTEs.add(new PlayerTE(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < olNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make OLs
            teamOLs.add(new PlayerOL(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < kNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make Ks
            teamKs.add(new PlayerK(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < dlNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make DLs
            teamDLs.add(new PlayerDL(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < lbNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;
            //make LBs
            teamLBs.add(new PlayerLB(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < cbNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make CBs
            teamCBs.add(new PlayerCB(league.getRandName(), 1, stars, this));
        }

        for (int i = 0; i < sNeeds; ++i) {
            stars = getRecruitLevel();
            if (stars < minRecruitStar) stars = minRecruitStar;
            if (recruitChance * Math.random() > Math.random() * 50) {
                stars += Math.random() * (maxStarRating - stars);
            } else {
                stars -= Math.random() * (stars);
            }
            if (stars > 10) stars = 10;

            //make Ss
            teamSs.add(new PlayerS(league.getRandName(), 1, stars, this));
        }

        //done making players, sort
        sortPlayers();
        recruitWalkOns();
    }

    /**
     * Recruits walk ons at each needed position.
     * This is used by user teams if there is a dearth at any position.
     */
    public void recruitWalkOns() {
        int star;
        walkon = true;


        //QUARTERBACKS
        int needs = minQBs - teamQBs.size();
        for (int i = 0; i < teamQBs.size(); ++i) {
            if (teamQBs.get(i).isRedshirt || teamQBs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamQBs.add(new PlayerQB(league.getRandName(), 1, star, this, walkon));
        }

        //RUNNING BACKS
        needs = minRBs - teamRBs.size();
        for (int i = 0; i < teamRBs.size(); ++i) {
            if (teamRBs.get(i).isRedshirt || teamRBs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamRBs.add(new PlayerRB(league.getRandName(), 1, star, this, walkon));
        }

        //WIDE RECEIVERS
        needs = minWRs - teamWRs.size();
        for (int i = 0; i < teamWRs.size(); ++i) {
            if (teamWRs.get(i).isRedshirt || teamWRs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamWRs.add(new PlayerWR(league.getRandName(), 1, star, this, walkon));
        }

        //TIGHT ENDS
        needs = minTEs - teamTEs.size();
        for (int i = 0; i < teamTEs.size(); ++i) {
            if (teamTEs.get(i).isRedshirt || teamTEs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamTEs.add(new PlayerTE(league.getRandName(), 1, star, this, walkon));
        }

        //OFFENSIVE LINE
        needs = minOLs - teamOLs.size();
        for (int i = 0; i < teamOLs.size(); ++i) {
            if (teamOLs.get(i).isRedshirt || teamOLs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamOLs.add(new PlayerOL(league.getRandName(), 1, star, this, walkon));
        }

        //KICKERS
        needs = minKs - teamKs.size();
        for (int i = 0; i < teamKs.size(); ++i) {
            if (teamKs.get(i).isRedshirt || teamKs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamKs.add(new PlayerK(league.getRandName(), 1, star, this, walkon));
        }

        //DEFENSIVE LINE
        needs = minDLs - teamDLs.size();
        for (int i = 0; i < teamDLs.size(); ++i) {
            if (teamDLs.get(i).isRedshirt || teamDLs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamDLs.add(new PlayerDL(league.getRandName(), 1, star, this, walkon));
        }

        //LINEBACKERS
        needs = minLBs - teamLBs.size();
        for (int i = 0; i < teamLBs.size(); ++i) {
            if (teamLBs.get(i).isRedshirt || teamLBs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamLBs.add(new PlayerLB(league.getRandName(), 1, star, this, walkon));
        }

        //CORNERBACKS
        needs = minCBs - teamCBs.size();
        for (int i = 0; i < teamCBs.size(); ++i) {
            if (teamCBs.get(i).isRedshirt || teamCBs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamCBs.add(new PlayerCB(league.getRandName(), 1, star, this, walkon));
        }

        //SAFETY
        needs = minSs - teamSs.size();
        for (int i = 0; i < teamSs.size(); ++i) {
            if (teamSs.get(i).isRedshirt || teamSs.get(i).isTransfer) {
                needs++;
            }
        }
        for (int i = 0; i < needs; ++i) {
            star = (int) Math.random() * 2 + 1;
            teamSs.add(new PlayerS(league.getRandName(), 1, star, this, walkon));
        }

        //done making players, sort them
        sortPlayers();
    }

    private PlayerQB[] getQBRecruits(int rating) {
        int adjNumRecruits = numRecruits;
        PlayerQB[] recruits = new PlayerQB[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerQB(league.getRandName(), 1, stars, this);
        }

        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerQB(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerQB(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerRB[] getRBRecruits(int rating) {
        int adjNumRecruits = numRecruits;
        PlayerRB[] recruits = new PlayerRB[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerRB(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerRB(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerRB(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerWR[] getWRRecruits(int rating) {
        int adjNumRecruits = 2 * numRecruits;
        PlayerWR[] recruits = new PlayerWR[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerWR(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerWR(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerWR(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerTE[] getTERecruits(int rating) {
        int adjNumRecruits = numRecruits;
        PlayerTE[] recruits = new PlayerTE[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerTE(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerTE(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerTE(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerOL[] getOLRecruits(int rating) {
        int adjNumRecruits = 2 * numRecruits;
        PlayerOL[] recruits = new PlayerOL[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerOL(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerOL(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerOL(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerK[] getKRecruits(int rating) {
        int adjNumRecruits = numRecruits;
        PlayerK[] recruits = new PlayerK[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerK(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerK(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerK(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerDL[] getDLRecruits(int rating) {
        int adjNumRecruits = 2 * numRecruits;
        PlayerDL[] recruits = new PlayerDL[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerDL(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerDL(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerDL(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerLB[] getLBRecruits(int rating) {
        int adjNumRecruits = 2 * numRecruits;
        PlayerLB[] recruits = new PlayerLB[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerLB(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerLB(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerLB(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerCB[] getCBRecruits(int rating) {
        int adjNumRecruits = 2 * numRecruits;
        PlayerCB[] recruits = new PlayerCB[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerCB(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerCB(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerCB(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }

    private PlayerS[] getSRecruits(int rating) {
        int adjNumRecruits = numRecruits;
        PlayerS[] recruits = new PlayerS[adjNumRecruits + 2 * recruitExtras];
        int stars;

        for (int i = 0; i < adjNumRecruits; ++i) {
            stars = (int) (rating * (float) (adjNumRecruits - i / 2) / adjNumRecruits);
            recruits[i] = new PlayerS(league.getRandName(), 1, stars, this);
        }


        int r = adjNumRecruits;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerS(league.getRandName(), 1, 2, this);
        }

        r = adjNumRecruits + recruitExtras;
        for (int i = 0; i < recruitExtras; ++i) {
            recruits[r + i] = new PlayerS(league.getRandName(), 1, 1, this);
        }

        Arrays.sort(recruits, new CompRecruit());
        return recruits;
    }


    /**
     * Recruit all players given in a string
     */
    public void recruitPlayersFromStr(String playersStr) {
        String[] players = playersStr.split("%\n");
        String currLine = players[0];
        int i = 0;
        while (!currLine.equals("END_RECRUITS")) {
            loadPlayerSaveData(currLine, false);
            currLine = players[++i];
        }

        recruitWalkOns();

    }

    /**
     * Gets the recruiting class strength.
     * Adds up all the ovrs of freshmen
     *
     * @return class strength as a number
     */
    public float getRecruitingClassRat() {
        double classStrength = 0;
        int numFreshman = 0;
        int numFreshmanScoring = 0;
        ArrayList<Player> allPlayers = getAllPlayers();
        for (Player p : allPlayers) {
            if(p.year == 1 && !p.wasRedshirt) numFreshman++;
            if (p.year == 1 && !p.wasRedshirt && p.ratOvr > two) {
                int pRat;
                if (p.ratOvr > five) pRat = 150;
                else if (p.ratOvr > four) pRat = 120;
                else if (p.ratOvr > three) pRat = 75;
                else if (p.ratOvr > two) pRat = 30;
                else pRat = 5;

                classStrength += pRat;
                numFreshmanScoring++;
            }
        }
        if (numFreshman > 0)
            return (float) (classStrength/(numFreshmanScoring + (.33*(numFreshman-numFreshmanScoring))));
        else return 0;
    }

    public void getLeagueFreshman() {
        ArrayList<Player> teamPlayers = getAllPlayers();
        for (int p = 0; p < teamPlayers.size(); ++p) {
            if (teamPlayers.get(p).year == 1 && !teamPlayers.get(p).isRedshirt) {
                league.freshmen.add(teamPlayers.get(p));
            }
            if (teamPlayers.get(p).year == 1 && teamPlayers.get(p).isRedshirt) {
                league.redshirts.add(teamPlayers.get(p));
            }
        }
    }

    public String getTopRecruit() {
        String topRecruit="";

        ArrayList<Player> teamPlayers = getAllPlayers();
        ArrayList<Player> teamRecruits = new ArrayList<>();

        for (int p = 0; p < teamPlayers.size(); ++p) {
            if (teamPlayers.get(p).year == 1 && !teamPlayers.get(p).wasRedshirt && !teamPlayers.get(p).position.equals("K")) {
                teamRecruits.add(teamPlayers.get(p));
            }
        }
        Collections.sort(teamRecruits, new CompPlayer());

        if(teamRecruits.size() > 0) topRecruit = teamRecruits.get(0).position + " " + teamRecruits.get(0).name + " (" + teamRecruits.get(0).ratOvr + ")";
        else topRecruit = "No Recruits";

        return topRecruit;
    }


    //Set Redshirts
    public void setRedshirts(ArrayList<Player> redshirts, ArrayList<Player> unredshirt, int position) {
        switch (position) {
            case 0:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamQBs, new CompPlayer());
                break;
            case 1:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamRBs, new CompPlayer());
                break;
            case 2:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamWRs, new CompPlayer());
                break;
            case 3:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamTEs, new CompPlayer());
                break;
            case 4:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamOLs, new CompPlayer());
                break;
            case 5:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamKs, new CompPlayer());
                break;
            case 6:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamDLs, new CompPlayer());
                break;
            case 7:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamLBs, new CompPlayer());
                break;
            case 8:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamCBs, new CompPlayer());
                break;
            case 9:
                for (Player p : redshirts) {
                    p.isRedshirt = true;
                }
                for (Player p : unredshirt) {
                    p.isRedshirt = false;
                }
                Collections.sort(teamSs, new CompPlayer());
                break;
        }

        // Set ranks so that Off/Def Talent rankings are updated
        if (league.currentWeek < league.regSeasonWeeks+2) league.setTeamRanks();
    }

    public int countRedshirts() {
        int count = 0;
        for (int i = 0; i < getAllPlayers().size(); ++i) {
            if (getAllPlayers().get(i).isRedshirt && !getAllPlayers().get(i).isTransfer) count++;
        }
        return count;
    }

    //Set Redshirts
    public int getActivePlayers(int position) {
        int numPlayers = 0;
        switch (position) {
            case 0:
                for (Player p : teamQBs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamQBs.size() - numPlayers;
            case 1:
                for (Player p : teamRBs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamRBs.size() - numPlayers;
            case 2:
                for (Player p : teamWRs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamWRs.size() - numPlayers;

            case 3:
                for (Player p : teamTEs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamTEs.size() - numPlayers;

            case 4:
                for (Player p : teamOLs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamOLs.size() - numPlayers;

            case 5:
                for (Player p : teamKs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamKs.size() - numPlayers;

            case 6:
                for (Player p : teamDLs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamDLs.size() - numPlayers;

            case 7:
                for (Player p : teamLBs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamLBs.size() - numPlayers;

            case 8:
                for (Player p : teamCBs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamCBs.size() - numPlayers;

            case 9:
                for (Player p : teamSs) {
                    if (p.isRedshirt) numPlayers++;
                }
                return teamSs.size() - numPlayers;

        }
        return numPlayers;
    }


    /**
     * Updates team history.
     */
    public void updateTeamHistory() {
        String histYear;
        if (teamPrestige > teamPrestigeStart)
            histYear = league.getYear() + ": #" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ") "
                    + confChampion + " " + semiFinalWL + natChampWL + " Prs: " + teamPrestige + " (+" + (teamPrestige - teamPrestigeStart) + ")";
        else
            histYear = league.getYear() + ": #" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ") "
                    + confChampion + " " + semiFinalWL + natChampWL + " Prs: " + teamPrestige + " (" + (teamPrestige - teamPrestigeStart) + ")";
        histYear += ">Head Coach: " + HC.get(0).name;
        for (int i = league.regSeasonWeeks-1; i < gameSchedule.size(); ++i) {
            Game g = gameSchedule.get(i);
            histYear += ">" + g.gameName + ": ";
            String[] gameSum = getGameSummaryStr(i);
            histYear += gameSum[1] + " " + gameSum[2];
        }

        teamHistory.add(histYear);
    }

    public void updateCoachHistory() {
        String histYear;
        if (teamPrestige > teamPrestigeStart)
            histYear = league.getYear() + ": #" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ") "
                    + confChampion + " " + semiFinalWL + natChampWL + " Prs: " + teamPrestige + " (+" + (teamPrestige - teamPrestigeStart) + ")";
        else
            histYear = league.getYear() + ": #" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ") "
                    + confChampion + " " + semiFinalWL + natChampWL + " Prs: " + teamPrestige + " (" + (teamPrestige - teamPrestigeStart) + ")";

        for (int i = league.regSeasonWeeks-1; i < gameSchedule.size(); ++i) {
            Game g = gameSchedule.get(i);
            histYear += ">" + g.gameName + ": ";
            String[] gameSum = getGameSummaryStr(i);
            histYear += gameSum[1] + " " + gameSum[2];
        }
        if (!HC.isEmpty()) {
            HC.get(0).history.add(histYear);
        }
    }

    /**
     * Gets the team history as a String array
     *
     * @return team history
     */
    public String[] getTeamHistoryList() {
        String[] hist = new String[teamHistory.size() + 8];
        hist[0] = "Location: " + league.getRegion(location);
        hist[1] = "Prestige: " + teamPrestige + " ("+ getRankStr(rankTeamPrestige) + ")";
        hist[2] = "Overall W-L: " + totalWins + "-" + totalLosses + " (" + df2.format(getWinPCT(totalWins, totalLosses)) + "%)";
        hist[3] = "Conf Champ Record: " + totalCCs + "-" + totalCCLosses + " (" + df2.format(getWinPCT(totalCCs, totalCCLosses)) + "%)";
        hist[4] = "Bowl Game Record: " + totalBowls + "-" + totalBowlLosses + " (" + df2.format(getWinPCT(totalBowls, totalBowlLosses)) + "%)";
        hist[5] = "National Champ Record: " + totalNCs + "-" + totalNCLosses + " (" + df2.format(getWinPCT(totalNCs, totalNCLosses)) + "%)";
        hist[6] = "Hall of Famers: " + HoFCount;
        hist[7] = " ";
        for (int i = 0; i < teamHistory.size(); ++i) {
            hist[i + 8] = teamHistory.get(i);
        }
        return hist;
    }

    private float getWinPCT(int w, int l) {
        if(w+l < 1) return 0;
        else {
            return (float)w/(w+l)*100;
        }
    }
    //DISCIPLINE SYSTEM

    public void disciplineSuccess() {
        HC.get(0).ratDiscipline += (int) (Math.random() * 3);
        teamDisciplineScore += (int) (Math.random() * 3);
        if (teamDisciplineScore > 100) teamDisciplineScore = 100;
    }

    //Disipline the most likely player that committed offense based on disicpline rating
    public void disciplineFailure() {
        getLowDisciplinePlayers(65);

        if(!userControlled) {
            int random = (int) (Math.random() * playersDis.size());
            if (random > playersDis.size()-1) random = playersDis.size()-1;
            Player player = playersDis.get(random);

            int duration = (int) (Math.random() * (66 - player.personality) / 2);
            if (duration <= 0) duration = 1;
            int issueNo = duration-1;
            if(issueNo > issue.length) issueNo = issue.length;
            String description = issue[issueNo];

            int choice = HC.get(0).ratDiscipline - (int)(80*Math.random());
            if(choice > 15) {
                choice = 1;
                duration = duration*2;
            }
            else if (choice > -20) {
                choice = 2;
            }
            else choice = 3;

            disciplineAction(player, description, duration, choice);

        } else {
            disciplineAction = true;
        }
    }

    //Apply Suspensions to Player
    public void suspendPlayerSetup(MainActivity main) {
        if(playersDis.size() <= 0) getLowDisciplinePlayers(75);
        int random = (int) (Math.random() * playersDis.size());
        if (random > playersDis.size() - 1) random = playersDis.size() - 1;
        Player player = playersDis.get(random);

        int duration = (int) (Math.random() * (65 - player.personality) / 2);
        if (duration <= 0) duration = 1;
        int duration2 = duration * 2;
        int issueNo = duration-1;
        if(issueNo > issue.length) issueNo = issue.length;
        String description = issue[issueNo];

        main.disciplineAction(player, description, duration, duration2);
    }

    public void disciplineAction(Player player, String description, int duration, int choice) {
        //choice = 1 - good discipline 2- medium 3- ignore
        int penalty = (65-player.personality)/2;

        if(choice == 1) {
            HC.get(0).ratDiscipline -= penalty*.75;
            disciplinePts --;
            teamDisciplineScore -= penalty;
            teamBudget -= (penalty * 100);
        } else if(choice == 2) {
            HC.get(0).ratDiscipline -= penalty*1.25;
            disciplinePts --;
            teamDisciplineScore -= penalty*1.45;
            teamBudget -= (penalty * 175);
        } else {
            player.troubledTimes++;
            HC.get(0).ratDiscipline -= penalty*1.55;
            disciplinePts --;
            teamDisciplineScore -= penalty*1.65;
            teamBudget -= (penalty * 250);
        }

        if(choice == 1 || choice == 2) {
            player.isSuspended = true;
            player.ratPot -= duration * 5;
            player.ratFootIQ -= duration * 3;
            player.weeksSuspended = duration;
            player.troubledTimes++;
            if (player.ratOvr > 77) {
                player.team.league.newsStories.get(player.team.league.currentWeek + 1).add("Player Suspended!>" + player.team.name + "'s " + player.position + ", " + player.name + " was suspended from the team today. The team cited the reason as: " + description
                        + ". The player will be suspended for " + duration + " weeks.");
            }

            if(userControlled) {
                suspensionNews = "Player Suspended!\n\n" + player.team.name + "'s star " + player.position + ", " + player.name + " was suspended from the team today. The team cited the reason as: " + description
                        + ". The player will be suspended for " + duration + " weeks.";
                suspension = true;
            }
            sortPlayers();
        }
    }

    private void getLowDisciplinePlayers(int minRating) {
        playersDis = new ArrayList<>();
        checkSuspensionPosition(teamQBs, startersQB + subQB, minRating);
        checkSuspensionPosition(teamRBs, startersRB + subRB, minRating);
        checkSuspensionPosition(teamWRs, startersWR + subWR, minRating);
        checkSuspensionPosition(teamTEs, startersTE + subTE, minRating);
        checkSuspensionPosition(teamOLs, startersOL + subOL, minRating);
        checkSuspensionPosition(teamKs, startersK + subK, minRating);
        checkSuspensionPosition(teamDLs, startersDL + subDL, minRating);
        checkSuspensionPosition(teamLBs, startersLB + subLB, minRating);
        checkSuspensionPosition(teamCBs, startersCB + subCB, minRating);
        checkSuspensionPosition(teamSs, startersS + subS, minRating);
        if(playersDis.size() < 1) getLowDisciplinePlayers(85);
    }

    private void checkSuspensionPosition(ArrayList<? extends Player> players, int numStarters, int minRating) {
        int numInjured = 0;

        for (Player p : players) {
            if (p.injury != null && !p.isSuspended) {
                numInjured++;
            }
        }

        // Only suspend if there are people left
        if (numInjured < numStarters) {
            for (int i = 0; i < numStarters; ++i) {
                Player p = players.get(i);
                if (p.personality < minRating) {
                    playersDis.add(p);
                }
            }
        }
    }

    public void updateSuspensions() {
        ArrayList<Player> allPlayers = getAllPlayers();
        for (int i = 0; i < allPlayers.size(); ++i) {
            if (allPlayers.get(i).isSuspended) {
                allPlayers.get(i).weeksSuspended--;
                if (allPlayers.get(i).weeksSuspended < 1) {
                    allPlayers.get(i).isSuspended = false;
                    sortPlayers();
                }
            }
        }
    }

    /**
     * May injure players.
     * Guaranteed not to injure more than the amount of starters for each position.
     */
    public void checkForInjury() {
        playersRecovered = new ArrayList<>();
        checkInjuryPosition(teamQBs, startersQB + subQB);
        checkInjuryPosition(teamRBs, startersRB + subRB);
        checkInjuryPosition(teamWRs, startersWR + subWR);
        checkInjuryPosition(teamTEs, startersTE + subTE);
        checkInjuryPosition(teamOLs, startersOL + subOL);
        checkInjuryPosition(teamKs, startersK + subK);
        checkInjuryPosition(teamDLs, startersDL + subDL);
        checkInjuryPosition(teamLBs, startersLB + subLB);
        checkInjuryPosition(teamCBs, startersCB + subCB);
        checkInjuryPosition(teamSs, startersS + subS);
    }

    private void checkInjuryPosition(ArrayList<? extends Player> players, int numStarters) {
        int numInjured = 0;

        for (Player p : players) {
            if (p.injury != null && !p.isSuspended && !p.isTransfer) {
                p.injury.advanceGame();
                numInjured++;
                if (p.injury == null) {
                    playersRecovered.add(p);
                    playersInjured.remove(p);
                }
            }
        }

        // Only injure if there are people left to injure
        if (numInjured < numStarters) {
            for (int i = 0; i < numStarters; ++i) {
                Player p = players.get(i);
                if (Math.random() < Math.pow(1 - (double) p.ratDur / 95, 3) && numInjured < numStarters) {
                    // injury!
                    p.injury = new Injury(p);
                    playersInjured.add(p);
                    numInjured++;
                }
            }
        }

        if (numInjured > 0) Collections.sort(players, new CompPlayer());
    }

    public void postSeasonHealing(int weeks) {
        playersRecovered = new ArrayList<>();
        healing(teamQBs, weeks);
        healing(teamRBs, weeks);
        healing(teamWRs, weeks);
        healing(teamTEs, weeks);
        healing(teamOLs, weeks);
        healing(teamKs, weeks);
        healing(teamDLs, weeks);
        healing(teamLBs, weeks);
        healing(teamCBs, weeks);
        healing(teamSs, weeks);
    }


    private void healing(ArrayList<? extends Player> players, int weeks) {
        int numInjured = 0;

        for (Player p : players) {
            if (p.injury != null && !p.isSuspended && !p.isTransfer) {
                numInjured++;
                for (int w=0; w < weeks; w++) {
                    p.injury.duration --;
                }
                if (p.injury.duration <= 0) {
                    // Done with injury
                    p.isInjured = false;
                    p.injury = null;
                }
                if (p.injury == null) {
                    playersRecovered.add(p);
                    playersInjured.remove(p);
                }
            }
        }

        if (numInjured > 0) Collections.sort(players, new CompPlayer());
    }

    /**
     * Gets a list of all the players that were injured that week.
     *
     * @return list of players in string
     */
    public String[] getInjuryReport() {
        ArrayList<Player> playersSuspended = new ArrayList<>();
        ArrayList<Player> allPlayers = getAllPlayers();
        for (int i = 0; i < allPlayers.size(); ++i) {
            if (allPlayers.get(i).isSuspended) {
                playersSuspended.add(allPlayers.get(i));
            }
        }

        if (playersInjured.size() > 0 || playersRecovered.size() > 0 || playersSuspended.size() > 0) {
            String[] injuries;

            injuries = new String[playersInjured.size() + playersRecovered.size() + playersSuspended.size() + 3];

            injuries[0] = "[B]Players Injured";
            for (int i = 0; i < playersInjured.size(); ++i) {
                injuries[i + 1]  = playersInjured.get(i).getPosNameYrOvrPot_Str();
            }

            injuries[playersInjured.size()+1] = "[B]Players Suspended";
            for (int i = 0; i < playersSuspended.size(); ++i) {
                injuries[playersInjured.size() + i + 2] = playersSuspended.get(i).getPosNameYrOvrPot_Str();
            }

            injuries[playersInjured.size() + playersSuspended.size() + 2] = "[B]Players Recovered";
            for (int i = 0; i < playersRecovered.size(); ++i) {
                injuries[playersInjured.size() + playersSuspended.size() + i + 3] = playersRecovered.get(i).getPosNameYrOvrPot_Str();
            }

            return injuries;
        } else return null;
    }

    /**
     * Get rid of all injuries
     */
    public void curePlayers() {
        curePlayersPosition(teamQBs);
        curePlayersPosition(teamRBs);
        curePlayersPosition(teamWRs);
        curePlayersPosition(teamTEs);
        curePlayersPosition(teamOLs);
        curePlayersPosition(teamKs);
        curePlayersPosition(teamDLs);
        curePlayersPosition(teamLBs);
        curePlayersPosition(teamCBs);
        curePlayersPosition(teamSs);
        sortPlayers();
    }

    private void curePlayersPosition(ArrayList<? extends Player> players) {
        for (Player p : players) {
            p.injury = null;
            p.isInjured = false;
        }
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> allPlayersList = new ArrayList<>();
        allPlayersList.addAll(teamQBs);
        allPlayersList.addAll(teamRBs);
        allPlayersList.addAll(teamWRs);
        allPlayersList.addAll(teamTEs);
        allPlayersList.addAll(teamOLs);
        allPlayersList.addAll(teamKs);
        allPlayersList.addAll(teamDLs);
        allPlayersList.addAll(teamLBs);
        allPlayersList.addAll(teamCBs);
        allPlayersList.addAll(teamSs);
        return allPlayersList;
    }

    public HeadCoach getHC(int depth) {
        return HC.get(depth);
    }

    public PlayerQB getQB(int depth) {
        if (depth < teamQBs.size() && depth >= 0) {
            return teamQBs.get(depth);
        } else {
            return teamQBs.get(0);
        }
    }

    public PlayerRB getRB(int depth) {
        if (depth < teamRBs.size() && depth >= 0) {
            return teamRBs.get(depth);
        } else {
            return teamRBs.get(0);
        }
    }

    public PlayerWR getWR(int depth) {
        if (depth < teamWRs.size() && depth >= 0) {
            return teamWRs.get(depth);
        } else {
            return teamWRs.get(0);
        }
    }

    public PlayerTE getTE(int depth) {
        if (depth < teamTEs.size() && depth >= 0) {
            return teamTEs.get(depth);
        } else {
            return teamTEs.get(0);
        }
    }

    public PlayerK getK(int depth) {
        if (depth < teamKs.size() && depth >= 0) {
            return teamKs.get(depth);
        } else {
            return teamKs.get(0);
        }
    }

    public PlayerOL getOL(int depth) {
        if (depth < teamOLs.size() && depth >= 0) {
            return teamOLs.get(depth);
        } else {
            return teamOLs.get(0);
        }
    }

    public PlayerDL getDL(int depth) {
        if (depth < teamDLs.size() && depth >= 0) {
            return teamDLs.get(depth);
        } else {
            return teamDLs.get(0);
        }
    }

    public PlayerLB getLB(int depth) {
        if (depth < teamLBs.size() && depth >= 0) {
            return teamLBs.get(depth);
        } else {
            return teamLBs.get(0);
        }
    }

    public PlayerCB getCB(int depth) {
        if (depth < teamCBs.size() && depth >= 0) {
            return teamCBs.get(depth);
        } else {
            return teamCBs.get(0);
        }
    }

    public PlayerS getS(int depth) {
        if (depth < teamSs.size() && depth >= 0) {
            return teamSs.get(depth);
        } else {
            return teamSs.get(0);
        }
    }


    /**
     * Get comma separated value of the team stats and their rankings.
     *
     * @return String of CSV stat,name,ranking
     */
    public String getTeamStatsStrCSV() {
        StringBuilder ts0 = new StringBuilder();

        ArrayList<Team> confTeams = new ArrayList<>();
        for (Conference c : league.conferences) {
            if (c.confName.equals(conference)) {
                confTeams.addAll(c.confTeams);
                Collections.sort(confTeams, new CompTeamConfWins());
                int confRank = 11;
                for (int i = 0; i < confTeams.size(); ++i) {
                    if (confTeams.get(i).equals(this)) {
                        confRank = i + 1;
                        break;
                    }
                }
                ts0.append(getConfWins() + "-" + getConfLosses() + ",");
                ts0.append("Conf W-L" + ",");
                ts0.append(getRankStr(confRank) + "%\n");
            }
        }

        ts0.append(df2.format(teamPollScore) + ",");
        ts0.append("Power Index" + ",");
        ts0.append(getRankStr(rankTeamPollScore) + "%\n");

        ts0.append(teamSOS + ",");
        ts0.append("Strength of Schedule" + ",");
        ts0.append(getRankStr(rankTeamSOS) + "%\n");

        ts0.append(teamStrengthOfWins + ",");
        ts0.append("Strength of Wins" + ",");
        ts0.append(getRankStr(rankTeamStrengthOfWins) + "%\n");

        ts0.append(df2.format((float) teamPoints / numGames()) + ",");
        ts0.append("Points" + ",");
        ts0.append(getRankStr(rankTeamPoints) + "%\n");

        ts0.append(df2.format((float) teamOppPoints / numGames()) + ",");
        ts0.append("Opp Points" + ",");
        ts0.append(getRankStr(rankTeamOppPoints) + "%\n");

        ts0.append(df2.format((float) teamYards / numGames()) + ",");
        ts0.append("Yards" + ",");
        ts0.append(getRankStr(rankTeamYards) + "%\n");

        ts0.append(df2.format((float) teamOppYards / numGames()) + ",");
        ts0.append("Opp Yards" + ",");
        ts0.append(getRankStr(rankTeamOppYards) + "%\n");

        ts0.append(df2.format((float) teamPassYards / numGames()) + ",");
        ts0.append("Pass Yards" + ",");
        ts0.append(getRankStr(rankTeamPassYards) + "%\n");

        ts0.append(df2.format((float) teamRushYards / numGames()) + ",");
        ts0.append("Rush Yards" + ",");
        ts0.append(getRankStr(rankTeamRushYards) + "%\n");

        ts0.append(df2.format((float) teamOppPassYards / numGames()) + ",");
        ts0.append("Opp Pass YPG" + ",");
        ts0.append(getRankStr(rankTeamOppPassYards) + "%\n");

        ts0.append(df2.format((float) teamOppRushYards / numGames()) + ",");
        ts0.append("Opp Rush YPG" + ",");
        ts0.append(getRankStr(rankTeamOppRushYards) + "%\n");

        if (teamTODiff > 0) ts0.append("+" + teamTODiff + ",");
        else ts0.append(teamTODiff + ",");
        ts0.append("TO Diff" + ",");
        ts0.append(getRankStr(rankTeamTODiff) + "%\n");

        ts0.append(df2.format(teamOffTalent) + ",");
        ts0.append("Off Talent" + ",");
        ts0.append(getRankStr(rankTeamOffTalent) + "%\n");

        ts0.append(df2.format(teamDefTalent) + ",");
        ts0.append("Def Talent" + ",");
        ts0.append(getRankStr(rankTeamDefTalent) + "%\n");

        ts0.append(df2.format(teamChemistry) + ",");
        ts0.append("Team Chemistry" + ",");
        ts0.append(getRankStr(rankTeamChemistry) + "%\n");

        ts0.append(teamPrestige + ",");
        ts0.append("Prestige" + ",");
        ts0.append(getRankStr(rankTeamPrestige) + "%\n");

        ts0.append(teamDisciplineScore + "%,");
        ts0.append("Discipline" + ",");
        ts0.append(getRankStr(rankTeamDisciplineScore) + "%\n");

        ts0.append(df2.format(getRecruitingClassRat()) + ",");
        ts0.append("Recruit Class" + ",");
        ts0.append(getRankStr(rankTeamRecruitClass) + "%\n");

        ts0.append("$" + teamBudget + ",");
        ts0.append("Team Budget" + ",");
        ts0.append(getRankStr(rankTeamBudget) + "%\n");

        ts0.append("L" + teamFacilities + ",");
        ts0.append("Facilities" + ",");
        ts0.append(getRankStr(rankTeamFacilities) + "%\n");

        return ts0.toString();
    }

    /**
     * Get the game summary of a played game.
     * [gameName, score summary, who they played]
     *
     * @param gameNumber number of the game desired
     * @return array of name, score, who was played
     */
    public String[] getGameSummaryStr(int gameNumber) {
        String[] gs = new String[3];
        Game g = gameSchedule.get(gameNumber);
        gs[0] = g.gameName;
        if (gameNumber < gameWLSchedule.size()) {
            if(g.gameName.equals("BYE WEEK")) {
                gs[1] = "BYE";
            } else {
                gs[1] = gameWLSchedule.get(gameNumber) + " " + gameSummaryStrScore(g);
                if (g.numOT > 0) gs[1] += " (" + g.numOT + "OT)";
            }
        } else {
            gs[1] = "---";
        }
        gs[2] = gameSummaryStrOpponent(g);
        return gs;
    }

    //Roster 2.0

    public ArrayList<String> getRoster() {
        ArrayList<String> roster = new ArrayList<>();

        roster.add(" , ,Coaching Staff, , , ");
            if (HC.size() > 0) {
                String hs = " ";
                if(HC.get(0).coachStatus().equals("Hot Seat")) hs = " [Hot Seat]";
                String imp = getRatImprovement(HC.get(0));
                roster.add("HC," + getHC(0).age + "," + getHC(0).name + "," + hs + "," + getHC(0).getHCOverall() + "," + getHC(0).getSeasonAwards() + "," + imp);
            }

        roster.add(" , , , ,  ");
        roster.add(" , ,Quarterbacks, , , ");
        int i = 0;
        for (Player p : teamQBs) {
            String starter = getRosterStatus(p, i, "QB");
            String imp = getRatImprovement(p);
            roster.add("QB,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Running Backs, , , ");
        i=0;
        for (Player p : teamRBs) {
            String starter = getRosterStatus(p, i, "RB");
            String imp = getRatImprovement(p);
            roster.add("RB,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Wide Receivers, , , ");
        i=0;
        for (Player p : teamWRs) {
            String starter = getRosterStatus(p, i, "WR");
            String imp = getRatImprovement(p);
            roster.add("WR,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Tight Ends, , , ");
        i=0;
        for (Player p : teamTEs) {
            String starter = getRosterStatus(p, i, "TE");
            String imp = getRatImprovement(p);
            roster.add("TE,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Offensive Linemen, , , ");
        i=0;
        for (Player p : teamOLs) {
            String starter = getRosterStatus(p, i, "OL");
            String imp = getRatImprovement(p);
            roster.add("OL,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Kickers, , , ");
        i=0;
        for (Player p : teamKs) {
            String starter = getRosterStatus(p, i, "K");
            String imp = getRatImprovement(p);
            roster.add("K,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Defensive Linemen, , , ");
        i=0;
        for (Player p : teamDLs) {
            String starter = getRosterStatus(p, i, "DL");
            String imp = getRatImprovement(p);
            roster.add("DL,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Linebackers, , , ");
        i=0;
        for (Player p : teamLBs) {
            String starter = getRosterStatus(p, i, "LB");
            String imp = getRatImprovement(p);
            roster.add("LB,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Cornerbacks, , , ");
        i=0;
        for (Player p : teamCBs) {
            String starter = getRosterStatus(p, i, "CB");
            String imp = getRatImprovement(p);
            roster.add("CB,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        roster.add(" , , , , ");
        roster.add(" , ,Safeties, , , ");
        i=0;
        for (Player p : teamSs) {
            String starter = getRosterStatus(p, i, "S");
            String imp = getRatImprovement(p);
            roster.add("S,"  + p.getYrStr() + "," + p.name + "," + starter + "," + p.ratOvr + "," + p.getSeasonAwards() + "," + imp);
            i++;
        }

        return roster;
    }

    private String getRatImprovement(Player p) {
        String imp = " ";

        if(league.currentWeek == league.regSeasonWeeks/2 || league.currentWeek > 21) {
            if (p.ratImprovement > 0) imp = " (+" + p.ratImprovement + ")";
            if (p.ratImprovement < 0) imp = " (" + p.ratImprovement + ")";
        } else if(league.showPotential && HC.size() > 0 && !p.position.equals("HC")) {
            imp = Integer.toString( p.getPotRating(getHC(0).ratTalent) );
        }


        return imp;
    }

    private String getRosterStatus(Player p, int i, String pos) {
        String status = " ";

        if (pos.equals("QB")) {
            if(i < startersQB) {
                status = "*";
            }
        } else if (pos.equals("RB")) {
            if(i < startersRB) {
                status = "*";
            }
        } else if (pos.equals("WR")) {
            if(i < startersWR) {
                status = "*";
            }
        } else if (pos.equals("TE")) {
            if(i < startersTE) {
                status = "*";
            }
        } else if (pos.equals("OL")) {
            if(i < startersOL) {
                status = "*";
            }
        } else if (pos.equals("K")) {
            if(i < startersK) {
                status = "*";
            }
        } else if (pos.equals("DL")) {
            if(i < startersDL) {
                status = "*";
            }
        } else if (pos.equals("LB")) {
            if(i < startersLB) {
                status = "*";
            }
        } else if (pos.equals("CB")) {
            if(i < startersCB) {
                status = "*";
            }
        } else if (pos.equals("S")) {
            if(i < startersS) {
                status = "*";
            }
        }
        if(p.isInjured) status = " [INJ - " + p.injury.duration + " wks]";
        if(p.isRedshirt) status = " [RS]";
        if(p.isTransfer) status = " [T]";
        if(p.isMedicalRS) status = " [Med RS]";
        if(p.isSuspended) status = " [Suspended - " + p.weeksSuspended + " wks]";

        return status;
    }

    public Player findTeamPlayer(String line) {
        for (Player p : HC) {
            if(p.name.equals(line)) return p;
        }
        for (Player p : teamQBs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamRBs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamWRs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamTEs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamOLs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamKs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamDLs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamLBs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamCBs) {
            if (p.name.equals(line)) return p;
        }
        for (Player p : teamSs) {
            if (p.name.equals(line)) return p;
        }
        return null;
    }


    //Roster String for Team Scouting For Coach Vacancies

    public String[] getTeamRosterString() {
        ArrayList<Player> rosters = getAllPlayers();
        String[] roster = new String[rosters.size()+19+8];

        roster[0] = "Conference: " + conference;
        roster[1] = "Prestige: " + teamPrestige + " ("+ getRankStr(rankTeamPrestige) + ")";
        roster[2] = "Discipline: " + teamDisciplineScore + "% | Facilities: L" + teamFacilities;
        roster[3] = "Overall W-L: " + totalWins + "-" + totalLosses + " (" + df2.format(getWinPCT(totalWins, totalLosses)) + "%)";
        roster[4] = "Conf Champ Record: " + totalCCs + "-" + totalCCLosses + " (" + df2.format(getWinPCT(totalCCs, totalCCLosses)) + "%)";
        roster[5] = "Bowl Game Record: " + totalBowls + "-" + totalBowlLosses + " (" + df2.format(getWinPCT(totalBowls, totalBowlLosses)) + "%)";
        roster[6] = "National Champ Record: " + totalNCs + "-" + totalNCLosses + " (" + df2.format(getWinPCT(totalNCs, totalNCLosses)) + "%)";
        roster[7] = " ";
        
        
        int i=8;
        roster[i] = "Quarterbacks";
        i++;
        for (Player p : teamQBs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;
        }
        roster[i] = "";
        i++;
        roster[i] = "Running Backs";
        i++;
        for (Player p : teamRBs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Wide Receivers";
        i++;

        for (Player p : teamWRs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Tight Ends";
        i++;

        for (Player p : teamTEs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Offensive Linemen";
        i++;

        for (Player p : teamOLs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Kickers";
        i++;

        for (Player p : teamKs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Defensive Linemen";
        i++;

        for (Player p : teamDLs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Linebackers";
        i++;

        for (Player p : teamLBs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Cornerbacks";
        i++;

        for (Player p : teamCBs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        roster[i] = "";
        i++;
        roster[i] = "Safeties";
        i++;

        for (Player p : teamSs) {
            roster[i] = p.getPosNameYrOvrPot_OneLine();
            i++;        }

        return roster;
    }



    /**
     * Gets rank str, i.e. 12 -> 12th, 3 -> 3rd
     *
     * @param num ranking
     * @return string of the ranking with correct ending
     */
    public String getRankStr(int num) {
        if (num == 11) {
            return "11th";
        } else if (num == 12) {
            return "12th";
        } else if (num == 13) {
            return "13th";
        } else if (num % 10 == 1) {
            return num + "st";
        } else if (num % 10 == 2) {
            return num + "nd";
        } else if (num % 10 == 3) {
            return num + "rd";
        } else {
            return num + "th";
        }
    }


    /**
     * Gets the number of games played so far
     *
     * @return number of games played
     */
    public int numGames() {
        if (wins + losses > 0) {
            return wins + losses;
        } else return 1;
    }

    public String getStrAbbrWL() {
        return name + " (" + wins + "-" + losses + ")";
    }

    public String getStrAbbrWL_2Lines() {
        return name + "\n(" + wins + "-" + losses + ")";
    }

    /**
     * Gets the number of in-conference wins, used for CCG rankings
     *
     * @return number of in-conf wins
     */
    public int getConfWins() {
        int confWins = 0;
        Game g;
        for (int i = 0; i < gameWLSchedule.size(); ++i) {
            g = gameSchedule.get(i);
            if (g.gameName.equals("Conference") || g.gameName.equals("Division")) {
                // in conference game, see if was won
                if (g.homeTeam == this && g.homeScore > g.awayScore) {
                    confWins++;
                } else if (g.awayTeam == this && g.homeScore < g.awayScore) {
                    confWins++;
                }
            }
        }
        return confWins;
    }

    /**
     * Gets the number of in-conference wins, used for CCG rankings
     *
     * @return number of in-conf wins
     */
    public int getDivWins() {
        int divWins = 0;
        Game g;
        for (int i = 0; i < gameWLSchedule.size(); ++i) {
            g = gameSchedule.get(i);
            if (g.gameName.equals("Division")) {
                // in conference game, see if was won
                if (g.homeTeam == this && g.homeScore > g.awayScore) {
                    divWins++;
                } else if (g.awayTeam == this && g.homeScore < g.awayScore) {
                    divWins++;
                }
            }
        }
        return divWins;
    }

    /**
     * Gets the number of in-conference losses, used for CCG rankings
     *
     * @return number of in-conf losses
     */
    public int getConfLosses() {
        int confLosses = 0;
        Game g;
        for (int i = 0; i < gameWLSchedule.size(); ++i) {
            g = gameSchedule.get(i);
            if (g.gameName.equals("Conference") || g.gameName.equals("Division")) {
                // in conference game, see if was won
                if (g.homeTeam == this && g.homeScore < g.awayScore) {
                    confLosses++;
                } else if (g.awayTeam == this && g.homeScore > g.awayScore) {
                    confLosses++;
                }
            }
        }
        return confLosses;
    }

    //Team Names for main display spinner
    public String strRankTeamRecord() {
        return "#" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ")";
    }

    /**
     * Str rep of team, no bowl results
     *
     * @return ranking abbr (w-l)
     */
    public String strRep() {
        return "#" + rankTeamPollScore + " " + name;
    }


    public String strConfStandings() {
        if (rankTeamPollScore < 26) return "#" + rankTeamPollScore + " " + name;
        else return name;
    }

    /**
     * Str rep of team, with prestige
     *
     * @return ranking abbr (Pres: XX)
     */
    public String strRepWithPrestige() {
        return /*"#" + rankTeamPollScore + " " + */name + " [#" + rankTeamPrestige + "]";
    }

    public String strTeamRecord() {
        return "(" + wins + "-" + losses + ") " + confChampion + " " + sweet16 + qtFinalWL + semiFinalWL + natChampWL;
    }

    /**
     * Get what happened during the week for the team
     *
     * @return name W/L gameSum, new poll rank #1
     */
    public String weekSummaryStr(int week) {
        int i = week - 1;
        if(week > league.regSeasonWeeks)  i = wins + losses + (league.regSeasonWeeks-13)-1;
        Game g = gameSchedule.get(i);
        String gameSummary;
        if(g.gameName.equals("BYE WEEK")) {
            gameSummary = "BYE WEEK";
        } else {
           gameSummary = gameWLSchedule.get(i) + " " + gameSummaryStr(g);
        }

        return name + " " + gameSummary + "\nNew poll rank: #" + rankTeamPollScore + " " + name + " (" + wins + "-" + losses + ")";
    }

    /**
     * Gets the one-line summary of a game
     *
     * @param g Game to get summary from
     * @return 31 - 43 @ GEO #60   POP UP MSG
     */
    private String gameSummaryStr(Game g) {
        if (g.homeTeam == this) {
            return g.homeScore + " - " + g.awayScore + " vs " + g.awayTeam.name + " #" + g.awayTeam.rankTeamPollScore;
        } else {
            return g.awayScore + " - " + g.homeScore + " at " + g.homeTeam.name + " #" + g.homeTeam.rankTeamPollScore;
        }
    }

    /**
     * Get just the score of the game
     *
     * @param g Game to get score from
     * @return "myTeamScore - otherTeamScore"
     */
    private String gameSummaryStrScore(Game g) {
        if (g.homeTeam == this) {
            return g.homeScore + " - " + g.awayScore;
        } else if(g.gameName.equals("BYE WEEK")) {
            return "";
        } else {
            return g.awayScore + " - " + g.homeScore;
        }
    }

    /**
     * Get the vs/@ part of the game summary
     *
     * @param g Game to get from
     * @return vs OPP #45
     */
    private String gameSummaryStrOpponent(Game g) {
        if (g.gameName.equals("BYE WEEK")) {
            return "BYE WEEK";
        } else {
            if (g.homeTeam == this) {
                return "vs " + g.awayTeam.name + " #" + g.awayTeam.rankTeamPollScore;
            } else {
                return "at " + g.homeTeam.name + " #" + g.homeTeam.rankTeamPollScore;
            }
        }
    }

    public String[] getGradPlayersList() {
        String[] playersLeavingList = new String[playersLeaving.size() + playersTransferring.size()];
        for (int i = 0; i < playersTransferring.size(); ++i) {
            playersLeavingList[i] = playersTransferring.get(i).getPosNameYrOvrPotTra_Str();
        }
        for (int i = 0; i < playersLeaving.size(); ++i) {
            playersLeavingList[i + playersTransferring.size()] = playersLeaving.get(i).getGraduatingPlayerInfo();
        }
        return playersLeavingList;
    }

    //Set Starters
    public void setStarters(ArrayList<Player> starters, int position) {
        switch (position) {
            case 0:
                ArrayList<PlayerQB> oldQBs = new ArrayList<>();
                oldQBs.addAll(teamQBs);
                teamQBs.clear();
                for (Player p : starters) {
                    teamQBs.add((PlayerQB) p);
                }
                Collections.sort(teamQBs, new CompPlayer());
                for (PlayerQB oldP : oldQBs) {
                    if (!teamQBs.contains(oldP)) teamQBs.add(oldP);
                }
                break;
            case 1:
                ArrayList<PlayerRB> oldRBs = new ArrayList<>();
                oldRBs.addAll(teamRBs);
                teamRBs.clear();
                for (Player p : starters) {
                    teamRBs.add((PlayerRB) p);
                }
                Collections.sort(teamRBs, new CompPlayer());
                for (PlayerRB oldP : oldRBs) {
                    if (!teamRBs.contains(oldP)) teamRBs.add(oldP);
                }
                break;
            case 2:
                ArrayList<PlayerWR> oldWRs = new ArrayList<>();
                oldWRs.addAll(teamWRs);
                teamWRs.clear();
                for (Player p : starters) {
                    teamWRs.add((PlayerWR) p);
                }
                Collections.sort(teamWRs, new CompPlayer());
                for (PlayerWR oldP : oldWRs) {
                    if (!teamWRs.contains(oldP)) teamWRs.add(oldP);
                }
                break;
            case 3:
                ArrayList<PlayerTE> oldTEs = new ArrayList<>();
                oldTEs.addAll(teamTEs);
                teamTEs.clear();
                for (Player p : starters) {
                    teamTEs.add((PlayerTE) p);
                }
                Collections.sort(teamTEs, new CompPlayer());
                for (PlayerTE oldP : oldTEs) {
                    if (!teamTEs.contains(oldP)) teamTEs.add(oldP);
                }
                break;
            case 4:
                ArrayList<PlayerOL> oldOLs = new ArrayList<>();
                oldOLs.addAll(teamOLs);
                teamOLs.clear();
                for (Player p : starters) {
                    teamOLs.add((PlayerOL) p);
                }
                Collections.sort(teamOLs, new CompPlayer());
                for (PlayerOL oldP : oldOLs) {
                    if (!teamOLs.contains(oldP)) teamOLs.add(oldP);
                }
                break;
            case 5:
                ArrayList<PlayerK> oldKs = new ArrayList<>();
                oldKs.addAll(teamKs);
                teamKs.clear();
                for (Player p : starters) {
                    teamKs.add((PlayerK) p);
                }
                Collections.sort(teamKs, new CompPlayer());
                for (PlayerK oldP : oldKs) {
                    if (!teamKs.contains(oldP)) teamKs.add(oldP);
                }
                break;
            case 6:
                ArrayList<PlayerDL> oldDLs = new ArrayList<>();
                oldDLs.addAll(teamDLs);
                teamDLs.clear();
                for (Player p : starters) {
                    teamDLs.add((PlayerDL) p);
                }
                Collections.sort(teamDLs, new CompPlayer());
                for (PlayerDL oldP : oldDLs) {
                    if (!teamDLs.contains(oldP)) teamDLs.add(oldP);
                }
                break;
            case 7:
                ArrayList<PlayerLB> oldLBs = new ArrayList<>();
                oldLBs.addAll(teamLBs);
                teamLBs.clear();
                for (Player p : starters) {
                    teamLBs.add((PlayerLB) p);
                }
                Collections.sort(teamLBs, new CompPlayer());
                for (PlayerLB oldP : oldLBs) {
                    if (!teamLBs.contains(oldP)) teamLBs.add(oldP);
                }
                break;
            case 8:
                ArrayList<PlayerCB> oldCBs = new ArrayList<>();
                oldCBs.addAll(teamCBs);
                teamCBs.clear();
                for (Player p : starters) {
                    teamCBs.add((PlayerCB) p);
                }
                Collections.sort(teamCBs, new CompPlayer());
                for (PlayerCB oldP : oldCBs) {
                    if (!teamCBs.contains(oldP)) teamCBs.add(oldP);
                }
                break;
            case 9:
                ArrayList<PlayerS> oldSs = new ArrayList<>();
                oldSs.addAll(teamSs);
                teamSs.clear();
                for (Player p : starters) {
                    teamSs.add((PlayerS) p);
                }
                Collections.sort(teamSs, new CompPlayer());
                for (PlayerS oldP : oldSs) {
                    if (!teamSs.contains(oldP)) teamSs.add(oldP);
                }
                break;
        }

        // Set ranks so that Off/Def Talent rankings are updated
        if (league.currentWeek < league.regSeasonWeeks+2) league.setTeamRanks();
    }

    //Adds a Game Start to each Starter
    public void addGamePlayedPlayers(boolean wonGame) {
        addGamePlayedList(teamQBs, startersQB, wonGame);
        addGamePlayedList(teamRBs, startersRB, wonGame);
        addGamePlayedList(teamWRs, startersWR, wonGame);
        addGamePlayedList(teamTEs, startersTE, wonGame);
        addGamePlayedList(teamOLs, startersOL, wonGame);
        addGamePlayedList(teamKs, startersK, wonGame);
        addGamePlayedList(teamDLs, startersDL, wonGame);
        addGamePlayedList(teamLBs, startersLB, wonGame);
        addGamePlayedList(teamCBs, startersCB, wonGame);
        addGamePlayedList(teamSs, startersS, wonGame);

        for (int i = 0; i < getAllPlayers().size(); ++i) {
            if (getAllPlayers().get(i).gameSnaps > 0) getAllPlayers().get(i).gamesPlayed++;
        }
    }

    private void addGamePlayedList(ArrayList<? extends Player> playerList, int starters, boolean wonGame) {
        for (int i = 0; i < starters; ++i) {
            playerList.get(i).gamesStarted++;
            if (wonGame) playerList.get(i).statsWins++;
        }

    }


    // Generate CPU Strategy
    public int getCPUOffense() {
        if (HC.size() < 1 || teamQBs.size() < 1) return 0;
        if (HC.get(0).offStrat < 0 || HC.get(0).offStrat > 5) HC.get(0).offStrat = 0;

        if (teamQBs.get(0).ratSpeed >= 75 && HC.get(0).offStrat == 4) {
            return 4;
        } else if (teamQBs.get(0).ratSpeed < 75 && HC.get(0).offStrat == 4) {
            return 0;
        }else if (teamQBs.get(0).ratSpeed >= 75 && HC.get(0).offStrat == 5) {
            return 5;
        } else if (teamQBs.get(0).ratSpeed < 75 && HC.get(0).offStrat == 5) {
            return 0;
        } else {
            return HC.get(0).offStrat;
        }
    }

    public int getCPUDefense() {
        if (HC.size() < 1) return 0;
        if (HC.get(0).defStrat < 0) HC.get(0).defStrat = 0;
        if (HC.get(0).defStrat > 4) HC.get(0).defStrat = 0;
        return HC.get(0).defStrat;
    }

    //Pulls available playbooks
    public PlaybookOffense[] getPlaybookOff() {
        PlaybookOffense[] ts = new PlaybookOffense[playbookOff.numPlaybooks];

        for (int i = 0; i < playbookOff.numPlaybooks; ++i) {
            ts[i] = new PlaybookOffense(i + 1);
        }
        return ts;
    }


    //Pulls available playbooks
    public PlaybookDefense[] getPlaybookDef() {
        PlaybookDefense[] ts = new PlaybookDefense[playbookDef.numPlaybooks];

        for (int i = 0; i < playbookDef.numPlaybooks; ++i) {
            ts[i] = new PlaybookDefense(i + 1);
        }
        return ts;
    }


    //Hall of Fame Credentials
    private void checkHallofFame() {

        for (Player p : playersLeaving) {
            int allConf = p.careerAllConference + (p.wonAllConference ? 1 : 0);
            int allAmer = p.careerAllAmerican + (p.wonAllAmerican ? 1 : 0);
            int poty = p.careerHeismans + (p.wonHeisman ? 1 : 0);
            int topF = p.careerTopFreshman;
            int allFresh = p.careerAllFreshman;
            int score = 10 * allConf + 25 * allAmer + 50 * poty + 15 * allFresh + 30 * topF;
            int statScore = p.getCareerScore();
            int totalScore = 10 * score + statScore;
            int avgScore = totalScore / (p.year);
            if (score > 85 || statScore > 28500 || totalScore > 30000 || avgScore > 7500) {
                // HOFer
                ArrayList<String> careerStats = p.getCareerStatsList();
                StringBuilder sb = new StringBuilder();
                sb.append(p.getPosNameYrOvr_Str() + "&");
                for (String s : careerStats) {
                    sb.append(s + "&");
                }

                hallOfFame.add(sb.toString());
                league.leagueHoF.add(sb.toString());
                HoFCount++;
            }
        }
    }


    //Checks if any of the league records were broken by this team.
    public void checkLeagueRecords(LeagueRecords records) {
        records.checkRecord("Team PPG", (float)teamPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team Opp PPG", (float)teamOppPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team YPG", (float)teamYards / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team Opp YPG", (float)teamOppYards / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team PPG", (float)teamPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team TO Diff", teamTODiff, name + "%" + abbr, league.getYear());

        for (int i = 0; i < teamQBs.size(); ++i) {
            if (getQB(i).gamesStarted > 6) {
                records.checkRecord("Pass Yards", getQB(i).statsPassYards, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Pass TDs", getQB(i).statsPassTD, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Ints Thrown", getQB(i).statsInt, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Comp Percent", getQB(i).getPassPCT(), getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("QB Rating", getQB(i).getPasserRating(), getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Rush Yards", getQB(i).statsRushYards, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Rush TDs", getQB(i).statsRushTD, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Fumbles Lost", getQB(i).statsFumbles, getQB(i).name + "%" + abbr, league.getYear());
            }
        }


        for (int i = 0; i < teamRBs.size(); ++i) {
            records.checkRecord("Rush Yards", getRB(i).statsRushYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rush TDs", getRB(i).statsRushTD, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Lost", getRB(i).statsFumbles, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret Yards", getRB(i).statsKickRetYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret TDs", getRB(i).statsKickRetTDs, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret Yards", getRB(i).statsPuntRetYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret TDs", getRB(i).statsPuntRetTDs, getRB(i).name + "%" + abbr, league.getYear());
        }

        for (int i = 0; i < teamWRs.size(); ++i) {
            records.checkRecord("Receptions", getWR(i).statsReceptions, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec Yards", getWR(i).statsRecYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec TDs", getWR(i).statsRecTD, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret Yards", getWR(i).statsKickRetYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret TDs", getWR(i).statsKickRetTDs, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret Yards", getWR(i).statsPuntRetYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret TDs", getWR(i).statsPuntRetTDs, getWR(i).name + "%" + abbr, league.getYear());
        }

        for (int i = 0; i < teamTEs.size(); ++i) {
            records.checkRecord("Receptions", getTE(i).statsReceptions, getTE(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec Yards", getTE(i).statsRecYards, getTE(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec TDs", getTE(i).statsRecTD, getTE(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamDLs.size(); ++i) {
            records.checkRecord("Tackles", getDL(i).statsTackles, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getDL(i).statsSacks, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getDL(i).statsFumbles, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getDL(i).statsInts, getDL(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamLBs.size(); ++i) {
            records.checkRecord("Tackles", getLB(i).statsTackles, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getLB(i).statsSacks, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getLB(i).statsFumbles, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getLB(i).statsInts, getLB(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamCBs.size(); ++i) {
            records.checkRecord("Tackles", getCB(i).statsTackles, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getCB(i).statsSacks, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getCB(i).statsFumbles, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getCB(i).statsInts, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Passes Defended", getCB(i).statsDefended, getCB(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamSs.size(); ++i) {
            records.checkRecord("Tackles", getS(i).statsTackles, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getS(i).statsSacks, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getS(i).statsFumbles, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getS(i).statsInts, getS(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamKs.size(); ++i) {
            records.checkRecord("Field Goals", getK(i).statsFGMade, getS(i).name + "%" + abbr, league.getYear());
        }
    }

    // Checks the career records for all the leaving players. Must be done after playersLeaving is populated.
    private void checkCareerRecords(LeagueRecords records) {
        for (Player p : playersLeaving) {
            if (p instanceof PlayerQB) {
                PlayerQB qb = (PlayerQB) p;
                if (qb.gamesStarted > 6) {
                    records.checkRecord("Career Pass Yards", qb.statsPassYards + qb.careerPassYards, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Pass TDs", qb.statsPassTD + qb.careerTDs, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Ints Thrown", qb.statsInt + qb.careerInt, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Comp PCT", qb.getCareerPassPCT(), qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career QB Rating", qb.getCareerPasserRating(), qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Rush Yards", qb.statsRushYards + qb.careerRushYards, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Rush TDs", qb.statsRushTD + qb.careerRushTD, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Fumbles Lost", qb.statsFumbles + qb.careerFumbles, qb.name + "%" + abbr, league.getYear() - 1);
                }
            } else if (p instanceof PlayerRB) {
                PlayerRB rb = (PlayerRB) p;
                records.checkRecord("Career Rush Yards", rb.statsRushYards + rb.careerRushYards, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rush TDs", rb.statsRushTD + rb.careerTDs, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Lost", rb.statsFumbles + rb.careerFumbles, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", rb.statsKickRetYards + rb.careerKickRetYards, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", rb.statsKickRetTDs + rb.careerKickRetTDs, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", rb.statsPuntRetYards + rb.careerPuntRetYards, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", rb.statsPuntRetTDs + rb.careerPuntRetTDs, rb.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerWR) {
                PlayerWR wr = (PlayerWR) p;
                records.checkRecord("Career Receptions", wr.statsReceptions + wr.careerReceptions, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec Yards", wr.statsRecYards + wr.careerRecYards, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec TDs", wr.statsRecTD + wr.careerTD, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", wr.statsKickRetYards + wr.careerKickRetYards, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", wr.statsKickRetTDs + wr.careerKickRetTDs, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", wr.statsPuntRetYards + wr.careerPuntRetYards, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", wr.statsPuntRetTDs + wr.careerPuntRetTDs, wr.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerTE) {
                PlayerTE te = (PlayerTE) p;
                records.checkRecord("Career Receptions", te.statsReceptions + te.careerReceptions, te.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec Yards", te.statsRecYards + te.careerRecYards, te.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec TDs", te.statsRecTD + te.careerTD, te.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerDL) {
                PlayerDL dl = (PlayerDL) p;
                records.checkRecord("Career Tackles", dl.statsTackles + dl.careerTackles, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", dl.statsSacks + dl.careerSacks, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", dl.statsFumbles + dl.careerFumbles, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", dl.statsInts + dl.careerInts, dl.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerLB) {
                PlayerLB lb = (PlayerLB) p;
                records.checkRecord("Career Tackles", lb.statsTackles + lb.careerTackles, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", lb.statsSacks + lb.careerSacks, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", lb.statsFumbles + lb.careerFumbles, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", lb.statsInts + lb.careerInts, lb.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerCB) {
                PlayerCB cb = (PlayerCB) p;
                records.checkRecord("Career Tackles", cb.statsTackles + cb.careerTackles, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", cb.statsSacks + cb.careerSacks, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", cb.statsFumbles + cb.careerFumbles, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", cb.statsInts + cb.careerInts, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Defended", cb.statsDefended + cb.careerDefended, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", cb.statsKickRetYards + cb.careerKickRetYards, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", cb.statsKickRetTDs + cb.careerKickRetTDs, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", cb.statsPuntRetYards + cb.careerPuntRetYards, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", cb.statsPuntRetTDs + cb.careerPuntRetTDs, cb.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerS) {
                PlayerS s = (PlayerS) p;
                records.checkRecord("Career Tackles", s.statsTackles + s.careerTackles, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", s.statsSacks + s.careerSacks, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", s.statsFumbles + s.careerFumbles, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", s.statsInts + s.careerInts, s.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerK) {
                PlayerK k = (PlayerK) p;
                records.checkRecord("Career Field Goals", k.statsFGMade + k.careerFGMade, k.name + "%" + abbr, league.getYear() - 1);
            }
        }
    }


    //Checks if any of the league records were broken by this team.
    public void checkTeamRecords(TeamRecords records) {
        records.checkRecord("Team PPG", (float)teamPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team Opp PPG", (float)teamOppPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team YPG", (float)teamYards / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team Opp YPG", (float)teamOppYards / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team PPG", (float)teamPoints / numGames(), name + "%" + abbr, league.getYear());
        records.checkRecord("Team TO Diff", teamTODiff, name + "%" + abbr, league.getYear());

        for (int i = 0; i < teamQBs.size(); ++i) {
            if (getQB(i).gamesStarted > 6) {
                records.checkRecord("Pass Yards", getQB(i).statsPassYards, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Pass TDs", getQB(i).statsPassTD, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Ints Thrown", getQB(i).statsInt, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Comp Percent", getQB(i).getPassPCT(), getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("QB Rating", getQB(i).getPasserRating(), getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Rush Yards", getQB(i).statsRushYards, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Rush TDs", getQB(i).statsRushTD, getQB(i).name + "%" + abbr, league.getYear());
                records.checkRecord("Fumbles Lost", getQB(i).statsFumbles, getQB(i).name + "%" + abbr, league.getYear());
            }
        }


        for (int i = 0; i < teamRBs.size(); ++i) {
            records.checkRecord("Rush Yards", getRB(i).statsRushYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rush TDs", getRB(i).statsRushTD, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Lost", getRB(i).statsFumbles, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret Yards", getRB(i).statsKickRetYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret TDs", getRB(i).statsKickRetTDs, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret Yards", getRB(i).statsPuntRetYards, getRB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret TDs", getRB(i).statsPuntRetTDs, getRB(i).name + "%" + abbr, league.getYear());
        }

        for (int i = 0; i < teamWRs.size(); ++i) {
            records.checkRecord("Receptions", getWR(i).statsReceptions, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec Yards", getWR(i).statsRecYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec TDs", getWR(i).statsRecTD, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret Yards", getWR(i).statsKickRetYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Kick Ret TDs", getWR(i).statsKickRetTDs, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret Yards", getWR(i).statsPuntRetYards, getWR(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Punt Ret TDs", getWR(i).statsPuntRetTDs, getWR(i).name + "%" + abbr, league.getYear());
        }

        for (int i = 0; i < teamTEs.size(); ++i) {
            records.checkRecord("Receptions", getTE(i).statsReceptions, getTE(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec Yards", getTE(i).statsRecYards, getTE(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Rec TDs", getTE(i).statsRecTD, getTE(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamDLs.size(); ++i) {
            records.checkRecord("Tackles", getDL(i).statsTackles, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getDL(i).statsSacks, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getDL(i).statsFumbles, getDL(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getDL(i).statsInts, getDL(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamLBs.size(); ++i) {
            records.checkRecord("Tackles", getLB(i).statsTackles, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getLB(i).statsSacks, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getLB(i).statsFumbles, getLB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getLB(i).statsInts, getLB(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamCBs.size(); ++i) {
            records.checkRecord("Tackles", getCB(i).statsTackles, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getCB(i).statsSacks, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getCB(i).statsFumbles, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getCB(i).statsInts, getCB(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Passes Defended", getCB(i).statsDefended, getCB(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamSs.size(); ++i) {
            records.checkRecord("Tackles", getS(i).statsTackles, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Sacks", getS(i).statsSacks, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Fumbles Recovered", getS(i).statsFumbles, getS(i).name + "%" + abbr, league.getYear());
            records.checkRecord("Interceptions", getS(i).statsInts, getS(i).name + "%" + abbr, league.getYear());
        }
        for (int i = 0; i < teamKs.size(); ++i) {
            records.checkRecord("Field Goals", getK(i).statsFGMade, getS(i).name + "%" + abbr, league.getYear());
        }
    }

    // Checks the career records for all the leaving players. Must be done after playersLeaving is populated.
    private void checkCareerTeamRecords(TeamRecords records) {
        for (Player p : playersLeaving) {
            if (p instanceof PlayerQB) {
                PlayerQB qb = (PlayerQB) p;
                if (qb.gamesStarted > 6) {
                    records.checkRecord("Career Pass Yards", qb.statsPassYards + qb.careerPassYards, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Pass TDs", qb.statsPassTD + qb.careerTDs, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Ints Thrown", qb.statsInt + qb.careerInt, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Comp PCT",  qb.getCareerPassPCT(), qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career QB Rating",  qb.getCareerPasserRating(), qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Rush Yards", qb.statsRushYards + qb.careerRushYards, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Rush TDs", qb.statsRushTD + qb.careerRushTD, qb.name + "%" + abbr, league.getYear() - 1);
                    records.checkRecord("Career Fumbles Lost", qb.statsFumbles + qb.careerFumbles, qb.name + "%" + abbr, league.getYear() - 1);
                }
            } else if (p instanceof PlayerRB) {
                PlayerRB rb = (PlayerRB) p;
                records.checkRecord("Career Rush Yards", rb.statsRushYards + rb.careerRushYards, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rush TDs", rb.statsRushTD + rb.careerTDs, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Lost", rb.statsFumbles + rb.careerFumbles, rb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", rb.statsKickRetYards + rb.careerKickRetYards, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", rb.statsKickRetTDs + rb.careerKickRetTDs, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", rb.statsPuntRetYards + rb.careerPuntRetYards, rb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", rb.statsPuntRetTDs + rb.careerPuntRetTDs, rb.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerWR) {
                PlayerWR wr = (PlayerWR) p;
                records.checkRecord("Career Receptions", wr.statsReceptions + wr.careerReceptions, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec Yards", wr.statsRecYards + wr.careerRecYards, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec TDs", wr.statsRecTD + wr.careerTD, wr.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", wr.statsKickRetYards + wr.careerKickRetYards, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", wr.statsKickRetTDs + wr.careerKickRetTDs, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", wr.statsPuntRetYards + wr.careerPuntRetYards, wr.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", wr.statsPuntRetTDs + wr.careerPuntRetTDs, wr.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerTE) {
                PlayerTE te = (PlayerTE) p;
                records.checkRecord("Career Receptions", te.statsReceptions + te.careerReceptions, te.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec Yards", te.statsRecYards + te.careerRecYards, te.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Rec TDs", te.statsRecTD + te.careerTD, te.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerDL) {
                PlayerDL dl = (PlayerDL) p;
                records.checkRecord("Career Tackles", dl.statsTackles + dl.careerTackles, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", dl.statsSacks + dl.careerSacks, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", dl.statsFumbles + dl.careerFumbles, dl.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", dl.statsInts + dl.careerInts, dl.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerLB) {
                PlayerLB lb = (PlayerLB) p;
                records.checkRecord("Career Tackles", lb.statsTackles + lb.careerTackles, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", lb.statsSacks + lb.careerSacks, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", lb.statsFumbles + lb.careerFumbles, lb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", lb.statsInts + lb.careerInts, lb.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerCB) {
                PlayerCB cb = (PlayerCB) p;
                records.checkRecord("Career Tackles", cb.statsTackles + cb.careerTackles, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", cb.statsSacks + cb.careerSacks, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", cb.statsFumbles + cb.careerFumbles, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", cb.statsInts + cb.careerInts, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Defended", cb.statsDefended + cb.careerDefended, cb.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career KR Yards", cb.statsKickRetYards + cb.careerKickRetYards, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career KR TDs", cb.statsKickRetTDs + cb.careerKickRetTDs, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR Yards", cb.statsPuntRetYards + cb.careerPuntRetYards, cb.name + "%" + abbr, league.getYear());
                records.checkRecord("Career PR TDs", cb.statsPuntRetTDs + cb.careerPuntRetTDs, cb.name + "%" + abbr, league.getYear());
            } else if (p instanceof PlayerS) {
                PlayerS s = (PlayerS) p;
                records.checkRecord("Career Tackles", s.statsTackles + s.careerTackles, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Sacks", s.statsSacks + s.careerSacks, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Fumbles Rec", s.statsFumbles + s.careerFumbles, s.name + "%" + abbr, league.getYear() - 1);
                records.checkRecord("Career Interceptions", s.statsInts + s.careerInts, s.name + "%" + abbr, league.getYear() - 1);
            } else if (p instanceof PlayerK) {
                PlayerK k = (PlayerK) p;
                records.checkRecord("Career Field Goals", k.statsFGMade + k.careerFGMade, k.name + "%" + abbr, league.getYear() - 1);
            }
        }
    }

    public void getLeaderboard() {
        //Google Play Games Leaderboard Implementation

    }

    public String getTeamSaveString() {
        String teamSave;
        
        teamSave = (conference + "," + name + "," + abbr + "," + teamPrestige + "," + totalWins + "," + totalLosses
                + "," + totalCCs + "," + totalNCs + "," + division + "," + location + "," + totalNCLosses
                + "," + totalCCLosses + "," + totalBowls + "," + totalBowlLosses + "," + playbookOffNum + "," + playbookDefNum
                + "," + (showPopups ? 1 : 0) + "," + winStreak.getStreakCSV() + "," + teamBudget + "," + teamDisciplineScore + "," + recentPenalty + "," + teamFacilities + "," + teamStadium + "%\n");

        return teamSave;
    }

    //Saves generated recruits to a file for Recruiting Activity
    public String getRecruitsInfoSaveFile() {
        int rating = getUserRecruitStars();

        StringBuilder sb = new StringBuilder();
        PlayerQB[] qbs = getQBRecruits(rating);
        for (PlayerQB p : qbs) {
            sb.append("QB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratPassPow + "," + p.ratPassAcc + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerRB[] rbs = getRBRecruits(rating);
        for (PlayerRB p : rbs) {
            sb.append("RB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratRushPower + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratCatch + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerWR[] wrs = getWRRecruits(rating);
        for (PlayerWR p : wrs) {
            sb.append("WR," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratCatch + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratJump + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerTE[] tes = getTERecruits(rating);
        for (PlayerTE p : tes) {
            sb.append("TE," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratCatch + "," + p.ratRunBlock + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerK[] ks = getKRecruits(rating);
        for (PlayerK p : ks) {
            sb.append("K," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratKickPow + "," + p.ratKickAcc + "," + p.ratKickFum + "," + p.ratPressure + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerOL[] ols = getOLRecruits(rating);
        for (PlayerOL p : ols) {
            sb.append("OL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratStrength + "," + p.ratRunBlock + "," + p.ratPassBlock + "," + p.ratAwareness + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerDL[] dls = getDLRecruits(rating);
        for (PlayerDL p : dls) {
            sb.append("DL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratStrength + "," + p.ratRunStop + "," + p.ratPassRush + "," + p.ratTackle + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerLB[] lbs = getLBRecruits(rating);
        for (PlayerLB p : lbs) {
            sb.append("LB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratCoverage + "," + p.ratRunStop + "," + p.ratTackle + "," + p.ratSpeed + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerCB[] cbs = getCBRecruits(rating);
        for (PlayerCB p : cbs) {
            sb.append("CB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratJump + "," + p.height + "," + p.weight + "%\n");
        }
        PlayerS[] ss = getSRecruits(rating);
        for (PlayerS p : ss) {
            sb.append("S," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.cost + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratRunStop + "," + p.height + "," + p.weight + "%\n");
        }

        return sb.toString();
    }

    //Creates a Save File for Team Roster
    public String getPlayerInfoSaveFile() {
        StringBuilder sb = new StringBuilder();
        for (HeadCoach hc : HC) {
            sb.append("HC," + hc.name + "," + hc.age + "," + hc.year + "," + hc.contractYear + "," + hc.contractLength + "," + hc.ratPot
                    + "," + hc.ratOff + "," + hc.ratDef + "," + hc.ratTalent + "," + hc.ratDiscipline + "," + hc.offStrat + "," + hc.defStrat + "," + hc.baselinePrestige
                    + "," + hc.wins + "," + hc.losses + "," + hc.bowlwins + "," + hc.bowllosses + "," + hc.confchamp + "," + hc.natchamp + "," + hc.allconference
                    + "," + hc.allamericans + "," + hc.confAward + "," + hc.awards + "," + hc.cumulativePrestige
                    + "%\n");
        }
        for (PlayerQB p : teamQBs) {
            sb.append("QB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratPassPow + "," + p.ratPassAcc + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerPassAtt + "," + p.careerPassComp + "," + p.careerTDs + "," + p.careerInt
                    + "," + p.careerPassYards + "," + p.careerSacked + "," + p.careerRushAtt + "," + p.careerRushYards + "," + p.careerRushTD + "," + p.careerFumbles
                    + "%\n");
        }

        for (PlayerRB p : teamRBs) {
            sb.append("RB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratRushPower + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratCatch + "," + p.height + "," + p.weight
                    + "," + p.careerRushAtt + "," + p.careerRushYards + "," + p.careerTDs + "," + p.careerFumbles + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerRecTD
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "%\n");
        }

        for (PlayerWR p : teamWRs) {
            sb.append("WR," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCatch + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratJump + "," + p.height + "," + p.weight
                    + "," + p.careerTargets + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerTD + "," + p.careerDrops + "," + p.careerFumbles
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "%\n");
        }


        for (PlayerTE p : teamTEs) {
            sb.append("TE," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCatch + "," + p.ratRunBlock + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerTargets + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerTD + "," + p.careerDrops + "," + p.careerFumbles
                    + "%\n");
        }

        for (PlayerOL p : teamOLs) {
            sb.append("OL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratStrength + "," + p.ratRunBlock + "," + p.ratPassBlock + "," + p.ratAwareness + "," + p.height + "," + p.weight
                    + "%\n");
        }

        for (PlayerK p : teamKs) {
            sb.append("K," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratKickPow + "," + p.ratKickAcc + "," + p.ratKickFum + "," + p.ratPressure + "," + p.height + "," + p.weight
                    + "," + p.careerXPAtt + "," + p.careerXPMade + "," + p.careerFGAtt + "," + p.careerFGMade
                    + "%\n");
        }

        for (PlayerDL p : teamDLs) {
            sb.append("DL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratStrength + "," + p.ratRunStop + "," + p.ratPassRush + "," + p.ratTackle + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "%\n");
        }

        for (PlayerLB p : teamLBs) {
            sb.append("LB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratRunStop + "," + p.ratTackle + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "%\n");
        }

        for (PlayerCB p : teamCBs) {
            sb.append("CB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratJump + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts + "," + p.careerTargets + "," + p.careerIncomplete + "," + p.careerDefended
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "%\n");
        }

        for (PlayerS p : teamSs) {
            sb.append("S," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratRunStop + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "%\n");
        }

        return sb.toString();
    }

    public String midseasonTeamSave() {
        StringBuilder teamSave = new StringBuilder();

        //Save Team Data
        teamSave.append(conference + "," + name + "," + abbr + "," + teamPrestige + "," + totalWins + "," + totalLosses
                + "," + totalCCs + "," + totalNCs + "," + division + "," + location + "," + totalNCLosses
                + "," + totalCCLosses + "," + totalBowls + "," + totalBowlLosses + "," + playbookOffNum + "," + playbookDefNum
                + "," + (showPopups ? 1 : 0) + "," + winStreak.getStreakCSV() + "," + teamBudget + "," + teamDisciplineScore + "," + recentPenalty + "," + teamFacilities + "," + teamStadium + "%\n");

        //Save Team Season Data

/*
        public int wins;
        public int losses;
        public int totalWins;
        public int totalLosses;
        public int totalCCs;
        public int totalNCs;
        public int totalCCLosses;
        public int totalNCLosses;
        public int totalBowls;
        public int totalBowlLosses;
        public int teamPoints;
        public int teamOppPoints;
        public int teamYards;
        public int teamOppYards;
        public int teamPassYards;
        public int teamRushYards;
        public int teamOppPassYards;
        public int teamOppRushYards;
        public int teamTODiff;
        public int teamPrestige;
        public int teamPrestigeStart;

        public int confPrestige;
        public int disciplinePts;
        public int projectedWins;
        public int projectedPollRank;
        public float projectedPollScore;
        public float teamStartOffTal;
        public float teamStartDefTal;

        public boolean fired;
        private boolean newContract;
        private boolean retired;

        //Save Team Schedule
        public ArrayList<Game> gameSchedule;
        public ArrayList<String> gameWLSchedule;
        public ArrayList<Team> gameWinsAgainst;
        public ArrayList<Team> gameLossesAgainst;
        public String confChampion;
        public String sweet16;
        public String qtFinalWL;
        public String semiFinalWL;
        public String natChampWL;
*/


        //Save Team Injuries & Suspensions

        return teamSave.toString();
    }

    //Creates a Save File for Team Roster
    public String midseasonRosterSave() {
        StringBuilder sb = new StringBuilder();
        for (HeadCoach hc : HC) {
            sb.append("HC," + hc.name + "," + hc.age + "," + hc.year + "," + hc.contractYear + "," + hc.contractLength + "," + hc.ratPot
                    + "," + hc.ratOff + "," + hc.ratDef + "," + hc.ratTalent + "," + hc.ratDiscipline + "," + hc.offStrat + "," + hc.defStrat + "," + hc.baselinePrestige
                    + "," + hc.wins + "," + hc.losses + "," + hc.bowlwins + "," + hc.bowllosses + "," + hc.confchamp + "," + hc.natchamp + "," + hc.allconference
                    + "," + hc.allamericans + "," + hc.confAward + "," + hc.awards + "," + hc.cumulativePrestige
                    + "," + hc.teamWins+ "," + hc.teamLosses+ "," + hc.ratImprovement
                    + "%\n");
        }
        for (PlayerQB p : teamQBs) {
            sb.append("QB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratPassPow + "," + p.ratPassAcc + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerPassAtt + "," + p.careerPassComp + "," + p.careerTDs + "," + p.careerInt
                    + "," + p.careerPassYards + "," + p.careerSacked + "," + p.careerRushAtt + "," + p.careerRushYards + "," + p.careerRushTD + "," + p.careerFumbles
                    + "," + p.statsPassAtt+ "," + p.statsPassComp+ "," + p.statsPassTD + "," + p.statsInt + "," + p.statsPassYards + "," + p.statsSacked + "," + p.statsRushAtt + "," + p.statsRushYards + "," + p.statsRushTD + "," + p.statsFumbles
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerRB p : teamRBs) {
            sb.append("RB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratRushPower + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratCatch + "," + p.height + "," + p.weight
                    + "," + p.careerRushAtt + "," + p.careerRushYards + "," + p.careerTDs + "," + p.careerFumbles + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerRecTD
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "," + p.statsRushAtt+ "," + p.statsRushYards+ "," + p.statsRushTD+ "," + p.statsFumbles+ "," + p.statsReceptions+ "," + p.statsRecYards+ "," + p.statsRecTD
                    + "," + p.statsKickRets+ "," + p.statsKickRetYards + "," + p.statsKickRetTDs+ "," + p.statsPuntRets+ "," + p.statsPuntRetYards+ "," + p.statsPuntRetTDs
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerWR p : teamWRs) {
            sb.append("WR," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCatch + "," + p.ratSpeed + "," + p.ratEvasion + "," + p.ratJump + "," + p.height + "," + p.weight
                    + "," + p.careerTargets + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerTD + "," + p.careerDrops + "," + p.careerFumbles
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "," + p.statsTargets + "," + p.statsReceptions + "," + p.statsRecYards + "," + p.statsRecTD + "," + p.statsDrops + "," + p.statsFumbles
                    + "," + p.statsKickRets+ "," + p.statsKickRetYards + "," + p.statsKickRetTDs+ "," + p.statsPuntRets+ "," + p.statsPuntRetYards+ "," + p.statsPuntRetTDs
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }


        for (PlayerTE p : teamTEs) {
            sb.append("TE," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCatch + "," + p.ratRunBlock + "," + p.ratEvasion + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerTargets + "," + p.careerReceptions + "," + p.careerRecYards + "," + p.careerTD + "," + p.careerDrops + "," + p.careerFumbles
                    + "," + p.statsTargets + "," + p.statsReceptions + "," + p.statsRecYards + "," + p.statsRecTD + "," + p.statsDrops + "," + p.statsFumbles
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerOL p : teamOLs) {
            sb.append("OL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratStrength + "," + p.ratRunBlock + "," + p.ratPassBlock + "," + p.ratAwareness + "," + p.height + "," + p.weight
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerK p : teamKs) {
            sb.append("K," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratKickPow + "," + p.ratKickAcc + "," + p.ratKickFum + "," + p.ratPressure + "," + p.height + "," + p.weight
                    + "," + p.careerXPAtt + "," + p.careerXPMade + "," + p.careerFGAtt + "," + p.careerFGMade
                    + "," + p.statsXPAtt + "," + p.statsXPMade + "," + p.statsFGAtt + "," + p.statsFGMade
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerDL p : teamDLs) {
            sb.append("DL," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratStrength + "," + p.ratRunStop + "," + p.ratPassRush + "," + p.ratTackle + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "," + p.statsTackles + "," + p.statsSacks + "," + p.statsFumbles + "," + p.statsInts
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerLB p : teamLBs) {
            sb.append("LB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratRunStop + "," + p.ratTackle + "," + p.ratSpeed + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "," + p.statsTackles + "," + p.statsSacks + "," + p.statsFumbles + "," + p.statsInts
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerCB p : teamCBs) {
            sb.append("CB," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratJump + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts + "," + p.careerTargets + "," + p.careerIncomplete + "," + p.careerDefended
                    + "," + p.careerKickRets + "," + p.careerKickRetYards + "," + p.careerKickRetTDs + "," + p.careerPuntRets + "," + p.careerPuntRetYards + "," + p.careerPuntRetTDs
                    + "," + p.statsTackles + "," + p.statsSacks + "," + p.statsFumbles + "," + p.statsInts
                    + "," + p.statsKickRets+ "," + p.statsKickRetYards + "," + p.statsKickRetTDs+ "," + p.statsPuntRets+ "," + p.statsPuntRetYards+ "," + p.statsPuntRetTDs
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        for (PlayerS p : teamSs) {
            sb.append("S," + p.name + "," + p.year + "," + p.homeState + "," + p.personality + "," + p.ratFootIQ + "," + p.recruitRating + "," + p.isTransfer + "," + p.wasRedshirt + "," + p.isWalkOn + "," + p.ratPot + "," + p.ratDur
                    + "," + p.ratOvr + "," + p.ratImprovement + "," + p.careerGames + "," + p.careerWins + "," + p.careerHeismans + "," + p.careerAllAmerican + "," + p.careerAllConference + "," + p.careerTopFreshman + "," + p.careerAllFreshman
                    + "," + p.ratCoverage + "," + p.ratSpeed + "," + p.ratTackle + "," + p.ratRunStop + "," + p.height + "," + p.weight
                    + "," + p.careerTackles + "," + p.careerSacks + "," + p.careerFumbles + "," + p.careerInts
                    + "," + p.statsTackles + "," + p.statsSacks + "," + p.statsFumbles + "," + p.statsInts
                    + "," + p.gamesStarted + "," + p.gamesPlayed + "," + p.statsWins + "," + p.isInjured + "," + p.isMedicalRS + "," + p.troubledTimes + "," + p.ratImprovement + "," + p.wonHeisman + "," + p.wonAllAmerican + "," + p.wonAllConference + "," + p.wonTopFreshman + "," + p.wonAllFreshman
                    + "%\n");
        }

        return sb.toString();
    }


    //LOAD PLAYER DATA
    private void loadPlayerSaveData(String line, boolean isRedshirt) {
        String[] playerInfo = line.split(",");
        //Name checker
        if (playerInfo.length > 1) {
            if (playerInfo[1].split(" ").length > 1) {
                if (Boolean.parseBoolean(playerInfo[7])) isRedshirt = true;

                if (playerInfo[0].equals("QB")) {
                    loadQBSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("RB")) {
                    loadRBSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("WR")) {
                    loadWRSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("TE")) {
                    loadTESaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("OL")) {
                    loadOLSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("K")) {
                    loadKSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("DL")) {
                    loadDLSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("LB")) {
                    loadLBSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("CB")) {
                    loadCBSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("S")) {
                    loadSSaveData(playerInfo, isRedshirt);
                } else if (playerInfo[0].equals("HC")) {
                    loadHCSaveData(playerInfo);
                }
            }
        }
    }

    private void loadHCSaveData(String[] data) {
        int cPrestige = 0;
        if (data.length > 25) {
            if (data.length > 24) cPrestige = Integer.parseInt(data[24]);
            HC.add(new HeadCoach(data[1], this,
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                    Integer.parseInt(data[8]), Integer.parseInt(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]),
                    Integer.parseInt(data[12]), Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    cPrestige, Integer.parseInt(data[25]), Integer.parseInt(data[26]),
                    Integer.parseInt(data[27])
            ));
        } else {
            if (data.length > 24) cPrestige = Integer.parseInt(data[24]);
            HC.add(new HeadCoach(data[1], this,
                    Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                    Integer.parseInt(data[8]), Integer.parseInt(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]),
                    Integer.parseInt(data[12]), Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    cPrestige));
        }
    }

    private void loadQBSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 38)
            teamQBs.add(new PlayerQB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38]), Integer.parseInt(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Integer.parseInt(data[42]), Integer.parseInt(data[43]),
                    Integer.parseInt(data[44]), Integer.parseInt(data[45]),
                    Integer.parseInt(data[46]), Integer.parseInt(data[47]),
                    Integer.parseInt(data[48]), Integer.parseInt(data[49]),
                    Boolean.parseBoolean(data[50]), Boolean.parseBoolean(data[51]),
                    Integer.parseInt(data[52]), Integer.parseInt(data[53]),
                    Boolean.parseBoolean(data[54]), Boolean.parseBoolean(data[55]),
                    Boolean.parseBoolean(data[56]), Boolean.parseBoolean(data[57]), Boolean.parseBoolean(data[56])
            ));
        else if (data.length > 20)
            teamQBs.add(new PlayerQB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36])
            ));
        else
            teamQBs.add(new PlayerQB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadRBSaveData(String[] data, boolean isRedshirt) {
       if (data.length > 40)
           teamRBs.add(new PlayerRB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                   Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                   Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                   Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                   Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                   Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                   Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                   Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                   Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                   Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                   Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                   Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                   Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                   Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                   Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                   Integer.parseInt(data[38]), Integer.parseInt(data[39]),
                   Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                   Integer.parseInt(data[42]), Integer.parseInt(data[43]),
                   Integer.parseInt(data[44]), Integer.parseInt(data[45]),
                   Integer.parseInt(data[46]), Integer.parseInt(data[47]),
                   Integer.parseInt(data[48]), Integer.parseInt(data[49]),
                   Integer.parseInt(data[50]), Integer.parseInt(data[51]),
                   Integer.parseInt(data[52]), Integer.parseInt(data[53]),
                   Integer.parseInt(data[54]), Integer.parseInt(data[55]),
                   Boolean.parseBoolean(data[56]), Boolean.parseBoolean(data[57]),
                   Integer.parseInt(data[58]), Integer.parseInt(data[59]),
                   Boolean.parseBoolean(data[60]), Boolean.parseBoolean(data[61]),
                   Boolean.parseBoolean(data[62]), Boolean.parseBoolean(data[63]), Boolean.parseBoolean(data[64])
                   ));
       else if (data.length > 20)
            teamRBs.add(new PlayerRB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38]), Integer.parseInt(data[39])));
        else
            teamRBs.add(new PlayerRB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadWRSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 40)
            teamWRs.add(new PlayerWR(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38]), Integer.parseInt(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Integer.parseInt(data[42]), Integer.parseInt(data[43]),
                    Integer.parseInt(data[44]), Integer.parseInt(data[45]),
                    Integer.parseInt(data[46]), Integer.parseInt(data[47]),
                    Integer.parseInt(data[48]), Integer.parseInt(data[49]),
                    Integer.parseInt(data[50]), Integer.parseInt(data[51]),
                    Integer.parseInt(data[52]), Integer.parseInt(data[53]),
                    Boolean.parseBoolean(data[54]), Boolean.parseBoolean(data[55]),
                    Integer.parseInt(data[56]), Integer.parseInt(data[57]),
                    Boolean.parseBoolean(data[58]), Boolean.parseBoolean(data[59]),
                    Boolean.parseBoolean(data[60]), Boolean.parseBoolean(data[61]), Boolean.parseBoolean(data[62])));
        else if (data.length > 20)
            teamWRs.add(new PlayerWR(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38])));
        else
            teamWRs.add(new PlayerWR(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadTESaveData(String[] data, boolean isRedshirt) {
        if (data.length > 34)
                teamTEs.add(new PlayerTE(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                        Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                        Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                        Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                        Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                        Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                        Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                        Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                        Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                        Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                        Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                        Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                        Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                        Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                        Integer.parseInt(data[38]), Integer.parseInt(data[39]),
                        Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                        Boolean.parseBoolean(data[42]), Boolean.parseBoolean(data[43]),
                        Integer.parseInt(data[44]), Integer.parseInt(data[45]),
                        Boolean.parseBoolean(data[46]), Boolean.parseBoolean(data[47]),
                        Boolean.parseBoolean(data[48]), Boolean.parseBoolean(data[49]), Boolean.parseBoolean(data[50])
                ));
        else if (data.length > 20)
            teamTEs.add(new PlayerTE(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32])));
        else
            teamTEs.add(new PlayerTE(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadOLSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 28)
            teamOLs.add(new PlayerOL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Boolean.parseBoolean(data[30]), Boolean.parseBoolean(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Boolean.parseBoolean(data[34]), Boolean.parseBoolean(data[35]),
                    Boolean.parseBoolean(data[36]), Boolean.parseBoolean(data[37]), Boolean.parseBoolean(data[38])
            ));
        else if (data.length > 20)
            teamOLs.add(new PlayerOL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26])));
        else
            teamOLs.add(new PlayerOL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadKSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 32)
            teamKs.add(new PlayerK(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Boolean.parseBoolean(data[38]), Boolean.parseBoolean(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Boolean.parseBoolean(data[42]), Boolean.parseBoolean(data[43]),
                    Boolean.parseBoolean(data[44]), Boolean.parseBoolean(data[45]), Boolean.parseBoolean(data[46])
            ));
        else if (data.length > 20)
            teamKs.add(new PlayerK(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30])));
        else
            teamKs.add(new PlayerK(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadDLSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 32)
            teamDLs.add(new PlayerDL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Boolean.parseBoolean(data[38]), Boolean.parseBoolean(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Boolean.parseBoolean(data[42]), Boolean.parseBoolean(data[43]),
                    Boolean.parseBoolean(data[44]), Boolean.parseBoolean(data[45]), Boolean.parseBoolean(data[46])
            ));
        else if (data.length > 20)
            teamDLs.add(new PlayerDL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30])));
        else
            teamDLs.add(new PlayerDL(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadLBSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 32)
            teamLBs.add(new PlayerLB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Boolean.parseBoolean(data[38]), Boolean.parseBoolean(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Boolean.parseBoolean(data[42]), Boolean.parseBoolean(data[43]),
                    Boolean.parseBoolean(data[44]), Boolean.parseBoolean(data[45]), Boolean.parseBoolean(data[46])
            ));
        else if (data.length > 20)
            teamLBs.add(new PlayerLB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30])));
        else
            teamLBs.add(new PlayerLB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadCBSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 41)
            teamCBs.add(new PlayerCB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38]), Integer.parseInt(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Integer.parseInt(data[42]), Integer.parseInt(data[43]),
                    Integer.parseInt(data[44]), Integer.parseInt(data[45]),
                    Integer.parseInt(data[46]), Integer.parseInt(data[47]),
                    Integer.parseInt(data[48]), Integer.parseInt(data[49]),
                    Integer.parseInt(data[50]), Integer.parseInt(data[51]),
                    Integer.parseInt(data[52]),
                    Boolean.parseBoolean(data[53]), Boolean.parseBoolean(data[54]),
                    Integer.parseInt(data[55]), Integer.parseInt(data[56]),
                    Boolean.parseBoolean(data[57]), Boolean.parseBoolean(data[58]),
                    Boolean.parseBoolean(data[59]), Boolean.parseBoolean(data[60]), Boolean.parseBoolean(data[61])
            ));
        else if (data.length > 20)
            teamCBs.add(new PlayerCB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Integer.parseInt(data[38]), Integer.parseInt(data[39])));
        else
            teamCBs.add(new PlayerCB(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }

    private void loadSSaveData(String[] data, boolean isRedshirt) {
        if (data.length > 32)
            teamSs.add(new PlayerS(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30]), Integer.parseInt(data[31]),
                    Integer.parseInt(data[32]), Integer.parseInt(data[33]),
                    Integer.parseInt(data[34]), Integer.parseInt(data[35]),
                    Integer.parseInt(data[36]), Integer.parseInt(data[37]),
                    Boolean.parseBoolean(data[38]), Boolean.parseBoolean(data[39]),
                    Integer.parseInt(data[40]), Integer.parseInt(data[41]),
                    Boolean.parseBoolean(data[42]), Boolean.parseBoolean(data[43]),
                    Boolean.parseBoolean(data[44]), Boolean.parseBoolean(data[45]), Boolean.parseBoolean(data[46])
            ));
        else if (data.length > 20)
            teamSs.add(new PlayerS(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]), Boolean.parseBoolean(data[9]),
                    Integer.parseInt(data[10]), Integer.parseInt(data[11]), isRedshirt,
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]),
                    Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18]), Integer.parseInt(data[19]),
                    Integer.parseInt(data[20]), Integer.parseInt(data[21]),
                    Integer.parseInt(data[22]), Integer.parseInt(data[23]),
                    Integer.parseInt(data[24]), Integer.parseInt(data[25]),
                    Integer.parseInt(data[26]), Integer.parseInt(data[27]),
                    Integer.parseInt(data[28]), Integer.parseInt(data[29]),
                    Integer.parseInt(data[30])));
        else
            teamSs.add(new PlayerS(this, data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Boolean.parseBoolean(data[7]), Boolean.parseBoolean(data[8]),
                    Integer.parseInt(data[9]), Integer.parseInt(data[10]), isRedshirt, Integer.parseInt(data[13]),
                    Integer.parseInt(data[14]), Integer.parseInt(data[15]), Integer.parseInt(data[16]), Integer.parseInt(data[17]),
                    Integer.parseInt(data[18])));
    }
}
