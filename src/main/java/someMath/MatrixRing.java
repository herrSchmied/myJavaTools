package someMath;


import java.util.Set;

import someMath.exceptions.MathException;

public class MatrixRing extends Operations<Matrix<Double>>
{

	public MatrixRing(Set<Operation<Matrix<Double>>> set) throws MathException
	{
		
		super(set);		
	}
	
}
