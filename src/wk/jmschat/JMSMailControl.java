package wk.jmschat;

import javax.jms.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Jakob Klepp
 */
public class JMSMailControl
        extends WindowAdapter
        implements MessageListener, Runnable, ActionListener{

    /** Words indicating some special command used by mailsystem */
    public final static String[] KEYWORDS = {"MAIL", "MAILBOX"};

    /**  */
	private Connection mailConnection;
    /**  */
	private Session mailSession;
    /**  */
	private MessageProducer mailSender;
    /**  */
	private MessageConsumer mailReceiver;
    /**  */
	private JMSOptions options;
    /**  */
	private JMSModel model;
    /**  */
	private Text text;

    private boolean stoped;

    /**
     *
     * @param model
     * @param textContainer
     * @param options
     */
	public JMSMailControl(JMSModel model, Text textContainer, JMSOptions options) {
        this.model = model;
        this.text = textContainer;
        this.options = options;
	}

	public void stop() {
        stoped = true;
	}

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = this.text.getText();
        if(text.startsWith("MAILBOX")) {
            //todo push MAILBOX into JMSModel
            //todo clear Queue
        } else
        if(text.startsWith("MAIL")) {
            Message m = null;
            try {
                m = mailSession.createTextMessage(text);
                mailSender.send(m);
            } catch (JMSException e1) {
                //todo proper exception handling
                    //maybe logging
                System.out.println(e1.getMessage());
            }
        }
    }

    /**
     * Invoked when a message arrives.
     *
     * @param message Message in box
     */
    @Override
    public void onMessage(Message message) {
        //todo append Message to Queue

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
        //todo establish a connection
        while (!stoped) {

        }
        try {
            mailReceiver.close();
            mailSender.close();
            mailSession.close();
            mailConnection.close();
        } catch (JMSException e1) {
            //todo proper exception handling
                //maybe logging
            System.out.println(e1.getMessage());
        }
    }
}
