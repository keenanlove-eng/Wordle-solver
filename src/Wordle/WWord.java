package Wordle;
import java.util.ArrayList;

public class WWord {
    private String word;
    private String ans;
    private ArrayList<String> solutions;
    private String guess;
    public WWord(String word) {
        this.word = word;
        this.ans = word;
    }
    public WWord(ArrayList<String> solutions, String guess) {
        this.solutions = solutions;
        this.guess = guess;
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

    /** Returns the candidate answers that match the supplied Wordle feedback. */
    public ArrayList<String> listShrinker(ArrayList<String> pattern) {
        ArrayList<String> result = new ArrayList<>();

        if (solutions == null || solutions.isEmpty() || guess == null || pattern == null
                || guess.length() != pattern.size()) {
            return result;
        }

        ArrayList<String> normalizedPattern = new ArrayList<>();
        for (String color : pattern) {
            normalizedPattern.add(color == null ? null : color.toLowerCase());
        }

        for (String candidate : solutions) {
            if (candidate == null || candidate.length() != guess.length()) {
                continue;
            }

            ArrayList<String> candidatePattern = new WWord(candidate).wordChecker(guess);
            if (candidatePattern.equals(normalizedPattern)) {
                result.add(candidate);
            }
        }

        return result;
    }


    public static void main(String[] args) {
        WWord wWord = new WWord("hello");
        ArrayList<String> charList = wWord.splitW("HELLO");
        System.out.println(charList);
        System.out.println("Exact match: " + wWord.wordChecker("lllaa"));
        System.out.println("Different guess: " + wWord.wordChecker("apple"));
        System.out.println("Current answer: " + wWord.ans);

        ArrayList<String> solutions = new ArrayList<>();
        solutions.add("cigar");
        solutions.add("cider");
        solutions.add("rebut");
        String guess = "crane";
        ArrayList<String> pattern = new WWord("cigar").wordChecker(guess);
        System.out.println("Guess: " + guess + ", pattern: " + pattern);
        WWord shrinker = new WWord(solutions, guess);
        System.out.println("Matching solutions: "
            + shrinker.listShrinker(pattern));
    }
}