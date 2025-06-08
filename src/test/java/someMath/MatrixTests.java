package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import someMath.exceptions.MathException;

public class MatrixTests
{

	int minMatrixAddition = 2;
	int maxMatrixAddition = 50;
	
	List<Double> neutrals = Arrays.asList(0.0, 0.0, 0.0, 0.0);
	Matrix<Double> neutrumOfMatrixAddition;

	int minMatrixMultiplication = 2;
	int maxMatrixMultiplication = 2;
	
	List<Double> neutrals2 = Arrays.asList(1.0, 0.0, 0.0, 1.0);
	Matrix<Double> neutrumOfMatrixMultiplication;

	Set<Operation<Matrix<Double>>> set = new HashSet<>();
	MatrixRing field;
	
	DoubleField dField;

	@BeforeEach
	public void setup() throws MathException
	{

		neutrumOfMatrixAddition = new Matrix<>(2, neutrals);

		neutrumOfMatrixMultiplication = new Matrix<>(2, neutrals2);

		Operation<Matrix<Double>> addOpp = new Operation(Operations.add, neutrumOfMatrixAddition,
				minMatrixAddition, maxMatrixAddition, addition);

		Operation<Matrix<Double>> multiply = new Operation(Operations.multiply, neutrumOfMatrixMultiplication, minMatrixMultiplication, maxMatrixMultiplication, multiplication);

		set.add(addOpp);
		set.add(multiply);
		
		field = new MatrixRing(set);
		
		dField = new DoubleField();

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

	Function<List<Matrix<Double>>, Matrix<Double>> addition = (list)-> 
	{
		
		Matrix<Double> sum;

		int rows = list.get(0).getRows();
		int cols = list.get(0).getColumns();
		
		try
		{
			
			DoubleField df = new DoubleField();

			Double neutrum = df.neutrumAddition;

			sum = new Matrix<>(rows, neutrals);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException();//Remember: Is this optimal?
		}
		
		List<Double> valueList = new ArrayList<>();
		
		for(Matrix<Double> summand: list)
		{			
			for(int col=0;col<cols;col++)
			{
				for(int row=0;row<rows;row++)
				{
					Double d = summand.getValue(col, row);
					Double d2 = sum.getValue(col, row);
					sum.setValue(col, row, (d+d2));
				}
			}
		};

		return sum;
	};		


	Function<List<Matrix<Double>>, Matrix<Double>> multiplication = (list)-> 
	{
		
		
		Matrix<Double> product;

		int rows = list.get(0).getRows();
		int cols = list.get(0).getColumns();

		try
		{
			
			DoubleField df = new DoubleField();

			Double neutrum = df.neutrumAddition;

			product = new Matrix<>(rows, neutrals2);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException();//Remember: Is this optimal?
		}
		
		
		Matrix<Double> left = list.get(0);
		Matrix<Double> right = list.get(1);
				
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				Matrix<Double> leftRow = left.getRow(row);
				Matrix<Double> rightCol = right.getColumn(col);
				
				Double sumProd = 0.0;
				for(int r=0;r<rows;r++)
				{
					Double d = leftRow.getValue(r, 0);
					Double d2 = rightCol.getValue(0, r);
					
					sumProd = sumProd + d*d2;
				}
				
				product.setValue(col, row, sumProd);
			}
		}

		return product;
	};		

	@Test
	public void testMatrixAdditionTest() throws MathException
	{


				
		Matrix<Double> zero = new Matrix<>(2, neutrals);
		
		List<Double> listOfValues = Arrays.asList(1.0, 0.0, 1.0, 0.0);

		Matrix<Double> one = new Matrix<>(2, listOfValues);
		
		Matrix<Double> s = new Matrix<>(2, neutrals);

		s = field.add(zero, zero);
		
		assert(s.equals(zero));
		
		Matrix<Double> unchanged = field.add(one, zero);
		assert(one.equals(unchanged));
		
		List<Double> listOfValues1 = Arrays.asList(1.0, 1.0, 1.0, 1.0);
		Matrix<Double> zeroDetOne = new Matrix<Double>(2, listOfValues1);

		List<Double> listOfValues2 = Arrays.asList(2.0, 0.0, 2.0, 0.0);
		Matrix<Double> zeroDetTwo = new Matrix<Double>(2, listOfValues2);
		
		List<Double> listOfValues3 = Arrays.asList(0.0, 3.0, 0.0, 3.0);
		Matrix<Double> zeroDetThree = new Matrix<Double>(2, listOfValues3);
		
		List<Double> listOfValues34 = Arrays.asList(3.0, 4.0, 3.0, 4.0);
		Matrix<Double> zeroDet34 = new Matrix<Double>(2, listOfValues34);
		
		Matrix<Double> zeroDet = field.add(zeroDetOne, zeroDetTwo, zeroDetThree);
		assert(zeroDet34.equals(zeroDet));
	}
	
	@Test
	public void testMatrixMultiplicationTest() throws MathException
	{
		
		List<Double> listOfValues = Arrays.asList(0.0, 2.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(2, listOfValues);
		
		Matrix<Double> prod = field.multiply(detTwoMinus, neutrumOfMatrixMultiplication);

		assert(prod.equals(detTwoMinus));
	}
	
	@Test
	public void testMatrixDetSimpleCaseTest() throws MathException
	{
		
		List<Double> listOfValues = Arrays.asList(0.0, 2.0, 1.0, 1.0);
		Matrix<Double> detTwoMinus = new Matrix<Double>(2, listOfValues);
		
		Double o = MatrixProperties.determinantSimpleCase(dField, detTwoMinus);
		
		assert(o.equals(-2.0));
	}

}