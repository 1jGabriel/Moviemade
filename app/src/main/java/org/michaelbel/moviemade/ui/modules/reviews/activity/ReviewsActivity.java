package org.michaelbel.moviemade.ui.modules.reviews.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.reviews.fragment.ReviewsFragment;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsActivity extends BaseActivity {

    public Movie movie;
    private ReviewsFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        toolbarTitle.setText(R.string.reviews);
        toolbarSubtitle.setText(movie.getTitle());

        fragment = (ReviewsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.loadReviews(movie.getId());
        }
    }
}