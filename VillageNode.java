import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

public class VillageNode { //node class that holds villages only
	private Village village;
	private boolean isVisited;
	private int outdegree;
	private int indegree;
	private Que<Edge> edgelist = new Que();
	private Que<VillageNode> neighbours = new Que();
	private Que<VillageNode> pointingat = new Que();
	private static BufferedImage pic;
	static{
        try{
            pic = ImageIO.read(new File("res/" + "village.png"));
        }catch(IOException e){
            System.out.println("Didn't find the file.");
        }
    }
	
	public VillageNode(int x, int y) {
		Village vlg = new Village(x,y);
		vlg.setVillageNode(this);
		village = vlg;
		outdegree = 0;
		indegree = 0;
		isVisited = false;
		DirectedGraph.addNode(this);
		System.out.println("NEW VILLAGE CREATED :" + village.getName());
	}
	public Que<VillageNode> getNbs() {
		return neighbours;
	}
	
	public Que<VillageNode> getpointing() {
		return pointingat;
	}
	
	public void addPointing(Node<VillageNode> e) {
		pointingat.push(e);
	}
	
	
	public String getName() {
		return village.getName();
	}
	
	public int getOutdegree()
	{
		return outdegree;
	}
	
	public int getIndegree() {
		return indegree;
	}
	
	public Village getVillage() {
		return village;
	}
	
	public void incrementOut() {
		outdegree++;
	}
	
	public void incrementIn() {
		indegree++;
	}
	
	public void displayEdges() {
			Node<Edge>frontq = edgelist.getFront();
				for(LystIterator i = new LystIterator(frontq);!i.pastEnd(); i.increment()) {
					System.out.println("Edge from "+ this.getName() + " " + ((Edge) i.getCurrent().getData()).getEnd().getName());		
			}	
	}
			 //end of for loop
	public boolean visited() {
		return isVisited;
	}
	
	public void setVisited(boolean setter) {
		isVisited = setter;
	}

	
	public void addEdge(int price, int distance, VillageNode end) throws AlreadyExistsException {
		if(edgeExists(end)) {
			throw new AlreadyExistsException();
		} else {
			Edge e = new Edge(price,distance,this,end);
			edgelist.push(new Node<Edge>(e));
			end.addPointing(new Node<VillageNode>(this));
			neighbours.push(new Node<VillageNode>(end)); //adds the villagenode to the list of neighbours
			DirectedGraph.addEdge(e); //adds this edge to the directed graph class
			this.incrementOut(); //increments outdegree of current
			end.incrementIn(); //increments indegree of end edge
		}
	}
	
	public boolean edgeExists(VillageNode end) { //iterates through edgeList to see if there is an existing edge to a certain node
		boolean exists = false;
		if (edgelist.isEmpty()) {
			exists = false;
		} else {
			for (LystIterator i = new LystIterator(edgelist.getFront());!i.pastEnd(); i.increment()) {
				if(((Edge) i.getCurrent().getData()).getEnd().equals(end)) {
					exists = true;
					break;
				}
			} //end of for loop
		}
		return exists;
	}
	
	public void removeEdge(VillageNode end) throws NotFoundException { //find the edge ending at end, and removes it	
		try {
			if(findEdge(end) == null) {
				System.out.println("This edge is not existing");
			} else {
				edgelist.leave(findEdge(end).getCurrent());
				//Changed
				DirectedGraph.removeEdge(findEdge(end).getCurrent().getData());
				
			}
		} catch(NotFoundException e) {
			System.out.println("Empty queu");
		}	
	}
	
	public int getDistance(VillageNode end) { //returns the distance with another edge
		int distance = 0;
		try {
			if(findEdge(end) == null) {
				System.out.println("This edge does not exist ");
			} else {
					distance = ((Edge) findEdge(end).getCurrent().getData()).getDistance();
			}
		} catch (NotFoundException e) {
			System.out.println("This node has no edges");
		}
		return distance;
	}
	
	public int getPrice(VillageNode end) { //returns the distance with another edge
		int price = 0;
		try {
			if(findEdge(end) == null) {
				System.out.println("Edge from " + village + " to " 
						+ end.getVillage() + " does not exist.");
			} else {
				price = ((Edge) findEdge(end).getCurrent().getData()).getDistance();
			}
		} catch (NotFoundException e) {
			System.out.println("This node has no edges");
		}
		return price;
	}
	
	
	public LystIterator<Edge> findEdge(VillageNode end) throws NotFoundException {
		if(edgelist.isEmpty()) { //checks if the edgelist is empty or its a real
			throw new NotFoundException();
		}
		boolean found = false;
		LystIterator<Edge> where = null;
		for (LystIterator<Edge> i = new LystIterator(edgelist.getFront());!i.pastEnd() && !found; i.increment()) {
			if(((Edge) i.getCurrent().getData()).getEnd().equals(end)) {
				where = i;
				found = true;
				break;
			}
			} //end of for loop
		if(found) {
			return where;
		} else {
			return null;
		}
	}

	public void draw(Graphics2D g2, int w){
		g2.drawImage(pic, null, village.getXLoc() * w / 30, village.getYLoc() * w / 30 + 50);
		g2.setFont(new Font("arial", Font.BOLD, 15));
		g2.drawString(village.getName(), village.getXLoc() * w / 30, (village.getYLoc() + 1) * w / 30);
	}

}
