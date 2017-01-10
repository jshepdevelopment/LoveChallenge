package com.jshepdevelopment.lovechallenge.view;

/**
 * Created by Jason Shepherd on 1/6/2017.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jshepdevelopment.lovechallenge.screens.EndScreen;

import java.util.ArrayList;

public class GameView  {

    private Game game;

    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LadylikeBB.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

    private BitmapFont font12;

    private boolean ending = false;
    private SpriteBatch batch;

    private static final float SIZE = 1.0f;

    private Sprite playerOneSprite;
    private Sprite playerTwoSprite;
    private TextureRegion backgroundTexture;

    private Circle p1Circle;
    private ParticleEffect heartBoomEffect;
    private ParticleEffect heartFlashEffect;
    private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
    private Sound eatSound;

    private int playerOneScore;
    private float playerOneTime;

    private int playerTwoScore;
    private float playerTwoTime;

    public GameView(Game game) {
        this.game = game;
        loadItems();
    }

    // Loading all the necessary items
    private void loadItems() {

        parameter.size = 150;
        parameter.color = Color.RED;
        parameter.borderWidth = 3;
        parameter.borderColor = Color.PINK;

        font12 = generator.generateFont(parameter); // font size 12 pixels
        textButtonStyle.font = font12;
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        TextureRegion playerOneTexture;
        TextureRegion playerTwoTexture;
        playerOneTexture = new TextureRegion(new Texture(Gdx.files.internal("heart800px.png")));
        playerTwoTexture = new TextureRegion(new Texture(Gdx.files.internal("heart300px.png")));

        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/background.png")));
        playerOneSprite = new Sprite(playerOneTexture);
        playerOneSprite.setPosition(Gdx.graphics.getWidth()/2 - playerOneSprite.getWidth()/2,
                Gdx.graphics.getHeight()/2 - playerOneSprite.getHeight()/2);

        p1Circle = new Circle();
        p1Circle.set(playerOneSprite.getX() + playerOneSprite.getWidth()/2, playerOneSprite.getY() +
                playerOneSprite.getHeight()/2, playerOneSprite.getRegionWidth()/2);

        playerTwoSprite = new Sprite(playerTwoTexture);

        batch = new SpriteBatch();

        eatSound = Gdx.audio.newSound(Gdx.files.internal("sounds/eat.wav"));

        // Loading the particle effect used when snatching the food
        heartBoomEffect = new ParticleEffect();
        heartBoomEffect.load(Gdx.files.internal("effects/heartparticle.p"), Gdx.files.internal("effects"));
        heartBoomEffect.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        heartBoomEffect.start();

        heartFlashEffect = new ParticleEffect();
        heartFlashEffect.load(Gdx.files.internal("effects/heartflash.p"), Gdx.files.internal("effects"));
        heartFlashEffect.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        heartFlashEffect.start();

    }

    private float theTimer = 0.0f;
    int heartFlashTimerCount = 0;
    double[] heartFlashTimer = new double[heartFlashTimerCount];

    // Render the game
    public void render(float delta)
    {
        // if ending reaches 0 the lives are gone and it's gameOver
        // the Ending Screen will be called

        /*for(int i = 0; i < heartFlashTimerCount; i ++) {
            heartFlashTimer[i] += delta;
            Gdx.app.log("JSLOG", "heartFlashTimer[i]: " + heartFlashTimer[i]);

            if(heartFlashTimer[i] > 1.0f) {

                effects.remove(heartFlashEffect);
            }
        }*/

        theTimer += delta;

        if (ending) {
            dispose();
            game.setScreen(new EndScreen(playerOneScore, game));
        }

        // Drawing everything on the screen
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerOneSprite.draw(batch);

        // Displaying the heart boom particle effects
        for (ParticleEffect eff : effects) {
            eff.draw(batch, delta/2);
        }
        //heartBoomEffect.draw(batch, delta/2);

        // HUD
        font12.draw(batch, String.valueOf(playerOneScore), Gdx.graphics.getWidth()/2 -
                Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-100.0f);
        font12.draw(batch, String.valueOf((int)theTimer), Gdx.graphics.getWidth()/2 +
                Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-100.0f);
        batch.end();

    }

    public void setTouchedArea(Vector2 area) {

        // Check for touch within bounding circle and increase score
        if (p1Circle.contains(area.x, area.y)) {

            // Updating the sweet graphics
            //Gdx.app.log("JSLOG", "heart touch detected");

            if(effects.contains(heartBoomEffect)) {
                effects.remove(heartBoomEffect);
            }
            effects.add(heartBoomEffect);
            heartBoomEffect.start();

            if(effects.contains(heartFlashEffect)) {
                effects.remove(heartFlashEffect);
            }

            effects.add(heartFlashEffect);
            heartFlashEffect.start();

            heartFlashTimerCount++;
            playerOneScore++;

        }

        // Remove after debug
        if ((int)theTimer >= 10) {
            ending = true;
        }
    }

    public void dispose() {
        batch.dispose();
        eatSound.dispose();
        heartBoomEffect.dispose();
    }

}
