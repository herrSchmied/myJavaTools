package someMath;

import someMath.exceptions.MathException;

public interface Ring<R>
{

	public R add(R r1, R r2) throws MathException;
	public R multiply(R r1, R r2) throws MathException;

	public R negate(R r1) throws MathException;
	
	public R zero() throws MathException;
	public R one() throws MathException;
}
