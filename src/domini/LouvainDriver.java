package domini;

import java.util.Scanner;

public class LouvainDriver {
	private static void tryGraph() {
		Graph <Node,Edge> g= new Graph <Node,Edge>();
		for(int i=0;i<3;++i) g.addNode(new NodeLouvain(String.valueOf(i)));
		Node[] nodes=new Node[3];
		g.getAllNodes().toArray(nodes);
		EdgeLouvain e=new EdgeLouvain();
		e.setWeight(1);
		g.addEdge(nodes[0], nodes[1],e);
		g.addEdge(nodes[0], nodes[2], new EdgeLouvain());
		g.addEdge(nodes[1], nodes[2], new EdgeLouvain());
		NodeLouvain[] n=new NodeLouvain[3];
		g.getAllNodes().toArray(n);
		for(int i=0;i<3;++i) System.out.println(n[i]+" ");
		System.out.println(OpsLouvain.weighted_degree(g, nodes[0]));
		TrueLouvain T= new TrueLouvain();
		T.algorithm(g);
	}
	private static void tryGraph2(){
		
	}
	private static void tryGraph3(){
		
	}

	public static void main(String[] args) {
		Graph <Node,Edge> g;
		int n=0;
		Scanner inputs=new Scanner(System.in);
		tryGraph();
		/*while(n!=-1){
			n=inputs.nextInt();
			switch(n){
			case 1:
				tryGraph();
				break;
			case 2:
				tryGraph2();
				break;
			case 3: tryGraph3(); break;
			}
			
		}*/
		
	}

}
