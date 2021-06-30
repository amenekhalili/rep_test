package ir.fararayaneh.erp.utils.work_manager_helper

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ir.fararayaneh.erp.services.SocketService

class SocketReceiverWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
       SocketService.intentToSocketService(context)
       context.startService(Intent(context, SocketService::class.java))
        Log.i("arash", "doWork: SocketReceiverWorker ")
        return Result.success()
    }
}