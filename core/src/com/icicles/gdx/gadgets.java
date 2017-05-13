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
        public int count=0,c=0;
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
//            if(MathUtils.random()<delta*difficulty.spawnRate) {
//                Vector2 newIciclepos = new Vector2(MathUtils.random() * viewport.getWorldWidth(), viewport.getWorldHeight());
//
//                Icicle icicle = new Icicle(newIciclepos);
//                list.add(icicle);
//            }
            if(MathUtils.random()<delta*.2)
            {
                Vector2 newGdt=new Vector2(MathUtils.random()*viewport.getWorldWidth(),viewport.getWorldHeight());
                gadget gdt=new gadget(newGdt);
                list2.add(gdt);
            }
//            for(Icicle icicle: list){
//                icicle.update(delta);
//            }
            for(gadget gdt: list2){
                gdt.update(delta);
            }
           // list.begin();
            list2.begin();
//            for(int i=0;i<list.size;i++)
//            {
//                if(list.get(i).position.y<Constant.PLAYER_HEAD_HEIGHT/2+0.2&&list.get(i).position.y>Constant.PLAYER_HEAD_HEIGHT/2)
//                {
//                    count++;
//                }
//            }
//            for (int i = 0; i < list.size; i++) {
//                if (list.get(i).position.y < -Constant.ICICLES_HEIGHT) {
//                    list.removeIndex(i);
//                    c++;
//                }
//            }
            for (int i = 0; i < list2.size; i++) {
                if (list2.get(i).position.y < -Constant.GADAGETTYPE_1_RADIUS) {
                    list2.removeIndex(i);

                }
            }
           // list.end();
            list2.end();
        }
        public void render(ShapeRenderer shapeRenderer){
//            shapeRenderer.setColor(Constant.ICICLE_COLOR);
//            for(Icicle icicle:list){
//                icicle.render(shapeRenderer);
//            }
            shapeRenderer.setColor(Constant.GADGETTYPE_1);
            for(gadget gd:list2){
                gd.render(shapeRenderer);
            }
        }
    }


