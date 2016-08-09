package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    static int wordLength = DEFAULT_WORD_LENGTH;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static HashSet<String> wordSet = new HashSet<String>();
    private static ArrayList<String> wordList = new ArrayList<String>();
    private static HashMap<String, ArrayList> lettersToWord = new HashMap<String, ArrayList>();
    private static HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while((line = in.readLine()) != null) {
            String word = line.trim();


            wordSet.add(word);
            wordList.add(word);


            if(!sizeToWords.containsKey(word.length())){
                ArrayList<String> olo = new ArrayList<String>();
                olo.add(word);
                sizeToWords.put(word.length(), olo);
            }
            else{
                ArrayList<String> ko = sizeToWords.get(word.length());
                ko.add(word);
                sizeToWords.put(word.length(), ko);
            }



            String key = alphaMaker(word);

            if(!lettersToWord.containsKey(key)){
                ArrayList<String> lol = new ArrayList<String>();
                lol.add(word);
                lettersToWord.put(key, lol);
            }
            else{
                ArrayList<String> ok = lettersToWord.get(key);
                ok.add(word);
                lettersToWord.put(key, ok);
            }

        }


    }

    String alphaMaker(String word) {
        char[] alphaword = word.toCharArray();
        Arrays.sort(alphaword);
        StringBuilder sb = new StringBuilder(alphaword.length);
        for (char c : alphaword) sb.append(c);
        return sb.toString();
    }
    public boolean isGoodWord(String word, String base) {
        if(AnagramDictionary.wordSet.contains(word)) return true;
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char [] tword = word.toCharArray();
        char ch = 97;
        for(int i = 0; i < 26; i++) {
            String testing = word;
            testing += ch;
            ch++;
            if (AnagramDictionary.lettersToWord.containsKey(alphaMaker(testing))){
                result.addAll(AnagramDictionary.lettersToWord.get(alphaMaker(testing)));
            }
        }
        for(int i = 0; i < result.size(); i++) {
            if (result.get(i).contains(word)) {
                result.remove(i);
                i--;
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        String yolo = AnagramDictionary.sizeToWords.get(wordLength).get(random.nextInt(AnagramDictionary.sizeToWords.get(wordLength).size() - 1));
        //String yolo = AnagramDictionary.wordList.get(random.nextInt(9999)); Old static 9999 code
        while(true) { // dont ever do this. ever.
            if (getAnagramsWithOneMoreLetter(yolo).size() >= AnagramDictionary.MIN_NUM_ANAGRAMS) {
                if(wordLength < AnagramDictionary.MAX_WORD_LENGTH) wordLength++;
                return yolo; //YOLO
            }
            yolo = (AnagramDictionary.sizeToWords.get(wordLength).get((AnagramDictionary.sizeToWords.get(wordLength).indexOf(yolo)+ 1) % AnagramDictionary.sizeToWords.get(wordLength).size()));
        }
    }
}
