package someMath;



public interface AlgebraicField<O>
{

	public O sum(O o1, O o2);
	public O multiply(O o1, O o2);
	
	public O sumInverse(O o);
	public O multiplyInverse(O o);
	
	public O sumNeutral();
	
	public O multiplyNeutral();
}
