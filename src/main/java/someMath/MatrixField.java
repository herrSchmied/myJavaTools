package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class MatrixField extends Operations<Matrix<Double>>
{

	private final int rows;
	private final int columns;
	
	private final DoubleField underField = new DoubleField();
	private Matrix<Double> neutrumOfMatrixAddition;
	private Matrix<Double> neutrumOfMatrixMultiplication;

	private Integer minMatrixAddition = 2;
	private Integer maxMatrixAddition = 1000;

	private final Set<Operation<Matrix<Double>>> setOfOperations = new HashSet<>();

	private Operation<Matrix<Double>> addOpp;

	private Function<List<Matrix<Double>>, Matrix<Double>> addition;

	public MatrixField(Set<Operation<Matrix<Double>>> set, int rows, int columns) throws MathException
	{
		
		super(set);
		
		if(rows<2&&columns<2)throw new MathException("Matrix has not enough columns and rows.");
		
		this.rows = rows;
		this.columns = columns;

		addition = (list)-> 
		{
			
			Double neutrum = underField.neutrumAddition;
			List<Double> neutrals = new ArrayList<>();
			for(int n=0;n<rows*columns;n++)neutrals.add(neutrum);
			
			Matrix<Double> sum;
			try
			{
				sum = new Matrix<>(rows,neutrals);
			}
			catch(MathException e)
			{
				e.printStackTrace();
				throw new RuntimeException();//Remember: Is this optimal?
			}
			
			List<Double> valueList = new ArrayList<>();
			
			for(Matrix<Double> summand: list)
			{
				BiConsumer<Point, Double> bic = (p,v)->
				{
					int col = p.x;
					int row = p.y;
				
					int n = col*row+col;

					Double d = summand.getValue(col, row)+v;
					valueList.set(n, d);
				};
			
				sum.walkThrouMatrix(bic);
			};
			
			try
			{
				Matrix<Double> output = new Matrix<>(rows, valueList);
				return output;
			}
			catch (MathException e)
			{
				e.printStackTrace();
				throw new RuntimeException();//Remember: Is this optimal?
			}
		};
		
		List<Double> neutrals = new ArrayList<>();
		int l = columns*rows;
		for(int n=0;n<l;n++)neutrals.add(underField.getNeutrumOfOperation(Operations.add));
		neutrumOfMatrixAddition = new Matrix<>(rows, neutrals);
		
		addOpp = new Operation(Operations.add, neutrumOfMatrixAddition, minMatrixAddition,
				maxMatrixAddition, addition);

		setOfOperations.add(addOpp);
	}
	
}
