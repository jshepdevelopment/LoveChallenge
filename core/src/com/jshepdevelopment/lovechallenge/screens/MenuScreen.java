package com.jshepdevelopment.lovechallenge.screens;

/**
 * Created by Jason Shepherd on 1/6/2017.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen{

    private Game game;

    private TextureRegion backgroundTexture;
    private SpriteBatch batch;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonPlay;
    private Label heading;
    private BitmapFont title;

    public MenuScreen (Game game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1);

        stage.act(delta);
        batch.begin();
        batch.setColor(Color.WHITE);
        //		bckRenderer.render(-1);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.draw();
        //		Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        backgroundTexture = new TextureRegion(new Texture(Gdx.files.internal("background/background.png")));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/blueButtons.pack");
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Button PLAY
        buttonPlay = new TextButton("Play", skin);
        buttonPlay.pad(20);
        buttonPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        //Button EXIT
        buttonExit = new TextButton("Exit", skin);
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        title = new BitmapFont(Gdx.files.internal("fonts/title.fnt"));
        LabelStyle headingStyle = new LabelStyle(title, new Color(255, 255, 255, 255));
        heading = new Label("Valentine's Day Love Challenge", headingStyle);

        //putting all that into a table
        table.add(heading);
        table.getCell(heading).spaceBottom(200);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(20);
        table.row();
        table.add(buttonExit);
        table.getCell(buttonExit).spaceBottom(20);
        table.debug();
        stage.addActor(table);

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        batch.dispose();
    }

}
