package DataStructure;

import java.util.*;

public class StringArrayMatrix {
  public static void main(String[] args){
	String my_string;
	my_string = "Hello World";
	//toCharArray()
	char [] my_array = my_string.toCharArray();
	
	//get index char
	char c = my_string.charAt(0);
	
	//length of string
	int len1 = my_string.length();
	
	//length of array[]
	int len2 = my_array.length;
	
	//substring
	String sub_sub_string = my_string.substring(0, 6);
	
	//substring
	String sub_sub_string1 = my_string.substring(0);
	
	//Integer.valueOf()
	int i = Integer.valueOf('1');
	int j = Integer.valueOf(1);
	
	// String.valueOf()
	String my_string2 = String.valueOf(1);
	
	int[] int_array = {3,4,7,2,4};
	//Can sort almost everything...
	//Arrays.sort(byte, char, int, float, double,)
	Arrays.sort(int_array);

	System.out.println(c);
	return;
  }
}
