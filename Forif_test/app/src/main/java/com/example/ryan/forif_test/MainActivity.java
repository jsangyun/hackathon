package com.example.ryan.forif_test;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    //이전 키를 2번 누르면 앱을 종료
    private long mExitModeTime = 0L;
    GetMLBScoreboard task = null;
    GetMLBScoreboard task_btn = null;

    @Override
    public void onBackPressed() {
        if (mExitModeTime != 0 && SystemClock.uptimeMillis() - mExitModeTime < 3000) {
            finish();
        } else {
            android.widget.Toast.makeText(this, "이전키를 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            mExitModeTime = SystemClock.uptimeMillis();
        }
    }


    /*private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    */
    //리스트뷰 대신에 리사이클러뷰 사용

    //리사이클러뷰 , 어댑터 , 레이아웃매니저 선언
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Item> Items;//Item class들 Arraylist


    public Button btn_yesterday;
    public Button btn_refresh;
    public Button btn_tomorrow;


    //데이터를 담을 변수 선언
    public String ATeamName[];
    public String ATeamScore[];
    public Drawable ATeamLogo[];


    public String HTeamName[];
    public String HTeamScore[];
    public Drawable HTeamLogo[];


    public String Player[];
    public String batter_first[];
    public String batter_last[];
    public String pitcher_first[];
    public String pitcher_last[];


    public String BallCount[];
    public String StrikeCount[];
    public String OutCount[];
    public String BaseStatus[];

    public String GameStatus[];
    public String Reason[];
    public String Inning[];
    public String InningState[];

    public Drawable BaseStatus_Image[];


    public String tmp_1[];
    public String tmp_2[];
    public String tmp_3[];

    public Integer temp_1[];
    public Integer temp_2[];

    public String us_time[];
    public String kr_time[];

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


    long date;
    TextView current_Date = null;
    String uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        task = new GetMLBScoreboard();

        //버튼 바인딩
        btn_yesterday = (Button) findViewById(R.id.btn_yesterday);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_tomorrow = (Button) findViewById(R.id.btn_tomorrow);
//        btn_refresh.setOnClickListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //수직 리사이클러뷰 바인딩
        mRecyclerView.setHasFixedSize(true);

//        startActivity(new Intent(this, SplashActivity.class)); splash activity 에서 Action import못하고
        //appindex에서 "cannot resolve method 발생"


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Items = new ArrayList<Item>();
//        mAdapter=new MyAdapter(Items);
//        mRecyclerView.setAdapter(mAdapter);

        // 스플래쉬 화면
//        startActivity(new Intent(this, SplashActivity.class));

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

        String tmp3=month;
        tmp3="05";

        month=tmp3;

        int tmp2=Integer.parseInt(day_current);
        tmp2=17;
        day_current=Integer.toString(tmp2);

        // 미국날짜 -> 한국날짜
        int tmp = Integer.parseInt(day_current);
        tmp--;

        if (tmp < 10) {
            us_day = Integer.toString(tmp);
            us_day = "0" + us_day;
        } else {
            us_day = Integer.toString(tmp);
        }

        //객체 생성 , url 변수
        task = new GetMLBScoreboard();
        uri = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml";
        task.execute(uri);
//        mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());

    }

    public void push_to_yesterday(View v) {
        task_btn = new GetMLBScoreboard();
//        int tmp_date=0;
//        if (1) {
        int tmp_date = Integer.parseInt(day_current);
//        }
        tmp_date--;
        day_current = Integer.toString(tmp_date); // btn의 반복적인 구현을 위해서
        tmp_date--; //미국 시간하고 맞추기 위해서
        us_day = Integer.toString(tmp_date);
        dataString=year + "/" + month + "/" + day_current;
        current_Date.setText(dataString);
        uri = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml";
        task_btn.execute(uri);
    }


    public void push_to_refresh(View v) {
        task_btn = new GetMLBScoreboard();
        int tmp_date = Integer.parseInt(day_current);
        tmp_date--;
        us_day = Integer.toString(tmp_date);

        uri = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml";
        task_btn.execute(uri);
    }

    public void push_to_tomorrow(View v) {
        task_btn = new GetMLBScoreboard();
        int tmp_date = Integer.parseInt(day_current);
        tmp_date++;//25
        day_current = Integer.toString(tmp_date);// btn의 반복적인 구현을 위해서
        tmp_date = Integer.parseInt(day_current); // 25
        tmp_date--;//24
        us_day = Integer.toString(tmp_date);//us_day : 24
        dataString=year + "/" + month + "/" + day_current;
        current_Date.setText(dataString);
        uri = "http://gd2.mlb.com/components/game/mlb/year_" + year + "/month_" + month + "/day_" + us_day + "/scoreboard_android.xml";
        task_btn.execute(uri);

//        mAdapter.notifyDataSetChanged();
    }

    //ViewHolder
    /*
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
    }*/

    //AsyncTask와 Generics 같이 설명
    private class GetMLBScoreboard extends AsyncTask<String, Void, Elements> {
        @Override

        protected Elements doInBackground(String... strings) {
            try {
                Document doc;
                doc = Jsoup.parse(new URL(strings[0]).openStream(), "utf-8", strings[0]);

                // game 이란 변수로 elements 를 받아온다.
                Elements elements = doc.select("game");


                ATeamName = new String[20];
                ATeamScore = new String[20];
                ATeamLogo = new Drawable[20];
//              ATeamPit_Last = new String[50];
//              ATeamPit_First = new String[50];


                HTeamName = new String[20];
                HTeamScore = new String[20];
                HTeamLogo = new Drawable[20];

//              HTeamPit_First = new String[50];
//              HTeamPit_Last = new String[50];


                Player = new String[35];
                batter_first = new String[50];
                batter_last = new String[50];

                pitcher_first = new String[50];
                pitcher_last = new String[50];


                BallCount = new String[20];
                StrikeCount = new String[20];
                OutCount = new String[20];
                BaseStatus = new String[20];


                GameStatus = new String[20];
                Reason = new String[20];
                Inning = new String[20];
                InningState = new String[20];
                BaseStatus_Image = new Drawable[20];


                tmp_1 = new String[20];
                tmp_2 = new String[20];
                tmp_3 = new String[20];

                temp_1 = new Integer[20];
                temp_2 = new Integer[20];

                us_time = new String[20];
                kr_time = new String[20];

                // 게임 수 만큼 for loop
                for (int i = 0; i < elements.size(); i++) {

                    ATeamName[i] = elements.get(i).attr("away_name_abbrev");
                    HTeamName[i] = elements.get(i).attr("home_name_abbrev");


                    GameStatus[i] = elements.get(i).select("status").attr("status");
                    Reason[i] = elements.get(i).select("status").attr("reason");
                    BallCount[i] = elements.get(i).select("status").attr("b");
                    StrikeCount[i] = elements.get(i).select("status").attr("s");
                    OutCount[i] = elements.get(i).select("status").attr("o");


                    Inning[i] = elements.get(i).select("status").attr("inning");
                    InningState[i] = elements.get(i).select("status").attr("inning_state");


                    ATeamScore[i] = elements.get(i).select("linescore").select("r").attr("away");
                    HTeamScore[i] = elements.get(i).select("linescore").select("r").attr("home");


                    batter_first[i] = elements.get(i).select("batter").attr("first");
                    batter_last[i] = elements.get(i).select("batter").attr("last");
                    pitcher_first[i] = elements.get(i).select("pitcher").attr("first");
                    pitcher_last[i] = elements.get(i).select("pitcher").attr("last");


                    BaseStatus[i] = elements.get(i).select("runners_on_base").attr("status");
                    us_time[i] = elements.get(i).attr("time");


                }
                return elements;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void onPostExecute(Elements elements) {
            super.onPostExecute(elements);

            // 리스트뷰와 어댑터 선언 , 연결
//            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new MyAdapter(Items);
            mRecyclerView.setAdapter(mAdapter);


            //경기 상황에 대한 분류
            for (int i = 0; i < elements.size(); i++) {

                // 경기 이전
                if (GameStatus[i].equals("Preview")) {
                    tmp_1[i] = us_time[i].substring(0, 1);
                    tmp_3[i] = us_time[i].substring(2, 3);
                    temp_1[i] = Integer.parseInt(tmp_1[i]);

                    if (tmp_3[i].equals(":")) { //시간이 10단위 이상일때
                        tmp_2[i] = us_time[i].substring(0, 2);
                        temp_2[i] = Integer.parseInt(tmp_2[i]);
                        temp_2[i]++;

                        kr_time[i] = Integer.toString(temp_2[i]) + ":" + us_time[i].substring(3, 5);
                        GameStatus[i] = kr_time[i];
                        StrikeCount[i] = null;
                        BallCount[i] = null;
                        OutCount[i] = null;
                        BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);
                        Player[2 * i] = null;
                        Player[2 * i + 1] = null;
                    } else {

                        temp_1[i]++; //kr_time은 string
                        kr_time[i] = Integer.toString(temp_1[i]) + ":" + us_time[i].substring(2, 4);
                        GameStatus[i] = kr_time[i];
                        StrikeCount[i] = null;
                        BallCount[i] = null;
                        OutCount[i] = null;
                        BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);
                        Player[2 * i] = null;
                        Player[2 * i + 1] = null;
                    }
                    // 경기 시작 임박
                } else if (GameStatus[i].equals("Pre-Game") || GameStatus[i].equals("Warmup")) {

                    GameStatus[i] = "곧 시작";
                    StrikeCount[i] = null;
                    BallCount[i] = null;
                    OutCount[i] = null;
                    BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);
                    Player[2 * i] = null;
                    Player[2 * i + 1] = null;

                    // 챌린지 or 지연 상황
                } else if (GameStatus[i].equals("In Progress")) {
                    if (GameStatus[i].equals("ManagerChallenge")) {
                        GameStatus[i] = "챌린지";
                        break;
                    }
                    /*if (GameStatus[i].equals("Delayed") || GameStatus[i].equals("Delayed Start")) {
                        GameStatus[i] = "지연";
                        break;
                    }*/ //not work


                    // 진루 상황(8가지 경우의 수)
                    switch (Integer.parseInt(BaseStatus[i])) {
                        case 0: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);
                            break;
                        }
                        case 1: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_1);
                            break;
                        }
                        case 2: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_2);
                            break;
                        }
                        case 3: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_3);
                            break;
                        }
                        case 4: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_4);
                            break;
                        }
                        case 5: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_5);
                            break;
                        }
                        case 6: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_6);
                            break;
                        }
                        case 7: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_7);
                            break;
                        }
                        default: {
                            BaseStatus_Image[i] = getResources().getDrawable(R.drawable.refresh);
                            break;
                        }
                    }

                    // ~회 초
                    if (InningState[i].equals("Top")) {
                        InningState[i] = "초";
                        Player[2 * i + 1] = batter_first[i] + " " + batter_last[i]; //batter_first == batter의 이름
                        Player[2 * i] = pitcher_first[i] + " " + pitcher_last[i];
                    }
                    // ~회초 종료
                    if (InningState[i].equals("Middle")) {
                        InningState[i] = "초 종료";
                    }
                    // ~회 말
                    if (InningState[i].equals("Bottom")) {
                        InningState[i] = "말";
                        Player[2 * i] = batter_first[i] + " " + batter_last[i];
                        Player[2 * i + 1] = pitcher_first[i] + " " + pitcher_last[i];
                    }
                    // ~회말 종료
                    if (InningState[i].equals("End")) {
                        InningState[i] = "말 종료";
                    }
                    GameStatus[i] = Inning[i] + "회" + InningState[i];
                    // 경기종료
                } else if (GameStatus[i].equals("Final") || GameStatus[i].equals("Game Over")) {
                    GameStatus[i] = "경기 종료";
                    BallCount[i] = "N";
                    OutCount[i] = "N";
                    StrikeCount[i] = "N";
//                    BallCount[i] = null;
//                    StrikeCount[i] = null;
                    OutCount[i] = "N";
                    BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);
                    Player[2 * i] = null;
                    Player[2 * i + 1] = null;
                } else if (GameStatus[i].equals("Delayed") || GameStatus[i].equals("Delayed Start")) {
                    GameStatus[i] = "지연";
                    BaseStatus_Image[i] = getResources().getDrawable(R.drawable.base_0);

                } else if (GameStatus[i].equals("Postponed")) {
                    if (Reason[i].equals("Rain")) {
                        GameStatus[i] = "우천 취소";
                    } else {
                        GameStatus[i] = "취소";
                    }

                }
            }

            //팀에 대한 분류 , 경기수의 2배만큼 for-loop
            for (int i = 0; i < elements.size(); i++) {
                switch (ATeamName[i]) {
                    case "ARI": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.ari);
                        break;
                    }
                    case "ATL": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.atl);
                        break;
                    }
                    case "BAL": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.bal);
                        break;
                    }
                    case "BOS": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.bos);
                        break;
                    }
                    case "CHC": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.chc);
                        break;
                    }
                    case "CIN": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.cin);
                        break;
                    }
                    case "CLE": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.cle);
                        break;
                    }
                    case "COL": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.col);
                        break;
                    }
                    case "CWS": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.cws);
                        break;
                    }
                    case "DET": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.det);
                        break;
                    }
                    case "HOU": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.hou);
                        break;
                    }
                    case "KC": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.kc);
                        break;
                    }
                    case "LAA": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.laa);
                        break;
                    }
                    case "LAD": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.lad);
                        break;
                    }
                    case "MIA": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.mia);
                        break;
                    }
                    case "MIL": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.mil);
                        break;
                    }
                    case "MIN": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.min);
                        break;
                    }
                    case "NYM": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.nym);
                        break;
                    }
                    case "NYY": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.nyy);
                        break;
                    }
                    case "OAK": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.oak);
                        break;
                    }
                    case "PHI": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.phi);
                        break;
                    }
                    case "PIT": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.pit);
                        break;
                    }
                    case "SD": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.sd);
                        break;
                    }
                    case "SEA": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.sea);
                        break;
                    }
                    case "SF": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.sf);
                        break;
                    }
                    case "STL": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.stl);
                        break;
                    }
                    case "TB": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.tb);
                        break;
                    }
                    case "TEX": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.tex);
                        break;
                    }
                    case "TOR": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.tor);
                        break;
                    }
                    case "WSH": {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.wsh);
                        break;
                    }
                    default: {
                        ATeamLogo[i] = getResources().getDrawable(R.drawable.refresh);
                        break;
                    }
                    // 30개 어웨이 팀의 이름,로고를 넣기

                }
                switch (HTeamName[i]) {
                    case "ARI": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.ari);
                        break;
                    }
                    case "ATL": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.atl);
                        break;
                    }
                    case "BAL": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.bal);
                        break;
                    }
                    case "BOS": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.bos);
                        break;
                    }
                    case "CHC": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.chc);
                        break;
                    }
                    case "CIN": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.cin);
                        break;
                    }
                    case "CLE": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.cle);
                        break;
                    }
                    case "COL": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.col);
                        break;
                    }
                    case "CWS": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.cws);
                        break;
                    }
                    case "DET": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.det);
                        break;
                    }
                    case "HOU": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.hou);
                        break;
                    }
                    case "KC": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.kc);
                        break;
                    }
                    case "LAA": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.laa);
                        break;
                    }
                    case "LAD": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.lad);
                        break;
                    }
                    case "MIA": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.mia);
                        break;
                    }
                    case "MIL": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.mil);
                        break;
                    }
                    case "MIN": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.min);
                        break;
                    }
                    case "NYM": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.nym);
                        break;
                    }
                    case "NYY": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.nyy);
                        break;
                    }
                    case "OAK": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.oak);
                        break;
                    }
                    case "PHI": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.phi);
                        break;
                    }
                    case "PIT": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.pit);
                        break;
                    }
                    case "SD": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.sd);
                        break;
                    }
                    case "SEA": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.sea);
                        break;
                    }
                    case "SF": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.sf);
                        break;
                    }
                    case "STL": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.stl);
                        break;
                    }
                    case "TB": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.tb);
                        break;
                    }
                    case "TEX": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.tex);
                        break;
                    }
                    case "TOR": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.tor);
                        break;
                    }
                    case "WSH": {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.wsh);
                        break;
                    }
                    default: {
                        HTeamLogo[i] = getResources().getDrawable(R.drawable.refresh);
                        break;
                    }
                    // 30개 홈 팀의 이름,로고를 넣기

                }
            }

            // addItem
            Items.clear(); //배열리스트안에 item 다 지우기
            for (int i = 0; i < elements.size(); i++) {
                Items.add(new Item(ATeamLogo[i], HTeamLogo[i], BaseStatus_Image[i],
                        ATeamName[i], HTeamName[i], Player[2 * i + 1], Player[2 * i], BallCount[i],
                        StrikeCount[i], OutCount[i], ATeamScore[i], HTeamScore[i], GameStatus[i]));
            }
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount()); //화면 번쩍이는 효과...? 실제로 아이템 지워주지는 않음
        }
    }
}

