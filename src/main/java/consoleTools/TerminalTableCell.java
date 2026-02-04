package consoleTools;

import someMath.StringManipulation;

public class TerminalTableCell
{

	private final int nrOfLines;
	private final String [] lines;
	private final char delimiter;
	private final int cellWidth;

	public TerminalTableCell(String [] lines, char delimiter, int cellWidth)
	{

		this.lines = lines;
		this.nrOfLines = lines.length;
		this.delimiter = delimiter;
		this.cellWidth = cellWidth;
	}
		
	public String getLineByNr(int n)
	{

		String output = delimiter + StringManipulation.customMonoRepeatChar(' ', cellWidth);

		if(n<nrOfLines)output = lines[n];

		return output;
	}
		
	public int getNrOfLines()
	{
		return nrOfLines;
	}
		
	public String toString()
	{
		String output = "";
		
		for(int n=0;n<nrOfLines;n++)
		{
			output = output+lines[n]+"\n";
		}
		
		
		return output;
	}
}
