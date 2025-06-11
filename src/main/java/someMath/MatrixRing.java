package someMath;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class MatrixRing extends Operations<Matrix<Double>>
{

	private int minMatrixAddition = 2;
	private int maxMatrixAddition = 50;
	private Matrix<Double>neutrumMatrixAddition;
	
	private int minMatrixMultiplication = 2;
	private int maxMatrixMultiplication = 2;
	private Matrix<Double>neutrumMatrixMultiplication;

	Function<List<Matrix<Double>>, Matrix<Double>> addition = (list)-> 
	{
		
		Matrix<Double> sum;

		int rows = list.get(0).getRows();
		int cols = list.get(0).getColumns();
		
		try
		{

			Double neutrum = DoubleField.neutrumAddition;
			List<Double>neutrals = new ArrayList<>();
			for(int n=0;n<(rows*cols);n++)neutrals.add(neutrum);
			
			sum = new Matrix<>(rows, neutrals);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException();//Remember: Is this optimal?
		}
		
		for(Matrix<Double> summand: list)
		{			
			for(int col=0;col<cols;col++)
			{
				for(int row=0;row<rows;row++)
				{
					Double d = summand.getValue(col, row);
					Double d2 = sum.getValue(col, row);
					sum.setValue(col, row, (d+d2));
				}
			}
		};

		return sum;
	};
	
	Function<List<Matrix<Double>>, Matrix<Double>> multiplication = (list)-> 
	{
		
		
		Matrix<Double> product;

		int rows = list.get(0).getRows();
		int cols = list.get(0).getColumns();

		Double valueArr[][]= new Double[cols][rows];
			
		for(int c=0;c<cols;c++)
		{
			for(int r=0;r<rows;r++)
			{
				if(c==r)valueArr[c][r]= DoubleField.neutrumMultiplication;
				else valueArr[c][r] = DoubleField.neutrumAddition;
			}
		}
			
		product = new Matrix<>(valueArr);

		Matrix<Double> left = list.get(0);
		Matrix<Double> right = list.get(1);
				
		for(int col=0;col<cols;col++)
		{
			for(int row=0;row<rows;row++)
			{
				Matrix<Double> leftRow = left.getRow(row);
				Matrix<Double> rightCol = right.getColumn(col);
				
				Double sumProd = 0.0;
				for(int r=0;r<rows;r++)
				{
					Double d = leftRow.getValue(r, 0);
					Double d2 = rightCol.getValue(0, r);
					
					sumProd = sumProd + d*d2;
				}
				
				product.setValue(col, row, sumProd);
			}
		}

		return product;
	};		
	
	public MatrixRing(int n) throws MathException
	{
		
		super(new HashSet<Operation<Matrix<Double>>>());
		
		List<Double> zeros = new ArrayList<>();
		for(int m=0;m<n*n;m++)zeros.add(DoubleField.neutrumAddition);
		neutrumMatrixAddition = new Matrix<>(n, zeros);
		
		List<Double> diagonalMOne = new ArrayList<>();
		for(int x=0;x<n;x++)for(int y=0;y<n;y++)
		{
			if(x==y)diagonalMOne.add(DoubleField.neutrumMultiplication);
			else diagonalMOne.add(DoubleField.neutrumAddition);
		}
		neutrumMatrixMultiplication = new Matrix<>(n, diagonalMOne);
		
		Operation<Matrix<Double>> addOpp = new Operation<>(Operations.add, neutrumMatrixAddition,
				minMatrixAddition, maxMatrixAddition, addition);

		Operation<Matrix<Double>> multiply = new Operation<>(Operations.multiply, neutrumMatrixMultiplication, minMatrixMultiplication, maxMatrixMultiplication, multiplication);
		
		super.setOperation(addOpp);
		super.setOperation(multiply);
	}
	
}
