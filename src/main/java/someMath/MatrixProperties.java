package someMath;

import java.util.ArrayList;
import java.util.List;

import someMath.exceptions.MathException;

public class MatrixProperties
{

	@SuppressWarnings("unchecked")
	public static <O, T extends Operations<O>> O determinant(T t,Matrix<O> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(matrix.getRows()==2)return determinantSimpleCase(t, matrix);
		
		int rows = matrix.getRows();
		
		int col = 0;
		O sum = t.getNeutrumOfOperation(Operations.add);
		
		for(int row=0;row<rows;row++)
		{
			O m = matrix.getValue(0, row);
			
			Matrix<O> subM = subMatrix(0, row, matrix);
			O subDet = determinant(t, subM);
			
			O o = t.getNeutrumOfOperation(Operations.add);
			O o2 = t.getNeutrumOfOperation(Operations.multiply);
			O minusOne = t.minus(o, o2);
			
			O o3;
			
			if((row+col)%2==0)o3= t.multiply(subDet, m);
			else o3 = t.multiply(minusOne, subDet, m);
			
			sum = t.add(sum, o3);
		}
		
		return sum;
	}
	
	public static <O, T extends Operations<O>> O determinantSimpleCase(T t,Matrix<O> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(!(matrix.getRows()==2))throw new MathException("Shouldn't happen!(Matrix got not exactly 2 rows");
		

		O o11 = matrix.getValue(0, 0);
		O o12 = matrix.getValue(1, 0);
		O o21 = matrix.getValue(0, 1);
		O o22 = matrix.getValue(1, 1);
		
		O q1 = t.multiply(o11, o22);
		O q2 = t.multiply(o12, o21);
		
		O det = t.minus(q1, q2);
		
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

}
