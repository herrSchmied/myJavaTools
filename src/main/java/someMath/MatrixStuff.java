package someMath;

import java.util.ArrayList;
import java.util.List;

import someMath.exceptions.MathException;

public class MatrixStuff
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
			List<O> operands = new ArrayList<>();
			operands.add(o);
			operands.add(o2);
			
			O minusOne = t.minus(operands);
			
			O o3;
			
			operands.clear();
			operands.add(subDet);
			operands.add(m);
			if((row+col)%2==0)o3= t.multiply(operands);
			else
			{
				operands.clear();
				operands.add(minusOne);
				operands.add(subDet);
				operands.add(m);
				o3 = t.multiply(operands);
			}
			
			operands.clear();
			operands.add(sum);
			operands.add(o3);
			sum = t.add(operands);
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
		
		List<O> operands = new ArrayList<>();
		operands.add(o11);
		operands.add(o22);
		O q1 = t.multiply(operands);
		
		operands.clear();
		operands.add(o12);
		operands.add(o21);
		O q2 = t.multiply(operands);
		
		
		operands.clear();
		operands.add(q1);
		operands.add(q2);
		O det = t.minus(operands);
		
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
	
	public <O, T extends Operations<O> > Matrix<O> scalarMultiplication(T t, O o, Matrix<O> matrix) throws MathException
	{
		
		int cols = matrix.getColumns();
		int rows = matrix.getRows();
		
		Matrix<O> klon = matrix.clone();
		
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				
				List<O> operands = new ArrayList<>();
				O op = klon.getValue(col, row);
				O op2 = o;
				operands.add(op);
				operands.add(op2);
				
				O product = t.multiply(operands);
				
				klon.setValue(col, row, product);
			}
		}
		
		return klon;
	}
}
