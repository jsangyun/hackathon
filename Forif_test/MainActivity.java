package com.example.kjanghoi.mlb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.kjanghoi.mlb.R;

public class MainActivity extends ActionBarActivity {

    //이전 키를 2번 누르면 앱을 종료
    private long mExitModeTime = 0L;
    @Override
    public  void onBackPressed() {
        if(mExitModeTime != 0 && SystemClock.uptimeMillis() - mExitModeTime < 3000) {
            finish();
        }else {
            android.widget.Toast.makeText(this, "이전키를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            mExitModeTime = SystemClock.uptimeMillis();
        }
    }

    //리스트뷰 , 어댑터의 선언
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    //데이터를 담을 변수 선언
    



    //날짜와 url 관련 변수
    SimpleDateFormat sdf = null;
    String dataString = null;
    String year = null;
    String month = null;
    String us_day = null;
    String day_current = null;
    String day_before = null;
    String day_after = null;
    String xml_day = null;
    String uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스플래쉬 화면 
        startActivity(new Intent(this, SplashActivity.class));

        //날짜 , 시간을 처리하는 부분
        current_Date = (TextView) findViewById(R.id.date);
        // 현재 시간을 받아와서
        date = System.currentTimeMillis();
        // 이러한 형식에 맞추고
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataString = sdf.format(date);
        // 메인화면에 오늘 날짜를 출력
        current_Date.setText(dataString);

        // 년,월,일을 잘라서 저장
        year = dataString.substring(0, 4);
        month = dataString.substring(5, 7);
        day_current = dataString.substring(8, 10);

        // 미국날짜 -> 한국날짜
        int tmp = Integer.parseInt(day_current);
        tmp--;

        if(tmp < 10) {
            us_day = Integer.toString(tmp);
            us_day = "0" + us_day;
        }
        else {
            us_day = Integer.toString(tmp);
        }

        //객체 생성 , url 변수
        task = new GetMLBScoreboard();
        uri = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml";
        task.execute("http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml");
    }

    //ViewHolder
    private class ViewHolder {
        public ImageView f_logo;
        public ImageView s_logo;
        public ImageView sit;

        public TextView f_team;
        public TextView f_player;
        public TextView f_score;

        public TextView s_team;
        public TextView s_player;
        public TextView s_score;

        public TextView inning;
        public TextView b_count;
        public TextView s_count;
        public TextView o_count;
    }

    //AsyncTask와 Generics 같이 설명
    private class GetMLBScoreboard extends AsyncTask<String, Void, Elements> {
        @Override

