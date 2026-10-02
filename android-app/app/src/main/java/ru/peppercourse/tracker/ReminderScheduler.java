package ru.peppercourse.tracker;
import android.app.*;import android.content.*;import org.json.*;import java.time.*;
final class ReminderScheduler {
 static PendingIntent pending(Context c,int day){return PendingIntent.getBroadcast(c,day,new Intent(c,ReminderReceiver.class).putExtra("day",day),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);}
 static boolean completed(JSONObject s,int day){JSONArray a=s.optJSONArray("completed");if(a!=null)for(int i=0;i<a.length();i++)if(a.optInt(i)==day)return true;return false;}
 static void schedule(Context c,String json){try{AlarmManager alarms=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);JSONObject s=new JSONObject(json);for(int day=1;day<=30;day++){alarms.cancel(pending(c,day));if(completed(s,day)||!s.optBoolean("notifications"))nm.cancel(day);}if(!s.optBoolean("notifications"))return;
 LocalDate start=LocalDate.parse(s.getString("startDate"));LocalTime time=LocalTime.parse(s.optString("time","09:00"));LocalDate today=LocalDate.now();long now=System.currentTimeMillis();long snooze=s.optLong("snoozeUntil",0);
 for(int day=1;day<=30;day++){LocalDate date=start.plusDays(day-1);if(completed(s,day)||date.isBefore(today))continue;long when=date.atTime(time).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();if(date.equals(today)&&snooze>now)when=snooze;String marker=start+":"+day+":"+when;if(c.getSharedPreferences("course",Context.MODE_PRIVATE).getBoolean(marker,false))continue;if(when<=now)continue;alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,when,pending(c,day));}
 }catch(Exception ignored){}}
}
