package com.example.a3dprint.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
import java.time.LocalDateTime

/**
 * Worker pre spracovanie notifikácie na pozadí, ktorá upozorňuje na zákazku.
 *
 * Tento worker je zodpovedný za vytvorenie a odoslanie notifikácie používateľovi, ktorá
 * obsahuje informácie o konkrétnej zákazke. Notifikácia je odoslaná v prípade, že uplynul čas
 * určený pre zákazku. Worker sa spúšťa cez WorkManager.
 * Pri tvorbe som si pomahal s chatGBT a https://vtsen.hashnode.dev/simple-example-to-use-workmanager-and-notification
 *
 * @param appContext Kontext aplikácie.
 * @param workerParams Parametre pre spustenie tohto workeru.
 */
class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    /**
     * Hlavná funkcia workeru, ktorá vykonáva prácu na pozadí.
     *
     * V tejto funkcii sa získava popis zákazky z dátového vstupu a následne sa odosiela
     * notifikácia používateľovi o tejto zákazke.
     */
    override fun doWork(): Result {
        val zakazkaName = inputData.getString("zakazka_popis") ?: applicationContext.getString(R.string.neznamaZak)
        sendNotification(zakazkaName)
        return Result.success()
    }

    /**
     * Odosiela notifikáciu o zákazke.
     *
     * Tento spôsob vytvára notifikáciu s názvom zákazky a odosiela ju používateľovi,
     * ak je splnený podmienok.
     *
     * @param zakazkaName Názov zákazky, ktorý sa zobrazí v notifikácii.
     */
    private fun sendNotification(zakazkaName: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "zakazka_channel"
        val channel = NotificationChannel(
            channelId,
            applicationContext.getString(R.string.notifikacia_zakazka),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(applicationContext.getString(R.string.pripomienka))
            .setContentText("${applicationContext.getString(R.string.dnes_zakazka)} $zakazkaName")
            .setSmallIcon(R.drawable.ic_launcher)
            .build()

        notificationManager.notify(1, notification)
    }
}

/**
 * Plánuje notifikáciu na určitý dátum a čas pre konkrétnu zákazku.
 *
 * Funkcia vypočíta oneskorenie medzi aktuálnym časom a určeným časom pre notifikáciu,
 * a potom naplánuje spustenie workeru.
 *
 * @param context Kontext aplikácie.
 * @param zakazkaName Názov zákazky, ktorý sa zobrazí v notifikácii.
 * @param date Dátum zákazky vo formáte "dd.MM.yyyy".
 */
fun scheduleNotification(context: Context, zakazkaName: String, date: String) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val localDate = LocalDate.parse(date, formatter)

    val now = LocalDateTime.now()
    val targetDateTime = localDate.atTime(8, 0)

    val delayMillis = Duration.between(now, targetDateTime).toMillis()
   // val delayMillis = 10 * 1000L // 10 sekund
    if (delayMillis > 0) {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("zakazka_popis" to zakazkaName))
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}