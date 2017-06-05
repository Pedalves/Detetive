package util;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import jogo.Cell;

public class Grafo<E>
{
	private HashMap<E, LinkedList<E>> graph;
	
	private final Class<E> _typeParameterClass;
	
	public Grafo(Class<E> typeParameterClass)
	{
		_typeParameterClass = typeParameterClass;
		graph = new HashMap<E, LinkedList<E>>();
	}
	
	public ArrayList<E> bfs(int level, E rootPosition) throws Exception
	{
		if(!graph.containsKey(rootPosition))
		{
			throw new Exception("Invalid rootPosition");
		}
		
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
					if(_typeParameterClass == Cell.class)
					{
						if(!((Cell)child).getOcuppied())
						{
							tempQueue.add(child);
						}
					}
					else
					{
						tempQueue.add(child);
					}
				}
			}
			
			queue = tempQueue;
		}
		ArrayList<E> available = new ArrayList<>();
		queue.forEach(available::add);
		
		return available;
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
	
	public static void main(String arg[])
	{		
		Grafo<Integer> g = new Grafo<Integer>(Integer.class);  
		
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);
		g.addVertex(6);
		
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		
		ArrayList<Integer> q = null;
		try {
			q = g.bfs(3, 1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		q.forEach(item -> {
			System.out.println(item);
		});
	}
}
