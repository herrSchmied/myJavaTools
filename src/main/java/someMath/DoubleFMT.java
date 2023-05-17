package someMath;

           //FMT=ForMathTools
public class DoubleFMT implements MultiplyableAndAddable<DoubleFMT>
{
	
	public final Double value;
	
	public DoubleFMT(Double value)
	{
		this.value = value;
	}


	public String toString()
	{
		return value.toString();
	}


	@Override
	public boolean hasNeutralOne() 
	{
		return true;
	}

	@Override
	public DoubleFMT getNeutralOne()
	{
		return new DoubleFMT(1.0);
	}

	@Override
	public boolean hasNeutralZero() 
	{
		return true;
	}

	@Override
	public DoubleFMT getNeutralZero() 
	{
		return new DoubleFMT(0.0);
	}

	@Override
	public DoubleFMT multiplyWith(DoubleFMT e)
	{
		return new DoubleFMT(e.value*this.value);
	}

	@Override
	public DoubleFMT addWith(DoubleFMT e)
	{
		return new DoubleFMT(e.value+this.value);
	}


	@Override
	public DoubleFMT subtractArg(DoubleFMT e) 
	{
		
		return new DoubleFMT(this.value - e.value);
	}
	
	public boolean equals(Object obj)
	{
		
		if (obj == this) return true;
		
	    if (!(obj instanceof DoubleFMT)) return false;
	    DoubleFMT other = (DoubleFMT)obj;
	    
	    if(!(other.value== this.value))return false;
	
	    return true;
	}
}