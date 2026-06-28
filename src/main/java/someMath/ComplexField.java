package someMath;


import someMath.exceptions.MathException;



public class ComplexField<O, T extends AlgebraicField<O>, A extends ComplexNumber<O, T>> implements AlgebraicField<A>
{

	public ComplexField()
	{
		
	}

	@Override
	public A add(A a1, A a2) throws MathException 
	{
		
		T t = a1.getDummy();
		O newReal = t.add(a1.getRealPart(), a2.getRealPart());
		O newImaginary = t.add(a1.getImaginaryPart(), a2.getImaginaryPart());
		
		return (A) new ComplexNumber(newReal, newImaginary, t);
	}

	@Override
	public A multiply(A a1, A a2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A sumInverse(A a1) throws MathException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A multiplyInverse(A a1) throws MathException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A sumNeutral() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public A multiplyNeutral() {
		// TODO Auto-generated method stub
		return null;
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