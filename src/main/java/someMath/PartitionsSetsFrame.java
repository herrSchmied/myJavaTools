package someMath;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import someMath.exceptions.MathException;

@Getter
public class PartitionsSetsFrame
{
	
	private final int minSizeOfSummand;
	private final int nrOfSummands;
	private final int sum;

	public PartitionsSetsFrame(int minSizeOfSummand, int nrOfSummands, int sum) throws MathException
	{
		this.minSizeOfSummand = minSizeOfSummand;
		this.nrOfSummands = nrOfSummands;
		this.sum = sum;
	}

	public static boolean validator(PartitionsSetsFrame frame) throws MathException
	{
		
		int minSizeOfSummand = frame.getMinSizeOfSummand();
		int nrOfSummands = frame.nrOfSummands;
		int sum = frame.getSum();
		
		if(minSizeOfSummand<=0||nrOfSummands<=0||sum<=0) throw new MathException("At least one of the Arguments is Zero or Below.");
		if(minSizeOfSummand*nrOfSummands>sum) return false;

		return true;
	}

	public static boolean validator(int minSizeOfSummand, int nrOfSummands, int sum)throws MathException
	{
		
		PartitionsSetsFrame frame = new PartitionsSetsFrame(minSizeOfSummand, nrOfSummands, sum);
		
		return validator(frame);
	}

}
