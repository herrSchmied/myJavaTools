package someMath.exceptions;


public class SnakeException extends Exception
{

	private final String msg;
	
	public SnakeException(String msg)
	{
		this.msg = msg;
	}
	
	
	public String getMessage()
	{
		return msg;
	}
}
