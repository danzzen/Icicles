package com.icicles.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static java.lang.Math.abs;

public class Player {
    //we create vector to store x and y coordinates of objects
    private  Vector2 position;
    //some constants
    public int death=0,health=2;
    Icicles icicles;
    gadgets gdt;
    Vector2 positionSheild;
    public boolean isRed=false;
    public int count=0;
    public static final float PLAYER_HEIGHT_RATIO=0.02f;
    public static final float PLAYER_HEAD_RADIUS=0.001f;
    //When dealing with different screens it is often necessary to decide for a certain strategy how those different screen sizes and aspect
    // ratios should be handled. Camera and Stage support different viewport strategies, for example when doing picking via
    // Camera.project(vec, viewportX, viewportY, viewportWidth, viewportHeight).
    Viewport viewport;
    public Player(Viewport viewport)
    {   icicles=new Icicles();
        this.viewport=viewport;//assigned it the view port
        health=2;
        gdt=new gadgets();
        init();
    }
    public void init(){
        position= new Vector2(viewport.getWorldHeight(), Constant.PLAYER_HEAD_HEIGHT);
       positionSheild=new Vector2(0,0);
        //giving the initial position

    }
    public void update(float delta)//delta is time difference between last frame and current frame
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            position.x-=delta*Constant.PLAYER_MOVEMENT;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            position.x+=delta*Constant.PLAYER_MOVEMENT;
        }
        float acc=-Gdx.input.getAccelerometerY()/(Constant.GRAVITATIONAL_CONSTANT*Constant.GRAVITATIONAL_SENSTIVITY);
        position.x+=-delta*acc*Constant.PLAYER_MOVEMENT;
        ensureBounds();
        shieldAct(delta);
    }
    public void shieldAct(float delta){
        count-=delta*100;
    if(isRed==true)
    {
        if(count<=0) {
            isRed = false;
            positionSheild.x=-10;
            positionSheild.y=-10;
        }
    }
    }
    public void ensureBounds(){
        if(position.x-Constant.PLAYER_HEAD_RADIUS<0){
            position.x=Constant.PLAYER_HEAD_RADIUS;
        }
        if(position.x+ Constant.PLAYER_HEAD_RADIUS>viewport.getWorldWidth()){
            position.x=viewport.getWorldWidth()-Constant.PLAYER_HEAD_RADIUS;
        }
    }
    public boolean hitByIcicle(Icicles icicles){
        boolean isHi=false;
        for(Icicle icicle:icicles.list){
            if(icicle.position.dst(position)<Constant.PLAYER_HEAD_RADIUS) {
                isHi = true;
                health--;
                if(0>=health)
                icicles.count=0;
            }
        }
        return  isHi;
    }
    public boolean hitBySheild(Icicles icicles){
        boolean sh=false;
        for(Icicle icicle:icicles.list){

            if(abs(icicle.position.y-positionSheild.y)<=0.1&&abs(icicle.position.x-positionSheild.x)<3f*Constant.PLAYER_HEAD_RADIUS&&isRed) {
                sh = true;
                icicle.position.x=-10;
                icicle.position.y=-10;
            }
        }
        return sh;
    }
    public boolean hitByGadget(gadgets gadt)
    {
        boolean isGd=false;
        for(gadget gd:gadt.list2)
        {
            if(gd.position.dst(position)<Constant.PLAYER_HEAD_RADIUS)
            {
                isGd=true;
            }
        }
        return isGd;
    }
    public void render(ShapeRenderer renderer){
        renderer.setColor(191/255f, 244/255f, 66/255f,1);
        positionSheild=new Vector2(position.x-Constant.PLAYER_HEAD_RADIUS,position.y+Constant.PLAYER_HEAD_RADIUS+0.2f);
        if(isRed==true)
        {
            renderer.rectLine(positionSheild.x-0.5f,positionSheild.y,positionSheild.x+2*Constant.PLAYER_HEAD_RADIUS+0.5f,positionSheild.y,Constant.PLAYER_LIMB_WIDTH*3/2);
        }
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x,position.y,Constant.PLAYER_HEAD_RADIUS,20);
        Vector2 BarTop=new Vector2(position.x,position.y-Constant.PLAYER_HEAD_RADIUS);
        Vector2 BarBottom=new Vector2(position.x,BarTop.y-2*Constant.PLAYER_HEAD_RADIUS);
        renderer.rectLine(BarTop.x,BarTop.y,BarBottom.x,BarBottom.y,Constant.PLAYER_LIMB_WIDTH);
        renderer.rectLine(BarTop.x,BarTop.y,BarTop.x+Constant.PLAYER_HEAD_RADIUS,BarTop.y-Constant.PLAYER_HEAD_RADIUS,Constant.PLAYER_LIMB_WIDTH);
        renderer.rectLine(BarTop.x,BarTop.y,BarTop.x-Constant.PLAYER_HEAD_RADIUS,BarTop.y-Constant.PLAYER_HEAD_RADIUS,Constant.PLAYER_LIMB_WIDTH);
        renderer.rectLine(BarBottom.x,BarBottom.y,BarBottom.x-Constant.PLAYER_HEAD_RADIUS,BarBottom.y-Constant.PLAYER_HEAD_RADIUS,Constant.PLAYER_LIMB_WIDTH);
        renderer.rectLine(BarBottom.x,BarBottom.y,BarBottom.x+Constant.PLAYER_HEAD_RADIUS,BarBottom.y-Constant.PLAYER_HEAD_RADIUS,Constant.PLAYER_LIMB_WIDTH);

    }
}
