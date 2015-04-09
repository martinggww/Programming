package DesignPattern.OOPBasic;

public class WorkingWithAnimals {
	int name;
public static void main(String[] args){
	
	Animal dog = new Dog();
	Animal[] animals = new Animal[4];
	animals[0] = new Dog();
	speakAnimal(dog);
	((Dog)dog).eat();
	
	// System.out.println(name); 
	// This is wrong, because you can't use a non-static variable in a static method!
	// sayHello();
	// Also wrong, you can't use a non-static function in a static function
	
	//wrong, can't access private function
	//dog.privateAccess();
	//Wrong: can't access dog's function
	//dog.publicAccess();
	
	Dog fredo = new Dog();
	fredo.publicAccess(); //correct
	
	Griffe griffe = new Griffe();
	griffe.setName("Andy");
	

}
public static void speakAnimal(Animal animal){
	
	System.out.println(animal.getWeight());
}

public void sayHello(){
	System.out.println(name);
}
}
