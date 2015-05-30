package domini;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LouvainDriver {
	private static void tryGraph() {
		Graph <Node> g= new Graph <Node>();
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
		Louvain T= new Louvain();
		Solution s=T.getSolution(g);

		System.out.println("comms");
		for(int i=0;i<s.getNumCommunities();++i){
			ArrayList<Community> p= s.getCommunities();
			System.out.println("com : " +i);
			for(int j=0;j<p.size();++j){
				Community pp= p.get(i);
				System.out.println("numbernodes: "+pp.getNumberOfNodes());
				List<Node> ns=pp.getCommunity();
				for(int x=0;x<pp.getNumberOfNodes();++x){
					System.out.print(ns.get(x).getId()+" ");
				}
			}
		}
	}
	private static void tryGraph2(){
		Graph<Node> g= new Graph<Node>();
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
		Louvain T=new Louvain();
		Solution s=T.getSolution(g);
		System.out.println("comms: "+ s.getNumCommunities());
		for(int i=0;i<s.getNumCommunities();++i){
			ArrayList<Community> p= s.getCommunities();
			System.out.println("com : " +i);
			for(int j=0;j<p.size();++j){
				Community pp= p.get(i);
				System.out.println("numbernodes: "+pp.getNumberOfNodes());
				List<Node> ns=pp.getCommunity();
				for(int x=0;x<pp.getNumberOfNodes();++x){
					System.out.print(ns.get(x).getId()+" ");
				}
				System.out.println();
			}
		}
		System.out.println(s.getTime());
	}
	private static void tryGraph3(){
		Graph<Node> g= new Graph<Node>();
		Louvain T= new Louvain();
		T.getSolution(g);
	}

	public static void main(String[] args) {
		Graph <Node> g;
		int n=0;
		boolean b=true;
		while(b) tryGraph2();
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
