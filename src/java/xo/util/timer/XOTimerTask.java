package xo.util.timer;

public interface XOTimerTask {
    void startFunction();
    void periodFunction();
    boolean finishedCondition();
}
