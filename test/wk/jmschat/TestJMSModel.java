package wk.jmschat;

import org.junit.*;
import wk.jmschat.stub.JMSOptionsStub;

/**
 * Unittest for JMSModel
 *
 * @author Andreas Willinger
 */
public class TestJMSModel
{
    private JMSModel model;

    @Before
    public void before()
    {
        JMSOptionsStub stub = new JMSOptionsStub();
        this.model = new JMSModel(stub);
    }

    @Test
    public void test_getMessages()
    {
        Assert.assertNotEquals(this.model.getMessages(), null);
    }

    @Test
    public void test_appendMessage_null()
    {
        this.model.appendMessage(null);
        String[] messages = this.model.getMessages();

        if(messages.length > 0)
        {
            Assert.assertNotEquals(messages[messages.length-1], null);
        }
    }

    @Test
    public void test_appendMessage_notnull()
    {
        this.model.appendMessage("test");
        String[] messages = this.model.getMessages();

        Assert.assertEquals(messages[messages.length-1], "test");
    }

    @Test
    public void test_addObserver()
    {
        this.model.addObserver(new testObserver());
    }

    public class testObserver implements  ModelObserver
    {
        @Override
        public void update(JMSModel model) {}
    }
}
