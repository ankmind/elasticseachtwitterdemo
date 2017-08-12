package twitter.parser;

import org.json.JSONObject;
import twitter.beans.TweetObject;
import twitter.constants.TweetConstants;

import java.util.Map;

/**
 * Created by ganku on 8/12/17.
 */
public class TweetParser {

    public static TweetObject getTweetObject(String tweet){
        TweetObject tweetObject= null;
        JSONObject jsonObject=new JSONObject(tweet);

        try {
            String text=jsonObject.getString(TweetConstants.TEXT);
            long timeStamp=jsonObject.getLong(TweetConstants.TIME_STAMP);
            String source= jsonObject.getString(TweetConstants.SOURCE);
            tweetObject=new TweetObject(text,source,timeStamp);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return tweetObject;
    }


    public static TweetObject getTweetObject(Map<String,Object> tweet){
        TweetObject tweetObject= null;
        try {
            String text= (String) tweet.get(TweetConstants.TEXT);
            Long timeStamp= (Long) tweet.get(TweetConstants.TIME_STAMP);
            String source= (String) tweet.get(TweetConstants.SOURCE);
            tweetObject=new TweetObject(text,source,timeStamp);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return tweetObject;
    }
}
