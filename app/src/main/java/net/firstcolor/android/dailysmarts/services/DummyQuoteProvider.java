package net.firstcolor.android.dailysmarts.services;

import android.content.Context;

import net.firstcolor.android.dailysmarts.data.Quote;

import java.util.ArrayList;
import java.util.List;

public class DummyQuoteProvider implements QuoteProvider {

    @Override
    public void getQuotes(QuoteProvider.quotesListener listener) {
        List<Quote> quotes = new ArrayList<Quote>();
        quotes.add(Quote.getFakeQuote());
        quotes.add(Quote.getFakeQuote());
        listener.onSuccess(quotes);
    }
}
