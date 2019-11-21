package orth.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Orth extends Game {

	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private Viewport viewport;
	private OrthographicCamera camera;

	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1024, 768);
		Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
		Gdx.graphics.setFullscreenMode(mode);
		Gdx.graphics.setVSync(true);

		Box2D.init();
		camera = new OrthographicCamera();
		viewport = new PixelPerfectViewPort(20f, 11f, camera);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);

		showMenuScreen();
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void showMenuScreen() {
		setScreen(menuScreen);
	}

	public void showGameScreen() {
		setScreen(gameScreen);
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public void dispose() {
		System.exit(0);
	}
}
