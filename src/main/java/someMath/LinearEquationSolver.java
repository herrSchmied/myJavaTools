package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import someMath.exceptions.MathException;

public class LinearEquationSolver
{

	private static List<String> variableNames = new ArrayList<>();
	private static Set<String> freeVariables = new HashSet<>();
	private static Matrix<Double> offTheTop;
	
	public static Vektor<String> solve(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

	
		variableNames = new ArrayList<>();
		
		for(int n=0;n<cols;n++)
		{
			variableNames.add("x"+n);
		}
		
		shortenMatrix(extendedCoefficientMatrix);
		bubbleSortByLeadingZeros(extendedCoefficientMatrix);
		scrapeOffTheTop(extendedCoefficientMatrix);
		
		if(isRowEchelonForm(extendedCoefficientMatrix))return calculateSolvingVektor(extendedCoefficientMatrix);
		else transFormEquations(extendedCoefficientMatrix);
		
		return calculateSolvingVektor(extendedCoefficientMatrix);
	}

	public static void scrapeOffTheTop(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		if(!isOverDeterministic(extendedCoefficientMatrix))return;
		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		int diff = rows-cols;
		
		offTheTop = new Matrix<>(cols, cols, 0.0);
		
		for(int row=0;row<diff;row++)
		{
			Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
			eraseRow(row, extendedCoefficientMatrix);
			offTheTop = offTheTop.setRow(rowVektor, row);
		}
	}

	public static boolean isOverDeterministic(Matrix<Double> extendedCoefficientMatrix)
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		
		return (rows>cols);
	}

	public static void transFormEquations(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		
		if(rows<=1)return;
		
		Matrix<Double> rowVektor1 = extendedCoefficientMatrix.getRow(0);
		int k1 = nrOfLeadingZeros(rowVektor1);
		if(k1==cols-1)return;
		
		Matrix<Double> rowVektor2 = extendedCoefficientMatrix.getRow(1);
		int k2 = nrOfLeadingZeros(rowVektor2);
		if(k2==cols-1)return;
		
		if(k1==k2)
		{
			makeAtLeastOneLeadingZeroExtra(rowVektor1, rowVektor2);
			extendedCoefficientMatrix.setRow(rowVektor2, 1);
			bubbleSortByLeadingZeros(extendedCoefficientMatrix);
		}
	}

	public static void makeAtLeastOneLeadingZeroExtra(Matrix<Double> source, Matrix<Double> dest) throws MathException
	{

		int kSource = nrOfLeadingZeros(source);
		int kDest = nrOfLeadingZeros(dest);
		
		if(kDest>kSource)return;
		if(kDest<kSource)throw new MathException("Destination has less leading Zeros than Source.");
	
		double sourceValueAtIndexK = source.getValue(kSource, 0);
		double destValueAtIndexK = dest.getValue(kSource, 0);
			
		double factor = -(destValueAtIndexK/sourceValueAtIndexK);
			
		Matrix<Double> addOn = MatrixRing.scaling.apply(factor, source);
			
		dest = MatrixRing.addition.apply(dest, addOn);
	}
	
	public static Vektor<String> calculateSolvingVektor(Matrix<Double> extendedCoefficientMatrix)
	{
		return null;
	}

	public static void shortenMatrix(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		
//		boolean [] eraseRow = new boolean[rows];
//		boolean [] eraseColumn = new boolean[cols];
		
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
					String v = variableNames.remove(col);
					freeVariables.add(v);
					break;
				}
			}
			
			break;
		}
	}

	public static boolean columnContainsOnlyZeros(int column, Matrix<Double> extendedCoefficientMatrix) throws MathException
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

	public static boolean rowContainsOnlyZeros(int row, Matrix<Double> extendedCoefficientMatrix) throws MathException
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
	
	public static Matrix<Double> eraseRow(int eraseRow, Matrix<Double> extendedCoefficientMatrix) throws MathException
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
	
	public static Matrix<Double> eraseColumn(int eraseCol, Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

		Matrix<Double> output = new Matrix<>(rows, cols-1, 0.0);

		for(int col=0;col<cols;col++)
		{

			if(col<eraseCol)
			{
				Matrix<Double> colVektor = extendedCoefficientMatrix.getColumn(col);
				output.setColumn(colVektor, col);
			}
			
			if(col>eraseCol)
			{
				Matrix<Double> colVektor = extendedCoefficientMatrix.getColumn(col);
				output.setColumn(colVektor, col-1);
			}
		}

		return output;
	}
	
	public static void bubbleSortByLeadingZeros(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		int rows = extendedCoefficientMatrix.getRows();
		if(rows<=1)return;
		
		while(true)
		{

			boolean sortActionHappend = false;
			for(int row=0;row<rows-1;row++)
			{
				Matrix<Double> above = extendedCoefficientMatrix.getRow(row+1);
				Matrix<Double> current = extendedCoefficientMatrix.getRow(row);
			
				int a = nrOfLeadingZeros(above);
				int c = nrOfLeadingZeros(current);
			
				if(a<c)
				{
					extendedCoefficientMatrix.switchRows(row, row+1);
					sortActionHappend= true;
				}
			}
			
			if(!sortActionHappend)break;
		}

	}

	public static boolean isRowEchelonForm(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		System.out.println("Rows: " + rows);
		int cols = extendedCoefficientMatrix.getColumns();
		boolean bottom = true;
		int n = 0;
		int lastK = 0;
		for(int row=rows-1;row>-1;row--)//From The Bottom up!!
		{
			
			int k = nrOfLeadingZeros(extendedCoefficientMatrix.getRow(row));
			if((row==0)&&(k==0))return(row==0)&&(k==0);

			if(rowContainsOnlyZeros(row, extendedCoefficientMatrix))
			{
				
				if(bottom)continue;
				else return false;
			}
			else
			{
				bottom=false;
				n++;
				if(n==1)
				{
					if(!(k==cols-1))return false;
					
					lastK=k;
					continue;
				}
				if(k<lastK)lastK=k;
				else return false;
			}
		}

		return true;
	}
	
	public static int nrOfLeadingZeros(Matrix<Double> rowVektor) throws MathException
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
