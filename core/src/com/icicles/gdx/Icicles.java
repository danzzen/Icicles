package com.icicles.gdx;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by lenovo on 12-04-2017.
 */

public class Icicles {
    DelayedRemovalArray<Icicle> list;
    Viewport viewport;
    public Icicles(Viewport viewport){
        this.viewport=viewport;
        init();
    }
    public void init(){
        list=new DelayedRemovalArray<Icicle>(100);
    }
    public void update(float delta){
        if(MathUtils.random()<delta*Constant.ICICLE_SPAWNS_PER_SECOND) {
            Vector2 newIciclepos = new Vector2(MathUtils.random() * viewport.getWorldWidth(), viewport.getWorldHeight());

            Icicle icicle = new Icicle(newIciclepos);
            list.add(icicle);
        }
        for(Icicle icicle: list){
            icicle.update(delta);


        }
        list.begin();

        // TODO: Remove any icicle completely off the bottom of the screen
        for (int i = 0; i < list.size; i++) {
            if (list.get(i).position.y < -Constant.ICICLES_HEIGHT) {
                list.removeIndex(i);
            }
        }
        // TODO: End removal session
        list.end();
    }
    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(Constant.ICICLE_COLOR);
        for(Icicle icicle:list){
            icicle.render(shapeRenderer);
        }
    }
}
