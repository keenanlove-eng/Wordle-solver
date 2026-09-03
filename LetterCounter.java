public class LetterCounter {
    private String text;
    
    public LetterCounter(String text) {
        this.text=text;
    }

    public static int[] count(String text) {
        int[] count = new int[26];
        for(int i=0;i<= text.length() - 1;i++){
           if(text.charAt(i) == 'a'){
                count[0]++;} 
           else if(text.charAt(i) == 'b') {
                count[1]++; }
           else if(text.charAt(i) == 'c') {
                count[2]++; }
        else if(text.charAt(i) == 'd') {
                count[3]++;}
            else if(text.charAt(i) == 'e') {
                count[4]++;}
            else if(text.charAt(i) == 'f') {
                count[5]++;}
            else if(text.charAt(i) == 'g') {
            count[6]++;}
            else if(text.charAt(i) == 'h') {
            count[7]++;}
            else if(text.charAt(i) == 'i') {
                count[8]++;}
            else if(text.charAt(i) == 'j') {
                count[9]++;}
            else if(text.charAt(i) == 'k') {
                count[10]++;}
            else if(text.charAt(i) == 'l') {
                count[11]++;}
            else if(text.charAt(i) == 'm') {
                count[12]++;}
            else if(text.charAt(i) == 'n') {
                count[13]++;}
            else if(text.charAt(i) == 'o') {
                count[14]++;}
            else if(text.charAt(i) == 'p') {
                count[15]++;}
            else if(text.charAt(i) == 'q') {
                count[16]++;}
            else if(text.charAt(i) == 'r') {
                count[17]++;}
            else if(text.charAt(i) == 's') {
                count[18]++;}
            else if(text.charAt(i) == 't') {
                count[19]++;}
            else if(text.charAt(i) == 'u') {
                count[20]++;}
            else if(text.charAt(i) == 'v') {
                count[21]++;}
            else if(text.charAt(i) == 'w') {
                count[22]++;}
            else if(text.charAt(i) == 'x') {
                count[23]++;}
            else if(text.charAt(i) == 'y') {
                count[24]++;}
            else if(text.charAt(i) == 'z') {
                count[25]++;}
                 }
         
        return count;
    }
    
    public static void main(String[] args) {
        String test = "cigarrebutsissyaaaaaabbbb";
        System.out.println(java.util.Arrays.toString(count(test)));

        
    }

}