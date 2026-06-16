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
	
	public RationalNumber(boolean sign, NaturalNumber numerator, NaturalNumber denominator) throws NaturalNumberException, MathException
	{

		if(denominator.equals(NaturalNumber.zero))throw new MathException("Denominator can't be Zero.");

		this.sign = sign;

		if(numerator.equals(NaturalNumber.zero))
		{

			this.integerPart = NaturalNumber.zero;
			this.numerator = numerator;
			this.denominator = NaturalNumber.one;
		}
		else
		{

			List<NaturalNumber> list = shorten(numerator.intValue(), denominator.intValue());
			this.integerPart = list.get(0);
			this.numerator = list.get(1);
			
			if(this.numerator==NaturalNumber.zero)this.denominator = NaturalNumber.one;
			else this.denominator = list.get(2);
		}
	}
	
	public RationalNumber(boolean sign, NaturalNumber intPart)
	{
		this.sign = sign;
		this.integerPart = intPart;
		this.numerator = NaturalNumber.zero;
		this.denominator = NaturalNumber.one;
	}
	
	public RationalNumber(int intIntegerPart, int intNumerator, int intDenominator) throws MathException, NaturalNumberException
	{

		if(intDenominator==0)throw new MathException("Denominator can't be Zero.");
		
		boolean intSign = intIntegerPart>0;
		
		RationalNumber intOne = new RationalNumber(intSign, new NaturalNumber(intIntegerPart));
		RationalNumber fracOne = new RationalNumber(intNumerator, intDenominator);
			
		RationalNumber sum = intOne.add(fracOne);
			
		this.sign = sum.sign;
		this.integerPart = sum.integerPart;
		this.numerator = sum.numerator;
		this.denominator = sum.denominator;
	}

	public RationalNumber(int intNumerator, int intDenominator) throws MathException, NaturalNumberException
	{
		if(intDenominator==0)throw new MathException("Denominator can't be Zero.");
		
		if(intNumerator==0)
		{
			this.sign = true;
			this.integerPart = NaturalNumber.zero;
			this.numerator = NaturalNumber.zero;
			this.denominator = NaturalNumber.one;
		}
		else
		{
			boolean signNumerator = (intNumerator>0);
			boolean signDenominator = (intDenominator>0);
			
			this.sign = (signNumerator==signDenominator);
			
			int intNumerator2 = Math.abs(intNumerator);
			
			int intDenominator2 = Math.abs(intDenominator);

			List<NaturalNumber> list = shorten(intNumerator2, intDenominator2);
			
			this.integerPart = list.get(0);
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
	
	public RationalNumber add(RationalNumber rn) throws NaturalNumberException, MathException
	{

		if(this.sign==rn.sign)
		{

			NaturalNumber integerPart1 = this.getIntegerPart().add(rn.integerPart);
			
			NaturalNumber newNumerator = numerator
											.multiply(rn.denominator)
											.add(rn.numerator.multiply(denominator));
			
			NaturalNumber newDenominator = denominator.multiply(rn.denominator);

			RationalNumber sum1 = new RationalNumber(this.sign, newNumerator, newDenominator);
			
			NaturalNumber integerPart2 = integerPart1.add(sum1.integerPart);

			RationalNumber sum2 = new RationalNumber
					(this.sign, integerPart2, sum1.numerator, sum1.denominator);
			
			return sum2;
			
		}
		else
		{

			if(this.getAmount().equals(rn.getAmount()))
				return new RationalNumber(true, NaturalNumber.zero, NaturalNumber.one);
			
			RationalNumber negativeOne;
			RationalNumber positiveOne;
			if(!this.sign)
			{
				negativeOne = this.clone();
				positiveOne = rn.clone();
			}
			else
			{
				negativeOne = rn.clone();
				positiveOne = this.clone();
			}
			
			boolean theLargerAmountIsNegative = negativeOne.getAmount().isLargerThan(positiveOne);
			boolean newSign = !theLargerAmountIsNegative;
			
			int newDenominator = negativeOne.denominator.intValue()*positiveOne.denominator.intValue();
			int newIntPart = negativeOne.integerPart.intValue()-positiveOne.integerPart.intValue();
			
			boolean negativeOneGotLargerFracAmount = negativeOne
					.getFrac()
					.getAmount()
					.isLargerThan(rn.getFrac());
			
			int newNumerator = -negativeOne.numerator.intValue()*positiveOne.denominator.intValue()
					+negativeOne.denominator.intValue()*positiveOne.numerator.intValue();


			if(theLargerAmountIsNegative)
			{

				if(negativeOneGotLargerFracAmount)
				{
					
					NaturalNumber nnInt = new NaturalNumber(newIntPart);
					NaturalNumber nnNum = new NaturalNumber(newNumerator);
					NaturalNumber nnDenom = new NaturalNumber(newDenominator);
					
					return new RationalNumber(newSign, nnInt, nnNum, nnDenom);
				}
				else
				{

					newNumerator = newDenominator+newNumerator;
					
					NaturalNumber nnInt = new NaturalNumber(newIntPart);
					NaturalNumber nnNum = new NaturalNumber(newNumerator);
					NaturalNumber nnDenom = new NaturalNumber(newDenominator);

					return new RationalNumber(newSign, nnInt, nnNum, nnDenom);
				}
			}
			else
			{

				newIntPart = -newIntPart;
				if(negativeOneGotLargerFracAmount)
				{

					newNumerator = newDenominator+newNumerator;

					NaturalNumber nnInt = new NaturalNumber(newIntPart);
					NaturalNumber nnNum = new NaturalNumber(newNumerator);
					NaturalNumber nnDenom = new NaturalNumber(newDenominator);
					
					return new RationalNumber(newSign, nnInt, nnNum, nnDenom);

				}
				else
				{
			
					NaturalNumber nnInt = new NaturalNumber(newIntPart);
					NaturalNumber nnNum = new NaturalNumber(newNumerator);
					NaturalNumber nnDenom = new NaturalNumber(newDenominator);
			
					return new RationalNumber(newSign, nnInt, nnNum, nnDenom);

				}
			}
		}
	}
	
	public RationalNumber getFrac() throws NaturalNumberException, MathException
	{
		return new RationalNumber(this.sign, numerator, denominator);
	}
	
	public RationalNumber getAmount() throws NaturalNumberException, MathException
	{
		return new RationalNumber(true, integerPart, numerator, denominator);
	}
	
	public boolean isLargerThan(RationalNumber rn) throws NaturalNumberException, MathException
	{
		
		if(this.equals(rn))return false;
		
		if(sign&&!(rn.getSign()))return true;
		if(!(sign)&&rn.getSign())return false;
		
		if(sign&&rn.getSign())
		{
			if(integerPart.isGreaterThen(rn.getIntegerPart()))return true;
			if(integerPart.isSmallerThen(rn.getIntegerPart()))return false;
			
			NaturalNumber newNum1 = numerator.multiply(rn.getDenominator());
			NaturalNumber newNum2 = rn.getNumerator().multiply(denominator);
			
			return (newNum1.isGreaterThen(newNum2));
		}
		
		if(!(sign)&&!(rn.getSign()))
		{
			if(integerPart.isGreaterThen(rn.getIntegerPart()))return false;
			if(integerPart.isSmallerThen(rn.getIntegerPart()))return true;
			
			NaturalNumber newNum1 = numerator.multiply(rn.getDenominator());
			NaturalNumber newNum2 = rn.getNumerator().multiply(denominator);
			
			return (newNum1.isSmallerThen(newNum2));
		}

		
		throw new MathException("Should not happen.");
	}
	
	public boolean isSmallerThan(RationalNumber rn) throws NaturalNumberException, MathException
	{
		if(this.equals(rn))return false;
		
		return !this.isLargerThan(rn);
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
			s = "(" + numerator.intValue()
						+ "/"
						+ denominator.intValue()+ ")";

			if(!integerPart.equals(NaturalNumber.zero))
			{
				s = integerPart.intValue()+s;
			}
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