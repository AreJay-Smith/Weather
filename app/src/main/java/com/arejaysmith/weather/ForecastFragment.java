package com.arejaysmith.weather;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    //Automatically called when created and happens before onCreateView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Tells the fragment that there is a menu
        setHasOptionsMenu(true);
    }

    //Inflates the new menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_refresh){

            FetchWeatherTask weatherTask = new FetchWeatherTask();
            weatherTask.execute("84606");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /***************************************************************
         * This is the static array and adapter stuff
         ***************************************************************/

        //Create string array of fake data.
        String[] forecasArray = {
                "Monday - Yup",
                "Tuesday - Hello",
                "Wednesday - Done",
                "Thursday - Snow",
                "Friday - Sun",
                "Saturday - Sex"
        };

        //Create a list of the array
        List<String> weekForecast = new ArrayList<String>(
                Arrays.asList(forecasArray)
        );

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecast
        );

        //Bind Adapter to Listview
        ListView lv = (ListView) rootView.findViewById(
                R.id.listview_forecast
        );

        lv.setAdapter(mAdapter);
     /***************************************************************/


        //Return the view to be displayed
        return rootView;
    }


    /***************************************************************
     * New Thread to make Http Connection and call JSON
     ***************************************************************/

    public  class FetchWeatherTask extends AsyncTask<String, Void, Void>{

        //List the name of the class
        private final String LOG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {

            if (params.length == 0){
                return null;
            }

            /***************************************************************
             * This is where we make the Http connection and grab the Json
             ***************************************************************/

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            //Declare variables that will make URI
            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String UNITS_PARAM = "units";
                final String DAYS_PARAM = "cnt";
                final String APPID_PARAM = "APPID";
                +
                        +                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        +                        .appendQueryParameter(QUERY_PARAM, params[0])
                        +                        .appendQueryParameter(FORMAT_PARAM, format)
                        +                        .appendQueryParameter(UNITS_PARAM, units)
                        +                        .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                        +                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                        +                        .build();
                +
                        +                URL url = new URL(builtUri.toString());
                +
                        +                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            /***************************************************************/

            return null;
        }
    }

}
