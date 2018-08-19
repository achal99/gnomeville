
public class LystIterator<Type> {
	
	private Node<Type> current;
	private int indexofpointer;
	
	public LystIterator(Type frontq) {
		current = (Node<Type>) frontq;
		indexofpointer = -1;
	}
	
	public int getIndex() {
		return indexofpointer; 
	}
	
	public boolean pastEnd() {
		return current == null;
	}
	
	public Node<Type> getCurrent() {
		return current;
	}
	
	public void increment() {
		if (pastEnd()) {
			current = null;
		} else {
			current = ((Node) current).getNext();
			indexofpointer++;
		}
	} 
	
	public boolean atEnd() throws AlreadyEndException  {
		if (pastEnd()) {
			throw new AlreadyEndException("The list is already past the end");
		}
	
		return current.getNext()==null;
}
	
	public String toString() {
		return "Index equals =" + indexofpointer;
		
	}

}
