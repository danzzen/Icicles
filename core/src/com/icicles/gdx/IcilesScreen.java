package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.sun.xml.internal.bind.CycleRecoverable;

import jdk.nashorn.internal.runtime.Context;

public class IcilesScreen implements Screen{
    //implementing screen so we will implement all the meethods
    public static final String TAG = IcilesScreen.class.getName();
    ExtendViewport iciclesViewport;
    ShapeRenderer renderer;
    Player player;
    Icicles icicles;
    SpriteBatch batch;
    BitmapFont font;
    int topScore=0;
    public static final String fps="192.168.43.229";
    Preferences prefs;
    public IcilesScreen(Context context, DifficultyScreen d){

    }
    @Override
    public void show() {
        iciclesViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);
        renderer = new ShapeRenderer();//inilize the shape renderer
        renderer.setAutoShapeType(true);
        player = new Player(iciclesViewport);//creating innstance of player class
        icicles = new Icicles(iciclesViewport);//creating innstance of Icicles class
        batch=new SpriteBatch();
        font=new BitmapFont();

     prefs = Gdx.app.getPreferences("my-preferences");
    if(prefs.getInteger("int")==0)
    {
        topScore=0;
    }
    else
    {
        topScore=prefs.getInteger("int");
    }

//        font.draw(batch,fps,10,iciclesViewport.getWorldHeight());
    }

    @Override
    public void resize(int width, int height) {
        iciclesViewport.update(width, height, true);//update viewport on resize
        player.init();
        icicles.init();
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {
        renderer.dispose();
    }


    @Override
    public void render(float delta) {
        icicles.update(delta);
        player.update(delta);
        if(player.hitByIcicle(icicles))
        {
            icicles.init();
        }
        //apply viewport
        iciclesViewport.apply(true);
        //always
        Gdx.gl.glClearColor(Constant.BACKGROUND_COLOR.r, Constant.BACKGROUND_COLOR.g, Constant.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      //  batch.setProjectionMatrix(iciclesViewport.getCamera().combined); //or your matrix to draw GAME WORLD, not UI

        batch.begin();
        batch.setColor(Color.BLACK);

        font.draw(batch,Integer.toString(icicles.count),10,iciclesViewport.getWorldHeight());
        font.draw(batch,Integer.toString(icicles.c),50,iciclesViewport.getWorldHeight());
        topScore=Math.max(topScore,icicles.count);
        prefs.putInteger("int", topScore);
        prefs.flush();
        font.draw(batch,Integer.toString(topScore),100,iciclesViewport.getWorldHeight());

        batch.end();
        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        icicles.render(renderer);
        player.render(renderer);
        renderer.end();

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
