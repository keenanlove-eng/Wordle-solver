import java.io.*;
import java.util.*;

public class WWord {
    private String word;
    private String ans;
    public ArrayList<String> solutions;
    private String guess;
    private ArrayList<String> legalwords;
    private ArrayList<String> solutionwords;
    private int[] letterCounts = new int[26];
    private int[][] positionCounts = new int[5][26];
    public WWord(String word) {
        this.word = word;
        this.ans = word;
        legalwords = new ArrayList<String>();
        solutionwords = new ArrayList<String>();
    }

    public WWord(ArrayList<String> solutions, String guess) {
        this.solutions = solutions;
        this.guess = guess;
        legalwords = new ArrayList<String>();
        solutionwords = new ArrayList<String>();
    }

    private void countLetters() {
        letterCounts = new int[26];
        positionCounts = new int[5][26];

        for (String word : solutionwords) {
            boolean[] seen = new boolean[26];
            for (int i = 0; i < 5; i++) {
                int c = Character.toLowerCase(word.charAt(i)) - 'a';
                if (c < 0 || c >= 26) {
                    continue;
                }

                positionCounts[i][c]++;
                if (!seen[c]) {
                    seen[c] = true;
                    letterCounts[c]++;
                }
            }
        }
    }
    
    public int ScoreWord(String word){
        int score = 0;
        boolean[]seen=new boolean[26];
        for(int i=0;i<5;i++){
            int c = Character.toLowerCase(word.charAt(i)) - 'a';
            if(c<0||c>=26){
                continue;
            } 
            score+=positionCounts[i][c];
            if(!seen[c]){
                seen[c]=true;
                score+=letterCounts[c];
            }
        }
        return score;
    }

    public void printCounts() {
        for (int c = 0; c < 26; c++) {
            if (letterCounts[c] == 0) {
                continue;
            }

            char letter = (char) ('a' + c);
            System.out.print(letter + " appears in " + letterCounts[c] + " words: ");
            for (int position = 0; position < 5; position++) {
                System.out.print(positionCounts[position][c]);
                if (position < 4) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public ArrayList<String> getLegalWords(File file)throws IOException{
        legalwords = readWords(file);
        return new ArrayList<>(legalwords);
    }
    public ArrayList<String> getSolutionWords(File file)throws IOException{
        solutionwords = readWords(file);
        countLetters();
        return new ArrayList<>(solutionwords);
    }

    public ArrayList<String> getLegalWordList() {
        return new ArrayList<>(legalwords);
    }

    public ArrayList<String> getSolutionWordList() {
        return new ArrayList<>(solutionwords);
    }

    private ArrayList<String> readWords(File file) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            input.useDelimiter("[\\s,\\[\\]\"]+");
            while (input.hasNext()) {
                String nextWord = input.next().trim().toLowerCase();
                if (nextWord.length() == 5 && !words.contains(nextWord)) {
                    words.add(nextWord);
                }
            }
        }
        return words;
    }
    
    
    
    public ArrayList<String> splitW(String word){
        ArrayList<String> charList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            charList.add(String.valueOf(word.charAt(i)));
        }
        return charList;
    }
    public ArrayList<String> findDoubles(String word) {
        ArrayList<String> doubles = new ArrayList<>();
    
        for (int i = 0; i < word.length() - 1; i++) {
            for (int a = i + 1; a < word.length(); a++) {
           
                if (word.charAt(i) == word.charAt(a)) {
                    String letter = String.valueOf(word.charAt(i));
                
                    if (!doubles.contains(letter)) {
                        doubles.add(letter);
                    }
                }
            }
        }

        return doubles;
    }
    public ArrayList<String> findTriples(String word){
        ArrayList<String> triples = new ArrayList<>();
        for(int i=0;i<word.length()-2;i++){
            for(int a=i+1;a<word.length()-1;a++){
                for(int h=a+1;h<word.length();h++){
                    if(word.charAt(i)==word.charAt(a)& word.charAt(a)==word.charAt(h)){
                        String letter = String.valueOf(word.charAt(i));
                        if(!triples.contains(letter)){
                            triples.add(letter);
                        }
                    }
                }
            }
        }
        return triples;
    }


    public void removeWords(ArrayList<String> words, String wordToRemove) {
        for(int i = 0; i < words.size(); i++) {
            if(words.get(i).equalsIgnoreCase(wordToRemove)) {
                words.remove(i);
                i--; // Decrement index to account for the removed element
            }
        }
    } 
    
    // checks if candidate word matches the answer and returns a list of colors representing the feedback
    public ArrayList<String> wordChecker(String test) {
        ArrayList<String> result = new ArrayList<>();

        if (test == null || ans == null || ans.isEmpty() || test.length() != ans.length()) {
            return result;
        }

        int[] remaining = new int[26];
        for (int i = 0; i < ans.length(); i++) {
            char c = Character.toLowerCase(ans.charAt(i));
            remaining[c - 'a']++;
        }

        for (int i = 0; i < test.length(); i++) {
            char guessed = Character.toLowerCase(test.charAt(i));
            char actual = Character.toLowerCase(ans.charAt(i));

            if (guessed == actual) {
                result.add("green");
                remaining[guessed - 'a']--;
            } else {
                result.add("blank");
            }
        }

        for (int i = 0; i < test.length(); i++) {
            if (result.get(i).equals("green")) {
                continue;
            }

            char guessed = Character.toLowerCase(test.charAt(i));
            if (remaining[guessed - 'a'] > 0 && ans.toLowerCase().indexOf(guessed) != -1) {
                result.set(i, "yellow");
                remaining[guessed - 'a']--;
            } else {
                result.set(i, "grey");
            }
        }

        return result;
    }

