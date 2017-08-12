package twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ganku on 8/12/17.
 */
public class TwitterClientConnection {

    private List<String> keywords;
    private Client client;
    private BlockingQueue<String> msgQueue;

    public Client getClient() {
        return client;
    }

    public BlockingQueue<String> getMsgQueue() {
        return msgQueue;
    }

    public TwitterClientConnection(List<String> keywords){
        this.keywords = keywords;
        msgQueue = new LinkedBlockingQueue<String>(1000000);
        initialiseClient();
    }


    private  void initialiseClient(){
        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        //set up some track terms
        hosebirdEndpoint.trackTerms(keywords);
        //only english tweets required
        hosebirdEndpoint.languages(Lists.<String>newArrayList("en"));


        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(Credentials.consumerKey, Credentials.consumerSecret,
                Credentials.token, Credentials.tokenSecret);


        ClientBuilder builder = new ClientBuilder()
                //.name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));


        Client hosebirdClient = builder.build();
        this.client = hosebirdClient;
    }
}
