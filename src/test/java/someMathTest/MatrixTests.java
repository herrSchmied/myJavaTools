package someMathTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.Matrix;
import someMath.MatrixRing;
import someMath.MatrixStuff;
import someMath.Operations;
import someMath.exceptions.MathException;

public class MatrixTests
{

	MatrixRing ring;
	DoubleField dField;

	public void setup(int n) throws MathException
	{

		dField = new DoubleField();
		ring = new MatrixRing(n);
	}

	@Test
	public void setAndGettingRowsAndColumns()
	{

		Double[][] valueArr = new Double[2][2];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 2.0;
		valueArr[0][1]= 3.0;
		valueArr[1][1]= 4.0;
		
		Matrix<Double> matrix = new Matrix<>(valueArr);
		
		Double[][] valueArrRow = new Double[2][1];
		valueArrRow[0][0]= 7.0;
		valueArrRow[1][0]= 7.0;

		Matrix<Double> rowVektor = new Matrix<>(valueArrRow);
		matrix.setRow(rowVektor, 1);
		assert(matrix.getRow(1).equals(rowVektor));


		Double[][] valueArrCol = new Double[1][2];
		valueArrCol[0][0]= 10.0;
		valueArrCol[0][1]= 10.0;

		Matrix<Double> colVektor = new Matrix<>(valueArrCol);
		matrix.setColumn(colVektor, 0);
		
		assert(matrix.getColumn(0).equals(colVektor));
	}
	
	@Test
	public void ValueTests() throws MathException
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
		List<Double> valueList = Arrays.asList(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				1.0);		
		Matrix<Double> m2 = new Matrix<>(3, valueList);
		assert(matrix.equals(m2));
		
		valueArr = new Double[2][3];
		valueArr[0][0]= 1.0;
		valueArr[0][1]= 0.0;
		valueArr[0][2]= 1.0;
		valueArr[1][0]= 1.0;
		valueArr[1][1]= 0.0;
		valueArr[1][2]= 0.0;

		matrix = new Matrix<>(valueArr);
		valueList = Arrays.asList(1.0, 1.0, 0.0, 0.0, 1.0, 0.0);
		m2 = new Matrix<>(3, valueList);		
		assert(matrix.equals(m2));
		
		valueList = Arrays.asList(1.0, 1.0);
		m2 = new Matrix<>(1, valueList);
		assert(matrix.getRow(0).equals(m2));
		
		valueList = Arrays.asList(1.0, 0.0, 1.0);
		m2 = new Matrix<>(3, valueList);
		assert(matrix.getColumn(0).equals(m2));
		System.out.println(matrix.getColumn(0));
	}



	@Test
	public void testMatrixAdditionTest() throws MathException
	{

		int matrixSideLength = 2;
		setup(2);//Matrix side length and related stuff.
		
		Matrix<Double> zero = ring.getNeutrumOfOperation(Operations.add);
		
		Matrix<Double> s = ring.add(zero, zero);
		
		Matrix<Double> one = ring.getNeutrumOfOperation(Operations.multiply);
		
		assert(s.equals(zero));
		
		Matrix<Double> unchanged = ring.add(zero, one);
		assert(one.equals(unchanged));
		
		List<Double> listOfValues1 = Arrays.asList(1.0, 1.0, 1.0, 1.0);
		Matrix<Double> zeroDetOne = new Matrix<Double>(matrixSideLength, listOfValues1);

		List<Double> listOfValues2 = Arrays.asList(2.0, 0.0, 2.0, 0.0);
		Matrix<Double> zeroDetTwo = new Matrix<Double>(matrixSideLength, listOfValues2);
		
		List<Double> listOfValues3 = Arrays.asList(0.0, 3.0, 0.0, 3.0);
		Matrix<Double> zeroDetThree = new Matrix<Double>(matrixSideLength, listOfValues3);
		
		List<Double> listOfValues34 = Arrays.asList(3.0, 4.0, 3.0, 4.0);
		Matrix<Double> zeroDet34 = new Matrix<Double>(matrixSideLength, listOfValues34);
		
		Matrix<Double> holder = ring.add(zeroDetOne, zeroDetTwo);
		Matrix<Double> zeroDet = ring.add(holder, zeroDetThree);
		assert(zeroDet34.equals(zeroDet));
	}
	
	@Test
	public void testMatrixMultiplicationTest() throws MathException
	{
		int matrixSideLength = 2;
		setup(matrixSideLength);//Matrix side length and related stuff.
		
		List<Double> listOfValues = Arrays.asList(0.0, 2.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(matrixSideLength, listOfValues);
		System.out.println(detTwoMinus);
		Matrix<Double> neutrumMatrixMultiplication = ring.getNeutrumOfOperation(Operations.multiply);
		System.out.println(neutrumMatrixMultiplication);
		Matrix<Double> prod = ring.multiply(neutrumMatrixMultiplication, detTwoMinus);

		assert(prod.equals(detTwoMinus));
		
		prod = ring.multiply(detTwoMinus, neutrumMatrixMultiplication);
		assert(prod.equals(detTwoMinus));
	}

	@Test
	public void testMatrixDetTest() throws MathException
	{
		
		int matrixSideLength = 3;
		setup(matrixSideLength);//Matrix side length and related stuff.
		List<Double> listOfValues = Arrays.asList(1.0, 0.0, 0.0, 0.0, 1.0, 3.0, 0.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(matrixSideLength, listOfValues);
		System.out.println(detTwoMinus);
		
		Double o = MatrixStuff.determinant(dField, detTwoMinus);

		assert(o.equals(-2.0));
	}
	
	@Test
	public void testTransponing()throws MathException
	{
		int matrixSideLength = 3;
		setup(matrixSideLength);//Matrix side length and related stuff.
		List<Double> listOfValues = Arrays.asList(1.0, 0.0, 0.0, 0.0, 1.0, 3.0, 0.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(matrixSideLength, listOfValues);
		System.out.println(detTwoMinus);
		
		//Double o = MatrixStuff.determinant(dField, detTwoMinus);

		
		Matrix<Double> t = ring.transponent.apply(detTwoMinus);
		assert(!t.equals(detTwoMinus));
		
		Matrix<Double> t2 = ring.transponent.apply(t);
		assert(detTwoMinus.equals(t2));
	}
}