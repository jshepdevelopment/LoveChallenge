package com.jshepdevelopment.lovechallenge;

import com.badlogic.gdx.Game;
import com.jshepdevelopment.lovechallenge.screens.MenuScreen;

public class LoveChallenge extends Game {

	public static PlayServices playServices;

	public LoveChallenge(PlayServices playServices) {
		this.playServices = playServices;
	}

	@Override
	public void create () {

		setScreen(new MenuScreen(this, this));
	}

}
