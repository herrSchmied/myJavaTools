package someMath;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import java.util.List;

import someMath.exceptions.MathException;

public class NaturalNumberTest
{

	@Test
	public void test() throws MathException, NaturalNumberException
	{
		NaturalNumber a = NaturalNumber.zero;
		NaturalNumber b = NaturalNumber.one;
		
		NaturalNumberOps nno = new NaturalNumberOps();
		
		List<NaturalNumber> operands = new ArrayList<>();
		operands.add(a);
		operands.add(b);
		NaturalNumber c = nno.add(operands);
		
		assert(c.equals(b));
		
		operands.clear();
		operands.add(b);
		operands.add(c);
		NaturalNumber d = nno.add(operands);
		
		assert(d.isGreaterThen(b));
	}
}
