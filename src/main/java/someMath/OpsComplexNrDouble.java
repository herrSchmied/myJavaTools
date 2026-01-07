package someMath;


import java.util.HashSet;
import java.util.Set;

import someMath.exceptions.MathException;

public class OpsComplexNrDouble extends Operations<ComplexNrDouble>
{

	private static final Set<Operation<ComplexNrDouble>> set = new HashSet<>();
	
	private OpsComplexNrDouble(Set<Operation<ComplexNrDouble>> set) throws MathException
	{
		super(set);
	}
	
	public OpsComplexNrDouble() throws MathException
	{
		this(set);
	}

	public void setOperation(Operation<ComplexNrDouble> op)
	{
		super.setOperation(op);
	}
}
