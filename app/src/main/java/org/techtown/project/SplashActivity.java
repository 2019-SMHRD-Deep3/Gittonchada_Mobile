package org.techtown.project;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

    public class SplashActivity extends AppCompatActivity {
        TextView tv_solgit, tv_gittong;
        ImageView iv_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce_ani);
        Animation linear = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.linear_ani);

        tv_solgit = findViewById(R.id.tv_solgit);
        tv_gittong = findViewById(R.id.tv_gittong);
        iv_logo = findViewById(R.id.iv_logo);

        tv_solgit.startAnimation(bounce);
        tv_gittong.startAnimation(linear);
        iv_logo.startAnimation(linear);

        Handler handler = new Handler();
        handler.postDelayed(new animation(),3000);

    }
    public class animation implements Runnable{
        public void run(){

                startActivity(new Intent(getApplication(), Login.class));
                SplashActivity.this.finish();
            }

        }
}
