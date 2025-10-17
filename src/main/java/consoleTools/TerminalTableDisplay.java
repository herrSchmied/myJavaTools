package consoleTools;


import java.util.List;
import java.util.Set;

import javafx.util.Pair;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


import someMath.StringManipulation;


import static consoleTools.TerminalXDisplay.*;


public class TerminalTableDisplay 
{

	private final List<String> headers;
	private final List<List<String>> cellValues;
	private int columns = 0;
	private int rows = 0;
	private final int cellWidth;
	private final TerminalTableCell [][]cell;
	private final char delimiter;
	private final int dL = 1; //delimiterLength = 1;
	private final Set<Pair<Color, Point>> highlights;
	
	public TerminalTableDisplay(List<String> headers, List<List<String>> cellValues, char delimiter, int cellWidth, Set<Pair<Color, Point>> highlights)
	{

		this.headers = headers;
		this.delimiter = delimiter;
		this.cellWidth = cellWidth;
		this.highlights = highlights;
		
		List<List<String>> newCellValues = new ArrayList<>();
		newCellValues.add(this.headers);
		newCellValues.addAll(cellValues);
		this.cellValues = newCellValues;
		this.rows = this.cellValues.size();
		this.columns = headers.size();
		
		cell = new TerminalTableCell[rows][columns];

		for(int n=0;n<rows;n++)
		{
			List<String> row = this.cellValues.get(n);
			
			for(int m=0;m<columns;m++)
			{
				
				String s = row.get(m);
				if(s.contains("\n"))
				{
					System.out.println("Warning Cell contains \'\\n\'. Will be replaced with Whitespace.");
					s= s.replace('\n', ' ');
				}
				
				Color c = null;
				if(highlights!=null)c = getHighlightColor(new Point(n,m));
				cell[n][m] = new TerminalTableCell(breakupContent(s, c), delimiter, cellWidth);
			}
		}
	}

	public TerminalTableDisplay(List<String> headers, List<List<String>> cellValues, char delimiter, int cellWidth)
	{
		this(headers, cellValues, delimiter, cellWidth, null);
	}

	public Color getHighlightColor(Point p)
	{
		for(Pair<Color, Point> pair: highlights)
		{
			Point p2 = pair.getValue();
			if(p2.equals(p))return pair.getKey();
		}
		
		return null;
	}

	public String[] breakupContent(String s, Color c)
	{
		
		int l = s.length();
		if(l<=cellWidth)
		{
			
			int r = cellWidth-l;
			String[] singleLine = new String[1];
			singleLine[0] = delimiter + s + StringManipulation.customMonoRepeatChar(' ', r);
			
			if(highlights==null)return singleLine;

			if(c!=null&&c.equals(Color.red))
			{
				singleLine[0] = delimiter + formatBashStringBoldAndRed(s) + StringManipulation.customMonoRepeatChar(' ', r);
			}
			if(c!=null&&c.equals(Color.yellow))
			{
				singleLine[0] = delimiter + formatBashStringBoldAndYellow(s) + StringManipulation.customMonoRepeatChar(' ', r);
			}
			if(c!=null&&c.equals(Color.green))
			{
				singleLine[0] = delimiter + formatBashStringBoldAndGreen(s) + StringManipulation.customMonoRepeatChar(' ', r);
			}

			if(c!=null&&c.equals(Color.blue))
			{
				singleLine[0] = delimiter + formatBashStringBoldAndBlue(s) + StringManipulation.customMonoRepeatChar(' ', r);
			}

			return singleLine;
		}
		
		int linesNr = Math.floorDiv(l, (cellWidth));
		if(l%(cellWidth)!=0)linesNr=linesNr+1;

		String[] lines = new String[linesNr];
		
		String copy = String.valueOf(s);
		
		for(int n=0;n<linesNr;n++)
		{
			String a = "";
			
			if(copy.length()>=cellWidth)a = copy.substring(0, cellWidth);
			else 
			{
				int r = cellWidth-copy.length();
				a = String.valueOf(copy)+StringManipulation.customMonoRepeatChar(' ', r);
			}

			if(copy.length()>=cellWidth)copy = copy.substring(cellWidth, copy.length());

			lines[n] = delimiter + a;
		}
		
		return lines;
	}

	
	public int mostLinesInThatRow(int row)
	{
		
		int maxRow = 1;
		
		for(int m=0;m<columns;m++)
		{
			int x = cell[row][m].getNrOfLines();
			if(x>maxRow)maxRow = x;
		}
		
		return maxRow;
	}
	
	public String toString()
	{
		String output = "";
		
		for(int n=0;n<rows;n++)
		{
			//List<String> row = this.cellValues.get(n);
			int maxRow = mostLinesInThatRow(n);
		
			for(int l=0;l<maxRow;l++)
			{
				
				for(int m=0;m<columns;m++) 
				{
					output = output + cell[n][m].getLineByNr(l);
				}
				
				output = output + delimiter + "\n";
			}
			
			//floors (plural).
			output = output + StringManipulation.customMonoRepeatChar('-', (columns*cellWidth)+(dL*columns) + dL)+"\n";
		}

		//Roof
		output = StringManipulation.customMonoRepeatChar('-', (columns*cellWidth)+(dL*columns) + dL)+"\n"+output;

		return output;
	}
}