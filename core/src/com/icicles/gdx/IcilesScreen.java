package com.icicles.gdx;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IcilesScreen implements Screen{
    //implementing screen so we will implement all the meethods
    public static final String TAG = IcilesScreen.class.getName();
    private ExtendViewport iciclesViewport;
    private ShapeRenderer renderer;
    private Player player;
    Icicles icicles;
    private Constant.Difficulty difficulty;
    private SpriteBatch batch;
    private BitmapFont font;
    private IciclesGame game;
    int topScore=0;
    //Stage s;
    private Preferences prefs;
    private ScreenViewport hudViewport;
    private gadgets gdts;
    public IcilesScreen(IciclesGame game, Constant.Difficulty d){
        this.game = game;
        this.difficulty =d;
    }
    @Override
    public void show() {
        iciclesViewport = new ExtendViewport(Constant.WORLD_SIZE, Constant.WORLD_SIZE);
        renderer = new ShapeRenderer();//inilize the shape renderer
        renderer.setAutoShapeType(true);
        hudViewport=new ScreenViewport();
        player = new Player(iciclesViewport);//creating innstance of player class
        icicles = new Icicles(difficulty,iciclesViewport);//creating innstance of Icicles class
        gdts=new gadgets(difficulty,iciclesViewport);
        batch=new SpriteBatch();
        font=new BitmapFont();
       // Gdx.input.setInputProcessor(this);
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
        hudViewport.update(width,height,true);
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
        icicles.update(delta);
        gdts.update(delta);
        player.update(delta);
        if(player.hitByIcicle(icicles))
        {
            icicles.init();
            gdts.init();
        }
        //apply viewport
        if(player.hitByGadget(gdts))
        {
            icicles.init();
            gdts.init();
        }
        //always
        Gdx.gl.glClearColor(Constant.BACKGROUND_COLOR.r, Constant.BACKGROUND_COLOR.g, Constant.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
      //  batch.setProjectionMatrix(iciclesViewport.getCamera().combined); //or your matrix to draw GAME WORLD, not UI

        batch.begin();
        batch.setColor(Color.BLACK);
        hudViewport.apply(true);
        //font.draw(batch,Integer.toString(icicles.count),10,iciclesViewport.getWorldHeight());
        topScore=Math.max(topScore,icicles.count);
        prefs.putInteger("int", topScore);
        prefs.flush();
        font.draw(batch, "Deaths: " + player.death + "\nDifficulty: " +difficulty.label,
               10,10);
        font.draw(batch, "Score: " + icicles.count+ "\nTop Score: " + topScore,
                hudViewport.getWorldWidth() - Constant.HUD_MARGIN, hudViewport.getWorldHeight() - Constant.HUD_MARGIN,
                0, Align.right, false);
        font.draw(batch,"Health: "+player.health,hudViewport.getWorldWidth()/4-20,hudViewport.getWorldHeight()-Constant.HUD_MARGIN,0,Align.right,false);
        batch.end();
        iciclesViewport.apply(true);
        renderer.setProjectionMatrix(iciclesViewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        icicles.render(renderer);
        gdts.render(renderer);
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
