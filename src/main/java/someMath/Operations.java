package someMath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class Operations<O>
{	
	
	public final String add = "add";
	public final String multiply = "multiply";
	public final String minus = "minus";
	public final String divide = "div";
	public final String pow = "pow";
	public final String root = "root";
	public final String log = "log";

	public final Set<String> opNames = new HashSet<>(Arrays.asList(add, multiply, minus, divide, pow, root, log));

	public final Map<String, Operation<O>> definedOperations = new HashMap<>();

	public Operations(Set<Operation<O>> set)
	{
		
		for(Operation<O> op: set)
		{
			definedOperations.put(op.getName(), op);
		}
	}

	public O execute(String name, O o1, O o2) throws MathException
	{
		Operation<O> op = definedOperations.get(name);
		return op.operate(o1, o2);
	}

	public O add(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(add))throw new MathException("Addition not defined!");
		Operation<O> op = definedOperations.get(add);
		return op.operate(oArray);
	}

	public O multiply(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(multiply))throw new MathException("Multiplication not defined!");
		Operation<O> op = definedOperations.get(multiply);
		return op.operate(oArray);
	}

	public O minus(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(minus))throw new MathException("Subtraction not defined!");
		Operation<O> op = definedOperations.get(minus);
		return op.operate(oArray);
	}

	public O dived(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(divide))throw new MathException("Division not defined!");
		Operation<O> op = definedOperations.get(divide);
		return op.operate(oArray);
	}

	public O pow(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(pow))throw new MathException("Exponentiation not defined!");
		Operation<O> op = definedOperations.get(pow);
		return op.operate(oArray);
	}

	public O root(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(root))throw new MathException("Root not defined!");
		Operation<O> op = definedOperations.get(root);
		return op.operate(oArray);
	}
	
	public O log(O...oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(log))throw new MathException("Logarithim not defined!");
		Operation<O> op = definedOperations.get(log);
		return op.operate(oArray);
	}

	public O getNeutrumOfOperation(String name) throws MathException
	{
		
		Operation<O> op = definedOperations.get(name);
	
		return op.getNeutrum();
	}
	
	public Operation<O> getOperation(String name)
	{
		return definedOperations.get(name);
	}
	
	public void setOperation(Operation<O> op)
	{
		definedOperations.put(op.getName(), op);
	}

	public <E extends Operations<O>> E add(E value) {
		// TODO Auto-generated method stub
		return null;
	}
}