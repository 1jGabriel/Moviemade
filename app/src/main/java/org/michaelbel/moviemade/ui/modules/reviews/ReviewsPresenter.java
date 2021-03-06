package org.michaelbel.moviemade.ui.modules.reviews;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.data.dao.ReviewsResponse;
import org.michaelbel.moviemade.data.service.MOVIES;
import org.michaelbel.moviemade.utils.ApiFactory;
import org.michaelbel.moviemade.utils.TmdbConfigKt;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class ReviewsPresenter extends MvpPresenter<ReviewsMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadReviews(int movieId) {
        if (NetworkUtil.INSTANCE.notConnected()) {
            getViewState().setError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        MOVIES service = ApiFactory.createService2(MOVIES.class);
        Observable<ReviewsResponse> observable = service.getReviews(movieId, BuildConfig.TMDB_API_KEY, TmdbConfigKt.en_US, 1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<ReviewsResponse>() {
            @Override
            public void onNext(ReviewsResponse response) {
                List<Review> results = new ArrayList<>(response.getReviews());
                if (results.isEmpty()) {
                    getViewState().setError(EmptyViewMode.MODE_NO_REVIEWS);
                    return;
                }
                getViewState().setReviews(response.getReviews());
            }

            @Override
            public void onError(Throwable e) {
                getViewState().setError(EmptyViewMode.MODE_NO_REVIEWS);
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}