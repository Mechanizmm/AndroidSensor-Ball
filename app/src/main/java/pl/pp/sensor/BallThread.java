package pl.pp.sensor;

import android.os.Handler;

class BallThread implements Runnable {
    private Handler handler;
    public Ball ball;

    public BallThread(Handler h, Ball b)
    {
        handler = h;
        ball = b;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ball.move();
                        ball.invalidate();
                    }
                });

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

