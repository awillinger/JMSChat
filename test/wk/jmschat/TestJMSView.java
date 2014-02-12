package wk.jmschat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import wk.jmschat.stub.JMSOptionsStub;

/**
 * JUnitTest for JMSView
 * @author Andreas Willinger
 */
public class TestJMSView
{
    private JMSView view;
    private JMSModel model;

    @Before
    public void before()
    {
        JMSOptionsStub options = new JMSOptionsStub();
        this.model = new JMSModel(options);
        this.view = new JMSView(this.model, options);
    }

    @Test
    public void test_update_null()
    {
        this.view.update((JMSModel)null);
    }

    @Test
    public void test_update_notnull()
    {
        this.view.update(this.model);
    }


    @Test
    public void test_getText()
    {
        this.view.getText();
    }

    @Test
    public void test_clearText()
    {
        String old = this.view.getText();
        this.view.clearText();
        if(!old.equals("")) Assert.assertNotEquals(old, this.view.getText());
    }

    @Test
    public void test_close()
    {
        this.view.close();
        Assert.assertEquals(this.view, null);
    }

    @After
    public void after()
    {
    }
}
