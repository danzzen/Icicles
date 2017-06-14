package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IcilesScreen extends InputAdapter implements Screen, AssetErrorListener {
    public static final String TAG = IcilesScreen.class.getName();
    private ExtendViewport iciclesViewport;
    private ShapeRenderer renderer;
    private Player player;
    Icicles icicles;
    private Constant.Difficulty difficulty;
    private SpriteBatch batch;
    private BitmapFont font;
    private IciclesGame game;
    int topScore = 0;
    private Preferences prefs;
    private ScreenViewport hudViewport;
    private gadgets gdts;
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    AssetManager assetManager;
    TextureAtlas.AtlasRegion atlasRegion;
    TextureRegion backgroundTexture;
    private static final String ATLAS = "images/button.pack.atlas";
    private static final String STANDING_RIGHT = "btn";
    Stage stage;

    public IcilesScreen(IciclesGame game, Constant.Difficulty d) {
        this.game = game;
        this.difficulty = d;
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {
        iciclesViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);
        renderer = new ShapeRenderer();//inilize the shape renderer
        renderer.setAutoShapeType(true);
        hudViewport = new ScreenViewport();
        player = new Player(iciclesViewport);//creating innstance of player class
        icicles = new Icicles(difficulty, iciclesViewport);//creating innstance of Icicles class
        gdts = new gadgets(difficulty, iciclesViewport);
        batch = new SpriteBatch();
        font = new BitmapFont();
        backgroundTexture = new TextureRegion(new Texture("background_edited.jpg") ,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas = assetManager.get(ATLAS);
        atlasRegion = atlas.findRegion(STANDING_RIGHT);
        skin = new Skin();
        stage = new Stage();
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("btn");
        button = new TextButton("Button1", textButtonStyle);
        button.setHeight(Gdx.graphics.getHeight() / 3); //** Button Height **//
        button.setWidth(Gdx.graphics.getWidth() / 4); //** Button Width **//
        button.setPosition(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight());
        button.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//

                return true;

            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Rggggggeleased");

                ///and level
                game.showDifficultyScreen();

                dispose();

            }
        });


        MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        moveAction.setPosition(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2, Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 6);
        moveAction.setDuration(.5f);
        button.addAction(moveAction);
        stage.addActor(button);
        //stage.addAction(backgroundTexture);
        prefs = Gdx.app.getPreferences("my-preferences");
        if (prefs.getInteger("int") == 0) {
            topScore = 0;
        } else {
            topScore = prefs.getInteger("int");
        }

//        font.draw(batch,fps,10,iciclesViewport.getWorldHeight());
    }

    @Override
    public void resize(int width, int height) {
        iciclesViewport.update(width, height, true);//update viewport on resize
        hudViewport.update(width, height, true);
        player.init();
        icicles.init();
        gdts.init();
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
        if (player.health <= 0) {
            Gdx.gl.glClearColor(242 / 255f, 194 / 255f, 128 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act();
            batch.begin();
            batch.draw(backgroundTexture,0,0);
            stage.draw();
            batch.end();
        } else {
            icicles.update(delta);
            gdts.update(delta);
            player.update(delta);
            if (player.hitByIcicle(icicles)) {
                icicles.init();
                gdts.init();
            }
            //apply viewport
            if (player.hitByGadget(gdts)) {
                if (gdts.type == 1)
                    icicles.init();
                if (gdts.type == 2) {
                    player.count = 500;
                    player.isRed = true;
                }
                if (gdts.type == 3) {
                    if (player.health < 5)
                        player.health += 1;
                }
                gdts.init();
            }
            if (player.hitBySheild(icicles)) {

            }
            //always

            Gdx.gl.glClearColor(242 / 255f, 194 / 255f, 128 / 255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //  batch.setProjectionMatrix(iciclesViewport.getCamera().combined); //or your matrix to draw GAME WORLD, not UI

            batch.begin();
            batch.draw(backgroundTexture,0,0);

            hudViewport.apply(true);
            //font.draw(batch,Integer.toString(icicles.count),10,iciclesViewport.getWorldHeight());
            topScore = Math.max(topScore, icicles.count);
            prefs.putInteger("int", topScore);
            prefs.flush();
            font.draw(batch, "Score: " + icicles.count + "\nTop Score: " + topScore,
                    hudViewport.getWorldWidth() - Constant.HUD_MARGIN, hudViewport.getWorldHeight() - Constant.HUD_MARGIN,
                    0, Align.right, false);
            font.draw(batch, "Life: " + player.health, hudViewport.getWorldWidth() / 4 - 20, hudViewport.getWorldHeight() - Constant.HUD_MARGIN, 0, Align.right, false);
            batch.end();
            iciclesViewport.apply(true);
            renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);
            renderer.begin(ShapeRenderer.ShapeType.Filled);
            icicles.render(renderer);
            gdts.render(renderer);
            player.render(renderer);
            renderer.end();
        }

    }

    @Override
    public void resume() {
        game.showResumeScreen(icicles.count, topScore, difficulty);
    }

    @Override
    public void hide() {
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 WorldTouch = iciclesViewport.unproject(new Vector2(screenX, screenY));
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {

        }
        return false;
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }
}
