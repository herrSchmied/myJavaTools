package someMath;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import someMath.exceptions.MathException;

public class AnotherPartitionsTest
{

	@Test
	public void test() throws MathException
	{
		Set<List<Integer>> set = Partitions.partitionsOfNAsLists(3);
		
		System.out.println(set);
	}
}
