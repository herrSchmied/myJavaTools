package someMath.AlgebraicStructures.Interfaces;

import someMath.exceptions.MathException;

public interface MultiplicativeGroup <A>
{

	public A multiply(A a1, A a2) throws MathException;
	
	public A inverse(A a1) throws MathException;

	public A one() throws MathException;
}
