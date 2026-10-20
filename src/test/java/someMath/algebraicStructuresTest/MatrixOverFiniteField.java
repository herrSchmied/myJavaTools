package someMath.algebraicStructuresTest;



import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;

import someMath.NaturalNumber;
import someMath.exceptions.NaturalNumberException;
import someMath.SmallTools;
import someMath.algebraicStructures.FiniteField;
import someMath.algebraicStructures.MatrixRing;
import someMath.algebraicStructures.RationalField;
import someMath.algebraicStructures.Storage.Matrix;
import someMath.algebraicStructures.Storage.RationalNumber;
import someMath.algebraicStructures.operations.MatrixStuff;
import someMath.exceptions.MathException;

import static consoleTools.TerminalXDisplay.*;


public class MatrixOverFiniteField
{

		MatrixRing<Long> ring;
		
		int finiteFieldSize = 11;
		FiniteField finiteField;

		Long kZero = (long)0;
		Long kOne = (long)1;
		Long kTwo = (long)2;
		Long kThree = (long)3;
		Long kFour = (long)4;
		Long kFive = (long)5;
		Long kSix = (long)6;
		Long kSeven = (long)7;
		Long kEight= (long)8;
		Long kNine = (long)9;
		Long kTen = (long)10;

		public void setup(int n) throws MathException, NaturalNumberException, ClassNotFoundException, IOException
		{

			finiteField = new FiniteField(finiteFieldSize, Path.of("someResources/pList"));
			ring = new MatrixRing<>(n, finiteField);
		}

		@Test
		public void testMatrixAdditionTest() throws MathException, NaturalNumberException, ClassNotFoundException, IOException
		{

			int matrixSideLength = 2;
			setup(2);//Matrix side length and related stuff.
			
			Matrix<Long> zero = ring.zero();
			
			Matrix<Long> s = ring.add(zero, zero);
			
			Matrix<Long> one = ring.one();
			
			assert(s.equals(zero));
			
			Matrix<Long> unchanged = ring.add(zero, one);
			assert(one.equals(unchanged));
			
			List<Long> listOfValues1 = Arrays.asList(kOne, kOne, kOne, kOne);
			Matrix<Long> zeroDetOne = new Matrix<Long>(matrixSideLength, listOfValues1, finiteField);

			List<Long> listOfValues2 = Arrays.asList(kTwo, kZero, kTwo, kZero);
			Matrix<Long> zeroDetTwo = new Matrix<Long>(matrixSideLength, listOfValues2, finiteField);
			
			List<Long> listOfValues3 = Arrays.asList(kZero, kThree, kZero, kThree);
			Matrix<Long> zeroDetThree = new Matrix<Long>(matrixSideLength, listOfValues3, finiteField);
			
			List<Long> listOfValues34 = Arrays.asList(kThree, kFour, kThree, kFour);
			Matrix<Long> zeroDet34 = new Matrix<Long>(matrixSideLength, listOfValues34, finiteField);
			
			Matrix<Long> holder = ring.add(zeroDetOne, zeroDetTwo);
			Matrix<Long> zeroDet = ring.add(holder, zeroDetThree);
			assert(zeroDet34.equals(zeroDet));
		}
		
		@Test
		public void testMatrixMultiplicationTest() throws MathException, NaturalNumberException, ClassNotFoundException, IOException
		{
			int matrixSideLength = 2;
			setup(matrixSideLength);//Matrix side length and related stuff.
			
			List<Long> listOfValues = Arrays.asList(kZero, kTwo, kOne, kOne);
			Matrix<Long> detTwoMinus = new Matrix<Long>(matrixSideLength, listOfValues, finiteField);
			Matrix<Long> neutrumMatrixMultiplication = ring.one();
			Matrix<Long> prod = ring.multiply(neutrumMatrixMultiplication, detTwoMinus);

			assert(prod.equals(detTwoMinus));

			prod = ring.multiply(detTwoMinus, neutrumMatrixMultiplication);
			assert(prod.equals(detTwoMinus));
		}

