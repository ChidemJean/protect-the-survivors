package com.chidemgames.protectthesurvivors.android;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.StateGame;
import com.chidemgames.protectthesurvivors.callbacks.ChangeSpeechRecognizerCallback;
import com.chidemgames.protectthesurvivors.callbacks.RequestActionCallback;
import com.chidemgames.protectthesurvivors.managers.GameManager;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public class AndroidLauncher extends AndroidApplication implements RecognitionListener{
	
	private static final String TAG = "Recognizer";
 	private SpeechRecognizer speech = null;
 	private Timer speechTimeout = null;
    private Timer timerForInitSpeech= null;
 	private boolean isSRAvailable = true;
 	private boolean isActive = true;
 	private AudioManager am;
 	private LinkedList<String> mainQueue = new LinkedList<String>();
	private StateGame currentState;
	private PTSGame game;
 	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();		
		game = new PTSGame();
		initialize(game, config);
		
		game.setRequestActionListener(new RequestActionCallback());
		game.setChangeSRListener(new ChangeSpeechRecognizerCallback(this));
		
        PackageManager pm = this.getPackageManager();
 		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
 		
 		if (activities.size() == 0) {
 			this.isSRAvailable = false;
 		}
 		isActive = true;
		
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        startTimer();
        
    }
        
    private Handler mainThread = new Handler(Looper.getMainLooper());
    private Thread thread = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while (currentState == StateGame.RUNNING) {
				if (mainQueue.size() > 0){
					mainThread.post(new Runnable() {
						@Override
						public void run() {
							CommandActions.executeCommand(mainQueue.poll(), game, AndroidLauncher.this);
						}
					});
				}
				SystemClock.sleep(600);
			}
		}
	});
    
    @Override
    protected void onPause() {
    	super.onPause();
    	isActive = false;
    	Toast.makeText(this, "Reconhecedor parado", Toast.LENGTH_SHORT).show();
    }
    
    public void setIsActive(boolean i){
    	isActive = i;
    	if (i){
	    	startVoiceRecognition();
    	}
    }

    @Override
    protected void onResume() {
    	super.onResume();
    	if (!isActive) {
	    	isActive = true;
	    	startVoiceRecognition();
	    	Toast.makeText(this, "Reconhecedor reiniciado", Toast.LENGTH_SHORT).show();
    	}
	}
    
    public void startTimer(){
    	
    	this.timerForInitSpeech = new Timer();
    	this.timerForInitSpeech.schedule(new InitSpeechTimer(), 2000);
    }
    
    
    public void verifyCommands (ArrayList<String> comandos){
   
    	LevenshteinDistance.prepareDistanceOfString(comandos, CommandsManager.getInstance().getCurrentCommands(game.getScreen().getType()), this.mainQueue);
    	LevenshteinDistance.initializeMatcher();
    	
    	if (LevenshteinDistance.getNumWords() > 0){
 			game.getScreen().changeMicrophone(Microphone.RECONHECIDO);
    	} else {
    		Toast.makeText(this, "Comando n�o existe! \n Diga comandos para lista-los", Toast.LENGTH_SHORT).show();
 			game.getScreen().changeMicrophone(Microphone.ERRO);
    	}
    	if (LevenshteinDistance.getComandsExists() > 0){
    		Toast.makeText(this, "Algum dos comandos j� foram falados", Toast.LENGTH_SHORT).show();
 			game.getScreen().changeMicrophone(Microphone.ERRO);
    	}
    	this.mainQueue = LevenshteinDistance.getQueue();
    	
    }
    
 	public class InitSpeechTimer extends TimerTask {
 		@Override
 		public void run() {
 			if (speech == null) {
				startVoiceRecognition();
				currentState = StateGame.RUNNING;
				thread.start();
			}
			else {
				stopVoiceRecognition();
			}
 		}
 	}
 	
 	private boolean isSpeechTimeoutOn = false;

 	public class SilenceTimer extends TimerTask {
 		@Override
 		public void run() {
 			mainThread.post(new Runnable() {
 				@Override
 				public void run() {
 		 			onError(SpeechRecognizer.ERROR_SPEECH_TIMEOUT);				
 				}
 			});
 		}
 	}

 	private SpeechRecognizer getSpeechRecognizer(){
 		Log.i(TAG, "getSpeechRecognizer()");
 		
 		if (speech == null && this.isSRAvailable) {
 			speech = SpeechRecognizer.createSpeechRecognizer(this);
 			speech.setRecognitionListener(this);
 		}
 		
 		return speech;
 	}

 	public void startVoiceRecognition() {
 		if (isActive){
	 		final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	 		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	 		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 2);
	 		this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					SpeechRecognizer sr = getSpeechRecognizer();
					if (sr != null){
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
							am.setStreamMute(AudioManager.STREAM_SYSTEM, true);
						}
			 			sr.startListening(intent);	
					} else {
			 			Toast.makeText(AndroidLauncher.this, "SR n�o disponivel para seu dispositivo!", Toast.LENGTH_SHORT).show();
					}
				}
			});
 		}
 	}

 	public void stopVoiceRecognition() {
 		speechTimeout.cancel();
 		if (speech != null) {
 			currentState = StateGame.STOPPED;
 			speech.destroy();
 			speech = null;
 		}
 	}
 	
 	@Override
 	public void onReadyForSpeech(Bundle params) {
 		Log.d(TAG,"onReadyForSpeech");
 		this.isSpeechTimeoutOn = true;
 		speechTimeout = new Timer();
 		speechTimeout.schedule(new SilenceTimer(), 5000);
 		Toast.makeText(this, "Diga um comando", Toast.LENGTH_SHORT).show();
 	}
 	
 	@Override
 	public void onBeginningOfSpeech() {
 		Log.d(TAG,"onBeginningOfSpeech");
 		if (this.isSpeechTimeoutOn){
 			this.isSpeechTimeoutOn = false;
 			speechTimeout.cancel();
 			game.getScreen().changeMicrophone(Microphone.LISTENING);
 		}
 	}

 	@Override
 	public void onBufferReceived(byte[] buffer) {
 		Log.d(TAG,"onBufferReceived");
 	}
 	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopVoiceRecognition();
	}
	
 	@Override
 	public void onEndOfSpeech() {
 		Log.d(TAG,"onEndOfSpeech");
 	}
 	
	boolean restart = true;

 	@Override
 	public void onError(int error) {
 		String message;
		game.getScreen().changeMicrophone(Microphone.ERRO);

 		if (this.isSpeechTimeoutOn){
 			this.isSpeechTimeoutOn = false;
 			speechTimeout.cancel();
 		}

 		switch (error)
 		{
 			case SpeechRecognizer.ERROR_AUDIO:
 				message = "Erro ao gravar audio";
 				break;
 			case SpeechRecognizer.ERROR_CLIENT:
 				message = "Erro do lado cliente";
 				restart = false;
 				break;
 			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
 				message = "Permissoes insuficientes";
 				restart = false;
 				break;
 			case SpeechRecognizer.ERROR_NETWORK:
 				message = "Erro de rede";
 				break;
 			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
 				message = "Tempo de conexao esgotado";
 				break;
 			case SpeechRecognizer.ERROR_NO_MATCH:
 				message = "Sem compara��es";
 		 		Toast.makeText(this, "N�o entedi! Tente novamente", Toast.LENGTH_SHORT).show();
 				break;
 			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
 				message = "RecognitionService busy";
 				break;
 			case SpeechRecognizer.ERROR_SERVER:
 				message = "Erro do servidor";
 				break;
 			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
 				message = "Sem entrada de fala";
 				break;
 			default:
 				message = "N�o reconhecido";																																													
 				break;
 		}
 		 		
 		if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT || error == SpeechRecognizer.ERROR_NETWORK_TIMEOUT){
 			restarFromError();
 		} else {
	 		if (restart){
	 			Log.d(TAG,"onError code:" + message + " message: " + message);
		    	restarFromError();
	 		}
 		}
 	}
 	
 	public void restarFromError(){
 		Timer timer = new Timer();
			timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mainThread.post(new Runnable() {
					@Override
					public void run() {
			 	 		getSpeechRecognizer().cancel();
						startVoiceRecognition();
					}
				});					
			}
		}, 1500);
 	}
 	
 	public void restartFromResults(){
 		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mainThread.post(new Runnable() {
					@Override
					public void run() {
						startVoiceRecognition();
					}
				});					
			}
		}, 500);
 	}

 	@Override
 	public void onEvent(int eventType, Bundle params) {
 		Log.d(TAG,"onEvent");
 	}

 	@Override
 	public void onPartialResults(Bundle partialResults) {
 		Log.d(TAG,"onPartialResults");
 	}

 	@Override
 	public void onResults(Bundle results) {
 		String scores = null;
 		
 		float[] confidences = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
 		for (int i = 0; i < confidences.length; i++) {
 			scores += confidences[i] + " ";
 		}

 		Log.d(TAG,"onResults: " + confidences + " scores: " + scores.toString());

 		if (results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) != null) {
 			verifyCommands(results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION));
 		}
		startVoiceRecognition();
 	}

 	@Override
 	public void onRmsChanged(float rmsdB) {
 		Log.d(TAG,"onRmsChanged");
 	}
	
}
