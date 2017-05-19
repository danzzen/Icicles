package com.icicles.gdx;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lenovo on 13-05-2017.
 */

public class gadget{
    Vector2 position;
    Vector2 velocity;
    public int type=1;
    public gadget(Vector2 position)
    {
        this.position=position;
        velocity=new Vector2(0,0);
    }
    public void update(float delta)
    {

        velocity.mulAdd(Constant.GADGETS_ACCELERATION, delta);
//        public Vector2 mulAdd (Vector2 vec, float scalar) {
//            this.x += vec.x * scalar;
//            this.y += vec.y * scalar;
//            return this;
//        }
        position.mulAdd(velocity, delta);
    }
    public void render(ShapeRenderer shapeRenderer,int t)
    {type=t;
     switch (type)
     {
         case 1:
             shapeRenderer.setColor(Constant.GADGETTYPE_1);
             shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
             shapeRenderer.circle(position.x,position.y,Constant.GADAGETTYPE_1_RADIUS);
             break;
         case 2:
             shapeRenderer.setColor(Constant.GADGETTYPE_2);
             shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
             shapeRenderer.circle(position.x,position.y,Constant.GADAGETTYPE_1_RADIUS);
             break;
         case 3:
             shapeRenderer.setColor(Constant.GADGETTYPE_3);
             shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
             shapeRenderer.circle(position.x,position.y,Constant.GADAGETTYPE_1_RADIUS);
             break;
     }
    }
}
