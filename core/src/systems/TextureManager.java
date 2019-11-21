package systems;

import characters.base.CreationType;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {

    private static volatile TextureManager instance;

    public static TextureManager getInstance() {
        if (instance == null)
            instance = new TextureManager();
        return instance;
    }

    private Map<CreationType, Texture> textures = new HashMap<CreationType, Texture>();

    private TextureManager() {
        // load all textures,
        // todo: change your custom path
        textures.put(CreationType.Brick, new Texture("core/assets/levels/basement/solid.png"));
        textures.put(CreationType.Water, new Texture("core/assets/levels/basement/solid.png"));

        textures.put(CreationType.Evilball, new Texture("core/assets/items/evilball.png"));
        textures.put(CreationType.Fireball, new Texture("core/assets/items/fireball.png"));

        textures.put(CreationType.EnemyDevil, new Texture("core/assets/creations/enemy.png"));
        textures.put(CreationType.Hero, new Texture("core/assets/creations/redguy.png"));
        textures.put(CreationType.Agent, new Texture("core/assets/creations/scanner.png"));

    }

    public Texture getTexture(CreationType type) {
        return textures.get(type);
    }

}
