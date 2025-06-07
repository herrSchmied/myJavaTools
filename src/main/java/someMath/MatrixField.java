package someMath;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import someMath.exceptions.MathException;

public class MatrixField extends Operations<Matrix<Double>>
{

	private final int rows;
	private final int columns;
	
	private final DoubleField underField = new DoubleField();
	private Matrix<Double> neutrumOfMatrixAddition;
	private Matrix<Double> neutrumOfMatrixMultiplication;

	private Integer minMatrixAddition = 2;
	private Integer maxMatrixAddition = 1000;

	/*
	 * private final Set<Operation<Matrix<Double>>> setOfOperations = new
	 * HashSet<>();
	 * 
	 * private Operation<Matrix<Double>> addOpp;
	 */

	public MatrixField(Set<Operation<Matrix<Double>>> set, int rows, int columns) throws MathException
	{
		
		super(set);
		
		if(rows<2&&columns<2)throw new MathException("Matrix has not enough columns and rows.");
		if(rows<1) throw new MathException("Matrix rows below 1! Is not possible.");
		if(columns<1) throw new MathException("Matrix columns below 1! Is not possible");

		this.rows = rows;
		this.columns = columns;
	}
	
}
