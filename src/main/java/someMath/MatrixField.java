package someMath;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class MatrixField<O, E extends Operations<O>> extends Operations<Matrix<O>>
{

	private final int rows;
	private final int columns;
	
	private final O neutrumOfUnderFieldAddition;
	private final O neutrumOfUnderFieldMultiplication;
	private Matrix<O> neutrumOfMatrixAddition;
	private Matrix<O> neutrumOfMatrixMultiplication;

	private Integer minMatrixAddition = 2;
	private Integer maxMatrixAddition = 1000;

	private final Set<Operation<Matrix<O>>> setOfOperations = new HashSet<>();

	private Operation<Matrix<O>> addOpp;

	private MatrixField(Set<Operation<Matrix<O>>> set, int rows, int columns, O neutrumOfUnderFieldAddition
			, O neutrumOfUnderFieldMultiplication) throws MathException
	{
		
		super(set);
		
		if(rows<2&&columns<2)throw new MathException("Matrix has not enough columns and rows.");
		
		this.rows = rows;
		this.columns = columns;
		if(neutrumOfUnderFieldAddition==null)throw new MathException("Neutrum of underlying field (addition) of the Matrix can't be null.");
		if(neutrumOfUnderFieldMultiplication==null)throw new MathException("Neutrum of underlying field (multiplication) of the Matrix can't be null.");

		this.neutrumOfUnderFieldAddition = neutrumOfUnderFieldAddition;
		this.neutrumOfUnderFieldMultiplication = neutrumOfUnderFieldMultiplication;
		
		List<O> neutrals = new ArrayList<>();
		int l = columns*rows;
		for(int n=0;n<l;n++)neutrals.add(neutrumOfUnderFieldAddition);
		neutrumOfMatrixAddition = new Matrix(rows, neutrals);
		
		addOpp = new Operation(Operations.add, neutrumOfMatrixAddition, minMatrixAddition,
				maxMatrixAddition, getAdditionLambda());

		setOfOperations.add(addOpp);
	}
	
	private Function<List<Matrix<O>>, Matrix<O>> getAdditionLambda()
	{
		Function<List<Matrix<O>>, Matrix<O>> addition = (list)-> 
		{
		
			Matrix<O> sum = new Matrix(rows,null);
		
			//No specific order approach commutative or not!?
			for(Matrix<O> summand: list)
			{
				O o = summand.getValue(0, 0);
			}
			
			return sum;
		};
		
		return addition;
	}
}
