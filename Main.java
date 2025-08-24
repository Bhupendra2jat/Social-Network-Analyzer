
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

 
  public static void buildNetwork(Graph graph) {
    graph.addConnection("Alice", "Bob");
    graph.addConnection("Alice", "Charlie");
    graph.addConnection("Bob", "Diana");
    graph.addConnection("Charlie", "Diana");
    graph.addConnection("Charlie", "Eve");
    graph.addConnection("Diana", "Frank");
    graph.addConnection("Eve", "Frank");
    graph.addConnection("Frank", "Grace");
    graph.addConnection("Grace", "Heidi");
    graph.addConnection("Heidi", "Ivan");
    graph.addConnection("Ivan", "Grace");
    //  separate community
    graph.addConnection("Jake", "Kevin");
    graph.addConnection("Kevin", "Laura");
    graph.addConnection("Laura", "Jake");
    graph.addConnection("Alice (Eng)", "Bob (Eng)");
    graph.addConnection("Alice (Eng)", "Charlie (Eng)");
    graph.addConnection("Bob (Eng)", "Diana (Eng)");
    graph.addConnection("Charlie (Eng)", "Diana (Eng)");

    // --- Marketing Department ---
    graph.addConnection("Eve (Mkt)", "Frank (Mkt)");
    graph.addConnection("Eve (Mkt)", "Grace (Mkt)");
    graph.addConnection("Frank (Mkt)", "Grace (Mkt)");

    // --- Sales Department ---
    graph.addConnection("Heidi (Sales)", "Ivan (Sales)");
    graph.addConnection("Heidi (Sales)", "Judy (Sales)");
    graph.addConnection("Ivan (Sales)", "Judy (Sales)");

    // --- HR Department (A smaller, separate community initially) ---
    graph.addConnection("Kevin (HR)", "Laura (HR)");

    // --- Management & Cross-Departmental Links ---
    // Diana from Engineering is a key connector
    graph.addConnection("Diana (Eng)", "Frank (Mkt)");
    graph.addConnection("Diana (Eng)", "Heidi (Sales)"); 
    graph.addConnection("Diana (Eng)", "Mallory (Mgmt)"); 

    // Grace from Marketing also connects to Sales
    graph.addConnection("Grace (Mkt)", "Ivan (Sales)"); 

    // Mallory is the central management figure
    graph.addConnection("Mallory (Mgmt)", "Eve (Mkt)");
    graph.addConnection("Mallory (Mgmt)", "Heidi (Sales)");
    graph.addConnection("Mallory (Mgmt)", "Kevin (HR)");
    System.out.println("Sample social network has been built.");
  }

  public static void main(String[] args) {
    Graph socialGraph = new Graph();
    buildNetwork(socialGraph);

    Scanner scanner = new Scanner(System.in);
    String choice = "";

    while (!choice.equals("5")) {
      System.out.println("\n--- Social Network Analysis Menu ---");
      System.out.println("1. Find Degrees of Separation");
      System.out.println("2. Identify Communities");
      System.out.println("3. Find Top Influencers");
      System.out.println("4. Print Graph");
      System.out.println("5. Exit");

      System.out.print("Enter your choice (1-5): ");
      choice = scanner.nextLine();

      switch (choice) {
        case "1":
          System.out.print("Enter the first user's name: ");
          String user1 = scanner.nextLine();
          System.out.print("Enter the second user's name: ");
          String user2 = scanner.nextLine();
          List<String> path = Analysis.degreesOfSeparation(socialGraph, user1, user2);
          if (!path.isEmpty()) {
            System.out.println("Path: " + String.join(" -> ", path));
            System.out.println("Degrees of separation: " + (path.size() - 1));
          } else {
            System.out.println("No path found between the users.");
          }
          break;
        case "2":
          List<List<String>> communities = Analysis.findCommunities(socialGraph);
          System.out.println("\n--- Detected Communities ---");
          for (int i = 0; i < communities.size(); i++) {
            System.out.println("Community " + (i + 1) + ": " + String.join(", ", communities.get(i)));
          }
          break;
        case "3":
          List<Map.Entry<String, Double>> influencers = Analysis.identifyInfluencers(socialGraph);
          System.out.println("\n--- Top Influencers (by PageRank) ---");
          for (Map.Entry<String, Double> entry : influencers) {
            System.out.printf("%s: %.4f\n", entry.getKey(), entry.getValue());
          }
          break;
        case "4":
          System.out.println("\n--- Current Network Graph ---");
          System.out.println(socialGraph);
          break;
        case "5":
          System.out.println("Exiting. Goodbye!");
          break;
        default:
          System.out.println("Invalid choice. Please enter a number between 1 and 5.");
          break;
      }
    }
    scanner.close();
  }
}
