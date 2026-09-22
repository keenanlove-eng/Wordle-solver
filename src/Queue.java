public interface Queue<E> {
    boolean isEmpty();
    E dequeue();
    void enqueue( E item );
} 


