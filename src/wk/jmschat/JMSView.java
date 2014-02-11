package wk.jmschat;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Iterator;

/**
 * @author Andreas Willinger
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

        Thread tTopic = new Thread(this.topicControl);
        Thread tMail = new Thread(this.mailControl);

        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.txtContent = new JTextArea();
        this.txtCommand = new JTextField("");
        this.bSend = new JButton("Senden");

        this.txtContent.setBorder(new EmptyBorder(8,8,8,8));
        this.txtContent.setBackground(new Color(200,200,200));
        this.txtContent.setForeground(new Color(255,255,255));
        this.txtCommand.setPreferredSize(new Dimension(450, 30));

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

        tTopic.start();
        tMail.start();

        this.setVisible(true);
	}

	/**
	 * @see ModelObserver#update(JMSModel)
	 * 
	 *  
	 */
	public void update(JMSModel model)
    {
        this.txtContent.setText("");
        TextMessage[] messages = model.getMessages();

        String updated = "";
        try
        {
            for(TextMessage text:messages)
            {
                updated += text.getText();
                updated += "\n";
            }
        }
        catch(JMSException e)
        {
            updated = "*** Fehler beim Abrufen der Nachrichten: "+e.getMessage();
        }

        this.txtContent.setText(updated);
	}


	/**
	 * @see Text#getText()
	 * 
	 *  
	 */
	public String getText()
    {
		return this.txtCommand.getText();
	}

}
