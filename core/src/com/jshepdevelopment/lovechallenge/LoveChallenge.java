package com.jshepdevelopment.lovechallenge;

import com.badlogic.gdx.Game;
import com.jshepdevelopment.lovechallenge.screens.MenuScreen;

public class LoveChallenge extends Game {
	@Override
	public void create () {
		setScreen(new MenuScreen(this));
	}
}
