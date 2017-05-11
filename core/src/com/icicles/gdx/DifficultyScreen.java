package com.icicles.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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

        // TODO: Initialize a FitViewport with the difficulty world size constant
        viewport = new FitViewport(Constant.DIFFICULTY_WORLD_SIZE, Constant.DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        // TODO: Set the font scale using the constant we defined
        font.getData().setScale(Constant.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        // TODO: Apply the viewport
        viewport.apply();
        Gdx.gl.glClearColor(Constant.BACKGROUND_COLOR.r, Constant.BACKGROUND_COLOR.g, Constant.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // TODO: Set the ShapeRenderer's projection matrix
        renderer.setProjectionMatrix(viewport.getCamera().combined);


        // TODO: Use ShapeRenderer to draw the buttons
        renderer.begin(ShapeType.Filled);

        renderer.setColor(Constant.EASY_COLOR);
        renderer.circle(Constant.EASY_CENTER.x, Constant.EASY_CENTER.y, Constant.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(Constant.MEDIUM_COLOR);
        renderer.circle(Constant.MEDIUM_CENTER.x, Constant.MEDIUM_CENTER.y, Constant.DIFFICULTY_BUBBLE_RADIUS);

        renderer.setColor(Constant.HARD_COLOR);
        renderer.circle(Constant.HARD_CENTER.x, Constant.HARD_CENTER.y, Constant.DIFFICULTY_BUBBLE_RADIUS);

        renderer.end();


        // TODO: Set the SpriteBatche's projection matrix
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // TODO: Use SpriteBatch to draw the labels on the buttons
        // HINT: Use GlyphLayout to get vertical centering
        batch.begin();

        final GlyphLayout easyLayout = new GlyphLayout(font, Constant.EASY_LABEL);
        font.draw(batch, Constant.EASY_LABEL, Constant.EASY_CENTER.x, Constant.EASY_CENTER.y + easyLayout.height / 2, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, Constant.MEDIUM_LABEL);
        font.draw(batch, Constant.MEDIUM_LABEL, Constant.MEDIUM_CENTER.x, Constant.MEDIUM_CENTER.y + mediumLayout.height / 2, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, Constant.HARD_LABEL);
        font.draw(batch, Constant.HARD_LABEL, Constant.HARD_CENTER.x, Constant.HARD_CENTER.y + hardLayout.height / 2, 0, Align.center, false);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO: Update the viewport
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

        // TODO: Unproject the touch from the screen to the world
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        // TODO: Check if the touch was inside a button, and launch the icicles screen with the appropriate difficulty

        if (worldTouch.dst(Constant.EASY_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.EASY);
        }

        if (worldTouch.dst(Constant.MEDIUM_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.MEDIUM);
        }

        if (worldTouch.dst(Constant.HARD_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.HARD);
        }

        return true;
    }
}