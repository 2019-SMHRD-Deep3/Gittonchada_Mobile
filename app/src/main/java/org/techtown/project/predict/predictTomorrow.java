package org.techtown.project.predict;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.techtown.project.R;

import java.util.ArrayList;

public class predictTomorrow extends Fragment {

    //bar//
    private LineChart mChart;
    ArrayList<Entry> entry_chart = new ArrayList<>();
    // private  static  final String TAG = "predict_Fragment";

    ArrayList<Float> predictlist = new ArrayList<>();
    //bar//
    private TextView solar_generation;
    //가져온코드
    RequestQueue requestQueue;
    Predict predict = null;
    ProgressBar progressBar2;
    Button btn_today;
    View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.predict_tomorrow, container, false);
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }
        Log.v("hhd","tomorrow hi");
        // Log.v("hhd","응답 => " + response);

        mChart = v.findViewById(R.id.linechart2);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        sendRequest();
        progressBar2 = v.findViewById(R.id.weatherProgressBar2);
        btn_today = v.findViewById(R.id.btn_today);

        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.techtown.project.predict.predictFragment predictFragment= new org.techtown.project.predict.predictFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.framelayout , predictFragment).commit();
            }
        });
        return v;
    }

    public void sendRequest() {

        String url = "http://172.30.1.43:9002/re/get_generation";
        StringRequest request = new StringRequest(

                Request.Method.POST,
                url,

                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {

                        Log.v("gson 실행전", response);
                        Gson gson = new Gson();
                        Predict jsonStr = gson.fromJson(response, Predict.class);
                        predict = (new Gson()).fromJson(response, Predict.class);
                        if (predict == null) {
                            return;
                        }
                        ArrayList<Entry> yValue = new ArrayList<>();
//                            yValue.add(new Entry(1,predict.solar_generation0));
//                            yValue.add(new Entry(2,predict.solar_generation1));
//                            yValue.add(new Entry(3, predict.solar_generation2));
//                            yValue.add(new Entry(4, predict.solar_generation4));
//                            yValue.add(new Entry(5, predict.solar_generation5));
//                            yValue.add(new Entry(6, predict.solar_generation6));
//                            yValue.add(new Entry(7, predict.solar_generation7));
//                            yValue.add(new Entry(8, predict.solar_generation8));
//                            yValue.add(new Entry(9, predict.solar_generation9));
//                            yValue.add(new Entry(10, predict.solar_generation10));
//                            yValue.add(new Entry(11, predict.solar_generation11));
//                            yValue.add(new Entry(12, predict.solar_generation12));
//                            yValue.add(new Entry(13, predict.solar_generation13));
//                            yValue.add(new Entry(14, predict.solar_generation14));
//                            yValue.add(new Entry(15, predict.solar_generation15));
//                            yValue.add(new Entry(16, predict.solar_generation16));
//                            yValue.add(new Entry(17, predict.solar_generation17));
//                            yValue.add(new Entry(18, predict.solar_generation18));
//                            yValue.add(new Entry(19, predict.solar_generation19));
//                            yValue.add(new Entry(20, predict.solar_generation20));
//                            yValue.add(new Entry(21, predict.solar_generation21));
//                            yValue.add(new Entry(22, predict.solar_generation22));
//                            yValue.add(new Entry(23, predict.solar_generation23));
                        yValue.add(new Entry(24, predict.solar_generation24));
                        yValue.add(new Entry(25, predict.solar_generation25));
                        yValue.add(new Entry(26, predict.solar_generation26));
                        yValue.add(new Entry(27, predict.solar_generation27));
                        yValue.add(new Entry(28, predict.solar_generation28));
                        yValue.add(new Entry(29, predict.solar_generation29));
                        yValue.add(new Entry(30, predict.solar_generation30));
                        yValue.add(new Entry(31, predict.solar_generation31));
                        yValue.add(new Entry(32, predict.solar_generation32));
                        yValue.add(new Entry(33, predict.solar_generation33));
                        yValue.add(new Entry(34, predict.solar_generation34));
                        yValue.add(new Entry(35, predict.solar_generation35));
                        yValue.add(new Entry(36, predict.solar_generation36));
                        yValue.add(new Entry(37, predict.solar_generation37));
                        yValue.add(new Entry(38, predict.solar_generation38));
                        yValue.add(new Entry(39, predict.solar_generation39));
                        yValue.add(new Entry(40, predict.solar_generation40));
                        yValue.add(new Entry(41, predict.solar_generation41));
                        yValue.add(new Entry(42, predict.solar_generation42));
                        yValue.add(new Entry(43, predict.solar_generation43));
                        yValue.add(new Entry(44, predict.solar_generation44));
                        yValue.add(new Entry(45, predict.solar_generation45));
                        yValue.add(new Entry(46, predict.solar_generation46));
                        yValue.add(new Entry(47, predict.solar_generation47));

                        LineDataSet set1 = new LineDataSet(yValue,"실시간 발전량");

                        set1.setFillAlpha(110);
                        set1.setColor(Color.DKGRAY);
                        set1.setLineWidth(3f);
                        set1.setValueTextSize(8f);
                        set1.setValueTextColor(Color.RED);
                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        LineData data = new LineData(dataSets);
                        mChart.setData(data);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();

                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        mChart.getXAxis().setDrawGridLines(false);
                        mChart.getAxisLeft().setDrawGridLines(false);
                        mChart.getAxisRight().setDrawGridLines(false);

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


//        private void requestPredict() {
//
//            if (requestQueue == null) { return; }
//            if (progressBar.getVisibility() == View.VISIBLE) { return; }
//            else {
//                progressBar.setVisibility(View.VISIBLE);
//            }


        request.setShouldCache(false);
        request.setShouldRetryServerErrors(true);
        // 커스텀 정책을 생성하여 지정한다.
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                10000 ,

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }
}


