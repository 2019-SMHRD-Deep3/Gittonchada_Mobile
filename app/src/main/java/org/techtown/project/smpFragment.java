package org.techtown.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class smpFragment extends Fragment {
    static RequestQueue requestQueue;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*if(requestQueue == null){
            requestQueue = Volley.newRequestQueue();
        }

        String url = "http://localhost:9001/re/";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();

                //tv_content.append(response + "");
                tv_content.append("rank : "+movieList.boxOfficeResult.dailyBoxOfficeList.get(0).rank+"\n");
                tv_content.append("rank : "+movieList.boxOfficeResult.dailyBoxOfficeList.get(0).movieNm+"\n");
                tv_content.append("rank : "+movieList.boxOfficeResult.dailyBoxOfficeList.get(0).audiCnt+"\n");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv_content.append("에러");
            }
        }){
            //post방식 일 시
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id","wonho");
                return params;
            }
        };
        // 클릭할 때마다 새로운 정보를 출력해서 보여줌
        request.setShouldCache(false);

        requestQueue.add(request);
*/
        return inflater.inflate(R.layout.fragment_smp, container, false);
    }
}
