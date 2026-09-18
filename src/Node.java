
public class Node<E> {
    private E item;
    private Node<E> next;

    public Node( E item, Node<E> next ) {
        this.item = item;
        this.next = next;
    }

    public Node( E item ) {
        this(item, null);
    }

    public E getItem() { 
        return item;
    }
    public Node<E> getNext() { 
        
        return next; 
    
    }
    public void setNext( Node<E> node ) { 
        next = node;
    }
    public void setNext() {
        next = null;
    }
    public E setItem( E item ) {
        E prevItem = this.item;
        this.item = item;
        return prevItem;
    }
}