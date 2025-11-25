import org.junit.jupiter.api.Test;

import someMath.exceptions.MathException;

import static CollectionTools.CollectionManipulation.*;

public class CollectionManipulationTest
{

	@Test
	public void arrayTests() throws MathException
	{

		Double[][] valueArr = new Double[2][2];
		valueArr[0][0]= 1.0;
		valueArr[1][0]= 2.0;
		valueArr[0][1]= 3.0;
		valueArr[1][1]= 4.0;

		assert(isRegularArray(valueArr));
		assert(!containsNull(valueArr));
		assert(getArrayDimension(valueArr)==2);
		
		valueArr = new Double[2][2];
		valueArr[0][0]= null;
		valueArr[1][0]= 2.0;
		valueArr[0][1]= 3.0;
		valueArr[1][1]= 4.0;

		//assert(isRegularArray(valueArr));
		assert(containsNull(valueArr));
		//assert(getArrayDimension(valueArr)==2);
		
		valueArr = new Double[2][2];
		valueArr[0][1]= 3.0;
		valueArr[1][1]= 4.0;

		//assert(isRegularArray(valueArr));
		assert(containsNull(valueArr));
		//assert(getArrayDimension(valueArr)==2);

	}
}
