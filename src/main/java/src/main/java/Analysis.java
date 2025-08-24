
import java.util.*;
import java.util.stream.Collectors;

public class Analysis {

    
    public static List<String> degreesOfSeparation(Graph graph, String startUser, String endUser) {
        if (!graph.getUsers().contains(startUser) || !graph.getUsers().contains(endUser)) {
            System.out.println("One or both users not found in the network.");
            return Collections.emptyList();
        }

        
        Queue<List<String>> queue = new LinkedList<>();
        
        queue.add(Arrays.asList(startUser));

        
        Set<String> visited = new HashSet<>();
        visited.add(startUser);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String currentUser = path.get(path.size() - 1);

            if (currentUser.equals(endUser)) {
                return path; 
            }

            for (String friend : graph.getConnections(currentUser)) {
                if (!visited.contains(friend)) {
                    visited.add(friend);
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(friend);
                    queue.add(newPath);
                }
            }
        }

        return Collections.emptyList(); 
    }

    public static List<List<String>> findCommunities(Graph graph) {
        List<List<String>> communities = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String user : graph.getUsers()) {
            if (!visited.contains(user)) {
                List<String> newCommunity = new ArrayList<>();
                Queue<String> queue = new LinkedList<>();

                queue.add(user);
                visited.add(user);

                while (!queue.isEmpty()) {
                    String currentUser = queue.poll();
                    newCommunity.add(currentUser);
                    for (String friend : graph.getConnections(currentUser)) {
                        if (!visited.contains(friend)) {
                            visited.add(friend);
                            queue.add(friend);
                        }
                    }
                }
                communities.add(newCommunity);
            }
        }
        return communities;
    }

    public static List<Map.Entry<String, Double>> identifyInfluencers(Graph graph) {
        Set<String> users = graph.getUsers();
        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        int numUsers = users.size();
        double d = 0.85; 
        int iterations = 20;

        Map<String, Double> pagerank = new HashMap<>();
        for (String user : users) {
            pagerank.put(user, 1.0 / numUsers);
        }

        for (int i = 0; i < iterations; i++) {
            Map<String, Double> newPagerank = new HashMap<>();
            for (String user : users) {
                double rankSum = 0;
                for (String otherUser : users) {
                    List<String> connections = graph.getConnections(otherUser);
                    if (connections.contains(user)) {
                        rankSum += pagerank.get(otherUser) / connections.size();
                    }
                }
                double newRank = (1 - d) / numUsers + d * rankSum;
                newPagerank.put(user, newRank);
            }
            pagerank = newPagerank;
        }

        return pagerank.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
