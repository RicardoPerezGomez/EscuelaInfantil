package com.example.lionkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;





public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"kidszone.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        TextView subtitulo = (TextView) findViewById(R.id.subtitulo);
        titulo.setTypeface(miFuente);
        subtitulo.setTypeface(miFuente);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.splash);
        titulo.startAnimation(anim);


        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.fade_in_splash);
        View circulo = findViewById(R.id.circularFillableLoaders);
        circulo.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.splash2);
        subtitulo.startAnimation(anim2);
         anim2.setAnimationListener(this);
    }



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}