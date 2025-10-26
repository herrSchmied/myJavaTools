package someMathTest;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.exceptions.MathException;

import static someMath.Operations.*;

public class DoubleFieldTest
{

	@Test
	public void checkOpsTest() throws MathException
	{

		DoubleField dField = new DoubleField();
		
		Double d = 1.0;

		assert(d.equals(dField.getNeutrumOfOperation(multiply)));
	
		Double d2 = 2.0;
		Double d3 = 3.0;
		
		Double product = dField.multiply(d2, d3);
		assert(product.equals(6.0));
		
		Double sum = dField.add(d2, d3);
		assert(sum.equals(5.0));
	}
}