package org.techtown.project.smp;

import android.content.Context;
import android.content.Intent;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import org.techtown.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class smp_time extends Fragment {
    View view;
    private RequestQueue requestQueue;
    private LineChart mChart;
    private ProgressBar progressBar;
    TextView tv_label2, tv_smp2;
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

        tv_label2 = view.findViewById(R.id.tv_label2);
        tv_smp2 = view.findViewById(R.id.tv_smp2);
        tv_label2.setVisibility(View.INVISIBLE);
        tv_smp2.setVisibility(view.INVISIBLE);
        sendRequest();
        progressBar = view.findViewById(R.id.timeProgressBar);
        progressBar.setVisibility(View.VISIBLE);

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
                        set1.setDrawValues(false);
                        set1.setCircleRadius(1f);
                        set1.setFillAlpha(110);
                        set1.setColor(Color.rgb(255,131,85));
                        set1.setLineWidth(3f);
                        set1.setValueTextSize(10f);
                        set1.setValueTextColor(Color.BLACK);
                        set2.setDrawValues(false);
                        set2.setCircleRadius(1f);
                        set2.setFillAlpha(110);
                        set2.setColor(Color.rgb(255, 156, 119));
                        set2.setLineWidth(3f);
                        set2.setValueTextSize(10f);
                        set2.setValueTextColor(Color.BLACK);
                        set3.setDrawValues(false);
                        set3.setCircleRadius(1f);
                        set3.setFillAlpha(110);
                        set3.setColor(Color.rgb(255, 180, 153));
                        set3.setLineWidth(3f);
                        set3.setValueTextSize(10f);
                        set3.setValueTextColor(Color.BLACK);
                        mChart.setDoubleTapToZoomEnabled(false);
                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        YAxis yAxisRight = mChart.getAxisRight();
                        yAxisRight.setDrawLabels(false);
                        yAxisRight.setDrawAxisLine(false);
                        yAxisRight.setDrawGridLines(false);

                        mChart.setDescription(null);

                        Legend l = mChart.getLegend();
                        l.setForm(Legend.LegendForm.LINE);
                        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

                        MyMarkerView marker = new MyMarkerView(getContext(), R.layout.maker_item);


                        Log.v("hhd",getContext()+"get");
                        marker.setChartView(mChart);
                        mChart.setMarker(marker);


                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        dataSets.add(set2);
                        dataSets.add(set3);
                        LineData data = new LineData(dataSets);
                        mChart.setData(data);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                        progressBar.setVisibility(View.GONE);
                        tv_label2.setVisibility(View.VISIBLE);
                        tv_smp2.setVisibility(View.VISIBLE);
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
    public class MyMarkerView extends MarkerView {
        TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            tvContent = this.findViewById(R.id.markerItem);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            tvContent.setText(String.format("%.2f",e.getY()));

            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }

}
