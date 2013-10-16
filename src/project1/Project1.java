/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import project1.BSTDictionary.DictEntry;

/**
 *
 * @author Daniel
 */
public class Project1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BSTDictionary<Integer, Double> dictWordLengths;
        try {
            WordGenerator wordGenerator = createWordGenerator("english_word_list.txt", 10);
            
            Scanner scanner = new Scanner(System.in);
            while (true)  {
                System.out.print("\nEnter a value for n (0 to quit): ");
                int n = scanner.nextInt();
                if (n == 0)
                    return;
                for (int i=0; i<100; i++) {
                    System.out.println(wordGenerator.generateRandomWord(3));
                }
            }//end while
            
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        
        //testDictionary();
        
        
        
    }
    
    public static WordGenerator createWordGenerator(String filename, int n) throws FileNotFoundException {
        System.out.println("Generating trees...");
        long timer = System.currentTimeMillis();
        WordGenerator wordGenerator = new WordGenerator("english_word_list.txt", n);
        timer = System.currentTimeMillis()-timer;
        System.out.println("Trees generated in " + timer + " milliseconds!");
        return wordGenerator;
    }
    
    public static void testWordFrequencies() throws FileNotFoundException {
        BSTDictionary<Integer, Double> dictWordLengths;
        dictWordLengths = WordGeneratorBackup.createDictWordLengths("english_word_list.txt");
        
        Iterator iterator = dictWordLengths.iterator();
        
        while (iterator.hasNext()) {
            DictEntry entry = (DictEntry) iterator.next();
            System.out.println(entry.key + " - " + entry.value);
        }
        
        //System.out.println(dictWordLengths.toString());
    }
    
    public static void testLetterFrequencies() throws FileNotFoundException {
        BSTDictionary<String, BSTDictionary<String, Double>> dict;
        dict = WordGeneratorBackup.createDictLetterFrequencies("english_word_list.txt", 3);
        
        //Iterator iterator = dict.iterator();
        
        //System.out.println(dict.toString());
        
        System.out.println(dict.getValue("aar"));
    }
    
    
    public static void testGenerateKey() throws FileNotFoundException {
        BSTDictionary<String, BSTDictionary<String, Double>> dict;
        dict = WordGeneratorBackup.createDictLetterFrequencies("english_word_list.txt", 3);
        
        BSTDictionary smallDict = dict.getValue("aar");
        
        int[] frequencies = new int[3];
        for (int i=0; i<3; i++)
            frequencies[i] = 0;
        
        System.out.println(smallDict.toString());
        
        System.out.println("Generating random keys...");
        
        for (int i=0; i<10000; i++) {
            String key = (String)WordGeneratorBackup.generateKey(smallDict);
            
            if (key.equals("d"))
                frequencies[0]++;
            else if (key.equals("s"))
                frequencies[1]++;
            else if (key.equals("t"))
                frequencies[2]++;
        }//end for

        System.out.println("d - " + frequencies[0]);
        System.out.println("s - " + frequencies[1]);
        System.out.println("t - " + frequencies[2]);
    }
    
    public static void testDictionary() {
        BSTDictionary<Integer, String> dict = new BSTDictionary<Integer, String>();
        
        dict.add(3, "Sloth");
        dict.add(1, "Yes");
        dict.add(2, "No");
        dict.add(4, "Banana");
        dict.add(5, "WHOO");
        
        System.out.println(dict.toString());
        
        Iterator<DictEntry<Integer, String>> iterator = dict.iterator();
        
        while (iterator.hasNext()) {
            System.out.println(iterator.next().value);
        }//end while
        
        System.out.println(dict.contains(3));
        System.out.println(dict.contains(6));
        
        System.out.println(dict.getValue(2));
        System.out.println(dict.getValue(7));
    }
}
