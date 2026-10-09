package consoleTools;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TestInputReader implements InputReader
{

    private final Queue<String> inputs;

    public TestInputReader(String... inputs)
    {
        this.inputs = new LinkedList<>(Arrays.asList(inputs));
    }

    @Override
    public String readLine(String prompt)
    {
        return inputs.remove();
    }

    @Override
    public void close()
    {

    }

    public void print(String text)
    {
    	
    }

    public void println(String text)
    {
    	
    }

	@Override
	public void page(String text) throws IOException
	{

	}
}