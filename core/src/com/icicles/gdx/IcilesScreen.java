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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    public  static int topScore = 0,cScore=0;
    private Preferences prefs;
    private ScreenViewport hudViewport;
    private gadgets gdts;
    TextButton button,button2,button3,button4;
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
        backgroundTexture = new TextureRegion(new Texture("background.png") ,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        assetManager = new AssetManager();
        assetManager.setErrorListener(this);
        assetManager.load(ATLAS, TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas = assetManager.get(ATLAS);
        atlasRegion = atlas.findRegion(STANDING_RIGHT);
        skin = new Skin();
        stage = new Stage();
        //stage.addAction(backgroundTexture);
        prefs = Gdx.app.getPreferences("my-preferences");
        if (prefs.getInteger("int") == 0) {
            topScore = 0;
        } else {
            topScore = prefs.getInteger("int");
        }
        Gdx.input.setInputProcessor(stage);
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("btn");

        button = new TextButton("TopScore "+topScore, textButtonStyle);
        button.setHeight(Gdx.graphics.getHeight() / 8);button.setWidth(Gdx.graphics.getWidth() / 5);button.setPosition(0,Gdx.graphics.getHeight()*2/3+10);




        MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        moveAction.setPosition(Gdx.graphics.getWidth() / 2-button.getWidth()/2, Gdx.graphics.getHeight() *2/3+10);
        moveAction.setDuration(.3f);
        button.addAction(moveAction);
        button.setName("top");
        stage.addActor(button);

        button2 = new TextButton("Your score ", textButtonStyle);

        button2.setHeight(Gdx.graphics.getHeight() / 8);button2.setWidth(Gdx.graphics.getWidth() / 5);button2.setPosition(0,Gdx.graphics.getHeight()/2+20);



        MoveToAction moveAction1 = new MoveToAction();//Add dynamic movement effects to button
        moveAction1.setPosition(Gdx.graphics.getWidth() / 2-button2.getWidth()/2, Gdx.graphics.getHeight()/2+20);
        moveAction1.setDuration(.4f);
        button2.addAction(moveAction1);
        button2.setName("your");
        stage.addActor(button2);

        button3 = new TextButton("Restart", textButtonStyle);

        button3.setHeight(Gdx.graphics.getHeight() / 8);button3.setWidth(Gdx.graphics.getWidth() / 5);button3.setPosition(0,Gdx.graphics.getHeight()/3+20);
        button3.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("my app", "Rggggggeleased");
                game.showIciclesScreen(difficulty);
                dispose();
            }
        });


        MoveToAction moveAction3 = new MoveToAction();//Add dynamic movement effects to button
        moveAction3.setPosition(Gdx.graphics.getWidth() / 2-button3.getWidth()/2, Gdx.graphics.getHeight()/3+20);
        moveAction3.setDuration(.5f);
        button3.addAction(moveAction3);
        button3.setName("re");
        stage.addActor(button3);

        button4 = new TextButton("Back", textButtonStyle);

        button4.setHeight(Gdx.graphics.getHeight() / 8);button4.setWidth(Gdx.graphics.getWidth() / 5);button4.setPosition(0,Gdx.graphics.getHeight()/4-10);
        button4.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                game.showDifficultyScreen();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("my app", "Rggggggeleased");

                dispose();
            }
        });


        MoveToAction moveAction4 = new MoveToAction();//Add dynamic movement effects to button
        moveAction4.setPosition(Gdx.graphics.getWidth() / 2-button4.getWidth()/2, Gdx.graphics.getHeight()/ 4-10);
        moveAction4.setDuration(.6f);
        button4.addAction(moveAction4);
        button4.setName("bk");
        stage.addActor(button4);

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
            resume();
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
            cScore=icicles.count;
            if(cScore!=0)
            prefs.putInteger("score", cScore);
            font.draw(batch, "Score: " + cScore + "\nTop Score: " + topScore,
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

//        icicles.update(delta);
//        gdts.update(delta);
        TextButton labelActor = (TextButton) stage.getRoot().findActor("your");
        labelActor.setText("Your Score : "+prefs.getInteger("score"));

        iciclesViewport.apply(true);
        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        batch.begin();
        batch.draw(backgroundTexture,0,0);
        TextButton hg = (TextButton) stage.getRoot().findActor("top");
        hg.setText("High Score : "+topScore);
        batch.end();
        stage.act();
        stage.draw();
        renderer.end();
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
           resume();
        }
    }

    @Override
    public void hide() {
    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }
}
