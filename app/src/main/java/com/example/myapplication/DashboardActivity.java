package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DashboardActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    int timerValue = 20;
    ProgressBar progressbar;
    Button try_again_button;
    ArrayList<ModelClass> list;
    ModelClass modelClass;
    int Index = 0;
    int correctCount =0;
    int wrongCount =0;
    TextView show_question,optiona,optionb,optionc,optiond;
    CardView card_question,card_opA,card_opB,card_opC,card_opD;
    Button nextbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Hooks();
        nextbtn.setClickable(false);
        progressbar = findViewById(R.id.progressBar2);
        progressbar.setMax(timerValue);
        countDownTimer = new CountDownTimer(20000,1000) {
            @Override
            public void onTick(long l) {
                timerValue = timerValue - 1;
                progressbar.setProgress(timerValue);
            }

            @Override
            public void onFinish() {
                Dialog dialog = new Dialog(DashboardActivity.this);
                dialog.setContentView(R.layout.time_out_popup);
                dialog.show();
                dialog.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();

        listlombard();
        setAllData();
    }

    private void listlombard() {
        list = new ArrayList<>();
        list.add(new ModelClass("Which of the following option leads to the portability and security of Java?","Bytecode is executed by JVM","The applet makes the Java code secure and portable","Use of exception handling","Dynamic binding between objects","Bytecode is executed by JVM"));
        list.add(new ModelClass("Which of the following is not a Java features?","Dynamic","Architecture Neutral","Use of pointers","Object-oriented","Use of pointers"));
        list.add(new ModelClass(" The "+"\\"+"u0021 article referred to as a","Unicode escape sequence","Octal escape","Hexadecimal","Line feed","Unicode escape sequence"));
        list.add(new ModelClass("_____ is used to find and fix bugs in the Java programs.","JVM","JRE","JDK","JDB","JDB"));
        list.add(new ModelClass("Which of the following is a valid declaration of a char?","char ch = +'\'+'\'+utea+';'","char ca = 'tea';","char cr = \\u0223;","char cc = '\\itea';","char ch = +'\'+'\'+utea+';'"));
        Collections.shuffle(list);
        modelClass = list.get(Index);
    }

    private void setAllData() {
       show_question.setText(modelClass.getQuestion());
       optiona.setText(modelClass.getoA());
       optionb.setText(modelClass.getoB());
       optionc.setText(modelClass.getoC());
       optiond.setText(modelClass.getoD());
    }

    private void Hooks() {
        progressbar = findViewById(R.id.progressBar2);
        nextbtn = findViewById(R.id.nextbtn);
        //text view
        show_question = findViewById(R.id.show_question);
        optiona = findViewById(R.id.optiona);
        optionb = findViewById(R.id.optionb);
        optionc = findViewById(R.id.optionc);
        optiond = findViewById(R.id.optiond);
        // card view
        card_question = findViewById(R.id.card_question);
        card_opA = findViewById(R.id.card_opA);
        card_opB = findViewById(R.id.card_opB);
        card_opC = findViewById(R.id.card_opC);
        card_opD = findViewById(R.id.card_opD);

    }

    public void Correct(CardView card_opA){
        card_opA.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.green));
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                Index++;
                modelClass = list.get(Index);
                setAllData();
                resetColor();
                enableButton();
            }
        });
    }
    public void Wrong(CardView card_opA){
        card_opA.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.red));
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCount++;
                if(Index<list.size()-1){
                    wrongCount++;
                    Index++;
                    modelClass = list.get(Index);
                    setAllData();
                    resetColor();
                    enableButton();
                }else{
                    GameWon();
                }
            }
        });

    }

    private void GameWon() {
        Intent intent = new Intent(DashboardActivity.this, WonActivity.class);
        intent.putExtra("right",correctCount);
        intent.putExtra("wrong",wrongCount);
        startActivity(intent);
    }
    public void enableButton(){
        card_opA.setClickable(true);
        card_opB.setClickable(true);
        card_opC.setClickable(true);
        card_opD.setClickable(true);
    }
    public void disableButton(){
        card_opA.setClickable(false);
        card_opB.setClickable(false);
        card_opC.setClickable(false);
        card_opD.setClickable(false);
    }
    public void resetColor(){
        card_opA.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.white));
        card_opB.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.white));
        card_opC.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.white));
        card_opD.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.white));

    }

    public void OptionAClick(View view) {
        disableButton();
        nextbtn.setClickable(true);
        if(modelClass.getoA().equals(modelClass.getAns())){
            card_opA.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.green));
            if(Index< list.size()-1){
                Correct(card_opA);
            }else{
                GameWon();
            }
        }else{
            Wrong(card_opA);
        }
    }

    public void OptionBClick(View view) {
        disableButton();
        nextbtn.setClickable(true);
        if(modelClass.getoB().equals(modelClass.getAns())){
            card_opB.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.green));
            if(Index< list.size()-1){
                Correct(card_opB);
            }else{
                GameWon();
            }
        }else{
            Wrong(card_opB);
        }
    }


    public void OptionCClick(View view) {
        disableButton();
        nextbtn.setClickable(true);
        if(modelClass.getoC().equals(modelClass.getAns())){
            card_opC.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.green));
            if(Index< list.size()-1){
                Correct(card_opC);
            }else{
                GameWon();
            }
        }else{
            Wrong(card_opC);
        }
    }

    public void OptionDClick(View view) {
        disableButton();
        nextbtn.setClickable(true);
        if(modelClass.getoD().equals(modelClass.getAns())){
            card_opD.setBackgroundColor(ContextCompat.getColor(DashboardActivity.this, R.color.green));
            if(Index< list.size()-1){
                Correct(card_opD);
            }else{
                GameWon();
            }
        }else{
            Wrong(card_opD);
        }
    }

    public void backBtnClick(View view) {
        Intent intent1 = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent1);
    }

    public void exitActivity(View view) {
        finishAffinity(); // Closes all activities in the task
    }
}