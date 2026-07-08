package someMath;

import java.awt.Point;
import java.util.*;
import java.util.function.BiConsumer;

import static CollectionTools.CollectionManipulation.*;


import someMath.exceptions.MathException;



//TODO: It is important that the values of Type O have overwritten 
//toString Method and the Type O must overwrite equals Too. I'm looking 
//for a way to enforce that O is of Type: "Mathematical Field."

public class Matrix<O> implements Cloneable
{

	private final int rows;
	private final int columns;
	private final boolean isQuadratic;
	private final O[][] valueArr;

	//In All Related Code first Columns than Rows!!!
	@SuppressWarnings("unchecked")
	public Matrix(int columns, int rows, O monoValue)
	{
		
		this.columns = columns;
		this.rows = rows;
		this.isQuadratic = (rows==columns);
		
		Object [][] arrayOfValues = new Object[columns][rows];
		
		for(int row=0;row<rows;row++)
		{
			for(int col=0;col<columns;col++)
			{
				arrayOfValues[col][row]= monoValue;
			}
		}
		
		valueArr = (O[][])arrayOfValues;
	}

	@SuppressWarnings("unchecked")
	public Matrix(int columns, List<O> valueList) throws MathException
	{
		if(columns<1)throw new MathException("To few rows.");
		if(valueList.size()%columns!=0)throw new MathException("Matrix can not be initiated due to nr. of Values they don't fit.");
		if(valueList.contains(null))throw new MathException("Matrix can not contain null values!");
		this.columns = columns;
		this.rows = valueList.size()/columns;
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
	
	public Matrix(O[][] valueArr) throws MathException
	{
		if(containsNull(valueArr)) throw new MathException("Can't Except array with null values in it!");
		if(!isRegularArray(valueArr))throw new MathException("Array is not Regular meaning some Elements differ in Dimension despite having the same Position!");
		this.columns = valueArr.length;
		this.rows = valueArr[0].length;

		isQuadratic = (rows==columns);
		this.valueArr = (O[][]) valueArr;
	}

	public Matrix<O> clone()
	{	
		try
		{
			return new Matrix<O>(deepCopyOfValueArr());
		}
		catch (MathException e) 
		{
			throw new RuntimeException("This should not happen!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private O[][] deepCopyOfValueArr()
	{

		Object [][] arrayOfValues = new Object[columns][rows];

		for(int row=0;row<rows;row++)
		{
			for(int col=0;col<columns;col++)
			{
				arrayOfValues[col][row]= valueArr[col][row];
			}
		}

		return (O[][])arrayOfValues;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		
	    if (!(obj instanceof Matrix)) return false;

		Matrix<O> other = (Matrix<O>)obj;//TODO: Must be raw?
	    try
	    {
			if(!(other.getEnclosedType()== this.getEnclosedType()))
			{
				
				System.out.println("Matrixes aren't Enclosing same Type.");
				return false;
			}
		}
	    catch (MathException e)
	    {
			e.printStackTrace();
		}
	    
	    
	    if(other.getRows()!=this.getRows())return false;
	    if(other.getColumns()!=this.getColumns())return false;
	    
	    boolean[] check = new boolean[1];
	    check[0]= true;
	    BiConsumer<Point, O> bic=(p, v)->
	    {
	    	try
	    	{
				if(!other.getValue(p.x, p.y).equals(this.getValue(p.x, p.y)))check[0] = false;
			}
	    	catch (MathException e)
	    	{
				e.printStackTrace();
			}
	    };
	    
	    try
	    {
			walkThrouMatrix(bic);
		}
	    catch (MathException e)
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return check[0];
	}

	public Matrix<O> eraseColumn(int eraseCol) throws MathException
	{

		O o = getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns-1, rows, o);

		for(int col=0;col<columns;col++)
		{

			if(col<eraseCol)
			{
				Matrix<O> colVektor = getColumn(col);
				output = output.setColumn(colVektor, col);
			}
			
			if(col>eraseCol)
			{
				Matrix<O> colVektor = getColumn(col);
				output = output.setColumn(colVektor, col-1);
			}
		}
	
	
		return output;
	}
	
	public Matrix<O> eraseRow(int eraseRow) throws MathException
	{
		
		O o = getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns, rows-1, o);
		
		for(int row=0;row<rows;row++)
		{
			if(row<eraseRow)
			{
				Matrix<O> rowVektor = getRow(row);
				output = output.setRow(rowVektor, row);
			}
			
			if(row>eraseRow)
			{
				Matrix<O> rowVektor = getRow(row);
				output = output.setRow(rowVektor, row-1);
			}
		}
	
		return output;
	}

	@SuppressWarnings("unchecked")
	public Matrix<O> getColumn(int column) throws MathException
	{
		
		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		O[][] valueArr = (O[][]) new Object[1][rows];
		
		for(int i=0;i<rows;i++)valueArr[0][i]=getValue(column, i);
		
		return (Matrix<O>)new Matrix<>(valueArr);
	}

	public Vektor<O> getColumnAsVektor(int col) throws MathException
	{

		if(col>=columns||col<0)throw new MathException("Column out of Bounds.");

		O o = this.getValue(0, 0);
		int rows = this.getRows();
		Vektor<O> output = new Vektor<>(rows, o);
		for(int row=0;row<rows;row++)
		{
			O value = this.getValue(col, row);
			output = output.setValue(row, value);
		}

		return output;
	}

	public int getColumns()
	{ return columns;}
	
	@SuppressWarnings("unchecked")
	public Class<O> getEnclosedType() throws MathException
	{
		
		O o = this.getValue(0, 0);
		
		return (Class<O>) o.getClass();
	}

	@SuppressWarnings("unchecked")
	public Matrix<O> getRow(int row) throws MathException
	{
		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");
		O[][] valueArr = (O[][]) new Object[columns][1];
		
		for(int i=0;i<columns;i++)valueArr[i][0]=getValue(i, row);

		return (Matrix<O>)new Matrix<>(valueArr);
	}

	public Vektor<O> getRowAsVektor(int row) throws MathException
	{

		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");

		O o = this.getValue(0, 0);
		int cols = this.getColumns();
		Vektor<O> output = new Vektor<>(cols, o);
		for(int r=0;r<cols;r++)
		{
			O value = this.getValue(r, row);
			output = output.setValue(r, value);
		}

		return output;
	}
	
	public int getRows()
	{ return rows;}

	public O getValue(int column, int row) throws MathException
	{

		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");

		return valueArr[column][row];
	}

	public O[][] getValueArray()
	{
		return deepCopyOfValueArr();
	}

	public Matrix<O> glueColumnToThisOnTheLeft(Vektor<O> columnVektor) throws MathException
	{

		O o = this.getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns+1, rows, o);
		for(int col=1;col<columns+1;col++)
		{
			Vektor<O> v = this.getColumnAsVektor(col-1);
			output = output.setColumn(v, col);
		}
		output = output.setColumn(columnVektor, 0);

		return output;
	}
	
	public Matrix<O> glueColumnToThisOnTheRight(Vektor<O> columnVektor) throws MathException
	{

		O o = this.getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns+1, rows, o);
		for(int col=0;col<columns;col++)
		{
			Vektor<O> v = this.getColumnAsVektor(col);
			output = output.setColumn(v, col);
		}
		output = output.setColumn(columnVektor, columns);

		return output;
	}
	
