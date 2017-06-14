package com.icicles.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;



public class DifficultyScreen extends InputAdapter implements Screen {

    public static final String TAG = DifficultyScreen.class.getName();

    IciclesGame game;
    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;
    BitmapFont font;

    public DifficultyScreen(IciclesGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        viewport = new FitViewport(Constant.DIFFICULTY_WORLD_SIZE, Constant.DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(Constant.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(244/255f, 227/255f, 203/255f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);

        renderer.setColor(247/255f, 205/255f, 145/255f,1);
        renderer.circle(Constant.EASY_CENTER.x,Constant.EASY_CENTER.y,Constant.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(244/255f, 186/255f,102/255f,1);
        renderer.circle(Constant.MEDIUM_CENTER.x, Constant.MEDIUM_CENTER.y,Constant.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(247/255f, 156/255f, 27/255f,1f);
        renderer.circle(Constant.HARD_CENTER.x, Constant.HARD_CENTER.y,Constant.DIFFICULTY_BUBBLE_RADIUS);
        renderer.setColor(247/255f, 185/255f, 123/255f,1);
        renderer.ellipse(viewport.getWorldWidth()/2-(3/2)*Constant.DIFFICULTY_BUBBLE_RADIUS,viewport.getWorldHeight()/2-20,150,70,20);
        renderer.ellipse(viewport.getWorldWidth()/2-(3/2)*Constant.DIFFICULTY_BUBBLE_RADIUS,viewport.getWorldHeight()/4,150,70,20);

        renderer.end();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        // HINT: Use GlyphLayout to get vertical centering
        batch.begin();

        font.draw(batch, Constant.EASY_LABEL, Constant.EASY_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.EASY_CENTER.y);

        font.draw(batch, Constant.MEDIUM_LABEL, Constant.MEDIUM_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.MEDIUM_CENTER.y);

        font.draw(batch, Constant.HARD_LABEL, Constant.HARD_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.HARD_CENTER.y);

        font.draw(batch,"Settings",viewport.getWorldWidth()/2-(3/2)*Constant.DIFFICULTY_BUBBLE_RADIUS+25,viewport.getWorldHeight()/2+25);
        font.draw(batch,"Exit",Constant.MEDIUM_CENTER.x,viewport.getWorldHeight()/4+35);
        batch.end();
    }
    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        Vector2 exit=new Vector2(viewport.getWorldWidth()/2-(3/2)*Constant.DIFFICULTY_BUBBLE_RADIUS+75,viewport.getWorldHeight()/4+35);
        if (worldTouch.dst(Constant.EASY_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.EASY);
        }
        if (worldTouch.dst(Constant.MEDIUM_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.MEDIUM);
        }
        if (worldTouch.dst(Constant.HARD_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.HARD);
        }
        if(worldTouch.dst(exit)<70)
        {
           Gdx.app.exit();
        }
        return true;
    }



}