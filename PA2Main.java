/*
* AUTHOR: Hamzah Firman
* FILE: PA2Main.java
* ASSIGNMENT: Programming Assignment 2 - Job Skills
* COURSE: CSc 210; Section C; Summer 2020
* PURPOSE: This program reads in two-to-three command-line arguments
* where the first one is a csv file contains various job skills, second
* is a Command (either 'CATCOUNTS' or 'LOCATIONS') to run on the data from 
* the input file and third is a job category. The 'CATCOUNTS' command will
* print out the category and the number of jobs in that category. Then, the
* 'LOCATIONS' command will print out all locations that have a position in 
* that category.
* 
* USAGE:
* java PA2Main infileName COMMAND optional 
* 
* where 'infileName' is the name of the directory of the input filename
*, 'COMMAND' is the chosen command to run on the data and 'optional' 
* is a job category when LOCATIONS command is chosen. 
* * ----------- EXAMPLE INPUT -------------
* Input files:
* ------------------------------------------------------------------
* Google, User Experience Research Intern Summer 2018, User Experience &
* Design, Mountain View CA United States, Design and conduct user studies 
* to gauge the usability of new and existing Google features.Make concrete 
* data-driven recommendations for change based on your findings.Communicate 
* with team members throughout the process., Currently pursuing a Bachelors 
* degree in human computer interaction cognitive psychology information science
* statistics data science or a related field and graduating in 2018 2019 or 
* 2020.Must be currently enrolled in a full-time degree program and returning 
* to the program after completion of the internship.Experience conducting user
* research.Authorization to work in the United States., Currently pursuing a 
* MS or PhD in human computer interaction cognitive psychology information 
* science statistics data science or a related field and graduating in 2018 
* 2019 or 2020.Relevant work/project experience in an applied research setting
* including but not limited to integrating user research into product designs 
* and design practices.Knowledge of statistics and the principles of experiment
* design.Strong understanding of strengths and shortcomings of different research
* methods including when and how to apply them during each product phase.
* Excellent analytical interpersonal communication negotiation and collaboration 
* skills.
* ---------------------------------------------------------------------
 */

