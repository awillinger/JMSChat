package wk.jmschat;

/**
 * @author Jakob Klepp
 */
public class Main {
    /**
     * @param args Commandline arguments
     *             -h <hostname>
     *             -u <username>
     *             -c <channel>
     */
	public static void main(String... args) {
        OptParser optParser;
        JMSModel model;
        JMSView view;

        optParser = new OptParser();
        optParser.parse(args);

        model = new JMSModel(optParser);

        view = new JMSView(model, optParser);
	}
}
