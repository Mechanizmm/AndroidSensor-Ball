package pl.pp.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView xCordText;
    private TextView yCordText;

    Ball ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xCordText = (TextView) findViewById(R.id.xCord);
        yCordText = (TextView) findViewById(R.id.yCord);

        ball = (Ball) findViewById(R.id.ball);
        Thread t = new Thread(new BallThread(new Handler(), ball));
        t.start();
    }

    public void doToast(){
        if(ball.isGameOver())
        {
            Context context = getApplicationContext();
            CharSequence text = "Oops! Try Again";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            ball.resetGameState();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        ball.setVx((int) event.values[0]);
        ball.setVy((int) event.values[1]);

        doToast();

        xCordText.setText("X: "+Integer.toString(ball.getXpos()));
        yCordText.setText("Y: "+Integer.toString(ball.getYpos()));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}
