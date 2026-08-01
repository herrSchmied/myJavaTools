package someMathTest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;


import someMath.Field;
import someMath.Matrix;
import someMath.MatrixRing;
import someMath.MatrixStuff;
import someMath.NaturalNumber;
import someMath.NaturalNumberException;
import someMath.RationalField;
import someMath.RationalNumber;
import someMath.SmallTools;
import someMath.exceptions.MathException;

import static consoleTools.TerminalXDisplay.*;

public class MatrixQTest
{


	MatrixRing<RationalNumber> ring;
	RationalField rField;
	RationalNumber prettySmall;
	
	RationalNumber rnZero;
	RationalNumber rnOne;
	RationalNumber two;
	RationalNumber three;
	RationalNumber four;
	RationalNumber five;
	RationalNumber six;
	RationalNumber seven;
	RationalNumber eight;
	RationalNumber nine;
	RationalNumber ten;	

	public void setup(int n) throws MathException, NaturalNumberException
	{

		rField = new RationalField();
		ring = new MatrixRing<>(n, new RationalField());
		prettySmall = new RationalNumber(true, NaturalNumber.zero, NaturalNumber.one, new NaturalNumber(1000000000));

		rnZero = RationalNumber.zero;
		rnOne = RationalNumber.one;
		two = rField.add(rnOne, rnOne);
		three = rField.add(two, rnOne);
		four = rField.add(three, rnOne);
		five = rField.add(three, two);
		six = rField.add(three, three);
		seven = rField.add(three, four);
		eight = rField.add(four, four);
		nine = rField.add(four, five);
		ten = rField.add(seven, three);	
	}

	



	@Test
	public void testMatrixAdditionTest() throws MathException, NaturalNumberException
	{

		int matrixSideLength = 2;
		setup(2);//Matrix side length and related stuff.
		
		Matrix<RationalNumber> zero = ring.sumNeutral();
		
		Matrix<RationalNumber> s = ring.add(zero, zero);
		
		Matrix<RationalNumber> one = ring.multiplyNeutral();
		
		assert(s.equals(zero));
		
		Matrix<RationalNumber> unchanged = ring.add(zero, one);
		assert(one.equals(unchanged));
		
		List<RationalNumber> listOfValues1 = Arrays.asList(rnOne, rnOne, rnOne, rnOne);
		Matrix<RationalNumber> zeroDetOne = new Matrix<RationalNumber>(matrixSideLength, listOfValues1);

		List<RationalNumber> listOfValues2 = Arrays.asList(two, rnZero, two, rnZero);
		Matrix<RationalNumber> zeroDetTwo = new Matrix<RationalNumber>(matrixSideLength, listOfValues2);
		
		List<RationalNumber> listOfValues3 = Arrays.asList(rnZero, three, rnZero, three);
		Matrix<RationalNumber> zeroDetThree = new Matrix<RationalNumber>(matrixSideLength, listOfValues3);
		
		List<RationalNumber> listOfValues34 = Arrays.asList(three, four, three, four);
		Matrix<RationalNumber> zeroDet34 = new Matrix<RationalNumber>(matrixSideLength, listOfValues34);
		
		Matrix<RationalNumber> holder = ring.add(zeroDetOne, zeroDetTwo);
		Matrix<RationalNumber> zeroDet = ring.add(holder, zeroDetThree);
		assert(zeroDet34.equals(zeroDet));
	}
	
	@Test
	public void testMatrixMultiplicationTest() throws MathException, NaturalNumberException
	{
		int matrixSideLength = 2;
		setup(matrixSideLength);//Matrix side length and related stuff.
		
		List<RationalNumber> listOfValues = Arrays.asList(rnZero, two, rnOne, rnOne);
		Matrix<RationalNumber> detTwoMinus = new Matrix<RationalNumber>(matrixSideLength, listOfValues);
		Matrix<RationalNumber> neutrumMatrixMultiplication = ring.multiplyNeutral();
		Matrix<RationalNumber> prod = ring.multiply(neutrumMatrixMultiplication, detTwoMinus);

		assert(prod.equals(detTwoMinus));

		prod = ring.multiply(detTwoMinus, neutrumMatrixMultiplication);
		assert(prod.equals(detTwoMinus));
	}

