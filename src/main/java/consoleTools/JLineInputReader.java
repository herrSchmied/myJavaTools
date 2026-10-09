package consoleTools;


import java.io.IOException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;



public class JLineInputReader implements InputReader
{

    private final Terminal terminal;
    private final LineReader reader;

    public JLineInputReader(Path historyFile)
            throws IOException
    {

        terminal = TerminalBuilder.builder()
                .system(true)
                .build();

        reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .variable(LineReader.HISTORY_FILE, historyFile)
                .option(LineReader.Option.HISTORY_IGNORE_DUPS, true)
                .option(LineReader.Option.HISTORY_BEEP, false)
                .build();
    }

    @Override
    public void print(String text)
    {
        terminal.writer().print(text);
        terminal.writer().flush();
    }

    @Override
    public void println(String text)
    {
        terminal.writer().println(text);
        terminal.writer().flush();
    }

    @Override
    public void page(String text)
    {
    	List<String> lines = Arrays.asList(text.split("\\R", -1));

    	// Reserve one terminal row for the pager prompt.
    	int pageSize = Math.max(1, terminal.getSize().getRows() - 1);
    	int displayed = 0;

    	for (String line : lines)
    	{
    		
    		terminal.writer().println(line);
    		terminal.writer().flush();
    		displayed++;

    		if (displayed >= pageSize && displayed < lines.size())
    		{
    			String key = reader.readLine(
                "--More-- (Space: next page, Enter: next line, q: quit) "
    					);

    			// readLine() is line-oriented, so use a simpler
    			// prompt interaction below if single-key paging is desired.
    			if (key.equalsIgnoreCase("q"))
    			{
    				return;
    			}

    			displayed = 0;
    		}
    	}
    }

    public Terminal getTerminal()
    {
    	return terminal;
    }

    public LineReader getReader()
    {
    	return reader;
    }

    @Override
    public String readLine(String prompt)
    {
        return reader.readLine(prompt);
    }

    @Override
    public void close() throws IOException
    {
        reader.getHistory().save();
        terminal.close();
    }
}