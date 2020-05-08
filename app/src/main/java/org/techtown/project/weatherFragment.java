package org.techtown.project;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.techtown.project.model.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import lib.kingja.switchbutton.SwitchMultiButton;

@SuppressLint({"SimpleDateFormat", "SetTextI18n"})
public class weatherFragment extends Fragment {

    // Sub views
    private TextView tv_location;
    private TextView tv_temp;
    private TextView tv_windspeed;
    private TextView tv_cloud;
    private TextView tv_view;
    private TextView temp1;
    private TextView temp2;
    private TextView temp3;
    private TextView temp4;
    private TextView time1;
    private TextView time2;
    private TextView time3;
    private TextView time4;
    private SwitchMultiButton switchMultiButton;
    private ProgressBar progressBar;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img_icon;
    private TextView tv_asia;
    // Data Object
    private Weather weather = null;
    private RequestQueue requestQueue = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        System.out.println();

        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        View view = inflater.inflate(R.layout.items, container, false);

        bindSubViews(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        requestWeather();
    }

    private void bindSubViews(View superView) {
        tv_location = superView.findViewById(R.id.tv_location);
        tv_temp = superView.findViewById(R.id.tv_temp);
        tv_windspeed = superView.findViewById(R.id.tv_windspeed);
        tv_cloud = superView.findViewById(R.id.tv_cloud);
        tv_view = superView.findViewById(R.id.tv_view);
        temp1 = superView.findViewById(R.id.temp1);
        temp2 = superView.findViewById(R.id.temp2);
        temp3 = superView.findViewById(R.id.temp3);
        temp4 = superView.findViewById(R.id.temp4);
        time1 = superView.findViewById(R.id.time1);
        time2 = superView.findViewById(R.id.time2);
        time3 = superView.findViewById(R.id.time3);
        time4 = superView.findViewById(R.id.time4);
        img1 = superView.findViewById(R.id.img1);
        img2 = superView.findViewById(R.id.img2);
        img3 = superView.findViewById(R.id.img3);
        img4 = superView.findViewById(R.id.img4);
        img_icon = superView.findViewById(R.id.img_icon);
        tv_asia = superView.findViewById(R.id.tv_asia);

        switchMultiButton = superView.findViewById(R.id.switchButton);
        switchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                requestWeather();
            }
        });

        progressBar = superView.findViewById(R.id.weatherProgressBar);
    }

    private void requestWeather() {

        if (requestQueue == null) { return; }
        if (progressBar.getVisibility() == View.VISIBLE) { return; }
        else {
            progressBar.setVisibility(View.VISIBLE);
        }

        String prefix = "https://api.openweathermap.org/data/2.5/onecall";
        double lat = 37.57;
        double lon = 126.98;
        String applicationID = "dbf3abee8d29ca1bd9cefa8675b55c52";

        String url = prefix;
        url += "?lat=" + lat;
        url += "&lon=" + lon;
        url += "&appid=" + applicationID;
        url += "&units=metric";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);

//                long curTime = System.currentTimeMillis();
//
//                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                TimeZone.getTimeZone("Asia/Seoul");
                Locale.setDefault(Locale.KOREA);
//                String str = sdf.format(new Date(curTime));

//                tv_view.setText(str);


                // Response Parse //////////////////////////////////////////////////////////////////
                weather = (new Gson()).fromJson(response, Weather.class);
                if (weather == null) {
                    return;
                }

                int clouds = weather.current.clouds;
                String cloudStatus = "운량 : 맑음";
                if (clouds >= 7) {
                    cloudStatus = "운량 : 흐림";
                }
                tv_cloud.setText(cloudStatus);

                String asia = weather.timezone;
                tv_asia.setText(asia);


                //img

                String houlytemp = weather.current.weather.get(0).description;
                if(houlytemp.equals("clear sky")){
                    img_icon.setImageResource(R.drawable.sunny1);
                }else if(houlytemp.equals("rainy")){
                    img_icon.setImageResource(R.drawable.rainy1);
                }else if(houlytemp.equals("overcast clouds")){
                    img_icon.setImageResource(R.drawable.clouds2);
                }else if(houlytemp.equals("scattered clouds")){
                    img_icon.setImageResource(R.drawable.cloud1);
                }else if(houlytemp.equals("few clouds")){
                    img_icon.setImageResource(R.drawable.cloud1);
                }else if(houlytemp.equals("broken clouds")){
                    img_icon.setImageResource(R.drawable.cloud1);
                }
                //img2
