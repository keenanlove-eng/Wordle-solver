import org.atpfivt.ljv.LJV;
//import org.atpfivt.ljv.nodes.Node;

public class LinkedListAP<E> implements APList<E> {
    private Node<E> begin;
    private Node<E> end;
    private int length;

    public LinkedListAP() {
        begin = null;
        end = null;
        length = 0;
    }

    public boolean add(E item) {
        Node<E> newNode = new Node<E>(item);

        if (begin == null) {
            begin = newNode;
            end = newNode;
        } else {
            end.setNext(newNode);
            end = newNode;
        }

        length++;
        return true;
    }

    public E get(int index) {
        checkIndex(index);

        Node<E> temp = begin;

        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        return temp.getItem();
    }

    public E set(int index, E obj) {
        checkIndex(index);

        Node<E> temp = begin;

        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        return temp.setItem(obj);
    }

    public void add(int index, E obj) {
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }

        if (index == length) {
            add(obj);
            return;
        }

        Node<E> newNode = new Node<E>(obj);

        if (index == 0) {
            newNode.setNext(begin);
            begin = newNode;
        } else {
            Node<E> temp = begin;

            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }

            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }

        length++;
    }

    public E remove(int index) {
        checkIndex(index);

        E removedItem;

        if (index == 0) {
            removedItem = begin.getItem();
            begin = begin.getNext();
            length--;

            if (length == 0) {
                end = null;
            }

            return removedItem;
        }

        Node<E> temp = begin;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.getNext();
        }

        Node<E> removedNode = temp.getNext();
        removedItem = removedNode.getItem();

        temp.setNext(removedNode.getNext());

        if (index == length - 1) {
            end = temp;
        }

        length--;

        return removedItem;
    }

    public int size() {
        return length;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
    }

    public String toString() {
        String str = "[";
    
        for(int i = 0; i < size(); i++) {
            str +=  get(i);
            if(i < size() -1) {
                str += ", ";  
            } 
        }
        return str + "]";
        
    }

    public static void main(String[] args) {
        LinkedListAP<String> animals = new LinkedListAP<>();

        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.add("dog");

        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.add("cat");

        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.add("pig");

        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        
        animals.add("horse");

        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        System.out.println(animals);

        animals.remove(0);
        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.remove(animals.size() - 1);
        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.add(2, "sheep");
        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

        animals.add(0, "cow");
        DisplayDS.browse(
            new LJV().setTreatAsPrimitive(String.class),
            animals
        );

    }
}