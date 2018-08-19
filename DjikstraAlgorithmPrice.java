import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DjikstraAlgorithmPrice {
		  private final List<VillageNode> nodes;
		  private final List<Edge> edges;
		  private Set<VillageNode> settledNodes;
		  private Set<VillageNode> unSettledNodes;
		  private Map<VillageNode, VillageNode> predecessors;
		  private Map<VillageNode, Integer> price;

		  public DjikstraAlgorithmPrice() {
		    // create a copy of the array so that we can operate on this array
		    this.nodes = DirectedGraph.getVillages();
		    this.edges = DirectedGraph.getEdges();
		  }

		  public void execute(VillageNode source) {
		    settledNodes = new HashSet<VillageNode>();
		    unSettledNodes = new HashSet<VillageNode>();
		    price = new HashMap<VillageNode, Integer>();
		    predecessors = new HashMap<VillageNode, VillageNode>();
		    price.put(source, 0);
		    unSettledNodes.add(source);
		    while (unSettledNodes.size() > 0) {
		      VillageNode node = getMinimum(unSettledNodes);
		      settledNodes.add(node);
		      unSettledNodes.remove(node);
		      findMinimalprices(node);
		    }
		  }

		  private void findMinimalprices(VillageNode node) {
		    List<VillageNode> adjacentNodes = getNeighbors(node);
		    for (VillageNode target : adjacentNodes) {
		      if (getShortestprice(target) > getShortestprice(node)
		          + getprice(node, target)) {
		        price.put(target, getShortestprice(node)
		            + getprice(node, target));
		        predecessors.put(target, node);
		        unSettledNodes.add(target);
		      }
		    }

		  }

		  private int getprice(VillageNode node, VillageNode target) {
			  int price = node.getPrice(target);
				return price;
		  }

		  private List<VillageNode> getNeighbors(VillageNode node) {
			  List<VillageNode> neighbors = new ArrayList<VillageNode>();
				for (LystIterator i = new LystIterator(node.getNbs().getFront());!i.pastEnd(); i.increment()) {
					neighbors.add((VillageNode) i.getCurrent().getData());
				}
				return neighbors;
			}
			

		  private VillageNode getMinimum(Set<VillageNode> VillageNodees) {
		    VillageNode minimum = null;
		    for (VillageNode VillageNode : VillageNodees) {
		      if (minimum == null) {
		        minimum = VillageNode;
		      } else {
		        if (getShortestprice(VillageNode) < getShortestprice(minimum)) {
		          minimum = VillageNode;
		        }
		      }
		    }
		    return minimum;
		  }

		  private boolean isSettled(VillageNode VillageNode) {
		    return settledNodes.contains(VillageNode);
		  }

		  private int getShortestprice(VillageNode destination) {
		    Integer d = price.get(destination);
		    if (d == null) {
		      return Integer.MAX_VALUE;
		    } else {
		      return d;
		    }
		  }

		  /*
		   * This method returns the path from the source to the selected target and
		   * NULL if no path exists
		   */
		  public LinkedList<VillageNode> getPath(VillageNode target) {
		    LinkedList<VillageNode> path = new LinkedList<VillageNode>();
		    VillageNode step = target;
		    // check if a path exists
		    if (predecessors.get(step) == null) {
		      return null;
		    }
		    path.add(step);
		    while (predecessors.get(step) != null) {
		      step = predecessors.get(step);
		      path.add(step);
		    }
		    // Put it into the correct order
		    Collections.reverse(path);
		    return path;
		  }

		} 



