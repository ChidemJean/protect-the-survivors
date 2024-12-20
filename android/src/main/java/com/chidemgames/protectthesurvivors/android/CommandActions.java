package com.chidemgames.protectthesurvivors.android;

import android.app.Activity;

import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.screens.GameScene;
import com.chidemgames.protectthesurvivors.screens.SplashScene;

public class CommandActions {

	
	public static void executeCommand(String commandId, PTSGame game, Activity activity){
		 switch (commandId){
			case "1-0":
				GameScene gs1 = (GameScene) game.getScreen();
				break;
				
			case "1-1":
				if (game.getScreen().getType() == TypeScene.SPLASH){
					SplashScene s = (SplashScene) game.getScreen();
					s.changeColor(!s.getChangeColor());
				}
				break;
				
			case "1-2":
				GameScene gs = (GameScene) game.getScreen();
				break;
			
			case "1-3":
				GameScene gs2 = (GameScene) game.getScreen();
				break;
				
			case "1-4":
				activity.finish();
				break;
				
			case "1-5":
				GameScene gs3 = (GameScene) game.getScreen();
				break;
				
			case "1-6":
				GameScene gs4 = (GameScene) game.getScreen();
				break;
				
			case "1-7":
				GameScene gs5 = (GameScene) game.getScreen();
				break;
	    }
	}
	
}
