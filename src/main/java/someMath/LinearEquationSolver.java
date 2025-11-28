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
	private static Set<Matrix<Double>> offTheTop = new HashSet<>();
	
	public static Vektor<String> solve(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		Matrix<Double> customizable = extendedCoefficientMatrix.clone();
		int rows = customizable.getRows();
		int cols = customizable.getColumns();

	
		variableNames = new ArrayList<>();
		
		for(int n=0;n<cols;n++)
		{
			variableNames.add("x"+n);
		}
		
		customizable = bubbleSortByLeadingZeros(customizable);
		customizable = scrapeOffTheTop(customizable);
		customizable = shortenMatrix(customizable);

		if(customizable.isQuadratic())
		{
			
			DoubleField dField = new DoubleField();
			Double determinant = MatrixStuff.determinant(dField, customizable);
			if(determinant.equals(0.0))
			{
				int n=0;
				cols = customizable.getColumns();
				for(int col=0;col<cols-1;col++)
				{
					Matrix<Double> switchMatrix = customizable.switchColumns(col, cols-1);
					if(MatrixStuff.determinant(dField, switchMatrix).equals(0.0))
						n++;
				}
				
				if(n<cols-1)
				{
					System.out.println("No Solution for this Linear Equation System");
					return null;
				}
			}
		}

		if(isRowEchelonForm(customizable))return calculateSolvingVektor(customizable);
		else transFormEquations(customizable);
		
		return calculateSolvingVektor(extendedCoefficientMatrix);
	}

	public static Matrix<Double> scrapeOffTheTop(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		Matrix<Double> output = extendedCoefficientMatrix.clone();
		
		int rows = output.getRows();
		int cols = output.getColumns();
		int diff = rows-cols;
		
		
		for(int row=0;row<diff;row++)
		{
			Matrix<Double> rowVektor = output.getRow(row);
			output = eraseRow(row, output);
			offTheTop.add(rowVektor);
		}
		
		return output;
	}

	public static boolean isOverDeterministic(Matrix<Double> matrix)
	{
		
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		
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
			shortenMatrix(extendedCoefficientMatrix);
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

	public static Matrix<Double> shortenMatrix(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		Matrix<Double> output = extendedCoefficientMatrix.clone();

		output = eraseZeroRows(output);
		output = eraseZeroColumns(output);

		return output;
	}
	
	public static Matrix<Double> eraseZeroRows(Matrix<Double> matrix) throws MathException
	{

		Matrix<Double> output = matrix.clone();
		int rows = output.getRows();
		
		for(int row=0;row<rows;row++)
		{
			
			if(rowContainsOnlyZeros(row, output))
			{
				output = eraseRow(row, output);
				output = eraseZeroRows(output);
				break;
			}
		}

		return output;
	}

	public static Matrix<Double> eraseZeroColumns(Matrix<Double> matrix) throws MathException
	{

		Matrix<Double> output = matrix.clone();
		int cols = output.getColumns();
		
		for(int col=0;col<cols;col++)
		{
			
			if(columnContainsOnlyZeros(col, output))
			{
				output = eraseColumn(col, output);
				output = eraseZeroColumns(output);
				break;
			}
		}

		return output;
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
	
	public static Matrix<Double> bubbleSortByLeadingZeros(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		int rows = extendedCoefficientMatrix.getRows();
		Matrix<Double> output = extendedCoefficientMatrix.clone();
		if(rows<=1)return output;
		
		boolean noSortingHappend = false;
		
		while(!noSortingHappend)
		{
			
			int n=0;
			for(int row=0;row<rows-1;row++)
			{

				Matrix<Double> current = output.getRow(row);
				Matrix<Double> beneath = output.getRow(row+1);
			
				int a = nrOfLeadingZeros(current);
				int b = nrOfLeadingZeros(beneath);
			
				if(a>b)
				{
					output = output.switchRows(row, row+1);
					n++;
				}
			}

			noSortingHappend=(n==0);
		}
		
		return output;
	}

	public static boolean isRowEchelonForm(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		int rows = extendedCoefficientMatrix.getRows();
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
