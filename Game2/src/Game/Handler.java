package Game;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> object  = new LinkedList<GameObject>();

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            GameObject tmpObject = object.get(i);
            tmpObject.tick();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tmpObject = object.get(i);
            tmpObject.render(g);
        }
    }

    public void addObject(GameObject o) {
        this.object.add(o);
    }

    public void removeObject(GameObject o) {
        this.object.remove(o);
    }
}
