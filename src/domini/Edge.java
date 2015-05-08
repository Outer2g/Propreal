package domini;

public abstract class Edge
{	float weight;
	public Edge (){
		weight=1;
	}
	public float getWeight(){
		return this.weight;
	}
	public  void  setWeight(float weight){
		this.weight=weight;
	}
}