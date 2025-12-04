package miniengine.Structure;


import miniengine.Graphics.Painter;

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
     * @param p O Pintor da engine (Wrapper do GraphicsContext).
     */
    public void draw(Painter p) {}

    /**
     * Chamado quando o Physics detecta colisão.
     */
    public void onCollision(GameObject other) {}

    /**
     * Chamado quando o objeto é destruído (limpeza).
     */
    public void destroy() {}
}
