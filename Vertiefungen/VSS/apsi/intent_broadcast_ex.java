Intent resultIntent = new Intent();
String action = "ch.fhnw.android.battery.BATTERY_SMS";
resultIntent.setAction(action);
resultIntent.putExtra("Sending Activity", "Battery Check");
resultIntent.putExtra("Battery Info", batteryRcv.batteryInfo);
sendBroadcast(resultIntent);