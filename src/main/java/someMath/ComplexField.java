package someMath;

import someMath.exceptions.MathException;

public final class ComplexField<O> implements Field<ComplexNumber<O>>
{

	private final Field<O> k;

	public ComplexField(Field<O> k)
	{
		this.k = k;
	}

	@Override
	public ComplexNumber<O> add(ComplexNumber<O> a1, ComplexNumber<O> a2) throws MathException 
	{
		O newReal = k.add(a1.getRealPart(), a2.getRealPart());
		O newImaginary = k.add(a1.getImaginaryPart(), a2.getImaginaryPart());
		
		return new ComplexNumber<>(newReal, newImaginary);
	}

	@Override
	public ComplexNumber<O> multiply(ComplexNumber<O> a1, ComplexNumber<O> a2) throws MathException
	{
		O a1Real = a1.getRealPart();
		O a2Real = a2.getRealPart();
		O a1Img = a1.getImaginaryPart();
		O a2Img = a2.getImaginaryPart();
		O newReal = k.add(k.multiply(a1Real, a2Real), k.negate(k.multiply(a1Img, a2Img)));
		O newImaginary = k.add(k.multiply(a1Real, a2Img), k.multiply(a1Img, a2Real));
		
		return new ComplexNumber<>(newReal, newImaginary);
	}

	@Override
	public ComplexNumber<O> negate(ComplexNumber<O> a1) throws MathException
	{

		O newReal = k.negate(a1.getRealPart());
		O newImaginary = k.negate(a1.getImaginaryPart());
		
		return new ComplexNumber<>(newReal, newImaginary);
	}

	@Override
	public ComplexNumber<O> inverse(ComplexNumber<O> a1) throws MathException
	{

		O a1Real = a1.getRealPart();
		O a1Img = a1.getImaginaryPart();

		O amount = k.add(k.multiply(a1Real, a1Real), k.multiply(a1Img, a1Img));
		O amountInverse = k.inverse(amount);

		O newReal = k.multiply(a1Real, amountInverse);
		O newImaginary = k.multiply(k.negate(a1Img), amountInverse);

		return new ComplexNumber<>(newReal, newImaginary);
	}

	@Override
	public ComplexNumber<O> zero() throws MathException
	{

		O newReal = k.zero();
		O newImaginary = k.zero();
		
		return new ComplexNumber<>(newReal, newImaginary);
	}

	@Override
	public ComplexNumber<O> one() throws MathException
	{

		O newReal = k.one();
		O newImaginary = k.zero();
		
		return new ComplexNumber<>(newReal, newImaginary);
	}


	/*
	///Written with capital Letter because of Math Standards.
	//
	public double Arg()
	{
		return polarRepresentation().getImaginaryPart();
	}
	
	public double amount()
	{
		return Math.sqrt(real*real + imaginary*imaginary);
	}
	
	public ComplexNrDouble getConjugate()
	{
		return new ComplexNrDouble(real, -imaginary);
	}

	
		
	public ComplexNrDouble polarRepresentation()
	{
		
		if(real<0&&imaginary==0)return new ComplexNrDouble(Double.NaN, -real);
		
		double alpha = 0;
		
		if(this.amount()==0)new ComplexNrDouble(0, 0);
		
		alpha = Math.asin(this.imaginary/this.amount());
		
		double r = this.amount();
		
		if(this.real < 0)//Left half
		{
			if(this.imaginary > 0)alpha = Math.toRadians( 180-Math.toDegrees(alpha));//2nd Quadrant
			if(this.imaginary < 0)alpha = Math.toRadians(-180-Math.toDegrees(alpha));//3rd Quadrant
		}
		
		return new ComplexNrDouble(r, alpha);
	}
	
	//Main branch of the Log function. Capital L because of Math standards.
	public ComplexNrDouble Log()
	{
		
		Double loga = Math.log(amount());
		
		return new ComplexNrDouble(loga, Arg());
	}
	
	public ComplexNrDouble fromPolarToGaussEbene()
	{
		
		double angle = imaginary;
		
		double x = real*Math.cos(angle);
		double y = real*Math.sin(angle);
		
		return new ComplexNrDouble(x,y);
	}

	public ComplexNrDouble toThePowerOf(ComplexNrDouble exponent)
	{

		ComplexNrDouble loga = this.Log();

		ComplexNrDouble newExpo = loga.multiplyWith(exponent);
		
		double x = Math.exp(newExpo.real)*Math.cos(newExpo.imaginary);
		double y = Math.exp(newExpo.real)*Math.sin(newExpo.imaginary);
		
		return new ComplexNrDouble(x, y);
	}
	*/

}