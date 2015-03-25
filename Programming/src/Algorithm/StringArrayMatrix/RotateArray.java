package Algorithm.StringArrayMatrix;

import java.util.Arrays;

/*
 * You may have been using Java for a while. 
 * Do you think a simple Java array question can be a challenge? 
 * Let's use the following problem to test.

Problem: Rotate an array of n elements to the right by k steps. 
For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is 
rotated to [5,6,7,1,2,3,4].

How many different ways do you know to solve this problem?
 * */

public class RotateArray {
	public static void reverse(int[] array, int begin, int end)
	{
		if(begin > end)
			return;
		int i= begin;
		int j= end;
		int temp;
		while(i < j){
			temp = array[j];
			array[j] = array[i];
			array[i] = temp;
			i++;
			j--;
		}
		return;
	}
	public static void Solution(int[] array, int k){
		//solution 1: copy 0-k-1 elements to string 1
		// copy k-n-1 element to string2
		// concrate string2 + string1, O(n), O(n)
		
		//Solution2 : What about time O(n), space O(1)
		// rotate 0-k-1, rotate k-n-1
		//rotate all 
		int length = array.length;
		if(k>length)
		
		reverse(array, 0, k-1);
		reverse(array, k, length -1);
		reverse(array, 0, length-1);
		return;
	}
	public static void main(String[] args){
		int array[] = {1,2,3,4,5,6,7};
		Solution(array, 3);
		return;
	}

}
