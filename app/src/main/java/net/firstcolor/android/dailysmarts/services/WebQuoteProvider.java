package net.firstcolor.android.dailysmarts.services;

import android.content.Context;

import net.firstcolor.android.dailysmarts.data.Quote;

import java.util.ArrayList;
import java.util.List;

public class WebQuoteProvider implements QuoteProvider {

    QuoteProvider.quotesListener mListener;

    @Override
    public void getQuotes(QuoteProvider.quotesListener listener) {
        mListener = listener;
        WebQuoteController controller = new WebQuoteController();
        controller.start(new WebQuoteController.OnSuccessCallback() {
            @Override
            public void handleNewQuote(Quote quote) {
                List<Quote> list = new ArrayList<Quote>();
                list.add(quote);
                mListener.onSuccess(list);
            }
        });
    }
}
