package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    EditText m_edtUser,m_edtPass;
    Button m_btnLogin;
    static String userNameLogined;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_edtUser = (EditText)findViewById(R.id.edtUser);
        m_edtPass = (EditText)findViewById(R.id.edtPass);
        m_btnLogin = (Button)findViewById(R.id.btnLogin);

        m_btnLogin.setOnClickListener(new CButtonLogin());
    }

    public class CButtonLogin implements View.OnClickListener {
        public void onClick(View v) {
            String szUser = m_edtUser.getText().toString();
            String szPass = m_edtPass.getText().toString();

            if(szUser.length() <= 3 || szPass.length() < 6){
                Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                apiLogin(szUser,szPass);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    void apiLogin(String user, String pass) throws IOException {
        OkHttpClient client = new OkHttpClient();
        userNameLogined = user;
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url("http://172.16.2.84:3080/login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("K40",e.toString());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("K40",response.body().string());
                if (!response.isSuccessful()){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);
            }
        });
    }

}