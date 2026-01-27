package someMathTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.SmallTools;
import someMath.Vektor;
import someMath.Vektorraum;
import someMath.exceptions.MathException;

public class VectorTests
{

	//Not capable of cast to Integer without loss.
	//It' different than in the other class(MatrixTests)
	private List<Double> createListOfDoubles(int n, int max, int min)
	{

		List<Double> list = new ArrayList<>();

		int span = max-min;
		for(int m=0;m<n;m++)
		{
			Double z = Math.floor((Math.random()*span)+min);
			list.add(z);
		}

		return list;
	}

	@Test
	public void scalarProduct() throws MathException
	{
		
		List<Double> list = Arrays.asList(1.0, 1.0, 1.0);
		Vektor<Double> vektor = new Vektor<>(list);
		Double product = Vektorraum.scalarProduct.apply(vektor, vektor);

		assert(product.equals(3.0));
		
		assert(vektor.getValue(0).equals(1.0));
		assert(vektor.getValue(1).equals(1.0));
		assert(vektor.getValue(2).equals(1.0));
	}

	@Test
	public void scaleTest() throws MathException
	{

		List<Double> list = createListOfDoubles(3,0,9);
		Vektor<Double> original = new Vektor<>(list);
		System.out.println("original:\n" + original);

		Double scaledValue0 = list.get(0);
		Double scaledValue1 = list.get(1);
		Double scaledValue2 = list.get(2);
		
		List<Double> list2 = 
				Arrays.asList(scaledValue0*1.5, scaledValue1*1.5, scaledValue2*1.5);
		Vektor<Double> scaleCheck = new Vektor<>(list2);
		System.out.println("scaleCheck:\n" + scaleCheck);

		Vektor<Double> scaled = Vektorraum.scaling.apply(1.5, original);
		System.out.println("scaled:\n" + scaled);

		assert(scaled.equals(scaleCheck));

		Vektor<Double> backToTheOriginal = Vektorraum.scaling.apply((2.0/3), scaleCheck);
		System.out.println("backToTheoriginal:\n" + backToTheOriginal);

		assert(scaled.equals(scaleCheck));
		assert(backToTheOriginal.equals(original));
		
		assert(backToTheOriginal.getValue(0).equals(scaledValue0));
		assert(backToTheOriginal.getValue(1).equals(scaledValue1));
		assert(backToTheOriginal.getValue(2).equals(scaledValue2));
		assert(scaled.getValue(0).equals(scaledValue0*1.5));
		assert(scaled.getValue(1).equals(scaledValue1*1.5));
		assert(scaled.getValue(2).equals(scaledValue2*1.5));

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
