package miniengine.Input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import miniengine.Core.Game;
import miniengine.Math.Vector2;

import java.util.HashSet;
import java.util.Set;

public class Input {

    // --- ESTADO DO HARDWARE ---
    private static final Set<KeyCode> currentKeys = new HashSet<>();
    private static final Set<KeyCode> downKeysFrame = new HashSet<>();
    private static final Set<KeyCode> upKeysFrame = new HashSet<>();

    private static final Set<MouseButton> currentMouseButtons = new HashSet<>();
    private static final Set<MouseButton> downMouseButtonsFrame = new HashSet<>();
    private static final Set<MouseButton> upMouseButtonsFrame = new HashSet<>();

    public static Vector2 mousePosition = Vector2.zero();

    // --- MÉTODOS DE CONSULTA (API Pura) ---

    /**
     * Retorna true enquanto a tecla estiver sendo segurada.
     */
    public static boolean getKey(KeyCode key) {
        return currentKeys.contains(key);
    }

    /**
     * Retorna true APENAS no frame que a tecla foi pressionada.
     */
    public static boolean getKeyDown(KeyCode key) {
        return downKeysFrame.contains(key);
    }

    /**
     * Retorna true APENAS no frame que a tecla foi solta.
     */
    public static boolean getKeyUp(KeyCode key) {
        return upKeysFrame.contains(key);
    }

    // --- MOUSE ---
    public static boolean getMouseButton(int buttonID) {
        return currentMouseButtons.contains(getButtonFromID(buttonID));
    }

    public static boolean getMouseButtonDown(int buttonID) {
        return downMouseButtonsFrame.contains(getButtonFromID(buttonID));
    }

    public static boolean getMouseButtonUp(int buttonID) {
        return upMouseButtonsFrame.contains(getButtonFromID(buttonID));
    }

    // Utilitário interno para converter int (0,1,2) em Enum do JavaFX
    private static MouseButton getButtonFromID(int id) {
        switch (id) {
            case 0: return MouseButton.PRIMARY;   // Esquerdo
            case 1: return MouseButton.MIDDLE;    // Meio
            case 2: return MouseButton.SECONDARY; // Direito
            default: return MouseButton.PRIMARY;
        }
    }

    // --- MÉTODOS INTERNOS DA ENGINE ---

    public static void _onKeyPressed(KeyCode k) {
        if (!currentKeys.contains(k)) {
            currentKeys.add(k);
            downKeysFrame.add(k);
        }
    }

    public static void _onKeyReleased(KeyCode k) {
        currentKeys.remove(k);
        upKeysFrame.add(k);
    }

    public static void _onMousePressed(MouseButton b) {
        if (!currentMouseButtons.contains(b)) {
            currentMouseButtons.add(b);
            downMouseButtonsFrame.add(b);
        }
    }

    public static void _onMouseReleased(MouseButton b) {
        currentMouseButtons.remove(b);
        upMouseButtonsFrame.add(b);
    }

    // Se a escala for 0 (erro de config), evita divisão por zero
    public static void _onMouseMoved(double screenX, double screenY) {
        double scale = Game.getInstance().getScale();
        if (scale != 0) {
            mousePosition.x = screenX / scale;
            mousePosition.y = screenY / scale;
        }
    }

    public static void _endFrame() {
        downKeysFrame.clear();
        upKeysFrame.clear();
        downMouseButtonsFrame.clear();
        upMouseButtonsFrame.clear();
    }
}