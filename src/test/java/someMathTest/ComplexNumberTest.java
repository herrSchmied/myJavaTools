package someMathTest;

import org.junit.jupiter.api.Test;

import someMath.ComplexField;
import someMath.ComplexNumber;
import someMath.DoubleField;
import someMath.exceptions.MathException;

public class ComplexNumberTest
{
	
	@Test
	public void test() throws MathException
	{

		ComplexField<Double, DoubleField, ComplexNumber<Double, DoubleField>> cf = new ComplexField();
		
		DoubleField df = new DoubleField();
		ComplexNumber<Double, DoubleField> c1 = new ComplexNumber<>(1.0, 2.0, df);
		ComplexNumber<Double, DoubleField> c2 = new ComplexNumber<>(1.0, 2.0, df);
		
		ComplexNumber c3 =cf.add(c1, c2);
		
		System.out.println(c3);
	}
}