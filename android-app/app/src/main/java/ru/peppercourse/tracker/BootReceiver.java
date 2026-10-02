package ru.peppercourse.tracker;
import android.content.*;
public class BootReceiver extends BroadcastReceiver {@Override public void onReceive(Context c,Intent i){ReminderScheduler.schedule(c,c.getSharedPreferences("course",Context.MODE_PRIVATE).getString("state","{}"));}}
