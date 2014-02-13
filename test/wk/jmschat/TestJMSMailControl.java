package wk.jmschat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wk.jmschat.stub.JMSModelStub;
import wk.jmschat.stub.JMSOptionsStub;
import wk.jmschat.stub.JMSViewStub;

import java.awt.event.ActionEvent;

/**
 * Unittest fpr JMSMailControl
 *
 * @author Jakob Klepp
 */
public class TestJMSMailControl {
    JMSModelStub model;
    Text text;
    JMSOptionsStub options;

    JMSMailControl mailControl;
    @Before
    public void before() {
        //create JMSOptions stub
        options = new JMSOptionsStub();
        //create JMSModel stub
        model = new JMSModelStub(options);
        //create Text stub
        text = new JMSViewStub(model, options);
        //create an instance of the tested object
        mailControl = new JMSMailControl(model, text, options);
    }

    @Test
    public void test_stop() {
        mailControl.stop();
    }

    @Test
    public void test_actionPerformed() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "JUnit Test!");
        mailControl.actionPerformed(event);
    }

    @Test
    public void test_onMessage() {
        Assert.fail("Not implemented!");
    }

    @Test
    public void test_run() {
        Assert.fail("Not implemented!");
    }

    @After
    public void after() {
        Assert.fail("Not implemented!");
    }
}