	public Matrix<O> glueRowToThisOnTheBottom(Vektor<O> rowVektor) throws MathException
	{
		
		O o = this.getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns, rows+1, o);
		for(int row=0;row<rows-1;row++)
		{
			Vektor<O> v = this.getRowAsVektor(row);
			output = output.setRow(v, row);
		}
		output = output.setRow(rowVektor, rows);
		
		return output;
	}

	public Matrix<O> glueRowToThisOnTheTop(Vektor<O> rowVektor) throws MathException
	{
		
		O o = this.getValue(0, 0);
		Matrix<O> output = new Matrix<>(columns, rows+1, o);
		for(int row=1;row<rows;row++)
		{
			Vektor<O> v = this.getRowAsVektor(row);
			output = output.setRow(v, row);
		}
		output = output.setRow(rowVektor, 0);
		
		return output;
	}

	public int hashCode()
	{
		
		int [] wert = new int[1];
		
		BiConsumer<Point, O> bic = (p, o)->wert[0] += o.hashCode() + p.x + p.y;
		
		try
		{
			walkThrouMatrix(bic);
		}
		catch(MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't walkThru Matrix.");
		}
		
		return Objects.hash(wert[0]);
	}
	
	public boolean isQuadratic() {return isQuadratic;}

	public Matrix<O> setColumn(List<O> list, int column) throws MathException
	{
		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		O[][] valueArrClone = valueArr.clone();
		
		for(int i=0;i<rows;i++)valueArrClone[column][i]= list.get(i);
		
		return new Matrix<O>(valueArrClone);
	}

	public Matrix<O> setColumn(Matrix<O> columnVektor, int column) throws MathException
	{

		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		O[][] valueArrClone = valueArr.clone();
		for(int i=0;i<rows;i++)valueArrClone[column][i]= columnVektor.getValue(0, i);
	
		return new Matrix<O>(valueArrClone);
	}

	public Matrix<O> setColumn(Vektor<O> columnVektor, int column) throws MathException
	{

		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		O[][] valueArrClone = valueArr.clone();
		for(int i=0;i<rows;i++)valueArrClone[column][i]= columnVektor.getValue(i);
	
		return new Matrix<O>(valueArrClone);
	}	

	public Matrix<O> setRow(List<O> list, int row) throws MathException
	{
		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");
		O[][] valueArrClone = valueArr.clone();
		for(int i=0;i<columns;i++)valueArrClone[i][row]=list.get(i);
		
		return new Matrix<O>(valueArrClone);
	}

	public Matrix<O> setRow(Matrix<O> rowVektor, int row) throws MathException
	{
		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");
		O[][] valueArrClone = valueArr.clone();
		for(int i=0;i<columns;i++)valueArrClone[i][row]=rowVektor.getValue(i, 0);
		
		return new Matrix<O>(valueArrClone);
	}
	
	public Matrix<O> setRow(Vektor<O> rowVektor, int row) throws MathException
	{
		if(row<0)throw new MathException("Row smaller than Zero!?");
		if(row>=rows)throw new MathException("Row toBig.");
		O[][] valueArrClone = valueArr.clone();
		for(int col=0;col<columns;col++)valueArrClone[col][row]=rowVektor.getValue(col);
		
		return new Matrix<O>(valueArrClone);
	}
	
	public Matrix<O> setValue(int column, int row, O o) throws MathException
	{

		if(column>=columns||column<0)throw new MathException("Column out of Bounds.");
		if(row>=rows||row<0)throw new MathException("Row out of Bounds.");
		if(o==null)throw new MathException("Can't accept Null-Value.");

		O[][] valueArrClone = deepCopyOfValueArr();
		valueArrClone[column][row] = o;
		
		return new Matrix<O>(valueArrClone);
	}

	public Matrix<O> switchColumns(int colA, int colB) throws MathException
	{

		if(colA>=columns||colA<0)throw new MathException("Column (A) out of Bounds.");
		if(colB>=columns||colB<0)throw new MathException("Column (B) out of Bounds.");

		Matrix<O> colVektorA = this.getColumn(colA);
		Matrix<O> colVektorB = this.getColumn(colB);
		Matrix<O> clone = this.clone();
		
		clone = clone.setColumn(colVektorA, colB);
		clone = clone.setColumn(colVektorB, colA);
		
		return clone;
	}

	public Matrix<O> switchRows(int rowA, int rowB) throws MathException
	{

		if(rowA>=rows||rowA<0)throw new MathException("Row (A) out of Bounds.");
		if(rowB>=rows||rowB<0)throw new MathException("Row (B) out of Bounds.");

		Matrix<O> rowVektorA = this.getRow(rowA);
		Matrix<O> rowVektorB = this.getRow(rowB);
		Matrix<O> clone = this.clone();
		
		clone = clone.setRow(rowVektorA, rowB);
		clone = clone.setRow(rowVektorB, rowA);
		
		return clone;
	}

	public Set<Point> findValues(O o) throws MathException
	{

		Set<Point> set = new HashSet<>();
		BiConsumer<Point, O> bic = (p, value)->
		{
			if(value.equals(o))set.add(p);
		};
	
		walkThrouMatrix(bic);
		
		return set;
	}
	
	public Matrix<O> replaceValues(Set<Point> locations, O replacement) throws MathException
	{

		Matrix<O> output = this.clone();

		for(Point p: locations)
		{
			output = output.setValue(p.x, p.y, replacement);
		}

		return output;
	}
	
	public Matrix<O> replaceValues(O target, O replacement) throws MathException
	{

		Matrix<O> output = this.clone();

		Set<Point> locations = findValues(target);

		output = output.replaceValues(locations, replacement);

		return output;
	}

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
			
			int valueLength;
			try
			{
				valueLength = getValue(x, y).toString().length();
			} catch (MathException e)
			{
			
				e.printStackTrace();
				throw new RuntimeException("For some Reason the BiConsumer won't work. Should not happen. At all.");
			}
			if(valueLength>longestValue[0])longestValue[0]=valueLength; 
		};

		try
		{
			walkThrouMatrix(bic);
		}
		catch (MathException e)
		{
			e.printStackTrace();
			throw new RuntimeException("Couldn't generate String Representation.");
		}
		
		for(int n=0;n<rows;n++)
		{
			for(int m=0;m<columns;m++)
			{
	
				try
				{
					int l = getValue(m, n).toString().length();
					int d = longestValue[0]-l+1;
					String whiteSpace = StringManipulation.customMonoRepeatChar(' ',d);
					output = output.concat(whiteSpace+getValue(m, n).toString());
				}
				catch (MathException e)
				{
					e.printStackTrace();
				}
			}
			output = output.concat("\n");
		}

		return output;
	}

	public void walkThrouMatrix(BiConsumer<Point, O> bic) throws MathException
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