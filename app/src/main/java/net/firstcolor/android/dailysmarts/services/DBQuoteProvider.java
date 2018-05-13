package net.firstcolor.android.dailysmarts.services;


import android.content.Context;
import android.os.AsyncTask;

import net.firstcolor.android.dailysmarts.App;
import net.firstcolor.android.dailysmarts.data.Quote;

import java.util.List;

public class DBQuoteProvider implements QuoteProvider {

    QuoteProvider.quotesListener mListener;
    Context mContext;

    @Override
    public void getQuotes(QuoteProvider.quotesListener listener) {
        mListener = listener;
        DBTask task = new DBTask(mListener);
        task.execute();
    }

    private static class DBTask extends AsyncTask<Void, Void, List<Quote>> {

        private QuoteProvider.quotesListener mListener;

        DBTask(QuoteProvider.quotesListener listener){
            mListener = listener;
        }

        @Override
        protected List<Quote> doInBackground(Void... voids) {
            return App.getAppDB().myQuotesDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            super.onPostExecute(quotes);
            mListener.onSuccess(quotes);
        }
    }

}
