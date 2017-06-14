package com.icicles.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;



public class DifficultyScreen extends InputAdapter implements Screen,AssetErrorListener {

    public static final String TAG = DifficultyScreen.class.getName();
    private static final String ATLAS ="images/button.pack.atlas";
    private static final String STANDING_RIGHT ="btn";

    IciclesGame game;
    private ShapeRenderer renderer;
    SpriteBatch batch;
    ExtendViewport viewport;
    BitmapFont font;
    AssetManager assetManager;
    TextureAtlas.AtlasRegion atlasRegion;
    public DifficultyScreen(IciclesGame game) {
        this.game = game;
    }
    TextureRegion backgroundTexture;
    @Override

    public void show() {
        renderer = new ShapeRenderer();
        assetManager=new AssetManager();
        assetManager.setErrorListener( this);
        assetManager.load(ATLAS,TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas=assetManager.get(ATLAS);
        atlasRegion= atlas.findRegion(STANDING_RIGHT);
        batch = new SpriteBatch();
        backgroundTexture = new TextureRegion(new Texture("background_edited.jpg") ,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        viewport = new ExtendViewport(Constant.DIFFICULTY_WORLD_SIZE, Constant.DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(Constant.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {

        viewport.apply();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);


        renderer.end();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        // HINT: Use GlyphLayout to get vertical centering
        batch.begin();
        batch.draw(backgroundTexture,0,0);
        batch.draw(
                atlasRegion.getTexture(),
                viewport.getWorldWidth()/4,
                viewport.getWorldHeight()/2+20,
                0,
                0,
                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),
                1,
                1,
                0,
                atlasRegion.getRegionX(),
                atlasRegion.getRegionY(),
                atlasRegion.getRegionWidth(),
                atlasRegion.getRegionHeight(),
                false,
                false);
        font.draw(batch, Constant.EASY_LABEL, Constant.EASY_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.EASY_CENTER.y);

        font.draw(batch, Constant.MEDIUM_LABEL, Constant.MEDIUM_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.MEDIUM_CENTER.y);

        font.draw(batch, Constant.HARD_LABEL, Constant.HARD_CENTER.x-Constant.DIFFICULTY_BUBBLE_RADIUS/2, Constant.HARD_CENTER.y);
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


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }
}