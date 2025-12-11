package someMath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class LinearEquationSolver
{

	private static VariableIndizies variablesTrackRecord;
	private static Set<Matrix<Double>> offTheTop = new HashSet<>();

	public static Vektor<String> solve(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		Matrix<Double> customizable = extendedCoefficientMatrix.clone();
		int rows = customizable.getRows();
		int cols = customizable.getColumns();

		variablesTrackRecord = new VariableIndizies(cols);
	
		customizable = bubbleSortByLeadingZeros(customizable);
		customizable = scrapeOffTheTop(customizable);
		customizable = shortenMatrix(customizable);

		rows = customizable.getRows();
		cols = customizable.getColumns();
		
		if(rows==cols-1)
		{
			
			DoubleField dField = new DoubleField();
			Matrix<Double> coefficientMatrix = new Matrix<>(cols-1, cols-1, 0.0);
			
			for(int col=0;col<cols-1;col++)
			{
				Matrix<Double> columnVektor = customizable.getColumn(col);
				coefficientMatrix = coefficientMatrix.setColumn(columnVektor, col);
			}
			
			Double determinant = MatrixStuff.determinant(dField, coefficientMatrix);
			Matrix<Double> columnVektor = customizable.getColumn(cols-1);
			
			if(determinant.equals(0.0))
			{
				int n=0;
				cols = customizable.getColumns();
				for(int col=0;col<cols-1;col++)
				{
					Matrix<Double> switchMatrix = coefficientMatrix.setColumn(columnVektor, col);
					Double sideDeterminant = MatrixStuff.determinant(dField, switchMatrix);
					if(sideDeterminant.equals(0.0))n++;
				}
				
				if(n<cols-1)
				{
					System.out.println("No Solution for this Linear Equation System");
					return null;
				}
			}
		}

		if(isRowEchelonForm(customizable))return calculateSolvingVektor(customizable);
		if(isInStaggeredForm(customizable)&&isUnderDeterministic(customizable))return calculateSolvingVektor(customizable);
		
		transFormEquations(customizable);
		
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
		
		return (rows>cols-1);
	}
	
	public static boolean isUnderDeterministic(Matrix<Double> matrix)
	{
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		
		return (rows<cols-1);

	}

	public static Matrix<Double> transFormEquations(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		Matrix<Double> output = extendedCoefficientMatrix.clone();
		
		int rows = output.getRows();
		int cols = output.getColumns();
		
		if(rows<=1)return output;
		
		while(true)
		{
			if(output.getRows()==1)return output;

			int a = 0;
			int b = 1;
			Matrix<Double> rowVektor1 = output.getRow(a);
			int k1 = nrOfLeadingZeros(rowVektor1);
			if(k1==cols-1)return output;
		
			Matrix<Double> rowVektor2 = output.getRow(b);
			int k2 = nrOfLeadingZeros(rowVektor2);
			if(k2==cols-1)return output;
			
			if(k2<k1)output = bubbleSortByLeadingZeros(output);

			if(k2>k1)
			{
				if(b<cols-1)
				{
					a++;
					b++;
				}
				else return output;
			}
		
			if(k1==k2)
			{
				Matrix<Double> newRow = makeAtLeastOneExtraLeadingZero(rowVektor1, rowVektor2);
				output = output.setRow(newRow, b);
				output = bubbleSortByLeadingZeros(output);
				output = eraseZeroRows(output);
				if(isRowEchelonForm(output)&&output.isQuadratic())return output;
				if(isInStaggeredForm(output)&&isUnderDeterministic(output))return output;
			}			
		}
	}

	public static Matrix<Double> makeAtLeastOneExtraLeadingZero(Matrix<Double> rowVektorSource, Matrix<Double> rowVektorDest) throws MathException
	{

		Matrix<Double> output = rowVektorDest.clone();

		int kSource = nrOfLeadingZeros(rowVektorSource);
		int kDest = nrOfLeadingZeros(output);
		if(kDest<kSource)throw new MathException("Destination has less leading Zeros than Source.");
		if(kDest>kSource)return output;

		double sourceValueAtIndexK = rowVektorSource.getValue(kSource, 0);
		double destValueAtIndexK = output.getValue(kSource, 0);
			
		double factor = -(destValueAtIndexK/sourceValueAtIndexK);
			
		Matrix<Double> addOn = MatrixRing.scaling.apply(factor, rowVektorSource);
			
		return MatrixRing.addition.apply(output, addOn);
	}


	public static Vektor<String> calculateSolvingVektor(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

		Map<String, Double> solvedVariables = new HashMap<>();

		//Backwards!!
		for(int row=rows-1;row>-1;row--)
		{
			Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);

			List<Integer> nonZeroList = nonZeros(rowVektor);
			
			Double rowResult = rowVektor.getValue(cols-1, 0);


			//Far Left Index is the variable which is
			//unsolved
			int positionToBeSolved = nonZeroList.get(0);
			int oldIndex = variablesTrackRecord.getOldIndexOf(positionToBeSolved+1);
			String toBeSolvedVariableName = variablesTrackRecord.indexToName(oldIndex);
			Double toBeSolvedVariableCoefficient = rowVektor.getValue(positionToBeSolved, 0);

			if(nonZeroList.size()==1)
			{

				Double result = rowResult/toBeSolvedVariableCoefficient;
				solvedVariables.put(toBeSolvedVariableName, result);

				continue;
			}

			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//TODO:This is a block im not sure about!!!!!
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(nonZeroList.size()==solvedVariables.size()+1)
			{


				Double sumOfProducts = 0.0;
				for(int place: nonZeroList)
				{

					if(place==positionToBeSolved)continue;
					int oldIndex2 = variablesTrackRecord.getOldIndexOf(place+1);
					String variableName = variablesTrackRecord.indexToName(oldIndex2);
					Double coefficient = rowVektor.getValue(place, 0);
					Double solvedVariable = solvedVariables.get(variableName);
					sumOfProducts = sumOfProducts + coefficient*solvedVariable;
				}

				Double result = (rowResult-sumOfProducts)/toBeSolvedVariableCoefficient;
				solvedVariables.put(toBeSolvedVariableName, result);
			}

		}

		List<String> values = new ArrayList<>();
		int s = cols;
		for(int n=1;n<s;n++)
		{

			String name = variablesTrackRecord.indexToName(n);
			if(solvedVariables.containsKey(name))
			{
				Double d = solvedVariables.get(name);
				values.add(name+": "+d.toString());
			}
			else values.add(name);
		}

		Vektor<String> solutionVektor = new Vektor<>(values);
		System.out.println(solutionVektor);
		return solutionVektor;
	}

	public static List<Integer> nonZeros(Matrix<Double> rowVektor) throws MathException
	{
		
		List<Integer> positions = new ArrayList<>();
		int cols = rowVektor.getColumns();
		
		//One the Right side is the value is not coefficient!!
		//So it goes only up too cols-1!!!
		for(int col=0;col<cols-1;col++)
		{
			Double coefficient = rowVektor.getValue(col, 0);
			if(!coefficient.equals(0.0))positions.add(col);

		}
		
		return positions;
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

	public static Matrix<Double> eraseZeroColumns(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		Matrix<Double> output = extendedCoefficientMatrix.clone();
		int cols = output.getColumns();
		
		//column to erase can not be the Right side of
		//a extendedCoefficientMatrix.
		for(int col=0;col<cols-1;col++)
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
		
		Matrix<Double> output = new Matrix<>(cols, rows-1, 0.0);
		
		for(int row=0;row<rows;row++)
		{
			if(row<eraseRow)
			{
				Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
				output = output.setRow(rowVektor, row);
			}
			
			if(row>eraseRow)
			{
				Matrix<Double> rowVektor = extendedCoefficientMatrix.getRow(row);
				output = output.setRow(rowVektor, row-1);
			}
		}

		return output;
	}

	public static void upDateVariableIndizies(int eraseIndex, Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		int indiziesSize = extendedCoefficientMatrix.getColumns();

		for(int n=eraseIndex+1;n<indiziesSize;n++)
		{
			int oldIndex = variablesTrackRecord.getOldIndexOf(n);
			variablesTrackRecord.setNewIndexOf(oldIndex, n-1);
		}
	}

	public static Matrix<Double> eraseColumn(int eraseCol, Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		if(eraseCol == cols-1)throw new MathException("Can't erase the Right side of extendedCoefficientMatrix.");
	
		Matrix<Double> output = new Matrix<>(cols-1, rows, 0.0);

		for(int col=0;col<cols;col++)
		{

			if(col<eraseCol)
			{
				Matrix<Double> colVektor = extendedCoefficientMatrix.getColumn(col);
				output = output.setColumn(colVektor, col);
			}
			
			if(col>eraseCol)
			{
				Matrix<Double> colVektor = extendedCoefficientMatrix.getColumn(col);
				output = output.setColumn(colVektor, col-1);
			}
		}

		upDateVariableIndizies(eraseCol, extendedCoefficientMatrix);

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

	public static boolean isInStaggeredForm(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		int rows =extendedCoefficientMatrix.getRows();
		
		//Starts at 1!
		for(int row=1;row<rows;row++)
		{
			Matrix<Double> rowVektor1 = extendedCoefficientMatrix
										.getRow(row-1);
			int k1 = nrOfLeadingZeros(rowVektor1);
			
			Matrix<Double> rowVektor2 = extendedCoefficientMatrix
										.getRow(row);
			int k2 = nrOfLeadingZeros(rowVektor2);
			
			if(k1>=k2)return false;

		}
		
		return true;
	}
	
	public static boolean isRowEchelonForm(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		boolean staggered = isInStaggeredForm(extendedCoefficientMatrix);
		
		Matrix<Double> topRow = extendedCoefficientMatrix.getRow(0);
		int topLeadingZeros = nrOfLeadingZeros(topRow);
		
		Matrix<Double> bottomRow = extendedCoefficientMatrix.getRow(rows-1);
		int bottomLeadingZeros = nrOfLeadingZeros(bottomRow);
		
		
		return staggered&&(topLeadingZeros==0)&&(bottomLeadingZeros==cols-2);
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
