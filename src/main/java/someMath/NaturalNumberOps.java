package someMath;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import someMath.exceptions.MathException;

public class NaturalNumberOps extends Operations<NaturalNumber>
{

	BiFunction<NaturalNumber, NaturalNumber, NaturalNumber> addFunc = (n1, n2)->
	{
		try
		{
			return new NaturalNumber(n1.numberCore+ n2.numberCore);
		}
		catch(NaturalNumberException e)
		{
			throw new RuntimeException("Can't add those.");
		}
	};
	
	Operation<NaturalNumber> add;
	Operations<NaturalNumber> opsNat;
	
	@SuppressWarnings("static-access")
	private NaturalNumberOps(Set<Operation<NaturalNumber>> set) throws MathException, NaturalNumberException
	{
		super(set);
		add = new Operation<>(super.add, NaturalNumber.zero, addFunc);
		setOperation(add);
	}

	public NaturalNumberOps() throws MathException, NaturalNumberException
	{
		this(new HashSet<Operation<NaturalNumber>>());
	}
}
