package someMathTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.SmallTools;
import someMath.FieldTuple;
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
		
		Vektorraum<Double> vr = new Vektorraum<>(3, new DoubleField()); 
		List<Double> list = Arrays.asList(1.0, 1.0, 1.0);
		FieldTuple<Double> vektor = new FieldTuple<>(list);
		Double product = vr.scalarProduct(vektor, vektor);
		
		assert(product.equals(3.0));
		
		assert(vektor.getValue(0).equals(1.0));
		assert(vektor.getValue(1).equals(1.0));
		assert(vektor.getValue(2).equals(1.0));
	}

	@Test
	public void scaleTest() throws MathException
	{

		Vektorraum<Double> vr = new Vektorraum<>(3, new DoubleField()); 

		Double scale = 1.5;
		
		List<Double> list = createListOfDoubles(3,0,9);
		FieldTuple<Double> original = new FieldTuple<>(list);
		System.out.println("original:\n" + original);

		Double value0 = list.get(0);
		Double value1 = list.get(1);
		Double value2 = list.get(2);
		
		List<Double> list2 = 
				Arrays.asList(value0*scale, value1*scale, value2*scale);
		FieldTuple<Double> scaleCheckVektor = new FieldTuple<>(list2);
		System.out.println("scaleCheck:\n" + scaleCheckVektor);

		FieldTuple<Double> scaledVektor = vr.scaling(scale, original);
		System.out.println("scaled:\n" + scaledVektor);

		assert(scaledVektor.equals(scaleCheckVektor));

		FieldTuple<Double> backToTheOriginal = vr.scaling((1/scale), scaleCheckVektor);
		System.out.println("backToTheoriginal:\n" + backToTheOriginal);

		assert(scaledVektor.equals(scaleCheckVektor));
		assert(backToTheOriginal.equals(original));
		
		assert(backToTheOriginal.getValue(0).equals(original.getValue(0)));
		assert(backToTheOriginal.getValue(1).equals(original.getValue(1)));
		assert(backToTheOriginal.getValue(2).equals(original.getValue(2)));
		assert(scaledVektor.getValue(0).equals(value0*scale));
		assert(scaledVektor.getValue(1).equals(value1*scale));
		assert(scaledVektor.getValue(2).equals(value2*scale));

	}
	
	@Test
	public void cloneTest() throws MathException
	{
		List<Double> list = Arrays.asList(1.0, 2.0, 3.0);
		FieldTuple<Double> original = new FieldTuple<>(list);
		
		FieldTuple<Double> clone = original.clone();
		
		assert(!(clone==original));
		assert(clone.equals(original));
	}

}
