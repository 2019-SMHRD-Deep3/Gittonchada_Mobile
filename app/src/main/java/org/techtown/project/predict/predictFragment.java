package org.techtown.project.predict;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;

import org.techtown.project.R;

import java.util.ArrayList;


public class predictFragment extends Fragment {
//bar//
    private LineChart mChart;
    ArrayList<Entry> entry_chart = new ArrayList<>();
   // private  static  final String TAG = "predict_Fragment";

    ArrayList<Float> predictlist = new ArrayList<>();
    //bar//
    private TextView solar_generation;
    //가져온코드
    private RequestQueue requestQueue;
    private Predict predict = null;
    private ProgressBar progressBar;
    private Button btn_tomorrow, btn_today;
    private TextView tv_predict;
    private TextView tv_time;
    View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_predict, container, false);
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }
        // Log.v("hhd","응답 => " + response);

        mChart = v.findViewById(R.id.linechart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        tv_predict = v.findViewById(R.id.tv_predict);
        tv_time = v.findViewById(R.id.tv_view);

        sendRequest();

        progressBar = v.findViewById(R.id.predictProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        btn_tomorrow = v.findViewById(R.id.btn_tomorrow);
        btn_today = v.findViewById(R.id.btn_today);



        btn_tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            predictTomorrow predictTomorrow = new predictTomorrow();
            getParentFragmentManager().beginTransaction().replace(R.id.framelayout , predictTomorrow).commit();

            }
        });
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                predictTomorrow predictTomorrow = new predictTomorrow();
                getParentFragmentManager().beginTransaction().replace(R.id.framelayout , predictTomorrow).commit();
            }
        });

        return v;
    }

    public void sendRequest() {

        String url = "http://172.30.1.23:9002/re/get_generation";
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
                        yValue.add(new Entry(1,predict.solar_generation0));
                        yValue.add(new Entry(2,predict.solar_generation1));
                        yValue.add(new Entry(3, predict.solar_generation2));
                        yValue.add(new Entry(4, predict.solar_generation3));
                        yValue.add(new Entry(5, predict.solar_generation4));
                        yValue.add(new Entry(6, predict.solar_generation5));
                        yValue.add(new Entry(7, predict.solar_generation6));
                        yValue.add(new Entry(8, predict.solar_generation7));
                        yValue.add(new Entry(9, predict.solar_generation8));
                        yValue.add(new Entry(10, predict.solar_generation9));
                        yValue.add(new Entry(11, predict.solar_generation10));
                        yValue.add(new Entry(12, predict.solar_generation11));
                        yValue.add(new Entry(13, predict.solar_generation12));
                        yValue.add(new Entry(14, predict.solar_generation13));
                        yValue.add(new Entry(15, predict.solar_generation14));
                        yValue.add(new Entry(16, predict.solar_generation15));
                        yValue.add(new Entry(17, predict.solar_generation16));
                        yValue.add(new Entry(18, predict.solar_generation17));
                        yValue.add(new Entry(19, predict.solar_generation18));
                        yValue.add(new Entry(20, predict.solar_generation19));
                        yValue.add(new Entry(21, predict.solar_generation20));
                        yValue.add(new Entry(22, predict.solar_generation21));
                        yValue.add(new Entry(23, predict.solar_generation22));
                        yValue.add(new Entry(24, predict.solar_generation23));

                        LineDataSet set1 = new LineDataSet(yValue,"실시간 발전량");
                        YAxis yAxisRight = mChart.getAxisRight();
                        yAxisRight.setDrawLabels(false);
                        yAxisRight.setDrawAxisLine(false);
                        yAxisRight.setDrawGridLines(false);

                        set1.setCircleColor(Color.rgb(213, 213, 213));
                        set1.setCircleRadius(3f);
                        set1.setFillAlpha(110);
                        set1.setColor(Color.rgb(255, 167, 167));
                        set1.setLineWidth(3f);
                        set1.setValueTextSize(10f);
                        set1.setValueTextColor(Color.rgb(0, 0, 84));
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
                        progressBar.setVisibility(View.GONE);



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



