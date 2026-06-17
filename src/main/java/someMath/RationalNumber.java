package someMath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.util.Pair;
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

			List<NaturalNumber> list =shorten(numerator, denominator);
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

			List<NaturalNumber> list = shorten(numerator, denominator);
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
		
		boolean numSmallerZero = (intNumerator<0);
		boolean denomSmallerZero = (intDenominator<0);
		boolean numBiggerZero = (intNumerator>0);
		boolean denomBiggerZero = (intDenominator>0);
		boolean numEqualZero = (intNumerator==0);
		boolean fracSign = (numSmallerZero&&denomSmallerZero)||(numBiggerZero&&denomBiggerZero)||(numEqualZero);
		boolean intSign = intIntegerPart>=0;

		if(intNumerator==0)
		{
			this.sign = intIntegerPart>=0;
			this.integerPart = new NaturalNumber(Math.abs(intIntegerPart));
			this.numerator = NaturalNumber.zero;
			this.denominator = NaturalNumber.one;
			return;
		}
		
		if(intIntegerPart==0)
		{
			
			this.sign=fracSign;
			NaturalNumber nnNum = new NaturalNumber(Math.abs(intNumerator));
			NaturalNumber nnDenom = new NaturalNumber(Math.abs(intDenominator));
			List<NaturalNumber> list = shorten(nnNum, nnDenom);
			this.integerPart = list.get(0);
			this.numerator = list.get(1);
			this.denominator = list.get(2);
			return;
		}


		//Bring it in certain format make the Numerator
		//always positive without changing the value;
		intNumerator = Math.abs(intNumerator);

		if(!fracSign)intDenominator = -Math.abs(intDenominator);
		else intDenominator = Math.abs(intDenominator);
		
		int newNum = intIntegerPart*intDenominator+intNumerator;
		int newDenom = intDenominator;
		
		boolean sameSign = (intSign==fracSign);
		boolean bothSmallerZero = ((!intSign)&&(!fracSign));
		
		this.sign = (newNum>0)||(!(sameSign&&bothSmallerZero));
		NaturalNumber nnNum = new NaturalNumber(Math.abs(newNum));
		NaturalNumber nnDenom = new NaturalNumber(Math.abs(newDenom));
		List<NaturalNumber> list = shorten(nnNum, nnDenom);
		this.integerPart = list.get(0);
		this.numerator = list.get(1);
		this.denominator = list.get(2);
		return;
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

			boolean signNumerator = (intNumerator>=0);
			boolean signDenominator = (intDenominator>=0);

			this.sign = (signNumerator==signDenominator);

			int intNumerator2 = Math.abs(intNumerator);

			int intDenominator2 = Math.abs(intDenominator);

			NaturalNumber nnNum = new NaturalNumber(Math.abs(intNumerator2));
			NaturalNumber nnDenom = new NaturalNumber(Math.abs(intDenominator2));
			List<NaturalNumber> list = shorten(nnNum, nnDenom);

			this.integerPart = list.get(0);
			this.numerator = list.get(1);
			
			if(this.numerator==NaturalNumber.zero)this.denominator = NaturalNumber.one;
			else this.denominator = list.get(2);
		}
	}
	
	public RationalNumber(int integerPart) throws NaturalNumberException
	{
		this.sign = integerPart>=0;
		this.integerPart = new NaturalNumber(Math.abs(integerPart));
		this.numerator = NaturalNumber.zero;
		this.denominator = NaturalNumber.one;
	}

	public List<NaturalNumber> shorten(NaturalNumber numerator, NaturalNumber denominator) throws NaturalNumberException
	{
		
		List<NaturalNumber> output = new ArrayList<>();

		int intNum = numerator.intValue();
		int intDenom = denominator.intValue();

		int grComDiv = SmallTools.gcd(intNum, intDenom);

		int newNumerator = intNum/grComDiv;
		
		int newDenominator = intDenom/grComDiv;

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
			

			Pair<Integer, Integer> pNegative = negativeOne.getAsOneFrac();
			Pair<Integer, Integer> pPositive = positiveOne.getAsOneFrac();

			int newDenominator = pNegative.getValue()*pPositive.getValue();
			
			int negativePairNum = pNegative.getKey()*pPositive.getValue();
			int positivePairNum = pPositive.getKey()*pNegative.getValue();
			
			int newNumerator = positivePairNum-negativePairNum;
			
			return new RationalNumber(newNumerator, newDenominator);
		}
	}
	
	public Pair<Integer, Integer> getAsOneFrac()//Without Sign.
	{

		int newNum = numerator.intValue()+integerPart.intValue()*denominator.intValue();
		
		return new Pair<Integer, Integer>(newNum, denominator.intValue());
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