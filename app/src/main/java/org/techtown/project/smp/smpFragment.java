package org.techtown.project.smp;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.kingja.switchbutton.SwitchMultiButton;

public class smpFragment extends Fragment {

    // Fragment
    private PriceFragment priceFragment = new PriceFragment();
    private TimeFragment timeFragment = new TimeFragment();

    private SwitchMultiButton switchMultiButton;

    private RequestQueue requestQueue = null;
    private Object Parcelable;
    ArrayList<smp> daily_smp = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        updateFrameLayout(priceFragment);

        View view = inflater.inflate(R.layout.smpfragment, container, false);

        bindSubViews(view);
        return view;
    }

    private void bindSubViews(View superView) {

        switchMultiButton = superView.findViewById(R.id.smpSwitchButton);
        switchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {

                Fragment fragment = null;
                if (position == 0) {
                    updateFrameLayout(priceFragment);
                } else {
                    updateFrameLayout(timeFragment);
                }
            }
        });

        sendRequest();
    }

    private void sendRequest(){
        final ArrayList<String> future2 = new ArrayList<>();
        ArrayList<String> future1 = new ArrayList<>();

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
                        //  Log.v("hhd","smp.future1 : " + smp.);
                        for(int i = 0; i<list.size(); i++) {
                            Log.v("hhd", "응답 => " + list.get(i).getFuture1());
                            Log.v("hhd", ""+ list.get(0).getPresent());
                        }
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("hhd","에러 => "+ error.getNetworkTimeMs());
                        Log.v("hhd","에러 => "+ String.valueOf(error));
                    }
                }
        );
        request.setShouldCache(false);
        request.setShouldRetryServerErrors(true);
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                50000 ,

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
    public  Fragment newInstance(ArrayList<String> arrayList){
        smpFragment smpFragment = new smpFragment();
        Bundle bundle = new Bundle();

        return  null;
    }
    private  void setChart(){

    }
}
