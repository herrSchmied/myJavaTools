package someMathTest;

import org.junit.jupiter.api.Test;

import someMath.LinearEquationSolver;
import someMath.Matrix;

public class LinearEquationSolverTest
{

	@Test
	public void isRowEchelonTest()
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
		System.out.println(matrix);
		
		assert(LinearEquationSolver.isRowEchelonForm(matrix));

	}
}
