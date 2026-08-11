package consoleTools;

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
        // Nothing to close.
    }
}