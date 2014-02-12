package wk.jmschat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * The JMSView class handles the frontend part of this Application, aka. interaction with the User.
 *
 * @author Andreas Willinger
 * @version 0.3
 */
public class JMSView
        extends  JFrame
        implements ModelObserver, Text
{
    // linking to the model & the controls
	private JMSModel model;
	private JMSTopicControl topicControl;
	private JMSMailControl mailControl;
    private JMSOptions options;

    // GUI elements
    private JTextArea txtContent;
    private JTextField txtCommand;
    private JButton bSend;

	public JMSView(JMSModel model, JMSOptions options)
    {
        super("JMS Chat");

        this.model = model;
        this.options = options;

        this.topicControl = new JMSTopicControl(this.model, this, this.options);
        this.mailControl = new JMSMailControl(this.model, this, this.options);
        this.addWindowListener(this.topicControl);
        this.addWindowListener(this.mailControl);

        Thread tTopic = new Thread(this.topicControl);
        Thread tMail = new Thread(this.mailControl);

        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.txtContent = new JTextArea();
        this.txtCommand = new JTextField("");
        this.bSend = new JButton("Senden");

        // make the main content box a bit more pretty
        this.txtContent.setBorder(new EmptyBorder(4,4,4,4));
        this.txtContent.setBackground(new Color(140,140,140));
        this.txtContent.setForeground(new Color(255,255,255));
        this.txtContent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        this.txtContent.setEditable(false);
        this.txtContent.setLineWrap(true);
        this.txtCommand.setPreferredSize(new Dimension(500, 30));

        this.bSend.addActionListener(this.topicControl);
        this.bSend.addActionListener(this.mailControl);
        this.bSend.setPreferredSize(new Dimension(250, 30));

        JPanel p1 = new JPanel();

        p1.setLayout(new FlowLayout());
        p1.add(this.txtCommand);
        p1.add(this.bSend);

        this.add(new JScrollPane(this.txtContent), BorderLayout.CENTER);
        this.add(p1, BorderLayout.SOUTH);

        this.getRootPane().setDefaultButton(this.bSend);
        this.model.addObserver(this);

        // this will also establish the connection to the chatserver
        tTopic.start();
        tMail.start();

        this.setVisible(true);

        org.apache.log4j.BasicConfigurator.configure();
	}

	/**
	 * @see ModelObserver#update(JMSModel)
	 */
	public void update(JMSModel model)
    {
        this.txtContent.setText("");
        String[] messages = model.getMessages();

        String updated = "";

        for(String text:messages)
        {
            updated += text;
            updated += "\n";
        }
        this.txtContent.setText(updated);
	}

	/**
	 * Returns the Text which is currentlty in the command field
	 */
	public String getText()
    {
		return this.txtCommand.getText();
	}

    /**
     * Clears the command field
     */
    public void clearText()
    {
        this.txtCommand.setText("");
    }

    /**
     * Fires the windowClosing Event manually and therefore cleans up all Connections correctly
     */
    public void close()
    {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
