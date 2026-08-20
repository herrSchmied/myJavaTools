package consoleTools;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

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