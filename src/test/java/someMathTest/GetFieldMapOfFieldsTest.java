package someMathTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import someMath.AlgebraicStructures.Interfaces.Field;
import someMath.algebraicStructures.DoubleField;
import someMath.algebraicStructures.RationalField;
import someMath.algebraicStructures.Storage.MapOfFields;
import someMath.algebraicStructures.Storage.RationalNumber;
import someMath.exceptions.MathException;

public class GetFieldMapOfFieldsTest
{

	@Test
	public void test() throws MathException
	{
		
		assertDoesNotThrow(()->
		{
			
			Field<Double, Integer> k1 = MapOfFields.getField(Double.class);
			DoubleField df = (DoubleField)k1;
		
			Field<RationalNumber, Integer> k2 = MapOfFields.getField(RationalNumber.class);
			RationalField rf = (RationalField)k2;
			
			/*
			Field<ComplexNumber???????
			*/
		});
	}
}
