package org.techtown.project.smp;

import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.project.R;

import lib.kingja.switchbutton.SwitchMultiButton;

public class smpFragment extends Fragment {

    // Fragment
    private PriceFragment priceFragment = new PriceFragment();
    private TimeFragment timeFragment = new TimeFragment();

    private SwitchMultiButton switchMultiButton;

    private RequestQueue requestQueue = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        updateFrameLayout(priceFragment);

        View view = inflater.inflate(R.layout.fragment_smp, container, false);

        bindSubViews(view);

        // Log.v("hhd","응답 => " + response);
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
        String url = "http://172.30.1.43:9001/re/";
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {

                        Log.v("hhd","응답 => " + response);
                        Log.v("hhd","응답 => " + response.length());
                        /* String chValue = response.replace("\"","");
                        chValue = chValue.replace("\\","");
                        */

                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            for(int i = 0; i<jsonArray.length();i++){
                                JSONObject smp = jsonArray.getJSONObject(i);
                                Log.v("hhd","future2"+smp.getString("future2"));
                            }


                            /*jsonObject = jsonArray.getJSONObject(0);
                            Log.v("myValueTest",jsonArray.length()+"");
                            Log.v("myValueTest",jsonObject.toString());
*/
                        }catch (Exception e){
                            Log.v("hhd","실패");
                            e.printStackTrace();
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
}
