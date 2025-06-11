package someMath;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import someMath.exceptions.MathException;

import java.lang.Double;

public class DoubleField extends Operations<Double>
{

	static Double neutrumAddition = 0.0;
	static Integer minOperandsAddition = 2;
	static Integer maxOperandsAddition = 1000;

	static Integer minOperandsSubtraction = 2;
	static Integer maxOperandsSubtraction = 2;

	static Double neutrumMultiplication = 1.0;
	static Integer minOperandsMultiplication = 2;
	static Integer maxOperandsMultiplication = 10;

	static Integer minOperandsDivision = 2;
	static Integer maxOperandsDivision = 2;

	static Function<List<Double>, Double> addition = (list)-> 
	{
		
		Double sum = neutrumAddition;
		
		//No specific order approach commutative or not!?
		for(Double summand: list)sum +=summand;

		return sum; 
	};
	
	static Function<List<Double>, Double> subtraction = (list)-> list.get(0)-list.get(1); 

	static Function<List<Double>, Double> multiplication = (list)-> 
	{
		
		Double product = neutrumMultiplication;
		
		//No specific order approach commutative or not!?
		for(Double factor: list)product *=factor;
		
		return product; 
	};

	static Function<List<Double>, Double> division = (list)-> list.get(0)/list.get(1); 

	static HashSet<Operation<Double>> set = new HashSet<Operation<Double>>();
	
	public DoubleField() throws MathException
	{
		super(set);
		
		super.setOperation(new Operation(Operations.add, neutrumAddition, minOperandsAddition,
			maxOperandsAddition, addition));
		
		super.setOperation(new Operation(Operations.minus, null, minOperandsSubtraction, 
				maxOperandsSubtraction, subtraction));
		
		super.setOperation(new Operation(Operations.multiply, neutrumMultiplication, minOperandsMultiplication,
						maxOperandsMultiplication, multiplication));

		super.setOperation(new Operation(Operations.divide, null, minOperandsDivision, 
				maxOperandsDivision, division));
	}

}
