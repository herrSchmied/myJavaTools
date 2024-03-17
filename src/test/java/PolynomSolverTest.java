

import org.junit.jupiter.api.Test;

import someMath.CollectionException;
import someMath.DivisionByZeroException;
import someMath.NaturalNumberException;
import someMath.RNumException;
import someMath.RationalNumber;
import someMath.SmallNatural;

import static someMath.SmallNatural.*;

public class PolynomSolverTest 
{
	
	private static final double prettySmall = Math.pow(10, -11);

	@Test
	public void testSolver() throws NaturalNumberException, RNumException, DivisionByZeroException, CollectionException, CloneNotSupportedException
	{
		
		RationalNumber two = new RationalNumber(2, 1);
		
		/*TODO:
		//equals x²+2 = 0.
		Set<ComplexNrDouble> set = PolynomSolver.quadraticSolver(one, zero, two);
		assert(set.size()==2);
		ComplexNrDouble z = CollectionManipulation.catchRandomElementOfSet(set);
		
		assert(Math.abs(z.getImaginaryPart())-Math.sqrt(2)<=prettySmall);
		
		System.out.println(set);
		*/
	}
}
