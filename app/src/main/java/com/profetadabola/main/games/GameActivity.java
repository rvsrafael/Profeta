package com.profetadabola.main.games;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.profetadabola.Navigator;
import com.profetadabola.R;
import com.profetadabola.about.AboutFragment;
import com.profetadabola.api.API;
import com.profetadabola.api.APITools;
import com.profetadabola.api.model.EighthGamesResponse;
import com.profetadabola.api.model.GameResponse;
import com.profetadabola.maps.MapsActivity;
import com.profetadabola.maps.MapsFragment;
import com.profetadabola.tools.PersistenceHawk;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GameActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.recyclerview_games)
    RecyclerView recyclerviewGames;

    @BindView(R.id.fab)
    FloatingActionButton fabGame;

    private GameAdapter mAdapter;
    private API mService;
    private double latitude;
    private double longitude;
    private String titlePin;
    private Context context;
    private EighthGamesResponse games;
    private EighthGamesResponse gamesDB;
    static final int SOLICITAR_PERMISSAO = 1;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        init();
        loadingArguments(savedInstanceState);
    }

    private void init() {
        setupToolbar();
        setupDrawer();
        setupNavigation();
        loadGames();
        setupGames();
        setupAdapterGames();
        setupFab();
        setupPush();
    }

    private void setupPush() {
        FirebaseMessaging.getInstance().subscribeToTopic("profeta");
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupFab() {
        fabGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
    }

    private void setupAdapterGames() {

        mAdapter = new GameAdapter(new EighthGamesResponse(), context, new OnItemClickListenerMap() {
            @Override
            public void onItemClick(GameResponse game, GameAction gameAction, int position) {

                switch (gameAction)
                {
                    case TEAM_A_GOAL_LESS: {
                        lessGoalTeamA(game, position);
                        break;
                    }
                    case TEAM_A_GOAL_MORE: {
                        moreGoalTeamA(game, position);
                        break;
                    }
                    case TEAM_B_GOAL_LESS: {
                        lessGoalTeamB(game, position);
                        break;
                    }
                    case TEAM_B_GOAL_MORE: {
                        moreGoalTeamB(game, position);
                        break;
                    }
                    case TEAM_MAP: {
                        latitude = game.getLatitude();
                        longitude = game.getLongitude();
                        titlePin = game.getStadium();

                        MapFragment mMapFragment = MapFragment.newInstance();
                        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.container_main, mMapFragment);
                        fragmentTransaction.commit();
                        fabGame.hide();
                        toolbar.setTitle(getString(R.string.title_activity_maps));
                        mMapFragment.getMapAsync(GameActivity.this);

                        break;
                    }
                }
            }
        });
    }

    private void moreGoalTeamB(GameResponse game, int position) {
        int newGoal = Integer.parseInt(game.getTeamB().getGoal())+1;
        games.getGames().get(position).getTeamB().setGoal(String.valueOf(newGoal));
        mAdapter.update(games, GameAction.TEAM_B_GOAL_MORE);
    }

    private void lessGoalTeamB(GameResponse game, int position) {
        if (Integer.parseInt(game.getTeamB().getGoal()) > 0) {
            int newGoal = Integer.parseInt(game.getTeamB().getGoal())-1;
            games.getGames().get(position).getTeamB().setGoal(String.valueOf(newGoal));
        }
        mAdapter.update(games, GameAction.TEAM_B_GOAL_LESS);
    }

    private void lessGoalTeamA(GameResponse game, int position) {
        if (Integer.parseInt(game.getTeamA().getGoal()) > 0) {
            int newGoal = Integer.parseInt(game.getTeamA().getGoal())-1;
            games.getGames().get(position).getTeamA().setGoal(String.valueOf(newGoal));
        }
        mAdapter.update(games, GameAction.TEAM_A_GOAL_LESS);
    }

    private void moreGoalTeamA(GameResponse game, int position) {
        int newGoal = Integer.parseInt(game.getTeamA().getGoal())+1;
        games.getGames().get(position).getTeamA().setGoal(String.valueOf(newGoal));
        mAdapter.update(games, GameAction.TEAM_A_GOAL_MORE);
    }


    private void setupGames() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerviewGames.setLayoutManager(layoutManager);
        recyclerviewGames.setAdapter(mAdapter);
        recyclerviewGames.setHasFixedSize(true);
    }

    public void loadGames() {
        mService = APITools.getAPI();

        gamesDB = PersistenceHawk.getSteps(GameStep.ROUND_OF_16);

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

                        if(gamesDB != null && gamesDB.getGames().size() > 0) {
                            games = gamesDB;
                        } else {
                            games = game;
                        }
                        mAdapter.update(games, GameAction.TEAM_DONE);
                    }
                });

    }

    private void loadingArguments(Bundle savedInstanceState) {

        String userNamePref = PersistenceHawk.getValueString("username");

        if (userNamePref != null && userNamePref != "") {
            setUserLoading(userNamePref);
        } else {
            String username;
            if (savedInstanceState == null) {
                Bundle extras = getIntent().getExtras();
                if(extras != null) {
                    username = extras.getString("username");
                    setUserLoading(username);
                }
            }  else {
                username = (String) savedInstanceState.getSerializable("username");
                setUserLoading(username);
            }
        }



    }

    private void setUserLoading(String username) {
        TextView textViewNavbarUser =  (TextView)findViewById(R.id.textview_navbar_user);
        //textViewNavbarUser.setText("Olá" +username);
        PersistenceHawk.setValueString("username", username);
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

        if (GameAdapter.isVisibility){
            mAdapter.update(games, GameAction.TEAM_DONE);
            PersistenceHawk.setSteps(GameStep.ROUND_OF_16, games);
            fabGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
            mAdapter.updateAction(false);
        } else {
            mAdapter.update(games, GameAction.TEAM_EDIT);
            fabGame.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
            mAdapter.updateAction(true);
        }
    }

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
        Fragment fragment = null;
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
            fragment = new AboutFragment();
            fabGame.hide();
            toolbar.setTitle(getString(R.string.title_activity_about));
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main, fragment,  "fragment");
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                checarPermissao();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.action_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void checarPermissao(){

        // Verifica  o estado da permissão de WRITE_EXTERNAL_STORAGE
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Se for diferente de PERMISSION_GRANTED, então vamos exibir a tela padrão
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
        } else {
            // Senão vamos compartilhar a imagem
            sharedImage();
            //takeScreenshot();
        }
    }

    private void sharedImage() {

        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);

        Bitmap b = Bitmap.createBitmap(v1.getDrawingCache());

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Profeta", null);

        Uri imageUri =  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Selecione"));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng localization = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(localization).title(titlePin).visible(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localization));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(localization).zoom(16.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.moveCamera(cameraUpdate);
    }
}
