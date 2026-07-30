package someMath;

import javafx.util.Pair;
import someMath.exceptions.MathException;

public class RationalField implements Field<RationalNumber>
{

	public RationalField()
	{
		
	}

	@Override
	public RationalNumber add(RationalNumber a1, RationalNumber a2) throws MathException
	{
		
		RationalNumber a3;
		
		try
		{
			a3 = a1.add(a2);
			return a3;
		}
		catch (NaturalNumberException | MathException e)
		{
			// 
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public RationalNumber multiply(RationalNumber a1, RationalNumber a2) throws MathException
	{

		RationalNumber a3;
		
		try
		{
			a3 = a1.multiply(a2);
			return a3;
		}
		catch (NaturalNumberException | MathException e)
		{
			// 
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RationalNumber sumInverse(RationalNumber a1) throws MathException
	{
		
		try
		{
			if(a1.equals(RationalNumber.zero))return RationalNumber.zero; 
			return new RationalNumber(!(a1.sign), a1.getIntegerPart(), a1.getNumerator(), a1.getDenominator());
		}
		catch (NaturalNumberException | MathException e)
		{
			// 
			e.printStackTrace();
			return null;
		}		
	}

	@Override
	public RationalNumber multiplyInverse(RationalNumber a1) throws MathException
	{

		if(a1.equals(RationalNumber.zero)) throw new MathException("Zero has no inverse.");
		try
		{

			int one = 1;
			if(!a1.sign)one = -1;
			Pair<Integer, Integer> pair = a1.getAsOneFrac();

			return new RationalNumber(one*pair.getValue(), pair.getKey());
		}
		catch (NaturalNumberException | MathException e)
		{
			// 
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public RationalNumber sumNeutral() throws MathException
	{
		return RationalNumber.zero;
	}

	@Override
	public RationalNumber multiplyNeutral()
	{
		return RationalNumber.one;
	}
	
	public int hashCode()
	{
		return 2;
	}
	
	public boolean equals(Object other)
	{		
	    return (other instanceof RationalField);
	}
}