package domini;

import java.util.ArrayList;
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
		TrueLouvain T= new TrueLouvain();
		T.algorithm(g);
	}
	private static void tryGraph2(){
		Graph<Node,Edge> g= new Graph<Node,Edge>();
		for(int i=0;i<6;++i)g.addNode(new NodeLouvain(String.valueOf(i)));
		Node[] nodes = new Node[6];
		g.getAllNodes().toArray(nodes);
		EdgeLouvain e= new EdgeLouvain();
		System.out.println(nodes[0].getId());
		g.addEdge(nodes[0],nodes[1], e);
		g.addEdge(nodes[0], nodes[2], e);
		g.addEdge(nodes[1], nodes[2], e);
		g.addEdge(nodes[2], nodes[3], e);
		g.addEdge(nodes[3], nodes[4], e);
		g.addEdge(nodes[3], nodes[5], e);
		g.addEdge(nodes[4], nodes[5], e);
		NodeLouvain[] n =new NodeLouvain[6];
		g.getAllNodes().toArray(n);
		g.print();
		TrueLouvain T=new TrueLouvain();
		Solution s=T.algorithm(g);
		for(int i=0;i<s.getNumCommunities();++i){
			ArrayList<Community> p= s.getCommunities();
			for(int j=0;j<p.size();++j) {
				
			}
		}
	}
	private static void tryGraph3(){
		Graph<Node,Edge> g= new Graph<Node,Edge>();
		TrueLouvain T= new TrueLouvain();
		T.algorithm(g);
	}

	public static void main(String[] args) {
		Graph <Node,Edge> g;
		int n=0;
		Scanner inputs=new Scanner(System.in);
		while(n!=-1){
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
			
		}
		
	}

}
