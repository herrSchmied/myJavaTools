package someMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import someMath.exceptions.MathException;

public class Operations<O>
{	
	
	public final static String add = "addition";
	public final static String multiply = "multiplication";
	public final static String minus = "subtraction";
	public final static String divide = "division";
	public final static String pow = "power";
	public final static String root = "root";
	public final static String log = "logarythm";

	public final Set<String> opNames = new HashSet<>(Arrays.asList(add, multiply, minus, divide, pow, root, log));

	public final Map<String, Operation<O>> definedOperations = new HashMap<>();
	
	public final Set<Operation<O>> setOfOperations = new HashSet<>();
	
	public Operations(Set<Operation<O>> set)
	{
		
		if(set==null)throw new NullPointerException("Set is null");
		
		setOfOperations.addAll(set);
		
		for(Operation<O> op: set)
		{
			definedOperations.put(op.getName(), op);
		}
	}

	public O execute(String name, O o1, O o2) throws MathException
	{
		Operation<O> op = definedOperations.get(name);
		List<O> list = new ArrayList<>();
		list.add(o1);
		list.add(o2);
		
		return op.operate(list);
	}

	public O add(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(add))throw new MathException("Addition not defined!");
		Operation<O> op = definedOperations.get(add);
		return op.operate(oArray);
	}

	public O multiply(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(multiply))throw new MathException("Multiplication not defined!");
		Operation<O> op = definedOperations.get(multiply);
		return op.operate(oArray);
	}

	public O minus(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(minus))throw new MathException("Subtraction not defined!");
		Operation<O> op = definedOperations.get(minus);
		return op.operate(oArray);
	}

	public O dived(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(divide))throw new MathException("Division not defined!");
		Operation<O> op = definedOperations.get(divide);
		return op.operate(oArray);
	}

	public O pow(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(pow))throw new MathException("Exponentiation not defined!");
		Operation<O> op = definedOperations.get(pow);
		return op.operate(oArray);
	}

	public O root(List<O> oArray) throws MathException
	{
		
		if(!definedOperations.containsKey(root))throw new MathException("Root not defined!");
		Operation<O> op = definedOperations.get(root);
		return op.operate(oArray);
	}
	
	public O log(List<O> oArray) throws MathException
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