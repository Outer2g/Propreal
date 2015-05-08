package domini;
 
import java.util.Vector;
  
public class Event {
        private String nom;
        private Data data;
        private int tipus;     
        private Vector<Integer> diputatsAssociats;
        private boolean trobat(Integer d){
                int n=diputatsAssociats.size();
                for(int i=0;i<n;++i){
                        if(diputatsAssociats.get(i).equals(d)) return true;
                }
                return false;
        }
        public Event(int tipusEvent,Data dataEvent,String nomEvent){
                nom=nomEvent;
                data= dataEvent;
                this.tipus=tipusEvent;
                this.diputatsAssociats= new Vector<Integer>();
        }
       
        public void eliminarDiputats(Vector<Integer> diputats){
                this.diputatsAssociats.removeAll(diputats);
        }
       
        public boolean associarDiputats(Vector<Integer> diputats){
                for (int d: diputats) {
                        if (diputatsAssociats.contains(d)) return false;
                }
                this.diputatsAssociats.addAll(diputats);
                return true;
        }
               
        public void modificarData(Data data){
                this.data=data;
        }
        public String consultarNomEvent(){
                return this.nom;
        }
        public Data consultarData(){
                return this.data;
        }
        public Integer consultarTipus(){
                return (this.tipus);
        }
        public Vector<Integer> consultarAssociats(){
                return this.diputatsAssociats;
        }
}