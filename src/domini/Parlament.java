package domini;

import java.util.Vector;

public class Parlament {
	private Vector<Integer> diputats;
	public Parlament() {
		diputats=new Vector<Integer>();
		Integer d1=new Integer(1);
		Integer d2=new Integer(2);
		diputats.addElement(d1);
		diputats.addElement(d2);
	}
	public Vector<Integer> getParlament(){
		
		return (Vector<Integer>) diputats.clone();
	}
}