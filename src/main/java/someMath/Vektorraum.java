package someMath;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;


import someMath.exceptions.MathException;

public class Vektorraum extends Operations<Vektor<Double>>
{

	private final Vektor<Double> neutrumVektorAddition;
	private final DoubleField df;

	private final BiFunction<Vektor<Double>, Vektor<Double>, Vektor<Double>> addition;
	private final BiFunction<Vektor<Double>, Vektor<Double>, Vektor<Double>> subtraction;

	public static final BiFunction<Double, Vektor<Double>, Vektor<Double>> scaling = (d,s)->
	{

		Vektor<Double> v2 = s.clone();
		int rows = v2.getRows();
		
		for(int row=0;row<rows;row++)
		{
			try
			{
				double v = s.getValue(row);
				v2.setValue(row, v*d);
			}
			catch(MathException me)
			{
				me.printStackTrace();
			}
		}
		
		return v2;
	};

	public static final BiFunction<Vektor<Double>, Vektor<Double>, Double> scalarProduct = (v1, v2)->
	{

		if(v1.getColumns()!=1)
			throw new RuntimeException("Factor 1 has not the Right nr. of Columns");
		if(v2.getColumns()!=1)
			throw new RuntimeException("Factor 2 has not the Right nr. of Columns");
		if(v1.getRows()!=v2.getRows())
			throw new RuntimeException("These two Vektors have different number of Rows(Dimension).");
		
		try
		{
			Matrix<Double> t = MatrixRing.transponent.apply(v1);
			Matrix<Double> erg = MatrixRing.multiplication.apply(t, v2);
		
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
		super(new HashSet<Operation<Vektor<Double>>>());
		df = new DoubleField();
		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n;m++)zeros.add(df.getNeutrumOfOperation(add));
		neutrumVektorAddition = new Vektor<Double>(zeros);
		
		addition = (v1, v2)-> 
		{
			
			Vektor<Double> sum = v1.clone();

			int rows = v1.getRows();
			
	
				for(int row=0;row<rows;row++)
				{
					try
					{
						Double d = v1.getValue(row);
						Double d2 = v2.getValue(row);
						sum.setValue(row, (d+d2));
					}
					catch(MathException me)
					{
						me.printStackTrace();
					}
				}
			

			return sum;
		};
		
		Operation<Vektor<Double>> op = new Operation<>(Operations.add, neutrumVektorAddition, addition);
		super.setOperation(op);
		
		subtraction = (v1, v2)-> 
		{

			Vektor<Double> mV2 = scaling.apply(-1.0, v2);
			
			return addition.apply(v1, mV2);
		};

		op = new Operation<>(Operations.minus, null, subtraction);
		super.setOperation(op);
	}
}
