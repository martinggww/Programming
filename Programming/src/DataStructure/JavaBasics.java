package DataStructure;

public class JavaBasics {
	byte myByte;   //1 byte, 8bits, -128 -  127
	short myShort; //2 bytes, 16bits -32768 32767
	int myInt;     //4 bytes, 32bits, 2^-31, 2^32 -1
	long myLong;   //8 bytes, 64bits, 2^-63, 2^64-1
	float myFloat; //4 bytes, 32bits floating number
	double myDouble; // 8 bytes, 64 bits, floating number
	boolean myBoolen;  //1 bit 
	char myChar;       // 2 bytes, 16bits unicode character.
	String myString;
	
	public JavaBasics(){
		//Three types of assignments
		myInt = 128;
		myInt = 0x12;
		myInt = 0b11010;
		
		// 12.4f, f is a must using decrator
		myFloat = 12.4f;
		myDouble = 12.34e2;
	}
	public void MyArray(){
		//Declare an array
		int[] myArray;
		myArray = new int[10];
		
		char[] myCharArray = new char[10];
		float[] myFloatArray = new float[10];
		https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html
		
	}
}
