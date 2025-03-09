package someMath;

import java.util.List;
import java.util.function.Function;

import someMath.exceptions.MathException;


public class ComplexNrOperations
{

	public static Function<List<ComplexNrDouble>, ComplexNrDouble> addComplex = (list)->
	{
	
		ComplexNrDouble current = ComplexNrDouble.getZero();
		
		for(ComplexNrDouble z: list)
		{
			Double realZ = z.getRealPart();
			Double imgZ = z.getImaginaryPart();
			
			Double realCurrent = current.getRealPart();
			Double imgCurrent = current.getImaginaryPart();

			current = new ComplexNrDouble(realZ+realCurrent, imgZ+imgCurrent);
		}

		return current;
	};
	
	public static Operation<ComplexNrDouble> addition;

	public ComplexNrOperations()
	{
		try
		{
			addition = 	new Operation("C-Addition", new ComplexNrDouble(0,0),2, Integer.MAX_VALUE, addComplex);
		}
		catch (MathException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}