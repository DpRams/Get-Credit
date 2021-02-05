package com.example.timer201;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity  implements View.OnClickListener  {
    TextView sec, passnum, Level,words;
    ImageView img;
    Button start, stop, next,rank,clearRank;
    Timer timer = null;
    TimerTask tTask1 = null;

    int sec_for_count = 0, Level_for_count = 1,score_for_rank = 0,count_for_score_in_plus = 0,count_for_score_in_minus = 0;
    int random_for_speed, random_for_goal, speed_for_start, goal_for_start;
    String sec_for_show,name_for_rank;

    static final String db_name = "testDB";
    static final String tb_name = "test";
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sec = (TextView) findViewById(R.id.sec);
        passnum = (TextView) findViewById(R.id.passnum);
        Level = (TextView) findViewById(R.id.Level);
        words = (TextView)findViewById(R.id.words);


        start = (Button) findViewById(R.id.btnstart);
        stop = (Button) findViewById(R.id.btnstop);
        next = (Button) findViewById(R.id.btnNext);
        rank = (Button) findViewById(R.id.btnRank);
        clearRank = (Button)findViewById(R.id.btnClearRank);

        img = (ImageView)findViewById(R.id.imageView);


        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        next.setOnClickListener(this);
        words.setOnClickListener(this);
        rank.setOnClickListener(this);
        Level.setOnClickListener(this);
        clearRank.setOnClickListener(this);

        rank.setVisibility(View.INVISIBLE);

        Intent it = getIntent();
        name_for_rank=it.getStringExtra("name");
        /*name.setText(stname);*/

        db = openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        String createTable = "CREATE TABLE IF NOT EXISTS  "+tb_name+"(name VARCHAR(20),"+"score int (10))";
        db.execSQL(createTable);


    }


    private void addData(String name, int score)
    {
        ContentValues cv = new ContentValues(2);
        cv.put("name",name);
        cv.put("score",score);

        db.insert(tb_name,null,cv);

    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    sec.setText(sec_for_show );
                    break;
                case 2:
                    //用作檢查
                    sec.setText(sec_for_show + "\n Speed Level:" + (String.valueOf(speed_for_start))+ "\nLevel:" + Level_for_count);
                    break;
            }
        }
    };

    public void Pic(int i)
    {
        String [] uri = {"@drawable/level1","@drawable/level2","@drawable/level3","@drawable/level4","@drawable/level5","@drawable/level501","@drawable/level6"};
        int imageResource = getResources().getIdentifier(uri[i-1], null, getPackageName());
        Drawable image = getResources().getDrawable(imageResource);
        img.setImageDrawable(image);
    }
    public void Words(int i )
    {
        String[] sentence={"好那...我要切螢幕囉!","如果齁\n程式寫得不好可以轉系啦!","你一定哪裡打錯啊!\n不是叫你用複製就好嗎?","我記得我在美國的時候齁~","同學看一下這段程式碼","            print(\"死當\")"," 放暑假囉d(`･∀･)b"};
        words.setText(sentence[i-1]);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnstart) {

            StartTimer();
            start.setEnabled(false);
        }
        else if (v.getId() == R.id.btnstop)
        {
            try {
                Cancel();
            } catch (Exception e) {
            } finally {
                stop.setEnabled(false);
                if (stop.isEnabled() == false) {
                    if (sec_for_count != goal_for_start) {
                        next.setText("Again");
                    }
                }
            }
        }
        else if (v.getId() == R.id.btnNext)
        {
            if (Level.getText() == "進入下一堂") {
            sec_for_count = 0;
            next.setText("Next");

        }else if (Level.getText() == "失敗") {

            sec_for_count = 0;
        }
            if (Level_for_count <= 0) {
                Level_for_count = 0;
                Level.setText("你他媽很爛");
            } else if (Level_for_count == 1) {
                Level.setText("第一堂");
                Pic(1);
                Words(1);

            } else if (Level_for_count == 2) {
                Level.setText("第二堂");
                Pic(2);
                Words(2);

            } else if (Level_for_count == 3) {
                Level.setText("第三堂");
                Pic(3);
                Words(3);

            } else if (Level_for_count == 4) {
                Level.setText("第四堂");
                Pic(4);
                Words(4);

            } else if (Level_for_count == 5) {
                Level.setText("第五堂");
                Pic(5);
                Words(5);

            } else if(Level_for_count >5)
            {

                Level.setText("恭喜你");
                Level_for_count = 5;
                Pic(7);
                Words(7);
                words.setTextSize(22);
                score_for_rank = (count_for_score_in_plus*5)+(count_for_score_in_minus*-2);
                passnum.setText(name_for_rank+"的學分:"+score_for_rank);
                passnum.setTextSize(14);
                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                sec.setVisibility(View.INVISIBLE);
                rank.setVisibility(View.VISIBLE);
                clearRank.setVisibility(View.VISIBLE);

            }
            stop.setEnabled(true);
            if (stop.isEnabled() == true) {
                next.setText("Next");
            }
            start.setEnabled(true);

        }else if (v.getId()==R.id.Level) //作弊模式
        {
            Level_for_count+=1;
        }
         else if (v.getId() == R.id.words)
        {
            if(Level_for_count == 5)
            {
                Pic(6);
                Words(6);
                words.setEnabled(false);
            }
        }
        else if (v.getId() == R.id.btnRank)
        {
            /*Cursor c = db.rawQuery("SELECT * FROM "+tb_name,null);*/
            Cursor c = db.query(tb_name,null,null,null,null,null,"score desc");
            if(score_for_rank<=0)
            {
                score_for_rank =0;
            }
             addData(name_for_rank,score_for_rank);

            String str = "排行榜";


            if(c.moveToFirst())
            {
                do{
                    str+= "\n--------\n";
                    str+="姓名:"+c.getString(0)+"\n";
                    str+="學分:"+c.getString(1);
                }while(c.moveToNext());
                passnum.setText(str);
            }
        }
        else if (v.getId() == R.id.btnClearRank)
        {
            clearHistory_Table();
            String str = "排行榜已清空";

            Cursor c1 = db.query(tb_name,null,null,null,null,null,"score desc");
            passnum.setText(str);
        }

    }
    public void clearHistory_Table(){

        db.execSQL("delete  from "+tb_name);


    }
    public int Speed(int i) {
        random_for_speed = (int) (Math.random() * (100-i*10) + 90);
        return random_for_speed;
    }

    public int Goal() {
        random_for_goal = (int) (Math.random() * 30 + 10);
        return random_for_goal;
    }

    public void StartTimer() {
        goal_for_start = Goal();
        passnum.setText("目標秒數:" + String.valueOf(goal_for_start));
        if (Level_for_count ==0)
        {speed_for_start = Speed(0);}
        else if (Level_for_count ==1)
        {speed_for_start = Speed(1);}
        else if (Level_for_count ==2)
        {speed_for_start = Speed(2);}
        else if (Level_for_count ==3)
        {speed_for_start = Speed(3);}
        else if (Level_for_count ==4)
        {speed_for_start = Speed(4);}
        else if (Level_for_count ==5)
        {speed_for_start = Speed(5);}

        if (timer == null) {
            timer = new Timer();
        }
        if (tTask1 == null) {
            tTask1 = CreateTimerTask();
        }

        if (timer != null & tTask1 != null) {
            timer.schedule(tTask1, 1, speed_for_start);
        }

    }
    public void Cancel() {
        if (Math.abs(sec_for_count - goal_for_start) <= 0.5) {
            Level.setText("進入下一堂");
            if (Level.getText() == "進入下一堂") {
                Level_for_count += 1;
                count_for_score_in_plus+=1;
                if(count_for_score_in_plus>5)
                {count_for_score_in_plus = 5;}
            }

            tTask1.cancel();
            tTask1 = null;
        } else if (sec_for_count != goal_for_start) {
            if (Math.abs(sec_for_count - goal_for_start) <= 50) {
                Level.setText("失敗");
                Level_for_count -= 1;
                if (Level_for_count < 1) {
                    Level_for_count = 0;
                }
                tTask1.cancel();
                tTask1 = null;
            }
            if (Level.getText() == "失敗")
            {
                count_for_score_in_minus+=1;
            }
        }

    }

    public TimerTask CreateTimerTask()
    {

        TimerTask tTask;
        tTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                sec_for_count += 1;
                sec_for_show = String.valueOf(sec_for_count);
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        return tTask;
    }


}


