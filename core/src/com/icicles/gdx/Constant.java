package com.icicles.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
public class Constant {
    public static final float WORLD_SIZE = 10.0f;
    public static final float PLAYER_HEAD_RADIUS = 0.5f;
    public static final float PLAYER_HEAD_HEIGHT = 4.0f * PLAYER_HEAD_RADIUS;
    public static final float PLAYER_LIMB_WIDTH = 0.1f;
    public static final int PLAYER_HEAD_SEGMENTS = 20;
    public static final Color BACKGROUND_COLOR = Color.BLUE;
    public static final Color ICICLE_COLOR = Color.WHITE;
    public static final float PLAYER_MOVEMENT = 10.0f;
    public static final float GRAVITATIONAL_CONSTANT = 9.8f;
    public static final float GRAVITATIONAL_SENSTIVITY = 0.86f;
    public static final float ICICLES_HEIGHT = 1.0f;
    public static final float ICICLES_WIDTH = 0.5f;
    public static final Vector2 ICICLES_ACCELERATION = new Vector2(0, -5.0f);
    public static final float ICICLE_SPAWNS_PER_SECOND = 10.0f;
    public static final String EASY_LABEL = "Easy";
    public static final String MEDIUM_LABEL = "Hard";
    public static final String HARD_LABEL = "Expert";
    public static final float EASY_SPAWNS_PER_SECOND = 5;
    public static final float MEDIUM_SPAWNS_PER_SECOND = 10;
    public static final float HARD_SPAWNS_PER_SECOND =17;
    public static final Color EASY_COLOR = new Color(0.2f, 0.2f, 1, 1);
    public static final Color MEDIUM_COLOR = new Color(0.5f, 0.5f, 1, 1);
    public static final Color HARD_COLOR = new Color(0.7f, 0.7f, 1, 1);
    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;
    public static final float HUD_MARGIN = 20.0f;
    public static final float DIFFICULTY_WORLD_SIZE = 480.0f;
    public static final float DIFFICULTY_BUBBLE_RADIUS = DIFFICULTY_WORLD_SIZE / 9;
    public static final float DIFFICULTY_LABEL_SCALE = 1.5f;
    public static final Vector2 EASY_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 2, DIFFICULTY_WORLD_SIZE*3/4);
    public static final Vector2 MEDIUM_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE / 2, DIFFICULTY_WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(DIFFICULTY_WORLD_SIZE /2, DIFFICULTY_WORLD_SIZE *1/4);

    public enum Difficulty {
        EASY(EASY_SPAWNS_PER_SECOND, EASY_LABEL),
        MEDIUM(MEDIUM_SPAWNS_PER_SECOND, MEDIUM_LABEL),
        HARD(HARD_SPAWNS_PER_SECOND, HARD_LABEL);

        float spawnRate;
        String label;

        Difficulty(float spawnRate, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
        }
    }
}
