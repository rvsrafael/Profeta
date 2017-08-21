package com.profetadabola.main.games;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.profetadabola.R;
import com.profetadabola.api.model.EighthGamesResponse;
import com.profetadabola.tools.DateUtil;
import com.squareup.picasso.Picasso;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder>  {

    private final OnItemClickListenerMap listenerMap;
    private EighthGamesResponse games;
    public static boolean isVisibility;
    private Context context;

    public GameAdapter(EighthGamesResponse games,Context context, OnItemClickListenerMap listener) {
        this.games = games;
        this.listenerMap = listener;
        this.context = context;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View meuLayout = inflater.inflate(R.layout.row_game,
                parent, false);
        return new GameViewHolder(meuLayout);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, final int position) {
        holder.textviewLabelTeamA.setText(games.getGames().get(position).getTeamA().getName());
        holder.textviewLabelTeamB.setText(games.getGames().get(position).getTeamB().getName());
        holder.textviewLabelGoalA.setText(games.getGames().get(position).getTeamA().getGoal());
        holder.textviewLabelGoalB.setText(games.getGames().get(position).getTeamB().getGoal());
        holder.textviewLabelStadium.setText(games.getGames().get(position).getStadium());


        String dateFormat = DateUtil.formatDateGame(games.getGames().get(position).getDate());
        if (dateFormat != null) {
            holder.textviewLabelDate.setText(dateFormat);
        }

        Picasso.with(holder.itemView.getContext())
                .load(games.getGames().get(position).getTeamA().getIcon())
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.ic_menu_gallery)
                .into(holder.imageViewFlagTeamA);

        Picasso.with(holder.itemView.getContext())
                .load(games.getGames().get(position).getTeamB().getIcon())
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.ic_menu_gallery)
                .into(holder.imageViewFlagTeamB);

        holder.linearlayoutFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMap.onItemClick(games.getGames().get(position), GameAction.TEAM_MAP, position);
            }
        });

        holder.buttonLessTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMap.onItemClick(games.getGames().get(position), GameAction.TEAM_A_GOAL_LESS, position);
            }
        });

        holder.buttonMoreTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMap.onItemClick(games.getGames().get(position), GameAction.TEAM_A_GOAL_MORE, position);
            }
        });

        holder.buttonLessTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMap.onItemClick(games.getGames().get(position), GameAction.TEAM_B_GOAL_LESS, position);
            }
        });

        holder.buttonMoreTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerMap.onItemClick(games.getGames().get(position), GameAction.TEAM_B_GOAL_MORE, position);
            }
        });

        if (isVisibility){
            holder.buttonLessTeamA.setVisibility(View.VISIBLE);
            holder.buttonMoreTeamA.setVisibility(View.VISIBLE);
            holder.buttonLessTeamB.setVisibility(View.VISIBLE);
            holder.buttonMoreTeamB.setVisibility(View.VISIBLE);
        } else {
            holder.buttonLessTeamA.setVisibility(View.INVISIBLE);
            holder.buttonMoreTeamA.setVisibility(View.INVISIBLE);
            holder.buttonLessTeamB.setVisibility(View.INVISIBLE);
            holder.buttonMoreTeamB.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return games.getGames().size();
    }

    public void updateAction(boolean action) {
        isVisibility = action;
        notifyDataSetChanged();
    }


    public void update(EighthGamesResponse items, GameAction action) {

        switch (action) {
            case TEAM_A_GOAL_LESS: {
                isVisibility = true;
                break;
            }
            case TEAM_A_GOAL_MORE: {
                isVisibility = true;
                break;
            }
            case TEAM_B_GOAL_LESS: {
                isVisibility = true;
                break;
            }
            case TEAM_B_GOAL_MORE: {
                isVisibility = true;
                break;
            }
            case TEAM_MAP: {
                isVisibility = false;
                break;
            }
            case TEAM_EDIT: {
                isVisibility = true;
                break;
            }
            case TEAM_DONE: {
                isVisibility = false;
                break;
            }
        }

        games = items;
        notifyDataSetChanged();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewLabelTeamA;
        public TextView textviewLabelGoalA;
        public TextView textviewLabelTeamB;
        public TextView textviewLabelGoalB;
        public TextView textviewLabelStadium;
        public ImageView imageViewFlagTeamA;
        public ImageView imageViewFlagTeamB;
        public TextView textviewLabelDate;
        public Button buttonLessTeamA;
        public Button buttonMoreTeamA;
        public Button buttonMoreTeamB;
        public Button buttonLessTeamB;
        public LinearLayout linearlayoutFooter;
        public FloatingActionButton fabGame;



        public GameViewHolder(View itemView) {
            super(itemView);
            textviewLabelTeamA = (TextView) itemView.findViewById(R.id.textview_label_teamA);
            textviewLabelGoalA = (TextView) itemView.findViewById(R.id.textview_label_goalA);
            textviewLabelTeamB = (TextView) itemView.findViewById(R.id.textview_label_teamB);
            textviewLabelGoalB = (TextView) itemView.findViewById(R.id.textview_label_goalB);
            textviewLabelStadium = (TextView) itemView.findViewById(R.id.textview_label_stadium);
            textviewLabelDate = (TextView) itemView.findViewById(R.id.textview_label_date);
            imageViewFlagTeamA = (ImageView) itemView.findViewById(R.id.imageView_flag_teamA);
            imageViewFlagTeamB = (ImageView) itemView.findViewById(R.id.imageView_flag_teamB);
            buttonLessTeamA = (Button) itemView.findViewById(R.id.button_less_teamA);
            buttonLessTeamB = (Button) itemView.findViewById(R.id.button_less_teamB);
            buttonMoreTeamA = (Button) itemView.findViewById(R.id.button_more_teamA);
            buttonMoreTeamB = (Button) itemView.findViewById(R.id.button_more_teamB);
            linearlayoutFooter = (LinearLayout) itemView.findViewById(R.id.linearlayout_footer);
            fabGame = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }



    }

}
