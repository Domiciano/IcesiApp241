package icesi.edu.co.icesiapp241.retrofit

import icesi.edu.co.icesiapp241.domain.model.Message
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


object RetrofitConfiguration {

    private val fcmRetrofit = Retrofit.Builder()
        .baseUrl("https://cf31-200-3-193-77.ngrok-free.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val messagingService: MessagingService = fcmRetrofit.create(MessagingService::class.java)
}

interface MessagingService {
    @POST("fcm/send")
    suspend fun sendMessage(@Body fcmbody: FCMBody): FCMResponse
}

data class FCMBody(var message: FCMMessage? = null)
data class FCMMessage(var topic: String = "", var data: FCMData? = null)
data class FCMData(var subject: String = "", var content:String?="")
data class FCMResponse(var name:String = "")