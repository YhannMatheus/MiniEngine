package miniengine;
import javafx.scene.canvas.GraphicsContext;
import miniengine.components.Transform;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public static int instances;
    public String name;
    public boolean isActivated = true;
    public boolean isDestroyed = false;

    private final List<GameComponent> components = new ArrayList<>();
    public Transform transform;

    public GameObject() {
        instances++;
        this.name = "GameObject_"+instances;
        this.transform = new Transform();

        addComponent(this.transform);

        awake();
    }

    // --- MÉTODOS DO PROGRAMADOR ---

    public void awake(){}

    public void initialize() {}

    public void update() {}

    public void dispose() {}

    // --- GERENCIAMENTO DE COMPONENTES ---

    public void addComponent(GameComponent component){
        if(component instanceof Transform && component != this.transform){
            System.err.println(String.format("Aviso [Objeto %s ] : Já contem um componente Transform", name));
            return;
        }

        component.gameObject = this;
        components.add(component);
        component.start();
    }

    public void removeComponent(GameComponent component){
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

    // --- MÉTODOS DA ENGINE ---
    public final void runUpdate() {
        if (!isActivated) return;
        for (GameComponent c : components) {
            c.update();
        }

        this.update();
    }

    public final void runDraw(GraphicsContext gc) {
        if (!isActivated) return;

        for (GameComponent c : components) {
            c.draw(gc);
        }
    }
}
