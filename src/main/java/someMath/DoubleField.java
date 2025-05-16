package someMath;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import someMath.exceptions.MathException;

import java.lang.Double;

public class DoubleField extends Operations<Double>
{

	Double neutrumAddition = 0.0;
	Integer minOperandsAddition = 2;
	Integer maxOperandsAddition = 1000;
	
	Double neutrumMultiplication = 1.0;
	Integer minOperandsMultiplication = 2;
	Integer maxOperandsMultiplication = 10;

	Function<List<Double>, Double> addition = (list)-> 
	{
		
		Double sum = 0.0;
		
		//No specific order approach commutative or not!?
		for(Double summand: list)sum +=summand;

		return sum; 
	};
	
	Function<List<Double>, Double> multiplication = (list)-> 
	{
		
		Double product = 0.0;
		
		//No specific order approach commutative or not!?
		for(Double factor: list)product *=factor;
		
		return product; 
	};

	 
	Operation<Double> addOpp;
	Operation<Double> multiplyOpp;
	
	private static final Set<Operation<Double>> setOfOperations = new HashSet<>();

	
	public DoubleField() throws MathException
	{
		
		super(setOfOperations);
		
		addOpp = new Operation(Operations.add, neutrumAddition, minOperandsAddition,
				maxOperandsAddition, addition);
		multiplyOpp = new Operation(Operations.multiply, neutrumMultiplication, minOperandsMultiplication,
				maxOperandsMultiplication, multiplication);
		
		setOfOperations.add(addOpp);
		setOfOperations.add(multiplyOpp);
	}

}
