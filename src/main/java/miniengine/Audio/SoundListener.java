package miniengine.Audio;

import miniengine.Core.Game;
import miniengine.Structure.GameComponent;

public class SoundListener extends GameComponent {
    @Override
    public void start(){
        Game.getInstance().setListener(this);
    }
}
