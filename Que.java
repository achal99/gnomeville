
public class Que<Type>{
	private Node<Type> front;
	private Node<Type> back;
	private int length;
	public Que(){
		front = null;
		back = null;
		length = 0;
	}
	
	public void  displayQue(Que<Type> q) {
		Type frontq = (Type) front;
			for(LystIterator i = new LystIterator(frontq);!i.pastEnd(); i.increment()) {
				System.out.println(i.getCurrent().getData());		
		}
		
		} //end of for loop
		
	

	public Node<Type> push(Node<Type> p) {
		if (isEmpty()) {
			front = (Node<Type>) p;
			back = (Node<Type>) p;
			length++;
		} else {
			p.setPrevious(back);
			back.setNext(p);
			back = p;
			length++;
		}	
		return p;
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public Node<Type> leave(Node<Type> p) throws NotFoundException {
		if (p == null) { //if p isnt even in the queue
			throw new NotFoundException("This Node<Type> does not exist in the Que.");
		}
		if(isEmpty()) {
			throw new NotFoundException("This Que<Type>is empty.");
		}
		if (front.equals(p)) {
			pop();
		} else if(back.equals(p)) {
			p.getPrevious().setNext(null);
		} else {
				p.getPrevious().setNext(p.getNext()); // changes the pointers so that p's previous is pointing at p's next
				p.getNext().setPrevious(p.getPrevious());
				length--;	
			}
		
		return p;
		
	}
	
	public Node<Type> pop() throws NotFoundException{
		if(isEmpty()) {
			throw new NotFoundException("This Que<Type>is empty.");
		}
		Node<Type> temp = front;
		front = front.getNext();
		front.setPrevious(null);
		length--;
		return temp;
	}

	public int getLength() {
		return length;
	}
	
	public Node<Type> getFront() {
		return front;
	}

	public void setFront(Node<Type> front) {
		this.front = front;
	}

	public Node<Type> getBack() {
		return back;
	}

	public void setBack(Node<Type> back) {
		this.back = back;
	}
	
	public boolean existsinQue(Node<Type> p) throws NotFoundException { //returns if Node<Type> is in the Que<Type>or not
		boolean found = false;
		if(isEmpty()) {
			throw new NotFoundException("Que<Type>is empty");
		} 
		
		if (find(p) == null) {
			return false;
		} else {
			return true;
		}
	}

	public LystIterator find(Node<Type> p) throws NotFoundException { // returns the LystIterator object pointing at Node
		if(isEmpty()) {
			throw new NotFoundException("Que<Type>is empty.");
		}
		boolean found = false;
		LystIterator where = null;
		for(LystIterator i = new LystIterator(front);!i.pastEnd() && !found; i.increment()) {
			if(i.getCurrent().equals(p)) {
				found = true;
				where  = i;
			} //end of if statement
			
		} //end of for loop
		if (found) {
			return where;
		} else {
			return null;
		}
	}
	
	
	
	
	

}

