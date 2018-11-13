package org.michaelbel.moviemade.modules_beta.person;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.modules_beta.person.PersonMvp;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.Person;
import org.michaelbel.moviemade.utils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PersonPresenter extends MvpPresenter<PersonMvp> {

    private final CompositeDisposable disposables = new CompositeDisposable();

    public void loadPerson(int personId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        PEOPLE service = ApiFactory.createService2(PEOPLE.class);
        Observable<Person> observable = service.getDetails(personId, BuildConfig.TMDB_API_KEY, Url.en_US, null).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        disposables.add(observable.subscribeWith(new DisposableObserver<Person>() {
            @Override
            public void onNext(Person person) {
                getViewState().showPerson(person);
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }

            @Override
            public void onComplete() {}
        }));
    }

    @Override
    public void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}