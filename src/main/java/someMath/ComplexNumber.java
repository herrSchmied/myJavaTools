package someMath;

import someMath.exceptions.MathException;

public class ComplexNumber<O>
{

	private final O real;
	private final O imaginary;
	
	public ComplexNumber(O real, O imaginary) throws MathException
	{

		if(real==null||imaginary==null) throw new MathException("Null value in Constructor not allowed.");
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public O getRealPart()
	{
		return real;
	}
	
	public O getImaginaryPart()
	{
		return imaginary;
	}
	
	public String toString()
	{
		return "" + real.toString() + " + i*" + imaginary.toString();
	}
}