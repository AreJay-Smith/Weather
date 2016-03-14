package com.arejaysmith.weather;

/**
 * Created by Urge_Smith on 3/10/16.
 */

import android.provider.BaseColumns;
import android.text.format.Time;


public class WeatherContract {

    //Defines table and column names for the weather database.

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.

    public static long normalizeDate(long startDate) {
        // Normalize the start date to the beginning of the day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return  time.setJulianDay(julianDay);
    }

    /*
        Inner class that defines the table contents of the location table
        This is where you will add the strings.  (Similar to what has been
        done for WeatherEntry)
     */
    public static final class LocationEntry implements BaseColumns {
        public static  final String TABLE_NAME = "location";

        // the location setting string what will be sent to openweathermap
        // as the location query
        public static final String COLUMN_LOCATION_SETTING = "location_setting";

        // Human readable location string, provided by the API. Because for styling,
        // the city name is more recognizable than the sip code.
        public static final String COLUMN_CITY_NAME = "city_name";

        // In order to uniquely pinpoint the location on the map when we launch the map
        //intent, we store the latitude and longitude as returned by the openweather API
        public static final String COLUMN_COORD_LAT = "coord_late";
        public static final String COLUMN_COORD_LONG = "coord_long";



    }

    // Inner class that defines the contents of the weather table
    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        // Column with the foreign key into the locaiton table
        public static final String COLUMN_LOC_KEY = "location_id";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_DATE = "date";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_WEATHER_ID = "weather_id";

        // Short description and long description of the weather, as provided by API
        //e.g "clear vs "sky is clear"
        public static final String COLUMN_SHORT_DESC = "short_desc";

        // Min and max temperatures for the day (stored as floats)
        public  static final String COLUMN_MIN_TEMP = "min";
        public  static final String COLUMN_MAX_TEMP = "max";

        // Humidity is stored as a float representing percentage
        public static  final String COLUMN_HUMIDITY = "humidity";

        // Presure is stored as a float representing percentage
        public static final String COLUMN_PRESSURE = "pressure";

        // Windspeed is stored as a float representing windspeed  mph
        public static final String COLUMN_WIND_SPEED = "wind";

        // Degrees are meteorological degrees (e.g, 0 is north, 180 is south).  Stored as floats.
        public static final String COLUMN_DEGREES = "degrees";

    }

}
