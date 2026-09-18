import java.util.ArrayList;

public class ArrayListStack {
    private ArrayList<Integer> items;

    public ArrayListStack() {
        items = new ArrayList<>();
    }

    public void push(int x) {
        items.add(x);
    }

    public int pop() {
        return items.remove(items.size() - 1);
    }

    public int peek() {
        return items.get(items.size() - 1);
    }
} 

