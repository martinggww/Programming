package Practice;
/*
 * Problem:
   You have got a range of numbers between 1 to N, where one of the number is
   repeated. You need to write a program to find out the duplicate number.
   Thoughts:
   Since the range is fixed and number are all positive integers, define an array size of N
   Scan the range of numbers, if a[i] is 0, fill it with 1; if a[i] is 1, return 1;
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FindDuplicate {

	public static int findDuplicate(List<Integer> my_list){
		List<Integer> hash_list = new ArrayList<Integer>();
		hash_list.clear();
		
		// Java's iterate a loop
		// int value = hash_list.get(i);
		// int value = hash_list.set(index, value);
		for(int i=0;i<hash_list.size() ;i ++){
			int value = hash_list.get(i);
			if(value == 0)
				hash_list.set(i, 1);
			if(value == 1)
				return i;
		}
		
		Iterator<Integer> itr = hash_list.iterator();
		while(itr.hasNext()){
			int value = itr.next();
		}
		
		return 0;
	}
	public static void main(String[] args){
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i=0;i<30;i++)
			numbers.add(i);
		//Add an duplicate number
		numbers.add(22);
		int dup = findDuplicate(numbers);
		return ;
	}
	
}
