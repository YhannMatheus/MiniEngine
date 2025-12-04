package miniengine.Components.UIcollection;

import miniengine.Core.Game;
import miniengine.Math.Vector2;
import miniengine.Structure.GameComponent;

public class InterfaceComponent extends GameComponent {
    public UIAnchor anchor = UIAnchor.CENTER;
    public Vector2 anchorOffset = new Vector2();

    @Override
    public void update(){
        updatePositionBasedOnAnchor();

        onUpdateUI();
    }
    protected void onUpdateUI(){}

    private void updatePositionBasedOnAnchor(){
        if(gameObject == null) return;

        double screenX = Game.getInstance().getWidth();
        double screenY = Game.getInstance().getHeight();

        double anchorX = 0;
        double anchorY = 0;

        switch (anchor){
            case TOP_LEFT:      anchorX = 0;           anchorY = 0;           break;
            case TOP_CENTER:    anchorX = screenX / 2; anchorY = 0;           break;
            case TOP_RIGHT:     anchorX = screenX;     anchorY = 0;           break;

            case CENTER_LEFT:   anchorX = 0;           anchorY = screenY / 2; break;
            case CENTER:        anchorX = screenX / 2; anchorY = screenY / 2; break;
            case CENTER_RIGHT:  anchorX = screenX;     anchorY = screenY / 2; break;

            case BOTTOM_LEFT:   anchorX = 0;           anchorY = screenY;     break;
            case BOTTOM_CENTER: anchorX = screenX / 2; anchorY = screenY;     break;
            case BOTTOM_RIGHT:  anchorX = screenX;     anchorY = screenY;     break;
        }

        double finalX = anchorX + anchorOffset.x;
        double finalY = anchorY + anchorOffset.y;

        gameObject.transform.position.x = finalX;
        gameObject.transform.position.y = finalY;
    }
}
