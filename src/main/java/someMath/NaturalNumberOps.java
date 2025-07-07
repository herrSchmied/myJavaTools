package someMath;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class NaturalNumberOps extends Operations<NaturalNumber>
{

	Function<List<NaturalNumber>, NaturalNumber> addFunc = (list)->
	{
		
		int size = list.size();
		
		NaturalNumber start = NaturalNumber.zero;
		for(int n=0;n<size;n++)
		{
			NaturalNumber listItem = list.get(n);
			
			try
			{
				start = new NaturalNumber(start.numberCore+listItem.numberCore);
			} 
			catch (NaturalNumberException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return start;
	};
	
	Operation<NaturalNumber> add;
	Operations<NaturalNumber> opsNat;
	
	@SuppressWarnings("static-access")
	private NaturalNumberOps(Set<Operation<NaturalNumber>> set) throws MathException, NaturalNumberException
	{
		super(set);
		add = new Operation<>(super.add, NaturalNumber.zero, 2, Integer.MAX_VALUE, addFunc);
		setOperation(add);
	}

	public NaturalNumberOps() throws MathException, NaturalNumberException
	{
		this(new HashSet<Operation<NaturalNumber>>());
	}
}
