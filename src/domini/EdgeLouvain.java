package domini;

public class EdgeLouvain extends Edge {
	float weight;
	public EdgeLouvain() {
		weight=1;
	}

	@Override
	public float getWeight() {
		
		return weight;
	}

	@Override
	public void setWeight(float weight) {
		
		this.weight=weight;
	}

}
