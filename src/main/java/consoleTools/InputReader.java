package consoleTools;

import java.io.IOException;

public interface InputReader extends AutoCloseable
{

	String readLine(String prompt);

    @Override
    void close() throws IOException;
}