package someMath;



import java.lang.Double;


import someMath.exceptions.MathException;



public final class DoubleField implements Field<Double>
{

	public DoubleField()
	{
		
	}

	@Override
	public Double add(Double a1, Double a2)
	{
		return a1+a2;
	}

	@Override
	public Double multiply(Double a1, Double a2)
	{
		return a1*a2;
	}

	@Override
	public Double negate(Double a1) throws MathException
	{
		return -a1;
	}

	@Override
	public Double inverse(Double a1) throws MathException
	{
		if(a1.equals(0.0)) throw new MathException("Zero has no Multiplication inverse!");
		
		return (1.0/a1);
	}

	@Override
	public Double zero()
	{
		return 0.0;
	}

	@Override
	public Double one()
	{
		return 1.0;
	}
	
	public int hashCode()
	{
		return DoubleField.class.hashCode();
	}
	
	public boolean equals(Object other)
	{	
	    return other instanceof DoubleField;
	}


}