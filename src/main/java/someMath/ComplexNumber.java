package someMath;

import someMath.exceptions.MathException;

public class ComplexNumber<O, T extends AlgebraicField<O>>
{

	private final T dummy;
	private final O real;
	private final O imaginary;
	
	public ComplexNumber(O real, O imaginary, T dummy) throws MathException
	{
		if(dummy==null||real==null||imaginary==null) throw new MathException("Null value in Constructor not allowed.");
		this.dummy = dummy;
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
	
	public T getDummy()
	{
		return dummy;
	}
	
	public String toString()
	{
		return "" + real.toString() + " i*" + imaginary.toString();
	}
}