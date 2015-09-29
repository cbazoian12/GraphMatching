import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Graph 
{
	//an ArrayList of nodes.
	ArrayList<Node> V = new ArrayList<Node>();
	// an array to demonstrate matches already found
	ArrayList<Match>matches = new ArrayList<Match>(V.size());
	
	
	
	Graph(String fileName) 
	{
		try {
			createGraph(fileName);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGraph(String fileName) throws IOException, NullPointerException
	{
		try{
			//create a scanner to read in the file
			Scanner reader = new Scanner(new FileInputStream(fileName));
			
			while(reader.hasNext()){
				//read in the next line and store in "line"
				String line = reader.nextLine();
				//reading in current line, separate each element by "," and put into an array.
				String lineList[] = line.split(",");
				//create a temp node
				Node tempNode = new Node(lineList[0]);
				
				for(int i = 1; i < lineList.length; i++)
				{
					tempNode.edgeTo.add(lineList[i]); //add edges
				}				
				V.add(tempNode); //add the node to the graph V
			}
			System.out.println("Graph created successfully.");
		}
		catch(IOException e){ System.err.println("IOException" + e);	}
		catch(NullPointerException n){System.err.println("Null pointer exception" + n);}		
	}
	
	public void matching(Node root)
	{
		Queue<Node> queue = new LinkedList<Node>();
		
		boolean[] visited = new boolean[V.size()];
		
		if(root == null){ System.out.println("Root is null."); return;	}
		
		queue.add(root); //add root to the queue
		
		while(!queue.isEmpty())
		{
			//remove next item from queue
			Node parent = queue.remove();			
			//visit node
			visited[V.indexOf(parent)] = true;			
			//get child nodes
			for(int i = 0; i < parent.edgeTo.size(); i++)
			{
				for(int j = 0; j < V.size(); j++)
				{
					if(V.get(j).name.equals(parent.edgeTo.get(i)))
					{
						Node child = V.get(j);
						
						if(visited[V.indexOf(child)] == false){queue.add(child);}

						if(containedInMatches(parent.name) == false && containedInMatches(child.name)== false)
						{
							createMatch(parent.name,child.name);
						}
					}
				}
			}
		}
	}
	
	public boolean containedInMatches(String node){
		for(int i = 0; i < matches.size(); i++){
			if(matches.get(i).head == node){return true;}
			else if(matches.get(i).tail == node){return true;}
		}			
		return false;		
	}
	
	
	
	//An inner class to store nodes
	public class Node<E>
	{
		//The name of the node
		String name;
		
		// an ArrayList to store the names of the nodes there is an edge to.
		ArrayList<String> edgeTo = new ArrayList<String>(); 
		
		//constructor
		Node(String name)
		{
			this.name = name;
		}
	}
	
	public class Match<E>
	{
		String head;
		String tail;
		
		public String getHead()
		{
			return head;
		}
		public void setHead(String head)
		{
			this.head = head;
		}
		public String getTail()
		{
			return this.tail;
		}
		public void setTail(String tail)
		{
			this.tail = tail;
		}
	}
	
	public void createMatch(String head, String tail)
	{
		Match temp = new Match();
		temp.setHead(head);
		temp.setTail(tail);
		matches.add(temp);
	}
	
	public void printMatches()
	{
		System.out.println("Number of Vertices: " + V.size() );
		System.out.println("Number of Matches: " + matches.size());
		System.out.println("Matches: ");
		
		for(int i = 0; i < matches.size(); i++)
		{
			System.out.println(matches.get(i).head + " " + matches.get(i).tail);
		}
		
	}
	
	public static void main(String[] args) 
	{
		Graph graph = new Graph("testgraph.txt");
		
		graph.matching(graph.V.get(0));
		
		graph.printMatches();
		
	}
}


