package wk.jmschat.stub;

import wk.jmschat.JMSModel;
import wk.jmschat.JMSOptions;

/**
 * The JMSModel class contains all data used in this Application and provides simply Methods to access/modify them.
 *
 * @author Andreas Willinger
 * @version 0.2
 */
public class JMSModelStub
    extends JMSModel
{
    public JMSModelStub(JMSOptions options)
    {
        super(options);
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
