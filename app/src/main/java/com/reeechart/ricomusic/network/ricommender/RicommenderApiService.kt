package com.reeechart.ricomusic.network.ricommender

import com.reeechart.ricomusic.models.AuthResponse
import com.reeechart.ricomusic.models.Music
import com.reeechart.ricomusic.models.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by Reeechart on 06-Apr-19.
 */
interface RicommenderApiService {
    @GET("/music/recommendation")
    fun getRecommendation(@Query("n") n: Int,
                          @Query("user") user: String,
                          @Query("loc") location: String,
                          @Query("weather") weather: String):
            Observable<List<Music>>

    @POST("/history/add")
    fun addHistory(@Field("user") user: String,
                   @Field("music") music: Int,
                   @Field("location") location: String,
                   @Field("weather") weather: String,
                   @Field("music_rank") musicRank: Int)

    @GET("/user/login/{username}")
    fun login(@Path(value="username", encoded=true) username: String): Observable<AuthResponse.Result>

    @POST("/user/register")
    fun register(@Body user: User): Observable<AuthResponse.Result>

    companion object {
        private val BASE_URL: String = "http://157.230.243.204"

        fun create(): RicommenderApiService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(RicommenderApiService::class.java)
        }
    }
}