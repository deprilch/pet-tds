package orth.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends ScreenAdapter {

    private Stage menuStage;
    private Table menuTable;
    private TextButton.TextButtonStyle style;
    private TextButton start;
    private TextButton exit;

    private Orth game;

    MenuScreen(Orth orth) {
        game = orth;

        Skin skin = new Skin();
        TextureAtlas menuAtlas = new TextureAtlas(Gdx.files.internal("core/assets/gui/menuPack.atlas"));
        skin.addRegions(menuAtlas);

        //Setting style for menu
        style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont();
        style.up = skin.getDrawable("Start");
        style.down = skin.getDrawable("Exit");
        style.over = skin.newDrawable("Exit");

        start = new TextButton("Start game", style);
        exit = new TextButton("Exit", style);

        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.showGameScreen();
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        menuStage = new Stage();
        menuTable = new Table();

        menuStage.addActor(menuTable);
        menuTable.setFillParent(true);
        menuTable.add(start).padBottom(10);
        menuTable.row();
        menuTable.add(exit);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuStage.draw();  //only draws
        menuStage.act();   //only acts

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.showGameScreen();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(menuStage);
    }

    @Override
    public void resize(int width, int height) {
        menuStage.getViewport().update(width, height);
    }
}
