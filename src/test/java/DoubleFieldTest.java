import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.Operations;
import someMath.exceptions.MathException;

public class DoubleFieldTest
{

	@Test
	public void checkOpsTest() throws MathException
	{

		DoubleField dField = new DoubleField();
		
		Double d = 1.0;

		assert(d.equals(dField.getNeutrumOfOperation(Operations.multiply)));
	
		Double d2 = 2.0;
		Double d3 = 3.0;
		List<Double> operands = new ArrayList<>();
		operands.add(d2);
		operands.add(d3);
		
		Double product = dField.multiply(operands);
		assert(product.equals(6.0));
		
		Double sum = dField.add(operands);
		assert(sum.equals(5.0));
	}
}