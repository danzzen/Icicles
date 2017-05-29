package com.icicles.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class IciclesGame extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create() {
		showDifficultyScreen();
	}

	public void showDifficultyScreen() {

		setScreen(new DifficultyScreen(this));
	}
	public void showIciclesScreen(Constant.Difficulty di)
	{
		setScreen(new IcilesScreen(this,di));
	}
	public void showResumeScreen(int x ,int y){
		setScreen(new ResumeScreen(x,y));
	}
}
