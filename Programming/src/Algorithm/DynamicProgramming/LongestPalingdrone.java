/*
 * Given a string S, find the longest palindromic substring in S. 
 * You may assume that the maximum length of S is 1000, and 
 * there exists one unique longest palindromic substring.
 * Show Tags
 * 
 * maintain a two D array, pal[i][j]
 * Start condition:
 * 
 * p[i][i] = 1
 * p[i][i+1] = 1, if s.charAt(i) == s.charAt(i+1)
 *           = 0, if s.charAt(i) != s.charAt(i+1)
 * change condition:
 * p[i+1][j-1] = 1, if p[i][j] = 1, and p[i-1] == p[j+1]             
 * */
package Algorithm.DynamicProgramming;

public class LongestPalingdrone {
	
public static void String Solution(String s)
{
	if(s == null)	
		return null;
	if(s.length() <= 1)
		return s;
	int max_len = 0;
	String longest_str = null;
	
	int length = s.length();
	
	int[][] table = new int[length][length];
	
	//Initiate the table
	for(int i=0;i<length;i++)
		table[i][i] = 1;
	
	for(int i=0;i<=length-2; i++){
		if(s.charAt(i) == s.charAt(i+1)){
			table[i][i+1] =1 ;
			longest_str = s.substring(i, i+2);
		}
	}
				
		
}
public static void main(String[] args){
	String s= "1234554321";
	String longest_palingdrone = Solution(s);
	return;
}
}
