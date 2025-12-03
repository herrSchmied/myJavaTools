package someMathTest;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.Vektor;
import someMath.Vektorraum;
import someMath.exceptions.MathException;

public class VectorTests
{
	
	@Test
	public void scaleTest() throws MathException
	{
		
		List<Double> list = Arrays.asList(1.0, 2.0, 3.0);
		List<Double> list2 = Arrays.asList(1.5, 3.0, 4.5);
		Vektor<Double> original = new Vektor<>(list);
		Vektor<Double> scaled = Vektorraum.scaling.apply(1.5, original);
		Vektor<Double> scaleCheck = new Vektor<>(list2);
		Vektor<Double> backToTheOriginal = Vektorraum.scaling.apply((2.0/3), scaleCheck);
		
		assert(scaled.equals(scaleCheck));
		assert(backToTheOriginal.equals(original));
	}
	
	@Test
	public void cloneTest() throws MathException
	{
		List<Double> list = Arrays.asList(1.0, 2.0, 3.0);
		Vektor<Double> original = new Vektor<>(list);
		
		Vektor<Double> clone = original.clone();
		
		assert(!(clone==original));
		assert(clone.equals(original));
	}

}
