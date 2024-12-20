package com.chidemgames.protectthesurvivors.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.chidemgames.protectthesurvivors.PTSGame;
import com.chidemgames.protectthesurvivors.PTSGame.TypeScene;
import com.chidemgames.protectthesurvivors.ui.Microphone;

public abstract class AbstractScene extends ScreenAdapter{
	
	protected TypeScene type;
	
	public void setType(TypeScene typeS){
		type = typeS;
	}
	
	public TypeScene getType(){
		return type;
	}
	
	public abstract void changeMicrophone(Microphone status);

}
