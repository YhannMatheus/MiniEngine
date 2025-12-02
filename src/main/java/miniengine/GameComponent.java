package miniengine;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameComponent {

    public GameObject gameObject;

    // --- CICLO DE VIDA ---

    /**
     * Chamado automaticamente logo após o componente ser adicionado ao GameObject.
     * Use isso para pegar referências de outros componentes (ex: pegar o Transform).
     */
    public void start() {
        // Opcional por padrão
    }

    /**
     * Chamado a cada frame pela Engine.
     * Coloque a lógica de comportamento aqui (movimento, IA, timer).
     */
    public void update() {
        // Opcional por padrão
    }

    /**
     * Chamado a cada frame de desenho.
     * Use apenas para desenhar na tela (Sprite, Partículas, Debug).
     */
    public void draw(GraphicsContext gc) {
        // Opcional: Scripts de lógica não precisam desenhar
    }

    /**
     * Chamado quando o componente ou o objeto é removido.
     * Use para limpar listeners ou parar sons.
     */
    public void destroy() {
        // Opcional
    }
}
