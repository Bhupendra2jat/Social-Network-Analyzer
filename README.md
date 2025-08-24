# Social-Network-Analyzer


Social Network Analysis Tool (Java):-
This command-line tool, built in Java, analyzes relationship structures within a network. It's designed to model a corporate environment, providing insights into internal communication, collaboration, and influence.

---------------------------------------------------------------------

Core Features & Real-World Application:-

1. Degrees of Separation (The "Internal LinkedIn" Feature)
What it does: Calculates the shortest connection path between any two users using a Breadth-First Search (BFS) algorithm.

Real-World Problem: In large companies, it's difficult to find the right person to contact in other departments. This feature acts as an internal "LinkedIn," showing the shortest professional path to connect with anyone, breaking down communication barriers and making collaboration faster.

2. Influencer Identification (Finding Key Connectors)
What it does: Implements a simplified PageRank algorithm to identify the most influential and well-connected individuals in the network, regardless of their official job title.

Real-World Problem: When rolling out new policies or technologies, this tool identifies key "go-to" people. By engaging these influencers first, a company can leverage their social capital to drive adoption and spread information more effectively than a generic mass email.

3. Community Detection (Mapping the "Real" Org Chart)
What it does: Analyzes the network to find distinct clusters of highly interconnected users.

Real-World Problem: The official org chart doesn't show how work actually gets done. This feature reveals informal, cross-departmental teams and communities. Management can use this to foster collaboration, identify potential innovation hubs, and understand the company's true operational structure.

--------------------------------------------------------------------------

Technical Deep Dive:-

Data Structures & Algorithms
This project leverages fundamental computer science concepts to function. Below is a breakdown of the key components.



Data Structures Used
Graph (Adjacency List): The entire social network is stored as a graph. Specifically, it uses an adjacency list, which is implemented with a HashMap. The keys of the map are the users (nodes), and the value for each key is a List of their connections (edges). This structure is highly efficient for social networks, where users are connected to a small fraction of the total network.

HashMap: Provides the core structure for our adjacency list, allowing for constant-time O(1) average lookup of any user's connections.

--
Queue: Used in the Breadth-First Search (BFS) algorithm to keep track of which users to visit next. Its First-In, First-Out (FIFO) nature is essential for exploring the graph level by level.

--
HashSet: Used to keep track of visited nodes during graph traversals, ensuring that the algorithms don't get stuck in infinite loops and don't do redundant work. It provides O(1) average time complexity for add and contains operations.

--
Algorithms Implemented
Breadth-First Search (BFS): This is a graph traversal algorithm used for two key features: finding the shortest path ("Degrees of Separation") and detecting communities. It works by starting at a given user and exploring all of their direct neighbors first, then their neighbors' neighbors, and so on, level by level.

PageRank (Simplified): This algorithm, famously used by Google to rank web pages, is adapted here to find influential users. It works iteratively: each user starts with an equal "rank" score. In each iteration, users "pass" their rank to their connections. After several iterations, users who have many connections from other well-connected users accumulate a higher rank, identifying them as influencers.

----------------------
CV / Portfolio Summary Points
Developed a comprehensive Social Network Analysis command-line tool in Java from the ground up, utilizing graph data structures (adjacency lists) to efficiently model and manage complex user relationships within a network.

Implemented core graph traversal and analysis algorithms, including Breadth-First Search (BFS) to calculate the "degrees of separation" between users and a simplified PageRank algorithm to identify key influencers and communication hubs within the organization.

Engineered a feature for community detection that identifies distinct clusters of interconnected employees, providing valuable insights for improving cross-departmental collaboration, understanding informal team structures, and targeted internal communications.










### How Communities Are Built Automatically
--
The communities are found automatically using an algorithm that works like an "island explorer." In graph theory, what we're looking for are called **"connected components."**

Here’s the step-by-step process the code follows:

1.  **Pick a Starting Point:** The algorithm starts with a list of all users and an empty "visited" list. It picks the first user in the main list (let's say, Alice) who hasn't been visited yet.

2.  **Explore the Island:** Starting with Alice, it uses **Breadth-First Search (BFS)** to find everyone she is connected to. It finds Bob and Charlie. It adds them to a "to-visit" queue and marks them as "visited."

3.  **Map the Entire Island:** The algorithm then visits Bob and finds his connection, Diana. Then it visits Charlie and finds his connections. It keeps exploring like this, fanning out from the starting point until it has found every single person who can be reached from Alice, no matter how many steps it takes.

4.  **Declare the Island a Community:** Once it can't find any new people connected to this group, it declares everyone it found as **"Community 1."**

5.  **Look for New Islands:** The algorithm then goes back to the main list of all users and looks for the next person who *hasn't* been visited yet (in our sample data, this would be Jake). It repeats the whole process, starting a new exploration from Jake, and finds his separate island of connections (Kevin and Laura). This becomes **"Community 2."**

It continues this until every single user has been visited and assigned to a community.

***

### Do They Change With Time?

That's the crucial point for a real-world application.

In **our current program, no, the communities do not change.** The network is built once when you press "Run," and the analysis is performed on that single snapshot in time.

However, in a **real company, absolutely, they would change constantly.**

* **New Connections:** If a new project starts that requires someone from Engineering (`Diana (Eng)`) to work with someone from HR (`Kevin (HR)`), a new connection is formed. The moment you add `graph.addConnection("Diana (Eng)", "Kevin (HR)")`, our two separate communities would merge into one giant community.
* **People Leaving:** If a key connector like `Mallory (Mgmt)` leaves the company, many paths between departments might be broken, potentially splitting one large community back into smaller ones.
* **New Hires:** New employees join and form connections, growing their respective communities.

To see these changes in our tool, you would have to **update the `buildNetwork` function with the new connections and re-run the program.** A more advanced, real-world version of this tool would be connected to a live database (like the company's email or chat logs) and would re-calculate the communities periodically (e.g., every week) to show how the organization is evolving.
