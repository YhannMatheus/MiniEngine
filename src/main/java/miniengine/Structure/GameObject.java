package miniengine.Structure;

import javafx.scene.canvas.GraphicsContext;
import miniengine.Graphics.Painter;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public static int instances = 0;
    public String name;
    public boolean isActivated = true;
    public boolean isDestroyed = false;

    private final List<GameComponent> components = new ArrayList<>();
    public Transform transform;

    public GameObject() {
        instances++;
        this.name = "GameObject_" + instances;
        this.transform = new Transform();
        awake();
    }

    public GameObject(String name) {
        this.name = name;
        this.transform = new Transform();
        awake();
    }

    public void awake() {}

    public void initialize() {}

    public void update() {}

    public void dispose() {}

    public void addComponent(GameComponent component) {
        component.gameObject = this;
        components.add(component);
        component.start();
    }

    public void removeComponent(GameComponent component) {
        component.gameObject = null;
        this.components.remove(component);
    }

    public <T extends GameComponent> T getComponent(Class<T> componentClass) {
        for (GameComponent c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }
        return null;
    }

    public final void runOnCollision(GameObject other) {
        if (!isActivated) return;
        for (GameComponent c : components) {
            c.onCollision(other);
        }
    }

    public final void runUpdate() {
        if (!isActivated) return;

        for (int i = 0; i < components.size(); i++) {
            components.get(i).update();
        }

        this.update();
    }

    public final void runDraw(Painter p) {
        if (!isActivated) return;

        for (int i = 0; i < components.size(); i++) {
            components.get(i).draw(p);
        }
    }
}
