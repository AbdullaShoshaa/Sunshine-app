package com.example.shoshaa.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailedActivityFragment extends Fragment {
    //String to hold loggin tag text which will contain "DetailedActivityFragment" as string.
    final static private String LOG_TAG = DetailedActivityFragment.class.getSimpleName();
    //object of sharedActionProvider that sticks the share feature inside Actionbar.
    private ShareActionProvider mActionProvider;
    //String variable will store forecast data.
    private String mForecastString;
    final static private String sunshineAppHashtag = " #Sunshine App";
    //mIntent is the Intent that holds forecast data from MainActivityFragment.
    Intent mIntent ;

    public DetailedActivityFragment() {
        //to tell DetailedActivity that this fragment has options menu.
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the forecast from the Intent.
        mIntent = getActivity().getIntent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //get menu options, which is SharedActionProvider.
        MenuItem item = menu.findItem(R.id.action_share);
        //using MenuItemCompat to support older versions of Android.
        mActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        //check if mActionProvider is not null
        if(mActionProvider != null){
            //set shared Intent that hold data to be shared.
            mActionProvider.setShareIntent(setSharedIntent());
            Log.d(LOG_TAG, "Shared Intent has been set. ");
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);
        //inflate the textView that will hold forecast data.
        TextView text = (TextView) view.findViewById(R.id.forecast_textview);
        //now bring the data that came from MainActivityFragment and call it by key Intent.Extra_Text.
        mForecastString = mIntent.getStringExtra(Intent.EXTRA_TEXT);
        //set the data to textView.
        text.setText(mForecastString);

        return view;
    }

    //helper method to load an Intent with data to be shared using SharedProvider.
    private Intent setSharedIntent(){
        //set the action to do to Action_send so the Intent system understands that this Intent holds data to be shared outside the App.
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        //set the data type to string.
        shareIntent.setType("text/plain");
        //after sharing string using external app this flag
        // returns the user to Sunshine app rather than returning him to a new instance of the app.
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        //upload the data to the intent and give it key name as Intent.Extra_TEXT, "Don't give it custom key name or the Intent system
        // will not understand that this data is for sharing".
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastString + sunshineAppHashtag);

        Log.d(LOG_TAG, mForecastString+sunshineAppHashtag);
        return shareIntent;
    }

}
