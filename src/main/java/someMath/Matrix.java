package someMath;

import java.util.ArrayList;
import java.util.*;
import java.util.function.BiConsumer;

import someMath.exceptions.MathException;

//TODO: exclude operations try tests(Old&&New) and later transfer operations to matrixOps.
//It is important that the values of Type E have a good overwritten toString Method. 
//and the Type E must overwrite equals Too.
//I'm looking for a way to enforce that E is of Type: "Mathematical Field."
public class Matrix<E, O extends Operations<E>>
{

	private final List<E> valList;
	private final int rows;
	private final int columns;
	private final Operations<E> mOps;//Remember:Mathematischer Körper
	private final boolean isQuadratic;
	
	public Matrix(Operations<E> mOps, int rows, List<E> values)
	{
	    int size = values.size();
	    int cols =(int)(size/rows);
	    
		if(cols*rows!=values.size()) throw new IllegalArgumentException("Values don't Fit");
		if(values.remove(null)) throw new IllegalArgumentException("Please no Null values.");
		this.rows = rows;
		this.columns = cols;
		this.mOps = mOps;
		isQuadratic = (rows==columns);
		
		/*
		 * Cannot create a Array of Generic Type: "MultiplyableAndAddable<E>[][]" 
		 * The line below causes the Suppress Warning. How can i get rid of This?
		 */
		valList = new ArrayList<>();
		
		BiConsumer<Integer, Integer> bic = (n,m)-> valList.add(values.get(n*columns+m));
		walkThrouMatrix(this, bic);
	}
	
	public Matrix(Operations<E> mOps, E[][] valArr)
	{
		
		if(valArr==null)throw new IllegalArgumentException("Can't create Matrix with null Array.");

		valList = new ArrayList<>();
		this.columns = valArr[0].length;
		this.rows = valArr.length;
		int homogeneLengthStndrt = 1;
		for(int n=0;n<valArr.length;n++)
		{
			int l = valArr[n].length;
			if(n==0)homogeneLengthStndrt = l;
			if(l!=homogeneLengthStndrt)throw new IllegalArgumentException("Row's aren't all of same Length.");
			
			for(int m=0;m<l;m++)
			{
				if(valArr[n][m]==null)throw new IllegalArgumentException("Null Values at row: "+n+" column:"+m);
				else valList.set(n*columns+m, valArr[n][m]);
			}
		}
		this.mOps = mOps;
		isQuadratic = (rows==columns);
	}

	public int getRows()
	{ return rows;}
	
	public int getColumns()
	{ return columns;}
	
	public E getValue(int row, int column)
	{
		return valList.get(row*columns+column);
	}

	public Matrix<E, O> getColumn(int column)
	{
		
		List<E> list = new ArrayList<>();
		
		for(int i=0;i<rows;i++)list.add(getValue(i, column));
		
		Matrix<E, O> outputRowMatrix = new Matrix<E, O>(mOps, rows, list);
		
		return outputRowMatrix;
	}
	
	public void setColumn(List<E> list, int column)
	{
		for(int i=0;i<rows;i++)valList.set(i*columns +column, list.get(i));
	}
	
	public void setColumn(Matrix<E, O> input, int column)
	{
		for(int i=0;i<rows;i++)valList.set(i*columns + column, input.getValue(i,0));
	}

	public Matrix<E, O> getRow(int n)
	{
		List<E> list = new ArrayList<>();
		
		for(int i=0;i<columns;i++)list.add(getValue(n, i));
		
		Matrix<E, O> outputRowMatrix = new Matrix<E, O>(mOps, 1, list);
		
		return outputRowMatrix;
	}
	
	public void setRow(List<E> list, int row)
	{
		for(int i=0;i<columns;i++)valList.set(row*columns + i, list.get(i));
	}

	public void setRow(Matrix<E, O> input, int row)
	{
		for(int i=0;i<columns;i++)valList.set(row*columns+i, input.getValue(0, i));
	}

	public boolean isQuadratic() {return isQuadratic;}
	
	public Operations<E> getFieldOps()//Remember this new Method!!!!!!
	{
		return mOps;
	}

	//It is important that the values of Type E have a good overwritten toString Method.
	public String toString()
	{
		
		String output = "";
		
		int [] longestValue = new int[1];
		longestValue[0]= 1;
		BiConsumer<Integer, Integer> bic = (n,m)->
		{
			int valueLength = getValue(n, m).toString().length();
			if(valueLength>longestValue[0])longestValue[0]=valueLength; 
		};
		walkThrouMatrix(this, bic);
		
		for(int n=0;n<rows;n++)
		{
			for(int m=0;m<columns;m++)
			{
				
				int l = getValue(n, m).toString().length();
				int d = longestValue[0]-l+1;
				String whiteSpace = StringManipulation.customMonoRepeatChar(' ',d);
				
				output = output.concat(whiteSpace+getValue(n, m).toString());
			}
			output = output.concat("\n");
		}

		return output;
	}

	public Class<E> getEnclosedType()
	{
		
		E e = this.getValue(0, 0);
		
		return (Class<E>) e.getClass();
	}
	
	public int hashCode()
	{
		return Objects.hash(valList);
	}
	
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		
	    if (!(obj instanceof Matrix)) return false;
	    
	    @SuppressWarnings("rawtypes")
		Matrix other = (Matrix)obj;//TODO: Must be raw?
	    if(!(other.getEnclosedType()== this.getEnclosedType()))
	    {
	    	
	    	System.out.println("Matrixes aren't Enclosing same Type.");
	    	return false;
	    }
	    
	    
	    if(other.getRows()!=this.getRows())return false;
	    if(other.getColumns()!=this.getColumns())return false;
	    
	    boolean[] check = new boolean[1];
	    check[0]= true;
	    BiConsumer<Integer, Integer>bic=(n,m)->
	    {
	    	if(!other.getValue(n, m).equals(this.getValue(n, m)))check[0] = false;
	    };
	    
	    walkThrouMatrix(this, bic);
	    
	    return check[0];
	}

	
	public static <O, T extends Operations<O>> void walkThrouMatrix(Matrix<O, T> matrix, BiConsumer<Integer, Integer> bic)
	{
		
		for(int n=0;n<matrix.getRows();n++)
		{
			for(int m=0;m<matrix.getColumns();m++)
			{
				bic.accept(n,m);
			}
		}
	}
}