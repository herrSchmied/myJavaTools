package someMath;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class MatrixRing extends Operations<Matrix<Double>>
{

	private final int minMatrixAddition = 2;
	private final int maxMatrixAddition = 50;
	private final Matrix<Double>neutrumMatrixAddition;
	
	private final int minMatrixMultiplication = 2;
	private final int maxMatrixMultiplication = 2;
	private final Matrix<Double>neutrumMatrixMultiplication;

	private final int minMatrixTransponing = 1;
	private final int maxMatrixTransponing = 1;
	private final Matrix<Double>neutrumMatrixTransponing = null;
	private final String operationTransponingName = "transponing";

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
	
	Function<List<Matrix<Double>>, Matrix<Double>> transponent = (list)->
	{
		
		if(list.size()!=1)throw new RuntimeException("Exactly one Operand is allowed!");
		if(list.get(0)==null)throw new RuntimeException("Argument is null.");
	
		Matrix<Double> matrix = list.get(0);

		int cols = matrix.getColumns();
		int rows = matrix.getRows();
		
		List<Double> neutrals = new ArrayList<>();
		for(int n=0;n<cols*rows;n++)neutrals.add(DoubleField.neutrumAddition);
		Matrix<Double> transponed;
		try
		{
								//Remember columns and rows get switched!!
			transponed = new Matrix<Double>(cols, neutrals);
			for(int col=0;col<cols;col++)
			{
				for(int row=0;row<rows;row++)
				{
					Double d = matrix.getValue(col, row);
					transponed.setValue(row, col, d);
				}
			}
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't initialize matrix transponend");
		}
		
		return transponed;
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
		
		Operation<Matrix<Double>> transpone = new Operation<>(operationTransponingName, neutrumMatrixTransponing, minMatrixTransponing, maxMatrixTransponing, transponent);
		super.setOperation(addOpp);
		super.setOperation(multiply);
		super.setOperation(transpone);
	}

	public int getMinMatrixAddition() {
		return minMatrixAddition;
	}

	public int getMaxMatrixAddition() {
		return maxMatrixAddition;
	}

	public Matrix<Double> getNeutrumMatrixAddition() {
		return neutrumMatrixAddition;
	}

	public int getMinMatrixMultiplication() {
		return minMatrixMultiplication;
	}

	public int getMaxMatrixMultiplication() {
		return maxMatrixMultiplication;
	}

	public Matrix<Double> getNeutrumMatrixMultiplication() {
		return neutrumMatrixMultiplication;
	}

	public int getMinMatrixTransponing() {
		return minMatrixTransponing;
	}

	public int getMaxMatrixTransponing() {
		return maxMatrixTransponing;
	}

	public Matrix<Double> getNeutrumMatrixTransponing() {
		return neutrumMatrixTransponing;
	}

	public String getOperationTransponingName() {
		return operationTransponingName;
	}
	
}
