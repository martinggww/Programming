package Algorithm.DFS_BFS;

import java.util.Queue;
import java.util.LinkedList;

class Node{

Node left;
Node right;
int value;
	Node(int value){
		this.value = value;
		left = null;
		right = null;
	}
}

public class BFS {
	public static void printBFS(Node root){
		if(root == null)
			return;
		Queue<Node> q = new LinkedList<Node>();
		Queue<Node> p = new LinkedList<Node>();
		q.add(root);
		while(!q.isEmpty()){
			Node cur = q.poll();
			System.out.println(cur.value);
			if(cur.left != null)
			  p.add(cur.left);
			if(cur.right != null)
			  p.add(cur.right);
			q = p;
		}
		return;
	}
public static void main(String[] args)
{
	Node root = new Node(10);
	root.left = new Node(7);
	root.right = new Node(11);
	root.left.left = new Node(5);
	root.left.right = new Node(9);
	root.right.right = new Node(19);
	printBFS(root);
	
	
	return;
}
}
