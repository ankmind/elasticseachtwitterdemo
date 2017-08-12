package jetty.jettyserver;

import jetty.jettyserver.servlets.SearchServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


/**
 * Created by ganku on 5/2/17.
 */

public class MainServer {

    public static void main(String[] args) throws Exception {
        //postUrl : http://localhost:2020/posts
        String portNum=System.getenv("PORT");

        if(portNum==null) {
            runServer(2020);
        }
        else{
            runServer(Integer.parseInt(portNum));
        }
    }


    static void runServer(int portNumber) throws Exception {
        Server server = new Server(portNumber);
        ServletContextHandler handler = new
                ServletContextHandler(server, "");
        handler.addServlet(SearchServlet.class, "/search");
        server.start();
    }
}
