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


public class FileServer extends Server {

	Logger logger = LoggerFactory.getLogger(FileServer.class);
	
	private static final int PORT = 8888;
	
	public FileServer() {
		super(PORT);

		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase("public");
		setHandler(resourceHandler);
	}

	public void startServer() throws Exception {
		logger.info("Starting server at " + findIpAddress() + ":" + PORT);
		start();
		dumpStdErr();
		join();
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
