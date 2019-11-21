package pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import systems.LevelManager;

public class HeuristicImp implements Heuristic<Node>{

    @Override
    public float estimate(Node startNode, Node endNode) {

        float startY = startNode.y;
        float startX = startNode.x;
        float endY = endNode.y;
        float endX = endNode.x;

        float distance = Math.abs(startX - endX) + Math.abs(startY - endY);

        return distance;
    }
}
