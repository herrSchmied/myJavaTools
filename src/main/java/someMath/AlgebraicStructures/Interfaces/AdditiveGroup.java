package someMath.AlgebraicStructures.Interfaces;

import someMath.exceptions.MathException;

public interface AdditiveGroup<A>
{

	public A add(A a1, A a2) throws MathException;

	public A negate(A a1) throws MathException;
	
	public A zero() throws MathException;
}
