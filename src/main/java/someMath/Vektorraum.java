package someMath;


import java.util.ArrayList;

import java.util.List;



import someMath.exceptions.MathException;

public class Vektorraum<O> //TODO: Interface VectorSpace is coming!!!
{

	private final Field<O> k;
	private final Vektor<O> multiplyNeutrum;
	
	public Vektorraum(int n, Field<O> k) throws MathException
	{

		this.k = k;
		List<O> zeros = new ArrayList<>();
		for(int m=0;m<n;m++)zeros.add(k.sumNeutral());
		multiplyNeutrum = new Vektor<>(zeros);
	}


	public final Vektor<O> sum(Vektor<O> v1, Vektor<O> v2) throws MathException
	{
		
		
		if(v1.getRows()!=v2.getRows()) throw new MathException("Can't add those.");
		
		Vektor<O> sum = v1.clone();
		for(int r=0;r<v1.getRows();r++)
		{
			
			O s = k.add(v1.getValue(r), v2.getValue(r));
			sum = sum.setValue(r, s);
		}
		
		return sum;
	}
	
	public final Vektor<O> scaling(O scale, Vektor<O> toBeScaled) throws MathException
	{

		Vektor<O> output = toBeScaled.clone();

		int rows = toBeScaled.getRows();

		for(int r=0;r<rows;r++)
		{
			O oldValue = toBeScaled.getValue(r);
			O newValue = k.multiply(oldValue, scale);
			output = output.setValue(r, newValue);
		}

		return output;
	}

	public final O scalarProduct(Vektor<O> v1, Vektor<O> v2) throws MathException
	{

		if(v1.getRows()!=v2.getRows())
			throw new RuntimeException("These two Vektors have different number of Rows(Dimension).");
		
		MatrixRing<O> ring = new MatrixRing<>(2, k);

		try
		{
			Matrix<O> t = MatrixStuff.transpone(v1);
			Matrix<O> erg = ring.multiply(t, v2);
		
			return erg.getValue(0, 0);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't multiply those 'Vektors'");
		}
	}
}
