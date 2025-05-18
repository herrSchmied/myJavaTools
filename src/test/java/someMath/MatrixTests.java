package someMath;

import java.util.Arrays;
import java.util.List;

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
}