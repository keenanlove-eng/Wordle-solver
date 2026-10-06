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

        String secret = ans.toLowerCase();
        String guess = test.toLowerCase();
        int[] remaining = new int[26];

        for (int i = 0; i < secret.length(); i++) {
            char actual = secret.charAt(i);
            if (actual >= 'a' && actual <= 'z') {
                remaining[actual - 'a']++;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            result.add("grey");
            if (guess.charAt(i) == secret.charAt(i)) {
                result.set(i, "green");
                remaining[guess.charAt(i) - 'a']--;
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (result.get(i).equals("green")) {
                continue;
            }

            char guessed = guess.charAt(i);
            if (guessed >= 'a' && guessed <= 'z' && remaining[guessed - 'a'] > 0) {
                result.set(i, "yellow");
                remaining[guessed - 'a']--;
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

    /** Filters candidates using the latest feedback, then returns the highest-scoring
     * remaining candidate guess; if the remaining set is empty, it falls back to the legal list. */
    public String getNextBestGuess(ArrayList<String> pattern, ArrayList<String> legalGuesses) {
        if (solutions == null || guess == null || pattern == null || legalGuesses == null
                || guess.length() != pattern.size()) {
            return null;
        }

        listShrinker(pattern);
        if (solutions.isEmpty()) {
            return null;
        }

        if (solutions.size() == 1) {
            return solutions.get(0);
        }

        solutionwords = new ArrayList<>(solutions);
        countLetters();

        String bestGuess = null;
        int bestScore = Integer.MIN_VALUE;
        for (String candidateGuess : solutions) {
            if (candidateGuess == null || candidateGuess.length() != 5) {
                continue;
            }

            int score = ScoreWord(candidateGuess);
            if (score > bestScore) {
                bestScore = score;
                bestGuess = candidateGuess;
            }
        }

        if (bestGuess != null) {
            return bestGuess;
        }

        for (String candidateGuess : legalGuesses) {
            if (candidateGuess == null || candidateGuess.length() != 5) {
                continue;
            }

            int score = ScoreWord(candidateGuess);
            if (score > bestScore) {
                bestScore = score;
                bestGuess = candidateGuess;
            }
        }
        return bestGuess;
    }

    


    public static void main(String[] args) throws IOException {

       /*  WWord wordLists = new WWord("drone");
        File solutionFile = new File("src/WordleSolutionWords.txt");
        ArrayList<String> solutions = wordLists.getSolutionWords(solutionFile);
        File legalFile = new File("src/WordleLegalWords.txt");
        ArrayList<String> legalWords = wordLists.getLegalWords(legalFile);
        System.out.println("Loaded legal guesses: " + legalWords.size());
        String guess = "ariot";
        for (int turn = 1; turn <= 5 && guess != null; turn++) {
            ArrayList<String> pattern = wordLists.wordChecker(guess);
            System.out.println("Guess " + turn + ": " + guess + ", result: " + pattern);

            if (pattern.stream().allMatch("green"::equals)) {
                System.out.println("Solved in " + turn + " guesses.");
                System.out.println("Matching solution: " + guess);
                break;
            }

            WWord round = new WWord(solutions, guess);
            guess = round.getNextBestGuess(pattern, legalWords);
            System.out.println("Matching solutions: " + solutions.size());

            if (turn < 5 && guess != null) {
                System.out.println("Next best guess: " + guess);
            } else if (turn == 5) {
                System.out.println("Matching solutions after five guesses: " + solutions);
            }
        } 
        */


        WWord wordLists = new WWord("drone");
        File solutionFile = new File("src/WordleSolutionWords.txt");
        ArrayList<String> solutions = wordLists.getSolutionWords(solutionFile);
        File legalFile = new File("src/WordleLegalWords.txt");
        ArrayList<String> legalWords = wordLists.getLegalWords(legalFile);
        System.out.println("Loaded legal guesses: " + legalWords.size());

        int totalGuesses = 0;
        int solvedWords = 0;
        int unsolvedWords = 0;

        for (String solution : solutions) {
            WWord answer = new WWord(solution);
            ArrayList<String> remainingSolutions = new ArrayList<>(solutions);
            String guess = "salet";
            int guessesForWord = 0;
            boolean solved = false;

            while (guess != null && guessesForWord < 6) {
                guessesForWord++;
                ArrayList<String> pattern = answer.wordChecker(guess);
                if (pattern.stream().allMatch("green"::equals)) {
                    totalGuesses += guessesForWord;
                    solvedWords++;
                    solved = true;
                    break;
                }

                if (guessesForWord < 6) {
                    WWord round = new WWord(remainingSolutions, guess);
                    guess = round.getNextBestGuess(pattern, legalWords);
                }
            }

            if (!solved) {
                unsolvedWords++;
                System.out.println("Unsolved word: " + solution);
            }
        }

        System.out.println("Solved words: " + solvedWords);
        System.out.println("Unsolved words: " + unsolvedWords);
        if (solvedWords > 0) {
            System.out.printf("Average guesses per solved word: %.2f%n",
                    (double) totalGuesses / solvedWords);
        }


    }
}