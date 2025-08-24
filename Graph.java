
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    
    private Map<String, List<String>> adjList;

    public Graph() {
        this.adjList = new HashMap<>();
    }

    public void addUser(String user) {
       
        adjList.putIfAbsent(user, new ArrayList<>());
    }

    public void addConnection(String user1, String user2) {
       
        addUser(user1);
        addUser(user2);

        
        if (!adjList.get(user1).contains(user2)) {
            adjList.get(user1).add(user2);
        }
       
        if (!adjList.get(user2).contains(user1)) {
            adjList.get(user2).add(user1);
        }
    }

    
    public Set<String> getUsers() {
        return adjList.keySet();
    }

    public List<String> getConnections(String user) {
        return adjList.getOrDefault(user, new ArrayList<>());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String user : adjList.keySet()) {
            sb.append(user).append(" -> ");
            List<String> connections = adjList.get(user);
            if (connections.isEmpty()) {
                sb.append("No connections");
            } else {
                sb.append(String.join(", ", connections));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

