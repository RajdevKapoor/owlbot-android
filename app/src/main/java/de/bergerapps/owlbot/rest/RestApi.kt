package de.bergerapps.owlbot.rest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RestAPI {

    private val owlBotService: OwlBotService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://owlbot.info/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        owlBotService = retrofit.create(OwlBotService::class.java)
    }

    fun getDictionary(
        data: MutableLiveData<OwlBotResponse>,
        dictionary: String
    ) {
        doAsync {

            owlBotService.getDictionary(dictionary)
                .enqueue(object : Callback<OwlBotResponse> {
                    override fun onFailure(call: Call<OwlBotResponse>?, t: Throwable?) {
                        Log.v("retrofit", "call failed")
                    }

                    override fun onResponse(
                        call: Call<OwlBotResponse>?,
                        response: Response<OwlBotResponse>?
                    ) {
                        if (response?.body() != null)
                            data.value = response!!.body()!!
                    }

                })
        }
    }
}