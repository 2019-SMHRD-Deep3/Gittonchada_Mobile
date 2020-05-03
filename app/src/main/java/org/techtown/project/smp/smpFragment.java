package org.techtown.project.smp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
    }

    private void updateFrameLayout(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.graphFrameLayout, fragment).commit();
    }
}
