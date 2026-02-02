package someMath;


import java.util.ArrayList;

import java.util.List;



import someMath.exceptions.MathException;

public class Vektorraum
{

	private final Vektor<Double> neutrumVektorAddition;


	public static final Vektor<Double> sum(Vektor<Double> v1, Vektor<Double> v2) throws MathException
	{
		
		if(v1.getRows()!=v2.getRows()) throw new MathException("Can't add those.");
		
		Vektor<Double> sum = v1.clone();
		for(int r=0;r<v1.getRows();r++)
		{
			
			Double s = v1.getValue(r)+v2.getValue(r);
			sum = sum.setValue(r, s);
		}
		
		return sum;
	}
	
	public static final Vektor<Double> scaling(Double scale, Vektor<Double> toBeScaled) throws MathException
	{

		Vektor<Double> output = toBeScaled.clone();
		int rows = toBeScaled.getRows();

		for(int r=0;r<rows;r++)
		{
			double oldValue = toBeScaled.getValue(r);
			double newValue = oldValue*scale;
			output = output.setValue(r, newValue);
		}

		return output;
	}

	public static final Double scalarProduct(Vektor<Double> v1, Vektor<Double> v2)
	{

		if(v1.getRows()!=v2.getRows())
			throw new RuntimeException("These two Vektors have different number of Rows(Dimension).");
		
		try
		{
			Matrix<Double> t = MatrixRing.transpone(v1);
			Matrix<Double> erg = MatrixRing.multiply(t, v2);
		
			return erg.getValue(0, 0);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't multiply those 'Vektors'");
		}
	};

	public Vektorraum(int n) throws MathException
	{

		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n;m++)zeros.add(0.0);//DoubleField Neutral of Addition!
		neutrumVektorAddition = new Vektor<Double>(zeros);
	}
}
