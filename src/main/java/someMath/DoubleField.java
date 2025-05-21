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

	Integer minOperandsSubtraction = 2;
	Integer maxOperandsSubtraction = 2;

	Double neutrumMultiplication = 0.0;
	Integer minOperandsMultiplication = 2;
	Integer maxOperandsMultiplication = 10;

	Integer minOperandsDivision = 2;
	Integer maxOperandsDivision = 2;

	Function<List<Double>, Double> addition = (list)-> 
	{
		
		Double sum = neutrumAddition;
		
		//No specific order approach commutative or not!?
		for(Double summand: list)sum +=summand;

		return sum; 
	};
	
	Function<List<Double>, Double> subtraction = (list)-> list.get(0)-list.get(1); 

	Function<List<Double>, Double> multiplication = (list)-> 
	{
		
		Double product = neutrumMultiplication;
		
		//No specific order approach commutative or not!?
		for(Double factor: list)product *=factor;
		
		return product; 
	};

	Function<List<Double>, Double> division = (list)-> list.get(0)/list.get(1); 

	Operation<Double> addOp;
	Operation<Double> subtractOp;
	Operation<Double> multiplyOp;
	Operation<Double> divisionOp;
	
	private static final Set<Operation<Double>> setOfOperations = new HashSet<>();

	
	public DoubleField() throws MathException
	{
		
		super(setOfOperations);
		
		addOp = new Operation(Operations.add, neutrumAddition, minOperandsAddition,
				maxOperandsAddition, addition);
		subtractOp = new Operation(Operations.minus, null, minOperandsSubtraction, 
				maxOperandsSubtraction, subtraction);
		multiplyOp = new Operation(Operations.multiply, neutrumMultiplication, minOperandsMultiplication,
				maxOperandsMultiplication, multiplication);
		divisionOp = new Operation(Operations.divide, null, minOperandsDivision, 
				maxOperandsDivision, division);

		setOfOperations.add(addOp);
		setOfOperations.add(subtractOp);
		setOfOperations.add(multiplyOp);
		setOfOperations.add(divisionOp);
	}

}
