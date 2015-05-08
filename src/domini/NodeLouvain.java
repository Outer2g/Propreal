package domini;

public class NodeLouvain extends Node {
	String s;
	public NodeLouvain(){
		s="comunitat";
	}
	public NodeLouvain(String s) {
		this.s=s;
	}

	@Override
	public String getId() {
		return s;
	}
	

}
