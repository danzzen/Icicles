package com.icicles.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class gadgets {
        DelayedRemovalArray<gadget> list2;
        private Viewport viewport;
        private Constant.Difficulty difficulty;
        Icicles icicles;
        private float countDown;
        public int type;
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
            countDown-=delta*100;
            if(MathUtils.random()<delta*1.15)
            {
                if(countDown<=0) {
                    Vector2 newGdt = new Vector2(MathUtils.random() * viewport.getWorldWidth(), viewport.getWorldHeight());
                    gadget gdt = new gadget(newGdt);
                    list2.add(gdt);
                    countDown+=500;
                    Random random = new Random();
                    type = random.nextInt(3) + 1;
               }
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

                gd.render(shapeRenderer,type);
            }
        }
    }


