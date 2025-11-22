package someMath;

public class LinearEquationSolver
{

	public static Vektor<Double> solve(Matrix<Double> extendedCoefficientMatrix)
	{
		

		return null;
	}
	
	public static Matrix<Double> shortenTheMatrix(Matrix<Double> extendedCoefficientMatrix)
	{
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		
		boolean [] eraseRow = new boolean[rows];
		boolean [] eraseColumn = new boolean[rows];
		
		while(true)
		{
			for(int row=0;row<rows;row++)
			{
				if(rowContainsOnlyZeros(row, extendedCoefficientMatrix))
				{
					extendedCoefficientMatrix =eraseRow(row, extendedCoefficientMatrix);
					break;
				}
			}
		
			for(int col=0;col<cols;col++)
			{
				if(columnContainsOnlyZeros(col, extendedCoefficientMatrix))
				{
					extendedCoefficientMatrix = eraseColumn(col, extendedCoefficientMatrix);
					break;
				}
			}
			
			break;
		}
		
		return null;
	}

	public static boolean columnContainsOnlyZeros(int column, Matrix<Double> extendedCoefficientMatrix)
	{
		Matrix<Double> columnVektor = extendedCoefficientMatrix.getColumn(column);
		return false;
	}

	public static boolean rowContainsOnlyZeros(int row, Matrix<Double> extendedCoefficientMatrix)
	{
		
		Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
		return false;
	}
	
	public static Matrix<Double> eraseRow(int row, Matrix<Double> extendedCoefficientMatrix)
	{
		return null;
	}
	
	public static Matrix<Double> eraseColumn(int col, Matrix<Double> extendedCoefficientMatrix)
	{
		return null;
	}

}
