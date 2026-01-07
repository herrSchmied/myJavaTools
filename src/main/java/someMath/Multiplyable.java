package someMath;

import someMath.exceptions.CollectionException;
import someMath.exceptions.DivisionByZeroException;
import someMath.exceptions.NaturalNumberException;


/*
 * Any Class implementing this Interface
 * should make the Generic 'E' identical 
 * to its own type.
*/
public abstract class Multiplyable<E> 
{
	
	public final E one;

	public Multiplyable(E one)
	{
		this.one = one;
	}
	
	public abstract boolean hasNeutralOne();
	
	public abstract E multiplyWith(E e) throws NaturalNumberException, CloneNotSupportedException, DivisionByZeroException, CollectionException;
	
	public abstract E getNeutralOne() throws NaturalNumberException;
}
