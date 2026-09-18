public class LinkedQueue<E> implements Queue<E>{
    private Node<E> front;
    private Node<E> back;

    public LinkedQueue() {
        front = null;
        back = null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    
    public E dequeue() { 
        if (isEmpty()) { return null; } 
        E item = front.getItem(); 
        front = front.getNext(); 
        
        if (front == null) { 
            back = null; 
        } 
            return item; 
    }
    
    
   public void enqueue(E item) { 
        Node<E> newNode = new Node<>(item); 
    
    if (isEmpty()) { 
        front = newNode; back = newNode; 
    } 
    else { 
        back.setNext(newNode); 
        back = newNode; 
    } 

    }

public static void main(String[] args) {
    LinkedQueue<Integer> queue = new LinkedQueue<>();
    queue.enqueue(1);
    queue.dequeue();
    System.out.println(queue.isEmpty()); // Output: true
    queue.enqueue(2);
    queue.enqueue(3);
    queue.enqueue(4);
    queue.enqueue(5);
    queue.enqueue(6);
    queue.dequeue();
    queue.dequeue();
    System.out.println(queue.dequeue()); // Output: 4
    System.out.println(queue.dequeue()); // Output: 5

    queue.enqueue(7);
    queue.enqueue(8);

    while(!queue.isEmpty()) { // 6 7 8
        System.out.println(queue.dequeue());
    }
}


} 



    
