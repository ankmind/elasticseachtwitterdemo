package elastic_search;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import twitter.beans.TweetObject;
import twitter.constants.TweetConstants;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by ganku on 8/12/17.
 */
public class InsertTweets {

    private static Integer idCounter = 0;
    private static String doc = "tweet";

    private static void incrementId(){
        synchronized (idCounter) {
            idCounter++;
        }
    }

    public static void insertAll(List<TweetObject> tweetObjectList) throws IOException {
        //bulk insert

        Client client = ElasticClientConnection.getClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(TweetObject tweetObject:tweetObjectList){
            bulkRequest.add(client.prepareIndex(TweetConstants.INDEX,doc,
                    Integer.toString(idCounter))
                    .setSource(jsonBuilder()
                            .startObject()
                            .field(TweetConstants.TEXT, tweetObject.getText())
                            .field(TweetConstants.TIME_STAMP, tweetObject.getTimeStamp())
                            .field(TweetConstants.SOURCE , tweetObject.getSource())
                            .endObject()
                    )
            );

            System.out.println(idCounter);
            incrementId();
        }

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
        client.close();
    }
}
