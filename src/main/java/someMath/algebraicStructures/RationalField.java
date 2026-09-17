package someMath.algebraicStructures;

import java.util.Objects;

import javafx.util.Pair;
import someMath.AlgebraicStructures.Interfaces.Field;
import someMath.algebraicStructures.Storage.RationalNumber;
import someMath.exceptions.*;


public class RationalField implements Field<RationalNumber, Integer>
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
	public RationalNumber negate(RationalNumber a1) throws MathException
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
	public RationalNumber inverse(RationalNumber a1) throws MathException
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
	public RationalNumber zero() throws MathException
	{
		return RationalNumber.zero;
	}

	@Override
	public RationalNumber one()
	{
		return RationalNumber.one;
	}
	
	public int hashCode()
	{
		return Objects.hash(RationalNumber.zero, RationalNumber.one);
	}
	
	public boolean equals(Object other)
	{		
	    return (other instanceof RationalField);
	}

	@Override
	public boolean isAmbiguous()
	{
		return false;
	}

	@Override
	public Integer distinguish()
	{
		return null;
	}

	@Override
	public Integer distinguisher() {
		// TODO Auto-generated method stub
		return null;
	}
}