package someMath;

import someMath.exceptions.MathException;

public class VectorSpaceForFieldTuples<K, V extends FieldTuple<K>> implements VectorSpace<K, V>
{

	private final Field<K> k;
	
	public VectorSpaceForFieldTuples(Field<K> k)
	{
		this.k = k;
	}
	
	@Override
	public V scalarMultiplication(K a, V v) throws MathException
	{
		int l = v.getRows();
		
		FieldTuple<K> newTuple = v.clone();
		
		for(int n=0;n<l;n++)
		{
			K oldValue = v.getValue(n);
			newTuple = newTuple.setValue(n, k.multiply(oldValue, a));
		}
		
		return (V) newTuple;
	}

	@Override
	public V vectorAddition(V v1, V v2) throws MathException
	{
		if(v1.getRows()!=v2.getRows())throw new MathException("Vectors belong too different Vectorspaces because of different amount of rows.");

		int l = v1.getRows();
		
		FieldTuple<K> v3 = new FieldTuple(l, k.one());
		
		for(int n=0;n<l;n++)
		{
			
			K sum = k.add(v1.getValue(n), v2.getValue(n));
			v3 = v3.setValue(n, sum);
		}

		return (V) v3;
	}

	@Override
	public V negate(V v1) throws MathException
	{
	
		int l = v1.getRows();
		
		FieldTuple<K> v2 = new FieldTuple(l, k.one());
		
		for(int n=0;n<l;n++)
		{
			
			K negative = k.negate(v1.getValue(n));
			v2 = v2.setValue(n, negative);
		}

		return (V) v2;
	}

	@Override
	public V zero(int l) throws MathException
	{

		FieldTuple<K> v2 = new FieldTuple(l, k.one());
		
		for(int n=0;n<l;n++)v2 = v2.setValue(n, k.zero());

		return (V) v2;
	}

}
