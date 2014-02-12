package wk.jmschat.stub;

import wk.jmschat.JMSModel;
import wk.jmschat.JMSOptions;
import wk.jmschat.Text;

/**
 * A Stub class for the JMSView
 *
 * @author Andreas Willinger
 * @version 0.3
 */
public class JMSViewStub
    implements Text
{

    private String text;

    public JMSViewStub(JMSModel model, JMSOptions options)
    {
        this.text = "test";
    }

    public void update(JMSModelStub model)
    {
    }

    /**
     * Returns the Text which is currentlty in the command field
     */
    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Clears the command field
     */
    public void clearText()
    {
    }

    /**
     * Fires the windowClosing Event manually and therefore cleans up all Connections correctly
     */
    public void close()
    {
    }
}
