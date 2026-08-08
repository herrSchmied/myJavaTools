package someMath;

import someMath.exceptions.MathException;

public class VecFieldTupStuff <K>
{
	
	private final Field<K> k;

	public VecFieldTupStuff(Field<K> k)
	{
		this.k = k;
	}

	public K scalarProduct(FieldTuple<K> v1, FieldTuple<K> v2) throws MathException
	{
		
		if(v1.getRows()!=v2.getRows())throw new MathException("Vectors belong too different Vectorspaces because of different amount of rows.");
		int l = v1.getRows();
		
		K sum = k.zero();
		
		for(int n=0;n<l;n++)
		{
			K product = k.multiply(v1.getValue(n), v2.getValue(n));
			
			sum = k.add(sum, product);
		}

		return sum;
	}

}