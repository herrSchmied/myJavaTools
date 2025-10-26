package someMath;


import java.util.function.BiFunction;


import someMath.exceptions.MathException;


public class Operation<O>
{
	
	private final String name;
	private final O neutrum;
	private final BiFunction<O, O, O> op;
	
	public Operation(String name, O neutrum,  BiFunction<O, O, O> op) throws MathException
	{
		this.name = name;
		this.neutrum = neutrum;

		this.op = op;
	}


	public O operate(O o1, O o2) throws MathException
	{		
		return op.apply(o1, o2);
	}

	public Boolean hasNeutralElement()
	{
		return !(neutrum==null);
	}
	
	public O getNeutrum() throws MathException
	{
		if(hasNeutralElement())return neutrum;
		else throw new MathException("This has no Neutrum.");
	}

	public String getName()
	{
		return name; 
	}
}