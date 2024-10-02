package com.example.weatherappstartingassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    String url, url2;
    EditText userZip;
    String enteredzip;
    Button run;
    String result,result2, latitude, longitude;
    JSONObject jsonObject, jsonObject2;
    TextView ran, latitudeText, longitudeText, cityText, currentTempText, timeText1, timeText2, timeText3, timeText4, currentTimeText, tempText1, tempText2, tempText3, tempText4, descText1, descText2, descText3, descText4;
    ImageView pic1, pic2, pic3, pic4;
    LinearLayout ly1, ly2, ly3, ly4;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run = findViewById(R.id.Run);
        userZip = findViewById(R.id.userZip);
        latitudeText = findViewById(R.id.latitude);
        longitudeText = findViewById(R.id.longitude);
        cityText = findViewById(R.id.city);
        currentTempText = findViewById(R.id.currentTemp);
        tempText1 = findViewById(R.id.Temp1);
        tempText2 = findViewById(R.id.temp2);
        tempText3 = findViewById(R.id.temp3);
        tempText4 = findViewById(R.id.temp4);
        timeText1 = findViewById(R.id.time1);
        timeText2 = findViewById(R.id.time2);
        timeText3 = findViewById(R.id.time3);
        timeText4 = findViewById(R.id.time4);
        currentTimeText = findViewById(R.id.currentTime);
        descText1 = findViewById(R.id.Desc1);
        descText2 = findViewById(R.id.desc2);
        descText3 = findViewById(R.id.desc3);
        descText4 = findViewById(R.id.desc4);
        pic1 = findViewById(R.id.picture1);
        pic2 = findViewById(R.id.picture2);
        pic3 = findViewById(R.id.picture3);
        pic4 = findViewById(R.id.picture4);
        ly1 = findViewById(R.id.linearLayout);
        ly2 = findViewById(R.id.linearLayout2);
        ly3 = findViewById(R.id.linearLayout3);
        ly4 = findViewById(R.id.linearLayout4);
        ran = findViewById(R.id.textView7);


        userZip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enteredzip = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonTask js = new jsonTask();
                js.execute();
            }
        });



    }

    class jsonTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            try {
                url = "https://api.openweathermap.org/geo/1.0/zip?zip=" + enteredzip.toString() + ",US&appid=5e9c4e794b947b540c784ecac310c3c4";

                URL myurl = new URL(url);
                URLConnection urlConnection = myurl.openConnection();
                InputStreamReader streamReader = new InputStreamReader(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder builder = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                }
                result = builder.toString();


            } catch(Exception e){}

            return result;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try
            {
                jsonObject = new JSONObject(s);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                latitude = jsonObject.get("lat").toString();
                longitude = jsonObject.get("lon").toString();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            jsonTask2 js2 = new jsonTask2();
            js2.execute();


        }
    }

    //
    class jsonTask2 extends AsyncTask<Void, Void, String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... voids)
        {
            try {
                url2 = "https://api.openweathermap.org/data/2.5/onecall?lat=" + latitude.toString()+ "&lon=" + longitude.toString()+ "&exclude=daily,minutely,alerts&units=imperial&appid=5e9c4e794b947b540c784ecac310c3c4";

                URL myurl2 = new URL(url2);
                URLConnection urlConnection2 = myurl2.openConnection();
                InputStreamReader streamReader2 = new InputStreamReader(urlConnection2.getInputStream());

                BufferedReader reader2 = new BufferedReader(streamReader2);
                StringBuilder builder2 = new StringBuilder();
                String line2;

                while((line2 = reader2.readLine()) != null)
                {
                    builder2.append(line2);
                }
                result2 = builder2.toString();

            } catch(Exception e){}

            return result2;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try {
                jsonObject2 = new JSONObject(s);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            latitudeText.setText("Latitude: " + latitude);
            longitudeText.setText("Longitude: " + longitude);
            try {
                cityText.setText(jsonObject.get("name").toString());

                long time1 = (int)jsonObject2.getJSONArray("hourly").getJSONObject(0).get("dt");
                long time2 = (int)jsonObject2.getJSONArray("hourly").getJSONObject(1).get("dt");
                long time3 = (int)jsonObject2.getJSONArray("hourly").getJSONObject(2).get("dt");
                long time4 = (int)jsonObject2.getJSONArray("hourly").getJSONObject(3).get("dt");
                long timeC = (int)jsonObject2.getJSONObject("current").get("dt");
                switch(jsonObject2.getString("timezone"))
                {
                    case "America/Los_Angeles":
                        time1 -=10800;
                        time2 -=10800;
                        time3 -=10800;
                        time4 -=10800;
                        timeC -=10800;
                        break;
                    case  "America/Detroit":
                        time1 -= 3600;
                        time2 -= 3600;
                        time3 -= 3600;
                        time4 -= 3600;
                        timeC -= 3600;
                        break;
                    case "America/New_York":
                        break;
                }
                Date date1 = new Date(time1*1000L);
                SimpleDateFormat jdf1 = new SimpleDateFormat("hh:mm aa");
                String formattedTime1 = jdf1.format(date1);
                timeText1.setText(formattedTime1);


                Date date2 = new Date(time2*1000L);
                SimpleDateFormat jdf2 = new SimpleDateFormat("hh:mm aa");
                String formattedTime2 = jdf2.format(date2);
                timeText2.setText(formattedTime2);


                Date date3 = new Date(time3*1000L);
                SimpleDateFormat jdf3 = new SimpleDateFormat("hh:mm aa");
                String formattedTime3 = jdf3.format(date3);
                timeText3.setText(formattedTime3);


                Date date4 = new Date(time4*1000L);
                SimpleDateFormat jdf4 = new SimpleDateFormat("hh:mm aa");
                String formattedTime4 = jdf4.format(date4);
                timeText4.setText(formattedTime4);


                Date dateC = new Date(timeC*1000L);
                SimpleDateFormat jdfC = new SimpleDateFormat("EEE, MMM d hh:mm aa");
                String formattedTimeC = jdfC.format(dateC);
                currentTimeText.setText(formattedTimeC);

                DecimalFormat dfC = new DecimalFormat("##");
                String actC = dfC.format(jsonObject2.getJSONObject("current").get("temp"));
                currentTempText.setText(actC + "°F");

                DecimalFormat df1 = new DecimalFormat("##");
                String act1 = df1.format(jsonObject2.getJSONArray("hourly").getJSONObject(0).get("temp"));

                DecimalFormat df2 = new DecimalFormat("##");
                String act2 = df2.format(jsonObject2.getJSONArray("hourly").getJSONObject(1).get("temp"));

                DecimalFormat df3 = new DecimalFormat("##");
                String act3 = df3.format(jsonObject2.getJSONArray("hourly").getJSONObject(2).get("temp"));

                DecimalFormat df4 = new DecimalFormat("##");
                String act4 = df4.format(jsonObject2.getJSONArray("hourly").getJSONObject(3).get("temp"));


                tempText1.setText(act1 + "°F");
                tempText2.setText(act2 + "°F");
                tempText3.setText(act3 + "°F");
                tempText4.setText(act4 + "°F");

                descText1.setText(jsonObject2.getJSONArray("hourly").getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("description").toString());
                descText2.setText(jsonObject2.getJSONArray("hourly").getJSONObject(1).getJSONArray("weather").getJSONObject(0).get("description").toString());
                descText3.setText(jsonObject2.getJSONArray("hourly").getJSONObject(2).getJSONArray("weather").getJSONObject(0).get("description").toString());
                descText4.setText(jsonObject2.getJSONArray("hourly").getJSONObject(3).getJSONArray("weather").getJSONObject(0).get("description").toString());

                String pic1desc = jsonObject2.getJSONArray("hourly").getJSONObject(0).getJSONArray("weather").getJSONObject(0).get("main").toString();
                String pic2desc = jsonObject2.getJSONArray("hourly").getJSONObject(1).getJSONArray("weather").getJSONObject(0).get("main").toString();
                String pic3desc = jsonObject2.getJSONArray("hourly").getJSONObject(2).getJSONArray("weather").getJSONObject(0).get("main").toString();
                String pic4desc = jsonObject2.getJSONArray("hourly").getJSONObject(3).getJSONArray("weather").getJSONObject(0).get("main").toString();

                switch(pic1desc)
                {
                    case "Clouds": pic1.setImageResource(R.drawable.cloud);
                        break;
                    case "Clear":pic1.setImageResource(R.drawable.clear);
                        break;
                    case "Snow":pic1.setImageResource(R.drawable.snowy);
                        break;
                    case"Rain":pic1.setImageResource(R.drawable.rainy);
                        break;
                }

                switch(pic2desc)
                {
                    case "Clouds": pic2.setImageResource(R.drawable.cloud);
                        break;
                    case "Clear":pic2.setImageResource(R.drawable.clear);
                        break;
                    case "Snow":pic2.setImageResource(R.drawable.snowy);
                        break;
                    case"Rain":pic2.setImageResource(R.drawable.rainy);
                        break;
                }

                switch(pic3desc)
                {
                    case "Clouds": pic3.setImageResource(R.drawable.cloud);
                        break;
                    case "Clear":pic3.setImageResource(R.drawable.clear);
                        break;
                    case "Snow":pic3.setImageResource(R.drawable.snowy);
                        break;
                    case"Rain":pic3.setImageResource(R.drawable.rainy);
                        break;
                }

                switch(pic4desc)
                {
                    case "Clouds": pic4.setImageResource(R.drawable.cloud);
                        break;
                    case "Clear":pic4.setImageResource(R.drawable.clear);
                        break;
                    case "Snow":pic4.setImageResource(R.drawable.snowy);
                        break;
                    case"Rain":pic4.setImageResource(R.drawable.rainy);
                        break;
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }

            ly1.setVisibility(View.VISIBLE);
            ly2.setVisibility(View.VISIBLE);
            ly3.setVisibility(View.VISIBLE);
            ly4.setVisibility(View.VISIBLE);
            ran.setVisibility(View.VISIBLE);
            currentTimeText.setVisibility(View.VISIBLE);
            currentTempText.setVisibility(View.VISIBLE);
            longitudeText.setVisibility(View.VISIBLE);
            latitudeText.setVisibility(View.VISIBLE);
            cityText.setVisibility(View.VISIBLE);













        }
    }

}
