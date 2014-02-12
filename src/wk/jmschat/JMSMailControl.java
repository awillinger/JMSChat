package wk.jmschat;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jakob Klepp
 */
public class JMSMailControl extends WindowAdapter implements MessageListener, Runnable, ActionListener {

	private javax.jms.Connection mailConnection;

	private javax.jms.Session mailSession;

	private javax.jms.MessageListener mailSender;

	private MessageConsumer mailReceiver;

	private JMSOptions options;

	private JMSModel jMSModel;

	private Text text;

	public JMSMailControl(JMSModel model, Text textContainer, JMSOptions options) {

	}

	public void stop() {

	}

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        System.out.println("#2 closed");
        this.stop();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
