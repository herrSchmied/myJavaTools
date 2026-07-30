package someMathTest;



import java.awt.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.Matrix;
import someMath.MatrixRing;
import someMath.MatrixStuff;
import someMath.SmallTools;
import someMath.Vektor;
import someMath.exceptions.MathException;

import static consoleTools.TerminalXDisplay.*;

public class MatrixTests
{


	MatrixRing<Double> ring;
	DoubleField dField;
	Double prettySmall = Math.pow(10, -12);


	public void setup(int n) throws MathException
	{

		dField = new DoubleField();
		ring = new MatrixRing<>(n, new DoubleField());
	}

	@Test
	public void cloneTest() throws MathException
	{
		setup(3);
		
		List<Double> list = createListOfDoubles(9,0,9);
		Matrix<Double> matrix = new Matrix<>(3, list);
		Matrix<Double> klon = matrix.clone();
		
		assert(matrix.equals(klon));
		assert(klon!=matrix);
	}
	
	@Test
	public void switchRowsAndColumnTest() throws MathException
	{

		dField = new DoubleField();

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

		Double det1 = MatrixStuff.determinant(matrix);

		Matrix<Double> matrix2 = matrix.switchRows(0, 1);

		assert(matrix.getRow(0).equals(matrix2.getRow(1)));
		assert(det1.equals(-MatrixStuff.determinant(matrix2)));


		Matrix<Double> matrix3 = matrix2.switchColumns(0, 1);

		assert(matrix2.getColumn(0).equals(matrix3.getColumn(1)));
		Double det2 = MatrixStuff.determinant(matrix3);

		assert(det1.equals(det2));
	}

	@Test
	public void setAndGettingRowsAndColumnsTest() throws MathException
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
		m2 = new Matrix<>(2, valueList);		
		assert(matrix.equals(m2));
		
		valueList = Arrays.asList(1.0, 1.0);
		m2 = new Matrix<>(2, valueList);
		assert(matrix.getRow(0).equals(m2));
		
