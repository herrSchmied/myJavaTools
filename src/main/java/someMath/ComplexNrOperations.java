package someMath;


import java.util.function.BiFunction;

import someMath.exceptions.MathException;


public class ComplexNrOperations
{

	public static BiFunction<ComplexNrDouble, ComplexNrDouble, ComplexNrDouble> addComplex = (z1, z2)->
	{
	
		Double r1 = z1.getRealPart();
		Double r2 = z2.getRealPart();
		Double sumR = r1+r2;
		
		Double im1 = z1.getImaginaryPart();
		Double im2 = z2.getImaginaryPart();
		Double sumIm = im1+im2;
		
		return new ComplexNrDouble(sumR, sumIm);
	};
	
	public static Operation<ComplexNrDouble> addition;

	public ComplexNrOperations()
	{
		try
		{
			addition = 	new Operation<>("C-Addition", new ComplexNrDouble(0,0), addComplex);
		}
		catch (MathException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}