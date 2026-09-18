public interface APList<E> {
	E get(int index);
	E set(int index, E obj);
	boolean add(E obj);
	void add(int index, E obj);
	E remove(int index);
	int size();
}
