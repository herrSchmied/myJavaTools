package someMath;

import java.util.ArrayList;
import java.util.List;

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
	
	public Matrix<Double> scalarMultiplication(Double o, Matrix<Double> matrix) throws MathException
	{
		
		int cols = matrix.getColumns();
		int rows = matrix.getRows();
		
		Matrix<Double> klon = matrix.clone();
		
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				
				Double op = klon.getValue(col, row);
	
				
				Double product = o * op;
				
				klon.setValue(col, row, product);
			}
		}
		
		return klon;
	}
}
