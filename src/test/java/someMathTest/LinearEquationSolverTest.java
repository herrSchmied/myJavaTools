package someMathTest;



import org.junit.jupiter.api.Test;


import static someMath.LinearEquationSolver.*;


import java.util.Arrays;
import java.util.List;


import someMath.SmallTools;

import someMath.Matrix;

import someMath.Vektor;

import someMath.Vektorraum;

import someMath.exceptions.MathException;

import someMath.LinearEquationSolver;

public class LinearEquationSolverTest
{

	@Test
	public void solvingTest() throws MathException, InterruptedException
	{

		LinearEquationSolver les = new LinearEquationSolver();
		
		Matrix<Double> coefficientmatrix = new Matrix<>(2,2, 0.0);
		coefficientmatrix = coefficientmatrix.setValue(0, 0, 3.0);
		coefficientmatrix = coefficientmatrix.setValue(1, 1, 3.0);

		Vektor<Double> rowResults = new Vektor<>(2, 1.0);

		Matrix<Double> extendedCoefficientMatrix = coefficientmatrix.glueColumnToThisOnTheRight(rowResults);

		Vektor<Double> solution1 = les.solve(extendedCoefficientMatrix);
		//Vektor<Double> solution = les.convertSolutionVektorToExampleVektor(objectSolution1);
		Vektor<Double> rowVektor = coefficientmatrix.getRowAsVektor(0);
		Double r = Vektorraum.scalarProduct.apply(solution1, rowVektor);
		assert(r.equals(rowResults.getValue(1)));
		
		coefficientmatrix = coefficientmatrix.setValue(1, 0, 1.0);
		extendedCoefficientMatrix = coefficientmatrix.glueColumnToThisOnTheRight(rowResults);
		Vektor<Double> v1 = coefficientmatrix.getRowAsVektor(0);
		//solution = les.convertSolutionVektorToExampleVektor(les.solve(extendedCoefficientMatrix));

		r = Vektorraum.scalarProduct.apply(v1, solution1);

		assert(r.equals(rowResults.getValue(1)));
		
		List<Double> list = Arrays.asList(1.0, 2.0, 3.0, 4.0);
		coefficientmatrix = new Matrix<>(2, list);

		rowResults = new Vektor<>(2, 1.0);

		extendedCoefficientMatrix = coefficientmatrix.glueColumnToThisOnTheRight(rowResults);
		
		solution1 = les.solve(extendedCoefficientMatrix);
		//solution = les.convertSolutionVektorToExampleVektor(objectSolution1);
		rowVektor = coefficientmatrix.getRowAsVektor(0);
		r = Vektorraum.scalarProduct.apply(solution1, rowVektor);
		assert(r.equals(rowResults.getValue(1)));
	}

	@Test
	public void makeAtLeastOneExtraLeadingZeroTest() throws MathException
	{

		LinearEquationSolver les = new LinearEquationSolver();

		for(int n=0;n<4;n++)
		{
			
			double z= (double)SmallTools.randomInt(100, 1);
			List<Double> listS = Arrays.asList(z,(z+1),(z+2));
			List<Double> listD = Arrays.asList((z*2),(z+1),(z+2));

			Matrix<Double> sourceRow = new Matrix<>(3, listS);
		
			Matrix<Double> destRow = new Matrix<>(3, listD);
		
			Matrix<Double> result = les.makeAtLeastOneExtraLeadingZero(sourceRow, destRow);

			List<Double> listC = Arrays.asList(0.0, -(z+1), -(z+2));
			
			Matrix<Double> checkRow = new Matrix<>(3, listC);
		
			assert(checkRow.equals(result));

			assert(result.getValue(0, 0).equals(0.0));
		}
	}
	
