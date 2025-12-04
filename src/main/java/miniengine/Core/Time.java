package miniengine.Core;

public class Time {
    public static double time = 0;
    public static double deltaTime = 0;
    public static double timeScale = 1;

    public static double _unscaledDeltaTime = 0;
    private static long _lastFrameTime = 0;

    public static void update(long currentNanoTime){
        if(_lastFrameTime == 0){
            _lastFrameTime = currentNanoTime;
            return;
        }

        long diff = currentNanoTime - _lastFrameTime;
        _unscaledDeltaTime = diff / 1_000_000_000;

        deltaTime += _unscaledDeltaTime * timeScale;

        time += _unscaledDeltaTime;

        _lastFrameTime = currentNanoTime;
    }
}
