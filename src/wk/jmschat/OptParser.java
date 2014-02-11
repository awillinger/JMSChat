package wk.jmschat;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        //retrieve ip
        {
            URL url;
            HttpURLConnection conn;
            BufferedReader rd;
            String line;
            String result = "";
            try {
                url = new URL("http://truh.in/ip");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.ip = result;
        }

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
