package orth.main;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PixelPerfectViewPort extends FitViewport {

    PixelPerfectViewPort(float worldWidth, float worldHeight, Camera camera){
        super(worldWidth, worldHeight, camera);
    }

    @Override
    public void update(int screenWidth, int screenHeight, boolean centerCamera) {

        float wRate = screenWidth / getWorldWidth();
        float hRate = screenHeight / getWorldHeight();
        float rate = Math.min(wRate, hRate); //px per meter
        int correctedRate = (int) (rate / 16) * 16;

        int viewportWidth = (int) getWorldWidth() * correctedRate;
        int viewportHeight = (int) getWorldHeight() * correctedRate;

        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);

        apply(centerCamera);
    }
}


