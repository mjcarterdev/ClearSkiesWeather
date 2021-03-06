package fi.carterm.clearskiesweather.services.background

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import fi.carterm.clearskiesweather.R
import fi.carterm.clearskiesweather.activities.MainActivity

/**
 *
 * Sensor Service. Creates a service that will monitor and report sensor readings from the phone.
 *
 * @author Michael Carter
 * @version 1
 *
 */

class SensorService: Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var light = 0f
    private var temp = 0f
    private var press = 0f
    private var humid = 0f

    private var background = false
    private val notificationActivityRequestCode = 0
    private val notificationId = 1
    private val notificationStopRequestCode = 2

    companion object{
        const val KEY_LIGHT = "light"
        const val KEY_TEMP = "temperature"
        const val KEY_PRESSURE = "pressure"
        const val KEY_HUMIDITY = "humidity"
        const val KEY_BACKGROUND = "background"
        const val KEY_NOTIFICATION_ID = "notificationId"
        const val KEY_ON_SENSOR_CHANGED_ACTION = "fi.carterm.clearskiesweather.ON_SENSOR_CHANGED"
        const val KEY_NOTIFICATION_STOP_ACTION = "fi.carterm.clearskiesweather.NOTIFICATION_STOP"

    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)?.also { lightSensor ->
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.also { tempSensor ->
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)?.also { pressureSensor ->
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)?.also { humiditySensor ->
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

        val notification = createNotification()
        startForeground(notificationId, notification)

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // Removes notification if service is destroyed
    override fun onDestroy() {
        super.onDestroy()
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            background = it.getBooleanExtra(KEY_BACKGROUND, false)
        }

        return START_STICKY
    }

    /**
     * When a sensor reading changes, the new reading is compared to the previous reading if
     * a large enough change occurs will update the view via the broadcast receiver.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        when(event?.sensor?.type){
            Sensor.TYPE_LIGHT -> {
                if(checkLightReading(event.values[0])){
                    light = event.values[0]
                    updateSensorReadings(KEY_LIGHT)
                }
            }
            Sensor.TYPE_AMBIENT_TEMPERATURE ->{
                if(checkTempReading(event.values[0])){
                    temp = event.values[0]
                    updateSensorReadings(KEY_TEMP)
                }
            }
            Sensor.TYPE_PRESSURE ->{
                if(checkPressureReading(event.values[0])){
                    press = event.values[0]
                    updateSensorReadings(KEY_PRESSURE)
                }
            }
            Sensor.TYPE_RELATIVE_HUMIDITY ->{
                if(checkHumidityReading(event.values[0])){
                    humid = event.values[0]
                    updateSensorReadings(KEY_HUMIDITY)
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // Compares previous reading with new reading
    private fun checkLightReading(newLight: Float): Boolean {
        val minRange = light - 1
        val maxRange = light + 1
        return newLight !in minRange..maxRange
    }
    // Compares previous reading with new reading
    private fun checkTempReading(newTemp: Float): Boolean {
        val minRange = temp - 1
        val maxRange = temp + 1
        return newTemp !in minRange..maxRange
    }
    // Compares previous reading with new reading
    private fun checkPressureReading(newPressure: Float): Boolean {
        val minRange = press - 1
        val maxRange = press + 1
        return newPressure !in minRange..maxRange
    }
    // Compares previous reading with new reading
    private fun checkHumidityReading(newHumidity: Float): Boolean {
        val minRange = humid - 1
        val maxRange = humid + 1
        return newHumidity !in minRange..maxRange
    }
    // Creates a broadcast intent with new sensor readings
    private fun updateSensorReadings(type: String) {
        val intent = Intent()
        when(type){
            KEY_LIGHT -> {
                intent.putExtra(KEY_LIGHT, light)
            }
            KEY_PRESSURE -> {
                intent.putExtra(KEY_PRESSURE, press)
            }
            KEY_HUMIDITY -> {
                intent.putExtra(KEY_HUMIDITY, humid)
            }
            KEY_TEMP -> {
                intent.putExtra(KEY_TEMP, temp)
            }
        }
        intent.action = KEY_ON_SENSOR_CHANGED_ACTION
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
    }

    private fun createNotification(): Notification {

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(
            application.packageName,
            "Notifications", NotificationManager.IMPORTANCE_DEFAULT
        )

        // Configure the notification channel.
        notificationChannel.enableLights(false)
        notificationChannel.setSound(null, null)
        notificationChannel.enableVibration(false)
        notificationChannel.vibrationPattern = null
        notificationChannel.setShowBadge(false)
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder = NotificationCompat.Builder(baseContext, application.packageName)
        // Open activity intent
        val contentIntent = PendingIntent.getActivity(
            this, notificationActivityRequestCode,
            Intent(this, MainActivity::class.java),PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        // Stop notification intent
        val stopNotificationIntent = Intent(this, ActionListener::class.java)
        stopNotificationIntent.action = KEY_NOTIFICATION_STOP_ACTION
        stopNotificationIntent.putExtra(KEY_NOTIFICATION_ID, notificationId)
        val pendingStopNotificationIntent =
            PendingIntent.getBroadcast(this, notificationStopRequestCode, stopNotificationIntent,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText("Phone Sensors Running")
            .setWhen(System.currentTimeMillis())
            .setDefaults(0)
            .setVibrate(longArrayOf(0L))
            .setSound(null)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(contentIntent)
            .addAction(R.mipmap.ic_launcher_round, "Stop Sensors", pendingStopNotificationIntent)


        return notificationBuilder.build()
    }

    class ActionListener : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent != null && intent.action != null) {
                if (intent.action.equals(KEY_NOTIFICATION_STOP_ACTION)) {
                    context?.let {
                        val notificationManager =
                            it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        val sensorIntent = Intent(it, SensorService::class.java)
                        it.stopService(sensorIntent)
                        val notificationId = intent.getIntExtra(KEY_NOTIFICATION_ID, -1)
                        if (notificationId != -1) {
                            notificationManager.cancel(notificationId)
                        }
                    }
                }
            }
        }
    }
}

