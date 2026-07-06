package someMath;

import someMath.exceptions.MathException;

public interface Field<A>
{

	public A add(A a1, A a2) throws MathException;
	public A multiply(A a1, A a2) throws MathException;
	
	public A sumInverse(A a1) throws MathException;
	public A multiplyInverse(A a1) throws MathException;
	
	public A sumNeutral();
	
	public A multiplyNeutral();
}
