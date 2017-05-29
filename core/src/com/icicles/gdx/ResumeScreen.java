package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ResumeScreen implements Screen {
    private ExtendViewport pasViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private IciclesGame game;
    private Icicles icicles;
    private int score, topscore;
    private ScreenViewport hudViewport;

    public ResumeScreen(int count, int topscore) {
        this.score = count;
        this.topscore = topscore;
    }

    @Override
    public void show() {
        pasViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);
        renderer = new ShapeRenderer();//inilize the shape renderer
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont();
        hudViewport = new ScreenViewport();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(242 / 255f, 194 / 255f, 128 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //  pasViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined); //or your matrix to draw GAME WORLD, not UI
        batch.begin();
        batch.setColor(Color.BLUE);
        hudViewport.apply();
        // font.getData().setScale(3,4);
//        if(topscore>0)
//        {
//            font.draw(batch,"New High Score",hudViewport.getWorldWidth()-20f,hudViewport.getWorldHeight()-20f,0,Align.right,false);
//        }
//        font.draw(batch, "Score: ",
//                20,20);
        font.setColor(Color.BLACK);
        font.draw(batch, "Score: " + "\nTop Score: ",
                0,0);
        // font.draw(batch, "Life: " + player.health, hudViewport.getWorldWidth() / 4 - 20, hudViewport.getWorldHeight() - Constant.HUD_MARGIN, 0, Align.right, false);
        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        pasViewport.update(width, height,true);
        hudViewport.update(width,height,true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
