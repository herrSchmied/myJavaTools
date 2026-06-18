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
		
		RationalNumber rn3 = 
				new RationalNumber(true, new NaturalNumber(9), new NaturalNumber(88), new NaturalNumber(88));

		RationalNumber rn4 = 
				new RationalNumber(true, new NaturalNumber(5), new NaturalNumber(10), new NaturalNumber(2));
	
		System.out.println(rn3 + " vs " + rn4);
		
		assert(rn3.equals(rn4));

		RationalNumber rn5 = 
				new RationalNumber(false, new NaturalNumber(9), new NaturalNumber(11), new NaturalNumber(88));
		RationalNumber rn6 = 
				new RationalNumber(false, new NaturalNumber(8), new NaturalNumber(99), new NaturalNumber(88));

		System.out.println(rn5 + " vs " + rn6);

		assert(rn5.equals(rn6));
		
		RationalNumber rn7 = 
				new RationalNumber(false, new NaturalNumber(9), new NaturalNumber(11), new NaturalNumber(77));
		RationalNumber rn8 = 
				new RationalNumber(false, new NaturalNumber(8), new NaturalNumber(88), new NaturalNumber(77));

		System.out.println(rn7 + " vs " + rn8);

		assert(rn7.equals(rn8));

		RationalNumber rn9 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn10 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));


		RationalNumber sum = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(2), new NaturalNumber(7*9));


		System.out.println(rn9 + " + " + rn10 + " = " + sum);

		assert(sum.equals(rn9.add(rn10)));
		
		RationalNumber rn11 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn12 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		sum = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(2), new NaturalNumber(7*9));

		System.out.println(rn11 + " + " + rn12 + " = " + sum);

		assert(sum.equals(rn11.add(rn12)));

		RationalNumber rn13 = 
				new RationalNumber(true, new NaturalNumber(2), new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn14 = 
				new RationalNumber(false, new NaturalNumber(3), new NaturalNumber(1), new NaturalNumber(7));

		sum = 
				new RationalNumber(false, NaturalNumber.one, new NaturalNumber(2), new NaturalNumber(7*9));

		System.out.println(rn13 + " + " + rn14 + " = " + sum);

		assert(sum.equals(rn13.add(rn14)));
		
		RationalNumber rn15 = 
				new RationalNumber(false, new NaturalNumber(2), new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn16 = 
				new RationalNumber(true, new NaturalNumber(3), new NaturalNumber(1), new NaturalNumber(7));

		sum = 
				new RationalNumber(true, NaturalNumber.one, new NaturalNumber(2), new NaturalNumber(7*9));

		System.out.println(rn15 + " + " + rn16 + " = " + sum);

		assert(sum.equals(rn15.add(rn16)));
		
		RationalNumber rn17 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn18 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		sum = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(9+7), new NaturalNumber(7*9));

		System.out.println(rn17 + " + " + rn18 + " = " + sum);

		assert(sum.equals(rn17.add(rn18)));

		RationalNumber rn19 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn20 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		sum = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(9+7), new NaturalNumber(7*9));

		System.out.println(rn19 + " + " + rn20 + " = " + sum);

		assert(sum.equals(rn19.add(rn20)));
		
		RationalNumber rn21 = new RationalNumber(1, -1, 3);
		RationalNumber rn22 = new RationalNumber(2, 3);
		
		System.out.println(rn21 + " vs " + rn22);

		RationalNumber rn23 = new RationalNumber(4, -20, 5);
		RationalNumber rn24 = new RationalNumber(0, -3);
		
		System.out.println(rn23 + " vs " + rn24);

		assert(rn23.equals(rn24));
		
		RationalNumber rn25 = new RationalNumber(-4, -20, 5);
		RationalNumber rn26 = new RationalNumber(-8);
		
		System.out.println(rn25 + " vs " + rn26);

		assert(rn25.equals(rn26));

		RationalNumber rn27 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		RationalNumber rn28 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		RationalNumber product = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7*9));


		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));
		
		rn27 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		rn28 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		product = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7*9));

		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));

		rn27 = 
				new RationalNumber(true, new NaturalNumber(2), new NaturalNumber(1), new NaturalNumber(9));
		rn28 = 
				new RationalNumber(false, new NaturalNumber(3), new NaturalNumber(1), new NaturalNumber(7));

		product = 
				new RationalNumber(false, new NaturalNumber(19*22), new NaturalNumber(7*9));

		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));
		
		rn27 = 
				new RationalNumber(false, new NaturalNumber(2), new NaturalNumber(1), new NaturalNumber(9));
		rn28 = 
				new RationalNumber(true, new NaturalNumber(3), new NaturalNumber(1), new NaturalNumber(7));

		//Product already there
		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));
		
		rn27 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		rn28 = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		product = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7*9));

		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));

		rn27 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(9));
		rn28 = 
				new RationalNumber(false, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7));

		product = 
				new RationalNumber(true, NaturalNumber.zero, new NaturalNumber(1), new NaturalNumber(7*9));

		System.out.println(rn27 + " * " + rn28 + " = " + product);

		assert(product.equals(rn27.multiply(rn28)));

		
		assert(!rn.isLargerThan(rn2));
		assert(rn3.isLargerThan(rn));
		assert(rn3.isLargerThan(rn2));
		assert(rn3.isLargerThan(rn5));
		assert(rn3.isLargerThan(rn6));
		assert(rn5.isLargerThan(rn7));
		assert(rn5.isLargerThan(rn8));


	}
}
