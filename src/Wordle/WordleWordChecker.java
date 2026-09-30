package Wordle;
import java.io.File;
import java.util.ArrayList;

public class WordleWordChecker extends WWord {

    public WordleWordChecker(String word) {
        super(word);
    }

    public WordleWordChecker(ArrayList<String> solutions, String guess) {
        super(solutions, guess);
    }
    
    /*public String checkWords() {
        String ans = "";
        WWord wordLists = new WWord("cigar");
        File solutionFile = new File("src/Wordle/WordleSolutionWords.txt");
        ArrayList<String> solutions = wordLists.getSolutionWords(solutionFile);
        String guess = "queue";
        while(solutions.size()>0) {
            WWord shrinker = new WWord(solutions, guess);
            shrinker.listShrinker(pattern);

        }

        return ans;

    }*/
}
