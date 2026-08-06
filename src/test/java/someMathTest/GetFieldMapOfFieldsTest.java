package someMathTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.Field;
import someMath.MapOfFields;
import someMath.RationalField;
import someMath.RationalNumber;
import someMath.exceptions.MathException;

public class GetFieldMapOfFieldsTest
{

	@Test
	public void test() throws MathException
	{
		
		assertDoesNotThrow(()->
		{
			
			Field<Double> k1 = MapOfFields.getField(Double.class);
			DoubleField df = (DoubleField)k1;
		
			Field<RationalNumber> k2 = MapOfFields.getField(RationalNumber.class);
			RationalField rf = (RationalField)k2;
			
			/*
			Field<ComplexNumber???????
			*/
		});
	}
}
