package com.example.connectserverdatabasedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Promlert Lovichit
 * Jan 19, 2017
 * http://www.3bugs.com/
 * https://www.facebook.com/AndroidDevBook/
 */

public class MainActivity extends AppCompatActivity {

    // ถ้าเครื่องจำลองที่ใช้คือ Genymotion ให้เปลี่ยน IP เป็น 10.0.3.2
    private static final String BASE_URL = "http://10.0.2.2/demo/";
    private static final String ADD_DATA_URL = BASE_URL + "add_data.php";

    private final OkHttpClient mClient = new OkHttpClient();

    private Button mAddDataButton;
    private EditText mNameEditText, mLastNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    private void setupViews() {
        mAddDataButton = (Button) findViewById(R.id.add_data_button);
        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mLastNameEditText = (EditText) findViewById(R.id.last_name_edit_text);

        mAddDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNameEditText.length() == 0) {
                    mNameEditText.setError("ป้อนชื่อ");
                    mNameEditText.requestFocus();
                } else if (mLastNameEditText.length() == 0) {
                    mLastNameEditText.setError("ป้อนนามสกุล");
                    mLastNameEditText.requestFocus();
                } else {
                    String name = "พร้อมเลิศ"; //mNameEditText.getText().toString();
                    String lastName = "หล่อวิจิตร"; //mLastNameEditText.getText().toString();
                    addData(name, lastName);
                }
            }
        });
    }

    private void addData(String name, String lastName) {
        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("last_name", lastName)
                .build();

        Request request = new Request.Builder()
                .url(ADD_DATA_URL)
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // รันโค้ดใน UI Thread (ใส่โค้ดที่จะรันในเมธอด run())
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                String msg = "การเพิ่มข้อมูลล้มเหลว: ไม่สามารถเชื่อมต่อกับ Server ได้";
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // ตรวจสอบสถานะของ Response ว่าสำเร็จหรือไม่
                if (response.isSuccessful()) {
                    String jsonResult = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        //int success = jsonObject.getInt("success");
                        final String message = jsonObject.getString("message");
                        // รันโค้ดใน UI Thread (ใส่โค้ดที่จะรันในเมธอด run())
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // รันโค้ดใน UI Thread (ใส่โค้ดที่จะรันในเมธอด run())
                        new Handler(Looper.getMainLooper()).post(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        String msg = "การเพิ่มข้อมูลล้มเหลว: เกิดข้อผิดพลาดในการ Parse ข้อมูล JSON";
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                    }
                } else {
                    // รันโค้ดใน UI Thread (ใส่โค้ดที่จะรันในเมธอด run())
                    new Handler(Looper.getMainLooper()).post(
                            new Runnable() {
                                @Override
                                public void run() {
                                    String msg = "การเพิ่มข้อมูลล้มเหลว: " + response.message();
                                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();                                }
                            }
                    );
                }
            }
        });
    }
}
