package orth.main;

import characters.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import pathfinding.*;
import systems.LevelManager;
import systems.ListenerClass;

public class GameScreen extends ScreenAdapter {

    private FPSLogger fpsLogger = new FPSLogger();
    private Array<Body> bodies = new Array<Body>();
    private SpriteBatch batch = new SpriteBatch();
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

    private Stage gameStage;
    private ListenerClass listenerClass;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Orth game;
    private Hero protagonist;
    private EnemyDevil enemyDevil;
    private EnemyDevil enemyDevil1;

    //Vectors for translating screen pixels to units using camera.unproject
    private Vector3 clickPointRaw = new Vector3();
    private Vector3 clickPointUnit = new Vector3();
    private Vector3 rotatePointRaw = new Vector3();
    private Vector3 rotatePointUnit = new Vector3();

    GameScreen(Orth orth) {
        game = orth;
        gameStage = new Stage(game.getViewport());
        //batch.setProjectionMatrix(game.getCamera().combined);

        world = new World(new Vector2(), true);
        LevelManager.getInstance().loadLvl(world, "core/assets/levels/basement/map.tmx");
        listenerClass = new ListenerClass(world);

        protagonist = new Hero(world, new Vector2(10, 10));

        enemyDevil = new EnemyDevil(world, new Vector2(13, 15));
        //enemyDevil1 = new EnemyDevil(world, new Vector2(5, 5));

        TmxMapLoader tmxMapLoader = new TmxMapLoader();
        TiledMap map = tmxMapLoader.load("core/assets/levels/basement/map.tmx");

        float unitScale = 1 / 16f;
        game.getCamera().setToOrtho(false, 900, 900); //How much of tiled map dots camera shows
        renderer = new OrthogonalTiledMapRenderer(map, unitScale, batch);
        renderer.setView(game.getCamera());
        game.getCamera().position.set(protagonist.getPosition().x, protagonist.getPosition().y, 0);
    }

    @Override
    public void render(float delta) {
        //fpsLogger.log();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        game.getCamera().position.set(protagonist.getPosition().x, protagonist.getPosition().y, 0);
        game.getCamera().update();
        //We tell to batch where camera is looking
        batch.setProjectionMatrix(game.getCamera().combined);
        batch.begin();

        //Here we get every body's userdata(sprites) and draw them on the body's current position
        world.getBodies(bodies);
        for (Body body : bodies) {
            Sprite sprite = (Sprite) body.getUserData();
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2); //
            sprite.draw(batch); //NOT batch.draw, because it uses fixed width/height and ignores sprite.setSize();
        }

        batch.end();

        //find path, steer to player and shoot
        if (enemyDevil.isAlive) {
            PathFinder.getInstance().search(game.getCamera(), enemyDevil, protagonist);
            //PathFinder.getInstance().search(game.getCamera(), enemyDevil1, protagonist);

            //raycast
            boolean raycast = enemyDevil.raycast(protagonist.getPosition());
            //boolean raycast1 = enemyDevil1.raycast(protagonist.getPosition());

            if(raycast) {
                enemyDevil.shoot(protagonist.getPosition());
            }
            //if(raycast1) {
            //    enemyDevil1.shoot(protagonist.getPosition());
            //}

        }

        protagonist.movement(); //refactor this.

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.showMenuScreen();
        }

        //destroy all needed objects after each step
        clearDeadBodies();

        //Box2d stuff for render and stepping
        //debugRenderer.render(world, game.getCamera().combined);
        world.step(1 / 60f, 6, 2);
    }


    public void onMouseDown(float worldX, float worldY) {
        protagonist.shoot(new Vector2(worldX, worldY));
    }

    public void rotateMouse(float worldX, float worldY) {
        protagonist.rotate(worldX, worldY);
    }

    @Override
    public void resize(int width, int height) {

        /*int correctedWidth = resolutionCorrection(width, 20);
        int correctedHeight = resolutionCorrection(height, 10);*/

        gameStage.getViewport().update(width, height); //updates viewport to current window size
    }

    //remove objects defined in toRemove array
    public void clearDeadBodies() {
        for (Body body : listenerClass.toRemove) {
            Array<Body> bd = new Array<Body>();
            world.getBodies(bd);
            if (bd.contains(body, true)) {
                body.setActive(false);
                world.destroyBody(body);
            }
            if (!bd.contains(enemyDevil.getBody(), true)) {
                enemyDevil.isAlive = false;
            }
        }
        listenerClass.toRemove.clear();
    }

    public int resolutionCorrection(int value, int pixelsPerMeter){

        value = value / pixelsPerMeter;
        value = value / 16;
        Math.floor(value);
        value = value * 16 * pixelsPerMeter;

        return value;
    }

    @Override
    public void show() {
        //Set custom contact listener to objects with isSensitive = true
        world.setContactListener(listenerClass);

        Gdx.input.setInputProcessor(gameStage);
        //Custom input processor
        //REFACTOR THIS!
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    //Set to custom vector x, y in units not in pixels of screen
                    clickPointUnit.set(game.getCamera().unproject(clickPointRaw.set(screenX, screenY, 0)));
                    onMouseDown(clickPointUnit.x, clickPointUnit.y);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                //Set to custom vector x, y in units not in pixels of screen
                rotatePointUnit.set(game.getCamera().unproject(rotatePointRaw.set(screenX, screenY, 0)));
                rotateMouse(rotatePointUnit.x, rotatePointUnit.y);
                return true;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

}
