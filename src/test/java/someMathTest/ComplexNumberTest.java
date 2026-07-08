package someMathTest;



import org.junit.jupiter.api.Test;


import someMath.ComplexField;

import someMath.ComplexNumber;

import someMath.DoubleField;

import someMath.MapOfFields;



public class ComplexNumberTest
{

	@Test
	public void test() throws Exception
	{

		ComplexField<Double, DoubleField, ComplexNumber<Double>> cf = new ComplexField<>(new DoubleField());

		ComplexNumber<Double> c1 = new ComplexNumber<>(1.0, 2.0);
		ComplexNumber<Double> c2 = new ComplexNumber<>(1.0, 2.0);

		ComplexNumber<Double> c3 = cf.add(c1, c2);

		System.out.println(c3);

		Double d1 = 1.0;
		Double d2 = 2.0;
		DoubleField df =  (DoubleField) MapOfFields.getField(Double.class);

		Double d3 = df.add(d1, d2);

		System.out.println(d3);

		assert(d3.equals(3.0));
	}
}