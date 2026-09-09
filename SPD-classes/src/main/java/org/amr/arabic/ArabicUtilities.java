package org.amr.arabic;

import java.util.ArrayList;
import java.util.List;

/**
 * نسخة منظّفة من ArabicUtilities لمشروع Shattered Pixel Dungeon.
 * أُزيلت اعتماديات أندرويد، وصُحح تعبير الفصل ليصبح \\s
 */
public class ArabicUtilities {

    private static boolean isArabicCharacter(char target){
        for(int i = 0; i < ArabicReshaper.ARABIC_GLPHIES.length; i++){
            if(ArabicReshaper.ARABIC_GLPHIES[i][0] == target)
                return true;
        }
        for(int i = 0; i < ArabicReshaper.HARAKATE.length; i++){
            if(ArabicReshaper.HARAKATE[i] == target)
                return true;
        }
        return false;
    }

    private static String[] getWords(String sentence){
        if (sentence != null) {
            return sentence.split("\\s");
        } else {
            return new String[0];
        }
    }

    public static boolean hasArabicLetters(String word){
        for(int i = 0; i < word.length(); i++){
            if(isArabicCharacter(word.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean isArabicWord(String word){
        for(int i = 0; i < word.length(); i++){
            if(!isArabicCharacter(word.charAt(i)))
                return false;
        }
        return true;
    }

    private static String[] getWordsFromMixedWord(String word){
        List<String> finalWords = new ArrayList<String>();
        String tempWord = "";
        for(int i = 0; i < word.length(); i++){
            if(isArabicCharacter(word.charAt(i))){
                if(!tempWord.equals("") && !isArabicWord(tempWord)) {
                    finalWords.add(tempWord);
                    tempWord = "" + word.charAt(i);
                } else {
                    tempWord += word.charAt(i);
                }
            } else {
                if(!tempWord.equals("") && isArabicWord(tempWord)){
                    finalWords.add(tempWord);
                    tempWord = "" + word.charAt(i);
                } else {
                    tempWord += word.charAt(i);
                }
            }
        }
        if (!tempWord.equals("")) {
            finalWords.add(tempWord);
        }
        String[] theWords = new String[finalWords.size()];
        theWords = finalWords.toArray(theWords);
        return theWords;
    }

    public static String reshape(String allText) {
        if (allText != null) {
            StringBuffer result = new StringBuffer();
            String[] sentences = allText.split("\n");
            for (int i = 0; i < sentences.length; i++) {
                result.append(reshapeSentence(sentences[i]));
                if (i < sentences.length - 1) {
                    result.append("\n");
                }
            }
            return result.toString();
        } else {
            return null;
        }
    }

    public static String reshapeSentence(String sentence){
        String[] words = getWords(sentence);
        StringBuffer reshapedText = new StringBuffer("");
        for(int i = 0; i < words.length; i++){
            if(hasArabicLetters(words[i])){
                if(isArabicWord(words[i])){
                    ArabicReshaper arabicReshaper = new ArabicReshaper(words[i]);
                    reshapedText.append(arabicReshaper.getReshapedWord());
                } else {
                    String[] mixedWords = getWordsFromMixedWord(words[i]);
                    for(int j = 0; j < mixedWords.length; j++){
                        ArabicReshaper arabicReshaper = new ArabicReshaper(mixedWords[j]);
                        reshapedText.append(arabicReshaper.getReshapedWord());
                    }
                }
            } else {
                reshapedText.append(words[i]);
            }
            if (i < words.length - 1) {
                reshapedText.append(" ");
            }
        }
        return reshapedText.toString();
    }
}