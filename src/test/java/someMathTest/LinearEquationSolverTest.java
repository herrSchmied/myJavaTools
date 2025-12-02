package someMathTest;

import org.junit.jupiter.api.Test;

import static someMath.LinearEquationSolver.*;
import someMath.Matrix;
import someMath.exceptions.MathException;

public class LinearEquationSolverTest
{

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
		assert(isRowEchelonForm(matrix));
	}
	
	@Test
	public void scrapeOffTheTopTest() throws MathException
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
		Matrix<Double> m2 = scrapeOffTheTop(matrix);

		assert(matrix.equals(m2));
		
		
		//It' under deterministic now.
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
		
		m2 = scrapeOffTheTop(matrix);

		assert(matrix.equals(m2));
		assert(!isOverDeterministic(matrix));
		assert(isUnderDeterministic(matrix));

		//It' over deterministic now.
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
		
		m2 = scrapeOffTheTop(matrix);

		assert(!matrix.equals(m2));
		assert(isOverDeterministic(matrix));
		
		Matrix<Double> synthetic = new Matrix<>(rows, cols, 0.0);
		
		synthetic = synthetic.setRow(matrix.getRow(0), 0);
		synthetic = synthetic.setRow(m2.getRow(0), 1);
		synthetic = synthetic.setRow(m2.getRow(1), 2);
		
		assert(synthetic.equals(matrix));
	}

	@Test
	public void isRowEchelonTest() throws MathException
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
