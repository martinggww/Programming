package DataStructure;

import DataStructure.Node;
public class Stack {
  Node top_node;
  public Node top(){
	  if(top_node != null)
		  return top_node;
	  else
		  return null;
  }
  public Node pop(){
	  if(top_node == null)
		  return null;
	  else{
		  Node ret_node = new Node(top_node.val);
		  top_node = top_node.next;
		  return ret_node;
	  }
  }
  
  public void push(Node node){
	  if(node == null)
		  return;
	  node.next = top_node;
	  top_node = node;
	  return;
  }
}
