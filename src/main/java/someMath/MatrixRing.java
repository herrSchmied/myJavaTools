package someMath;


import java.awt.Point;
import java.util.ArrayList;

import java.util.List;

import java.util.function.BiConsumer;


import someMath.exceptions.MathException;



public class MatrixRing
{

	private final Matrix<Double> neutrumMatrixAddition;
	
	private final Matrix<Double> neutrumMatrixMultiplication;

	public static final Matrix<Double> sum(Matrix<Double> m1, Matrix<Double> m2)
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
					sum = sum.setValue(col, row, (d+d2));
				}
				catch(MathException me)
				{
					me.printStackTrace();
				}
			}
		}

		return sum;
	}
	
	public static final Matrix<Double> multiply(Matrix<Double> m1, Matrix<Double> m2)
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

		for(int r=0;r<rows;r++)
		{
			for(int c=0;c<cols;c++)
			{

				try
				{
					double sum = 0.0;
					for(int n=0;n<s;n++)
					{
						sum = sum + m1.getValue(n, r)*m2.getValue(c, n);
					}
				
					product = product.setValue(c, r, sum);
				}
				catch(MathException me)
				{
					me.printStackTrace();
				}
			}
		}
		
		return product;
	}
	
	public static final Matrix<Double> transpone(Matrix<Double> matrix)
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
					transponed = transponed.setValue(row, col, d);
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

	public static final Matrix<Double> scale(Double d, Matrix<Double> m)
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

	public static final Matrix<Double> invert(Matrix<Double> matrix) throws MathException
	{

		LinearEquationSolver les = new LinearEquationSolver();
	
		if(!matrix.isQuadratic())
		{
			System.out.println("Matrix is not quadratic so not invertable.");
			return null;
		}

		System.out.println("Inverting Matrix.");
		
		Double determinant = MatrixStuff.determinant(new DoubleField(), matrix);
		if(determinant.equals(0.0))
		{
			System.out.println("Matrix determinant is Zero so not invertable.");
			return null;
		}
		
		int columns = matrix.getColumns();
		int rows = columns;
		
		Matrix<Double> coefficientMatrix = matrix.clone();
		coefficientMatrix = transpone(coefficientMatrix);
		Matrix<Double> output = new Matrix<>(columns, rows, 0.0);
		for(int n=0;n<rows;n++)
		{
	
			System.out.println("Making ExtendedMatrix.");
			Vektor<Double> rowResults = new Vektor<>(rows, 0.0);
			rowResults = rowResults.setValue(n, 1.0);

			Matrix<Double> extendedCoefficientMatrix = 
				coefficientMatrix.glueColumnToThisOnTheRight(rowResults);

			Vektor<Double> result = les.solve(extendedCoefficientMatrix);
			//Vektor<Double> doubleResult = les.convertSolutionVektorToExampleVektor(result);
			output = output.setRow(result, n);				
		}
		
		System.out.println("Inverison Complete.");
		return output;
	}

	public static final Double frobeniusNorm (Matrix<Double> matrix)
	{
		Double[] output = new Double[1];
		output[0]= 0.0;
		
		BiConsumer<Point, Double> bic = (p, d)->
		{
			output[0] = output[0]+d*d;
		};

		try
		{
			matrix.walkThrouMatrix(bic);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Could not compute Frobenius norm.");
		}
		
		output[0]=Math.sqrt(output[0]);
		
		return output[0];
	}

	public MatrixRing(int n) throws MathException
	{

		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n*n;m++)zeros.add(0.0);//DoubleField Neutral of addition!
		neutrumMatrixAddition = new Matrix<>(n, zeros);
		
		List<Double> diagonalMOne = new ArrayList<>();
		for(int x=0;x<n;x++)for(int y=0;y<n;y++)
		{
			if(x==y)diagonalMOne.add(1.0);//DoubleField Neutral of multiplication!
			else diagonalMOne.add(0.0);//DoubleField Neutral of addition!
		}
		neutrumMatrixMultiplication = new Matrix<>(n, diagonalMOne);
		
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
