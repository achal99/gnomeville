public class Node<Type> {
	public Type getObj() {
		return obj;
	}
	private Type obj;
	private Node<Type> next;
	private Node<Type> previous;

	//Constructors for the Nodes that hold Objects
	public Node(Type p, Node<Type> s, Node<Type> n) {
		obj = p;
		previous = s;
		next = n;
	}
	
	
	public Node(Type p, Node<Type> n){
		obj = p;
	}
	
	public Node(Type p) {
		obj = p;
		next = null;
		previous = null;
	}
	
	public Type getData() {
		return obj;
	}
	
	public void setPrevious(Node<Type> prv) {
		previous = prv;
	}
	
	public Node<Type> getNext() {
		return next;
	}
	
	public Node<Type> getPrevious() {
		return previous;
	}
	
	
	public void setNext(Node<Type> nxt) {
		next = nxt;
	}
	public String toString(){
		return obj.toString();
	}
	public boolean equals(Node<Type> n){
		return obj.equals(n.getData());
	}
	

}
