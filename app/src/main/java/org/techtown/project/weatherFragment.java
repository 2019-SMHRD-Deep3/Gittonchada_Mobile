package org.techtown.project;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // Data Object
    private Weather weather = null;
    private RequestQueue requestQueue = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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

                long curTime = System.currentTimeMillis();

                TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                TimeZone.getTimeZone("Asia/Seoul");
                Locale.setDefault(Locale.KOREA);
                String str = sdf.format(new Date(curTime));

                tv_view.setText(str);


                // Response Parse //////////////////////////////////////////////////////////////////
                weather = (new Gson()).fromJson(response, Weather.class);
                if (weather == null) {
                    return;
                }

                int clouds = weather.current.clouds;

                String cloudStatus = "(맑음)";
                if (clouds >= 8) {
                    cloudStatus = "(흐림)";
                }
                tv_cloud.setText(clouds + cloudStatus);

                // Start Index at 0.
                ArrayList<Integer> timeList = new ArrayList<>();
                timeList.add(2);timeList.add(5);timeList.add(8);timeList.add(11);

                ArrayList<TextView> tempTextViewList = new ArrayList<>();
                tempTextViewList.add(temp1);tempTextViewList.add(temp2);
                tempTextViewList.add(temp3);tempTextViewList.add(temp4);

                ArrayList<TextView> timeTextViewList = new ArrayList<>();
                timeTextViewList.add(time1);timeTextViewList.add(time2);
                timeTextViewList.add(time3);timeTextViewList.add(time4);

                clearSubViewTextView(tempTextViewList, timeTextViewList);

                for (Integer timeIndex : timeList) {

                    Integer addedTimeIndex = timeIndex;
                    if (switchMultiButton.getSelectedTab() == 1) {
                        addedTimeIndex += 12;
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
        textView.setText(new SimpleDateFormat("hh").format(df));
    }
}

