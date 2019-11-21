package pathfinding;

import characters.Brick;
import characters.Water;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import orth.main.GameScreen;
import systems.LevelManager;

public class GraphGenerator {

    public static GraphImp generateGraph(World world, TiledMap map) {
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get(0);

        int cost = 0;

        //width and height in tiles
        int mapHeight = LevelManager.getInstance().lvlTileHeight;
        int mapWidth = LevelManager.getInstance().lvlTileWidth;

        //for every tile create node with index
        for (int i = 0; i < mapHeight; i++){
            for(int j = 0; j < mapWidth; j++){
                Node node = new Node();
                nodes.add(node);
                node.index = nodes.size - 1;
                setCoordinates(node);
            }
        }

        //for every node create connections to neighbours
        for (int y = 0; y < mapHeight; y++){
            for (int x = 0; x < mapWidth; x++){
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y + 1);
                TiledMapTileLayer.Cell left = tiles.getCell(x - 1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x + 1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y - 1);

                //refactor this
                //here we have ID 1 == floor
                if(target.getTile().getId() == 18 || target.getTile().getId() == 19 || target.getTile().getId() == 34 || target.getTile().getId() == 2
                || target.getTile().getId() == 1 || target.getTile().getId() == 3 || target.getTile().getId() == 17 || target.getTile().getId() == 33){
                    cost = 1;
                }
                else if(target.getTile().getId() == 129){
                    cost = 50;
                    new Water(world, x+0.5f, y+0.5f);
                }
                else{
                    cost = 50;
                    new Brick(world, x+0.5f, y+0.5f);
                }

                Node targetNode = nodes.get(mapWidth * y + x);
                if(target != null){
                    if(x != 0 && left != null){
                       Node leftNode = nodes.get(mapWidth * y + x - 1);
                       targetNode.createConnection(leftNode, cost);
                    }
                    if(y < mapHeight && up != null){
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, cost);
                    }
                    if(x < mapWidth && right != null){
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, cost);
                    }
                    if(y !=0 && down != null){
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, cost);
                    }

                }

            }
        }
        //return graph with nodes with connections
        return new GraphImp(nodes);
    }

    public static void setCoordinates(Node node) {
        int index = node.getIndex();
        int y = index / LevelManager.getInstance().lvlTileWidth;
        int x = index % LevelManager.getInstance().lvlTileWidth;
        node.setCoordinates(x, y);
    }


}
