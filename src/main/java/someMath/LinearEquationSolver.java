package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class LinearEquationSolver
{

	private List<Integer> erasedIndizies = new ArrayList<>();
	private static Set<Matrix<Double>> offTheTop = new HashSet<>();

	public LinearEquationSolver()
	{
		
	}

	public Vektor<Double> solve(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		System.out.println("Solving Extendedmatrix.");
		Matrix<Double> customizable = extendedCoefficientMatrix.clone();
		int rows = customizable.getRows();
		int cols = customizable.getColumns();

		customizable = bubbleSortByLeadingZeros(customizable);
		customizable = scrapeOffTheTop(customizable);
		customizable = shortenMatrix(customizable);

		rows = customizable.getRows();
		cols = customizable.getColumns();

		if(rows==cols-1)
		{
			
			//calculate the original coefficientMatrix
			DoubleField dField = new DoubleField();
			Matrix<Double> coefficientMatrix = new Matrix<>(cols-1, cols-1, 0.0);
			for(int col=0;col<cols-1;col++)
			{
				Matrix<Double> columnVektor = customizable.getColumn(col);
				coefficientMatrix = coefficientMatrix.setColumn(columnVektor, col);
			}

			Double determinant = MatrixStuff.determinant(coefficientMatrix);
			Matrix<Double> columnVektor = customizable.getColumn(cols-1);

			//Check if despite determinant being Zero it is solvable??
			if(determinant.equals(0.0))
			{
				int n=0;
				cols = customizable.getColumns();
				for(int col=0;col<cols-1;col++)
				{
					Matrix<Double> switchMatrix = coefficientMatrix.setColumn(columnVektor, col);
					Double sideDeterminant = MatrixStuff.determinant(switchMatrix);
					if(sideDeterminant.equals(0.0))n++;
				}

				if(n<cols-1)
				{

					System.out.println("No Solution for this Linear Equation System");
					return null;
				}
			}
		}

		if(isInStaggeredForm(customizable))return calculateSolvingVektor(customizable);
		customizable = transformCoefficients(customizable);

		return calculateSolvingVektor(customizable);
	}

	public Matrix<Double> scrapeOffTheTop(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		
		Matrix<Double> output = extendedCoefficientMatrix.clone();
		
		int rows = output.getRows();
		int cols = output.getColumns();
		int diff = rows-cols;
		
		
		for(int row=0;row<diff;row++)
		{
			Matrix<Double> rowVektor = output.getRow(row);
			output = output.eraseRow(row);
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

	public Matrix<Double> transformCoefficients(Matrix<Double> extendedCoefficientMatrix, int upperRowNr) throws MathException
	{

		System.out.println("Transforming Coefficients. upperRowNr: " + upperRowNr);

		Matrix<Double> output = extendedCoefficientMatrix.clone();
		
		if(isInStaggeredForm(output))return output;

		int rows = output.getRows();
		System.out.println("Rows: " + rows);
		
		if(rows<=1)return output;
		
		int lowerRowNr = upperRowNr + 1;
		System.out.println("Transforming Coefficients. lowerRowNr: " + lowerRowNr);

		if(lowerRowNr>rows-1)return output;

		Matrix<Double> upperRowVektor = output.getRow(upperRowNr);
		int upperRowLeadingZeros = nrOfLeadingZeros(upperRowVektor);
	
		Matrix<Double> lowerRowVektor = output.getRow(lowerRowNr);
		int lowerRowLeadingZeros = nrOfLeadingZeros(lowerRowVektor);
		
		if(lowerRowLeadingZeros<upperRowLeadingZeros)
		{
			output = bubbleSortByLeadingZeros(output);
			return transformCoefficients(output, 0);
		}

		//if upperRowLeadingZeros are less than
		//lowerRowLeadingZeros then increase rowNrs.
		if(lowerRowLeadingZeros>upperRowLeadingZeros)
		{

			System.out.println("Next Line.");
			upperRowNr++;
			lowerRowNr++;
			return transformCoefficients(extendedCoefficientMatrix, upperRowNr);
		}

		//if upper and low leandingZeros are Equal
		//it can't stay so.
		//try to make it staggered or delete a row.
		if(upperRowLeadingZeros==lowerRowLeadingZeros)
		{

			System.out.println("Lines of Equal length.");

			Matrix<Double> klon = output.clone();

			Matrix<Double> newRow = makeAtLeastOneExtraLeadingZero(upperRowVektor, lowerRowVektor);
			klon = klon.setRow(newRow, lowerRowNr);
			klon = bubbleSortByLeadingZeros(klon);
			klon = eraseZeroRows(klon);
			
			int leadingZerosLowerRowOld = nrOfLeadingZeros(lowerRowVektor);
			int leadingZerosLowerRowNew = nrOfLeadingZeros(newRow);
			if(!(leadingZerosLowerRowOld<leadingZerosLowerRowNew))
			{
				String s = "Upper Row: " + upperRowVektor.toString() + "n"
						+  "Lower Row Old: " + lowerRowVektor.toString() +"\n"
						+  "Lower Row New: " + newRow.toString();
				System.out.println(s);
				assert(false);
			}

			if(klon.equals(output))throw new MathException("I'm stuck here!");

			if(isInStaggeredForm(klon))return klon;
			else return transformCoefficients(klon, 0);
		}

		throw new MathException("Should not happen!");			
	}
	
	public Matrix<Double> transformCoefficients(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{
		return transformCoefficients(extendedCoefficientMatrix, 0);
	}

	public Matrix<Double> makeAtLeastOneExtraLeadingZero(Matrix<Double> rowVektorSource, Matrix<Double> rowVektorDest) throws MathException
	{

		Matrix<Double> output = rowVektorDest.clone();

		int kSource = nrOfLeadingZeros(rowVektorSource);
		int kDest = nrOfLeadingZeros(output);
		if(kDest<kSource)throw new MathException("Destination has less leading Zeros than Source.");
		if(kDest>kSource)return output;

		double sourceValueAtIndexK = rowVektorSource.getValue(kSource, 0);
		double destValueAtIndexK = output.getValue(kSource, 0);
			
		double factor = -(destValueAtIndexK/sourceValueAtIndexK);
			
		Matrix<Double> addOn = MatrixRing.scale(factor, rowVektorSource);
		
//		//Finding all Locations that should be Zero!!
//		Set<Point> locations = rowVektorSource.findValues(sourceValueAtIndexK);
		
		output = MatrixRing.sum(output, addOn);
		
//		//Making sure!! because Value might be prettySmall but not Zero!!
//		//locations are found before addOn is changing output!!
//		Does not work why??
//		output = output.replaceValues(locations, 0.0); 
		
		//Making sure see comment above. Seems to work
		output = output.setValue(kSource, 0, 0.0);
		return output;
	}


	public Vektor<Double> calculateSolvingVektor(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		System.out.println("Calculating solving Vektor.");

		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

		Map<String, Double> solvedVariables = new HashMap<>();

		//Backwards!!
		for(int row=rows-1;row>-1;row--)
		{
			Vektor<Double> rowVektor = extendedCoefficientMatrix.getRowAsVektor(row);

			List<Integer> nonZeroList = nonZeros(rowVektor);
			if(nonZeroList.isEmpty())throw new MathException("Hey!! Only Zeros here?\n " + rowVektor);

			Double rowResult = rowVektor.getValue(cols-1);

			//Far Left Index is the variable to be unsolved
			int positionToBeSolved = nonZeroList.get(0);
			String toBeSolvedVariableName = "x"+positionToBeSolved;
			Double toBeSolvedVariableCoefficient = rowVektor.getValue(positionToBeSolved);
			System.out.println("Position: " + positionToBeSolved+". Name: " + toBeSolvedVariableName);
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
					//int oldIndex2 = variablesTrackRecord.getOldIndexOf(place+1);
					String variableName = "x"+place;
					Double coefficient = rowVektor.getValue(place);
					Double solvedVariable = solvedVariables.get(variableName);
					sumOfProducts = sumOfProducts + coefficient*solvedVariable;
				}

				Double result = (rowResult-sumOfProducts)/toBeSolvedVariableCoefficient;
				solvedVariables.put(toBeSolvedVariableName, result);
			}

		}

		System.out.println("SolvedVariables: " + solvedVariables);
		List<Double> values = new ArrayList<>();
		int s = cols;
		for(int n=0;n<s;n++)
		{

			String name = "x"+n;
			if(solvedVariables.containsKey(name))
			{
				Double d = solvedVariables.get(name);
				values.add(d);
			}
			//else values.add(name);
		}

		Vektor<Double> solutionVektor = new Vektor<>(values);
		//System.out.println(solutionVektor);
		return solutionVektor;
	}
	
//	public Vektor<Double> convertSolutionVektorToExampleVektor(Vektor<Object> solution) throws MathException
//	{
//
//		System.out.println("Converting Vector.\n" + solution);
//
//		int rows = solution.getRows();
//		Vektor<Double> example = new Vektor<>(rows, 0.0);
//		
//		for(int row=0;row<rows;row++)
//		{
//			
//			Object value = solution.getValue(row);
//			if(!(value instanceof Double))
//			{
//				throw new MathException("Not Yet supported");
//			}
//			Double dValue = (Double)value;
//			example = example.setValue(row, dValue);
//		}
//		
//		return example;
//	}

	public List<Integer> nonZeros(Vektor<Double> rowVektor) throws MathException
	{
		
		List<Integer> positions = new ArrayList<>();
		int rows = rowVektor.getRows();
		
		//One the Right side is the value is not coefficient!!
		//So it goes only up too cols-1!!!
		for(int row=0;row<rows-1;row++)
		{
			Double coefficient = rowVektor.getValue(row);
			if(!coefficient.equals(0.0))positions.add(row);
		}
		
		return positions;
	}
	
	public Matrix<Double> shortenMatrix(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		Matrix<Double> output = extendedCoefficientMatrix.clone();

		output = eraseZeroRows(output);
		output = eraseZeroColumns(output);

		return output;
	}

	public Matrix<Double> eraseZeroRows(Matrix<Double> matrix) throws MathException
	{

		Matrix<Double> output = matrix.clone();
		int rows = output.getRows();
		
		for(int row=0;row<rows;row++)
		{
			
			if(rowContainsOnlyZeros(row, output))
			{
				output = output.eraseRow(row);
				//output = eraseZeroRows(output);
				break;
			}
		}

		return output;
	}

	public Matrix<Double> eraseZeroColumns(Matrix<Double> extendedCoefficientMatrix) throws MathException
	{

		Matrix<Double> output = extendedCoefficientMatrix.clone();
		int cols = output.getColumns();

		//column to erase can not be the Right side of
		//a extendedCoefficientMatrix.
		for(int col=0;col<cols-1;col++)
		{
			
			if(columnContainsOnlyZeros(col, output))
			{
				output = output.eraseColumn(col);
				//output = eraseZeroColumns(output);
				erasedIndizies.add(col);
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
