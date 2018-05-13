package net.firstcolor.android.dailysmarts.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;

import net.firstcolor.android.dailysmarts.App;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "quotes")
public class Quote {

    public Quote() {}

    public Quote(String text, String author) {
        this.quoteText = text;
        this.quoteAuthor = author;
    }

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name="quote_text")
    private String quoteText;

    @ColumnInfo(name="quote_author")
    private String quoteAuthor;

    @ColumnInfo(name="sender_name")
    private String senderName;

    @ColumnInfo(name="sender_link")
    private String senderLink;

    @ColumnInfo(name="quote_link")
    private String quoteLink;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderLink() {
        return senderLink;
    }

    public void setSenderLink(String senderLink) {
        this.senderLink = senderLink;
    }

    public void setQuoteLink(String quoteLink) {
        this.quoteLink = quoteLink;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public String getQuoteLink() {
        return quoteLink;
    }

    public static Quote getFakeQuote() {
        return new Quote("You should only use this for test purposes. It should be replaced with real data eventually.", "Teo");
    }

    public boolean isFromDB(){
        return !(id == null);
    }

    public void saveToDB(OnChangeStateListener listener) {
        DBTask task = new DBTask(DBTask.DB_ACTION_SAVE, listener);
        task.execute(this);
    }

    public void saveToDB()
    {
        DBTask task = new DBTask(DBTask.DB_ACTION_SAVE);
        task.execute(this);
    }

    public void deleteFromDB(OnChangeStateListener listener) {
        DBTask task = new DBTask(DBTask.DB_ACTION_DELETE, listener);
        task.execute(this);
    }

    public void deleteFromDB(){
        DBTask task = new DBTask(DBTask.DB_ACTION_DELETE);
        task.execute(this);
    }

    private static class DBTask extends AsyncTask<Quote, Integer, List<Quote>> {

        private static final String DB_ACTION_SAVE = "save";
        private static final String DB_ACTION_DELETE = "delete";

        OnChangeStateListener mListener;
        String mAction;

        public DBTask(String action, OnChangeStateListener listener){
            mListener = listener;
            mAction = action;
        }

        public DBTask(String action){
            mAction = action;
        }

        @Override
        protected List<Quote> doInBackground(Quote... quotes) {
            if(mAction.equals(DB_ACTION_SAVE)){
                App.getAppDB().myQuotesDao().insertAll(Arrays.asList(quotes));
            }
            else if(mAction.equals(DB_ACTION_DELETE)){
                App.getAppDB().myQuotesDao().delete(Arrays.asList(quotes));
            }
            return Arrays.asList(quotes);
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            super.onPostExecute(quotes);
            if(mListener != null){
                mListener.onSuccess(quotes);
            }
        }
    }

    public interface OnChangeStateListener {
        void onSuccess(List<Quote> quotes);
    }
}
