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
		
		Matrix<Double> matrix = new Matrix(valueArr);
		System.out.println("Matrix rows: " + matrix.getRows());
		System.out.println("Matrix columns: " + matrix.getColumns());
		
		System.out.println("\n" + matrix);
		
		List<Double> valueList = Arrays.asList(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				1.0);
		
		Matrix<Double> m2 = new Matrix(3, valueList);
		
		System.out.println(m2);
		
		assert(matrix.equals(m2));
		
		valueArr = new Double[2][3];
		valueArr[0][0]= 1.0;
		valueArr[0][1]= 0.0;
		valueArr[0][2]= 1.0;
		valueArr[1][0]= 1.0;
		valueArr[1][1]= 0.0;
		valueArr[1][2]= 0.0;

		matrix = new Matrix(valueArr);
		System.out.println("Matrix rows: " + matrix.getRows());
		System.out.println("Matrix columns: " + matrix.getColumns());
		
		String s = matrix.toString();
		System.out.println(s);
		
		valueList = Arrays.asList(1.0, 1.0, 0.0, 0.0, 1.0, 0.0);
		
		m2 = new Matrix(3, valueList);
		
		System.out.println(m2);
		
		assert(matrix.equals(m2));

	}
}