package Algorithm.StringArrayMatrix;

/*
 * Evaluate the value of an arithmetic expression in 
 * Reverse Polish Notation.

   Valid operators are +, -, *, /. Each operand may be an 
   integer or another expression.

Some examples:
  ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 * */
public class EvaluateReversePolishNotation {

import java.util.*;

public static int Solution(String[] tokens)
{
	//Read tokens, if reading a number, push to the stack.
	//if +, -, *, /, pop two numbers, do the calculation
	//then push the number back the to stack.
	//if end of string, return the values
	String operators = "+-*/";
	Stack<Integer> stack = new Stack<Integer>(); 
	for(String str : tokens){
		if(!operators.contains(str)){
			stack.push(Integer.valueOf(str));
		}else{
			int a = stack.peek();
			stack.pop();
			int b = stack.peek();
			stack.pop();
			
			int result;
			switch(str){
			case "+":
				result = a+b;
				break;
			case "-":
				result = a-b;			
			}
			stack.push(result);
		}
	}
	return stack.peek();
}
public static void main (String[] args) {
	String[] tokens = new String[]{"2", "1","+", "3"};
	System.out.println(Solution(tokens));
}

}