//                Log.v("hhd","weather : "+(weather==null));
//                Log.v("hhd","current : "+(weather.current==null));
//                Log.v("hhd","weather2 : "+(weather.current.weather==null));

                String tempClouds3 = weather.hourly.get(3).weather.get(0).icon;

                    if(tempClouds3.equals("01d")){
                        img1.setImageResource(R.drawable.sunny);
                    }else if(tempClouds3.equals("02d")){
                        img1.setImageResource(R.drawable.sunny);
                    }else if(tempClouds3.equals("03d")){
                        img1.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds3.equals("04d")){
                        img1.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds3.equals("09d")){
                        img1.setImageResource(R.drawable.rainy);
                    }else if(tempClouds3.equals("10d")){
                        img1.setImageResource(R.drawable.rainy);
                    }else if(tempClouds3.equals("11d")){
                        img1.setImageResource(R.drawable.thunder);
                    }else if(tempClouds3.equals("13d")){
                        img1.setImageResource(R.drawable.snowing);
                    }else if(tempClouds3.equals("01n")){
                        img1.setImageResource(R.drawable.nights);
                    }else if(tempClouds3.equals("02n")){
                        img1.setImageResource(R.drawable.nights);
                    }else if(tempClouds3.equals("03n")){
                        img1.setImageResource(R.drawable.nights);
                    }else if(tempClouds3.equals("04n")){
                        img1.setImageResource(R.drawable.nights);
                    }else if(tempClouds3.equals("09n")){
                        img1.setImageResource(R.drawable.rainy);
                    }else if(tempClouds3.equals("10n")){
                        img1.setImageResource(R.drawable.rainy);
                    }else if(tempClouds3.equals("11n")){
                        img1.setImageResource(R.drawable.rainy);
                    }else if(tempClouds3.equals("13n")) {
                        img1.setImageResource(R.drawable.nights);
                    }

                    String tempClouds6 = weather.hourly.get(6).weather.get(0).icon;
                    //ArrayList<Integer> img = new ArrayList<>();
                    if(tempClouds6.equals("01d")){
                        img2.setImageResource(R.drawable.sunny);
                    }else if(tempClouds6.equals("02d")){
                        img2.setImageResource(R.drawable.sunny);
                    }else if(tempClouds6.equals("03d")){
                        img2.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds6.equals("04d")){
                        img2.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds6.equals("09d")){
                        img2.setImageResource(R.drawable.rainy);
                    }else if(tempClouds6.equals("10d")){
                        img2.setImageResource(R.drawable.rainy);
                    }else if(tempClouds6.equals("11d")){
                        img2.setImageResource(R.drawable.thunder);
                    }else if(tempClouds6.equals("13d")){
                        img2.setImageResource(R.drawable.snowing);
                    }else if(tempClouds6.equals("01n")){
                        img2.setImageResource(R.drawable.nights);
                    }else if(tempClouds6.equals("02n")){
                        img2.setImageResource(R.drawable.nights);
                    }else if(tempClouds6.equals("03n")){
                        img2.setImageResource(R.drawable.nights);
                    }else if(tempClouds6.equals("04n")){
                        img2.setImageResource(R.drawable.nights);
                    }else if(tempClouds6.equals("09n")){
                        img2.setImageResource(R.drawable.rainy);
                    }else if(tempClouds6.equals("10n")){
                        img2.setImageResource(R.drawable.rainy);
                    }else if(tempClouds6.equals("11n")){
                        img2.setImageResource(R.drawable.rainy);
                    }else if(tempClouds6.equals("13n")) {
                        img2.setImageResource(R.drawable.nights);
                    }

                    String tempClouds9 = weather.hourly.get(9).weather.get(0).icon;
                    //ArrayList<Integer> img = new ArrayList<>();
                    if(tempClouds9.equals("01d")){
                        img3.setImageResource(R.drawable.sunny);
                    }else if(tempClouds9.equals("02d")){
                        img3.setImageResource(R.drawable.sunny);
                    }else if(tempClouds9.equals("03d")){
                        img3.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds9.equals("04d")){
                        img3.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds9.equals("09d")){
                        img3.setImageResource(R.drawable.rainy);
                    }else if(tempClouds9.equals("10d")){
                        img3.setImageResource(R.drawable.rainy);
                    }else if(tempClouds9.equals("11d")){
                        img3.setImageResource(R.drawable.thunder);
                    }else if(tempClouds9.equals("13d")){
                        img3.setImageResource(R.drawable.snowing);
                    }else if(tempClouds9.equals("01n")){
                        img3.setImageResource(R.drawable.nights);
                    }else if(tempClouds9.equals("02n")){
                        img3.setImageResource(R.drawable.nights);
                    }else if(tempClouds9.equals("03n")){
                        img3.setImageResource(R.drawable.nights);
                    }else if(tempClouds9.equals("04n")){
                        img3.setImageResource(R.drawable.nights);
                    }else if(tempClouds9.equals("09n")){
                        img3.setImageResource(R.drawable.rainy);
                    }else if(tempClouds9.equals("10n")){
                        img3.setImageResource(R.drawable.rainy);
                    }else if(tempClouds9.equals("11n")){
                        img3.setImageResource(R.drawable.rainy);
                    }else if(tempClouds9.equals("13n")) {
                        img3.setImageResource(R.drawable.nights);
                    }

                    String tempClouds12 = weather.hourly.get(12).weather.get(0).icon;
                    //ArrayList<Integer> img = new ArrayList<>();
                    if(tempClouds12.equals("01d")){
                        img4.setImageResource(R.drawable.sunny);
                    }else if(tempClouds12.equals("02d")){
                        img4.setImageResource(R.drawable.sunny);
                    }else if(tempClouds12.equals("03d")){
                        img4.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds12.equals("04d")){
                        img4.setImageResource(R.drawable.clouds2);
                    }else if(tempClouds12.equals("09d")){
                        img4.setImageResource(R.drawable.rainy);
                    }else if(tempClouds12.equals("10d")){
                        img4.setImageResource(R.drawable.rainy);
                    }else if(tempClouds12.equals("11d")){
                        img4.setImageResource(R.drawable.thunder);
                    }else if(tempClouds12.equals("13d")){
                        img4.setImageResource(R.drawable.snowing);
                    }else if(tempClouds12.equals("01n")){
                        img4.setImageResource(R.drawable.nights);
                    }else if(tempClouds12.equals("02n")){
                        img4.setImageResource(R.drawable.nights);
                    }else if(tempClouds12.equals("03n")){
                        img4.setImageResource(R.drawable.nights);
                    }else if(tempClouds12.equals("04n")){
                        img4.setImageResource(R.drawable.nights);
                    }else if(tempClouds12.equals("09n")){
                        img4.setImageResource(R.drawable.rainy);
                    }else if(tempClouds12.equals("10n")){
                        img4.setImageResource(R.drawable.rainy);
                    }else if(tempClouds12.equals("11n")){
                        img4.setImageResource(R.drawable.rainy);
                    }else if(tempClouds12.equals("13n")) {
                        img4.setImageResource(R.drawable.nights);}


                //temp
                Double temp = weather.current.temp;
                int tempk = Integer.parseInt(String.valueOf(Math.round(temp)));
                tv_temp.setText(tempk+"ºC");

                //temp
                Double windSpeed = weather.current.wind_speed;
                int windSpeed2 = Integer.parseInt(String.valueOf(Math.round(windSpeed)));
                tv_windspeed.setText("풍속 : "+windSpeed2+"m/s");

                String temp11 = "시";

                // Start Index at 0.
                ArrayList<Integer> timeList = new ArrayList<>();
                timeList.add(3);timeList.add(5);timeList.add(8);timeList.add(11);
                ArrayList<TextView> tempTextViewList = new ArrayList<>();
                tempTextViewList.add(temp1);tempTextViewList.add(temp2);
                tempTextViewList.add(temp3);tempTextViewList.add(temp4);

                ArrayList<TextView> timeTextViewList = new ArrayList<>();
                timeTextViewList.add(time1);timeTextViewList.add(time2);
                timeTextViewList.add(time3);timeTextViewList.add(time4);

