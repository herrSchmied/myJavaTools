package someMath;

import someMath.exceptions.MathException;

public interface Field<A> extends Ring<A>
{	
	public A inverse(A a1) throws MathException;
}
