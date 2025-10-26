package someMathTest;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import java.util.List;

import someMath.NaturalNumber;
import someMath.NaturalNumberException;
import someMath.NaturalNumberOps;
import someMath.exceptions.MathException;

public class NaturalNumberTest
{

	@Test
	public void test() throws MathException, NaturalNumberException
	{
		NaturalNumber a = NaturalNumber.zero;
		NaturalNumber b = NaturalNumber.one;
		
		NaturalNumberOps nno = new NaturalNumberOps();
		
		NaturalNumber c = nno.add(a, b);
		
		assert(c.equals(b));
		
		NaturalNumber d = nno.add(b, c);
		
		assert(d.isGreaterThen(b));
	}
}
