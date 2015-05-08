package domini;

import java.awt.List;
import java.util.ArrayList;

public class EventVotacio extends Event {
	private ArrayList<ArrayList<Integer>> votacions;
	public EventVotacio(int tipusEvent, Data dataEvent, String nomEvent) {
		super(tipusEvent, dataEvent, nomEvent);
	}
	public EventVotacio(Data dataEvent,String nomEvent){
		super(2,dataEvent,nomEvent);
		votacions= new ArrayList<ArrayList<Integer>>(4);
		for(int i=0;i<4;++i)votacions.add(new ArrayList<Integer>());
	}
	public void introduirDades(Integer diputat,int votacio){
		votacions.get(votacio).add(diputat);
	}
	public ArrayList<ArrayList<Integer>> consultaVotacions(){
		return (ArrayList<ArrayList<Integer>>) votacions.clone();
	}
}
