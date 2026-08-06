package someMath;

public interface VectorSpace<K, V>
{

	public V scalarMultiplication(K k, V v);
	public V vectorAddition(V v1, V v2);
	
	public V negate(V v1);
	public V zero();
}
