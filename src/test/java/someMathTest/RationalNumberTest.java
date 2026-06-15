package someMathTest;


import org.junit.jupiter.api.Test;

import someMath.NaturalNumber;
import someMath.NaturalNumberException;
import someMath.RationalNumber;
import someMath.exceptions.MathException;


public class RationalNumberTest
{

	@Test
	public void test() throws NaturalNumberException, MathException
	{

		RationalNumber rn = 
				new RationalNumber(false, new NaturalNumber(0), new NaturalNumber(88), new NaturalNumber(88));

		RationalNumber rn2 = 
				new RationalNumber(false, new NaturalNumber(0), new NaturalNumber(2), new NaturalNumber(2));
	
		System.out.println(rn + " vs " + rn2);
		
		assert(rn.equals(rn2));
		
		rn = new RationalNumber(false, new NaturalNumber(9), new NaturalNumber(88), new NaturalNumber(88));

		rn2 = new RationalNumber(false, new NaturalNumber(5), new NaturalNumber(10), new NaturalNumber(2));
	
		System.out.println(rn + " vs " + rn2);
		
		assert(rn.equals(rn2));

		rn = new RationalNumber(false, new NaturalNumber(9), new NaturalNumber(11), new NaturalNumber(88));
		rn2 = new RationalNumber(false, new NaturalNumber(8), new NaturalNumber(99), new NaturalNumber(88));

		System.out.println(rn + " vs " + rn2);

		assert(rn.equals(rn2));

	}
}
