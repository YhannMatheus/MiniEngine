package miniengine.Structure;


import miniengine.Graphics.Painter;
import miniengine.Math.Vector2;

public abstract class GameComponent {

    public GameObject gameObject;

    /**
     * Chamado apenas uma vez quando o objeto entra no jogo.
     */
    public void start() {}

    /**
     * Chamado a cada frame (Lógica).
     */
    public void update() {}

    /**
     * Chamado a cada frame (Desenho).
     */
    public void draw(Painter p) {}

    /**
     * Chamado quando o objeto é destruído (limpeza).
     */
    public void destroy() {}

    /**
     * Chamado quando o Physics detecta colisão.
     */
    public void onCollision(GameObject other) {}

    /**
     * Avisa o componente que o objeto foi empurrado pela física
     */
    public void onPhysicsResolved(Vector2 correction) {}

}
