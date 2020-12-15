package ex1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

public class WGraph_DS implements weighted_graph, Serializable {

	private Hashtable<Integer, node_info> nodes;
	private int numOfEdges;
	private int modeCount;

	/**
	 * This method Initialize the fields 
	 * @param nodes
	 * @param numOfEdges
	 * @param modeCount
	 */
	public WGraph_DS(Hashtable<Integer, node_info> nodes, int numOfEdges, int modeCount) {

		this.nodes = nodes;
		this.numOfEdges = 0;
		this.modeCount = 0;
	}

	/**
	 * Default contractor 
	 */
	public WGraph_DS() {

		this.nodes = new Hashtable<Integer, node_info>();
		this.numOfEdges = 0;
		this.modeCount = 0;
	}

	/**
	 * 
	 * @author Flamed Soul
	 *
	 */
	private class NodeInfo implements node_info, Comparable<node_info> , Serializable{

		private int id; // unique key
		private Hashtable<Integer ,  Edge> nieghbors;	//<key=unip key , value= contain the node that conected by the edge and the weight of the the edge >
		private String info;
		private double tag;	//the distance of the node from the source

		@Override
		public boolean equals(Object obj) {

			if (obj == null) {
				return false;
			}
			if (obj.getClass() != this.getClass()) {
				return false;
			}
			NodeInfo other = (NodeInfo)obj;


			return other.id == this.id && other.nieghbors.equals(this.nieghbors) && other.info.equals(this.info) && other.tag == this.tag;
		}

		/**
		 * 	This method Initialize the fields 
		 * @param id
		 */
		public NodeInfo(int id) {

			this.id = id;
			this.nieghbors = new Hashtable<Integer, Edge>();
			this.info = "id: " + id;
			this.tag = Double.POSITIVE_INFINITY;
		}

		@Override
		public int getKey() {

			return id;
		}

		@Override
		public String getInfo() {

			return info;
		}

		@Override
		public void setInfo(String s) {

			this.info = s;
		}

		@Override
		public double getTag() {

			return tag;
		}

		@Override
		public void setTag(double t) {

			this.tag = t;
		}

		@Override
		public int compareTo(node_info o) {
			if (o.getTag() < this.tag ) {
				return 1;
			}
			else if (o.getTag() == this.tag) {
				return 0;
			}
			else {
				return -1;
			}

		}

	}
	@Override
	public node_info getNode(int key) {

		return nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) {

		NodeInfo n1 = (NodeInfo)nodes.get(node1);
		NodeInfo n2 = (NodeInfo)nodes.get(node2);

		return n1!= null && n2 != null && n1.nieghbors.containsKey(node2) && n2.nieghbors.containsKey(node1);
	}

	@Override
	public double getEdge(int node1, int node2) {

		if (!hasEdge(node1, node2)) {
			return -1;
		}
		NodeInfo n1 = (NodeInfo)nodes.get(node1);

		return n1.nieghbors.get(node2).getWeight();
	}

	@Override
	public void addNode(int key) {

		if (!nodes.containsKey(key)) {
			nodes.put(key, new NodeInfo(key));
			modeCount++;
		}

	}

	@Override
	public void connect(int node1, int node2, double w) {

		NodeInfo n1 = (NodeInfo)nodes.get(node1);
		NodeInfo n2 = (NodeInfo)nodes.get(node2);

		if (node1 != node2 && n1 != null && n2 != null) {
			if (!hasEdge(node1, node2)) {
				numOfEdges++;
			}
			n1.nieghbors.put(node2,new Edge(w, n2));
			n2.nieghbors.put(node1,new Edge(w, n1));
			modeCount++;
		}

	}

	@Override
	public Collection<node_info> getV() {

		return nodes.values();

	}

	@Override
	public Collection<node_info> getV(int node_id) {

		NodeInfo n1 = (NodeInfo)nodes.get(node_id);	//nieghbors collection
		ArrayList<node_info> listNie = new ArrayList<node_info>();

		for (Edge edges : n1.nieghbors.values() ) {
			listNie.add(edges.getInf());
		}


		return listNie;

	}

	
	@Override
	public node_info removeNode(int key) {

		NodeInfo n1 = (NodeInfo)nodes.get(key);
		

		if(n1 != null) {
			ArrayList<Edge> nieghborList = new ArrayList<Edge>(n1.nieghbors.values());

			for (Edge edges : nieghborList) {
				//problem with removing a none connected node
				NodeInfo n2 = (NodeInfo)edges.getInf();
				n1.nieghbors.remove(n2.getKey());
				n2.nieghbors.remove(key);
				numOfEdges--;
				modeCount++;
			}
			modeCount++;
		}
		
		return nodes.remove(key);
	}
	
	
	@Override
	public void removeEdge(int node1, int node2) {

		NodeInfo n1 = (NodeInfo)nodes.get(node1);
		NodeInfo n2 = (NodeInfo)nodes.get(node2);

		if(hasEdge(node1, node2)) {

			n1.nieghbors.remove(node2);
			n2.nieghbors.remove(node1);
			numOfEdges--;
			modeCount++;

		}
	}

	@Override
	public int nodeSize() {

		return nodes.size();
	}

	@Override
	public int edgeSize() {

		return numOfEdges;
	}

	@Override
	public int getMC() {

		return modeCount;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		WGraph_DS other = (WGraph_DS)obj;

		return other.numOfEdges == this.numOfEdges && other.modeCount == this.modeCount && other.nodes.equals(this.nodes);
	}

}
