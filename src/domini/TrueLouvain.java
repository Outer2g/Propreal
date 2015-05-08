package domini;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class TrueLouvain extends Algorithm {
	private Graph <Node,Edge> g;
	private int[] n2cFinal;
	private class LouvainCom{
		private Graph<Node,Edge> g;
		private int size;
		private int[] n2c;//node to community
		private float[] in;
		private float[] tot;
		private Node[] node; 
		
		LouvainCom(Graph <Node,Edge> graph){
			this.g=graph;
			node=(Node[]) g.getAllNodes().toArray();
			size=node.length;
			n2c=new int[size];
			in= new float[size];
			tot= new float[size];
			for(int i=0;i<size;++i){
				n2c[i]=i;
				in[i]= OpsLouvain.nb_selfloops(g, node[i]);
				tot[i]= OpsLouvain.weighted_degree(g,node[i]);
			}
		}
		
		public void remove(int node,int comm,double linksToComm){
			tot[comm] -=  OpsLouvain.weighted_degree(g,this.node[node]);
			in[comm] -= 2*linksToComm +  OpsLouvain.nb_selfloops(g,this.node[node]);
			n2c[node]=-1;
		}
		
		public void insert(int node,int comm,double linksToComm){
			tot[comm] +=  OpsLouvain.weighted_degree(g,this.node[node]);
			in[comm] += 2*linksToComm + OpsLouvain.nb_selfloops(g,this.node[node]);
			n2c[node]=comm;
		}
		
		public double modularityGain(int node,int comm, double linksToComm,double wDegree){
			double totcom=tot[comm];
			double degcom=wDegree;
			double m= OpsLouvain.getTotalWeight(g);
			double linkscom=linksToComm;
			double r=(linkscom-totcom*degcom/m);
			return r;
		}
		
		public int neighComm(int node,int[] veins,double[] weights){
			veins=new int[size-1];
			weights = new double[size-1];
			for (int i=0;i<size-1;++i) weights[i]=0.0;
			Vector<Pair<Integer,Float>> p=OpsLouvain.neighbors(g,node);
			int degreeN=g.getAdjacentNodesTo(this.node[node]).size();
			veins[0]=n2c[node];
			weights[veins[0]]=0;
			int nvecinos=1;
			for(int i=0;i<degreeN;++i){
				int neigh=p.get(i).GetFirst();
				int neighCom=n2c[neigh];
				double neighW=p.get(i).GetSecond();
				if(neigh!=node){
					if(weights[neighCom]==-1){
						weights[neighCom]=0.0;
						veins[nvecinos]=neighCom;
						++nvecinos;
					}
					weights[neighCom]+=neighW;
				}
			}
			return nvecinos;
		}
		
		double modularity(){
			double q=0;
			double m=OpsLouvain.getTotalWeight(g);
			for(int i=0;i<size;++i){
				if(tot[i]>0) q+= (in[i]/m) -(tot[i]/m)*(tot[i]/m);
			}
			return q;
		}
		
		public Graph<Node,Edge> partition2graph(){
			Graph<Node,Edge> gf2=new Graph<Node,Edge>();
			ArrayList <Integer> communities= new ArrayList<Integer>();
			//per a cada node, faig add de la seva comunitat al arraylist communities(per l'implementacio de l'add, no tindre repetides)
			for(int i=0;i<size;++i){
				communities.add(n2c[i]);
			}
			int numeroComunitats=communities.size();
			double [] comunitats= new double[numeroComunitats];
			double [][] comToCom= new double[numeroComunitats][numeroComunitats];
			//Per a cada comunitat creo un node al nou graf
			for (int i=0;i<numeroComunitats;++i){
				gf2.addNode( new NodeLouvain());
				//inicialitzacions per millorar rendiment
				comunitats[i]=0;
				for(int j=0;j<numeroComunitats;++j) comToCom[i][j]=0;
			}
			//per cada node, agafo la suma de edges que van a ell i acumulo la suma a la comunitat pertinent si els dos nodes son de la mateixa comunitat
			//si no, acumulo la suma en el array comToCom que, per la comunitat i acumula la suma cap a la comunitat j
			
			for(int i=0;i<size;++i){
				int comi=n2c[i];
				for(int j=i;j<size;++j){
					if(comi==n2c[j]) comunitats[comi]+= g.getEdge(node[i], node[j]).getWeight();
					else comToCom[i][j] += g.getEdge(node[i], node[j]).getWeight();
				}
			}
			//Per a cada node del nou graf, li poso el edge corresponent segons elsvectors abans calculats
			Node[] nodesNew= new Node[numeroComunitats];
			gf2.getAllNodes().toArray(nodesNew);
			for(int i=0;i<numeroComunitats;++i){
				//para [i][i] comunitats
				EdgeLouvain e= new EdgeLouvain();
				e.setWeight((float) comunitats[i]);
				gf2.addEdge(nodesNew[i], nodesNew[i],e);
				//para[i][j] comToCom
				for(int j=0;j<numeroComunitats;++j){
					e=new EdgeLouvain();
					e.setWeight((float) comToCom[i][j]);
					gf2.addEdge(nodesNew[i], nodesNew[j], e);
				}
			}
			return gf2;
		}
		
		public boolean oneLevel(){
			boolean improvement=false;
			int moves=0;
			int passes=0;
			double newMod=modularity();
			double mod=newMod;
			// reordenem el vector d'ids de nodes de forma aleatoria per tal de tenir millor resultats en els calculs
			int [] random= new int[size];
			Random r=new Random();
			//ponemos a cada posicion i, la propia i para lujego hacer un shuffle entre ella en posiciones random del vector
			for(int i=0;i<size;++i)random[i]=i;
			for(int i=0;i<size;++i){
				int rpos= r.nextInt()%(size-1);
				int aux= random[i];
				random[i]=random[rpos];
				random[rpos]=aux;
			}
			//itera mientras haya ganancia de modularidad (newMod-mod>0)
			while(moves>0 && newMod-mod>0){
				mod=newMod;
				moves=0;
				++passes;
				for(int i=0;i<size;++i){
					int nodeid=random[i];
					int comunitat=n2c[nodeid];
					double weightDegree=OpsLouvain.weighted_degree(g,node[nodeid]);
					//buscamos los links hacia otras comunidades, cogiendo su peso
					int[] veins=new int[0];
					double[] weights= new double[0];
					int nvecinos= neighComm(nodeid,veins,weights);
					//quitamos el nodo de su comunidad actual
					remove(nodeid,comunitat,weights[comunitat]);
					//Buscamos la mejor comunidad entre sus vecinos
					int bestCommunity=comunitat;
					double linksMillor=0.0;
					double bestIncrease=0.0;
					for(int j=0;j<nvecinos;++i){
						int vei=veins[j];
						double millora=modularityGain(nodeid, vei, weights[vei],weightDegree);
						if(millora>bestIncrease){
							bestCommunity=vei;
							linksMillor=weights[vei];
							bestIncrease=millora;
						}
					}
					//insertem el node a la millor comunitat trobada
					insert(nodeid,bestCommunity,linksMillor);
					if(bestCommunity!=comunitat){
						++moves;
						improvement=true;
					}
					
				}
				double totalTot=0.0;
				double totalIn=0.0;
				for(int i=0;i<tot.length;++i){
					totalTot+=tot[i];
					totalIn+=in[i];
				}
				
			}
			return improvement;
		}
		
		
	}
	public TrueLouvain() {
		
	}

	@Override
	public Solution algorithm(Graph<Node, Edge> g) {
		LouvainCom c=new LouvainCom(g);
		boolean improvement=true;
		double mod=c.modularity();
		double newMod;
		while(improvement){
			improvement=c.oneLevel();
			newMod=c.modularity();
			g=c.partition2graph();
			c=new LouvainCom(g);
			mod=newMod;
		}
		return null;
	}

}
