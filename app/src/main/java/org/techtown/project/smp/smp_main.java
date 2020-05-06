package org.techtown.project.smp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.techtown.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.kingja.switchbutton.SwitchMultiButton;

public class smp_main extends Fragment {
    SMPList smpList = new SMPList();
    //smp smp = new smp();

    // Fragment
    private RequestQueue requestQueue = null;
    private Object Parcelable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        View view = inflater.inflate(R.layout.fragment_smp, container, false);
        return view;
    }

    private void sendRequest(){

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
                        Log.v("hhd","응답 => " + list.toString());
                        /* String chValue = response.replace("\"","");
                        chValue = chValue.replace("\\","");
                        */



//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//                            //JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                            //Log.v("hhd",jsonArray.getJSONObject(0).get("future1"));
//
//                            for(int i = 0; i<jsonArray.length();i++){
//                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                                Log.v("hhd","future2 : "+jsonObject.getString("future1"));
//                               // Log.v("hhd","future1 : "+jsonObject.getString("future1"));
//                                //future2.add(smp.getString("future2"));
//                                //Log.v("hhd","arraylist(2) : " + future2.get(2));
//                            }
//
//                            /*jsonObject = jsonArray.getJSONObject(0);
//                            Log.v("myValueTest",jsonArray.length()+"");
//                            Log.v("myValueTest",jsonObject.toString());
//*/
//                        }catch (Exception e){
//                            Log.v("hhd","실패");
//                            e.printStackTrace();
//                        }
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
