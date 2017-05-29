package com.icicles.gdx;
import com.badlogic.gdx.Gdx;
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
        renderer.rect(Constant.EASY_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS,Constant.EASY_CENTER.y-Constant.DIFFICULTY_BUBBLE_RADIUS,2*Constant.DIFFICULTY_BUBBLE_RADIUS,50.0f);

        renderer.setColor(244/255f, 186/255f,102/255f,1);
        renderer.rect(Constant.MEDIUM_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS, Constant.MEDIUM_CENTER.y-Constant.DIFFICULTY_BUBBLE_RADIUS,2* Constant.DIFFICULTY_BUBBLE_RADIUS,50.0f);

        renderer.setColor(247/255f, 156/255f, 27/255f,1f);
        renderer.rect(Constant.HARD_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS, Constant.HARD_CENTER.y-Constant.DIFFICULTY_BUBBLE_RADIUS,2*Constant.DIFFICULTY_BUBBLE_RADIUS,50.0f);

        renderer.end();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        // HINT: Use GlyphLayout to get vertical centering
        batch.begin();

        final GlyphLayout easyLayout = new GlyphLayout(font, Constant.EASY_LABEL);
        font.draw(batch, Constant.EASY_LABEL, Constant.EASY_CENTER.x, Constant.EASY_CENTER.y-20, 0, Align.center, false);

        final GlyphLayout mediumLayout = new GlyphLayout(font, Constant.MEDIUM_LABEL);
        font.draw(batch, Constant.MEDIUM_LABEL, Constant.MEDIUM_CENTER.x, Constant.MEDIUM_CENTER.y-20, 0, Align.center, false);

        final GlyphLayout hardLayout = new GlyphLayout(font, Constant.HARD_LABEL);
        font.draw(batch, Constant.HARD_LABEL, Constant.HARD_CENTER.x, Constant.HARD_CENTER.y-20, 0, Align.center, false);

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
        if (worldTouch.dst(Constant.EASY_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.EASY);
            Gdx.input.setInputProcessor(null);
        }
        if (worldTouch.dst(Constant.MEDIUM_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.MEDIUM);
            Gdx.input.setInputProcessor(null);
        }
        if (worldTouch.dst(Constant.HARD_CENTER) < Constant.DIFFICULTY_BUBBLE_RADIUS) {
            game.showIciclesScreen(Constant.Difficulty.HARD);
            Gdx.input.setInputProcessor(null);
        }
        return true;
    }
}