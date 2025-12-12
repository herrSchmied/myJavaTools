package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import someMath.exceptions.MathException;

public class Vektor<O> extends Matrix<O>
{

	public static <O> List<O> getDefaultValueList(int n, O defaultValue)
	{
		List<O> list = new ArrayList<>();
		for(int m=0;m<n;m++)
		{
			list.add(defaultValue);
		}
		
		return list;
	}

	public Vektor(List<O> valueList) throws MathException
	{
		super(1, valueList);
	}
	
	public Vektor(O[] valueArray) throws MathException
	{
		this(Arrays.asList(valueArray));
	}
	
	public Vektor(int rows, O defaultValue) throws MathException
	{
		super(1, getDefaultValueList(rows, defaultValue));
	}

	public O getValue(int row) throws MathException
	{
		return super.getValue(0, row);
	}

	public int getRows()
	{
		return super.getRows();
	}
	
	public Vektor<O> setValue(int row, O value) throws MathException
	{
		
		if(row>super.getRows()-1||row<0)throw new MathException("Row out of Bounds.");
		if(value==null)throw new MathException("Can't accept Null-Value.");
		
		List<O> list = new ArrayList<>();
		
		for(int n=0;n<super.getRows();n++)
		{
			O o;
			if(n==row)o = value;
			else o = this.getValue(n);
			list.add(o);
		}

		return new Vektor<O>(list);
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
	
	public int hashCode()
	{
			
		int wert = 0;

		int rows = this.getRows();

		for(int row=0;row<rows;row++)
		{
		
			try
			{
				O o = this.getValue(row);
				wert += o.hashCode()+row;
			}
			catch(MathException mex)
			{
				mex.printStackTrace();
			}
		}

		return Objects.hash(wert);
	}

	public boolean equals(Object other)
	{

		if(!(other instanceof Vektor))return false;
		Vektor v = (Vektor)other;
		if(!(this.getRows()==v.getRows()))return false;
		int rows = this.getRows();
		
		try
		{
			boolean sameType = this.getValue(0).getClass().equals(v.getValue(0).getClass());
			if(!sameType)return false;
			
			for(int row=0;row<rows;row++)
			{
				Object a = this.getValue(row);
				Object b = this.getValue(row);
				
				if(!a.equals(b))return false;
			}
		}
		catch(MathException e)
		{
			e.printStackTrace();
		}

		return true;
	}
}