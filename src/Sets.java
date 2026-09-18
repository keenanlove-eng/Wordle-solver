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
        Sets<Integer> first = new Sets<>();
        first.add(1);
        first.add(2);
        first.add(3);

        Sets<Integer> second = new Sets<>();
        second.add(2);
        second.add(3);
        second.add(4);

        System.out.println("First: " + first);
        System.out.println("Second: " + second);
        System.out.println("Union: " + first.union(second));
        System.out.println("Intersection: " + first.intersection(second));
        System.out.println("Difference: " + first.difference(second));
        System.out.println("Contains 2? " + first.contains(2));
        System.out.println("Removed 3? " + first.remove(3));
        System.out.println("Updated First: " + first);
    }
}
