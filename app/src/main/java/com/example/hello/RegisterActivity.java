package com.example.hello;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.hello.MainActivity.JSON;

public class RegisterActivity extends AppCompatActivity {
    TextView txtBack;
    EditText edtUser,edtName,edtPass1,edtPass2; //Biến điều khiển EditText
    Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Gán các biến điều khiển cho các Controls
        txtBack = (TextView)findViewById(R.id.txtBack);
        edtUser = (EditText)findViewById(R.id.edtUser);
        edtName = (EditText)findViewById(R.id.edtName);
        edtPass1 = (EditText)findViewById(R.id.edtPass);
        edtPass2 = (EditText)findViewById(R.id.edtPass2);
        btnCreate = (Button)findViewById(R.id.btnCreateUser);

        txtBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Nhận thông tin từ Form
                String szUser = edtUser.getText().toString();
                String szPass1 = edtPass1.getText().toString();
                String szPass2 = edtPass2.getText().toString();
                String szName = edtName.getText().toString();

                //Kiểm tra tính hợp lệ của tài khoản, mật khẩu
                if (szUser.length() <= 3 || szPass1.compareTo(szPass2) != 0 || szPass1.length() < 6){
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Nên kiểm tra username đã tồn tại hay chưa trong hàm dịch vụ
                try {
                    apiRegister(szUser,szPass1,szName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //Gọi hàm dịch vụ Register
    void apiRegister(String user, String pass, String fullName) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\",\"fullname\":\"" + fullName + "\"}";
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://172.16.1.1:3000/register")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("K40", response.body().string());
                if (!response.isSuccessful()) {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Đăng ký tài khoản lỗi.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công.", Toast.LENGTH_SHORT).show();
                            //Quay trở lại form đăng nhập
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}