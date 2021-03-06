package org.michaelbel.moviemade.data.constants

import org.michaelbel.moviemade.data.dao.Genre
import java.util.*

const val GENRE_ACTION = 28
const val GENRE_ADVENTURE = 12
const val GENRE_ANIMATION = 16
const val GENRE_COMEDY = 35
const val GENRE_CRIME = 80
const val GENRE_DOCUMENTARY = 99
const val GENRE_DRAMA = 18
const val GENRE_FAMILY = 10751
const val GENRE_FANTASY = 14
const val GENRE_HISTORY = 36
const val GENRE_HORROR = 27
const val GENRE_MUSIC = 10402
const val GENRE_MYSTERY = 9648
const val GENRE_ROMANCE = 10749
const val GENRE_SCIENCE_FICTION = 878
const val GENRE_TV_MOVIE = 10770
const val GENRE_THRILLER = 53
const val GENRE_WAR = 10752
const val GENRE_WESTERN = 37

object Genres {

    private val genresList: List<Genre>
        get() {
            val genres = ArrayList<Genre>()
            genres.add(Genre(GENRE_ACTION, "Action"))
            genres.add(Genre(GENRE_ADVENTURE, "Adventure"))
            genres.add(Genre(GENRE_ANIMATION, "Animation"))
            genres.add(Genre(GENRE_COMEDY, "Comedy"))
            genres.add(Genre(GENRE_CRIME, "Crime"))
            genres.add(Genre(GENRE_DOCUMENTARY, "Documentary"))
            genres.add(Genre(GENRE_DRAMA, "Drama"))
            genres.add(Genre(GENRE_FAMILY, "Family"))
            genres.add(Genre(GENRE_FANTASY, "Fantasy"))
            genres.add(Genre(GENRE_HISTORY, "History"))
            genres.add(Genre(GENRE_HORROR, "Horror"))
            genres.add(Genre(GENRE_MUSIC, "Music"))
            genres.add(Genre(GENRE_MYSTERY, "Mystery"))
            genres.add(Genre(GENRE_ROMANCE, "Romance"))
            genres.add(Genre(GENRE_SCIENCE_FICTION, "Science Fiction"))
            genres.add(Genre(GENRE_TV_MOVIE, "TV Movie"))
            genres.add(Genre(GENRE_THRILLER, "Thriller"))
            genres.add(Genre(GENRE_WAR, "War"))
            genres.add(Genre(GENRE_WESTERN, "Western"))
            return genres
        }

    fun getGenreById(genreId: Int): Genre? {
        for (genre: Genre in genresList) {
            if (genre.id == genreId) {
                return genre
            }
        }

        return null
    }
}