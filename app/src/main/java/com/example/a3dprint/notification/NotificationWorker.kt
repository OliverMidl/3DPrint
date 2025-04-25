package com.example.a3dprint.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import java.time.Duration
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.a3dprint.R

class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val zakazkaName = inputData.getString("zakazka_popis") ?: "Neznáma zakazka"

        sendNotification(zakazkaName)
        return Result.success()
    }

    private fun sendNotification(zakazkaName: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "zakazka_channel"
        val channel = NotificationChannel(
            channelId,
            "Zakazka Notifikácie",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Pripomienka")
            .setContentText("Dnes máš naplánovanú zákazku: $zakazkaName")
            .setSmallIcon(R.drawable.ic_launcher)
            .build()

        notificationManager.notify(1, notification)
    }


}

fun scheduleNotification(context: Context, zakazkaName: String, date: String) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(date, formatter)

    val delay = Duration.between(LocalDate.now().atStartOfDay(), localDate.atStartOfDay()).toMillis() + (8 * 60 * 60 * 1000L)
    //val delay = 10 * 1000L // 10 sekund
    if (delay > 0) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("zakazka_popis" to zakazkaName))
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}