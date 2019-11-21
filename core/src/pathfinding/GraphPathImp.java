package pathfinding;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class GraphPathImp implements GraphPath<Node> {
    public Array<Node> nodes = new Array<Node>();
    public Array<Vector2> coordinates = new Array<Vector2>();

    public GraphPathImp(){

    }

    @Override
    public int getCount() {
        return nodes.size;
    }

    @Override
    public void reverse() {
        nodes.reverse();
    }

    @Override
    public void clear() {
        nodes.clear();
        coordinates.clear();
    }

    @Override
    public void add(Node node) {
        nodes.add(node);
        coordinates.add(node.getCoordinates());
    }

    @Override
    public Node get(int index) {
        return nodes.get(index);
    }

    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

}
