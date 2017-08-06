package com.profetadabola;

import android.content.Context;
import android.content.Intent;

import com.profetadabola.main.games.GameActivity;
import com.profetadabola.maps.MapsActivity;
import com.profetadabola.tools.Constant;

public class Navigator {

    public static void startMaps(Context context, double latitude, double longitude, String titlePinMaps) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.LATITUDE, latitude);
        intent.putExtra(Constant.LONGITUDE, longitude);
        intent.putExtra(Constant.TITLE_PIN_MAPS, titlePinMaps);
        context.startActivity(intent);
    }

    public static void startGames(Context context, String username){
        Intent intent = new Intent(context, GameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

}
