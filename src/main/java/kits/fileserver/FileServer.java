package kits.fileserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class FileServer extends Server {

	public FileServer() {
		super(8888);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase("public");
		setHandler(resourceHandler);
	}

	public void startServer() throws Exception {
		start();
		dumpStdErr();
		join();
	}

}
