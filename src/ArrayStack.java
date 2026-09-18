public class ArrayStack {
    private int[] items;
    private int top;

    public ArrayStack() {
        items = new int[10];
        top = 0;
    }

    public void push(int value) {
        // If the array is full, double its size
        if (top == items.length) {
            int[] newItems = new int[items.length * 2];

            for (int i = 0; i < items.length; i++) {
                newItems[i] = items[i];
            }

            items = newItems;
        }

        items[top] = value;
        top++;
    }

    public int pop() {
        if (top == 0) {
            throw new IllegalStateException("Stack is empty");
        }

        top--;
        return items[top];
    }

    public int peek() {
        if (top == 0) {
            throw new IllegalStateException("Stack is empty");
        }

        return items[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }
}