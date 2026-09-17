package someMath.algebraicStructures.operations;

import someMath.AlgebraicStructures.Interfaces.Field;
import someMath.algebraicStructures.Storage.FieldTuple;
import someMath.exceptions.MathException;

public class VecFieldTupStuff <K>
{
	
	private final Field<K, Integer> k;

	public VecFieldTupStuff(Field<K, Integer> k)
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