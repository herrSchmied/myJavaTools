package someMath;

import javafx.util.Pair;
import someMath.exceptions.MathException;

public class RationalField implements Field<RationalNumber>
{

	@Override
	public RationalNumber add(RationalNumber a1, RationalNumber a2) throws MathException, NaturalNumberException
	{
		return a1.add(a2);
	}

	@Override
	public RationalNumber multiply(RationalNumber a1, RationalNumber a2) throws MathException, NaturalNumberException
	{
		return a1.multiply(a2);
	}

	@Override
	public RationalNumber sumInverse(RationalNumber a1) throws NaturalNumberException, MathException
	{
		
		if(a1.equals(new RationalNumber(0,1)))return new RationalNumber(0, 1); 
		return new RationalNumber(!(a1.sign), a1.getIntegerPart(), a1.getNumerator(), a1.getDenominator());
	}

	@Override
	public RationalNumber multiplyInverse(RationalNumber a1) throws MathException, NaturalNumberException
	{

		int one = 1;
		if(!a1.sign)one = -1;
		Pair<Integer, Integer> pair = a1.getAsOneFrac();

		return new RationalNumber(one*pair.getValue(), pair.getKey());
	}

	@Override
	public RationalNumber sumNeutral() throws MathException, NaturalNumberException
	{
		return RationalNumber.zero;
	}

	@Override
	public RationalNumber multiplyNeutral() throws NaturalNumberException
	{
		return RationalNumber.one;
	}
}