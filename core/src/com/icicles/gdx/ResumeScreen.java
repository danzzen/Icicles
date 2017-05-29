package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ResumeScreen extends InputAdapter implements Screen {
    private ExtendViewport pasViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    Constant.Difficulty di;
    private IciclesGame game;
    private Icicles icicles;
    private int score, topscore;
    private ScreenViewport hudViewport;

    public ResumeScreen(int count, int topscore,IciclesGame gm,Constant.Difficulty di) {
        this.score = count;
        this.topscore = topscore;
        this.game=gm;
        this.di=di;
    }

    @Override
    public void show() {
        pasViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);
        renderer = new ShapeRenderer();//inilize the shape renderer
        renderer.setAutoShapeType(true);
        batch = new SpriteBatch();
        font = new BitmapFont();
        hudViewport = new ScreenViewport();
        Gdx.input.setInputProcessor(this);
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
        font.setColor(Color.BLACK);
        font.getData().setScale(3);
        if(topscore>=0)
        {
            font.draw(batch,"New High\n",hudViewport.getWorldWidth()/2-40,hudViewport.getWorldHeight()/2+80f);
        }
        font.draw(batch, "Score: "+score,
                hudViewport.getWorldWidth()/2-30,hudViewport.getWorldHeight()/2+10);
        font.setColor(Color.BLACK);
        batch.end();
        pasViewport.apply();
        renderer.setProjectionMatrix(pasViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(237/255f, 133/255f, 40/255f,1);
        renderer.circle(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4,0.7f,20);
        renderer.setColor( 242/255f, 194/255f, 128/255f, 1);
        renderer.circle(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4,0.6f,20);
        renderer.setColor(237/255f, 133/255f, 40/255f,1);
        // font.draw(batch, "Life: " + player.health, hudViewport.getWorldWidth() / 4 - 20, hudViewport.getWorldHeight() - Constant.HUD_MARGIN, 0, Align.right, false);
        renderer.rectLine(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4-0.7f,pasViewport.getWorldWidth()/4+0.3f,pasViewport.getWorldHeight()/4-0.9f,0.05f);
        renderer.rectLine(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4-0.6f,pasViewport.getWorldWidth()/4+0.3f,pasViewport.getWorldHeight()/4-0.5f+0.2f,0.05f);

        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        batch.setColor(Color.BLACK);
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.draw(batch,"restart",hudViewport.getWorldWidth()/4-20,hudViewport.getWorldHeight()/4-50);
        batch.end();
        renderer.rectLine(pasViewport.getWorldWidth()*3/4-0.5f,pasViewport.getWorldHeight()/4+0.5f,
                pasViewport.getWorldWidth()*3/4+0.5f,pasViewport.getWorldHeight()/4-0.5f,0.5f);
        renderer.rectLine(pasViewport.getWorldWidth()*3/4-0.5f,pasViewport.getWorldHeight()/4-0.5f,
                pasViewport.getWorldWidth()*3/4+0.5f,pasViewport.getWorldHeight()/4+0.5f,0.5f);

        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        batch.setColor(Color.BLACK);
        font.setColor(Color.BLACK);
        font.getData().setScale(1);
        font.draw(batch,"Exit to Menu",hudViewport.getWorldWidth()*3/4-20,hudViewport.getWorldHeight()/4-50);
        batch.end();

        renderer.end();


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
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        Gdx.input.setInputProcessor(this);
        Vector2 WorldTouch=pasViewport.unproject(new Vector2(screenX,screenY));
        Vector2 rtouch=new Vector2(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4);
        Vector2 etouct=new Vector2(pasViewport.getWorldWidth()*3/4,pasViewport.getWorldHeight()/4);
        if(WorldTouch.dst(rtouch)<0.7)
        {
            game.showIciclesScreen(di);
            //Gdx.input.setInputProcessor(null);
        }
        if(WorldTouch.dst(etouct)<0.7)
        {
            game.showDifficultyScreen();
           // Gdx.input.setInputProcessor(null);
        }
        return true;
    }

}
