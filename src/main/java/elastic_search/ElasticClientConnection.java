package elastic_search;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ganku on 8/12/17.
 */
public class ElasticClientConnection {

    public static Client getClient() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "my-application")
                .build();

        settings= Settings.EMPTY;
        String host="localhost";
        int port = 9300;

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.
                        getByName(host), port));
        return client;
    }
}
