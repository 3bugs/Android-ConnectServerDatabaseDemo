package com.example.connectserverdatabasedemo;

import okhttp3.OkHttpClient;

/**
 * Created by Promlert Lovichit
 * Jan 19, 2017
 * http://www.3bugs.com/
 * https://www.facebook.com/AndroidDevBook/
 */

public class WebServices {
    // ถ้าเครื่องจำลองที่ใช้คือ Genymotion ให้เปลี่ยน IP เป็น 10.0.3.2
    private static final String BASE_URL = "http://10.0.2.2/demo/";
    private static final String ADD_DATA_URL = BASE_URL + "add_data.php";
    private static final String LOAD_DATA_URL = BASE_URL + "load_data.php";

    private static final OkHttpClient mClient = new OkHttpClient();


}
