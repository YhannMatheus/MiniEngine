package miniengine;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public String name;
    private final List<GameComponent> components = new ArrayList<>();
    public boolean isActivated = true;
    public boolean isDestroyed = false;


    // --- MÉTODOS DO PROGRAMADOR ---

    // Chamado 1x antes de nascer
    public void born(){}

    // Chamado 1x ao nascer
    public void initialize() {}

    // Chamado a cada frame (Lógica customizada do objeto)
    public void update() {}

    // Chamado ao morrer/sair (Sugestão de nome novo)
    public void dispose() {}



    // --- GERENCIAMENTO DE COMPONENTES ---

    public void addComponent(GameComponent component){
        component.gameObject = this;

        this.components.add(component);
        component.start(); // Já inicia o componente assim que adiciona
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

    // --- MÉTODOS DA ENGINE (Engine Land) ---
    public final void runUpdate() {
        if (!isActivated) return;

        // 1. Roda a lógica dos componentes (Scripts, Física, etc)
        for (GameComponent c : components) {
            c.update();
        }

        // 2. Roda a lógica do programador
        this.update();
    }

    public final void runDraw(GraphicsContext gc) {
        if (!isActivated) return;

        // Desenha todos os componentes visuais
        for (GameComponent c : components) {
            c.draw(gc);
        }
    }
}
