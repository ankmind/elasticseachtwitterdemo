package elastic_search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import twitter.beans.TweetObject;
import twitter.constants.TweetConstants;
import twitter.parser.TweetParser;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganku on 8/12/17.
 */
public class SearchTweets {

    public static int maxResultSize = 50;

    public static List<TweetObject>  search(Client client,String searchTerm, long from, long to){

        QueryBuilder rangeFilter = QueryBuilders
                .rangeQuery(TweetConstants.TIME_STAMP)
                .from(from)
                .to(to)
                .includeLower(true)
                .includeUpper(true);

        QueryBuilder search= QueryBuilders.commonTermsQuery(TweetConstants.TEXT,
                searchTerm);

        QueryBuilder boolQuery=QueryBuilders.boolQuery().must(rangeFilter).must(search);

        SearchResponse scrollResp = client.prepareSearch(TweetConstants.INDEX)
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(boolQuery)
                .setSize(20).get();


        List<TweetObject> resultSet = new ArrayList<TweetObject>();

        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
               TweetObject tweetObject = TweetParser.getTweetObject(hit.getSource());
               if(tweetObject!=null){
                   resultSet.add(tweetObject);
               }
            }

            if(resultSet.size()>=maxResultSize)break;
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0);

        return resultSet;
    }




    //testcase
    //http://localhost:9200/twitter/tweet/_search?pretty=true&q=*:*&size=50
    public static void main(String[] args) throws Exception {
        Client client = ElasticClientConnection.getClient();
        //QueryBuilder qb = termsQuery(TweetConstants.TEXT,"The");
        List<TweetObject> tweetObjectList=search(client,"The Princess - Lilac apple",1502560481310l,1502560482381l);
        System.out.println("size= "+ tweetObjectList.size());
        System.out.println(tweetObjectList.get(0));
        client.close();
    }
}
