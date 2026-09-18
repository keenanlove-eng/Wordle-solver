public class LinkedStack {

    private Node top;

    private class Node {
        int data;
        Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public LinkedStack() {
        top = null;
    }

    public void push(int value) {
        top = new Node(value, top);
    }

    public int pop() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty");
        }

        int value = top.data;
        top = top.next;
        return value;
    }

    public int peek() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty");
        }

        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

public static void main(String[] args) {
    LinkedStack stack = new LinkedStack();
    stack.push(10);
    stack.push(20);
    System.out.println(stack.peek()); //  20
    System.out.println(stack.pop());  //  20
    System.out.println(stack.isEmpty()); //  false
    System.out.println(stack.pop());  //  10
    System.out.println(stack.isEmpty()); //  true
   //System.out.println(stack.peek()); //  Exception: Stack is empty


    stack.push(10);
    stack.push(20);
    stack.push(30);
    stack.push(40);

    
    while(!stack.isEmpty()) {
        System.out.println(stack.pop()); 
    }

}

}