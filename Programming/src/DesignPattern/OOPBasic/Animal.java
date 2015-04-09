package DesignPattern.OOPBasic;

public class Animal {
	private String name;
	private int weight;
	
	public void eat()
	{
		System.out.println("Eat");	
	}
	public void setWeight(int _weight){
		weight = _weight;
		
	} 
	public void setName(String _name){
		name = _name;
	}
	public int getWeight(){
		return weight;
	}
	public String getName(){
		return name;
	}
}


