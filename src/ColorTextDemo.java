public class ColorTextDemo {
    private static final String GREEN_BACK = "\u001b[42;1m";
    private static final String YELLOW_BACK = "\u001b[43;1m";
    private static final String RESET = "\u001b[0m";
    private static final String WHITE_TEXT = "\u001B[37m";
    private static final String GRAY_BACK = "\u001B[100m";

    public static void main( String [] args ) {
        String result = WHITE_TEXT + GRAY_BACK + "C" +  GREEN_BACK + "A" + GRAY_BACK + "T" + YELLOW_BACK+  "C" + GRAY_BACK + "H";
        System.out.println( result );
    }
}