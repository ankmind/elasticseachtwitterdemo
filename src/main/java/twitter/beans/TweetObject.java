package twitter.beans;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by ganku on 8/12/17.
 */
public class TweetObject {

    String text;
    String source;
    long timeStamp;

    public TweetObject(final String text, final String source, final long timeStamp) {
        this.text = text;
        this.source = source;
        this.timeStamp = timeStamp;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public static void main(String[] args) {
        Timestamp timestamp=new Timestamp(1L);
        Date date= new Date(timestamp.getTime());
    }

    @Override
    public String toString() {
        return "TweetObject{" +
                "text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
