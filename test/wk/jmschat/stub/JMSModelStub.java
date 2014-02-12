package wk.jmschat.stub;

/**
 * The JMSModel class contains all data used in this Application and provides simply Methods to access/modify them.
 *
 * @author Andreas Willinger
 * @version 0.2
 */
public class JMSModelStub
{
    public JMSModelStub(JMSOptionsStub options)
    {
    }

    /**
     */
    public void appendMessage(String message)
    {
    }

    /**
     * Gets all Messages currently available in the local buffer.
     *
     * @return An Array containing all Messages
     */
    public String[] getMessages()
    {
        return new String[]{"test","test2"};
    }
}
