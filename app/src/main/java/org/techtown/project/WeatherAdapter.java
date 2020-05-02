package org.techtown.project;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private ArrayList<TelDTO> mDataset;
    Button btn_conn;
    TextView tv_location;
    TextView tv_weather;
   // TextView tv_time;
    TextView tv_icon;
    TextView tv_temp;
    TextView tv_windspeed;
    TextView tv_cloud;
  //  TextView tv_sunrise;
  //  TextView tv_sunset;
    TextView tv_timezone;
 //   TextView tv_t;
    TextView tv_view;


    TextView temp1;
    TextView temp2;
    TextView temp3;
    TextView temp4;
    TextView time1;
    TextView time2;
    TextView time3;
    TextView time4;


    static RequestQueue requestQueue;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView tv_location;
        TextView tv_weather;
       // TextView tv_time;
        //TextView tv_icon;
        TextView tv_temp;
        TextView tv_windspeed;
        TextView tv_cloud;
        TextView tv_sunrise;
        TextView tv_sunset;
        Button button;
        TextView tv_timezone;
        TextView tv_t;
        TextView tv_view;
        View v;
        TextView temp1;
        TextView temp2;
        TextView temp3;
        TextView temp4;
        TextView time1;
        TextView time2;
        TextView time3;
        TextView time4;
        public MyViewHolder(View v) {
            super(v);
//              textView = v.findViewById(R.id.tv_today);
            this.v = v;
           // button = v.findViewById(R.id.btn_conn);
            tv_location = v.findViewById(R.id.tv_location);
           // tv_weather = v.findViewById(R.id.tv_date);
           // tv_time = v.findViewById(R.id.tv_time);
            //tv_icon = v.findViewById(R.id.tv_icon);
            tv_temp = v.findViewById(R.id.tv_temp);
            tv_windspeed = v.findViewById(R.id.tv_windspeed);
            tv_cloud = v.findViewById(R.id.tv_cloud);
          //  tv_sunrise = v.findViewById(R.id.tv_sunrise);
           // tv_sunset = v.findViewById(R.id.tv_sunset);
            tv_timezone = v.findViewById(R.id.tv_timezone);
      //      tv_t = v.findViewById(R.id.tv_t);
            tv_view = v.findViewById(R.id.tv_view);
            temp1 = v.findViewById(R.id.temp1);
            temp2 = v.findViewById(R.id.temp2);
            temp3 = v.findViewById(R.id.temp3);
            temp4 = v.findViewById(R.id.temp4);
            time1 = v.findViewById(R.id.time1);
            time2 = v.findViewById(R.id.time2);
            time3 = v.findViewById(R.id.time3);
            time4 = v.findViewById(R.id.time4);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public WeatherAdapter(ArrayList<TelDTO> myDataset) {
        mDataset = myDataset;
    }
//    public TextView textView;
//    Button button;
    // Create new views (invoked by the layout manager)

    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

       // btn_conn = v.findViewById(R.id.btn_conn);
        tv_location = v.findViewById(R.id.tv_location);
       // tv_weather = v.findViewById(R.id.tv_date);
        //tv_time = v.findViewById(R.id.tv_time);
        //tv_icon = v.findViewById(R.id.tv_icon);
        tv_temp = v.findViewById(R.id.tv_temp);
        tv_windspeed = v.findViewById(R.id.tv_windspeed);
        tv_cloud = v.findViewById(R.id.tv_cloud);
      //  tv_sunrise = v.findViewById(R.id.tv_sunrise);
      //  tv_sunset = v.findViewById(R.id.tv_sunset);
        tv_timezone = v.findViewById(R.id.tv_timezone);
    //    tv_t = v.findViewById(R.id.tv_t);
        tv_view = v.findViewById(R.id.tv_view);
        temp1 = v.findViewById(R.id.temp1);
        temp2 = v.findViewById(R.id.temp2);
        temp3 = v.findViewById(R.id.temp3);
        temp4 = v.findViewById(R.id.temp4);
        time1 = v.findViewById(R.id.time1);
        time2 = v.findViewById(R.id.time2);
        time3 = v.findViewById(R.id.time3);
        time4 = v.findViewById(R.id.time4);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tv_location.setText(mDataset.get(position).getName());

//        holder.button.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View v) {

                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(holder.v.getContext());
                    //requestQueue = Volley.newRequestQueue(getApplicationContext());
                }

                String url = "https://api.openweathermap.org/data/2.5/onecall?lat=37.57&lon=126.98&appid=dbf3abee8d29ca1bd9cefa8675b55c52&units=metric";

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        Log.v("hhd",""+(response));

                        String cloudvar = "";

                        try {
                            JSONObject obj = new JSONObject(response);

                            String timezone = obj.getString("timezone");
                            Log.v("hhd",""+(timezone));

                            JSONObject current = obj.getJSONObject("current");
                            int temp = current.getInt("temp");
                            Log.v("hhd",""+(temp));

                            int dt = current.getInt("dt");
                            Log.v("hhd",""+(dt));

                            int sunrise = current.getInt("sunrise");
                            Log.v("hhd",""+(sunrise));

                            int sunset = current.getInt("sunset");
                            Log.v("hhd",""+(sunset));

                            double wind_speed = current.getDouble("wind_speed");
                            Log.v("hhd",""+(wind_speed));

                            int clouds = current.getInt("clouds");
                            Log.v("hhd",""+(clouds));

                            //실시간으로 날짜 받아오기
                            JSONArray daily = obj.getJSONArray("daily");
                            Log.v("hhd_daily",""+daily);
                            JSONObject one = (JSONObject)daily.get(1);
                            Log.v("hhd_one",one.toString());

                            JSONArray hourly3 = obj.getJSONArray("hourly");
                            Log.v("hhd_hourly3",""+hourly3);
                            JSONObject da = (JSONObject)hourly3.get(3);
                            Log.v("hhd_one",da.toString());
                            int hourlyA = da.getInt("temp");
                            Log.v("hhd",""+(hourlyA));

                            JSONArray hourly6 = obj.getJSONArray("hourly");
                            Log.v("hhd_hourly6",""+hourly6);
                            JSONObject da2 = (JSONObject)hourly6.get(6);
                            Log.v("hhd_one",da2.toString());
                            int hourlyB = da2.getInt("temp");
                            Log.v("hhd",""+(hourlyB));

                            JSONArray hourly9 = obj.getJSONArray("hourly");
                            Log.v("hhd_hourly9",""+hourly9);
                            JSONObject da3 = (JSONObject)hourly6.get(6);
                            Log.v("hhd_one",da3.toString());
                            int hourlyC = da3.getInt("temp");
                            Log.v("hhd",""+(hourlyC));

                            JSONArray hourly12 = obj.getJSONArray("hourly");
                            Log.v("hhd_hourly12",""+hourly12);
                            JSONObject da4 = (JSONObject)hourly6.get(9);
                            Log.v("hhd_one",da4.toString());
                            int hourlyD = da2.getInt("temp");
                            Log.v("hhd",""+(hourlyD));

                            //실시간 온도 받아오기

                            JSONArray houlyTemp1 = obj.getJSONArray("hourly");
                            Log.v("hhd_houlyTemp1",""+houlyTemp1);
                            JSONObject te1 = (JSONObject)houlyTemp1.get(3);
                            Log.v("hhd_te1",te1.toString());
                            int tempA = te1.getInt("dt");
                            Log.v("hhd",""+(tempA));

                            JSONArray houlyTemp2 = obj.getJSONArray("hourly");
                            Log.v("hhd_houlyTemp2",""+houlyTemp2);
                            JSONObject te2 = (JSONObject)houlyTemp2.get(6);
                            Log.v("hhd_te2",te2.toString());
                            int tempB = te2.getInt("dt");
                            Log.v("hhd",""+(tempB));

                            JSONArray houlyTemp3 = obj.getJSONArray("hourly");
                            Log.v("houlyTemp3",""+houlyTemp1);
                            JSONObject te3 = (JSONObject)houlyTemp3.get(9);
                            Log.v("hhd_te3",te3.toString());
                            int tempC = te3.getInt("dt");
                            Log.v("hhd",""+(tempC));

                            JSONArray houlyTemp12 = obj.getJSONArray("hourly");
                            Log.v("hhd_houlyTemp12",""+houlyTemp12);
                            JSONObject te4 = (JSONObject)houlyTemp12.get(12);
                            Log.v("hhd_te4",te4.toString());
                            int tempD = te4.getInt("dt");
                            Log.v("hhd",""+(tempD));



                         //   JSONObject temp2 = (JSONObject)one.getJSONObject("temp");
                         //   Log.v("hhd_temp2",temp2.toString());


                            //날짜
                           // double day = temp2.getDouble("day");
                          //  Log.v("hhd_day",""+(day));

                            long curTime = System.currentTimeMillis();
                           // Date dt_final = new Date(curTime);

                            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            TimeZone.getTimeZone("Asia/Seoul");
                            Locale.setDefault(Locale.KOREA);

                            String time = sdf.format(new Date(System.currentTimeMillis()));
                            String str = sdf.format(new Date(curTime));
                            System.out.println(str);
                            System.out.println(new Date(curTime));
                            String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));


                            //unix 변환
                            Log.d("변환되기전의 h ",Integer.toString(tempA));
                            long dv = Long.valueOf(tempA)*1000;
                            Date df = new java.util.Date(dv);
                            String vv1 = new SimpleDateFormat("hh").format(df);
                            Log.d("3시간뒤의 h ",vv1);

                            long dv2 = Long.valueOf(tempB)*1000;
                            Date df2 = new java.util.Date(dv2);
                            String vv2 = new SimpleDateFormat("hh").format(df2);

                            long dv3 = Long.valueOf(tempC)*1000;
                            Date df3 = new java.util.Date(dv3);
                            String vv3 = new SimpleDateFormat("hh").format(df3);

                            long dv4 = Long.valueOf(tempD)*1000;
                            Date df4 = new java.util.Date(dv4);
                            String vv4 = new SimpleDateFormat("hh").format(df4);

                            //실시간으로 온도와 날짜를 받아서 보여주는 칸

                            temp1.append(  hourlyA  + "ºC"+ "\n");
                            temp2.append(  hourlyB  + "ºC"+ "\n");
                            temp3.append(  hourlyC  + "ºC"+ "\n");
                            temp4.append(  hourlyD  + "ºC"+ "\n");
                            time1.append(  vv1 + "시 " + "\n");
                            time2.append(  vv2 + "시 "+ "\n");
                            time3.append(  vv3 + "시 " + "\n");
                            time4.append(  vv4 + "시 " + "\n");


                            tv_timezone.append( timezone   + "\n");
                            tv_temp.append( temp   + "ºC"+ "\n");
                            tv_windspeed.append( Double.toString(wind_speed)  +"m/s" + "\n");

                            //시간이 나오는 함수
                            tv_view.append( str+ "\n" );

                            if(clouds >= 8){
                                cloudvar = "(흐림)";
                            }else{
                                cloudvar = "(맑음)";
                            }
                            tv_cloud.append( clouds  + cloudvar +  "\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
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


//            }
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }




}