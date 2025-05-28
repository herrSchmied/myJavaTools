package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import someMath.exceptions.MathException;

public class MatrixTests
{

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
	
	int minMatrixAddition = 2;
	int maxMatrixAddition = 50;
	
	Function<List<Matrix<Double>>, Matrix<Double>> addition = (list)-> 
	{
		
		int cols = 2;
		int rows = 2;
		DoubleField df;
		
		Matrix<Double> sum;
		try
		{
			
			df = new DoubleField();
			Double neutrum = df.neutrumAddition;
			List<Double> neutrals = new ArrayList<>();
			for(int n=0;n<rows*cols;n++)neutrals.add(neutrum);

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
			int c = sum.getColumns();
			int r = sum.getRows();
			
			for(int col=0;col<c;col++)
			{
				for(int row=0;row<r;row++)
				{
					Double d = summand.getValue(col, row);
					Double d2 = sum.getValue(col, row);
					sum.setValue(col, row, (d+d2));
				}
			}
		};
		
		try
		{
			Matrix<Double> output = new Matrix<>(2, valueList);
			return output;
		}
		catch (MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException();//Remember: Is this optimal?
		}
	};		

	@Test
	public void testMatrixAdditionTest() throws MathException
	{
		
		List<Double> neutrals = Arrays.asList(0.0, 0.0, 0.0, 0.0);
		Matrix<Double>neutrumOfMatrixAddition = new Matrix<>(2, neutrals);
		Operation<Matrix<Double>> addOpp = new Operation(Operations.add, neutrumOfMatrixAddition,
				minMatrixAddition, maxMatrixAddition, addition);

		Set<Operation<Matrix<Double>>> set = new HashSet<>();
		set.add(addOpp);
		
		MatrixField field = new MatrixField(set, 2, 2);
		List<Double> neutrums = Arrays.asList(0.0, 0.0, 0.0, 0.0);
		Matrix<Double> zero = new Matrix<>(2, neutrums);
		
		Matrix<Double> s = new Matrix<>(2, neutrums);
		s = field.add(zero, zero);
		
		assert(s.equals(zero));
		
	}
}