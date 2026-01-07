package someMath.exceptions;

public class ConsoleToolsException extends Exception
{

	private static final long serialVersionUID = 4880126760509943403L;
	
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