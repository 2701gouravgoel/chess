package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView[][] imageViews = new ImageView[8][8];
    private ImageView sampleimage;
    private TextView p1,p2;
    private boolean player1Turn = true ;
    private int who=0;
    private int xp=0;
    private int yp=0;
    private LinearLayout turnP1;
    private LinearLayout turnP2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sampleimage=(ImageView)findViewById(R.id.sample);
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                String bid = "c"+i+j;
                imageViews[i][j]=(ImageView)findViewById(getResources().getIdentifier(bid,"id",getPackageName()));
                imageViews[i][j].setOnClickListener(this);
            }
        }


        p1=(TextView)findViewById(R.id.forp1);
        p2=(TextView)findViewById(R.id.forp2);
        turnP1=(LinearLayout)findViewById(R.id.turnp1);
        turnP2=(LinearLayout)findViewById(R.id.turnp2);


        turnP1.setBackgroundColor(Color.parseColor("#8b0000"));
        p1.setVisibility(View.INVISIBLE);
        p2.setVisibility(View.INVISIBLE);




    }

    boolean check1=false;
    boolean check2=false;
    @Override
    public void onClick(View v)
    {
        String vtag = String.valueOf(v.getTag());


        if( vtag.substring(0,2).equals("bl"))
        {

            int xx=0,yy=0;
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    if((v.getId()==imageViews[i][j].getId()))
                    {
                        xx=i;
                        yy=j;
                    }
                }
            }
            imageViews[xx][yy].setImageDrawable(imageViews[xp][yp].getDrawable());
            imageViews[xx][yy].setTag(imageViews[xp][yp].getTag());
            imageViews[xp][yp].setTag("empty");
            imageViews[xp][yp].setImageDrawable(null);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 == 0) {
                        imageViews[i][j].setBackgroundColor(Color.parseColor("#8b4513"));
                    }
                    else {
                        imageViews[i][j].setBackgroundColor(Color.parseColor("#b5651d"));
                    }

                    String ss= String.valueOf(imageViews[i][j].getTag());
                    if(imageViews[i][j].getDrawable()!=null && ss.equals("blueempty"))
                    {
                        imageViews[i][j].setImageDrawable(null);
                    }

                    if(ss.substring(0,2).equals("bl"))
                    {
                        int l = ss.length();
                        imageViews[i][j].setTag(ss.substring(4,l));
                    }

                }
            }

            boolean f =checkCheckMate(imageViews,!player1Turn);

            if(f) {
                if (!player1Turn) {
                    check1 = true;
                } else {
                    check2 = true;
                }
            }
            else{
                check1=false;
                check2=false;
            }
            if(!f)
            {
                p1.setVisibility(View.INVISIBLE);
                p2.setVisibility(View.INVISIBLE);
            }
            if(player1Turn)
            {
                player1Turn=false;
                turnP1.setBackgroundColor(Color.parseColor("#992233"));
                turnP2.setBackgroundColor(Color.parseColor("#8b0000"));
            }
            else
            {
                turnP1.setBackgroundColor(Color.parseColor("#8b0000"));
                turnP2.setBackgroundColor(Color.parseColor("#992233"));
                player1Turn=true;
            }
        }

        else if(check1)
        {
            Toast.makeText(this,vtag,Toast.LENGTH_SHORT).show();

            if(checkMate(imageViews,player1Turn))
            {
                p2.setText("check mate");
                p2.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(!imageViews[i][j].getTag().equals("kingCheck")) {
                        if ((i + j) % 2 == 0) {
                            imageViews[i][j].setBackgroundColor(Color.parseColor("#8b4513"));
                        } else {
                            imageViews[i][j].setBackgroundColor(Color.parseColor("#b5651d"));
                        }

                        String ss = String.valueOf(imageViews[i][j].getTag());
                        if (imageViews[i][j].getDrawable() != null && ss.equals("blueempty")) {
                            imageViews[i][j].setImageDrawable(null);
                        }

                        if (ss.substring(0, 2).equals("bl")) {
                            int l = ss.length();
                            imageViews[i][j].setTag(ss.substring(4, l));
                        }
                    }
                }
            }
            if(vtag.equals("pawn")||vtag.equals("pawn1")||vtag.equals("pawn2")||vtag.equals("pawn3")||vtag.equals("pawn4")||
                    vtag.equals("pawn5") ||vtag.equals("pawn6")||vtag.equals("pawn7"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;


                total=getMovepawn(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("rook")||vtag.equals("rook2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoverook(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("bishop")||vtag.equals("bishop2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveBishop(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("kingCheck"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveKing(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String ss=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    if(ss.substring(0,1).equals("w")||ss.equals("empty")) {
                        String tt = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        String tt2 = String.valueOf(imageViews[xp][yp].getTag());
                        imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                        imageViews[xp][yp].setTag("empty");

                        if (!checkCheckMate(imageViews, player1Turn)) {
                            xs[cnt] = x[i];
                            ys[cnt] = y[i];
                            cnt++;
                        }

                        imageViews[xp][yp].setTag(tt2);
                        imageViews[x[i]][y[i]].setTag(tt);
                    }
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("knight")||vtag.equals("knight2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveknight(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("queen"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMovequeen(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
        }



        else if(check2)
        {
            Toast.makeText(this,vtag,Toast.LENGTH_SHORT).show();

            if(checkMate(imageViews,!player1Turn))
            {
                p1.setText("check mate");
                p1.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(!imageViews[i][j].getTag().equals("wkingCheck")) {
                        if ((i + j) % 2 == 0) {
                            imageViews[i][j].setBackgroundColor(Color.parseColor("#8b4513"));
                        } else {
                            imageViews[i][j].setBackgroundColor(Color.parseColor("#b5651d"));
                        }

                        String ss = String.valueOf(imageViews[i][j].getTag());
                        if (imageViews[i][j].getDrawable() != null && ss.equals("blueempty")) {
                            imageViews[i][j].setImageDrawable(null);
                        }

                        if (ss.substring(0, 2).equals("bl")) {
                            int l = ss.length();
                            imageViews[i][j].setTag(ss.substring(4, l));
                        }
                    }
                }
            }
            if(vtag.equals("wpawn")||vtag.equals("wpawn1")||vtag.equals("wpawn2")||vtag.equals("wpawn3")||vtag.equals("wpawn4")||
                    vtag.equals("wpawn5") ||vtag.equals("wpawn6")||vtag.equals("wpawn7"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;


                total=getMovewpawn(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,!player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (!s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("wrook")||vtag.equals("wrook2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoverook(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,!player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (!s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("wbishop")||vtag.equals("wbishop2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveBishop(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,!player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (!s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("wkingCheck"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveKing(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String ss=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    if(!ss.substring(0,1).equals("w")) {
                        String tt = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        String tt2 = String.valueOf(imageViews[xp][yp].getTag());
                        imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                        imageViews[xp][yp].setTag("empty");

                        if (!checkCheckMate(imageViews,!player1Turn)) {
                            xs[cnt] = x[i];
                            ys[cnt] = y[i];
                            cnt++;
                        }

                        imageViews[xp][yp].setTag(tt2);
                        imageViews[x[i]][y[i]].setTag(tt);
                    }
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("wknight")||vtag.equals("wknight2"))
            {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMoveknight(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,!player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (!s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
            if(vtag.equals("wqueen"))
            {
                int xp=0;
                int yp=0;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (String.valueOf(imageViews[i][j].getTag()).equals(vtag)) {
                            xp = i;
                            yp = j;
                            break;
                        }
                    }
                }
                int [] x = new int[64];
                int [] y = new int[64];
                int total=0;
                total=getMovequeen(imageViews,xp,yp,x,y,total);

                int [] xs =new int[64];
                int [] ys =new int[64];
                int cnt=0;

                for(int i=0;i<total;i++)
                {
                    String tt=String.valueOf(imageViews[x[i]][y[i]].getTag());
                    String tt2=String.valueOf(imageViews[xp][yp].getTag());
                    imageViews[x[i]][y[i]].setTag(imageViews[xp][yp].getTag());
                    imageViews[xp][yp].setTag("empty");

                    if(!checkCheckMate(imageViews,!player1Turn))
                    {
                        xs[cnt]=x[i];
                        ys[cnt]=y[i];
                        cnt++;
                    }

                    imageViews[xp][yp].setTag(tt2);
                    imageViews[x[i]][y[i]].setTag(tt);
                }
                for(int i=0;i<cnt;i++) {
                    String s = String.valueOf(imageViews[xs[i]][ys[i]].getTag());
                    if (s.equals("empty")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setImageResource(R.drawable.greendot);
                    } else if (!s.substring(0, 1).equals("w")) {
                        imageViews[xs[i]][ys[i]].setTag("blue" + s);
                        imageViews[xs[i]][ys[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
            }
        }
        else{
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 == 0) {
                        imageViews[i][j].setBackgroundColor(Color.parseColor("#8b4513"));
                    }
                    else {
                        imageViews[i][j].setBackgroundColor(Color.parseColor("#b5651d"));
                    }

                    String ss= String.valueOf(imageViews[i][j].getTag());
                    if(imageViews[i][j].getDrawable()!=null && ss.equals("blueempty"))
                    {
                        imageViews[i][j].setImageDrawable(null);
                    }

                    if(ss.substring(0,2).equals("bl"))
                    {
                        int l = ss.length();
                        imageViews[i][j].setTag(ss.substring(4,l));
                    }

                }
            }
            String value = String.valueOf(v.getTag());
            if (((ImageView) v).getDrawable() == null) {
                who = 0;
            }
            else if (value.equals("pawn") || value.equals("pawn1") || value.equals("pawn2") || value.equals("pawn3")
                    ||value.equals("pawn4") || value.equals("pawn5") || value.equals("pawn6") || value.equals("pawn7"))
            {
                if (player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMovepawn(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if (value.equals("wpawn") || value.equals("wpawn1") || value.equals("wpawn2") || value.equals("wpawn3")
                    ||value.equals("wpawn4") || value.equals("wpawn5") || value.equals("wpawn6") || value.equals("wpawn7"))
            {
                if (!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMovewpawn(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if (value.equals("knight") || value.equals("knight2"))
            {
                if(player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoveknight(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }

                }
            }
            else if (value.equals("wknight") || value.equals("wknight2"))
            {
                if(!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoveknight(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("wrook")||value.equals("wrook2"))
            {
                if(!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoverook(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("rook")||value.equals("rook2"))
            {
                if(player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoverook(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("bishop") ||value.equals("bishop2"))
            {
                if(player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];

                    int total=0;
                    total=getMoveBishop(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("wbishop") ||value.equals("wbishop2"))
            {
                if(!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoveBishop(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("king"))
            {
                if(player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }
                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMoveKing(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }
            }
            else if(value.equals("wking"))
            {
                if(!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                            total=getMoveKing(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }

                }
            }
            else if(value.equals("queen"))
            {
                if(player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];
                    int total=0;
                    total=getMovequeen(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }
                }

            }
            else if(value.equals("wqueen"))
            {
                if(!player1Turn)
                {
                    boolean f = true;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (String.valueOf(imageViews[i][j].getTag()).equals(value)) {
                                xp = i;
                                yp = j;

                                f = false;
                                break;
                            }
                        }
                        if (f == false) {
                            break;
                        }
                    }

                    int [] x = new int[64];
                    int [] y = new int[64];

                    int total=0;
                    total=getMovequeen(imageViews,xp,yp,x,y,total);


                    for(int i=0;i<total;i++)
                    {
                        String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                        if (s.equals("empty")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setImageResource(R.drawable.greendot);
                        }
                        else if (!s.substring(0, 1).equals("w")) {
                            imageViews[x[i]][y[i]].setTag("blue" + s);
                            imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff0000"));
                        }
                    }

                }

            }
            else
            {

            }
        }
    }

    private boolean checkMate(ImageView[][] imageViews, boolean player1Turn)
    {

        if(player1Turn) {

            boolean f = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String vtag = String.valueOf(imageViews[i][j].getTag());
                    if(vtag.equals("pawn")||vtag.equals("pawn1")||vtag.equals("pawn2")||vtag.equals("pawn3")||vtag.equals("pawn4")||
                            vtag.equals("pawn5") ||vtag.equals("pawn6")||vtag.equals("pawn7"))
                    {
                        Toast.makeText(this,"hii",Toast.LENGTH_SHORT).show();

                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMovepawn(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }

                    }
                    if(vtag.equals("rook")||vtag.equals("rook2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoverook(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("bishop")||vtag.equals("bishop2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveBishop(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("kingCheck"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveKing(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("knight")||vtag.equals("knight2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveknight(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("queen"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMovequeen(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }
                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                }
            }
            return f;
        }
        else
        {
            boolean f =true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String vtag = String.valueOf(imageViews[i][j]);
                    if(vtag.equals("wpawn")||vtag.equals("wpawn1")||vtag.equals("wpawn2")||vtag.equals("wpawn3")
                            ||vtag.equals("wpawn4")||
                            vtag.equals("wpawn5") ||vtag.equals("wpawn6")||vtag.equals("wpawn7"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMovewpawn(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }

                    }
                    if(vtag.equals("wrook")||vtag.equals("wrook2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoverook(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("wbishop")||vtag.equals("wbishop2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveBishop(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("wkingCheck"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveKing(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("wknight")||vtag.equals("wknight2"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMoveknight(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                    if(vtag.equals("wqueen"))
                    {
                        int [] x = new int[64];
                        int [] y = new int[64];
                        int total=0;
                        total=getMovequeen(imageViews,i,j,x,y,total);
                        for(int ii=0;ii<total;ii++)
                        {
                            String tt=String.valueOf(imageViews[x[ii]][y[ii]].getTag());
                            String tt2=String.valueOf(imageViews[i][j].getTag());
                            imageViews[x[ii]][y[ii]].setTag(imageViews[i][j].getTag());
                            imageViews[i][j].setTag("empty");
                            if(!checkCheckMate(imageViews,player1Turn))
                            {
                                f=false;
                            }

                            imageViews[i][i].setTag(tt2);
                            imageViews[x[ii]][y[ii]].setTag(tt);
                        }
                    }
                }
            }
            return  f;
        }
    }

    private int getMovepawn(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;

        if (xp <7) {
            if(imageViews[xp + 1][yp].getTag().equals("empty")) {
                x[cnt]=xp+1;
                y[cnt]=yp;
                cnt++;
            }

            if(xp==1){
                if(imageViews[xp+2][yp].getTag().equals("empty")) {
                    x[cnt]=xp+2;
                    y[cnt]=yp;
                    cnt++;
                }

            }
            if (yp > 0 && !imageViews[xp + 1][yp - 1].getTag().equals("empty")) {
                String s=String.valueOf(imageViews[xp+1][yp-1].getTag());
                if(s.substring(0,1).equals("w")) {
                    x[cnt]=xp+1;
                    y[cnt]=yp-1;
                    cnt++;
                }
            }
            if (yp < 7 && !imageViews[xp + 1][yp + 1].getTag().equals("empty")) {

                String s=String.valueOf(imageViews[xp+1][yp+1].getTag());
                if(s.substring(0,1).equals("w")) {
                    x[cnt]=xp+1;
                    y[cnt]=yp+1;
                    cnt++;
                }
            }
        }

        return cnt;
    }

    private int getMovewpawn(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;

        if (xp > 0) {
            if(imageViews[xp - 1][yp].getTag().equals("empty")) {
                x[cnt]=xp-1;
                y[cnt]=yp;
                cnt++;
            }
            if(xp==6){
                if(imageViews[xp - 2][yp].getTag().equals("empty")) {
                    x[cnt]=xp-2;
                    y[cnt]=yp;
                    cnt++;
                 }

            }
            if (yp > 0 && !imageViews[xp - 1][yp - 1].getTag().equals("empty")) {
                String s=String.valueOf(imageViews[xp-1][yp-1].getTag());
                if(!s.substring(0,1).equals("w")) {
                    x[cnt]=xp-1;
                    y[cnt]=yp-1;
                    cnt++;
                }
            }
            if (yp < 7 && !imageViews[xp - 1][yp + 1].getTag().equals("empty")) {

                String s=String.valueOf(imageViews[xp-1][yp+1].getTag());
                if(!s.substring(0,1).equals("w")) {
                    x[cnt]=xp-1;
                    y[cnt]=yp+1;
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private int getMoveknight(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;

        if(xp >1 )
        {
            if(yp>0)
            {
                x[cnt]=xp -2;
                y[cnt]=yp-1;
                cnt++;
            }

            if(yp<7)
            {
                x[cnt]=xp -2;
                y[cnt]=yp+1;
                cnt++;
            }
        }
        if(xp<6 )
        {
            if(yp>0)
            {
                x[cnt]=xp +2;
                y[cnt]=yp-1;
                cnt++;
            }

            if(yp<7)
            {
                x[cnt]=xp +2;
                y[cnt]=yp+1;
                cnt++;
            }
        }
        if(yp > 1 )
        {
            if(xp>0)
            {
                x[cnt]=xp -1;
                y[cnt]=yp-2;
                cnt++;
            }

            if(xp<7)
            {

                x[cnt]=xp +1;
                y[cnt]=yp-2;
                cnt++;
            }
        }
        if(yp<6 )
        {
            if(xp>0)
            {

                x[cnt]=xp-1;
                y[cnt]=yp+2;
                cnt++;
            }

            if(xp<7)
            {
                x[cnt]=xp +1;
                y[cnt]=yp+2;
                cnt++;
            }
        }

        return cnt;
    }

    private int getMoverook(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;

        int i=xp+1;
        while (i<7)
        {
            if(!imageViews[i][yp].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp+1<=7) {
            for (int j = xp + 1; j <= i; j++) {
                x[cnt]=j;
                y[cnt]=yp;
                cnt++;
            }
        }
        i=xp-1;
        while (i>0)
        {
            if(!imageViews[i][yp].getTag().equals("empty"))
            {
                break;
            }
            i--;
        }
        if(xp-1>=0) {
            for (int j = xp - 1; j >= i; j--) {
                x[cnt]=j;
                y[cnt]=yp;
                cnt++;
            }
        }

        i=yp-1;
        while (i>0)
        {
            if(!imageViews[xp][i].getTag().equals("empty"))
            {
                break;
            }
            i--;
        }
        if(yp-1>=0) {
            for (int j = yp - 1; j >= i; j--) {
                x[cnt]=xp;
                y[cnt]=j;
                cnt++;
            }
        }
        i=yp+1;
        while (i<7)
        {
            if(!imageViews[xp][i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(yp+1<=7) {
            for (int j = yp + 1; j <= i; j++) {
                x[cnt]=xp;
                y[cnt]=j;
                cnt++;
            }
        }
        return cnt;
    }

    private int getMoveBishop(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;
        int i=1;
        while((xp+i<7)&&(yp+i<7))
        {
            if(!imageViews[xp+i][yp+i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp<7 && yp<7)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp+j;
                y[cnt]=yp+j;
                cnt++;
            }
        }
        i=1;
        while((xp+i<7)&&(yp-i>0))
        {
            if(!imageViews[xp+i][yp-i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp<7 && yp>0)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp+j;
                y[cnt]=yp-j;
                cnt++;
            }
        }
        i=1;
        while((xp-i>0)&&(yp+i<7))
        {
            if(!imageViews[xp-i][yp+i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp>0 && yp<7)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp-j;
                y[cnt]=yp+j;
                cnt++;
            }
        }
        i=1;
        while((xp-i>0)&&(yp-i>0))
        {
            if(!imageViews[xp-i][yp-i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp>0 && yp>0)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp-j;
                y[cnt]=yp-j;
                cnt++;
            }
        }
        return cnt;
    }

    private int getMoveKing(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;
        if(xp>0 )
        {
            if(yp>0)
            {
                x[cnt]=xp-1;
                y[cnt]=yp-1;
                cnt++;
            }
            if(yp<7)
            {
                x[cnt]=xp-1;
                y[cnt]=yp+1;
                cnt++;
            }

            x[cnt]=xp-1;
            y[cnt]=yp;
            cnt++;
        }
        if(xp<7)
        {
            if(yp>0)
            {
                x[cnt]=xp+1;
                y[cnt]=yp-1;
                cnt++;
            }
            if(yp<7)
            {
                x[cnt]=xp+1;
                y[cnt]=yp+1;
                cnt++;
            }
            x[cnt]=xp+1;
            y[cnt]=yp;
            cnt++;
        }
        if(yp<7)
        {
            x[cnt]=xp;
            y[cnt]=yp+1;
            cnt++;
        }
        if(yp>0)
        {
            x[cnt]=xp;
            y[cnt]=yp-1;
            cnt++;
        }
        return cnt;
    }

    private int getMovequeen(ImageView[][] imageViews, int xp, int yp, int[] x, int[] y,int total)
    {
        int cnt=total;
        int i=1;
        while((xp+i<7)&&(yp+i<7))
        {
            if(!imageViews[xp+i][yp+i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp<7 && yp<7)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp+j;
                y[cnt]=yp+j;
                cnt++;
            }
        }
        i=1;
        while((xp+i<7)&&(yp-i>0))
        {
            if(!imageViews[xp+i][yp-i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp<7 && yp>0)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp+j;
                y[cnt]=yp-j;
                cnt++;
            }
        }
        i=1;
        while((xp-i>0)&&(yp+i<7))
        {
            if(!imageViews[xp-i][yp+i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp>0 && yp<7)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp-j;
                y[cnt]=yp+j;
                cnt++;
            }
        }
        i=1;
        while((xp-i>0)&&(yp-i>0))
        {
            if(!imageViews[xp-i][yp-i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp>0 && yp>0)
        {
            for(int j=1;j<=i;j++)
            {
                x[cnt]=xp-j;
                y[cnt]=yp-j;
                cnt++;
            }
        }

        i=xp+1;
        while (i<7)
        {
            if(!imageViews[i][yp].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(xp+1<=7) {
            for (int j = xp + 1; j <= i; j++) {
                x[cnt]=j;
                y[cnt]=yp;
                cnt++;
            }
        }
        i=xp-1;
        while (i>0)
        {
            if(!imageViews[i][yp].getTag().equals("empty"))
            {
                break;
            }
            i--;
        }
        if(xp-1>=0) {
            for (int j = xp - 1; j >= i; j--) {
                x[cnt]=j;
                y[cnt]=yp;
                cnt++;
            }
        }

        i=yp-1;
        while (i>0)
        {
            if(!imageViews[xp][i].getTag().equals("empty"))
            {
                break;
            }
            i--;
        }
        if(yp-1>=0) {
            for (int j = yp - 1; j >= i; j--) {
                x[cnt]=xp;
                y[cnt]=j;
                cnt++;
            }
        }
        i=yp+1;
        while (i<7)
        {
            if(!imageViews[xp][i].getTag().equals("empty"))
            {
                break;
            }
            i++;
        }
        if(yp+1<=7) {
            for (int j = yp + 1; j <= i; j++) {
                x[cnt]=xp;
                y[cnt]=j;
                cnt++;
            }
        }
        return cnt;
    }

    private boolean checkCheckMate(ImageView[][] imageViews, boolean player1Turn)
    {
        if(player1Turn) {
            boolean f=false;
            int total=0;
            int [] x=new int[64];
            int [] y=new int[64];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String s=String.valueOf(imageViews[i][j].getTag());
                    if(s.substring(0,1).equals("w"))
                    {
                        if(s.equals("wqueen"))
                            {total=getMovequeen(imageViews,i,j,x,y,total);}
                        if(s.equals("wking"))
                            {total=getMoveKing(imageViews,i,j,x,y,total);}
                        if(s.equals("wknight")||s.equals("wknight2"))
                            {total=getMoveknight(imageViews,i,j,x,y,total);}
                        if(s.equals("wpawn")||s.equals("wpawn1")||s.equals("wpawn2")||s.equals("wpawn3")||
                                s.equals("wpawn4")||s.equals("wpawn5")||s.equals("wpawn6")||s.equals("wpawn7"))
                            {total=getMovewpawn(imageViews,i,j,x,y,total);}
                        if(s.equals("wbishop")||s.equals("wbishop2"))
                            {total=getMoveBishop(imageViews,i,j,x,y,total);}
                        if(s.equals("wrook")||s.equals("wrook2"))
                            {total=getMoverook(imageViews,i,j,x,y,total);}
                    }
                }
            }
            for (int i = 0; i < total; i++) {
                String s = String.valueOf(imageViews[x[i]][y[i]].getTag());
                if (s.equals("king") || s.equals("kingCheck")) {
                    imageViews[x[i]][y[i]].setBackgroundColor(Color.parseColor("#ff3347"));
                    imageViews[x[i]][y[i]].setTag("kingCheck");
                    p2.setVisibility(View.VISIBLE);
                    f = true;
                }
            }
            return f;
        }
        else {
            boolean f=false;
            int total=0;
            int [] x=new int[200];
            int [] y=new int[200];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    String s=String.valueOf(imageViews[i][j].getTag());
                    if(!(s.substring(0,1).equals("w")||s.equals("empty")))
                    {
                        if(s.equals("queen"))
                        {total=getMovequeen(imageViews,i,j,x,y,total);}
                        if(s.equals("king"))
                        {total=getMoveKing(imageViews,i,j,x,y,total);}
                        if(s.equals("knight")||s.equals("knight2"))
                        {total=getMoveknight(imageViews,i,j,x,y,total);}
                        if(s.equals("pawn")||s.equals("pawn1")||s.equals("pawn2")||s.equals("pawn3")||
                                s.equals("pawn4")||s.equals("pawn5")||s.equals("pawn6")||s.equals("pawn7"))
                        {total=getMovewpawn(imageViews,i,j,x,y,total);}
                        if(s.equals("bishop")||s.equals("bishop2"))
                        {total=getMoveBishop(imageViews,i,j,x,y,total);}
                        if(s.equals("rook")||s.equals("rook2"))
                        {total=getMoverook(imageViews,i,j,x,y,total);}
                    }
                }
            }

                for(int k = 0; k < total; k++) {
                    String s = String.valueOf(imageViews[x[k]][y[k]].getTag());
                    if (s.equals("wking")||s.equals("wkingCheck")) {
                        imageViews[x[k]][y[k]].setBackgroundColor(Color.parseColor("#ff3347"));
                        imageViews[x[k]][y[k]].setTag("wkingCheck");
                        p1.setVisibility(View.VISIBLE);
                        f = true;
                        break;
                    }
                }
            return f;
        }
    }
}
