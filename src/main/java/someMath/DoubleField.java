package someMath;



import java.lang.Double;



public class DoubleField implements AlgebraicField<Double>
{

	public DoubleField()
	{
	}

	@Override
	public Double sum(Double o1, Double o2)
	{
		return o1 + o2;
	}

	@Override
	public Double multiply(Double o1, Double o2)
	{
		return o1*o2;
	}

	@Override
	public Double sumInverse(Double o)
	{
		return -1.0*o;
	}

	@Override
	public Double multiplyInverse(Double o)
	{
		return (1/o);
	}

	@Override
	public Double sumNeutral()
	{
		return 0.0;
	}

	@Override
	public Double multiplyNeutral()
	{
		return 1.0;
	}
}