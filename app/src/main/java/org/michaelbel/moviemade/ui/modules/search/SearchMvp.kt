package org.michaelbel.moviemade.ui.modules.search

import com.arellomobile.mvp.MvpView

import org.michaelbel.moviemade.utils.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Movie

interface SearchMvp : MvpView {

    fun searchStart()

    fun setMovies(movies: List<Movie>, firstPage: Boolean)

    fun setError(@EmptyViewMode mode: Int)
}