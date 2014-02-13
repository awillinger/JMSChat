package wk.jmschat;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The JMSMailControl checks user inputs for the the KEYWORDS
 *
 * If it detects the keyword MAIL it will send a message to the given user
 * syntax as follows:
 *
 * MAIL <username>@<ip> <message>
 *
 * If it detects the keyword MAILBOX it will retrieve all the messages addressed
 * to the current user (as defined in ``options``) and display them.
 *
 * @author Jakob Klepp
 */
public class JMSMailControl
        extends WindowAdapter
        implements MessageListener, Runnable, ActionListener{

    /** Words indicating some special command used by mailsystem */
    public final static String[] KEYWORDS = {"MAIL", "MAILBOX"};

    /** Connection to ActiveMQ */
	private Connection mailConnection;
    /** Session to ActiveMQ */
	private Session mailSession;
    /** "Mailbox" */
    private Destination queue;
    /** "Mailbox" */
	private MessageConsumer mailReceiver;
    /** jms connection options */
	private JMSOptions options;
    /** Model to push messages to */
	private JMSModel model;
    /** source for user inputs */
	private Text text;

    private boolean stoped;

    /**
     * @param model Model to push messages to
     * @param textContainer source for user inputs
     * @param options jms connection options
     */
	public JMSMailControl(JMSModel model, Text textContainer, JMSOptions options) {
        this.model = model;
        this.text = textContainer;
        this.options = options;
	}

    /**
     * Shuts the mail client down.
     */
	public void stop() {
        stoped = true;
        try {
            mailReceiver.close();
            mailSession.close();
            mailConnection.close();
        } catch (JMSException | NullPointerException e1) {
            this.model.appendMessage("Mailservice: Konnte Verbindung nicht trennen!");
        }
	}

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent Object, containing message source and some other data
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String text = this.text.getText();
        String[] words = text.split(" ");

        if(words.length == 0) return;

        //check the mailbox
        if(words[0].equals("MAILBOX")) {
            //push MAILBOX into JMSModel
            int count = 0;
            while (true) {
                try {
                    //timout of 500 ms
                    Message message = mailReceiver.receive(500);
                    //mailbox empty
                    if(message==null) {
                        break;
                    }
                    //append message to model
                    if(message instanceof TextMessage){
                        TextMessage textMessage = (TextMessage) message;
                        this.model.appendMessage(textMessage.getText());
                        count++;
                    }
                } catch (JMSException | NullPointerException e1) {
                    break;
                }
            }
        } else
        //send a mail
        if(words[0].equals("MAIL")) {
            Destination destination;
            MessageProducer producer;
            try {
                if(words.length < 3) {
                    this.model.appendMessage("Verwendung:");
                    this.model.appendMessage("MAIL <username>@<ip> <message>");
                    return;
                }
                //opening a message queue
                destination = mailSession.createQueue(words[1]);
                producer = mailSession.createProducer(destination);
                //parse input string -> extract message
                StringBuilder messageText = new StringBuilder("[" + options.getUsername() + "@" + options.getIp() + " -> Me]");
                for(int i=2; i<words.length; i++) {
                    messageText.append(" ")
                               .append(words[i]);
                }
                TextMessage message = mailSession.createTextMessage(messageText.toString());
                producer.send(message);
                producer.close();

                this.model.appendMessage("Nachricht gesendet!");
                this.text.clearText();
            } catch (JMSException | NullPointerException e1) {
                Logger.getLogger(this.getClass()).info(e1.getMessage());
                Logger.getLogger(this.getClass()).error(e1.getStackTrace());
            }
        }
    }

    /**
     * NOT USED
     * TODO remove
     */
    @Override
    public void onMessage(Message message) {
        this.model.appendMessage("MAIL: Nachricht erhalten");
    }

    /**
     * Shuts the connection down when the window gets closed.
     *
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     * @param e WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e)
    {
        this.stop();
    }

    /**
     * Comfort method for establishing connections
     */
    private void createConnection() {
        ConnectionFactory factory = new ActiveMQConnectionFactory(options.getHost());
        try {
            mailConnection = factory.createConnection();
            mailConnection.start();
            mailSession = mailConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = mailSession.createQueue(options.getUsername() + "@" + options.getIp());
            mailReceiver = mailSession.createConsumer(queue);
            //mailReceiver.setMessageListener(this);
        } catch (JMSException | NullPointerException e) {
            model.appendMessage("Mailservice: Konnte keine Verbindung aufbauen.");
        }
    }

    /**
     * starts the mail client
     */
    @Override
    public void run() {
        //establish a connection
        createConnection();
    }
}
