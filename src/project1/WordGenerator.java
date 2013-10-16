/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Daniel
 */
public class WordGenerator {
    
    private BSTDictionary<Integer, Double> dictWordLengths;
    private BSTDictionary<String, BSTDictionary<String, Double>>[] dictLetterFrequencies;
    
    private String filename;
    private int n;
    
    public WordGenerator(String filename, int n) throws FileNotFoundException {
        this.filename = filename;
        this.n = n;
        initialize();
    }
    
    private void initialize() throws FileNotFoundException {
        dictWordLengths = createDictWordLengths(filename);
        dictLetterFrequencies = new BSTDictionary[n];
        
        for (int i=0; i<n; i++)
            dictLetterFrequencies[i] = createDictLetterFrequencies(filename, i+1);
    }
    
    public String generateRandomWord(int n) throws FileNotFoundException {
        if (n > this.n) {
            this.n = n;
            initialize();
        }
        
        int length = (int)generateKey(dictWordLengths);
        
        String randomWord = generateRandomLetter();
        
        for (int i=0; i<length-1; i++) {
            String substring;
            if (i<n)
                substring = randomWord.substring(0, i+1);
            else
                substring = randomWord.substring(i-n+1, i+1);
            
            String nextLetter = (String)generateKey(dictLetterFrequencies[substring.length()-1].getValue(substring));
            
            randomWord += nextLetter;
        }// end for
        
        return randomWord;
    }
    
    private String generateRandomLetter() {
        char c = 'a';
        int rand = (int)(Math.random()*26);
        
        c += (char)rand;
        
        return String.valueOf(c);
    }
    
    private BSTDictionary createDictWordLengths(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        
        BSTDictionary<Integer, Double> dictWordLengths = new BSTDictionary<Integer, Double>();
        
        int numOfWords = 0;
        //counts the number of words in the text file
        while (scanner.hasNextLine()) {
            numOfWords++;
            scanner.nextLine();
        }//end while
        
        scanner = new Scanner(new File(filename));
        
        while(scanner.hasNextLine()) {
            String word = scanner.nextLine();
            int length = word.length();
            //double frequency = 1 / (double)numOfWords;
            double frequency = 1;
            
            if (dictWordLengths.contains(length))
                frequency += dictWordLengths.getValue(length);
            
            dictWordLengths.add(length, frequency);
            //System.out.println(length + " - " + frequency);
        }
        
        return reduceToFrequencies(dictWordLengths);
    }
    
    private BSTDictionary createDictLetterFrequencies(String filename, int n) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        
        BSTDictionary<String, BSTDictionary<String, Double>> dict;
        dict = new BSTDictionary<>();
        
        
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            
            for (int i=0; i<s.length()-n; i++) {
                String outerKey = s.substring(i, i+n);
                
                dict.add(outerKey, new BSTDictionary<String, Double>());
            }// end for
        }
        
        
        scanner = new Scanner(new File(filename));
        
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            
            for (int i=0; i<s.length()-n; i++) {
                String outerKey = s.substring(i, i+n);
                String innerKey = s.substring(i+n, i+n+1);
                
                //dict.add(outerKey, new BSTDictionary<String, Double>());
                
                double numOccurrences = 0;
                if (dict.getValue(outerKey).contains(innerKey) == false)
                    numOccurrences = 1;
                else 
                    numOccurrences = dict.getValue(outerKey).getValue(innerKey) + 1;
                    
                dict.getValue(outerKey).add(innerKey, numOccurrences);
            }// end for
        }// end while
        
        Iterator<BSTDictionary.DictEntry<String, BSTDictionary<String, Double>>> iterator;
        iterator = dict.iterator();
        
        while (iterator.hasNext()) {
            BSTDictionary.DictEntry entry = iterator.next();
            BSTDictionary innerDict = (BSTDictionary<String, Double>)entry.value;
            dict.add((String)entry.key, reduceToFrequencies(innerDict));
        }
        
        return dict;
    }
    
    
    /**
     * 
     * @param d     A <code>BSTDictionary</code> object that contains
     *              <code>double</code> values.
     * @return      A random key based on the statistics given by the
     *              <code>double</code> values.<br>
     *              
     */
    private Object generateKey(BSTDictionary d) {
        if (d == null)
            return generateRandomLetter();
        
        Iterator<BSTDictionary.DictEntry> iterator = d.iterator();
        
        double rand = Math.random();
        
        while (iterator.hasNext()) {
            BSTDictionary.DictEntry entry = iterator.next();
            
            if (rand < (double)entry.value)
                return entry.key;
            else
                rand -= (double)entry.value;
        }//end while
        
        return null;
    }
    
    
    /*
     * Takes a Dictionary where the values are only the number of occurences of
     * the keys, and reduces the values to percentage frequencies.
     */
    private BSTDictionary reduceToFrequencies(BSTDictionary dict) {
        Iterator<BSTDictionary.DictEntry<Object, Double>> iterator;
        iterator = dict.iterator();
        
        double total = 0;
        
        while (iterator.hasNext()) {
            total += iterator.next().value;
        }
        
        iterator = dict.iterator();
        
        while (iterator.hasNext()) {
            BSTDictionary.DictEntry<Object, Double> entry = iterator.next();
            Comparable key = (Comparable) entry.key;
            double value = entry.value / total;
            
            dict.add(key, value);
        }
        
        return dict;
    }
    
}
