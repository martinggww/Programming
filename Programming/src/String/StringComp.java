package String;

import java.util.*;

public class StringComp {
	//string.compareTo(another string) returns the difference between two strings...
	public static void main(String[] args){
		String str1 = "Hello world";
		String str2 = "hello world";
		
		System.out.println(str1.compareTo(str2));
		System.out.println(str1.compareToIgnoreCase(str2));
	}
}
