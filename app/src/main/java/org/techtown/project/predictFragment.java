package org.techtown.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;


public class predictFragment extends Fragment {

    LineChart lineChart;
    ArrayList<Entry> entry_chart = new ArrayList<>();
    TextView tv_ac,tv_pr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_predict,container,false);

        tv_ac = (TextView)v.findViewById(R.id.tv_ac);
        tv_pr = (TextView)v.findViewById(R.id.tv_pr);

        tv_ac.setText("실제값 : ");
        tv_pr.setText("예측값 : ");



        return v;
    }
}
