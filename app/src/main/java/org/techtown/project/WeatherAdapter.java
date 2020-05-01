package org.techtown.project;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


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

                            JSONArray daily = obj.getJSONArray("daily");
                            Log.v("hhd_daily",""+daily);

                            JSONObject one = (JSONObject)daily.get(1);
                            Log.v("hhd_one",one.toString());

                            JSONObject temp2 = (JSONObject)one.getJSONObject("temp");
                            Log.v("hhd_temp2",temp2.toString());

                            double day = temp2.getDouble("day");
                            Log.v("hhd_day",""+(day));



                            long curTime = System.currentTimeMillis();
                            Date dt_final = new Date(curTime);

                            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

                            String str = timeFormat.format(new Date(curTime));
                            System.out.println(str);
                            System.out.println(new Date(curTime));


                          //  dt_final.setSeconds(dt);
                           // long unixTimestamp = System.currentTimeMillis()/1000L;
                           // String dt_final2 = dt_final.getYear() + "년" + dt_final.getMonth() + "월" + dt_final.getDate() + "일";



                           // Date sunset_final = new Date(sunset*1000);
                          //  String sunset_final2 = sunset_final.getHours() + "시" + sunset_final.getMinutes() + "분" + sunset_final.getSeconds() + "초";


                            tv_timezone.append( timezone   + "\n");
                            //tv_weather.append(Double.toString(day)   + "\n");
                        //    tv_weather.append(dt + "\n");
                            //tv_time.append(Double.toString(day)   + "\n");
                            tv_temp.append( temp   + "º"+ "\n");
                            tv_windspeed.append( Double.toString(wind_speed)  +"m/s" + "\n");
                         //   tv_t.append( Double.toString(day)  + "\n");
                            tv_view.append( dt_final+ "\n" );

                            if(clouds >= 8){
                                cloudvar = "(흐림)";
                            }else{
                                cloudvar = "(맑음)";
                            }
                            tv_cloud.append( clouds  + cloudvar +  "\n");
                           // tv_sunrise.append(  sunrise   + "\n");
                           // tv_sunset.append(sunset   + "\n");

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