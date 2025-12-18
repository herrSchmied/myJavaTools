package someMath;


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


import someMath.exceptions.MathException;

import static someMath.LinearEquationSolver.*;

public class MatrixRing extends Operations<Matrix<Double>>
{

	private final Matrix<Double> neutrumMatrixAddition;
	
	private final Matrix<Double> neutrumMatrixMultiplication;

	public static final BiFunction<Matrix<Double>, Matrix<Double>, Matrix<Double>> addition = (m1, m2)-> 
	{
		
		if(!(m1.getRows()==m2.getRows()))throw new RuntimeException("Can't add those Matrizes.");
		if(!(m1.getColumns()==m2.getColumns()))throw new RuntimeException("Can't add those Matrizes.");
		
		int rows = m1.getRows();
		int cols = m1.getColumns();
		
		Matrix<Double> sum = m1.clone();
		
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				try
				{

					Double d = m1.getValue(col, row);
					Double d2 = m2.getValue(col, row);
					sum.setValue(col, row, (d+d2));
				}
				catch(MathException me)
				{
					me.printStackTrace();
				}
			}
		}

		return sum;
	};
	
	public static final BiFunction<Matrix<Double>, Matrix<Double>, Matrix<Double>> multiplication = (m1, m2)-> 
	{

		if(!(m1.getColumns()==m2.getRows()))throw new RuntimeException("Can't multiply those Matrizes.");
		
		int rows = m1.getRows();
		int cols = m2.getColumns();
		List<Double> valueList = new ArrayList<>();
		
		for(int n=0;n<rows*cols;n++)valueList.add(0.0);
		
		Matrix<Double> product;
		
		try
		{
			product = new Matrix<Double>(rows, valueList);
		}
		catch(MathException mExcep)
		{
			throw new RuntimeException("Can't !!");
		}

		int s = m1.getColumns();//equal to m2.getRows();
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				
				try
				{
					double sum = 0.0;
					for(int n=0;n<s;n++)
					{
						sum = sum + m1.getValue(n, row)*m2.getValue(col, n);
					}
				
					product.setValue(col, row, sum);
				}
				catch(MathException me)
				{
					me.printStackTrace();
				}
			}
		}
		
		return product;
	};
	
	public static final Function<Matrix<Double>, Matrix<Double>> transponent = (matrix)->
	{
		

		int cols = matrix.getColumns();
		int rows = matrix.getRows();
	
		Matrix<Double> transponed;
		try
		{
			//Remember columns and rows get switched!!
			transponed = new Matrix<Double>(rows, cols, 0.0);
			for(int col=0;col<cols;col++)
			{
				for(int row=0;row<rows;row++)
				{
					Double d = matrix.getValue(col, row);
					transponed.setValue(row, col, d);
				}
			}
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't initialize matrix transponend");
		}
		
		return transponed;
	};

	public static final BiFunction<Double, Matrix<Double>, Matrix<Double>> scaling = (d, m)->
	{

		Matrix<Double> m2 = m.clone();
		
		BiConsumer<Point, Double> bic = (p, v)->
		{
			try
			{
				m2.setValue(p.x, p.y, v*d);
			}
			catch (MathException e)
			{
				e.printStackTrace();
			}
		};

		try
		{
			m2.walkThrouMatrix(bic);
			return m2;
		}
		catch(MathException me)
		{
			throw new RuntimeException("Couldn't scale.");
		}
	};

	public static final Function<Matrix<Double>, Matrix<Double>> invert = (matrix)->
	{

		if(!matrix.isQuadratic())
		{
			System.out.println("Matrix is not quadratic so not invertable.");
			return null;
		}

		try
		{
			Double determinant = MatrixStuff.determinant(new DoubleField(), matrix);
			if(determinant.equals(0.0))
			{
				System.out.println("Matrix determinant is Zero so not invertable.");
				return null;
			}
		
			int columns = matrix.getColumns();
			int rows = columns;
		
			Matrix<Double> coefficientMatrix = matrix.clone();
			coefficientMatrix = MatrixRing.transponent.apply(coefficientMatrix);
			Matrix<Double> output = new Matrix<>(columns, rows, 0.0);
			for(int n=0;n<rows;n++)
			{

				Vektor<Double> rowResults = new Vektor<>(rows, 0.0);
				rowResults = rowResults.setValue(n, 1.0);

				Matrix<Double> extendedCoefficientMatrix = 
					coefficientMatrix.glueColumnToThisOnTheRight(rowResults);
				Vektor<Object> result = solve(extendedCoefficientMatrix);
				Vektor<Double> doubleResult = convertSolutionVektorToExampleVektor(result);
				output = output.setRow(doubleResult, n);				
			}
			
			return output;
		}
		catch(MathException mex)
		{
			mex.printStackTrace();
		}

		throw new RuntimeException("Should not happen!");
	};

	public MatrixRing(int n) throws MathException
	{
		
		super(new HashSet<Operation<Matrix<Double>>>());
		DoubleField df = new DoubleField();
		
		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n*n;m++)zeros.add(df.getNeutrumOfOperation(add));
		neutrumMatrixAddition = new Matrix<>(n, zeros);
		
		List<Double> diagonalMOne = new ArrayList<>();
		for(int x=0;x<n;x++)for(int y=0;y<n;y++)
		{
			if(x==y)diagonalMOne.add(df.getNeutrumOfOperation(multiply));
			else diagonalMOne.add(df.getNeutrumOfOperation(add));
		}
		neutrumMatrixMultiplication = new Matrix<>(n, diagonalMOne);
		
		@SuppressWarnings("static-access")
		Operation<Matrix<Double>> addOpp = new Operation<>(super.add, neutrumMatrixAddition,
				 addition);

		@SuppressWarnings("static-access")
		Operation<Matrix<Double>> multiply = new Operation<>(super.multiply, neutrumMatrixMultiplication, multiplication);
		
		super.setOperation(addOpp);
		super.setOperation(multiply);
	}

	public Matrix<Double> getNeutrumMatrixAddition()
	{
		return neutrumMatrixAddition;
	}

	public Matrix<Double> getNeutrumMatrixMultiplication()
	{
		return neutrumMatrixMultiplication;
	}
}
