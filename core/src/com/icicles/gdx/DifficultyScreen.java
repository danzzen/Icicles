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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;


public class DifficultyScreen  extends InputAdapter implements Screen,AssetErrorListener {

    public static final String TAG = DifficultyScreen.class.getName();
    private static final String ATLAS ="images/button.pack.atlas";
    private static final String STANDING_RIGHT ="diffuclty";

    IciclesGame game;
    private ShapeRenderer renderer;
    SpriteBatch batch;
    ExtendViewport viewport;
    BitmapFont font;
   // AssetManager assetManager;
   /// TextureAtlas.AtlasRegion atlasRegion;
    public DifficultyScreen(IciclesGame game) {
        this.game = game;
    }
    TextureRegion backgroundTexture;
    Stage stage;
    TextButton button,button2,button3,button4,button5;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    AssetManager assetManager;
    TextureAtlas.AtlasRegion atlasRegion;
    GDXDialogs dialogs;

    @Override

    public void show() {
        renderer = new ShapeRenderer();
         dialogs = GDXDialogsSystem.install();
        font=new BitmapFont();
        batch = new SpriteBatch();
        backgroundTexture = new TextureRegion(new Texture("background.png") ,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport = new ExtendViewport(Constant.DIFFICULTY_WORLD_SIZE, Constant.DIFFICULTY_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
        assetManager=new AssetManager();
        assetManager.setErrorListener( this);
        assetManager.load(ATLAS,TextureAtlas.class);
        assetManager.finishLoading();
        font.getData().setScale(Constant.DIFFICULTY_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        TextureAtlas atlas=assetManager.get(ATLAS);
        atlasRegion= atlas.findRegion(STANDING_RIGHT);
        skin = new Skin();

        stage=new Stage();
        Gdx.input.setInputProcessor(stage);
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("diffuclty");
        button = new TextButton("Easy", textButtonStyle);
        button.setHeight(Gdx.graphics.getHeight()/8); //** Button Height **//
        button.setWidth(Gdx.graphics.getWidth()/6); //** Button Width **//
        button2=new TextButton("Hard",textButtonStyle);
        button2.setHeight(Gdx.graphics.getHeight()/8); //** Button Height **//
        button2.setWidth(Gdx.graphics.getWidth()/6); //** Button Width **//
        button3=new TextButton("Expert",textButtonStyle);
        button3.setHeight(Gdx.graphics.getHeight()/8); //** Button Height **//
        button3.setWidth(Gdx.graphics.getWidth()/6); //** Button Width **//
        button.setPosition(0,Gdx.graphics.getHeight()*3/4);
        button2.setPosition(0,Gdx.graphics.getHeight()*3/4);
        button3.setPosition(0,Gdx.graphics.getHeight()*3/4);
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                game.showIciclesScreen(Constant.Difficulty.EASY);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.showIciclesScreen(Constant.Difficulty.EASY);
                dispose();
            }});



        MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        moveAction.setPosition(Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()*3/4);
        moveAction.setDuration(.5f);
        button.addAction(moveAction);

        button2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.showIciclesScreen(Constant.Difficulty.MEDIUM);
                dispose();
            }});



        MoveToAction moveAction1 = new MoveToAction();//Add dynamic movement effects to button
        moveAction1.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()*3/4);
        moveAction1.setDuration(.4f);
        button2.addAction(moveAction1);
        stage.addActor(button2);
        stage.addActor(button);
        button3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.showIciclesScreen(Constant.Difficulty.HARD);
                dispose();
            }});



        MoveToAction moveAction3 = new MoveToAction();//Add dynamic movement effects to button
        moveAction3.setPosition(Gdx.graphics.getWidth()*3/4, Gdx.graphics.getHeight()*3/4);
        moveAction3.setDuration(.2f);
        button3.addAction(moveAction3);
        stage.addActor(button3);
        button4= new TextButton("About", textButtonStyle);
        button4.setHeight(Gdx.graphics.getHeight()/8); //** Button Height **//
        button4.setWidth(Gdx.graphics.getWidth()/6); //** Button Width **//
        button4.setPosition(0,Gdx.graphics.getHeight()/2);
        button4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("About");
                bDialog.setMessage("How to play\n" +
                        "1.you have to avoid the falling white color icicles(Give damage only when front point directly touch the head)\n"+
                        "2.You can increase life by collecting green balls\n" +
                        "3.You can clear all icicles using Blue ball\n" +
                        "4.Orange Ball will provide a Shield ");

                bDialog.setClickListener(new ButtonClickListener() {

                    @Override
                    public void click(int button) {
                        // handle button click here
                    }
                });

                bDialog.addButton("Got it");
                bDialog.build().show();

                dispose();
            }});



        MoveToAction moveAction4= new MoveToAction();//Add dynamic movement effects to button
        moveAction4.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        moveAction4.setDuration(.5f);
        button4.addAction(moveAction4);
        stage.addActor(button4);
        button5=new TextButton("Quit",textButtonStyle);
        button5.setHeight(Gdx.graphics.getWidth()/8);
        button5.setWidth(Gdx.graphics.getWidth()/6); //** Button Width **//
        button5.setPosition(0,Gdx.graphics.getHeight()*2/3);
        button5.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
            }});



        MoveToAction moveAction5= new MoveToAction();//Add dynamic movement effects to button
        moveAction5.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/4);
        moveAction5.setDuration(.5f);
        button5.addAction(moveAction5);
        stage.addActor(button5);
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
        batch.begin();
        batch.draw(backgroundTexture,0,0);
        batch.end();
        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }
}