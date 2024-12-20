package com.chidemgames.protectthesurvivors.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chidemgames.protectthesurvivors.R;

public class SplashActivity extends Activity {

	private Timer timerForInitSpeech;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		startTimer();
	}

	public void startTimer(){
		this.timerForInitSpeech = new Timer();
    	this.timerForInitSpeech.schedule(new InitNextSreenTimer(), 7500);
	}

	public class InitNextSreenTimer extends TimerTask {

		@Override
		public void run() {
			initScreen();
		}
	}

	public void initScreen(){
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
