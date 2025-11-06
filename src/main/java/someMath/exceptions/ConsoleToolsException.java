package someMath.exceptions;

public class ConsoleToolsException extends Exception
{

	private final String msg;

	public ConsoleToolsException(String msg)
	{
		this.msg = msg;
	}
	
	public String getMessage()
	{
		return msg;
	}
}