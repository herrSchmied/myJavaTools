package consoleTools;

import java.io.IOException;

public interface InputReader extends AutoCloseable
{

	String readLine(String prompt);

    @Override
    void close() throws IOException;
    
    public void page(String text) throws IOException;
    
    public void print(String text);

    public void println(String text);
}