package someMath;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javafx.util.Pair;
import someMath.exceptions.MathException;
import someMath.simplePrimeTools.ToolsForSmallPrimes;

public class FiniteField implements Field<Long>
{

	private final int nrOfElements;
	
	public FiniteField(int nrOfElements, Path primeListFilePath) throws ClassNotFoundException, MathException, IOException
	{

		ToolsForSmallPrimes tfsp = new ToolsForSmallPrimes(primeListFilePath.toString(), 2000);
		if(nrOfElements>tfsp.getLargestPrime())throw new MathException("Nr of Elements exceeds largestPrime given by TFSP Object.");

		Pair<Long[], Long[]> primeFactors = tfsp.factorize(nrOfElements);

		Long[] primes = primeFactors.getKey();
		if(primes.length!=1)throw new MathException("Finite Field with that Nr. of Elements is "
				+ "not possible. It needs to be a prime as basis raised by an integer bigger or"
				+ " equal One.");
		
		this.nrOfElements = nrOfElements;
	}

	@Override
	public Long add(Long r1, Long r2) throws MathException
	{
		return reduce(reduce(r1)+reduce(r2));
	}

	@Override
	public Long multiply(Long r1, Long r2) throws MathException
	{
		return reduce(reduce(r1)*reduce(r2));
	}

	@Override
	public Long negate(Long r1) throws MathException
	{
		return (nrOfElements-r1);
	}

	@Override
	public Long zero() throws MathException
	{
		return (long)0;
	}

	@Override
	public Long one() throws MathException
	{

		return (long)1;
	}

	@Override
	public Long inverse(Long a1) throws MathException
	{
		
		//uses the Fermat's little theorem
		long output = (long)1;
		for(int n=0;n<nrOfElements-2;n++)
		{
			output = reduce(output*a1);
		}
		
		return output;
	}

	public Long reduce(Long l)
	{
		return l%nrOfElements;
	}
}
