package someMath;

import java.util.Objects;



public class ComplexNrDouble
{

	private final static ComplexNrDouble zero = new ComplexNrDouble(0, 0);
	private final static ComplexNrDouble one = new ComplexNrDouble(1, 0);
	private final static ComplexNrDouble i = new ComplexNrDouble(0,1);
	
	private final double real;
	private final double imaginary;
	
	public ComplexNrDouble(double real, double imaginary)
	{
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public double getRealPart() {return real;}
	
	public double getImaginaryPart() {return imaginary;}

	public int hashCode()
	{
		return Objects.hash(real, imaginary);
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		
	    if (!(obj instanceof ComplexNrDouble)) return false;
	    
	    ComplexNrDouble other = (ComplexNrDouble)obj;
	    
	    return ((other.real== this.real)&&(other.imaginary==this.imaginary));
	}
	
	public static ComplexNrDouble getZero()
	{
		return zero;
	}
	
	public static ComplexNrDouble getOne()
	{
		return one;
	}
	
	public String toString()
	{
		
		if(imaginary<0) return real + " -i" + Math.abs(imaginary);
		
		return real + " + i" + imaginary;
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