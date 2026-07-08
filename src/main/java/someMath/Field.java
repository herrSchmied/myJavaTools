package someMath;


public interface Field<A>
{

	public A add(A a1, A a2) throws Exception;
	public A multiply(A a1, A a2) throws Exception;
	
	public A sumInverse(A a1) throws Exception;
	public A multiplyInverse(A a1) throws Exception;
	
	public A sumNeutral() throws Exception;
	
	public A multiplyNeutral() throws Exception;
}
