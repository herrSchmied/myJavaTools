package someMath;


import java.util.ArrayList;

import java.util.List;



import someMath.exceptions.MathException;



public class MatrixRing<O> implements Ring<Matrix<O>>
{
	
	private final int sideLength;
	private final Field<O> k;


	public MatrixRing(int n, Field<O> k) throws MathException
	{

		if(n<1)throw new MathException("Minmal side length is 1.");
		this.sideLength = n;
		this.k = k;		
	}


	@Override
	public Matrix<O> add(Matrix<O> r1, Matrix<O> r2)
	{

		if(!(r1.getRows()==r2.getRows()))throw new RuntimeException("Can't add those Matrizes.");
		if(!(r1.getColumns()==r2.getColumns()))throw new RuntimeException("Can't add those Matrizes.");
		
		int rows = r1.getRows();
		int cols = r1.getColumns();
		
		Field<O> k = r1.getField();
		Matrix<O> sum = r1.clone();
		
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				try
				{

					O d = r1.getValue(col, row);
					O d2 = r2.getValue(col, row);
					O valueSum = k.add(d, d2);
					sum = sum.setValue(col, row, valueSum);
				}
				catch(MathException me)
				{
					me.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return sum;

	}

	@Override
	public Matrix<O> sumInverse(Matrix<O> m) throws MathException
	{
		Matrix<O> output = m.clone();
		
		int rows = m.getRows();
		int cols = m.getColumns();
		
		for(int row=0;row<rows;row++)
		{

			for(int col=0;col<cols;col++)
			{
				O o = m.getValue(col, row);
				O i = k.sumInverse(o);
				output = output.setValue(col, row, i);
			}
		}
		
		return output;
	}

	@Override
	public Matrix<O> sumNeutral() throws MathException
	{
		List<O> zeros = new ArrayList<>();
		for(int m=0;m<sideLength*sideLength;m++)zeros.add(k.sumNeutral());//DoubleField Neutral of addition!

		return new Matrix<>(sideLength, zeros);
	}

	@Override
	public Matrix<O> multiply(Matrix<O> r1, Matrix<O> r2)
	{
		if(!(r1.getColumns()==r2.getRows()))throw new RuntimeException("Can't multiply those Matrizes.");
		
		List<O> valueList = new ArrayList<>();
		Matrix<O> product = null;

		try
		{
			int rows = r1.getRows();
			int cols = r2.getColumns();

			Field<O> k = r1.getField();
			O addNeutral = k.sumNeutral();
		
			for(int n=0;n<rows*cols;n++)valueList.add(addNeutral);
		
			product = new Matrix<O>(rows, valueList);

			int s = r1.getColumns();//same as r2.getRows!!
			for(int row=0;row<rows;row++)
			{
				for(int col=0;col<cols;col++)
				{

					O sum = addNeutral;
					for(int n=0;n<s;n++)
					{
					
						O o1 = r1.getValue(n, row);
						O o2 = r2.getValue(col, n);
						O valueSum = k.multiply(o1, o2);
						sum = k.add(sum, valueSum);
					}
				
					product = product.setValue(col, row, sum);
				}
			}
		}
		catch(Exception mex)
		{
			mex.printStackTrace();
		}

		return product;
	}
	
	public Matrix<O> multiplyNeutral() throws MathException
	{

		List<O> diagonalMOne = new ArrayList<>();
		for(int x=0;x<sideLength;x++)for(int y=0;y<sideLength;y++)
		{
			if(x==y)diagonalMOne.add(k.multiplyNeutral());//DoubleField Neutral of multiplication!
			else diagonalMOne.add(k.sumNeutral());//DoubleField Neutral of addition!
		}

		return new Matrix<>(sideLength, diagonalMOne);
	}

}