package ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class WGraph_Algo implements weighted_graph_algorithms {

	private weighted_graph gra;
	private PriorityQueue<node_info> qu;
	private HashMap<Integer, node_info> parents;
	private HashMap<Integer, Integer> visited;

	/**
	 * Default contractor  
	 */
	public WGraph_Algo() {

		this.qu = new PriorityQueue<node_info>();
		this.parents = new HashMap<Integer, node_info>();
		this.visited = new HashMap<Integer, Integer>();
	}

	@Override
	public void init(weighted_graph g) {

		this.gra = g;
		parents = new HashMap<Integer, node_info>();
		visited = new HashMap<Integer, Integer>();
		for (node_info n : g.getV()) {
			visited.put(n.getKey(), 0);
			parents.put(n.getKey(), null);
			n.setTag(Double.POSITIVE_INFINITY);


		}
	}

	@Override
	public weighted_graph getGraph() {

		return gra;
	}

	/**
	 * this function make the copy
	 * it copy the info of the nodes and the info of the edges that connect those nodes
	 * @param wg
	 * @param node_of_graph
	 */
	public void copyToGraph(WGraph_DS wg , LinkedList<node_info> node_of_graph) {


		node_info source = node_of_graph.get(0);
		wg.addNode(source.getKey());
		wg.getNode(source.getKey()).setTag(source.getTag());
		wg.getNode(source.getKey()).setInfo(source.getInfo());

		qu.add(source);

		while(!qu.isEmpty()) {

			node_info queue_node = qu.remove();
			node_of_graph.remove(queue_node);


			for(node_info ni : gra.getV(queue_node.getKey())) {
				if(wg.getNode(ni.getKey()) == null) {

					wg.addNode(ni.getKey());
					wg.getNode(ni.getKey()).setTag(ni.getTag());
					wg.getNode(ni.getKey()).setInfo(ni.getInfo()); 


					qu.add(ni);

					node_of_graph.remove(ni);
				}	
				if(!wg.hasEdge(ni.getKey(), queue_node.getKey())) {
					wg.connect(ni.getKey(), queue_node.getKey(), wg.getEdge(ni.getKey(), queue_node.getKey()));
				}
			}
			node_of_graph.remove(queue_node);


		}
	}

	@Override
	public weighted_graph copy() {

		WGraph_DS newGrap = new WGraph_DS();
		LinkedList<node_info> lis = new LinkedList<node_info>(gra.getV());

		while(!lis.isEmpty()) {
			copyToGraph(newGrap, lis);
		}
		return newGrap;
	}

	/**
	 * this function works just like Dijkstra Algorithm 
	 * Dijkstra get a starting node and checks it's neighbors
	 * after counting the sums of the neighbors we update the parents with weights and who is every nodes parent
	 * then we'll move to the next node with the smallest weight and do the same till we pass every node in the graph
	 * @param source
	 */
	private void dijkstra(node_info source) {

		qu.add(source);
		source.setTag(0);
		while (!qu.isEmpty()) {
			node_info tempNode =  qu.poll();
			LinkedList<node_info> neiList = new LinkedList<node_info>(gra.getV(tempNode.getKey()));


			for (node_info n : neiList) {	
				if(visited.get(n.getKey())== 0) {
					double newDist = gra.getEdge(n.getKey(), tempNode.getKey()) + tempNode.getTag();
					if(n.getTag() > newDist) {
						n.setTag(newDist);
						parents.put(n.getKey(), tempNode);
						qu.remove(n);
						qu.add(n);
					}
				}
			}
			visited.put(tempNode.getKey(), 1);
		}

	}

	@Override
	public boolean isConnected() {

		ArrayList<node_info> nodes = new ArrayList<node_info>(gra.getV());

		if(!nodes.isEmpty()) {

			init(gra);
			dijkstra(nodes.get(0));
			if(visited.containsValue(0)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {

		node_info end = gra.getNode(dest);
		node_info start = gra.getNode(src);
		if(start != null) {
			init(gra);
			dijkstra(start);
		}
		if (gra.getNode(dest) == null) {
			return -1;
		}
		return end.getTag();

	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {

		node_info end = gra.getNode(dest);
		init(gra);
		node_info start = gra.getNode(src);
		ArrayList<node_info> arr = new ArrayList<node_info>();

		if (gra.getNode(src) == null || gra.getNode(dest) == null) {
			return null;
		}

		dijkstra(end);

		while (start != end) {
			arr.add(start);
			start = parents.get(start.getKey());
		}
		arr.add(end);

		return arr;
	}

	@Override
	public boolean save(String file) {

		try {
			ObjectOutputStream objOS = new ObjectOutputStream(new FileOutputStream(file));
			objOS.writeObject(gra);
			objOS.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean load(String file) {

		try {
			ObjectInputStream objOS = new ObjectInputStream(new FileInputStream(file));
			try {

				this.gra = (weighted_graph) objOS.readObject();;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			}
			finally {
				objOS.close();
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
