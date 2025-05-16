package someMath;

import java.awt.Point;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import someMath.exceptions.MathException;

//TODO: exclude operations try tests(Old&&New) and later transfer operations to matrixOps.
//It is important that the values of Type E have a good overwritten toString Method. 
//and the Type E must overwrite equals Too.
//I'm looking for a way to enforce that E is of Type: "Mathematical Field."
public class Matrix<O>
{

	private final List<O> valList;
	private final int rows;
	private final int columns;
	private final boolean isQuadratic;

	public Matrix(int rows, List<O> values)
	{
				
	    int size = values.size();
	    int cols =(int)(size/rows);
	    
		if(cols*rows!=values.size()) throw new IllegalArgumentException("Values don't Fit");
		if(values.remove(null)) throw new IllegalArgumentException("Please no Null values.");
		this.rows = rows;
		this.columns = cols;

		isQuadratic = (rows==columns);
		
		/*
		 * Cannot create a Array of Generic Type: "MultiplyableAndAddable<E>[][]" 
		 * The line below causes the Suppress Warning. How can i get rid of This?
		 */
		valList = new ArrayList<>();
		
		BiConsumer<Point, O> bic = (p,v)-> valList.add(v);
		walkThrouMatrix(bic);
	}
	
	public Matrix(O[][] valArr)
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

		isQuadratic = (rows==columns);
	}

	public int getRows()
	{ return rows;}
	
	public int getColumns()
	{ return columns;}
	
	public O getValue(int row, int column)
	{
		return valList.get(row*columns+column);
	}

	public Matrix<O> getColumn(int column)
	{
		
		List<O> list = new ArrayList<>();
		
		for(int i=0;i<rows;i++)list.add(getValue(i, column));
		
		Matrix<O> outputRowMatrix = new Matrix<O>(rows, list);
		
		return outputRowMatrix;
	}
	
	public void setColumn(List<O> list, int column)
	{
		for(int i=0;i<rows;i++)valList.set(i*columns +column, list.get(i));
	}
	
	public void setColumn(Matrix<O> input, int column)
	{
		for(int i=0;i<rows;i++)valList.set(i*columns + column, input.getValue(i,0));
	}

	public Matrix<O> getRow(int n)
	{
		List<O> list = new ArrayList<>();
		
		for(int i=0;i<columns;i++)list.add(getValue(n, i));
		
		Matrix<O> outputRowMatrix = new Matrix<O>(1, list);
		
		return outputRowMatrix;
	}
	
	public void setRow(List<O> list, int row)
	{
		for(int i=0;i<columns;i++)valList.set(row*columns + i, list.get(i));
	}

	public void setRow(Matrix<O> input, int row)
	{
		for(int i=0;i<columns;i++)valList.set(row*columns+i, input.getValue(0, i));
	}

	public boolean isQuadratic() {return isQuadratic;}
	

	//It is important that the values of Type E have a good overwritten toString Method.
	public String toString()
	{
		
		String output = "";
		
		int [] longestValue = new int[1];
		longestValue[0]= 1;
		BiConsumer<Point, O> bic = (p,v)->
		{
			int x = p.x;
			int y = p.y;
			
			int valueLength = getValue(x, y).toString().length();
			if(valueLength>longestValue[0])longestValue[0]=valueLength; 
		};
		walkThrouMatrix(bic);
		
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

	public Class<O> getEnclosedType()
	{
		
		O o = this.getValue(0, 0);
		
		return (Class<O>) o.getClass();
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
	    BiConsumer<Point, O> bic=(p, v)->
	    {
	    	if(!other.getValue(p.x, p.y).equals(this.getValue(p.x, p.y)))check[0] = false;
	    };
	    
	    walkThrouMatrix(bic);
	    
	    return check[0];
	}

	
	public void walkThrouMatrix(BiConsumer<Point, O> bic)
	{
		
		for(int n=0;n<getRows();n++)
		{
			for(int m=0;m<getColumns();m++)
			{
				
				Point p = new Point(m, n);
				O value = getValue(m, n);
				bic.accept(p, value);
			}
		}
	}
}