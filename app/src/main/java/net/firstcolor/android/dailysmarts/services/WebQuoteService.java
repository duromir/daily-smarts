package net.firstcolor.android.dailysmarts.services;

import net.firstcolor.android.dailysmarts.data.Quote;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebQuoteService {

    public static final String BASE_URL = "http://api.forismatic.com/api/1.0/";

    @GET("?method=getQuote&format=json&lang=en")
    Call<Quote> getRandomQuote();
}
