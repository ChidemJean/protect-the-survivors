package com.chidemgames.protectthesurvivors.callbacks;

import com.chidemgames.protectthesurvivors.android.AndroidLauncher;

public class ChangeSpeechRecognizerCallback implements IChangeSpeechRecognizerCallback{

	private AndroidLauncher activity;
	
	public ChangeSpeechRecognizerCallback(AndroidLauncher activity){
		this.activity = activity;
	}
	
	@Override
	public void stoppedSpeechRecognizer() {
		activity.setIsActive(false);
	}

	@Override
	public void startSpeechRecognizer() {
		activity.setIsActive(true);
	}

}
