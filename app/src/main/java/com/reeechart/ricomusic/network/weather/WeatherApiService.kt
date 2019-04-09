package com.reeechart.ricomusic.network.weather

import com.reeechart.ricomusic.models.WeatherResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Reeechart on 06-Apr-19.
 */
interface WeatherApiService {
    @GET("data/2.5/weather")
    fun getWeather(@Query("lat") latitude: Double,
                   @Query("lon") longitude: Double,
                   @Query("appid") appId: String = APP_ID):
            Observable<WeatherResponse.Result>

    companion object {
        private val BASE_URL: String = "https://api.openweathermap.org/"
        const val APP_ID: String = "240a82b19382a07d9db628295c24a9f7"

        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}