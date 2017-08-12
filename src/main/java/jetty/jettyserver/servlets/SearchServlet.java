package jetty.jettyserver.servlets;

import com.google.gson.Gson;
import elastic_search.ElasticClientConnection;
import elastic_search.SearchTweets;
import jetty.jettyserver.parser.TimeParser;
import jetty.jettyserver.requesthandlers.GetTweets;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import twitter.beans.TweetObject;
import utils.JSONHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ganku on 5/2/17.
 */
public class SearchServlet extends HttpServlet {

    /*
    sample post request
{
"text":"Princess",
"from":"23/04/2017,12:04:34",
"to" : "23/07/2017,23:04:34",
}
    *
    * */

    //test
    public static void main(String[] args) {
        JSONArray jsonArray= new JSONArray();
        List<TweetObject> tweets = new ArrayList<TweetObject>();
        tweets.add(new TweetObject("dd","frf",1l));
        tweets.add(new TweetObject("dd","frf",2l));
        Gson gson=new Gson();
        for(TweetObject tweetObject:tweets){
            jsonArray.put(new JSONObject(gson.toJson(tweetObject)));
        }
        System.out.println(jsonArray);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println("Embeddeee e e e e e edJetty");
        resp.getWriter().close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);


        JSONObject jsonObject = new JSONObject(jb.toString());
        JSONObject jsonResponse = new JSONObject();

            try {
                String from = JSONHelper.getIfPresent(jsonObject,"from");
                String to = JSONHelper.getIfPresent(jsonObject,"to");
                if (from == null) from = "07/04/2000,12:45:08";
                if (to == null) to = "07/04/2050,12:45:08";
                String text = jsonObject.getString("text");

                JSONArray jsonArray=GetTweets.get(from,to,text);
                jsonResponse.put("res",jsonArray);

            } catch (JSONException e) {
                // crash and burn
                System.out.println(e.toString());
                jsonResponse.put("error", "improper json sent");
            } catch (ParseException e) {
                e.printStackTrace();
                jsonResponse.put("error", "improper json sent");
            }


        resp.getWriter().println(jsonResponse.toString());
            resp.setStatus(HttpStatus.OK_200);
            //resp.getWriter().println(jsonResponse.toString());
            //resp.getWriter().println(jb.toString());
            resp.getWriter().close();
        }

}
