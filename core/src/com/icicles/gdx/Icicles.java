package com.icicles.gdx;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
public class Icicles {
    DelayedRemovalArray<Icicle> list;

    Viewport viewport;
    Constant.Difficulty difficulty;
    int count=0,c=0;
    public Icicles(){

    }
    public Icicles(Constant.Difficulty difficulty,Viewport viewport){
        this.viewport=viewport;
        this.difficulty=difficulty;
        init();
    }
    public void  init(){
        list=new DelayedRemovalArray<Icicle>(100);

    }
    public void update(float delta){
        if(MathUtils.random()<delta*difficulty.spawnRate) {
            Vector2 newIciclepos = new Vector2(MathUtils.random() * viewport.getWorldWidth(), viewport.getWorldHeight());

            Icicle icicle = new Icicle(newIciclepos);
            list.add(icicle);
        }

        for(Icicle icicle: list){
            icicle.update(delta);
        }

        list.begin();

        for(int i=0;i<list.size;i++)
        {
            if(list.get(i).position.y<Constant.PLAYER_HEAD_HEIGHT/2+0.2&&list.get(i).position.y>Constant.PLAYER_HEAD_HEIGHT/2)
            {
                count++;
            }
        }
        for (int i = 0; i < list.size; i++) {
            if (list.get(i).position.y < -Constant.ICICLES_HEIGHT) {
                list.removeIndex(i);
                c++;
            }
        }

        list.end();

    }
    public void render(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(Constant.ICICLE_COLOR);
        for(Icicle icicle:list){
            icicle.render(shapeRenderer);
        }

    }
}
