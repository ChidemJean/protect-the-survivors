package com.chidemgames.protectthesurvivors.android;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.R;
import com.chidemgames.protectthesurvivors.daos.external.JogadorDAO;
import com.chidemgames.protectthesurvivors.utils.Hash;
import com.chidemgames.protectthesurvivors.utils.Status;

public class CadastroActivity extends Activity implements RecognitionListener {

	Button btEntrar, btCadastrar;
	ImageView imgStatus;
	TextView tvLogin, tvSenha;
	EditText etNome, etSenha, etEmail;
 	private static final String TAG = "Recognizer";
 	private SpeechRecognizer speech = null;
 	private Timer speechTimeout = null;
    private Timer timerForInitSpeech= null;
 	private boolean isSRAvailable = true;
 	private boolean isActive = true;
 	private AudioManager am;
 	private LevenshteinDistance ld;
 	private LinkedList<String> mainQueue = new LinkedList<String>();
	private StateGame currentState;
	JogadorDAO jogadorDAO = new JogadorDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        init();
        PackageManager pm = this.getPackageManager();
 		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

 		if (activities.size() == 0) {
 			this.isSRAvailable = false;
 		}
 		isActive = true;

		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    }

    public enum StateGame {
    	RUNNING,
    	STOPPED
    }

    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	System.exit(0);
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
							executeCommand(mainQueue.poll());
						}
					});
				}
				SystemClock.sleep(1500);
			}
		}
	});

    @Override
    protected void onPause() {
    	super.onPause();
    	isActive = false;
    	Toast.makeText(this, "Reconhecedor parado", Toast.LENGTH_SHORT).show();
    }
//
    @Override
    protected void onResume() {
    	super.onResume();
    	if (!isActive) {
	    	isActive = true;
	    	Toast.makeText(this, "Reconhecedor reiniciado", Toast.LENGTH_SHORT).show();
    	}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

    public void executeCommand(String command){
	    switch (command){
			case "1-0":

				break;

			case "1-1":

				break;

			case "1-2":

				break;

			case "1-3":

				break;

			case "1-4":
				finish();
				break;
	    }
    }

    public void startTimer(){

    	this.timerForInitSpeech = new Timer();
    	this.timerForInitSpeech.schedule(new InitSpeechTimer(), 2000);
    }

    public Thread cadastrar(){
    	Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				String salt = Hash.randomSALT();
				String senhaTemp = Hash.saltMaisSenhaCript(etSenha.getText().toString(), salt);

				final String senha = salt + "!" + senhaTemp;

				final int status = jogadorDAO.save(etNome.getText().toString(), etEmail.getText().toString(), senha, "normal");
				showMessage(Status.getMessage(status, "Jogador"));

				if (status == Status.EXECUTADO_COM_SUCESSO){
					Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			}
		});
    	return t;
    }

    public void showMessage(final String message){
    	mainThread.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(CadastroActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		});
    }

    public void init(){

    	etNome = (EditText) findViewById(R.id.etc_nome);
    	etSenha = (EditText) findViewById(R.id.etc_senha);
    	etEmail = (EditText) findViewById(R.id.etc_email);

    	btCadastrar = (Button) findViewById(R.id.btc_cadastro);

    	btCadastrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cadastrar().start();
			}
		});
    }

    public void verifyCommands (ArrayList<String> comandos){

    	LevenshteinDistance.prepareDistanceOfString(comandos, CommandsManager.getInstance().getCurrentCommands(TypeScene.MENU), this.mainQueue);
    	LevenshteinDistance.initializeMatcher();

    	if (LevenshteinDistance.getNumWords() > 0){
     		imgStatus.setImageResource(R.drawable.green);
    	} else {
     		imgStatus.setImageResource(R.drawable.red);
    	}
    	if (LevenshteinDistance.getComandsExists() > 0){
    	}
    	this.mainQueue = LevenshteinDistance.getQueue();

    }

 	public class InitSpeechTimer extends TimerTask {
 		@Override
 		public void run() {

 			findPassword();
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

 	public void findPassword(){

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
			 			Toast.makeText(CadastroActivity.this, "SR n�o disponivel para seu dispositivo!", Toast.LENGTH_SHORT).show();
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
 		}
 		imgStatus.setImageResource(R.drawable.white);
 	}

 	@Override
 	public void onBufferReceived(byte[] buffer) {
 		Log.d(TAG,"onBufferReceived");
 	}

 	@Override
 	public void onEndOfSpeech() {
 		Log.d(TAG,"onEndOfSpeech");
 	}

	boolean restart = true;

 	@Override
 	public void onError(int error) {
 		String message;

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
	 	 		imgStatus.setImageResource(R.drawable.red);
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
