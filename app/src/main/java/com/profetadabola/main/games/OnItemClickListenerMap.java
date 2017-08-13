package com.profetadabola.main.games;

import com.profetadabola.api.model.GameResponse;

public interface OnItemClickListenerMap {
    void onItemClick(GameResponse game, GameAction action, int position);
}
