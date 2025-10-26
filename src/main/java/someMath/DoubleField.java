package someMath;


import java.util.HashSet;
import java.util.function.BiFunction;

import someMath.exceptions.MathException;

import java.lang.Double;

public class DoubleField extends Operations<Double>
{

	private static final Double neutrumAddition = 0.0;

	private static final Double neutrumMultiplication = 1.0;


	private static final BiFunction<Double, Double, Double> addition = (s1, s2)-> s1+s2; 

	private static final BiFunction<Double, Double, Double> subtraction = (s1, s2)-> s1-s2; 

	private static final BiFunction<Double, Double, Double> multiplication = (s1, s2)-> s1*s2; 

	private static final BiFunction<Double, Double, Double> division = (s1, s2)-> s1/s2; 

	private static final HashSet<Operation<Double>> set = new HashSet<Operation<Double>>();
	
	@SuppressWarnings("static-access")
	public DoubleField() throws MathException
	{
		super(set);
		
		super.setOperation(new Operation<>(super.add, neutrumAddition, addition));
		
		super.setOperation(new Operation<>(super.minus, null, subtraction));
		
		super.setOperation(new Operation<>(super.multiply, neutrumMultiplication, multiplication));

		super.setOperation(new Operation<>(super.divide, null, division));
	}

}