package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class LinearEquationSolver<O>
{

	private final Field<O> k;

	private List<Integer> erasedIndizies = new ArrayList<>();
	private Set<Matrix<O>> offTheTop = new HashSet<>();

	public LinearEquationSolver(Field<O> k)
	{
		this.k = k;
	}

	public Vektor<O> solve(Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		System.out.println("Solving Extendedmatrix.");
		Matrix<O> customizable = extendedCoefficientMatrix.clone();
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
			Matrix<O> coefficientMatrix = new Matrix<>(cols-1, cols-1, k.sumNeutral());
			for(int col=0;col<cols-1;col++)
			{
				Matrix<O> columnVektor = customizable.getColumn(col);
				coefficientMatrix = coefficientMatrix.setColumn(columnVektor, col);
			}

			O determinant = MatrixStuff.determinant(coefficientMatrix);
			Matrix<O> columnVektor = customizable.getColumn(cols-1);

			//Check if despite determinant being Zero it is solvable??
			if(determinant.equals(k.sumNeutral()))
			{
				int n=0;
				cols = customizable.getColumns();
				for(int col=0;col<cols-1;col++)
				{
					Matrix<O> switchMatrix = coefficientMatrix.setColumn(columnVektor, col);
					O sideDeterminant = MatrixStuff.determinant(switchMatrix);
					if(sideDeterminant.equals(k.sumNeutral()))n++;
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

	public Matrix<O> scrapeOffTheTop(Matrix<O> extendedCoefficientMatrix) throws MathException
	{
		
		Matrix<O> output = extendedCoefficientMatrix.clone();
		
		int rows = output.getRows();
		int cols = output.getColumns();
		int diff = rows-cols;
		
		
		for(int row=0;row<diff;row++)
		{
			Matrix<O> rowVektor = output.getRow(row);
			output = output.eraseRow(row);
			offTheTop.add(rowVektor);
		}
		
		return output;
	}

	public boolean isOverDeterministic(Matrix<O> matrix)
	{
		
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		
		return (rows>cols-1);
	}
	
	public boolean isUnderDeterministic(Matrix<O> matrix)
	{
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		
		return (rows<cols-1);

	}

	public Matrix<O> transformCoefficients(Matrix<O> extendedCoefficientMatrix, int upperRowNr) throws MathException
	{

		System.out.println("Transforming Coefficients. upperRowNr: " + upperRowNr);

		Matrix<O> output = extendedCoefficientMatrix.clone();
		
		if(isInStaggeredForm(output))return output;

		int rows = output.getRows();
		System.out.println("Rows: " + rows);
		
		if(rows<=1)return output;
		
		int lowerRowNr = upperRowNr + 1;
		System.out.println("Transforming Coefficients. lowerRowNr: " + lowerRowNr);

		if(lowerRowNr>rows-1)return output;

		Matrix<O> upperRowVektor = output.getRow(upperRowNr);
		int upperRowLeadingZeros = nrOfLeadingZeros(upperRowVektor);
	
		Matrix<O> lowerRowVektor = output.getRow(lowerRowNr);
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

			Matrix<O> klon = output.clone();

			Matrix<O> newRow = makeAtLeastOneExtraLeadingZero(upperRowVektor, lowerRowVektor);
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
	
	public Matrix<O> transformCoefficients(Matrix<O> extendedCoefficientMatrix) throws MathException
	{
		return transformCoefficients(extendedCoefficientMatrix, 0);
	}

	public Matrix<O> makeAtLeastOneExtraLeadingZero(Matrix<O> rowVektorSource, Matrix<O> rowVektorDest) throws MathException
	{

		Matrix<O> output = rowVektorDest.clone();

		int kSource = nrOfLeadingZeros(rowVektorSource);
		int kDest = nrOfLeadingZeros(output);
		if(kDest<kSource)throw new MathException("Destination has less leading Zeros than Source.");
		if(kDest>kSource)return output;

		O sourceValueAtIndexK = rowVektorSource.getValue(kSource, 0);
		O destValueAtIndexK = output.getValue(kSource, 0);
			
		O factor = k.sumInverse(k.multiply(destValueAtIndexK,k.multiplyInverse(sourceValueAtIndexK)));

			
		Matrix<O> addOn = MatrixStuff.scale(factor, rowVektorSource);
		
		//Finding all Locations that should be Zero!!
		Set<Point> locations = rowVektorSource.findValues(sourceValueAtIndexK);
		Set<Point> locations2 = rowVektorDest.findValues(destValueAtIndexK);
		Set<Point> shouldBeZeros = new HashSet<>();
		
		for(Point p: locations)
		{
			if(locations2.contains(p))shouldBeZeros.add(p);
		}
		
		MatrixRing<O> ring = new MatrixRing<>(2, k);
		output = ring.add(output, addOn);
		
		//Making sure!! because Value might be prettySmall but not Zero!!
		//locations of "should be Zero's" are found before addOn is 
		//changing output!!
		output = output.replaceValues(shouldBeZeros, k.sumNeutral()); 
		
		//Making sure see comment above. Seems to work
		output = output.setValue(kSource, 0, k.sumNeutral());
		return output;
	}


	public Vektor<O> calculateSolvingVektor(Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		System.out.println("Calculating solving Vektor.");

		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();

		Map<String, O> solvedVariables = new HashMap<>();

		//Backwards!!
		for(int row=rows-1;row>-1;row--)
		{
			Vektor<O> rowVektor = extendedCoefficientMatrix.getRowAsVektor(row);

			List<Integer> nonZeroList = nonZeros(rowVektor);
			if(nonZeroList.isEmpty())throw new MathException("Hey!! Only Zeros here?\n " + rowVektor);

			O rowResult = rowVektor.getValue(cols-1);

			//Far Left Index is the variable to be unsolved
			int positionToBeSolved = nonZeroList.get(0);
			String toBeSolvedVariableName = "x"+positionToBeSolved;
			O toBeSolvedVariableCoefficient = rowVektor.getValue(positionToBeSolved);
			System.out.println("Position: " + positionToBeSolved+". Name: " + toBeSolvedVariableName);
			if(nonZeroList.size()==1)
			{
						
				O result = k.multiply(rowResult, k.multiplyInverse(toBeSolvedVariableCoefficient));

				solvedVariables.put(toBeSolvedVariableName, result);

				continue;
			}

			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//TODO:This is a block im not sure about!!!!!
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(nonZeroList.size()==solvedVariables.size()+1)
			{


				O sumOfProducts = k.sumNeutral();
				for(int place: nonZeroList)
				{

					if(place==positionToBeSolved)continue;
					//int oldIndex2 = variablesTrackRecord.getOldIndexOf(place+1);
					String variableName = "x"+place;
					O coefficient = rowVektor.getValue(place);
					O solvedVariable = solvedVariables.get(variableName);
					sumOfProducts = k.add(sumOfProducts, k.multiply(coefficient, solvedVariable));
				}
				
				O numerator = k.add(rowResult, k.sumInverse(sumOfProducts));
				O denominator = k.multiplyInverse(toBeSolvedVariableCoefficient);
				O result = k.multiply(numerator, denominator);
				
				solvedVariables.put(toBeSolvedVariableName, result);
			}

		}

		System.out.println("SolvedVariables: " + solvedVariables);
		List<O> values = new ArrayList<>();
		int s = cols;
		for(int n=0;n<s;n++)
		{

			String name = "x"+n;
			if(solvedVariables.containsKey(name))
			{
				O d = solvedVariables.get(name);
				values.add(d);
			}
			//else values.add(name);
		}

		Vektor<O> solutionVektor = new Vektor<>(values);
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

	public List<Integer> nonZeros(Vektor<O> rowVektor) throws MathException
	{
		
		List<Integer> positions = new ArrayList<>();
		int rows = rowVektor.getRows();
		
		//One the Right side is the value is not coefficient!!
		//So it goes only up too cols-1!!!
		for(int row=0;row<rows-1;row++)
		{
			O coefficient = rowVektor.getValue(row);
			if(!coefficient.equals(k.sumNeutral()))positions.add(row);
		}
		
		return positions;
	}
	
	public Matrix<O> shortenMatrix(Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		Matrix<O> output = extendedCoefficientMatrix.clone();

		output = eraseZeroRows(output);
		output = eraseZeroColumns(output);

		return output;
	}

	public Matrix<O> eraseZeroRows(Matrix<O> matrix) throws MathException
	{

		Matrix<O> output = matrix.clone();
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

	public Matrix<O> eraseZeroColumns(Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		Matrix<O> output = extendedCoefficientMatrix.clone();
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

	public boolean columnContainsOnlyZeros(int column, Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		Matrix<O> columnVektor = extendedCoefficientMatrix.getColumn(column);
		int rows = columnVektor.getRows();

		for(int row=0;row<rows;row++)
		{
			O value = columnVektor.getValue(0, row);
			if(value!=k.sumNeutral())return false;
		}
		
		return true;
	}

	public boolean rowContainsOnlyZeros(int row, Matrix<O> extendedCoefficientMatrix) throws MathException
	{

		Matrix<O> rowVektor = extendedCoefficientMatrix.getRow(row);
		int cols = rowVektor.getColumns();
		
		for(int col=0;col<cols;col++)
		{
			O value = rowVektor.getValue(col, 0);
			if(value!=k.sumNeutral())return false;
		}
		
		return true;
	}
	
	public Matrix<O> bubbleSortByLeadingZeros(Matrix<O> extendedCoefficientMatrix) throws MathException
	{
		int rows = extendedCoefficientMatrix.getRows();
		Matrix<O> output = extendedCoefficientMatrix.clone();
		if(rows<=1)return output;
		
		boolean noSortingHappend = false;
		
		while(!noSortingHappend)
		{
			
			int n=0;
			for(int row=0;row<rows-1;row++)
			{

				Matrix<O> current = output.getRow(row);
				Matrix<O> beneath = output.getRow(row+1);
			
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

	public boolean isInStaggeredForm(Matrix<O> extendedCoefficientMatrix) throws MathException
	{
		int rows =extendedCoefficientMatrix.getRows();
		
		//Starts at 1!
		for(int row=1;row<rows;row++)
		{
			Matrix<O> rowVektor1 = extendedCoefficientMatrix
										.getRow(row-1);
			int k1 = nrOfLeadingZeros(rowVektor1);
			
			Matrix<O> rowVektor2 = extendedCoefficientMatrix
										.getRow(row);
			int k2 = nrOfLeadingZeros(rowVektor2);
			
			if(k1>=k2)return false;

		}
		
		return true;
	}
	
	public boolean isRowEchelonForm(Matrix<O> extendedCoefficientMatrix) throws MathException
	{
		
		int rows = extendedCoefficientMatrix.getRows();
		int cols = extendedCoefficientMatrix.getColumns();
		boolean staggered = isInStaggeredForm(extendedCoefficientMatrix);
		
		Matrix<O> topRow = extendedCoefficientMatrix.getRow(0);
		int topLeadingZeros = nrOfLeadingZeros(topRow);
		
		Matrix<O> bottomRow = extendedCoefficientMatrix.getRow(rows-1);
		int bottomLeadingZeros = nrOfLeadingZeros(bottomRow);
		
		
		return staggered&&(topLeadingZeros==0)&&(bottomLeadingZeros==cols-2);
	}
	
	public int nrOfLeadingZeros(Matrix<O> rowVektor) throws MathException
	{


		int cols = rowVektor.getColumns();
		int n=0;
		for(int col=0;col<cols;col++)
		{
			O value = rowVektor.getValue(col, 0);
			if(value == k.sumNeutral())n++;
			else return n;
		}
		
		return n;
	}
}
