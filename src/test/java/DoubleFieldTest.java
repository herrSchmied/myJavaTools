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
		
		Double d = dField.add(dField.getNeutrumOfOperation(Operations.multiply),
				dField.getNeutrumOfOperation(Operations.add));
		assert(d.equals(dField.getNeutrumOfOperation(Operations.multiply)));
	}
}