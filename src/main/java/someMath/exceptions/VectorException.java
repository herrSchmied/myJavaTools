package someMath.exceptions;

public class VectorException extends Exception
{
	private static final long serialVersionUID = 1L;
	private final String msg;
	
	
	public VectorException(String msg)
	{
		this.msg = msg;
	}
	
	public String getMessage()
	{
		return msg;
	}

}
