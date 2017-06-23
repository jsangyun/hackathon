package com.example.ryan.forif_test;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TestViewHolder> /*implements OnItemClickListener*/ {

    private ArrayList<Item> ArrayItems;
//    public OnItemClickListener mListener;

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        public ImageView AwayTeamLogo, HomeTeamLogo, BaseStatus;
        public TextView AwayTeamName, HomeTeamName, AwayTeamPit, HomeTeamPit, BallCount, StrikeCount, OutCount, AwayScore, HomeScore, GameStatus;
//        public OnItemClickListener mListener;

        private TestViewHolder(View v) {
            super(v);
            AwayTeamLogo = (ImageView) v.findViewById(R.id.mSAwayLogo);
            HomeTeamLogo = (ImageView) v.findViewById(R.id.mSHomeLogo);
            BaseStatus = (ImageView) v.findViewById(R.id.mSBaseStatus);
            AwayTeamName = (TextView) v.findViewById(R.id.mSAwayName);
            HomeTeamName = (TextView) v.findViewById(R.id.mSHomeName);
            AwayTeamPit = (TextView) v.findViewById(R.id.mSAwayPlayer);
            HomeTeamPit = (TextView) v.findViewById(R.id.mSHomePlayer);
            BallCount = (TextView) v.findViewById(R.id.mSBallCount);
            StrikeCount = (TextView) v.findViewById(R.id.mSStrikeCount);
            OutCount = (TextView) v.findViewById(R.id.mSOutCount);
            AwayScore = (TextView) v.findViewById(R.id.mSAwayScore);
            HomeScore = (TextView) v.findViewById(R.id.mSHomeScore);
            GameStatus = (TextView) v.findViewById(R.id.mSGameStatus);
        }
        /*public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mListener = onItemClickListener;
        }*/
    }


    public MyAdapter(ArrayList<Item> Items) {
        ArrayItems = Items;

    }


    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new TestViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        Item item = ArrayItems.get(position);
        holder.AwayTeamLogo.setImageDrawable(item.mAwayTeamLogo);
        holder.HomeTeamLogo.setImageDrawable(item.mHomeTeamLogo);
        holder.BaseStatus.setImageDrawable(item.mBaseStatus);
        holder.AwayTeamName.setText(item.mAwayTeamName);
        holder.HomeTeamName.setText(item.mHomeTeamName);
        holder.AwayTeamPit.setText(item.mAwayTeamPit);
        holder.HomeTeamPit.setText(item.mHomeTeamPit);
        holder.BallCount.setText(item.mBallCount);
        holder.StrikeCount.setText(item.mStrikeCount);
        holder.OutCount.setText(item.mOutCount);
        holder.AwayScore.setText(item.mAwayScore);
        holder.HomeScore.setText(item.mHomeScore);
        holder.GameStatus.setText(item.mGameStatus);


        /*--------------대체 코드----------------/
        holder.AwayTeamLogo.setImageDrawable(ArrayItems.get(position).mAwayTeamLogo);
         holder.HomeTeamLogo.setImageDrawable(ArrayItems.get(position).mHomeTeamLogo); 
        holder.BaseStatus.setImageDrawable(ArrayItems.get(position).mBaseStatus); 
        holder.AwayTeamName.setText(ArrayItems.get(position).mAwayTeamName); 
        holder.HomeTeamName.setText(ArrayItems.get(position).mHomeTeamName); 
        holder.AwayTeamPit.setText(ArrayItems.get(position).mAwayTeamPit); 
        holder.HomeTeamPit.setText(ArrayItems.get(position).mHomeTeamPit);
         holder.BallCount.setText(ArrayItems.get(position).mBallCount); 
        holder.StrikeCount.setText(ArrayItems.get(position).mStrikeCount);
         holder.OutCount.setText(ArrayItems.get(position).mOutCount); 
        holder.AwayScore.setText(ArrayItems.get(position).mAwayScore);
         holder.HomeScore.setText(ArrayItems.get(position).mHomeScore);
         holder.GameStatus.setText(ArrayItems.get(position).mGameStatus);

         /-----------------------------------*/
    }

    @Override
    public int getItemCount() {
        return ArrayItems.size();

    }
}
