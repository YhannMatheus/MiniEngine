package miniengine.components;

import miniengine.Game;
import miniengine.GameComponent;

public class SoundListener extends GameComponent {
    @Override
    public void start(){
        Game.getInstance().setListener(this);
    }
}
