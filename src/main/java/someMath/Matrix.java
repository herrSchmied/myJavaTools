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

	private final int rows;
	private final int columns;
	private final boolean isQuadratic;
	private final O[][] valueArr;

	public Matrix(O[][] valueArr)
	{
			
	    
		this.rows = valueArr[0].length;
		this.columns = valueArr.length;

		isQuadratic = (rows==columns);
		this.valueArr = (O[][]) valueArr;
	}
	
	public Matrix(int rows, List<O> valueList) throws MathException
	{
		if(rows<1)throw new MathException("To few rows.");
		if(valueList.size()%rows!=0)throw new MathException("Matrix can not be initiated due to nr. of Values they don't fit");
		this.rows = rows;
		this.columns = valueList.size()/rows;
		isQuadratic = (rows==columns);
		
		Object [][] arrayOfValues = new Object[columns][rows];
		
		for(int n=0;n<valueList.size();n++)
		{
			int col = n%columns;
			int row = (n-col)/columns;
			
			arrayOfValues[col][row] = valueList.get(n);
		}
		
	
		valueArr = (O[][])arrayOfValues;
	}

	public int getRows()
	{ return rows;}
	
	public int getColumns()
	{ return columns;}
	
	public O getValue(int column, int row)
	{
		return valueArr[column][row];
	}

	public void setValue(int column, int row, O o)
	{
		valueArr[column][row] = o;
	}

	public Matrix<O> getColumn(int column)
	{
		
		Object[][] valueArr = new Object[1][rows];
		List<O> list = new ArrayList<>();
		
		for(int i=0;i<rows;i++)valueArr[0][i]=getValue(column, i);
		
		Matrix<O> outputRowMatrix = new Matrix(valueArr);
		
		return outputRowMatrix;
	}
	
	public void setColumn(List<O> list, int column)
	{
		for(int i=0;i<rows;i++)valueArr[column][i]= list.get(i);
	}
	
	public Matrix<O> getRow(int row)
	{
		Object[][] valueArr = new Object[columns][1];
		
		for(int i=0;i<columns;i++)valueArr[i][0]=getValue(i, row);
		
		Matrix<O> outputRowMatrix = new Matrix(valueArr);
		
		return outputRowMatrix;
	}
	
	public void setRow(List<O> list, int row)
	{
		for(int i=0;i<columns;i++)valueArr[i][row]=list.get(i);
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
				
				int l = getValue(m, n).toString().length();
				int d = longestValue[0]-l+1;
				String whiteSpace = StringManipulation.customMonoRepeatChar(' ',d);
				
				output = output.concat(whiteSpace+getValue(m, n).toString());
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
		return Objects.hash(valueArr);
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
		
		for(int column=0;column<columns;column++)
		{
			for(int row=0;row<rows;row++)
			{
				
				Point p = new Point(column, row);
				O value = getValue(column, row);
				bic.accept(p, value);
			}
		}
	}
}