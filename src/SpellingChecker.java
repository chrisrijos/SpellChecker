/**@author: Christopher Rijos
 * Implements a spelling checker that builds a dictionary
 * and recommends possible spelling solutions
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Scanner;

public class SpellingChecker implements Runnable{

    /*Sets dynamic paths for spellchecker by retrieving users working directories*/
    String workingDir = System.getProperty("user.dir");//stores uses working director
    String DICT_FILENAME = "\\src\\dictionary.txt";
    String TEST_FILENAME = "\\src\\testTextFile.txt";
    String D_PATH = workingDir + DICT_FILENAME;
    String F_PATH = workingDir + TEST_FILENAME;

    public DictionaryTable DICT_MAP; //builds instance of Dictionary class as a HashTable
    char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    SpellingChecker(){
    /*Constructs SpellingChecker*/
        //System.out.println(D_PATH);
        DICT_MAP = new DictionaryTable();
        DICT_MAP.build(D_PATH);
    }

    @Override
    public void run() {
    /*Takes user input and prints suggestions*/
        Scanner kb = null;

        try {
            kb = new Scanner(new File(F_PATH));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;

        while(kb.hasNext()) {
            System.out.print("\nEnter a word to SpellCheck: ");
            line = kb.next();
            if (line.equals("")) {
                break;
            }
            if (DICT_MAP.contains(line)) {
                System.out.println("\n\t Spelling is correct for: " + line);
            } else {
                System.out.print("\nSpelling for " + line + " is incorrect");
                System.out.println(suggestWords(line));
            }
        }
    }

    private String suggestWords(String line) {
    /*Suggest words by integrating the swapCharacter */
        StringBuilder suggestWord = new StringBuilder();
        ArrayList<String> listOfSuggested = swapLetters(line);
        if(listOfSuggested.size() == 0) {
            return " no suggestions can be made. \n";
        }
        suggestWord.append(" here are some suggestions: \n");
        for(String input : listOfSuggested){
            suggestWord.append("\n -" + input);
        }
        return suggestWord.toString();
    }
    public ArrayList<String> swapLetters(String line) {
    /*Uses foreach loop to swap first and last letters of lined passed in*/
        ArrayList<String> swappedWord = new ArrayList();
        for(char character : letters){ //iterates over character array to swap letters
            String firstLetter = character + line;
            String lastLetter = line + character;
            if(DICT_MAP.contains(firstLetter)){
                swappedWord.add(firstLetter);
            }
            if(DICT_MAP.contains(lastLetter)){
                swappedWord.add(lastLetter);
            }
        }
        return swappedWord;
    }
    public ArrayList<String> letterMissing(String line) {
        ArrayList<String> charToReturn = new ArrayList();
        int len = line.length() - 1;
        //try removing char from the front
        if (DICT_MAP.contains(line.substring(1))) {
            charToReturn.add(line.substring(1));
        }
        for (int i = 1; i < len; i++) {
            //try removing each char between (not including) the first and last
            String working = line.substring(0, i);
            working = working.concat(line.substring((i + 1), line.length()));
            if (DICT_MAP.contains(working)) {
                charToReturn.add(working);
            }
        }
        if (DICT_MAP.contains(line.substring(0, len))) {
            charToReturn.add(line.substring(0, len));
        }
        return charToReturn;
    }
    private ArrayList<String> charsSwapped(String input) {
        ArrayList<String> charToReturn = new ArrayList();

        for (int i = 0; i < input.length() - 1; i++) {
            String working = input.substring(0, i);// System.out.println("    0:" + working);
            working = working + input.charAt(i + 1);  //System.out.println("    1:" + working);
            working = working + input.charAt(i); //System.out.println("    2:" + working);
            working = working.concat(input.substring((i + 2)));//System.out.println("    FIN:" + working);
            if (DICT_MAP.contains(working)) {
                charToReturn.add(working);
            }
        }
        return charToReturn;
    }
}