    /** Removes candidate answers that do not match the supplied Wordle feedback. */
    public void listShrinker(ArrayList<String> pattern) {
        if (solutions == null || solutions.isEmpty() || guess == null || pattern == null
                || guess.length() != pattern.size()) {
            return;
        }

        ArrayList<String> normalizedPattern = new ArrayList<>();
        for (String color : pattern) {
            normalizedPattern.add(color == null ? null : color.toLowerCase());
        }

        solutions.removeIf(candidate -> candidate == null
                || candidate.length() != guess.length()
                || !new WWord(candidate).wordChecker(guess).equals(normalizedPattern));
    }

// ends the game by finding the best eliminator word from the legal guesses based on the current solutions. It evaluates each legal guess against the remaining solutions and selects the one that maximizes the number of eliminated candidates.
    public String findBestEliminator(ArrayList<String> legalGuesses) {
        if (solutions == null || solutions.isEmpty() || legalGuesses == null || legalGuesses.isEmpty()) {
            return null;

        }

        String bestWord = null;
        int maxWordsRemoved = 0;

        for (String candidateGuess : legalGuesses) {
            if (candidateGuess == null || candidateGuess.length() != 5) {
                continue;
            }

            ArrayList<ArrayList<String>> feedbackPatterns = new ArrayList<>();
            ArrayList<Integer> feedbackCounts = new ArrayList<>();
            int validCandidates = 0;

            for (String candidate : solutions) {
                if (candidate == null || candidate.length() != 5) {
                    continue;
                }

                ArrayList<String> feedback = new WWord(candidate).wordChecker(candidateGuess);
                int patternIndex = feedbackPatterns.indexOf(feedback);
                if (patternIndex == -1) {
                    feedbackPatterns.add(feedback);
                    feedbackCounts.add(1);
                } else {
                    feedbackCounts.set(patternIndex, feedbackCounts.get(patternIndex) + 1);
                }
                validCandidates++;
            }

            int largestBucket = 0;
            for (int count : feedbackCounts) {
                largestBucket = Math.max(largestBucket, count);
            }
            int wordsRemoved = validCandidates - largestBucket;
            if (wordsRemoved > maxWordsRemoved) {
                maxWordsRemoved = wordsRemoved;
                bestWord = candidateGuess;
            }
        }

        return bestWord;
    }

    // Returns total guesses and unsolved answer count for one starting word.

    public int game(String answer, String startingWord, ArrayList<String> solutions,
            ArrayList<String> legalWords) {
        if (answer == null || startingWord == null || answer.length() != 5
                || startingWord.length() != 5) {
            return -1;
        }

        WWord target = new WWord(answer);
        ArrayList<String> remainingSolutions = (solutions == null) ? new ArrayList<>()
                : new ArrayList<>(solutions);
        String guess = startingWord;
        int guesses = 0;

        while (guess != null && guesses < 6) {
            guesses++;
            ArrayList<String> pattern = target.wordChecker(guess);

            if (pattern.stream().allMatch("green"::equals)) {
                return guesses;
            }

            if (guesses >= 6) {
                return -1;
            }

            WWord round = new WWord(remainingSolutions, guess);
            round.listShrinker(pattern);
            remainingSolutions = round.solutions;
            if (remainingSolutions == null || remainingSolutions.isEmpty()) {
                return -1;
            }

            if (remainingSolutions.size() == 1) {
                guess = remainingSolutions.get(0);
            } else {
                guess = round.findBestEliminator(legalWords);
            }
            if (guess == null) {
                return -1;
            }
        }

        return guesses;
    }


    public static void main(String[] args) throws IOException {

        WWord game = new WWord("slate");
        File solutionFile = new File("src/WordleSolutionWords.txt");
        File legalFile = new File("src/WordleLegalWords.txt");

        ArrayList<String> solutions = game.getSolutionWords(solutionFile);
        ArrayList<String> legalWords = game.getLegalWords(legalFile);

        String startingWord = "slate"; // Replace with your desired starting word
        int count = 0;
        int notSolved = 0;
        int wordsdone = 0;
        for(int i = 0; i < solutions.size(); i++) {
            String ans = solutions.get(i);
            wordsdone++;
            int guesses = new WWord("tests").game(ans, startingWord, solutions, legalWords);
            if (guesses == -1) {
                System.out.println(ans + " with " + startingWord + ": not solved");
                notSolved++;
                continue;
            }
            if(wordsdone % 150 == 0){
                System.out.println("Average guesses: " + (double) count / i);
            }
            
            if(guesses!=-1){
                count += guesses;
            }

            //System.out.println(ans + " with " + startingWord + ": " + guesses + " guesses");
        }

        System.out.println("Average guesses: " + (double) count / solutions.size());
        System.out.println("Not solved: " + notSolved);

        
    }
}