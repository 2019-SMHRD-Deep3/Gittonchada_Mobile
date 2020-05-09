package org.techtown.project.smp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.techtown.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class smp_main extends Fragment {
    Button btn_daily;
    private ProgressBar progressBar;
    private LineChart mChart;
    ArrayList<Entry> smp_entrychart = new ArrayList<>();
    //SMPList smpList = new SMPList();
    ArrayList<Float> smplist = new ArrayList<>();
    View view;
    // Fragment
    private RequestQueue requestQueue;
    private Object Parcelable;
    private Object smp_time;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.smpfragment, container, false);
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }
        mChart = view.findViewById(R.id.smp_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        sendRequest();

        progressBar = view.findViewById(R.id.dailyProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        btn_daily = view.findViewById(R.id.btn_daily);
        btn_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smp_time smp_time = new smp_time();
                getParentFragmentManager().beginTransaction().replace(R.id.framelayout, smp_time).commit();
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
                        smplist.add(list.get(0).getPast2());
                        smplist.add(list.get(0).getPast1());
                        smplist.add(list.get(0).getPresent());
                        smplist.add(list.get(0).getFuture1());
                        smplist.add(list.get(0).getFuture2());

                        for(int i=0; i<smplist.size();i++) {
                            //Log.v("hhd", smplist.get(i)+": smplist");
                        }
                        ArrayList<Entry> Value = new ArrayList<>();

                        Value.add(new Entry(0,smplist.get(0)));
                        Value.add(new Entry(1,smplist.get(1)));
                        Value.add(new Entry(2,smplist.get(2)));
                        Value.add(new Entry(3,smplist.get(3)));
                        Value.add(new Entry(4,smplist.get(4)));
                        LineDataSet set1 = new LineDataSet(Value,"daily SMP");
                        mChart.getXAxis().setDrawGridLines(false);
                        mChart.getAxisLeft().setDrawGridLines(false);
                        mChart.getAxisRight().setDrawGridLines(false);
                        set1.setDrawValues(true);
                        set1.setCircleRadius(1f);
                        set1.setFillAlpha(110);
                        set1.setColor(Color.RED);
                        set1.setLineWidth(3f);
                        set1.setValueTextSize(10f);
                        set1.setValueTextColor(Color.BLUE);
                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        YAxis yAxisRight = mChart.getAxisRight();
                        yAxisRight.setDrawLabels(false);
                        yAxisRight.setDrawAxisLine(false);
                        yAxisRight.setDrawGridLines(false);

                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        LineData data = new LineData(dataSets);
                        mChart.setData(data);
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd","daily 에러 => "+ error.getNetworkTimeMs());
                        Log.v("hhd","daily 에러 => "+ String.valueOf(error));
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

    private void updateFrameLayout(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.graphFrameLayout, fragment).commit();
    }


}
