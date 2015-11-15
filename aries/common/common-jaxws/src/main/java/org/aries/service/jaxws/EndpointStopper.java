package org.aries.service.jaxws;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class EndpointStopper {
	
	public EndpointStopper(final int port, final Endpoint endpoint) throws IOException {
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 5);
        final ExecutorService threads  = Executors.newFixedThreadPool(1);
        server.setExecutor(threads);
        server.start();

        HttpContext context = server.createContext("/stop");
        context.setHandler(new HttpHandler() {
    		public void handle(HttpExchange exchange) throws IOException {
				System.out.println("Shutting down the Endpoint");
				endpoint.stop();
				System.out.println("Endpoint is down");
                exchange.sendResponseHeaders(200, 0);
                exchange.close();
        		server.stop(1);
        		threads.shutdown();
			}
		});
    }
}

