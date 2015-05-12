package domini;

public class Node {
        private String id;
       
        public Node(){
        }
       
        public Node(String id){
                this.id = id;
        }
       
        public String getId(){
                return id;
        }
       
        public int GetId(){
                return Integer.decode(id);
        }
       
        public void SetId(String id){
                this.id = id;
        }
}