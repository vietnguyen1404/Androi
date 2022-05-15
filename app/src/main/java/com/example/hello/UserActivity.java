package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;


import android.os.Bundle;

public class UserActivity extends AppCompatActivity {

    TextView txtWelcome;
    Button  btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        String s = "Chào mừng tài khoản : " + MainActivity.userNameLogined;
        txtWelcome.setText(s);

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}