	@Test
	public <O> void testMatrixDetTest() throws MathException, NaturalNumberException
	{

		int l=3;
		setup(l);
		
		for(int n=0;n<6;n++)
		{
			List<RationalNumber> list = createListOfRNs(9);
			List<RationalNumber> list2 = createListOfRNs(9);
			Matrix<RationalNumber> matrix = new Matrix<RationalNumber>(l, list);
			@SuppressWarnings("unchecked")
			Field<O> k = (Field<O>) matrix.getField();
			assert(new RationalField().equals(rField));
			Matrix<RationalNumber> matrix2 = new Matrix<RationalNumber>(l, list2);
			Matrix<RationalNumber> matrix3 = ring.multiply(matrix, matrix2);

			RationalNumber o = MatrixStuff.determinant(matrix);
			RationalNumber o2 = MatrixStuff.determinant(matrix2);

			RationalNumber o3 = MatrixStuff.determinant(matrix3);
		
			RationalNumber o4 = rField.sumInverse(rField.multiply(o, o2));
			RationalNumber o5 = rField.add(o3, o4);
			assert(o5.isSmallerThan(prettySmall));
		}
	}

	@Test
	public void invertTest() throws MathException, InterruptedException, NaturalNumberException
	{

//		//TODO:Something goes wrong when using 3x3 Matrixes???
		int matrixSideLength = 3;
		setup(matrixSideLength);//Matrix side length and related stuff.
		MatrixRing<RationalNumber> ring2 = new MatrixRing<>(matrixSideLength, new RationalField());
		Matrix<RationalNumber> e = ring2.multiplyNeutral();
		Matrix<RationalNumber> e2 = MatrixStuff.invert(e);
		assert(e2.equals(e));
		
		
		Matrix<RationalNumber> test = e.setValue(2, 1, two);
		test = test.setValue(1, 2, rnOne);
		
		RationalNumber d1 = MatrixStuff.determinant(test);

		Matrix<RationalNumber> invertedMatrix = MatrixStuff.invert(test);
		RationalNumber d2 = MatrixStuff.determinant(invertedMatrix);
		RationalNumber d3 = rField.multiply(d1, d2);
		System.out.println("Product of determinants: " + d3);
		RationalNumber d4 = rField.sumInverse(d3);
		RationalNumber d5 = rField.add(rnOne, d4);
		assert(d5.isSmallerThan(prettySmall));
		
		Matrix<RationalNumber>  product = ring.multiply(test, invertedMatrix);
		assert(product.equals(e));

		int n = 0;
		while(n<1000)
		{
			
			System.out.println("InvertTestNr.:" + (n+1));
			int m = matrixSideLength*matrixSideLength;
			List<RationalNumber> list = createListOfRNs(m);

			Matrix<RationalNumber> matrix = new Matrix<RationalNumber>(matrixSideLength, list);

			RationalNumber determinante = MatrixStuff.determinant(matrix);
			if(!determinante.equals(rnZero))
			{

				Matrix<RationalNumber> inverted = MatrixStuff.invert(matrix);
				Matrix<RationalNumber> prod = ring.multiply(inverted, matrix);
				RationalNumber minusOne = rField.sumInverse(rnOne);
				Matrix<RationalNumber> minusProd = MatrixStuff.scale(minusOne, prod);
				Matrix<RationalNumber> sum = ring.add(e, minusProd);
				RationalNumber norm = MatrixStuff.frobeniusNorm(sum);
				if(norm.isSmallerThan(prettySmall))
				{
					System.out.println("Matrix:\n" + matrix);
					System.out.println("Inverted:\n" + inverted);
					System.out.println("Product:\n" + prod);
					System.out.println("Neutrum:\n" + e);
					System.out.println("Norm of Diff: " + norm);
					System.out.println("Nr. of Invert Tests:" + n);
					assert(false);
				}
				
				assert(norm.isSmallerThan(prettySmall));
				n++;
			}
			
		}
		
		System.out.println(formatBashStringBoldAndGreen("Done " + n + " Matrix inversions successfuly!"));
		Thread.sleep(1000);
	}

	private List<RationalNumber> createListOfRNs(int n) throws MathException, NaturalNumberException
	{
		List<RationalNumber> list = new ArrayList<>();
		
		for(int m=0;m<n;m++)
		{
			int numerator = SmallTools.randomInt(200, 0);
			int denominator = SmallTools.randomInt(200, 1);
			Double dsign = ((double)SmallTools.randomInt(1, 0));
			boolean sign = dsign.equals(1.0);
			int faktor = 1;
			if(!sign)faktor = -1;
			numerator = faktor*numerator;
			RationalNumber r = new RationalNumber(numerator, denominator);
			list.add(r);
		}
		return list;
	}
}