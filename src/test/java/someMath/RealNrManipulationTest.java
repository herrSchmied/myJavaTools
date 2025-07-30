package someMath;
import org.junit.jupiter.api.Test;

import someMath.RealNrManipulation;

public class RealNrManipulationTest 
{

	@Test
	public void testSuperRoot()
	{
		
		double p = Math.pow(5, 5);
		double r = RealNrManipulation.superRoot(p);
		System.out.println(p);
		System.out.println(r);
		assert(r==5.0d);
	}
}
