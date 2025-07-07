package someMath.exceptions;

public class MathException extends Exception
{

	private static final long serialVersionUID = 1L;
	private final String msg;
	
	public MathException(String msg)
	{
		this.msg = msg;
	}
	
	public String getMessage()
	{
		return msg;
	}
}
