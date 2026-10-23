public class Binary {
    public int getVal(int ind, int arr) {
        return (arr >> (ind * 4)) & 15;
    }

    public int setVal(int ind, int arr, int num) {
        return (arr & ~(15 << (ind * 4))) | (num << (ind * 4));
    }
    
    public static void main(String[] args) {
        Binary bt = new Binary();
        int arr = bt.setVal(0, 2677, 9);
        System.out.println(Integer.toBinaryString(arr));
        System.out.println(arr);
    }
}