	@Test
	public void staggeredTest() throws MathException
	{
		
		Double[][] valueArr = new Double[3][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 1.0;
		valueArr[2][0]= 1.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 1.0;
		valueArr[0][2]= 0.0;
		valueArr[1][2]= 0.0;
		valueArr[2][2]= 1.0;
		
		Matrix<Double> matrix = new Matrix<>(valueArr);
		
		assert(isInStaggeredForm(matrix));
		assert(!isRowEchelonForm(matrix));
		
		matrix = new Matrix<>(5, 3, 0.0);
		
		assert(!isInStaggeredForm(matrix));
		assert(!isRowEchelonForm(matrix));
		
		matrix = matrix.setValue(4, 2, 1.0);
		matrix = matrix.setValue(3, 2, 1.0);
		matrix = matrix.setValue(4, 1, 1.0);
		matrix = matrix.setValue(3, 1, 1.0);
		matrix = matrix.setValue(2, 1, 1.0);
		matrix = matrix.setValue(4, 0, 1.0);
		matrix = matrix.setValue(3, 0, 1.0);
		matrix = matrix.setValue(2, 0, 1.0);
		matrix = matrix.setValue(1, 0, 1.0);
		matrix = matrix.setValue(0, 0, 1.0);
		
		assert(isInStaggeredForm(matrix));
		assert(isRowEchelonForm(matrix));
	}
	
	@Test
	public void scrapeOffTheTopTest() throws MathException
	{

		LinearEquationSolver les = new LinearEquationSolver();

		Double[][] valueArr = new Double[3][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[2][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 0.0;
		valueArr[0][2]= 0.0;
		valueArr[1][2]= 0.0;
		valueArr[2][2]= 1.0;
		
		Matrix<Double> matrix = new Matrix<>(valueArr);
		Matrix<Double> m2 = les.scrapeOffTheTop(matrix);

		assert(matrix.equals(m2));
		
		
		//Left part is quadratic now!!!
		valueArr = new Double[3][2];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[2][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 0.0;
		
		matrix = new Matrix<>(valueArr);
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		
		m2 = les.scrapeOffTheTop(matrix);

		assert(matrix.equals(m2));
		assert(!isOverDeterministic(matrix));
		assert(!isUnderDeterministic(matrix));

		//Left side is quadratic now.
		valueArr = new Double[2][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[0][2]= 1.0;
		valueArr[1][2]= 1.0;
		
		matrix = new Matrix<>(valueArr);
		rows = matrix.getRows();
		cols = matrix.getColumns();
		
		m2 = les.scrapeOffTheTop(matrix);

		assert(!matrix.equals(m2));

		assert(!isUnderDeterministic(matrix));
		assert(isOverDeterministic(matrix));
		
		Matrix<Double> synthetic = new Matrix<>(cols, rows, 0.0);
		
		synthetic = synthetic.setRow(matrix.getRow(0), 0);
		synthetic = synthetic.setRow(m2.getRow(0), 1);
		synthetic = synthetic.setRow(m2.getRow(1), 2);
		
		assert(synthetic.equals(matrix));
	}

	@Test
	public void isRowEchelonTest() throws MathException
	{
		Double[][] valueArr = new Double[4][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[2][0]= 0.0;
		valueArr[3][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 0.0;
		valueArr[3][1]= 0.0;
		valueArr[0][2]= 0.0;
		valueArr[1][2]= 0.0;
		valueArr[2][2]= 1.0;
		valueArr[3][2]= 1.0;

		Matrix<Double> matrix = new Matrix<>(valueArr);

		assert(isRowEchelonForm(matrix));
		
		matrix = matrix.switchRows(1, 0);
		
		assert(!isRowEchelonForm(matrix));

	}
	
	@Test
	public void bubbleSortTest() throws MathException
	{
		
		Double[][] valueArr = new Double[3][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[2][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 0.0;
		valueArr[0][2]= 0.0;
		valueArr[1][2]= 0.0;
		valueArr[2][2]= 1.0;
		
		Matrix<Double> matrix = new Matrix<>(valueArr);
		
		Matrix<Double> unOrderedOne = matrix.switchRows(1, 0);
		
		assert(!unOrderedOne.equals(matrix));
		
		Matrix<Double> output = bubbleSortByLeadingZeros(unOrderedOne);

		assert(output.equals(matrix));
	}
	
	@Test
	public void leadingZerosTest() throws MathException
	{
		
		Double[][] valueArr = new Double[3][3];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 0.0;
		valueArr[2][0]= 0.0;
		valueArr[0][1]= 0.0;
		valueArr[1][1]= 1.0;
		valueArr[2][1]= 0.0;
		valueArr[0][2]= 0.0;
		valueArr[1][2]= 0.0;
		valueArr[2][2]= 1.0;

		Matrix<Double> matrix = new Matrix<>(valueArr);
		Matrix<Double> row0 = matrix.getRow(0);
		Matrix<Double> row1 = matrix.getRow(1);
		Matrix<Double> row2 = matrix.getRow(2);
		assert(nrOfLeadingZeros(row0)==0);
		assert(nrOfLeadingZeros(row1)==1);
		assert(nrOfLeadingZeros(row2)==2);

		Matrix<Double> unOrderedOne = matrix.switchRows(1, 0);

		row0 = unOrderedOne.getRow(0);
		row1 = unOrderedOne.getRow(1);
		row2 = unOrderedOne.getRow(2);
		assert(nrOfLeadingZeros(row0)==1);
		assert(nrOfLeadingZeros(row1)==0);
		assert(nrOfLeadingZeros(row2)==2);
	}
}
