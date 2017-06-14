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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ResumeScreen extends InputAdapter implements Screen,AssetErrorListener {
    private ExtendViewport pasViewport;
    private ShapeRenderer renderer;
    private SpriteBatch batch;
    private BitmapFont font;
    Constant.Difficulty di;
    private IciclesGame game;
    private Icicles icicles;
    private int score, topscore;
    private ScreenViewport hudViewport;
    Stage stage;
    TextButton button;
    TextButton.TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    AssetManager assetManager;
    TextureAtlas.AtlasRegion atlasRegion;
    private static final String ATLAS ="images/button.pack.atlas";
    private static final String STANDING_RIGHT ="btn";
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
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        assetManager=new AssetManager();
        assetManager.setErrorListener( this);
        assetManager.load(ATLAS,TextureAtlas.class);
        assetManager.finishLoading();
        TextureAtlas atlas=assetManager.get(ATLAS);
        atlasRegion= atlas.findRegion(STANDING_RIGHT);
        skin = new Skin();
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("btn");
        button = new TextButton("Button1", textButtonStyle);
        button.setHeight(Gdx.graphics.getHeight()/6); //** Button Height **//
        button.setWidth(Gdx.graphics.getWidth()/8); //** Button Width **//

        button.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight());
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//

                return true;

            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Rggggggeleased");

                ///and level
                game.showDifficultyScreen();

                dispose();

            }
        });



        MoveToAction moveAction = new MoveToAction();//Add dynamic movement effects to button
        moveAction.setPosition(Gdx.graphics.getWidth()/2-button.getWidth()/2, Gdx.graphics.getHeight()/2+ Gdx.graphics.getHeight()/6);
        moveAction.setDuration(.5f);
        button.addAction(moveAction);
        stage.addActor(button);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(242 / 255f, 194 / 255f, 128 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();        //  pasViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined); //or your matrix to draw GAME WORLD, not UI
        batch.begin();

        stage.draw();
        batch.setColor(Color.BLUE);
        hudViewport.apply();
        // font.getData().setScale(3,4);
        font.setColor(Color.BLACK);


       // Did you try setScale() method that what i use to resize my font

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       font.getData().setScale(3);

        if(topscore<score)
        {
            font.draw(batch,"New High\n",hudViewport.getWorldWidth()/2-40,hudViewport.getWorldHeight()/2+80f);
            font.draw(batch, "Score: "+score,
                    hudViewport.getWorldWidth()/2-30,hudViewport.getWorldHeight()/2+10);
        }
        else{
            font.draw(batch,"Best : ",hudViewport.getWorldWidth()/2-40,hudViewport.getWorldHeight()/2+80f);
            font.draw(batch, "Score: "+score,
                    hudViewport.getWorldWidth()/2-30,hudViewport.getWorldHeight()/2+10);
        }

        font.setColor(Color.BLACK);
        batch.end();
        pasViewport.apply();
        renderer.setProjectionMatrix(pasViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);


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
  //  @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
////        Gdx.input.setInputProcessor(this);
//        Vector2 WorldTouch=pasViewport.unproject(new Vector2(screenX,screenY));
//        Vector2 rtouch=new Vector2(pasViewport.getWorldWidth()/4,pasViewport.getWorldHeight()/4);
//        Vector2 etouct=new Vector2(pasViewport.getWorldWidth()*3/4,pasViewport.getWorldHeight()/4);
//        if(WorldTouch.dst(rtouch)<0.7)
//        {
//            game.showIciclesScreen(di);
//            //Gdx.input.setInputProcessor(null);
//        }
//        if(WorldTouch.dst(etouct)<0.7)
//        {
//            game.showDifficultyScreen();
//           // Gdx.input.setInputProcessor(null);
//        }
//        return true;
//    }


    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {

    }
}
