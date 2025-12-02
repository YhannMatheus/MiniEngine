package miniengine;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameObject {

    public String name;
    private final List<GameComponent> components = new ArrayList<>();
    public boolean isActivated = true;
    public boolean isDestroyed = false;


    // --- MÉTODOS DO PROGRAMADOR (User Land) ---

    // Chamado 1x ao nascer
    public void initialize() {}

    // Chamado a cada frame (Lógica customizada do objeto)
    public void update() {}

    // Chamado ao morrer/sair (Sugestão de nome novo)
    public void dispose() {}

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

    // --- GERENCIAMENTO DE COMPONENTES ---

    public void addComponent(GameComponent component){
        // CRÍTICO: Avisar o componente que ele pertence a este objeto
        component.gameObject = this;

        this.components.add(component);
        component.start(); // Já inicia o componente assim que adiciona
    }

    public void removeComponent(GameComponent component){
        component.gameObject = null;
        this.components.remove(component);
    }

    // CRÍTICO: Método para os componentes conversarem entre si
    public <T extends GameComponent> T getComponent(Class<T> componentClass) {
        for (GameComponent c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                return componentClass.cast(c);
            }
        }
        return null;
    }

    public List<GameComponent> getComponents(){
        return this.components;
    }
}
