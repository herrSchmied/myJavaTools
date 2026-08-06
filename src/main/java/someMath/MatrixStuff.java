package someMath;


import java.util.ArrayList;
import java.util.List;


import someMath.exceptions.MathException;

public class MatrixStuff
{

	public static <O> O determinant(Matrix<O> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(matrix.getRows()==2)return determinantSimpleCase(matrix);
		
		int rows = matrix.getRows();
		
		Field<O> k = matrix.getField();

		int col = 0;
		O sum = k.zero();
		
		for(int row=0;row<rows;row++)
		{

			O m = matrix.getValue(0, row);
			
			Matrix<O> subM = subMatrix(0, row, matrix);
			O subDet = determinant(subM);
			
			O minusOne = k.negate(k.one());
			
			O o3;
			
			if((row+col)%2==0)o3 = k.multiply(subDet, m);
			else
			{
				o3 = k.multiply(minusOne, subDet);
				o3 = k.multiply(o3, m);
			}
			
			sum = k.add(sum, o3);
		}
		
		return sum;
	}
	
	private static <O> O determinantSimpleCase(Matrix<O> matrix) throws MathException
	{
		if(!matrix.isQuadratic())throw new MathException("Can't compute Determinant of none quadratic Matrix");
		if(!(matrix.getRows()==2))throw new MathException("Shouldn't happen!(Matrix got not exactly 2 rows");
		

		O o11 = matrix.getValue(0, 0);
		O o12 = matrix.getValue(1, 0);
		O o21 = matrix.getValue(0, 1);
		O o22 = matrix.getValue(1, 1);
		
		Field<O> k = matrix.getField();

		O q1 = k.multiply(o11, o22);
		
		O q2 = k.multiply(o12, o21);
		
		
		O det = k.add(q1, k.negate(q2));
		
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
	
	public static final <O> Matrix<O> scale(O d, Matrix<O> m) throws MathException
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
	

	public static final <O> Matrix<O> invert(Matrix<O> matrix) throws MathException
	{

		LinearEquationSolver<O> les = new LinearEquationSolver<>(matrix.getField());
	
		if(!matrix.isQuadratic())
		{
			System.out.println("Matrix is not quadratic so not invertable.");
			return null;
		}

		System.out.println("Inverting Matrix.");
		
		O determinant = determinant(matrix);
		if(determinant.equals(0.0))
		{
			System.out.println("Matrix determinant is Zero so not invertable.");
			return null;
		}
		
		int columns = matrix.getColumns();
		int rows = columns;
		Field<O> k = matrix.getField();

		Matrix<O> coefficientMatrix = matrix.clone();
		coefficientMatrix = transpone(coefficientMatrix);
		Matrix<O> output = new Matrix<>(columns, rows, k.zero());
		for(int n=0;n<rows;n++)
		{
	
			System.out.println("Making ExtendedMatrix.");
			FieldTuple<O> rowResults = new FieldTuple<>(rows, k.zero());
			rowResults = rowResults.setValue(n, k.one());

			Matrix<O> extendedCoefficientMatrix = 
				coefficientMatrix.glueColumnToThisOnTheRight(rowResults);

			FieldTuple<O> result = les.solve(extendedCoefficientMatrix);
			//Vektor<Double> doubleResult = les.convertSolutionVektorToExampleVektor(result);
			output = output.setRow(result, n);				
		}
		
		System.out.println("Inverison Complete.");
		return output;
	}

	public static <O> Matrix<O> transpone(Matrix<O> matrix)
	{
		

		int cols = matrix.getColumns();
		int rows = matrix.getRows();
	
		Matrix<O> transponed;
		Field<O> k = matrix.getField();

		try
		{
			//Remember columns and rows get switched!!
			transponed = new Matrix<O>(rows, cols, k.zero());
			for(int col=0;col<cols;col++)
			{
				for(int row=0;row<rows;row++)
				{
					O d = matrix.getValue(col, row);
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

	public static final <O> O frobeniusNorm (Matrix<O> matrix) throws MathException
	{
		
		Field<O> k = matrix.getField();
		O output = k.zero();
		
	
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
