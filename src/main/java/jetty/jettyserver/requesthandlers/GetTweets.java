package jetty.jettyserver.requesthandlers;

import com.google.gson.Gson;
import elastic_search.ElasticClientConnection;
import elastic_search.SearchTweets;
import jetty.jettyserver.parser.TimeParser;
import org.elasticsearch.client.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import twitter.beans.TweetObject;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by ganku on 5/2/17.
 */
public class GetTweets {

    public static JSONArray get(String from,String to,String text) throws ParseException, UnknownHostException {
        long fromTime = TimeParser.getTimeStamp(from);
        long toTime = TimeParser.getTimeStamp(to);


        Client client = ElasticClientConnection.getClient();
        List<TweetObject> tweets =
                SearchTweets.search(client,text,fromTime,toTime);
        client.close();

        Gson gson= new Gson();
        JSONArray jsonArray= new JSONArray();
        for(TweetObject tweetObject:tweets){
            jsonArray.put(new JSONObject(gson.toJson(tweetObject)));
        }
        return jsonArray;
    }

}
