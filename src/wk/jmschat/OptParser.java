package wk.jmschat;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/**
 * Parses the Options provided by Main.
 *
 * @author Jakob Klepp
 */
public class OptParser implements JMSOptions {
    /** Host address */
    @Parameter(names = { "-h" }, description = "Host address, looks like tcp://hostname:port")
	public String host;

    /** Username, will be displayed in chat */
    @Parameter(names = { "-u" }, description = "Username")
    public String username;

    /** Channel name */
    @Parameter(names = { "-c" }, description = "Channel name")
    public String channel;

    /** public ip address of the client */
    private String ip;

    /**
     * @param args Commandline arguments
     *             -h <hostname>
     *             -u <username>
     *             -c <channel>
     * @return true if parsing was successfull,
     */
	public boolean parse(String [] args) {
        this.host = null;
        this.username = null;
        this.channel = null;
        try {
            new JCommander(this, args);
        } catch (ParameterException pe) {
            return false;
        }

        if(this.host==null|| this.username==null|| this.channel==null) {
            return false;
        }

        //todo retrieve ip

        return true;
	}

	/**
	 * @see JMSOptions#getHost()
	 */
	public String getHost() {
		return this.host;
	}


	/**
	 * @see JMSOptions#getUsername()
	 */
	public String getUsername() {
		return this.username;
	}


	/**
	 * @see JMSOptions#getChannel()
	 */
	public String getChannel() {
		return this.channel;
	}


	/**
	 * @see JMSOptions#getIp()
	 */
	public String getIp() {
		return this.ip;
	}

}
