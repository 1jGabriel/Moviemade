package org.michaelbel.moviemade.data.service

import io.reactivex.Observable
import org.michaelbel.moviemade.data.dao.Keyword
import org.michaelbel.moviemade.data.dao.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KEYWORDS {

    @GET("keyword/{keyword_id}?")
    fun getDetails(
        @Path("keyword_id") id: Int,
        @Query("api_key") apiKey: String
    ): Observable<Keyword>

    @GET("keyword/{keyword_id}/parts")
    fun getMovies(
        @Path("keyword_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("include_adult") adult: Boolean,
        @Query("page") page: Int
    ): Observable<MoviesResponse>
}