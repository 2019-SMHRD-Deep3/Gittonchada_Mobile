package org.techtown.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.techtown.project.model.Predict;
import org.techtown.project.model.Weather;

import java.util.ArrayList;

import lib.kingja.switchbutton.SwitchMultiButton;


public class predictFragment extends Fragment {

    LineChart lineChart;
    ArrayList<Entry> entry_chart = new ArrayList<>();
    TextView tv_ac, tv_pr;

    private TextView solar_radiation;
    private TextView solar_sunshine;
    private TextView solar_generation;
    //가져온코드
    private RequestQueue requestQueue = null;
    private Predict predict = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }


        // Log.v("hhd","응답 => " + response);
        View v = inflater.inflate(R.layout.fragment_predict, container, false);

        tv_ac = (TextView) v.findViewById(R.id.tv_ac);
        tv_pr = (TextView) v.findViewById(R.id.tv_pr);
        sendRequest();

        return v;
    }
//    public class NoConnectionError extends NetworkError {
//        public NoConnectionError() {
//            super();
//        }
//
//        public NoConnectionError(Throwable reason) {
//            super(reason);
//        }
//    }
    private void sendRequest() {
        String url = "http://172.30.1.23:9000/re/5/6/1/14.07/0/0.93/4/81/5";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {

                        Log.v("hhd", "응답 => " + response);
                        Log.v("hhd", "응답 => " + response.length());
                        /* String chValue = response.replace("\"","");
                        chValue = chValue.replace("\\","");
                        */

                        try {
                            // Response Parse //////////////////////////////////////////////////////////////////
                            predict = (new Gson()).fromJson(response, Predict.class);
                            if (predict == null) {
                                return;
                            }
                            //값 받아오기
                            Double solar_radiation = predict.solar_radiation;
                            tv_pr.setText(solar_radiation+"d");
                            Log.v("hhd", "찍힘? => " + solar_radiation);

                            Double solar_sunshine = predict.solar_sunshine;
                            tv_ac.setText(solar_sunshine+"d");
                            Log.v("hhd", "찍힘? => " + solar_sunshine);

                            Double solar_generation = predict.solar_generation;
                            tv_ac.setText(solar_generation+"d");
                            Log.v("hhd", "찍힘? => " + solar_generation);

                         //   JSONArray jsonArray = new JSONArray(response);
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject predict = jsonArray.getJSONObject(i);
//                                Log.v("hhd", "성공" + predict.getString("성공"));
//                            }
                            /*jsonObject = jsonArray.getJSONObject(0);
                            Log.v("myValueTest",jsonArray.length()+"");
                            Log.v("myValueTest",jsonObject.toString());
*/
                        } catch (Exception e) {
                            Log.v("hhd", "실패");
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd", "에러 => " + error.getNetworkTimeMs());
                        Log.v("hhd", "에러 => " + String.valueOf(error));
                    }
                }
        );

        // 커스텀 정책을 생성하여 지정한다.
//        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(
//
//                50000 ,
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//
//                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }
}



