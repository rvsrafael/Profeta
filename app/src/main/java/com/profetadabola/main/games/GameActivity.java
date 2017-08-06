package com.profetadabola.main.games;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.profetadabola.Navigator;
import com.profetadabola.R;
import com.profetadabola.about.AboutActivity;
import com.profetadabola.api.API;
import com.profetadabola.api.APITools;
import com.profetadabola.api.model.GameResponse;
import com.profetadabola.api.model.EighthGamesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.recyclerview_games)
    RecyclerView recyclerviewGames;

//    @BindViews({ R.id.button_less_teamA, R.id.button_more_teamA, R.id.button_less_teamB, R.id.button_more_teamB })
//    Button buttonLessTeamA;

    private GameAdapter mAdapter;
    private API mService;
    private double latitude;
    private double longitude;
    private String titlePin;
    private Context context;
    private EighthGamesResponse games;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        loadingArguments(savedInstanceState);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        setupDrawer();
        setupNavigation();
        loadGames();
        setupGames();
        loadGames();
        setupAdapterGames();
    }

    private void setupAdapterGames() {
        mAdapter = new GameAdapter(new EighthGamesResponse(), new OnItemClickListener() {
            @Override
            public void onItemClick(GameResponse game) {
                latitude = game.getLatitude();
                longitude = game.getLongitude();
                titlePin = game.getStadium();
                Navigator.startMaps(context,latitude,longitude,titlePin);
            }
        });
    }

    private void setupGames() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerviewGames.setLayoutManager(layoutManager);
        recyclerviewGames.setAdapter(mAdapter);
        recyclerviewGames.setHasFixedSize(true);
    }

    public void loadGames() {
        mService = APITools.getAPI();
        mService.getAllGames()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EighthGamesResponse>() {
                    @Override
                    public void onCompleted() {
                        setupGames();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Ocorreu uma falha",
                                Toast.LENGTH_LONG).show();
                        Log.e("MAPS", e.getMessage());
                    }

                    @Override
                    public void onNext(EighthGamesResponse game) {
                        games = game;
                        mAdapter.update(game, false);
                    }
                });
    }

    private void loadingArguments(Bundle savedInstanceState) {
        String username;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                username= null;
            } else {
                username= extras.getString("username");
            }
        } else {
            username= (String) savedInstanceState.getSerializable("username");
        }

        Toast.makeText(this, "Welcome "+username,Toast.LENGTH_LONG).show();
    }

    private void setupNavigation() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @OnClick(R.id.fab)
    void fab(View view){
        Snackbar.make(view, "Atualize seus palpites para oitavas de final da copa do mundo.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        mAdapter.update(games, true);
    }

    static final ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };

    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
