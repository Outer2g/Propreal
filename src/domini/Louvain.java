package domini;

import java.util.HashMap;
import java.util.Vector;

public class Louvain {
	private GraphLouvain g;
	private class LouvainCom{
		private GraphLouvain g;
		private int size;
		private HashMap<Integer,Double> neigh_weight;//Peso de los vecinos
		private HashMap<Node,Integer> neigh_pos;
		private Integer neigh_last;
		private HashMap<Node,Integer> n2c;//comunidad a la que pertenece el nodo
		private HashMap<Integer,Double> in,tot;
		private Node[] nodes;
		private int steps;
		private double minIncrease;
		LouvainCom(GraphLouvain g,int steps, double minIncrease){
			this.g=g;
			size=g.GetAllNodes().size();
			
			neigh_weight= new HashMap<Integer,Double>();
			neigh_pos= new HashMap<Node,Integer>();
			neigh_last=0;
			
			n2c=new HashMap<Node,Integer>();
			in=new HashMap<Integer,Double>();
			tot=new HashMap<Integer,Double>();
			nodes=(Node[]) g.GetAllNodes().toArray();
			for(int i=0;i<size;++i){
				n2c.put(nodes[i],i);
				tot.put(i,g.weighted_degree(nodes[i]));
				in.put(i,g.nb_selfloops(i));
			}
			this.steps=steps;
			this.minIncrease=minIncrease;
		}
		public double modularity(){
			double q=0.0;
			double m= (double) g.getTotalWeight();
			for(int i=0; i<size;++i){
				if(tot.get(i)>0) q+=((double)in.get(i)/m)-((double)tot.get(i)/m)*((double)tot.get(i)/m);
			}
			return q;
		}
		
		
		public GraphLouvain partition(){
			int[] renumber=new int[size];
			for(int i=0;i<size;++i) renumber[n2c.get(i).intValue()]++;
			
			return g;
		}
		
		
		public boolean oneLevel(){
			boolean improvement=false;
			int moves=1; //1 per a que faci la primera iteracio
			int passesDone=0;
			double newMod=modularity();
			double curMod=0.0;
			boolean first=true;
			while((moves>0 && (newMod-curMod)>minIncrease)||first){
				first=false;
				curMod=newMod;
				moves=0;
				++passesDone;
				//Remove from community, insert in the best
				for(int i=0;i<size;++i){
					Node node=nodes[i];
					Integer nodeCom=new Integer(n2c.get(node));
					double weightDegree = g.weighted_degree(node);
					//Vecinos
					neighComms(node);
					//remove from current comm
					remove(node,nodeCom,neigh_weight.get(nodeCom));
					//get best community
					Integer bestCommunity= new Integer(nodeCom);
					double bestnbLinks=0.0;
					double bestIncr=0.0;
					for(int j=0;i<neigh_last;++i){
						Integer neighPos=neigh_pos.get(nodes[j]);
						double increase=modularityGain(node,neighPos,neigh_weight.get(neighPos),weightDegree);
						if(increase>bestIncr){
							bestCommunity=neighPos;
							bestnbLinks=neigh_weight.get(neighPos);
							bestIncr=increase;
						}
					}
					//insert in best comm
					insert(node,bestCommunity,bestnbLinks);
					
					if(bestCommunity!=nodeCom) ++moves;
				}
				double totalTot=0;
				double totalIn=0;
				for(int j=0;j<tot.size();++j){
					totalTot+=tot.get(j);
					totalIn+=in.get(j);
				}
				if(moves>0) improvement=true;
				newMod=modularity();
				
			}
		return improvement;
		}
		private void neighComms(Node node){
			for(int i=0;i<neigh_last;++i){
				Integer neighPos= neigh_pos.get(nodes[i]);
				neigh_weight.put(neighPos,(double)-1);
			}
			neigh_last=0;
			Vector<Pair<Node,Double>> p= g.neighbors(node);
			int degree=g.nb_neighbors(node);
			neigh_pos.put(nodes[0],n2c.get(node));
			neigh_last=1;
			for(int i=0;i<degree;++i){
				Node neigh=p.get(i).GetFirst();
				Integer neighComm=new Integer(n2c.get(neigh));
				double neighW;
				if(g.getWeights().size()==0) neighW=1.0;
				else neighW=p.get(i).GetSecond();
				if(neigh!=node){
					if(neigh_weight.get(neighComm)==-1){
						neigh_weight.put(neighComm, 0.0);
						++neigh_last;
						neigh_pos.put(nodes[neigh_last], neighComm);
						
					}
					double w=neigh_weight.get(neighComm);
					w+=neighW;
					neigh_weight.put(neighComm,w);
				}
			}
		}
		private void remove(Node node,Integer nodeCom,Double degree){
			double aux=tot.get(nodeCom);
			aux-=g.weighted_degree(node);
			tot.put(nodeCom, aux);
			aux=in.get(nodeCom);
			aux-= 2*degree + g.nb_selfloops(node);
			n2c.put(node,-1);
		}
		private double modularityGain(Node node,Integer neighPos,Double neighWeight,double weightDegree){
			double totcommunity=tot.get(neighPos);
			double degc =neighWeight;
			double m=g.total_weight;
			double degree=weightDegree;
			return (degree-(totcommunity*degc/m));
			}
		private void insert(Node node,Integer bestCommunity,double bestnbLinks){
			double aux=tot.get(bestCommunity);
			aux+=g.weighted_degree(node);
			tot.put(bestCommunity, aux);
			aux=in.get(bestCommunity);
			aux+= 2*bestnbLinks + g.nb_selfloops(node);
			n2c.put(node,-1);
		}
	}
	public Louvain(GraphLouvain g){
		this.g=g;
	}
	public void aplicaLouvain(int steps,double minIncrease){
		//First phase
		//Cogemos todos los nodos y le asignamos una comunidad a si mismos
		LouvainCom c=new LouvainCom(g,steps,minIncrease);
		boolean improvement=true;
		double mod=c.modularity(),newMod;
		int level=0;
		while(improvement){
			improvement=c.oneLevel();
			newMod=c.modularity();
			g=c.partition();
			c= new LouvainCom(g,steps,minIncrease);
			mod=newMod;
			++level;
			if(level==steps) improvement=true;
		}
		
	}
}