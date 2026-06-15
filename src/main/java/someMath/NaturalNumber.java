package someMath;



import java.io.Serializable;
import java.util.Objects;



//Even so it implements addition, subtraction, multiplication and division this is
// NOT a mathematical Group!!!!
public class NaturalNumber extends Number implements Cloneable, Serializable
{

	private static final long serialVersionUID = 1L;

	
	public final Integer numberCore;

	/*Remember:*/
	//Extra private Constructor with out a possible Exception. For this two static Constants.
	public static final NaturalNumber zero = new NaturalNumber("0");
	public static final NaturalNumber one = new NaturalNumber("1");
	
	public NaturalNumber(Integer numberCore) throws NaturalNumberException
	{
		if(numberCore<0)throw new NaturalNumberException("Can't work with negative Integers.");

		this.numberCore = numberCore;
	}
	
	private NaturalNumber(String s)
	{
		this.numberCore = Integer.parseInt(s);
	}
	
	public NaturalNumber add(NaturalNumber nn) throws NaturalNumberException
	{
		return new NaturalNumber(numberCore+nn.getNumberCore());
	}
	
	public NaturalNumber multiply(NaturalNumber nn) throws NaturalNumberException
	{
		return new NaturalNumber(numberCore*nn.getNumberCore());
	}

	public boolean isGreaterThen(NaturalNumber n)
	{
		return numberCore>n.numberCore;
	}
	
	public boolean isSmallerThen(NaturalNumber n)
	{
		return numberCore<n.numberCore;
	}

	public Integer getNumberCore()
	{
		return numberCore;
	}
	
	public int hashCode()
	{
		return Objects.hash(numberCore);
	}
	
	public boolean equals(Object obj)
	{
		
		if (obj == this) return true;
		
	    if (!(obj instanceof NaturalNumber)) return false;
	    
	    NaturalNumber other = (NaturalNumber)obj;
	    
	    if(!other.getNumberCore().equals(numberCore))return false;
	    
	    return true;
	}

	public String toString()
	{
		return numberCore + "";
	}
	
	public NaturalNumber clone()
	{
		try
		{
			return new NaturalNumber(numberCore);
		}
		catch (NaturalNumberException e) 
		{
			
			System.out.println("Shouldn't happen!");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public double doubleValue()
	{
		return numberCore.doubleValue();
	}

	@Override
	public float floatValue()
	{
		return numberCore.floatValue();
	}

	@Override
	public int intValue()
	{
		return numberCore.intValue();
	}

	@Override
	public long longValue() 
	{
		return numberCore.longValue();
	}
}