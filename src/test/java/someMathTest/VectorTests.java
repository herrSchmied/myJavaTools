package someMathTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import someMath.DoubleField;
import someMath.FieldTuple;
import someMath.VecFieldTupStuff;
import someMath.VectorSpaceForFieldTuples;
import someMath.exceptions.MathException;

public class VectorTests
{
	
	Double prettySmall = Math.pow(10, -6);

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
		FieldTuple<Double> vektor = new FieldTuple<>(list);
		VecFieldTupStuff<Double> vfts = new VecFieldTupStuff<>(new DoubleField());
		Double product = vfts.scalarProduct(vektor, vektor);
		
		assert(product.equals(3.0));
		
		assert(vektor.getValue(0).equals(1.0));
		assert(vektor.getValue(1).equals(1.0));
		assert(vektor.getValue(2).equals(1.0));
	}

	@Test
	public void scaleTest() throws MathException
	{

		VectorSpaceForFieldTuples<Double, FieldTuple<Double>> vft = new VectorSpaceForFieldTuples<>(new DoubleField());

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

		FieldTuple<Double> scaledVektor = vft.scalarMultiplication(scale, original);
		System.out.println("scaled:\n" + scaledVektor);

		assert(scaledVektor.equals(scaleCheckVektor));

		FieldTuple<Double> backToTheOriginal = vft.scalarMultiplication((1.0/scale), scaleCheckVektor);
		System.out.println("backToTheoriginal:\n" + backToTheOriginal);

		assert(scaledVektor.equals(scaleCheckVektor));
		assert(backToTheOriginal.equals(original));
		
		assert(Math.abs(backToTheOriginal.getValue(0)-original.getValue(0))<prettySmall);
		assert(Math.abs(backToTheOriginal.getValue(1)-original.getValue(1))<prettySmall);
		assert(Math.abs(backToTheOriginal.getValue(2)-original.getValue(2))<prettySmall);
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