		valueList = Arrays.asList(1.0, 0.0, 1.0);
		m2 = new Matrix<>(1, valueList);
		assert(matrix.getColumn(0).equals(m2));
	}



	@Test
	public void testMatrixAdditionTest() throws MathException
	{

		int matrixSideLength = 2;
		setup(2);//Matrix side length and related stuff.
		
		Matrix<Double> zero = ring.sumNeutral();
		
		Matrix<Double> s = ring.add(zero, zero);
		
		Matrix<Double> one = ring.multiplyNeutral();
		
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
		Matrix<Double> neutrumMatrixMultiplication = ring.multiplyNeutral();
		Matrix<Double> prod = ring.multiply(neutrumMatrixMultiplication, detTwoMinus);

		assert(prod.equals(detTwoMinus));

		prod = ring.multiply(detTwoMinus, neutrumMatrixMultiplication);
		assert(prod.equals(detTwoMinus));
	}

	@Test
	public void testMatrixDetTest() throws MathException
	{

		int l=3;
		setup(l);
		for(int n=0;n<6;n++)
		{
			List<Double> list = createListOfDoubles(9, 10, 0);
			List<Double> list2 = createListOfDoubles(9, 10, 0);
			Matrix<Double> matrix = new Matrix<Double>(l, list);
			Matrix<Double> matrix2 = new Matrix<Double>(l, list2);
			Matrix<Double> matrix3 = ring.multiply(matrix, matrix2);

			Double o = MatrixStuff.determinant(matrix);
			Double o2 = MatrixStuff.determinant(matrix2);

			Double o3 = MatrixStuff.determinant(matrix3);
		
			assert(Math.abs(o3-o*o2)<Math.pow(10, -11));
		}
	}

	@Test
	public void invertTest() throws MathException, InterruptedException
	{

//		//TODO:Something goes wrong when using 3x3 Matrixes???
		int matrixSideLength = 3;
		setup(matrixSideLength);//Matrix side length and related stuff.
		MatrixRing<Double> ring2 = new MatrixRing<>(matrixSideLength, new DoubleField());
		Matrix<Double> e = ring2.multiplyNeutral();
		Matrix<Double> e2 = MatrixStuff.invert(e);
		assert(e2.equals(e));
		
		
		Matrix<Double> test = e.setValue(2, 1, 2.0);
		test = test.setValue(1, 2, 1.0);
		
		Double d1 = MatrixStuff.determinant(test);

		Matrix<Double> invertedMatrix = MatrixStuff.invert(test);
		Double d2 = MatrixStuff.determinant(invertedMatrix);
		
		assert(1-(d1*d2)<prettySmall);
		Matrix<Double>  product = ring.multiply(test, invertedMatrix);
		assert(product.equals(e));

		int n = 0;
		while(n<1000)
		{
			
			System.out.println("InvertTestNr.:" + (n+1));
			int m = matrixSideLength*matrixSideLength;
			List<Double> list = createListOfDoubles(m, 11, 1);

			Matrix<Double> matrix = new Matrix<Double>(matrixSideLength, list);

			Double determinante = MatrixStuff.determinant(matrix);
			if(!determinante.equals(0.0))
			{

				Matrix<Double> inverted = MatrixStuff.invert(matrix);
				Matrix<Double> prod = ring.multiply(inverted, matrix);
				Matrix<Double> minusProd = MatrixStuff.scale(-1.0, prod);
				Matrix<Double> sum = ring.add(e, minusProd);
				Double norm = MatrixStuff.frobeniusNorm(sum);
				if(!(norm<=prettySmall))
				{
					System.out.println("Matrix:\n" + matrix);
					System.out.println("Inverted:\n" + inverted);
					System.out.println("Product:\n" + prod);
					System.out.println("Neutrum:\n" + e);
					System.out.println("Norm of Diff: " + norm);
					System.out.println("Nr. of Invert Tests:" + n);
					assert(false);
				}
				
				assert(norm<prettySmall);
				n++;
			}
			
		}
		
		System.out.println(formatBashStringBoldAndGreen("Done " + n + " Matrix inversions successfuly!"));
		Thread.sleep(1000);
	}

	private List<Double> createListOfDoubles(int n, int max, int min)
	{
		List<Double> list = new ArrayList<>();
		
		for(int m=0;m<n;m++)
		{
			Double z = (double)SmallTools.randomInt(max, min);
			list.add(z);
		}
		return list;
	}

	@Test
	public void testTransponing()throws MathException
	{

		int matrixSideLength = 3;
		setup(matrixSideLength);//Matrix side length and related stuff.
		List<Double> listOfValues = Arrays.asList(1.0, 0.0, 0.0, 0.0, 1.0, 3.0, 0.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(matrixSideLength, listOfValues);

		Matrix<Double> t = MatrixStuff.transpone(detTwoMinus);
		assert(!t.equals(detTwoMinus));
		
		Matrix<Double> t2 = MatrixStuff.transpone(t);
		assert(detTwoMinus.equals(t2));
	}

	@Test
	public void testGluing() throws MathException
	{

		Vektor<Double> toBeAttached = new Vektor<>(Arrays.asList(1.0, 2.0, 3.0));
		List<Double> list = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		Matrix<Double> toBeAttachedTo = new Matrix<>(2, list);

		toBeAttachedTo = toBeAttachedTo.glueColumnToThisOnTheRight(toBeAttached);
		toBeAttachedTo = toBeAttachedTo.glueColumnToThisOnTheLeft(toBeAttached);

		Vektor<Double> left = toBeAttachedTo.getColumnAsVektor(0);
		int cols = toBeAttachedTo.getColumns();
		Vektor<Double> right = toBeAttachedTo.getColumnAsVektor(cols-1);

		assert(left.equals(right));
		assert(toBeAttached.equals(left));
		
		toBeAttached = new Vektor<>(Arrays.asList(1.0, 2.0, 3.0, 4.0));
		
		toBeAttachedTo = toBeAttachedTo.glueRowToThisOnTheTop(toBeAttached);
		toBeAttachedTo = toBeAttachedTo.glueRowToThisOnTheBottom(toBeAttached);

		Vektor<Double> top = toBeAttachedTo.getRowAsVektor(0);
		int rows = toBeAttachedTo.getRows();
		Vektor<Double> bottom = toBeAttachedTo.getRowAsVektor(rows-1);

		assert(top.equals(bottom));
		assert(toBeAttached.equals(top));
	}

	@Test
	public void testFindAndReplaceValues() throws MathException
	{

		List<Double> list = Arrays.asList(0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0);
		Matrix<Double> matrix = new Matrix<>(3, list);
		Set<Point> locations = matrix.findValues(1.0);
		matrix = matrix.replaceValues(0.0, 2.0);

		List<Double> list2 = Arrays.asList(2.0, 2.0, 1.0, 2.0, 1.0, 2.0, 1.0, 2.0, 2.0);
		Matrix<Double> matrix2 = new Matrix<>(3, list2);
		Set<Point> locations2 = matrix2.findValues(1.0);

		assert(matrix.equals(matrix2));
		assert(locations2.equals(locations));
	}
}