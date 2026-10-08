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

    /** Filters candidates using the latest feedback, then returns the highest-scoring legal guess. */
    public String getNextBestGuess(ArrayList<String> pattern, ArrayList<String> legalGuesses) {
        return getNextBestGuess(pattern, legalGuesses, null);
    }

    private String getNextBestGuess(ArrayList<String> pattern, ArrayList<String> legalGuesses,
            Map<String, String> guessCache) {
        if (solutions == null || guess == null || pattern == null || legalGuesses == null
                || guess.length() != pattern.size()) {
            return null;
        }

        listShrinker(pattern);
        if (solutions.isEmpty()) {
            return null;
        }

        // The remaining answers determine the next best guess, so reuse it when possible.
        String cacheKey = null;
        if (guessCache != null) {
            cacheKey = String.join(",", solutions);
            String cachedGuess = guessCache.get(cacheKey);
            if (cachedGuess != null) {
                return cachedGuess;
            }
        }

        solutionwords = new ArrayList<>(solutions);
        countLetters();

        ArrayList<String> possibleGuesses = new ArrayList<>(legalGuesses);
        for (String solution : solutions) {
            if (!possibleGuesses.contains(solution)) {
                possibleGuesses.add(solution);
            }
        }

        String bestGuess = null;
        int bestScore = Integer.MIN_VALUE;
        for (String candidateGuess : possibleGuesses) {
            if (candidateGuess == null || candidateGuess.length() != 5) {
                continue;
            }

            int score = ScoreWord(candidateGuess);
            if (score > bestScore) {
                bestScore = score;
                bestGuess = candidateGuess;
            }
        }
        if (guessCache != null && bestGuess != null) {
            guessCache.put(cacheKey, bestGuess);
        }
        return bestGuess;
    }

    // Returns total guesses and unsolved answer count for one starting word.
    private static int[] simulateStartingWord(String startingWord,
            ArrayList<String> solutions, ArrayList<String> legalWords) {
        int totalGuesses = 0;
        int unsolvedWords = 0;
        Map<String, String> guessCache = new HashMap<>();

        for (String solution : solutions) {
            WWord answer = new WWord(solution);
            // Start each answer with the complete q-only answer list.
            ArrayList<String> remainingSolutions = new ArrayList<>(solutions);
            String guess = startingWord;
            int guessesForWord = 0;
            boolean solved = false;

            while (guess != null && guessesForWord < 6) {
                guessesForWord++;
                ArrayList<String> pattern = answer.wordChecker(guess);
                if (pattern.stream().allMatch("green"::equals)) {
                    solved = true;
                    break;
                }

                if (guessesForWord < 6) {
                    WWord round = new WWord(remainingSolutions, guess);
                    guess = round.getNextBestGuess(pattern, legalWords, guessCache);
                }
            }

            if (solved) {
                totalGuesses += guessesForWord;
            } else {
                totalGuesses += 6;
                unsolvedWords++;
            }
        }

        return new int[] { totalGuesses, unsolvedWords };
    }


    public ArrayList<String> top1hundo()  throws IOException {
        WWord wordLists = new WWord("salet");
        File solutionFile = new File("src/WordleSolutionWords.txt");
        ArrayList<String> solutions = wordLists.getSolutionWords(solutionFile);
        File legalFile = new File("src/WordleLegalWords.txt");
        ArrayList<String> legalWords = wordLists.getLegalWords(legalFile);
        int bestScore = Integer.MIN_VALUE;
    

        ArrayList<String> top1hundo = new ArrayList<>(100);
        int place = 0;
        for (int i = 0; i < 100; i++){
            int highestScore = ScoreWord(legalWords.get(0));
            for(int j=0;j<legalWords.size();j++){
                int currentScore = ScoreWord(legalWords.get(j));
                if(currentScore>highestScore){
                    highestScore=currentScore;
                    place = j;
                }
            }
            top1hundo.add(legalWords.get(place));
            legalWords.remove(place);
                
        }
        
        
        return top1hundo;
    }

    public static int play(String answer, String startingGuess) throws IOException {
        WWord wordLists = new WWord(answer);
        ArrayList<String> solutions = wordLists.getSolutionWords(
                new File("src/WordleSolutionWords.txt"));
        ArrayList<String> legalWords = wordLists.getLegalWords(
                new File("src/WordleLegalWords.txt"));
        ArrayList<String> remainingSolutions = new ArrayList<>(solutions);
        ArrayList<String> remainingLegalWords = new ArrayList<>(legalWords);
        String guess = startingGuess;
        WWord answerWord = new WWord(answer);

        for (int turn = 1; turn <= 6; turn++) {
            ArrayList<String> pattern = answerWord.wordChecker(guess);
            System.out.println("Guess " + turn + ": " + guess + " -> " + pattern);
            if (pattern.stream().allMatch("green"::equals)) {
                System.out.println("Solved " + answer + " in " + turn + " guesses.");
                return turn;
            }

            WWord round = new WWord(remainingSolutions, guess);
            String nextGuess = null;
            if (turn < 6) {
                remainingLegalWords.remove(guess);
                nextGuess = round.getNextBestGuess(pattern, remainingLegalWords);
            } else {
                round.listShrinker(pattern);
            }
            System.out.println("Remaining solutions (" + remainingSolutions.size() + "): "
                    + remainingSolutions);

            if (remainingSolutions.isEmpty() || (turn < 6 && nextGuess == null)) {
                System.out.println("No matching solution remains.");
                return turn;
            }
            if (turn == 6) {
                return turn;
            }
            guess = nextGuess;
        }

        System.out.println("Could not solve " + answer + " in six guesses.");
        return 6;
    }

    public static void main(String[] args) throws IOException {
       WWord wordLists = new WWord("salet");
        ArrayList<String> solutions = wordLists.getSolutionWords(
                new File("src/WordleSolutionWords.txt"));
        ArrayList<String> legalWords = wordLists.getLegalWords(
                new File("src/WordleLegalWords.txt"));
        int totalGuesses = 0;

        for (int i = 0; i < 100; i++) {
            for(int j = 0; j < solutions.size(); j++) {
               if(play(wordLists.top1hundo().get(i), solutions.get(j)) == 6) {
                    System.out.println("Failed to solve " + solutions.get(j) + " with starting guess " + wordLists.top1hundo().get(i));
                }
                else {
                    totalGuesses += play(wordLists.top1hundo().get(i), solutions.get(j));
                }

            }
        }
        System.out.println("Average guesses: " + totalGuesses/(solutions.size()*100));
    }

}