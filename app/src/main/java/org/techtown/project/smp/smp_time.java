package org.techtown.project.smp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.Arrays;
import java.util.List;

public class smp_time extends Fragment {
    View view;
    private RequestQueue requestQueue;
    private LineChart mChart;
    Button btn_time;
    ArrayList<Float> smp_present_list = new ArrayList<>();
    ArrayList<Float> smp_future1_list = new ArrayList<>();
    ArrayList<Float> smp_future2_list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_smp_time, container, false);
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }
        mChart = view.findViewById(R.id.smp_time_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        sendRequest();

        btn_time = view.findViewById(R.id.btn_time);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smp_main smp_main = new smp_main();
                getParentFragmentManager().beginTransaction().replace(R.id.framelayout, smp_main).commit();
            }
        });
        return view;
    }
    private void sendRequest(){

        String url = "http://172.30.1.43:9001/re/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        smp[] smp = gson.fromJson(response, smp[].class);
                        List<smp> list = Arrays.asList((smp));

                        Log.v("hhd", "현재 list size : "+list.size());
                        for(int i=0; i<list.size()-3;i++) {
                            smp_present_list.add(list.get(i).getPresent());
                            Log.v("hhd", "현재 smp : "+smp_present_list.get(i));
                        }
                        for(int i=0; i<list.size()-3;i++) {
                            smp_future1_list.add(list.get(i).getFuture1());
                            Log.v("hhd", "future1 smp : "+smp_future1_list.get(i));
                        }
                        for(int i=0; i<list.size()-3;i++) {
                            smp_future2_list.add(list.get(i).getFuture2());
                            Log.v("hhd", "future2 smp : "+smp_future2_list.get(i));
                        }
                        Log.v("hhd", "현재 smp size : "+smp_present_list.size());

                        ArrayList<Entry> present = new ArrayList<>();
                        ArrayList<Entry> future1 = new ArrayList<>();
                        ArrayList<Entry> future2 = new ArrayList<>();

                        for(int i = 0; i<smp_present_list.size();i++) {
                            present.add(new Entry(i, smp_present_list.get(i)));
                            future1.add(new Entry(i, smp_future1_list.get(i)));
                            future2.add(new Entry(i, smp_future2_list.get(i)));
                        }

                        LineDataSet set1 = new LineDataSet(present,"present");
                        LineDataSet set2 = new LineDataSet(future1,"future1");
                        LineDataSet set3 = new LineDataSet(future2, "future2");
                        mChart.getXAxis().setDrawGridLines(false);
                        mChart.getAxisLeft().setDrawGridLines(false);
                        mChart.getAxisRight().setDrawGridLines(false);
                        set1.setDrawValues(true);
                        set1.setCircleRadius(1f);
                        set1.setFillAlpha(110);
                        set1.setColor(Color.DKGRAY);
                        set1.setLineWidth(3f);
                        set1.setValueTextSize(0);
                        set1.setValueTextColor(Color.BLACK);
                        set2.setDrawValues(true);
                        set2.setCircleRadius(1f);
                        set2.setFillAlpha(110);
                        set2.setColor(Color.GRAY);
                        set2.setLineWidth(3f);
                        set2.setValueTextSize(0);
                        set2.setValueTextColor(Color.BLACK);
                        set3.setDrawValues(true);
                        set3.setCircleRadius(1f);
                        set3.setFillAlpha(110);
                        set3.setColor(Color.GRAY);
                        set3.setLineWidth(3f);
                        set3.setValueTextSize(0);
                        set3.setValueTextColor(Color.BLACK);

                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        dataSets.add(set2);
                        dataSets.add(set3);
                        LineData data = new LineData(dataSets);
                        mChart.setData(data);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd","time 에러 => "+ error.getNetworkTimeMs());
                        Log.v("hhd","time 에러 => "+ String.valueOf(error));
                    }
                }
        );
        request.setShouldCache(false);
        request.setShouldRetryServerErrors(true);
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                30000 ,

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);

    }
}
