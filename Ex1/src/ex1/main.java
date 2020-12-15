package ex1;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class main {

	public static void main(String[] args) {
		
		 weighted_graph g = new WGraph_DS();
	        g.addNode(0);
	        g.addNode(1);
	       // g.addNode(1);
	        System.out.println("node size = " + g.nodeSize());
	        g.connect(0, 1, 16);
	        System.out.println("edge weight = " + g.getEdge(0, 1));
	        System.out.println("edge seze = " + g.edgeSize());
	        g.removeNode(2);
	        g.removeNode(1);
	       // g.removeNode(1);
	        int s = g.nodeSize();
	        System.out.println(s);
	        g.removeNode(1);
	       // assertEquals(1,s);
		/*
		weighted_graph gra = new WGraph_DS();
		gra.addNode(1);
		gra.addNode(1);
		gra.addNode(2);
		
		System.out.println(gra.getNode(2).getKey() == 2);
		
		System.out.println(gra.nodeSize() == 2);
		gra.connect(1, 2, 16.6);
		
		gra.addNode(3);
		gra.connect(1, 3, 4.6);
		
		System.out.println(gra.getV().size());
		
		System.out.println(gra.hasEdge(1, 2) == true);
		System.out.println(gra.edgeSize() ==2);
		System.out.println(gra.getEdge(1, 2) == 16.6);
		gra.removeEdge(1, 2);
		System.out.println(gra.hasEdge(1, 2) == false);
		System.out.println(gra.edgeSize() == 1);
		System.out.println(gra.getMC());
		gra.removeNode(1);
		System.out.println(gra.nodeSize() == 2);
		System.out.println(gra.edgeSize() == 0);
	*/
	}

}
