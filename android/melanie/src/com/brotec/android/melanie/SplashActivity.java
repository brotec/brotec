package com.brotec.android.melanie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

	private boolean backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler handler = new Handler();
		 
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                 
                if (!backButton) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                }
            }
 
        }, 2500); 
    }
 
    @Override
    public void onBackPressed() {
    	backButton = true;
        super.onBackPressed();	 
    }

}
