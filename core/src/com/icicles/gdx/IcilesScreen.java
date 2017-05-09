package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by lenovo on 12-04-2017.
 */

public class IcilesScreen implements Screen{
    public static final String TAG = IcilesScreen.class.getName();

    ExtendViewport iciclesViewport;
    ShapeRenderer renderer;

    Player player;
    Icicles icicles;
    // TODO: Add SpriteBatch
    SpriteBatch batch;

    // TODO: Add BitmapFont
    BitmapFont font;
    int topScore;

    @Override
    public void show() {
        iciclesViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        player = new Player(iciclesViewport);
        icicles = new Icicles(iciclesViewport);

    }

    @Override
    public void resize(int width, int height) {
        iciclesViewport.update(width, height, true);
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
        iciclesViewport.apply(true);
        Gdx.gl.glClearColor(Constant.BACKGROUND_COLOR.r, Constant.BACKGROUND_COLOR.g, Constant.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
