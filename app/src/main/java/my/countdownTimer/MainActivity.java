package my.countdownTimer;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Vibrator;
import android.content.Context;

public class MainActivity extends Activity{
    private TextView _timeLeft;
    private Button _stop, _10minutes, _25minutes;
    private CountDownTimer _timer;

    private void startTimer(long time){
        _timer = new CountDownTimer(time, 1000){
            public void onTick(long millisLeft){
                _timeLeft.setText("Left: " +
                        String.format("%02d:%02d", millisLeft / 60000, (millisLeft / 1000) % 60));
            }
            public void onFinish(){
                ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(500);
                showCancelNotification(getApplicationContext());
                _timeLeft.setText(" ");
                showStarters();
            }
        };
        _timer.start();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)     {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         _timeLeft = (TextView) findViewById(R.id.tv);
         _stop = (Button) findViewById(R.id.stop);
         _10minutes = (Button) findViewById(R.id.meditation);
         _25minutes = (Button) findViewById(R.id.pomodoro);
    }

    public void start10minutes(View view){
        hideStarters();
        //startTimer(10 * 60000 + 999);
        startTimer(10*1000);
    }
    public void start25minutes(View view){
        hideStarters();
        startTimer(25 * 60000 + 999);
    }
    public void stop(View view){
        _timer.cancel();
        _timeLeft.setText(" ");
        showStarters();
    }
    private void showCancelNotification(Context context){
        int notifyId = 1;
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(notifyId, getNotification(context));
    }
    private Notification getNotification(Context context){
        Notification.Builder builder = new Notification.Builder(context);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context,
                0,
                new Intent(context,MainActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = context.getResources();
        return builder.setContentIntent(contentIntent)
                .setContentTitle(res.getString(R.string.notification_title))
                .setContentText(res.getString(R.string.notification_content))
                .setLargeIcon(BitmapFactory.decodeResource(res,R.drawable.ic_small))
                .setSmallIcon(R.drawable.ic_small)
                .build();

    }
    private void showStarters(){
        _stop.setVisibility(View.INVISIBLE);
        _10minutes.setVisibility(View.VISIBLE);
        _25minutes.setVisibility(View.VISIBLE);
    }
    private void hideStarters(){
        _stop.setVisibility(View.VISIBLE);
        _10minutes.setVisibility(View.INVISIBLE);
        _25minutes.setVisibility(View.INVISIBLE);
    }

}