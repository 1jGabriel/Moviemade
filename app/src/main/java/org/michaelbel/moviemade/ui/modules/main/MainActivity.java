package org.michaelbel.moviemade.ui.modules.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.log;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.fragments.NowPlayingFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.TopRatedFragment;
import org.michaelbel.moviemade.ui.modules.main.fragments.UpcomingFragment;
import org.michaelbel.moviemade.ui.modules.main.views.topbar.TopBar;
import org.michaelbel.moviemade.ui.modules.profile.ProfileFragment;
import org.michaelbel.moviemade.ui.modules.search.SearchActivity;
import org.michaelbel.moviemade.ui.modules.settings.SettingsActivity;
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationBar;
import org.michaelbel.moviemade.ui.widgets.bottombar.BottomNavigationItem;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static final int CURRENT_FRAGMENT_DEFAULT = 0;

    private NowPlayingFragment nowPlayingFragment;
    private TopRatedFragment topRatedFragment;
    private UpcomingFragment upcomingFragment;

    private ProfileFragment profileFragment;

    @Inject
    SharedPreferences sharedPreferences;

    @BindView(R.id.topbar) TopBar topbar;
    @BindView(R.id.search_icon) AppCompatImageView searchIcon;
    @BindView(R.id.settings_icon) AppCompatImageView settingsIcon;
    @BindView(R.id.bottom_navigation_bar) BottomNavigationBar bottomBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppThemeTransparentStatusBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Moviemade.getComponent().injest(this);

        topbar.setTitle(R.string.app_name);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) topbar.getLayoutParams();
        params.topMargin = DeviceUtil.INSTANCE.getStatusBarHeight(this);

        searchIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
        settingsIcon.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

        nowPlayingFragment = new NowPlayingFragment();
        topRatedFragment = new TopRatedFragment();
        upcomingFragment = new UpcomingFragment();

        profileFragment = new ProfileFragment();

        bottomBar.setBarBackgroundColor(R.color.primary);
        bottomBar.setActiveColor(R.color.md_white);
        bottomBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomBar
            .addItem(new BottomNavigationItem(R.drawable.ic_fire, R.string.now_playing).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_star_circle, R.string.top_rated).setActiveColorResource(R.color.accent))
            .addItem(new BottomNavigationItem(R.drawable.ic_movieroll, R.string.upcoming).setActiveColorResource(R.color.accent))
            //.addItem(new BottomNavigationItem(R.drawable.ic_account_circle, "Profile").setActiveColorResource(R.color.accent))
            .setFirstSelectedPosition(sharedPreferences.getInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, CURRENT_FRAGMENT_DEFAULT))
            .initialise();
        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        startFragment(nowPlayingFragment, R.id.fragment_view);
                        sharedPreferences.edit().putInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, position).apply();
                        break;
                    case 1:
                        startFragment(topRatedFragment, R.id.fragment_view);
                        sharedPreferences.edit().putInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, position).apply();
                        break;
                    case 2:
                        startFragment(upcomingFragment, R.id.fragment_view);
                        sharedPreferences.edit().putInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, position).apply();
                        break;
                    case 3:
                        startFragment(profileFragment, R.id.fragment_view);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {}

            @Override
            public void onTabReselected(int position) {
                switch (position) {
                    case 0:
                        if (nowPlayingFragment.getAdapter().getItemCount() == 0) {
                            nowPlayingFragment.presenter.loadNowPlayingMovies();
                        } else {
                            nowPlayingFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                    case 1:
                        if (topRatedFragment.getAdapter().getItemCount() == 0) {
                            topRatedFragment.presenter.loadNowPlayingMovies();
                        } else {
                            topRatedFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                    case 2:
                        if (upcomingFragment.getAdapter().getItemCount() == 0) {
                            upcomingFragment.presenter.loadNowPlayingMovies();
                        } else {
                            upcomingFragment.recyclerView.smoothScrollToPosition(0);
                        }
                        break;
                }
            }
        });

        if (savedInstanceState == null) {
            startCurrentFragment();
        }
    }

    private void startCurrentFragment() {
        int position = sharedPreferences.getInt(SharedPrefsKt.KEY_MAIN_FRAGMENT, CURRENT_FRAGMENT_DEFAULT);

        switch (position) {
            case 0:
                startFragment(nowPlayingFragment, R.id.fragment_view);
                break;
            case 1:
                startFragment(topRatedFragment, R.id.fragment_view);
                break;
            case 2:
                startFragment(upcomingFragment, R.id.fragment_view);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String data = intent.getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            log.e("DATA: " + data);
        }
    }
}