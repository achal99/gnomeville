import java.util.HashSet;
import java.util.LinkedList;

public class TopologicalSort {
	private static LinkedList<VillageNode> sorted = new LinkedList<VillageNode>(); //keeps track of the top sorter
	private static HashSet<VillageNode> visited = new HashSet<VillageNode>();
	
	public static void topSort() throws Exception {
		for (VillageNode vn : DirectedGraph.getVillages()){
			if(!visited.contains(vn)) {
				visitNode(vn,visited);
			}
		}
	}
	
	 private static void visitNode(VillageNode node, HashSet<VillageNode> set) throws Exception { 
	        /* if n has a temporary mark then stop (not a DAG) */
	        if (node.visited()) { 
	            throw new Exception("graph cyclic");

	        /* if n is not marked (i.e. has not been visited) then... */
	        } else { 

	            /* mark n temporarily [using boolean field in node]*/
	            node.setVisited(true);

	            /* for each node m with an edge n to m do... */
	            Node<VillageNode> neighbsfront = node.getNbs().getFront();
	            for(LystIterator i = new LystIterator(neighbsfront);!i.pastEnd(); i.increment()) {
	            	if(!set.contains(i.getCurrent().getData())) {
	            		visitNode((VillageNode) i.getCurrent().getData(),set);
	            	}
	            }

	            /* mark n permanently */
	            set.add(node);

	            /* unmark n temporarily */
	            node.setVisited(false);

	            /* add n to head of L */
	            sorted.addFirst(node);
	        }
	    }
	 
	 public static void printToped() {
		 for (VillageNode vn : sorted) {
			 System.out.println(vn.getName());
		 }
	 }
	 
	 
	


}
