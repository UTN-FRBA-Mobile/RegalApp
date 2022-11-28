package com.utn.frba.mobile.regalapp.notifications
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationService {

    fun sendNotification(body: NotificationBody, onResult: (String?) -> Unit) {
        val notifiactionsAPI = NotificationHelper.getInstance().create(NotifiactionsAPI::class.java)

        notifiactionsAPI.sendNotification(body).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<String>, response: Response<String>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}