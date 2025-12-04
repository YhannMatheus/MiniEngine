package miniengine.Components;

import miniengine.Core.Time;
import miniengine.Math.Vector2;
import miniengine.Physics.Physics;
import miniengine.Structure.GameComponent;

public class PhysicalBody  extends GameComponent {

    public Vector2 velocity = new Vector2();
    private Vector2 acceleration = new Vector2();
    public double mass = 0;
    public double drag = 0;

    public boolean useGravity = false;
    public boolean isGrounded = false;

    @Override
    public void update() {
        double dt = Time.deltaTime;
        calculateGravityVector();

        integrateVelocity(dt);
        applyInertiaToTransform(dt);
        resetFrameForces();
    }

    @Override
    public void onPhysicsResolved(Vector2 correction) {
        // Eixo Y (Teto/Chão)
        if (Math.abs(correction.y) > 0.001) {
            velocity.y = 0;
        }

        // Eixo X (Paredes)
        if (Math.abs(correction.x) > 0.001) {
            velocity.x = 0;
        }
    }

    public void addForce(Vector2 force) {
        if(mass == 0 ) return;

        acceleration.x += force.x/mass;
        acceleration.y += force.y/mass;
    }

    public void stopMomentum(){
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 0;
    }

    //___ Metodos internos ___
    private void calculateGravityVector(){
        if(!useGravity) return;

        acceleration.x += Physics.gravity.x;
        acceleration.y += Physics.gravity.y;
    }

    private void integrateVelocity(double dt){
        velocity.x += acceleration.x * dt;
        velocity.y += acceleration.y * dt;

        if(drag > 0){
            double dragFactor = 1 - (drag / dt);
            dragFactor = Math.max(0, dragFactor);

            velocity.x *= dragFactor;
            velocity.y *= dragFactor;
        }
    }

    private void applyInertiaToTransform(double dt){
        if (Math.abs(velocity.x) < 0.001) velocity.x = 0;
        if (Math.abs(velocity.y) < 0.001) velocity.y = 0;

        if (velocity.x != 0 || velocity.y != 0) {
            gameObject.transform.translate(new Vector2(velocity.x * dt, velocity.y * dt));
        }
    }

    private void resetFrameForces() {
        acceleration.x = 0;
        acceleration.y = 0;
    }
}
