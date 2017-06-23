package com.example.ryan.forif_test;

import android.graphics.drawable.Drawable;

/**
 * RecyclerView Item on 2017. 5. 19..
 */

public class Item {
    public Drawable mAwayTeamLogo, mHomeTeamLogo, mBaseStatus;
    public String mAwayTeamName, mHomeTeamName;
    public String mAwayTeamPit, mHomeTeamPit, mBallCount;
    public String mStrikeCount, mOutCount, mAwayScore, mHomeScore, mGameStatus;

    public Item(Drawable mAwayTeamLogo, Drawable mHomeTeamLogo, Drawable mBaseStatus,
                String mAwayTeamName, String mHomeTeamName,
                String mAwayTeamPit, String mHomeTeamPit, String mBallCount,
                String mStrikeCount, String mOutCount, String mAwayScore,
                String mHomeScore, String mGameStatus) {
        this.mAwayTeamLogo = mAwayTeamLogo;
        this.mHomeTeamLogo = mHomeTeamLogo;
        this.mBaseStatus = mBaseStatus;

        this.mAwayTeamName = mAwayTeamName;
        this.mHomeTeamName = mHomeTeamName;
        this.mAwayTeamPit = mAwayTeamPit;
        this.mHomeTeamPit = mHomeTeamPit;
        this.mBallCount = mBallCount;
        this.mStrikeCount = mStrikeCount;
        this.mOutCount = mOutCount;
        this.mAwayScore = mAwayScore;
        this.mHomeScore = mHomeScore;
        this.mGameStatus = mGameStatus;

    }
}
