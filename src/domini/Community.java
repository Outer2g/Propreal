package domini;

import java.util.*;
 
public class Community {
   
    private Map<String, Node> com;
   
    public Community() {
            com = new HashMap<String,Node> (0);
    }
   
    public boolean addNode(Node n) {
            if (com.containsKey(n.getId() )) {
                    return false;
            }
            else {
                    com.put(n.getId(), n);
                    return true;
            }
           
    }
   
    public boolean deleteNode(String id) {
                    if (com.remove(id) == null) {
                            return false;
                    }
                    return true;
    }
   
    public boolean checkNode(String id) {
            return com.containsKey(id);    
    }
   
    public List<Node> getCommunity() {
            List<Node> list = new ArrayList<Node> ();
            for (Map.Entry<String, Node> entry : com.entrySet()) {
                    list.add(entry.getValue());
            }
            return list;
    }
   
    public int getNumberOfNodes() {
            return com.size();
    }
}