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

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/LadylikeBB.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

    private BitmapFont font12;

    private boolean ending = false;
    public SpriteBatch batch;
    private Vector2 touchedArea = new Vector2();

    private static final float SIZE = 1.0f;

    private TextureRegion backgroundTexture;
    private TextureRegion playerOneTexture;
    private TextureRegion playerTwoTexture;

    private Sprite playerOneSprite;
    private Sprite playerTwoSprite;

    private Circle p1Circle;
    private ParticleEffect effect;
    private ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
    private Sound eatSound;

    public int playerOneScore;
    public float playerOneTime;

    public int playerTwoScore;
    public float playerTwoTime;


    private static GameView instance = null;

    public GameView(Game game) {
        instance = this;
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

        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/background.png")));
        playerOneTexture = new TextureRegion(new Texture(Gdx.files.internal("heart800px.png")));
        playerTwoTexture = new TextureRegion(new Texture(Gdx.files.internal("heart300px.png")));

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
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/mist.p"), Gdx.files.internal("effects"));
        effect.setPosition(0, 0);
        effect.start();

        // Loading the enemy laser particle effect
        //enemyLaserEffect = new ParticleEffect();
        //enemyLaserEffect.load(Gdx.files.internal("effects/enemylaser.p"), Gdx.files.internal("effects"));
        //enemyLaserEffect.setPosition(0, 0);
        //enemyLaserEffect.start();

    }

	/*
	 * Render fields
	 */

    //private float timePassed = 0.0f;
    // Render the game
    public void render(float delta)
    {
        // if ending reaches 0 the lives are gone and it's gameOver
        // the Ending Screen will be called

        if (ending == true) {
            dispose();
            game.setScreen(new EndScreen(playerOneScore, game));
        }

        // Drawing everything on the screen
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerOneSprite.draw(batch);

        // Displaying the particle effects
        for (ParticleEffect eff : effects) {
            eff.draw(batch, delta/2);
        }

        // HUD
        font12.draw(batch, String.valueOf(playerOneScore), 20.0f, Gdx.graphics.getHeight()-80.0f);
        batch.end();

    }

    public void setTouchedArea(Vector2 area) {
        this.touchedArea = area;
        //ending = true;

        if (p1Circle.contains(this.touchedArea.x, this.touchedArea.y)) {
            Gdx.app.log("JSLOG", "heart touch detected");
            playerOneScore++;
        }

        if (playerOneScore >= 10) {
            ending = true;
        }


    }

    public void dispose() {
        batch.dispose();
        eatSound.dispose();
        effect.dispose();
    }

    /**
     * Accessor
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final <T extends GameView> T get() {
        return (T) instance;
    }
}
