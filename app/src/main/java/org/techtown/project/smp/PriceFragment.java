package org.techtown.project.smp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.techtown.project.R;

public class PriceFragment extends Fragment {

    private RequestQueue requestQueue = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.example, container, false);

        if (this.getContext() != null) {
            requestQueue = Volley.newRequestQueue(this.getContext());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        fetchPriceData();
    }

    private void fetchPriceData() {

        String url = "http://localhost:9001/example/";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("dsf","sdf");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("dsf","sdf");
                    }
                });

        request.setShouldCache(false);   // 누를 때마다 새로운 정보를 출력해서 보여줌
        requestQueue.add(request);
    }
}

