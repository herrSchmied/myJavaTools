package consoleTools;


import java.util.List;
import java.util.Set;

import javafx.util.Pair;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;


import someMath.StringManipulation;
import someMath.exceptions.ConsoleToolsException;

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
	
	public TerminalTableDisplay(List<String> headers, List<List<String>> cellValues, char delimiter, int cellWidth, Set<Pair<Color, Point>> highlights) throws ConsoleToolsException
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
				if(highlights!=null)c = getHighlightColor(new Point(m, n));
				cell[n][m] = new TerminalTableCell(breakupContent(s, c), delimiter, cellWidth);
			}
		}
	}

	public TerminalTableDisplay(List<String> headers, List<List<String>> cellValues, char delimiter, int cellWidth) throws ConsoleToolsException
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

	public String[] breakupContent(String s, Color c) throws ConsoleToolsException
	{
		
		int l = s.length();
		if(l<=cellWidth)
		{
			
			int r = cellWidth-l;
			String[] singleLine = new String[1];
			singleLine[0] = delimiter + s + StringManipulation.customMonoRepeatChar(' ', r);

			if(highlights==null)return singleLine;

			if(c!=null)
			{
				singleLine[0] = delimiter + formatBashStringBoldInColorX(c, s) + StringManipulation.customMonoRepeatChar(' ', r);
			}

			return singleLine;
		}
		
		int linesNr = Math.floorDiv(l, (cellWidth))+1;

		String[] lines = new String[linesNr];
		
		String copy = String.valueOf(s);
		
		for(int n=0;n<linesNr;n++)
		{
			String a = "";
			
			if(copy.length()>=cellWidth)
			{
				a = copy.substring(0, cellWidth);
				copy = copy.substring(cellWidth, copy.length());
			}
			else 
			{
				int r = cellWidth-copy.length();
				a = copy+StringManipulation.customMonoRepeatChar(' ', r);
			}

			lines[n] = delimiter + a;
			
			if(highlights!=null&&c!=null)
			{
				lines[n] = delimiter + formatBashStringBoldInColorX(c, a);
			}
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