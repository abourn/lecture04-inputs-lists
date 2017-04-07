package edu.uw.inputlistdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);


        /* first example of using Event Listeners
//         you have to cast because findViewById returns a view.
//         this is an abstracted view. but we know that it is a button,
//         so we cast it as a button so that we can treat it as such.
//         we would get a casting exception if btnSearch wasn't actually
//         a button but instead, say, a TextField
        Button searchButton = (Button)findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "The button hath been click-ed");
            }
        })
        */


//        model
//        String[] data = new String[99];
//        for (int i = 99; i > 0; i--) {
//            data[99-i] = i+" bottles of beer on the wall";
//        }

        ArrayList<String> data = new ArrayList<String>();



//      view: See the list_item.xml


//      controller
//      need to convert from string --> view
//      uses a mapping process called an Adapter.  Which adapts between the model & view
//      four parameters in this adapter.  Context, resource, textViewResource, and objects
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.txtItem, data);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }

//  the function that we specified in the xml
    public void handleButtonSearch(View v) {
        EditText searchQuery = (EditText)findViewById(R.id.txtSearch);
//      getText returns a String buffer, so we have to call toString() in
//      order to save it as a String
        String searchTerm = searchQuery.getText().toString();
        Log.v(TAG,"You searched for "+ searchTerm);
        MovieDownloadTask myTask = new MovieDownloadTask();
//      execute() will start up the doInBackground method.  Equivalent of .start() in pure java
        myTask.execute(searchTerm);
    }

    public class MovieDownloadTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            String[] results = MovieDownloader.downloadMovieData(strings[0]);
//            for(String movie : results) {
//                Log.v(TAG, movie);
//            }
            return results;
        }

        @Override
        protected void onPostExecute(String[] movies) {
            super.onPostExecute(movies);
            if (movies != null) {
                adapter.clear();
                for(String movie: movies) {
                    adapter.add(movie);
                }
            }
        }
    }
}
