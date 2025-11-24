package someMath;

public class LinearEquationSolver
{

	private static String [] variableNames;

	public static Vektor<Double> solve(Matrix<Double> extendedCoefficientMatrix)
	{
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

		variableNames = new String[cols];
		
		for(int n=0;n<cols;n++)
		{
			variableNames[n] = "x"+n;
		}
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
		
		return extendedCoefficientMatrix;
	}

	public static boolean columnContainsOnlyZeros(int column, Matrix<Double> extendedCoefficientMatrix)
	{

		Matrix<Double> columnVektor = extendedCoefficientMatrix.getColumn(column);
		int rows = columnVektor.getRows();

		for(int row=0;row<rows;row++)
		{
			double value = columnVektor.getValue(0, row);
			if(value!=0.0)return false;
		}
		
		return true;
	}

	public static boolean rowContainsOnlyZeros(int row, Matrix<Double> extendedCoefficientMatrix)
	{

		Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
		int cols = rowVektor.getColumns();
		
		for(int col=0;col<cols;col++)
		{
			double value = rowVektor.getValue(col, 0);
			if(value!=0.0)return false;
		}
		
		return true;
	}
	
	public static Matrix<Double> eraseRow(int eraseRow, Matrix<Double> extendedCoefficientMatrix)
	{
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		
		Matrix<Double> output = new Matrix<>(rows-1, cols, 0.0);
		
		for(int row=0;row<rows;row++)
		{
			if(row<eraseRow)
			{
				Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
				output.setRow(rowVektor, row);
			}
			
			if(row>eraseRow)
			{
				Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
				output.setRow(rowVektor, row-1);
			}

			
		}

		return output;
	}
	
	public static Matrix<Double> eraseColumn(int col, Matrix<Double> extendedCoefficientMatrix)
	{
		return null;
	}
	
	public static boolean isRowEchelonForm(Matrix<Double> extendedCoefficientMatrix)
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		System.out.println("Rows: " + rows);
		int cols = extendedCoefficientMatrix.getColumns();
		boolean bottom = true;
		int n = 0;
		int lastK = 0;
		for(int row=rows-1;row>-1;row--)//From The Bottom up!!
		{
		
			System.out.println("In Row: " + row);
			if(rowContainsOnlyZeros(row, extendedCoefficientMatrix))
			{
				
				System.out.println("Row(" + row +") contains only zeros");
				if(bottom)continue;
				else return false;
			}
			else
			{
				bottom=false;
				n++;
				int k = nrOfLeadingZeros(extendedCoefficientMatrix.getRow(row));
				System.out.println("Nr. of leading Zeros in row("+row+"): " + k);
				if((n==1)&&(k==cols-1))
				{
					lastK=k;
					continue;
				}
				if(k<lastK)lastK=k;
				else return false;
			}
		}

		return true;
	}
	
	public static int nrOfLeadingZeros(Matrix<Double> rowVektor)
	{

		int cols = rowVektor.getColumns();
		int n=0;
		for(int col=0;col<cols;col++)
		{
			double value = rowVektor.getValue(col, 0);
			if(value==0.0)n++;
			else return n;
		}
		
		return n;
	}
}