// All necessary Imports:
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PA2Main {
    public static void main(String[] args) {
        // ---------- DATA CONSTRUCTOR ----------
        // METHOD #1:
        // Reads every lines in the input file and returns the
        // a list of arrays where each array represents a single
        // line of data from the file
        List<String[]> allInfileLines = readInfile(args[0]);
        // METHOD #2:
        // Stores all job categories and their number of opening positions
        // in various cities in a Hash Map and returns it as the following:
        // {Business Strategy=[Count, 98, So Paulo Brazil, 2, Mountain View CA
        // United States, 26,Tokyo Japan, 1, Seoul South Korea, 5, Sunnyvale CA
        // United States, 10, .. ]
        Map<String, List<String>> collectedJobInfo = collectJobInfo(
                allInfileLines);

        // INTIALIZATION #1:
        // An list of keys (job titles) in an ordered format
        ArrayList<String> sortedKeys = new ArrayList<String>(
                collectedJobInfo.keySet());
        Collections.sort(sortedKeys);

        // ----------- COMMANDS -----------

        String userCmmnd = args[1]; // Input Command

        // CONDITION #1:
        // Checks whether the given COMMAND is valid or not

        if (userCmmnd.equals("CATCOUNT")) {
            // METHOD #3:
            // Executes the CatCOUNT command
            catCountCmmnd(sortedKeys, collectedJobInfo);

        } else if (userCmmnd.equals("LOCATIONS")) {


            // METHOD #4:
            // Executes the LOCATIONS Command
            String theCategory = checkCategory(args);
            // METHOD #5
            printCityCounts(theCategory, collectedJobInfo);

        } else {
            // Prints out "Invalid Command" if the command is
            // invalid
            System.out.println("Invalid Command");
        }
    }
    
    /*
     * Purpose: A method thats reads a input file and stores all of its
     * contents (job skills) into a list of arrays and returns it.
     * 
     * @param infileDir, a directory of the input file
     * 
     * @return allLines, a list of arrays that contains various job skills
     *
     */
    public static List<String[]> readInfile(String infileDir) {

        // INITIALIZATIONS:
        // #1 fileJobs - Scans the input files and reads every lines
        Scanner fileJobs = null;
        // #2 allCounts - Contains all Job counts as a whole and in various
        // cities
        List<String[]> allLines = new ArrayList<String[]>();
        // #3 count - integer
        int count = 0;

        // Try and Catch to check if the input file is exist
        try {
            fileJobs = new Scanner(new File(infileDir));
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // an error message will be printed out.
        }
        // LOOP #1:
        // Parses through the input file and prints every lines.
        while(fileJobs.hasNext()) {
            // Ignores the first line
            if (count != 0) {
                String[] arrPerLine = fileJobs.nextLine().split(",");
                allLines.add(arrPerLine); // Adds a line of job information

            } else {
                fileJobs.nextLine();
                count += 1;
            }
        }
        return allLines; // RETURN line
    }

    /*
     * Purpose: A method that takes an list of arrays contains of different job
     * skills and each category and the number of opening positions in
     * various cities into a hash map.
     * 
     * @param listOfLines, a list of arrays that contains various job skills
     * 
     * @return allJobsInfo, a hash map contains all job categories and their
     * number of opening positions in cities around the world
     */
    public static Map<String, List<String>> collectJobInfo(
            List<String[]> listOfLines) {

        // INITILIZATION #1:
        // A hash map where the keys are job categories and the values are
        // lists of number of openings in various cities across the world
        Map<String, List<String>> allJobsInfo = null;
        allJobsInfo = new HashMap<String, List<String>>();

        // LOOP #1:
        // Parses the given parameter (list of arrays)
        for (int i = 0; i < listOfLines.size(); i++) {
            // INITILIZATIONS:
            // #3 - A job category in a string format
            String jobCat = listOfLines.get(i)[2];
            // #4 - A location in a string format
            String jobLoc = listOfLines.get(i)[3];

            // CONDITION #1:
            // Checks if a job title is presence in the hash map
            if (allJobsInfo.containsKey(jobCat)) {

                // #5 - A list of all counts of opening positions in various
                // cities
                List<String> currListCounts = allJobsInfo.get(jobCat);

                // #6 - The current sum of a job category in a string format
                String strJobCount = currListCounts.get(1);
                // #7 - Increments the current sum by 1
                int newJobCount = Integer.valueOf(strJobCount) + 1;

                currListCounts.set(1, Integer.toString(newJobCount)); // replaces
                                                                      // the
                                                                      // current
                                                                      // sum of
                                                                      // that
                                                                      // category

                // #7 - The index of the current city in the list
                int cityIndx = currListCounts.indexOf(jobLoc);
                // METHOD #1:
                // Helps to check the presence of the current city in the list
                // and increments by 1 if found or set to 1 if new
                cityCheck(currListCounts, jobLoc, cityIndx);
            } else {
                // #8 - A list of strings to store all job counts and openings
                List<String> listCounts = new ArrayList<String>();
                // Initiates the first count of the job and the job in the
                // current city
                listCounts.add("Count");
                listCounts.add("1");
                listCounts.add(jobLoc);
                listCounts.add("1");
                allJobsInfo.put(jobCat, listCounts);
            }
        }
        return allJobsInfo; // RETURN line
    }

    /*
     * Purpose: A helper method that checks if the city name and count are
     * exists in the current array list. Sets to 1 if both are not found, and
     * adds 1 is found.
     * 
     * @param currListVal, a list of all jobs counts as a whole and various
     * cities
     * 
     * @param cityName, a current city name in string format
     * 
     * @param cityIndx, an integer that represents the index of the current city
     * name
     *
     * @return None.
     */
    public static void cityCheck(List<String> currListVal, String cityName,
            int cityIndx) {

        // CONDITION #1:
        // If the city name is presence
        if (cityIndx != -1) {
            // Increment job counts in this city by 1
            String strCityCount = currListVal.get(cityIndx + 1);
            int newCityCount = Integer.valueOf(strCityCount) + 1;
            // Replaces the current count with new count
            currListVal.set(cityIndx + 1, Integer.toString(newCityCount));
        } else {

            // Adds city name
            currListVal.add(cityName);
            // Adds number of jobs in this city
            currListVal.add("1");
        }
    }

    /*
     * Purpose: A method that runs when 'CATCOUNT' command is called and prints
     * the count of every job categories in an ordered format.
     * 
     * @param allKeys, a sorted array list of all keys (job categories)
     * 
     * @param allJobsInfo, a hash map contains all job categories and their
     * number of opening positions in cities around the world
     *
     * @return None.
     */
    public static void catCountCmmnd(ArrayList<String> allKeys,
            Map<String, List<String>> allJobsInfo) {

        System.out.println("Number of positions for each category");
        System.out.println("-------------------------------------");
        // LOOP #1:
        // Parses through the sorted keys in a List
        for (int aKey = 0; aKey < allKeys.size(); aKey++) {
            // VARIABLE #1: A list of all counts of position category as a whole
            // and in various cities Ex: [Count, 1, Houston 2, Miami 4]
            List<String> listOfPositionCounts = allJobsInfo
                    .get(allKeys.get(aKey));

            // VARIABLE #2: A total of a position for a category
            String totalPositions = listOfPositionCounts.get(1);
            // Prints out the number of positions for each category in an
            // ordered format
            System.out.println(allKeys.get(aKey) + ", " + totalPositions);

        }

    }

    /*
     * Purpose: A method that takes the command-line arguments and returns the
     * given job category from the command-line arguments.
     * 
     * @param allArgs, an array of strings (infile, COMMAND, optional)
     *
     * @return category, a string of the given category
     */
    public static String checkCategory(String[] allArgs) {
        // INITIALIZATION #1
        String category = "";

        if (allArgs.length == 3) {
            category = allArgs[2];
        } else {
            // LOOP #1
            // Parses through the command-line arguments
            for (int i = 2; i <= allArgs.length - 2; i++) {
                category += allArgs[i] + " ";
            }
            category += allArgs[allArgs.length - 1];
        }

        System.out.println("LOCATIONS for category: " + category);
        System.out.println("-------------------------------------");

        return category; // RETURN line

    }

    /*
     * Purpose: A method that prints runs when 'LOCATIONS' command is called and
     * prints all counts of opening positions from cities around the world of
     * the chosen category.
     * 
     * @param jobCat, a string of the given job category.
     * 
     * @param allJobsInfo, a hash map contains all job categories and their
     * number of opening positions in cities around the world.
     *
     * @return None.
     */
    public static void printCityCounts(String jobCat,
            Map<String, List<String>> allJobsInfo) {

        // CONDITION #1
        // Checks if category exists in the hash map
        if (allJobsInfo.containsKey(jobCat)) {
            // VARIABLE #1: A list of count of positions for the chosen category
            List<String> allNumberOfPositions = allJobsInfo.get(jobCat);
            // Temporary HashMap for all necessary counts in the list
            Map<String, String> temp = new HashMap<String, String>();

            // LOOP#1:
            // Traverse through the number of positions in every cities.
            for (int city = 2; city < allNumberOfPositions.size(); city += 2) {
                String cityName = allNumberOfPositions.get(city); // City name
                String cityCount = allNumberOfPositions.get(city + 1); // Total
                                                                       // count
                temp.put(cityName, cityCount);
            }
            // Sorts all keys in the 'temp' HashMap
            ArrayList<String> sortedCities = new ArrayList<String>(
                    temp.keySet());
            Collections.sort(sortedCities);

            // LOOP#2
            // Parses through the sorted array list of all keys 
            // Prints all number of positions of the given category in the
            // collected cities (in an ordered format)
            for (int key = 0; key < sortedCities.size(); key++) {
                System.out.println(sortedCities.get(key) + ", "
                        + temp.get(sortedCities.get(key)));
            }
        }
    }
}