package xo.util.timer;

public class XODelayTimer extends Thread{
    private XODelayTask task;
    private long delay;

    public XODelayTimer(long delay, XODelayTask task) {
        this.delay = delay;
        this.task = task;
    }

    @Override
    public void run() throws NullPointerException {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.delayAction();
    }

    public void start(){
        super.start();
    }
}
