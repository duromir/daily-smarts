package net.firstcolor.android.dailysmarts.services;

import android.content.Context;

import net.firstcolor.android.dailysmarts.data.Quote;

import java.util.List;

public interface QuoteProvider {
    void getQuotes(QuoteProvider.quotesListener listener);

    interface quotesListener {
        void onSuccess(List<Quote> quotes);
    }
}
