package systems;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import pathfinding.GraphGenerator;
import pathfinding.GraphImp;

public class LevelManager {

    private static volatile LevelManager instance;

    public static LevelManager getInstance() {
        if (instance == null)
            instance = new LevelManager();
        return instance;
    }

    private LevelManager() {
    }

    public int lvlTileWidth;
    public int lvlTileHeight;
    public int lvlPixelWidth;
    public int lvlPixelHeight;
    public int tilePixelWidth;
    public int tilePixelHeight;
    public TiledMap tiledMap;
    public GraphImp graph;

    public void loadLvl(World world, String filePath) {
        tiledMap = new TmxMapLoader().load(filePath);

        MapProperties properties = tiledMap.getProperties();
        lvlTileWidth = properties.get("width", Integer.class); //amount of tiles
        lvlTileHeight = properties.get("height", Integer.class);
        tilePixelWidth = properties.get("tilewidth", Integer.class);  //tilewidth
        tilePixelHeight = properties.get("tileheight", Integer.class);//tileheight
        lvlPixelWidth = tilePixelWidth * lvlTileWidth;
        lvlPixelHeight = tilePixelHeight * lvlTileHeight;

        graph = GraphGenerator.generateGraph(world, tiledMap);
    }

}
