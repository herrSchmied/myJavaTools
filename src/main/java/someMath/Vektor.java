package someMath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import someMath.exceptions.MathException;

public class Vektor<O> extends Matrix<O>
{

	public Vektor(List<O> valueList) throws MathException
	{
		super(1, valueList);
	}
	
	public Vektor(O[] valueArray) throws MathException
	{
		this(Arrays.asList(valueArray));
	}

	public O getValue(int row) throws MathException
	{
		return super.getValue(0, row);
	}

	public int getRows()
	{
		return super.getRows();
	}
	
	public void setValue(int row, O value) throws MathException
	{
		super.setValue(0, row, value);
	}
	public Vektor<O> clone()
	{
		
		O o = null;
		try
		{
			o = this.getValue(0, 0);
		}
		catch(MathException me)
		{
			me.printStackTrace();
		}
		
		List<O> list = new ArrayList<>();
		for(int n=0;n<super.getRows();n++)list.add(o);
		
		Vektor<O> klon = null;
		
		try
		{
		
			klon = new Vektor<O>(list);
			
			for(int row=0;row<super.getRows();row++)
			{
				O o2 = this.getValue(row);
				klon.setValue(row, o2);
			}

		}
		catch(MathException e)
		{
			e.printStackTrace();
		}
		
		return klon;
	}
}