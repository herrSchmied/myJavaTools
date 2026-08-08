package someMath;

import someMath.exceptions.MathException;

public interface VectorSpace<K, V>
{

	public V scalarMultiplication(K k, V v) throws MathException;
	public V vectorAddition(V v1, V v2) throws MathException;
	
	public V negate(V v1) throws MathException;
	public V zero(int l) throws MathException;
}
