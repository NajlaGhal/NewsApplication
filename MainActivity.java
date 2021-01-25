package com.example.newsapp;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import java.util.ArrayList;
import java.util.List;

//Displays the recent news about Covid-19  based on responses from Guardian.

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Topics>> {

    //URL for Guardian data set
    private static final String GUAR_REQUEST_URL = "https://content.guardianapis.com/search?api-key=test";
            //"https://content.guardianapis.com/search?q=Covid-19&show-tags=contributor&api-key=test";

    private NewsAdapter nAdapter;
    private static final int news_loader = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = findViewById(R.id.list);
        nAdapter = new NewsAdapter(this, new ArrayList<Topics>());
        newsListView.setAdapter(nAdapter);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long I) {
                Topics currentTopic = nAdapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri topicUri = Uri.parse(currentTopic.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, topicUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(news_loader, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // display no interent as error msg
            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }

        @Override
        public Loader<List<Topics>> onCreateLoader(int i, Bundle bundle) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String section = sharedPrefs.getString("section", "health");
            Uri baseUri = Uri.parse(GUAR_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
           uriBuilder.appendQueryParameter("q","health");
            uriBuilder.appendQueryParameter("show-tags","contributor");
           uriBuilder.appendQueryParameter("api-key" ,"test");
          //uriBuilder.appendQueryParameter("show-fields", "thumbnail");
           uriBuilder.appendQueryParameter("section", section);

            return new NewsLoader(this, uriBuilder.toString());
        }

    @Override
    public void onLoadFinished(Loader<List<Topics>> loader, List<Topics> topic) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // display "No news to Display" for empty text
        mEmptyStateTextView.setText(R.string.no_news);
        nAdapter.clear();

        if (topic != null && !topic.isEmpty()) {
            nAdapter.addAll(topic);
           // updateUi(topic);
        }
        else {
            mEmptyStateTextView.setText("No news");
        }
    }
        @Override
        public void onLoaderReset(Loader<List<Topics>> loader) {
            // Loader reset, so we can clear out our existing data.
            nAdapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, NewsSettings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}