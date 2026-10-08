
public class Sets<E> {
    private ArrayListAP<E> elements;

    public Sets() {
        elements = new ArrayListAP<>();
    }

    public boolean add(E item) {
        if (contains(item)) {
            return false;
        }

        elements.add(item);
        return true;
    }

    public boolean remove(E item) {
        return elements.remove(item);
    }

    public boolean contains(Object item) {
        return elements.contains(item);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void clear() {
        elements.clear();
    }

    public Sets<E> union(Sets<? extends E> other) {
        Sets<E> result = new Sets<>();

        for (int i = 0; i < this.size(); i++) {
            result.add(this.elements.get(i));
        }

        if (other != null) {
            for (int i = 0; i < other.size(); i++) {
                result.add(other.elements.get(i));
            }
        }

        return result;
    }

    public Sets<E> intersection(Sets<? extends E> other) {
        Sets<E> result = new Sets<>();

        if (other == null) {
            return result;
        }

        for (int i = 0; i < this.size(); i++) {
            E item = this.elements.get(i);
            if (other.contains(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public Sets<E> difference(Sets<? extends E> other) {
        Sets<E> result = new Sets<>();

        if (other == null) {
            result = this.copy();
            return result;
        }

        for (int i = 0; i < this.size(); i++) {
            E item = this.elements.get(i);
            if (!other.contains(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public Sets<E> copy() {
        Sets<E> copy = new Sets<>();

        for (int i = 0; i < this.size(); i++) {
            copy.add(this.elements.get(i));
        }

        return copy;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder("{");

        for (int i = 0; i < size(); i++) {
            sb.append(elements.get(i));
            if (i < size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Sets)) {
            return false;
        }

        Sets<?> other = (Sets<?>) obj;

        if (this.size() != other.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!other.contains(this.elements.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;

        for (int i = 0; i < size(); i++) {
            hash = 31 * hash + (elements.get(i) == null ? 0 : elements.get(i).hashCode());
        }

        return hash;
    }

    public static void main(String[] args) {
        Sets<String> fruits = new Sets<>();
        System.out.println("Add strawberry: " + fruits.add("apple"));
        System.out.println("Add banana: " + fruits.add("banana"));
        System.out.println("Add cherry: " + fruits.add("cherry"));
        System.out.println("Add duplicate apple: " + fruits.add("apple"));
        System.out.println("Fruits: " + fruits);
        System.out.println("Contains banana: " + fruits.contains("banana"));
        System.out.println("Contains date: " + fruits.contains("date"));
        System.out.println("Size: " + fruits.size());
        System.out.println("Remove cherry: " + fruits.remove("cherry"));
        System.out.println("After removal: " + fruits);

        Sets<String> otherFruits = new Sets<>();
        otherFruits.add("banana");
        otherFruits.add("date");
        System.out.println("Union: " + fruits.union(otherFruits));
        System.out.println("Intersection: " + fruits.intersection(otherFruits));
        System.out.println("Difference: " + fruits.difference(otherFruits));

        for (String fruit : new String[] {"apple", "banana"}) {
            fruits.remove(fruit);
        }
        System.out.println("Size after emptying: " + fruits.size());
    }
}
