package util;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;

public class Grafo<E>
{
	private HashMap<E, LinkedList<E>> graph;
	
	public Grafo()
	{
		graph = new HashMap<E, LinkedList<E>>();
	}
	
	public Queue<E> bfs(int level, E rootPosition)
	{
		Queue<E> queue = new LinkedList<E>();
		
		queue.add(rootPosition);
		
		for(int i = 0; i<level; i++)
		{
			Queue<E> tempQueue = new LinkedList<E>();

			while(!queue.isEmpty())
			{
				E node = queue.remove();
								
				for(E child : getChildren(node))
				{
					tempQueue.add(child);
				}
			}
			
			queue = tempQueue;
			tempQueue.clear();
		}
				
		return queue;
	}
	
	public void addVertex(E node)
	{
		graph.put(node, new LinkedList<E>());
	}
	
	public void addEdge(E node1, E node2)
	{
		graph.get(node1).add(node2);
		graph.get(node2).add(node1);
	}

	private LinkedList<E> getChildren(E node)
	{
		return graph.get(node);
	}
}
