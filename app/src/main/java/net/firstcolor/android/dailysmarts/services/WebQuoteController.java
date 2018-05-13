package net.firstcolor.android.dailysmarts.services;


import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.firstcolor.android.dailysmarts.data.Quote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebQuoteController implements Callback<Quote> {

    private OnSuccessCallback callback;

    public void start(OnSuccessCallback onSuccessCallback){
        callback = onSuccessCallback;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebQuoteService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WebQuoteService service = retrofit.create(WebQuoteService.class);
        Call<Quote> call = service.getRandomQuote();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Quote> call, Response<Quote> response) {
        try{
            callback.handleNewQuote(response.body());
        }
        catch (NullPointerException ex) {
            Log.d("TAG", "response.body is null");
        }
    }

    @Override
    public void onFailure(Call<Quote> call, Throwable t) {

    }

    public interface OnSuccessCallback {
        void handleNewQuote(Quote quote);
    }
}
