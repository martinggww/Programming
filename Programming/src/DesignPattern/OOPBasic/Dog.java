package DesignPattern.OOPBasic;

public class Dog extends Animal{
	public Dog(){
		super();
	}
	private void privateAccess()
	{
		System.out.println("This is a private function");
	}
	public void publicAccess()
	{
		privateAccess();
		
	}
}
