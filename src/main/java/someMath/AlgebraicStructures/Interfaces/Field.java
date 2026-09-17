package someMath.AlgebraicStructures.Interfaces;

import someMath.exceptions.MathException;

public interface Field<A, O> extends Ring<A, O>
{	
	public A inverse(A a1) throws MathException;
}
