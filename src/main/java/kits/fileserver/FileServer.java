package kits.fileserver;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class FileServer {

	Logger logger = LoggerFactory.getLogger(FileServer.class);
	
	private static final int PORT = 8888;
	
	private final Javalin app;
	
	public FileServer() {
	    app = Javalin.create(config -> {
	        //config.addStaticFiles("public", Location.EXTERNAL);
	        config.server(() -> {
	            Server server = new Server();
	            ResourceHandler resourceHandler = new ResourceHandler();
	            resourceHandler.setDirectoriesListed(true);
	            resourceHandler.setResourceBase("public");
	            server.setHandler(resourceHandler);
	            return server;
	        });
	    });
	}

	public void startServer() throws Exception {
		logger.info("Starting server at " + findIpAddress() + ":" + PORT);
		app.start(PORT);
	}
	
	private String findIpAddress() throws SocketException{
		for (Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces(); networkInterfaceEnumeration.hasMoreElements();){
			for (Enumeration<InetAddress> inetAddressEnumeration = networkInterfaceEnumeration.nextElement().getInetAddresses(); inetAddressEnumeration.hasMoreElements();){
				InetAddress inetAddress = inetAddressEnumeration.nextElement();
				if(!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
					return inetAddress.getHostAddress();
				}
			}
		}
		return "unknown address";
	}

}
