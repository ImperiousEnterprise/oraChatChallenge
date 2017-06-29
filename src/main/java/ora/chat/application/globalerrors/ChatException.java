package ora.chat.application.globalerrors;

/**
 * Created by MR.F on 7/1/2017.
 */
public class ChatException extends Exception
{
    private String problem;
    // Parameterless Constructor
    public ChatException() {}

    // Constructor that accepts a message
    public ChatException(String problem, String message)
    {
        super(message);
        this.problem =problem;
    }

    public String getProblem() {
        return problem;
    }
}