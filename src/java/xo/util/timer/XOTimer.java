package xo.util.timer;

public class XOTimer extends Thread{
    private long period;
    private XOTimerTask task;
    private boolean pause;

    public XOTimer(long period, XOTimerTask task){
        this.period = period;
        this.task = task;
    }

    @Override
    public void run() {
        while (!task.finishedCondition()){
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!pause)
                task.periodFunction();
        }
    }

    public synchronized void pauseTimer(){
        pause = true;
    }

    public synchronized void resumeTimer(){
        pause = false;
    }
}
