package pathfinding;

import characters.base.Creation;
import characters.base.DynamicCreation;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import steering.SteerableEntity;
import systems.LevelManager;

public class PathFinder {

    private static volatile PathFinder instance;

    public static PathFinder getInstance() {
        if (instance == null)
            instance = new PathFinder();
        return instance;
    }

    private IndexedAStarPathFinder<Node> pathFinder = new IndexedAStarPathFinder<Node>(LevelManager.getInstance().graph, false);
    private GraphPathImp resultPath = new GraphPathImp();
    private HeuristicImp heuristic = new HeuristicImp();

    private PathFinder() {
    }

    public void search(OrthographicCamera camera, DynamicCreation start, Creation end) {
        Node startNode = LevelManager.getInstance().graph.getNode((int) start.getPosition().x, (int) start.getPosition().y);
        Node endNode = LevelManager.getInstance().graph.getNode((int) end.getPosition().x, (int) end.getPosition().y);
        resultPath.clear();

        //path finding. resultPath has array with nodes of path
        pathFinder.searchNodePath(startNode, endNode, heuristic, resultPath);
        resultPath.coordinates.reverse(); //refactor this

        if(startNode.getIndex() != endNode.getIndex()){
            //path building
            LinePath<Vector2> linePath = new LinePath<Vector2>(resultPath.coordinates, true);
            SteerableEntity steerableEntity = new SteerableEntity(start);

            //setting path for steering for entity
            //pathOffset is a must for steering
            FollowPath<Vector2, LinePath.LinePathParam> followPath = new FollowPath<Vector2, LinePath.LinePathParam>(steerableEntity, linePath, 1)
                    .setPredictionTime(0.0f).setArrivalTolerance(0.0f);
            steerableEntity.setSteeringBehavior(followPath);
            steerableEntity.applySteering();
        }

        //debug render
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(int i =0; i < resultPath.coordinates.size - 1; i++){
            shapeRenderer.line(resultPath.coordinates.get(i).x, resultPath.coordinates.get(i).y, resultPath.coordinates.get(i+1).x, resultPath.coordinates.get(i+1).y);
        }

        shapeRenderer.end();
    }

}
