package someMath;

import someMath.exceptions.MathException;

public class ComplexNumber<O>
{

	private final O real;
	private final O imaginary;
	private final Field<O> k;
	
	public ComplexNumber(O real, O imaginary) throws MathException
	{

		if(real==null||imaginary==null) throw new MathException("Null value in Constructor not allowed.");
		this.k = (Field<O>) MapOfFields.getField(real.getClass());
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