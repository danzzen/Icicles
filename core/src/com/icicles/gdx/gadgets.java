package com.icicles.gdx;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by lenovo on 13-05-2017.
 */

public class gadgets {
        DelayedRemovalArray<gadget> list2;
        Viewport viewport;
        Constant.Difficulty difficulty;
        Icicles icicles;
        public gadgets(){
        }
        public gadgets(Constant.Difficulty difficulty,Viewport viewport){
            this.viewport=viewport;
            this.difficulty=difficulty;
            init();
        }
        public void init(){

            list2=new DelayedRemovalArray<gadget>(100);
        }
        public void update(float delta){

            if(MathUtils.random()<delta*0.15)
            {
                Vector2 newGdt=new Vector2(MathUtils.random()*viewport.getWorldWidth(),viewport.getWorldHeight());
                gadget gdt=new gadget(newGdt);
                list2.add(gdt);
            }

            for(gadget gdt: list2){
                gdt.update(delta);
            }

            list2.begin();

            for (int i = 0; i < list2.size; i++) {
                if (list2.get(i).position.y < -Constant.GADAGETTYPE_1_RADIUS) {
                    list2.removeIndex(i);
                }
            }

            list2.end();
        }
        public void render(ShapeRenderer shapeRenderer){

            shapeRenderer.setColor(Constant.GADGETTYPE_1);
            for(gadget gd:list2){
                gd.render(shapeRenderer);
            }
        }
    }


