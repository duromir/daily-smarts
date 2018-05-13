package net.firstcolor.android.dailysmarts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.firstcolor.android.dailysmarts.adapters.QuoteRecyclerViewAdapter;
import net.firstcolor.android.dailysmarts.data.Quote;
import net.firstcolor.android.dailysmarts.services.QuoteProvider;

import java.util.List;


public class QuotesFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private QuoteProvider mQuoteProvider;

    private List<Quote> mQuotes;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipe;



    public QuotesFragment() {
        // Required empty public constructor
    }

    public static QuotesFragment newInstance(QuoteProvider quoteProvider) {
        QuotesFragment fragment = new QuotesFragment();
        fragment.setQuoteProvider(quoteProvider);
        return fragment;
    }

    private void setQuoteProvider(QuoteProvider quoteProvider){
        mQuoteProvider = quoteProvider;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.rec_view_quotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        swipe = view.findViewById(R.id.swiperefresh);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });
        loadItems();
        return view;
    }

    public void loadItems(){
        mQuoteProvider.getQuotes(new QuoteProvider.quotesListener() {
            @Override
            public void onSuccess(List<Quote> quotes) {
                QuoteRecyclerViewAdapter adapter = new QuoteRecyclerViewAdapter(quotes, mListener);
                recyclerView.setAdapter(adapter);
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
