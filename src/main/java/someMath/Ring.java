package someMath;

import someMath.exceptions.MathException;

public interface Ring<R>
{
	
	public R add(R r1, R r2);
	public R multiply(R r1, R r2);

	public R sumInverse(R r1) throws MathException;
	public R sumNeutral() throws MathException;
}
