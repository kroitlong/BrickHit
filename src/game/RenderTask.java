package game;
public class RenderTask implements Runnable {
    private Thread thread;
    private boolean exitd = false;
    private int interval;
    public BricksGame game;

    public RenderTask(BricksGame game) {
        this.game = game;
        interval = 1000 / game.getFps();
    }

    @Override
    public void run() {
        while (!exitd) {
            game.repaint();//刷新屏幕
            try {
                Thread.sleep(interval);//表示每帧后刷新屏幕
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        game.exit();
    }

    public void start(){
        if (thread==null) {
            thread = new Thread(this, "[RenderThread]");
            thread.start();
        }
    }

    public void setExitd(boolean exitd) {
        this.exitd = exitd;
    }

    public boolean getExitd(){
        return this.exitd;
    }
}
