package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import someMath.exceptions.MathException;

public class MatrixStuff
{

	public static Double determinant(Matrix<Double> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(matrix.getRows()==2)return determinantSimpleCase(matrix);
		
		int rows = matrix.getRows();
		
		int col = 0;
		Double sum = 0.0;
		
		for(int row=0;row<rows;row++)
		{
			Double m = matrix.getValue(0, row);
			
			Matrix<Double> subM = subMatrix(0, row, matrix);
			Double subDet = determinant(subM);
			
			Double minusOne = -1.0;
			
			Double o3;
			
			if((row+col)%2==0)o3= subDet * m;
			else
			{
				/*
				 * operands.clear();
				 * operands.add(minusOne);
				 * operands.add(subDet);
				 * operands.add(m);
				 */				
				o3 = minusOne*subDet;
				o3 = o3* m;
			}
			
			sum = sum + o3;
		}
		
		return sum;
	}
	
	public static Double determinantSimpleCase(Matrix<Double> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(!(matrix.getRows()==2))throw new MathException("Shouldn't happen!(Matrix got not exactly 2 rows");
		

		Double o11 = matrix.getValue(0, 0);
		Double o12 = matrix.getValue(1, 0);
		Double o21 = matrix.getValue(0, 1);
		Double o22 = matrix.getValue(1, 1);
		
		Double q1 = o11 * o22;
		
		Double q2 = o12 * o21;
		
		
		Double det = q1 - q2;
		
		return det;
	}
	
	public static <O> Matrix<O> subMatrix(int col, int row, Matrix<O> matrix) throws MathException
	{
		
		int cols = matrix.getColumns();
		int rows = matrix.getRows();
		List<O> valueList = new ArrayList<>();
		
		for(int c=0;c<cols;c++)
		{
			for(int r=0;r<rows;r++)
			{
				if((c!=col&&r!=row))
				{
					
					O e = matrix.getValue(c, r);
					valueList.add(e);
				}
			}
		}

		return new Matrix<O>(rows-1, valueList);
	}
	
	public final <O> Matrix<O> scale(O d, Matrix<O> m) throws MathException
	{

		Matrix<O> m2 = m.clone();
		Field<O> k = m.getField();

		int rows = m.getRows();
		int cols = m.getColumns();
		
		for(int r=0;r<rows;r++)
		{
			for(int c=0;c<cols;c++)
			{
				O v = m.getValue(c, r);
				m2 = m2.setValue(c, r, k.multiply(v, d));
			}
		}
		
		return m2;
	}
	

	public final <O> Matrix<O> invert(Matrix<O> matrix) throws MathException
	{

		LinearEquationSolver les = new LinearEquationSolver(matrix.getField());
	
		if(!matrix.isQuadratic())
		{
			System.out.println("Matrix is not quadratic so not invertable.");
			return null;
		}

		System.out.println("Inverting Matrix.");
		
		Double determinant = MatrixStuff.determinant(matrix);
		if(determinant.equals(0.0))
		{
			System.out.println("Matrix determinant is Zero so not invertable.");
			return null;
		}
		
		int columns = matrix.getColumns();
		int rows = columns;
		
		Matrix<O> coefficientMatrix = matrix.clone();
		coefficientMatrix = transpone(coefficientMatrix);
		Matrix<O> output = new Matrix<>(columns, rows, k.sumNeutral());
		for(int n=0;n<rows;n++)
		{
	
			System.out.println("Making ExtendedMatrix.");
			Vektor<Double> rowResults = new Vektor<>(rows, 0.0);
			rowResults = rowResults.setValue(n, 1.0);

			Matrix<O> extendedCoefficientMatrix = 
				coefficientMatrix.glueColumnToThisOnTheRight(rowResults);

			Vektor<O> result = les.solve(extendedCoefficientMatrix);
			//Vektor<Double> doubleResult = les.convertSolutionVektorToExampleVektor(result);
			output = output.setRow(result, n);				
		}
		
		System.out.println("Inverison Complete.");
		return output;
	}


	public final <O> O frobeniusNorm (Matrix<O> matrix) throws MathException
	{
		
		Field<O> k = matrix.getField();
		O output = k.sumNeutral();
		
	
		int rows = matrix.getRows();
		int cols = matrix.getColumns();

		for(int row=0;row<rows;row++)
		{
			for(int col=0;col<cols;col++)
			{
				O d = matrix.getValue(col, row);
				output = k.add(output, k.multiply(d, d));
			}
		}	
		
		//Remember: I need to get the SQRT of: output!!!!!!!!!!!!
		return output;
	}
}
