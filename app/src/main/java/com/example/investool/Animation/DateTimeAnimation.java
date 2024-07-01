package com.example.investool.Animation;

import android.content.Context;
import android.graphics.Color;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeAnimation {

    private static long DURATION = 35000;
    private static final String MARKET_OPEN_COLOR = "#00FF00";
    private static final String MARKET_CLOSED_COLOR = "#FF0000";
    private TextView textView;
    private Animation animation;
    private Context context;

    public DateTimeAnimation(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
        createAnimation();
    }

    private void createAnimation() {
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.3f,
                Animation.RELATIVE_TO_SELF, -1.5f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0
        );
        animation.setDuration(DURATION);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                updateDateTime();
            }
        });
    }

    public void updateDateTime() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss z", Locale.getDefault());
            Date currentDate = new Date();
            String currentDateTime = dateFormat.format(currentDate);

            TimeZone timeZone = TimeZone.getTimeZone("America/New_York");
            dateFormat.setTimeZone(timeZone);
            String timeZoneName = timeZone.getDisplayName(false, TimeZone.SHORT);

            boolean isMarketOpen = isForexMarketOpen(currentDate, timeZone);

            textView.setText(currentDateTime + " " + timeZoneName);
            textView.setTextColor(isMarketOpen ? Color.parseColor(MARKET_OPEN_COLOR) : Color.parseColor(MARKET_CLOSED_COLOR));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isForexMarketOpen(Date currentDate, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.setTimeZone(timeZone);

        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        return (currentDayOfWeek == Calendar.SUNDAY && currentHour >= 17) // Sunday after 5:00 pm ET
                || (currentDayOfWeek >= Calendar.MONDAY && currentDayOfWeek <= Calendar.THURSDAY) // Monday to Thursday
                || (currentDayOfWeek == Calendar.FRIDAY && currentHour < 17); // Friday before 5:00 pm ET
    }

    public void startAnimation() {
        textView.startAnimation(animation);
    }

    public void stopAnimation() {
        textView.clearAnimation();
    }
}
