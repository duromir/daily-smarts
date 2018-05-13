package net.firstcolor.android.dailysmarts.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.firstcolor.android.dailysmarts.App;
import net.firstcolor.android.dailysmarts.MainActivity;
import net.firstcolor.android.dailysmarts.R;
import net.firstcolor.android.dailysmarts.data.Quote;
import java.util.List;
import net.firstcolor.android.dailysmarts.QuotesFragment.OnFragmentInteractionListener;


public class QuoteRecyclerViewAdapter extends RecyclerView.Adapter<QuoteRecyclerViewAdapter.ViewHolder> {

    private final List<Quote> mValues;
    private final OnFragmentInteractionListener mListener;

    public QuoteRecyclerViewAdapter(List<Quote> items, OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.include_quote_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setQuote(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final TextView txtText;
        private final TextView txtAuthor;

        private final Button btnSave;
        private final Button btnShare;

        private Quote mQuote;

        public void setQuote(Quote quote){
            mQuote = quote;
            txtText.setText(quote.getQuoteText());
            txtAuthor.setText(quote.getQuoteAuthor());
            initButtons();
        }

        private void refreshFavourites(View view){
            MainActivity activity = App.getMainActivityByView(view);
            if(activity != null){
                activity.refreshFavourites();
            }
        }

        private void initButtons(){
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mQuote.isFromDB()){
                        mQuote.deleteFromDB(new Quote.OnChangeStateListener() {
                            @Override
                            public void onSuccess(List<Quote> quotes) {
                                btnSave.setBackgroundResource(R.drawable.ic_favorite_border_black_24px);
                                refreshFavourites(btnSave);

                            }
                        });
                    }
                    else {
                        mQuote.saveToDB(new Quote.OnChangeStateListener() {
                            @Override
                            public void onSuccess(List<Quote> quotes) {
                                btnSave.setBackgroundResource(R.drawable.ic_favorite_black_24px);
                                refreshFavourites(btnSave);
                            }
                        });
                    }
                }
            });

            if(mQuote.isFromDB()){
                btnSave.setBackgroundResource(R.drawable.ic_favorite_black_24px);
            }

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Be smart and read this");
                        String sAux = "\n" + mQuote.getQuoteText() + "\n\n";
                        sAux += mQuote.getQuoteAuthor() + "\n\n";
                        sAux += mQuote.getQuoteLink() + "\n\n";
                        intent.putExtra(Intent.EXTRA_TEXT, sAux);
                        Intent chooserIntent = Intent.createChooser(intent, "Choose one");
                        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        App.appContext.startActivity(chooserIntent);
                    }
                    catch (Exception e){
                        Toast.makeText(App.appContext, "Error: can not be shared right now",  Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtText = view.findViewById(R.id.txt_quote_text);
            txtAuthor = view.findViewById(R.id.txt_quote_author);
            btnSave = view.findViewById(R.id.btn_save);
            btnShare = view.findViewById(R.id.btn_share);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + /*mContentView.getText() +*/ "'";
        }
    }



}
