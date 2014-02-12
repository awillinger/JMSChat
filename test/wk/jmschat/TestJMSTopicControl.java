package wk.jmschat;

import org.junit.*;
import wk.jmschat.stub.JMSOptionsStub;
import wk.jmschat.stub.JMSViewStub;

import java.awt.event.ActionEvent;

/**
 * Tests the JMSTopicControl class
 *
 * @author Andreas Willinger
 * @version 0.1
 */
public class TestJMSTopicControl
{
    private JMSTopicControl testTopic;

    private JMSModel model;
    private JMSViewStub view;
    private JMSOptionsStub options;

    @Before
    public void before()
    {
        this.options = new JMSOptionsStub();
        this.model = new JMSModel(this.options);
        this.view = new JMSViewStub(this.model, this.options);
        this.testTopic = new JMSTopicControl(this.model, this.view, this.options);
    }

    @Test
    public void test_stop_open()
    {
        this.testTopic.run();
        this.testTopic.stop();
    }

    @Test
    public void test_stop_notopen()
    {
        this.testTopic.stop();
    }

    @Test
    public void test_actionPerformed()
    {
        this.testTopic.run();

        ActionEvent ex = new ActionEvent(this.model, 1, "");
        this.testTopic.actionPerformed(ex);
    }

    @Test
    public void test_actionPerformed_command()
    {
        this.testTopic.run();

        ActionEvent ex = new ActionEvent(this.model, 1, "");
        String long_text = "";
        for(int i = 0; i < 1001; i++)
        {
            long_text += "a";
        }
        this.view.setText(long_text);
        this.testTopic.actionPerformed(ex);
    }

    @Test
    public void test_onMessage_null()
    {
        this.testTopic.onMessage(null);
    }

    @Test
    public void test_windowClosing()
    {
        this.testTopic.run();
        this.testTopic.windowClosing(null);
    }


    @Test
    public void test_run()
    {
        this.testTopic.run();
    }
}
