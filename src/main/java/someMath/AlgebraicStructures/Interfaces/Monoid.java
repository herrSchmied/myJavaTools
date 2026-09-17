package someMath.AlgebraicStructures.Interfaces;

public interface Monoid<O>
{
	
	public O operate(O o1, O o2);
	public O neutral();
}
