package Algorithm;
/*
 * You are given two linked lists representing two non-negative numbers. 
 * The digits are stored in reverse order and each of their nodes contain a single digit. 
 * Add the two numbers and return it as a linked list.

   Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
   Output: 7 -> 0 -> 8
   
   Thoughts:
   Use linked list, traverse the linked list from 
 * */
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class AddTwoNumbers {

public static void addTwoNumbers(List<Integer> l1,
								 List<Integer> l2,
			                     List<Integer> result){
	if(l1.isEmpty()){
		result.addAll(l2);
	}		
	if(l2.isEmpty())
		result.addAll(l1);
	
	Iterator<Integer> itr1 = l1.iterator();
	Iterator<Integer> itr2 = l2.iterator();
	int carry =0;
	int sum =0;
	while(itr1.hasNext() && itr2.hasNext()){
		int val1 = itr1.next();
		int val2 = itr2.next();
		
		sum = val1 + val2 + carry;
		carry = sum/10;
		sum = sum%10;
		result.add(sum);
	}
	while(itr1.hasNext()){
		int val = itr1.next();
		sum = val + carry;
		carry = sum/10;
		sum = sum%10;
		result.add(sum);
	}
	while(itr2.hasNext()){
		int val1 = itr1.next();
		sum = val1 + carry;
		carry = sum/10;
		sum = sum%10;
		result.add(sum);
	}
	if(carry > 0)
		result.add(carry);
	
	return;
}

public class ListNode {
     int val;
     ListNode next;
     ListNode(int x) {
         val = x;
         next = null;
     }
}

public ListNode addTwoNumber(ListNode l1, ListNode l2) {
	if(l1 == null)
		return l2;
	if(l2 == null)
		return l1;
	
	int carry =0;
	int sum =0;
	
	ListNode result = new ListNode(0);
	ListNode p = result;
	while(l1 != null && l2 != null){
		//Calculate the sume and carry;
		sum = l1.val + l2.val + carry;
		carry = sum/10;
		sum = sum%10;
		
		ListNode node = new ListNode(sum);
		p.next = node;
		p=node;
		
		l1 = l1.next;
		l2 = l2.next;
	}
	while(l1 != null){
		sum = l1.val + carry;
		carry = sum/10;
		sum = sum%10;
		
		ListNode node = new ListNode(sum);
		p.next = node;
		p=node;

		
		l1 = l1.next;
	}
	while(l2 != null){
		sum = l2.val + carry;
		carry = sum/10;
		sum = sum%10;
		
		ListNode node = new ListNode(sum);
		p.next = node;
		p=node;

		
		l2 = l2.next;
	}
	if(carry != 0){
		ListNode node = new ListNode(carry);
		p.next = node;
		p=node;

	}
	return result.next;    
}

public static void main(String[] args)
{
	List<Integer> l1 = new LinkedList<Integer>();
	List<Integer> l2 = new LinkedList<Integer>();
	l1.add(0);
	//l1.add(4);
	//l1.add(3);
	//l1.add(3);
	
	l2.add(0);
	//l2.add(6);
	//l2.add(4);
	
	List<Integer> result = new LinkedList<Integer>();
	addTwoNumbers(l1, l2, result);
	
	for(int num: result)
		System.out.println(num+"->");
    
	return;	
}
}