//                ArrayList<TextView> imgLinearLayout = new ArrayList<>();
//                imgLinearLayout.add();imgLinearLayout.add();
//                imgLinearLayout.add();imgLinearLayout.add();

                clearSubViewTextView(tempTextViewList, timeTextViewList);

                for (Integer timeIndex : timeList) {

                    Integer addedTimeIndex = timeIndex;
                    if (switchMultiButton.getSelectedTab() == 1) {
                        addedTimeIndex += 10;
                    }
                    int index =timeList.indexOf(timeIndex);

                    updateHourlyTextView(
                            weather.hourly.get(addedTimeIndex),
                            tempTextViewList.get(index)
                    );

                    updateTimeTextView(
                            weather.hourly.get(addedTimeIndex),
                            timeTextViewList.get(index)
                    );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_location.append("에러");
            }
        });
        request.setShouldCache(false);   // 누를 때마다 새로운 정보를 출력해서 보여줌
        requestQueue.add(request);
    }

    private void clearSubViewTextView(ArrayList<TextView> tempTextViewList,
                                      ArrayList<TextView> timeTextViewList) {
        for (TextView textView : tempTextViewList) {
            textView.setText("");
        }

        for (TextView textView : timeTextViewList) {
            textView.setText("");
        }
    }

    private void updateHourlyTextView(Weather.Hourly hourly, TextView textView) {
        int hourlyTemp = hourly.temp.intValue();
        textView.setText(hourlyTemp + "ºC");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateTimeTextView(Weather.Hourly hourly, TextView textView) {
        long dv = hourly.dt * 1000;
        Date df = new java.util.Date(dv);
        textView.setText(new SimpleDateFormat("hh").format(df)+"시");
    }


}

