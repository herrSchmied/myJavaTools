package someMath;





public class ComplexField<O, T extends Field<O>, A extends ComplexNumber<O>> implements Field<A>
{

	private final Field<O> k;

	public ComplexField(T k)
	{
		this.k = k;
	}

	@SuppressWarnings("unchecked")
	@Override
	public A add(A a1, A a2) throws Exception 
	{
		O newReal = k.add(a1.getRealPart(), a2.getRealPart());
		O newImaginary = k.add(a1.getImaginaryPart(), a2.getImaginaryPart());
		
		return (A) new ComplexNumber<>(newReal, newImaginary);
	}

	@SuppressWarnings("unchecked")
	@Override
	public A multiply(A a1, A a2) throws Exception
	{
		O a1Real = a1.getRealPart();
		O a2Real = a2.getRealPart();
		O a1Img = a1.getImaginaryPart();
		O a2Img = a2.getImaginaryPart();
		O newReal = k.add(k.multiply(a1Real, a2Real), k.sumInverse(k.multiply(a1Img, a2Img)));
		O newImaginary = k.add(k.multiply(a1Real, a2Img), k.multiply(a1Img, a2Real));
		
		return (A) new ComplexNumber<>(newReal, newImaginary);
	}

	@SuppressWarnings("unchecked")
	@Override
	public A sumInverse(A a1) throws Exception
	{

		O newReal = k.sumInverse(a1.getRealPart());
		O newImaginary = k.sumInverse(a1.getImaginaryPart());
		
		return (A) new ComplexNumber<>(newReal, newImaginary);
	}

	@SuppressWarnings("unchecked")
	@Override
	public A multiplyInverse(A a1) throws Exception
	{

		O a1Real = a1.getRealPart();
		O a1Img = a1.getImaginaryPart();

		O amount = k.add(k.multiply(a1Real, a1Real), k.multiply(a1Img, a1Img));
		O amountInverse = k.multiplyInverse(amount);

		O newReal = k.multiply(a1Real, amountInverse);
		O newImaginary = k.multiply(k.sumInverse(a1Img), amountInverse);

		return (A) new ComplexNumber<>(newReal, newImaginary);
	}

	@SuppressWarnings("unchecked")
	@Override
	public A sumNeutral() throws Exception
	{

		O newReal = k.sumNeutral();
		O newImaginary = k.sumNeutral();
		
		return (A) new ComplexNumber<>(newReal, newImaginary);
	}

	@SuppressWarnings("unchecked")
	@Override
	public A multiplyNeutral() throws Exception
	{

		O newReal = k.multiplyNeutral();
		O newImaginary = k.sumNeutral();
		
		return (A) new ComplexNumber<>(newReal, newImaginary);
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