package org.techtown.project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;



public class firstFragment extends Fragment {

    Button btn_solgit;
    Button btn_energy;
    Button btn_consulting;
    Button btn_smp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_first, container, false);

        btn_solgit = v.findViewById(R.id.btn_solgit);
        btn_energy = v.findViewById(R.id.btn_energy);
        btn_smp = v.findViewById(R.id.btn_smp);
        btn_consulting = v.findViewById(R.id.btn_consulting);


        btn_solgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com"));
                startActivity(intent); }});

        btn_energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kpx.or.kr/"));
                startActivity(intent); }});

        btn_smp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kpx.or.kr/www/contents.do?key=225"));
                startActivity(intent); }});

        btn_consulting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01099760685"));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                startActivity(intent);
            }

        });
        return v;

    }
}