        protected Elements doInBackground(String... strings) {
            try {
                Document doc = Jsoup.parse(new URL(strings[0]).openStream(), "utf-8", strings[0]);

                // game 이란 변수로 elements 를 받아온다.
                Elements elements = doc.select("game");

                // 게임 수 만큼 for loop
                for (int i = 0; i < elements.size(); i++) {


                    /*  파싱해서 데이터를 변수에 담는 부분
                    힌트:
                    어떤 변수 = element.get(i).attr("~~~~");
                    어떤 변수 = element.get(i).select("~~~~").attr("~~~~");
                    
                    // 원정,홈 팀의 이름
                    1.
                    2.
                    힌트: home_name_abbrev , away_name_abbrev

                    // 게임의 상태, 볼,스트라이크,아웃 카운트
                    1.
                    2.
                    3.
                    4.
                    힌트: status , b , s , o

                    // 이닝 , 이닝상태(초,중,말)
                    1.
                    2.
                    힌트: status , inning , inning_state

                    // 원정,홈팀의 스코어
                    1.
                    2.
                    힌트: linescore , r , home , away

                    // 현재와 다음 투수,타자
                    1.
                    2.
                    3.
                    4.
                    힌트 : batter , picther , first , last

                    // 진루 상황 , 미국시간
                    1.
                    2.
                    힌트: runners_on_base , status , time
                    */    
                    
                }
                return elements;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Elements elements) {
            super.onPostExecute(elements);

            // 리스트뷰와 어댑터 선언 , 연결
            mListView = (ListView) findViewById(R.id.mList);
            mAdapter = new ListViewAdapter(getApplicationContext());
            mListView.setAdapter(mAdapter);

            
            //경기 상황에 대한 분류
            for (int i = 0; i < elements.size(); i++) {

                // 경기 이전
                if (game_status[i].equals("Preview")) {
                    tmp_1[i] = us_time[i].substring(0, 1);
                    tmp_3[i] = us_time[i].substring(2, 3);
                    temp_1[i] = Integer.parseInt(tmp_1[i]);

                    if (tmp_3[i].equals(":")) {
                        tmp_2[i] = us_time[i].substring(0, 2);
                        temp_2[i] = Integer.parseInt(tmp_2[i]);
                        temp_2[i]++;

                        kr_time[i] = Integer.toString(temp_2[i]) + ":" + us_time[i].substring(3, 5);
                        game_status[i] = kr_time[i];

                        ball_counts[i] = null;
                        strike_counts[i] = null;
                        out_counts[i] = null;
                        base_image[i] = getResources().getDrawable(R.drawable.base_0);
                        player[2 * i] = null;
                        player[2 * i + 1] = null;
                    } else {
                        temp_1[i]++;

                        kr_time[i] = Integer.toString(temp_1[i]) + ":" + us_time[i].substring(2, 4);
                        game_status[i] = kr_time[i];
                        ball_counts[i] = null;
                        strike_counts[i] = null;
                        out_counts[i] = null;
                        base_image[i] = getResources().getDrawable(R.drawable.base_0);
                        player[2 * i] = null;
                        player[2 * i + 1] = null;
                    }
                    // 경기 시작 임박
                } else if (game_status[i].equals("Pre-Game") || game_status[i].equals("Warmup")) {
                    game_status[i] = "곧 시작";

                    ball_counts[i] = null;
                    strike_counts[i] = null;
                    out_counts[i] = null;
                    base_image[i] = getResources().getDrawable(R.drawable.base_0);
                    player[2 * i] = null;
                    player[2 * i + 1] = null;

                    // 챌린지 or 지연 상황
                } else if (game_status[i].equals("In Progress")) {
                    if (game_status[i].equals("ManagerChallenge")) {
                        game_status[i] = "챌린지";
                        break;
                    }
                    if (game_status[i].equals("Delayed") || game_status[i].equals("Delayed Start")) {
                        game_status[i] = "지연";
                        break;
                    }
                    // 진루 상황(8가지 경우의 수)
                    switch (Integer.parseInt(base_status[i])) {
                        case 0: {
                            ...
                        }
                        case 1: {
                            ...
                        }
                        case 2: {
                            ...
                        }
                        case 3: {
                            ...
                        }
                        case 4: {
                            ...
                        }
                        case 5: {
                            ...
                        }
                        case 6: {
                            ...
                        }
                        case 7: {
                            ...
                        }
                        default: {
                            ...
                        }
                    }

                    // ~회 초
                    if (inning_State[i].equals("Top")) {
                        inning_State[i] = "초";
                        player[2 * i + 1] = batter_first[i] + " " + batter_last[i];
                        player[2 * i] = pitcher_first[i] + " " + pitcher_last[i];
                    }
                    // ~회초 종료
                    if (inning_State[i].equals("Middle")) {
                        inning_State[i] = "초 종료";
                    }
                    // ~회 말
                    if (inning_State[i].equals("Bottom")) {
                        inning_State[i] = "말";
                        player[2 * i] = batter_first[i] + " " + batter_last[i];
                        player[2 * i + 1] = pitcher_first[i] + " " + pitcher_last[i];
                    }
                    // ~회말 종료
                    if (inning_State[i].equals("End")) {
                        inning_State[i] = "말 종료";
                    }
                    game_status[i] = inning[i] + "회" + inning_State[i];
                    // 경기종료
                } else if (game_status[i].equals("Final") || game_status[i].equals("Game Over")) {
                    game_status[i] = "경기 종료";
                    ball_counts[i] = null;
                    strike_counts[i] = null;
                    out_counts[i] = null;
                    base_image[i] = getResources().getDrawable(R.drawable.base_0);
                    player[2 * i] = null;
                    player[2 * i + 1] = null;
                }
            }

            //팀에 대한 분류 , 경기수의 2배만큼 for-loop
            for (int i = 0; i < 2 * elements.size(); i++) {
                // 30개 팀의 이름,로고를 넣기

            }

            // addItem
            for (int i = 0; i < elements.size(); i++) {
                mAdapter.addItem(~~~~~)
            }
        }
    }

    private class ListViewAdapter extends BaseAdapter {
        // 여긴 솔직히 안가르쳐준다 인간적으로.
    }
}

