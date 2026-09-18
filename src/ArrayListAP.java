

public class ArrayListAP<E> implements APList<E> {
    private Object [] objs;
    private int index;
    private static final int DEFAULT_CAPACITY = 5;
    
    public ArrayListAP() {
        index = 0;
        objs = new Object[DEFAULT_CAPACITY];
    }
    
    public int size() { return index; }
    
    public boolean add( E item ) {
        if( index == objs.length )
           doubleCapacity();
        objs[index] = item;
        index++;
        return true;
    }
    
    @SuppressWarnings("unchecked")
    public E get( int i ) {
        checkElementIndex(i);
        return (E) objs[i];
    }
    
    @SuppressWarnings("unchecked")
    public E set( int i, E item ) {
        checkElementIndex(i);
        E returnedItem = (E) objs[i];
        objs[i] = item;
        return returnedItem;
    }
    
    public String toString() {
        String str = "[";
        for( int i = 0; i < index; i++ ) {
            str += objs[i];
            if( i < index - 1 ) {
                str += ", ";
            }
        }
        return str + "]";
    }
    
    @SuppressWarnings("unchecked")
    public E remove( int i ) {
        checkElementIndex(i);
        E removedObj = (E) objs[i];
        for( int j = i; j < index - 1; j++ ) {
           objs[j] = objs[j+1];       
        }
        index--;
        objs[index] = null;
        return removedObj;
    }

    public boolean remove( Object item ) {
        for( int i = 0; i < index; i++ ) {
            if( item == null ? objs[i] == null : item.equals(objs[i]) ) {
                remove(i);
                return true;
            }
        }
        return false;
    }
    
    public void add( int i, E item ) {
        checkPositionIndex(i);
        if( index == objs.length )
           doubleCapacity();
        // shifting over elements
        for( int j = index; j > i; j-- ) {
            objs[j] = objs[j-1];
        }
        
        objs[i] = item;
        index++;
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public void clear() {
        for( int i = 0; i < index; i++ ) {
            objs[i] = null;
        }
        index = 0;
    }

    public boolean contains( Object item ) {
        for( int i = 0; i < index; i++ ) {
            if( item == null ? objs[i] == null : item.equals(objs[i]) ) {
                return true;
            }
        }
        return false;
    }

    private void checkElementIndex( int i ) {
        if( i < 0 || i >= index ) {
            throw new IndexOutOfBoundsException( "Index: " + i + ", Size: " + index );
        }
    }

    private void checkPositionIndex( int i ) {
        if( i < 0 || i > index ) {
            throw new IndexOutOfBoundsException( "Index: " + i + ", Size: " + index );
        }
    }
    
    private void doubleCapacity() {
        Object [] temp = new Object[ objs.length * 2 ];
        for( int j = 0; j < index; j++ ) {
            temp[j] = objs[j];
        }            
        objs = temp;
    }
    
    public static void main( String [] args ) {
        ArrayListAP<String> sal = new ArrayListAP<>();
        System.out.println( sal.size() );
        sal.add( "hog" );
        sal.add( "pig" );
        sal.add( "sow" );
        System.out.println( sal.size() );
        System.out.println( sal );
        sal.add( "cow" );
        sal.add( "ewe" );
        System.out.println( sal.size() + " " + sal );
        System.out.println( sal.remove(1) + " = pig" );
        System.out.println( sal.size() + " " + sal );
        sal.add( 0, "bat" );
        System.out.println( sal.size() + " " + sal );
        sal.add( sal.size(), "rat" );  // same as add without index
        System.out.println( sal.size() + " " + sal );
        sal.add( 2, "cat" );
        System.out.println( sal.size() + " " + sal );
    }
}