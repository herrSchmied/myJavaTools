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
	}
}