package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class Vektorraum extends Operations<Vektor<Double>>
{
	private final int minMatrixAddition = 2;
	private final int maxMatrixAddition = 50;
	private final Matrix<Double>neutrumMatrixAddition;

	Function<List<Vektor<Double>>, Vektor<Double>> addition = (list)-> 
	{
		
		Vektor<Double> sum;

		int rows = list.get(0).getRows();
		
		try
		{

			Double neutrum = DoubleField.neutrumAddition;
			List<Double>neutrals = new ArrayList<>();
			for(int n=0;n<(rows);n++)neutrals.add(neutrum);
			
			sum = new Vektor<>(neutrals);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException();//Remember: Is this optimal?
		}
		
		for(Vektor<Double> summand: list)
		{			
			for(int row=0;row<rows;row++)
			{
				Double d = summand.getValue(row);
				Double d2 = sum.getValue(row);
				sum.setValue(row, (d+d2));
			}
		};

		return sum;
	};

	public static final BiFunction<Double, Vektor<Double>, Vektor<Double>> scaling = (d,s)->
	{

		Vektor<Double> v2 = s.clone();
		
		BiConsumer<Point, Double> bic = (p, v)->
		{
			v2.setValue(p.x, p.y, v*d);
		};

		v2.walkThrouMatrix(bic);
		
		return v2;
	};
	
	public Vektorraum(int n) throws MathException
	{
		super(new HashSet<Operation<Vektor<Double>>>());
		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n;m++)zeros.add(DoubleField.neutrumAddition);
		neutrumMatrixAddition = new Vektor<>(zeros);
	}

}
