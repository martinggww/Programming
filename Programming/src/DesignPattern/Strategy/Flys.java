public interface Flys
{
    String fly();
}

public ItFlys implements Flys
{
    public String fly(){
	return "Flying High";
    }
}

public CantFly implements Flys
{
    public String fly(){
	return "I can't fly";
    }
}