package com.icicles.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lenovo on 12-04-2017.
 */

public class Constant {
    public static final float WORLD_SIZE = 10.0f;
    // TODO: Add constant for player head radius
    public static final float PLAYER_HEAD_RADIUS = 0.5f;

    // TODO: Add constant for player head height
    public static final float PLAYER_HEAD_HEIGHT = 4.0f * PLAYER_HEAD_RADIUS;

    // TODO: Add constant for player limb width
    public static final float PLAYER_LIMB_WIDTH = 0.1f;

    // TODO: Add constant for circle segments for the player's head
    public static final int PLAYER_HEAD_SEGMENTS = 20;
    // TODO: Add a constant for the background color of the world
    public static final Color BACKGROUND_COLOR = Color.BLUE;

    // TODO: Add a constant for the height of the icicle
//    public static final float ICICLES_HEIGHT = 1.0f;
//
//    // TODO: Add a constant for the width of the icicle
//    public static final float ICICLES_WIDTH = 0.5f;

    public static final Color ICICLE_COLOR = Color.WHITE;
    public static final float PLAYER_MOVEMENT = 10.0f;
    public static final float GRAVITATIONAL_CONSTANT = 9.8f;
    public static final float GRAVITATIONAL_SENSTIVITY = 0.86f;

    public static final float ICICLES_HEIGHT = 1.0f;
    public static final float ICICLES_WIDTH = 0.5f;

    // TODO: Add constant for icicle acceleration
    public static final Vector2 ICICLES_ACCELERATION = new Vector2(0, -5.0f);

    // TODO: Add constant for icicle spawns per second
    public static final float ICICLE_SPAWNS_PER_SECOND = 10.0f;
}