		@Test
		public <O> void testMatrixDetTest() throws MathException, NaturalNumberException, ClassNotFoundException, IOException
		{

			int l=3;
			setup(l);
			
			for(int n=0;n<6;n++)
			{
				
				System.out.println("MatrixQDet Test " +(n+1));
				List<Long> list = createListOfRandomNumbers(9);
				List<Long> list2 = createListOfRandomNumbers(9);
				
				Matrix<Long> matrix = new Matrix<Long>(l, list, finiteField);
				Matrix<Long> matrix2 = new Matrix<Long>(l, list2, finiteField);
				Matrix<Long> matrix3 = ring.multiply(matrix, matrix2);

				Long det = MatrixStuff.determinant(matrix);
				Long det2 = MatrixStuff.determinant(matrix2);
				Long det3 = MatrixStuff.determinant(matrix3);
			
				Long detProduct = finiteField.multiply(det, det2);
				assert(det3.equals(detProduct));
			}
		}

		@Test
		public void invertTest() throws MathException, InterruptedException, NaturalNumberException, ClassNotFoundException, IOException
		{

//			//TODO:Something goes wrong when using 3x3 Matrixes???
			int matrixSideLength = 3;
			setup(matrixSideLength);//Matrix side length and related stuff.
			MatrixRing<Long> ring2 = new MatrixRing<>(matrixSideLength, finiteField);
			Matrix<Long> e = ring2.one();
			Matrix<Long> e2 = MatrixStuff.invert(e);
			assert(e2.equals(e));
			
			
			Matrix<Long> test = e.setValue(2, 1, kTwo);
			test = test.setValue(1, 2, kOne);
			
			Long d1 = MatrixStuff.determinant(test);

			Matrix<Long> invertedMatrix = MatrixStuff.invert(test);
			Long d2 = MatrixStuff.determinant(invertedMatrix);
			Long d3 = finiteField.multiply(d1, d2);
			System.out.println("Product of determinants: " + d3);
			Long d4 = finiteField.negate(d3);
			Long d5 = finiteField.add(kOne, d4);
	
			
			Matrix<Long>  product = ring.multiply(test, invertedMatrix);
			assert(product.equals(e));

			int n = 0;
			while(n<1000)
			{
				
				System.out.println("InvertTestNr.:" + (n+1));
				int m = matrixSideLength*matrixSideLength;
				List<Long> list = createListOfRandomNumbers(m);

				Matrix<Long> matrix = new Matrix<Long>(matrixSideLength, list, finiteField);

				Long determinante = MatrixStuff.determinant(matrix);
				if(!(determinante%finiteFieldSize==0))
				{

					Matrix<Long> inverted = MatrixStuff.invert(matrix);
					Matrix<Long> prod = ring.multiply(inverted, matrix);
					Long minusOne = finiteField.negate(kOne);
					Matrix<Long> minusProd = MatrixStuff.scale(minusOne, prod);
					Matrix<Long> sum = ring.add(e, minusProd);
					Long norm = MatrixStuff.frobeniusNorm(sum);
					if(norm%11!=0)
					{
						System.out.println("Matrix:\n" + matrix);
						System.out.println("Inverted:\n" + inverted);
						System.out.println("Product:\n" + prod);
						System.out.println("Neutrum:\n" + e);
						System.out.println("Norm of Diff: " + norm);
						System.out.println("Nr. of Invert Tests:" + n);
						assert(false);
					}
					
					assert(norm%finiteFieldSize==(kZero));
					n++;
				}
				
			}
			
			System.out.println(formatBashStringBoldAndGreen("Done " + n + " Matrix inversions successfuly!"));
			Thread.sleep(1000);
		}

		private List<Long> createListOfRandomNumbers(int n) throws MathException, NaturalNumberException
		{
			List<Long> list = new ArrayList<>();
			
			for(int m=0;m<n;m++)
			{
				int zufi = SmallTools.randomInt(10, 0);

				list.add((long)zufi);
			}
			return list;
		}
}