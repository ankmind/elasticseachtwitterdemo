package twitter;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.twitter.hbc.core.Client;
import elastic_search.InsertTweets;
import org.json.JSONObject;
import twitter.beans.TweetObject;
import twitter.parser.TweetParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by ganku on 8/11/17.
 */
public class TwitterCrawler {

    private static int maxChunk=50;
    private static int maxTweets = 1000000;
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException, IOException {

        List<String> terms = Lists.newArrayList("amazon", "Jeff Bezos");
        TwitterClientConnection twitterClientConnection = new TwitterClientConnection(terms);

        Client client = twitterClientConnection.getClient();
        BlockingQueue<String> msgQueue = twitterClientConnection.getMsgQueue();
        client.connect();

        while (!client.isDone() && (maxTweets>counter)) {
            if (msgQueue.size()>=maxChunk) {
                List<TweetObject> tweetObjectList=getTweetsList(msgQueue);
                //insertion in elastic search is happening here
                InsertTweets.insertAll(tweetObjectList);
                counter+=tweetObjectList.size();
            }
        }

        client.stop();
    }


    private static List<TweetObject> getTweetsList(BlockingQueue<String> msgQueue) throws InterruptedException {
        List<TweetObject> list=new ArrayList<TweetObject>();
        while (!msgQueue.isEmpty()){
            String tweet = msgQueue.take();
            TweetObject tweetObject = TweetParser.getTweetObject(tweet);
            if(tweetObject!=null){
                list.add(tweetObject);
            }
        }
        return list;
    }

}
