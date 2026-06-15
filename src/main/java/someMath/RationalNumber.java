package someMath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import someMath.exceptions.MathException;



@SuppressWarnings("serial")
public class RationalNumber extends Number implements Cloneable, Serializable
{
	
//	public static final RationalNumber zero = 
//			new RationalNumber(true, NaturalNumber.zero, NaturalNumber.zero, NaturalNumber.one);
//	public static final RationalNumber one = 
//			new RationalNumber(true, NaturalNumber.one, NaturalNumber.zero, NaturalNumber.one);

	final boolean sign;

	final NaturalNumber integerPart;
	final NaturalNumber numerator;
	final NaturalNumber denominator;

	public RationalNumber(boolean sign, NaturalNumber integerPart, NaturalNumber numerator, NaturalNumber denominator) throws NaturalNumberException, MathException
	{

		if(denominator.equals(NaturalNumber.zero))throw new MathException("Denominator can't be Zero.");

		this.sign = sign;
		
		if(numerator.equals(NaturalNumber.zero))
		{
			this.integerPart = integerPart;
			this.numerator = numerator;
			this.denominator = NaturalNumber.one;
		}
		else
		{

			List<NaturalNumber> list =shorten(numerator.intValue(), denominator.intValue());
			this.integerPart = integerPart.add(list.get(0));
			this.numerator = list.get(1);
			
			if(this.numerator==NaturalNumber.zero)this.denominator = NaturalNumber.one;
			else this.denominator = list.get(2);
		}
	}

	public List<NaturalNumber> shorten(int numerator, int denominator) throws NaturalNumberException
	{
		
		List<NaturalNumber> output = new ArrayList<>();

		int grComDiv = SmallTools.gcd(numerator, denominator);

		int newNumerator = numerator/grComDiv;

		int newDenominator = denominator/grComDiv;

		int cnt = 0;
		int numerator2 = newNumerator;
			
		while(numerator2>=newDenominator)
		{
			cnt++;
			numerator2 = numerator2-newDenominator;
		}
			
		output.add(new NaturalNumber(cnt));
		output.add(new NaturalNumber(numerator2));
		output.add(new NaturalNumber(newDenominator));
			
		return output;
	}

	public NaturalNumber getIntegerPart() {
		return integerPart;
	}

	public NaturalNumber getNumerator() {
		return numerator;
	}

	public NaturalNumber getDenominator() {
		return denominator;
	}

	public boolean getSign()
	{
		return sign;
	}

	public int hashCode()
	{
		return Objects.hash(integerPart, numerator, denominator);
	}

	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		
	    if (!(obj instanceof RationalNumber)) return false;
	    
	    RationalNumber other = (RationalNumber)obj;
	    
	    if(!(other.getSign()==sign))return false;//Plus Zero is not equal to minus Zero???
	    if(!(other.getIntegerPart().equals(integerPart)))return false;
	    if(!(other.getNumerator().equals(numerator)))return false;
	    if(!(other.getDenominator().equals(denominator)))return false;


	    return true;

	}
	
	public String toString()
	{

		String s;
		
		if(!numerator.equals(NaturalNumber.zero))
		{
		s = integerPart.intValue()  
			+ "(" + numerator.intValue()
			+ "/"
			+ denominator.intValue()+ ")";
		}
		else
		{
			s = integerPart.intValue() + "";
		}
		
		if(!sign)s = "-" + s;
			
		return s;
	}

	public RationalNumber clone()
	{
		RationalNumber rn;

		try
		{
			rn = new RationalNumber(this.sign, this.integerPart, this.numerator, this.denominator);
			return rn;
		}
		catch(NaturalNumberException | MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Should not happen");
		}		
	}

	@Override
	public double doubleValue()
	{
		double value = integerPart.doubleValue() + (numerator.doubleValue()/denominator.doubleValue());
		return value;
	}

	@Override
	public float floatValue()
	{
		float value = (float)doubleValue();
		return value;
	}

	@Override
	public int intValue()
	{

		int value = integerPart.intValue();
		double frac = (numerator.doubleValue()/denominator.doubleValue());
		if(frac>=0.5) value++;

		return value;
	}

	@Override
	public long longValue()
	{
		
		long value = integerPart.longValue();
		double frac = (numerator.doubleValue()/denominator.doubleValue());
		if(frac>=0.5) value++;

		return value;
	}
}