import java.util.ArrayList;

public class DirectedGraph {

	private static ArrayList<Edge> edgelist = new ArrayList<Edge>();
	private static ArrayList<VillageNode> villagenodes = new ArrayList<VillageNode>();
	private static ArrayList<Gnome> gnomes = new ArrayList<Gnome>();

	public DirectedGraph() {

	}

	public static void addEdge(Edge e) {
		edgelist.add(e);
	}

	public static void addNode(VillageNode v) {
		villagenodes.add(v);
	}

	public static ArrayList<Edge> getEdges() {
		return edgelist;
	}
	
	public static void addGnome(Gnome g){
		gnomes.add(g);
	}

	public static ArrayList<VillageNode> getVillages() {
		return villagenodes;
	}

	public static void removeEdge(Edge e) {
		edgelist.remove(e);
	}

	public static boolean VillageExists(int x, int y) {
		for (int i = 0; i < villagenodes.size(); i++) {
			if (villagenodes.get(i).getVillage().getXLoc() == x && villagenodes.get(i).getVillage().getYLoc() == y) {
				return true;
			}
		}
		return false;
	}

	public static VillageNode getVillage(int x, int y) {
		for (int i = 0; i < villagenodes.size(); i++) {
			if (villagenodes.get(i).getVillage().getXLoc() == x && villagenodes.get(i).getVillage().getYLoc() == y) {
				return villagenodes.get(i);
			}
		}
		return null;
	}
	// end of for loop

	public static void evacuate(VillageNode v) {
		//evacuates a village before deletion
		for (int i = 0; i < gnomes.size();i++) {
			if (gnomes.get(i).getDestination() == v.getVillage() || gnomes.get(i).getCurrent() == v.getVillage()){
				int index = (int) (Math.random() * villagenodes.size());
				while (villagenodes.get(index).equals(v)) {
					index = (int) (Math.random() * villagenodes.size());
				}
				gnomes.get(i).setCurrent(villagenodes.get(index).getVillage());
			//	i.getCurrent().getData().setCurrent(villagenodes.get(index).getVillage());
				System.out.println(gnomes.get(i) + " has been evacuated from "
						+ gnomes.get(i) + " to " + villagenodes.get(index).getVillage());
			}
		}
		for (int i = 0; i < DirectedGraph.getVillages().size(); i++) {
			System.out.println("test");
			ArrayList<Gnome> gnomes = DirectedGraph.getVillages().get(i).getVillage().getResidents();
			for (int j = 0; j < gnomes.size(); j++) {
				if (gnomes.get(j).getDestination() != null
						&& gnomes.get(j).getDestination().equals(v.getVillage())) {
					gnomes.get(j).setDestination(v.getVillage());
				}
			}
		}
	}

	public static void removeNode(VillageNode v) throws AlreadyExistsException {
		Node<VillageNode> frontq = v.getpointing().getFront();
		for (LystIterator<VillageNode> i = new LystIterator(frontq); !i.pastEnd(); i.increment()) { 
			// connects all nodes that are pointing at v to all nodes v is pointing to
			DirectedGraph.connectVillages((VillageNode) i.getCurrent().getData(), v);
			try {
				i.getCurrent().getData().removeEdge(v);
			} catch (NotFoundException e) {
				System.out.println("Not even connected");
			} // end of try catch
		} // end of for loop
		Node<VillageNode> nbfront = v.getNbs().getFront();
		for (LystIterator<VillageNode> i = new LystIterator(nbfront); !i.pastEnd(); i.increment()) {
			try {
				v.removeEdge(i.getCurrent().getData());
			} catch (NotFoundException e) {
				System.out.println("has no edges");
			}
		}
		DirectedGraph.villagenodes.remove(v);
	}

	public static void connectVillages(VillageNode first, VillageNode second) throws AlreadyExistsException {
		Node<VillageNode> frontq = second.getNbs().getFront();
		for (LystIterator i = new LystIterator(frontq); !i.pastEnd(); i.increment()) {
			if (first.edgeExists((VillageNode) i.getCurrent().getData())) { // if
																			// edge
																			// exists
																			// don't
																			// create
																			// it
				System.out.println("Edge connection already exists");
			} else {
				first.addEdge((int)(Math.random() * 50), 20, (VillageNode) i.getCurrent().getData()); // otherwise
																				// connect
																				// this
																				// village
																				// to
																				// all
																				// the
																				// neighbors
																				// of
																				// that
																				// village
			}

		}
	}
	// end of for loop

	public static void addVillage(VillageNode v) {
		
		VillageNode newV = v;
		VillageNode closest = DirectedGraph.getVillages().get(0);
		double closestDistance = Integer.MAX_VALUE;
		VillageNode close = DirectedGraph.getVillages().get(1);
		double closeDistance = Integer.MAX_VALUE;
		for (int i = 0; i < DirectedGraph.getVillages().size(); i++) {
			VillageNode temp = DirectedGraph.getVillages().get(i);
			double distance = Math.sqrt(((newV.getVillage().getXLoc() - temp.getVillage().getXLoc())
					* (newV.getVillage().getXLoc() - temp.getVillage().getXLoc()))
					+ ((newV.getVillage().getYLoc() - temp.getVillage().getYLoc())
							* (newV.getVillage().getYLoc() - temp.getVillage().getYLoc())));
			if (distance < closestDistance && !temp.equals(newV)) {
				closestDistance = distance;
				closest = temp;
			}
		}

		for (int i = 0; i < DirectedGraph.getVillages().size(); i++) {
			VillageNode temp = DirectedGraph.getVillages().get(i);
			double distance = Math.sqrt(((newV.getVillage().getXLoc() - temp.getVillage().getXLoc())
					* (newV.getVillage().getXLoc() - temp.getVillage().getXLoc()))
					+ ((newV.getVillage().getYLoc() - temp.getVillage().getYLoc())
							* (newV.getVillage().getYLoc() - temp.getVillage().getYLoc())));
			if (distance < closeDistance && !temp.equals(closest) && !temp.equals(newV)) {
				closeDistance = distance;
				close = temp;
			}
		}
		try {
			newV.addEdge((int)(Math.random() * 50), (int)(closeDistance), close);
			close.addEdge((int)(Math.random() * 50), (int)(closeDistance), newV);
		} catch (AlreadyExistsException aee) {
			;
		}
		try {
			closest.addEdge((int)(Math.random() * 50), (int)(closeDistance), newV);
			newV.addEdge((int)(Math.random() * 50), (int)(closeDistance), closest);
		} catch (AlreadyExistsException aee) {
			;
		}
	}
}
