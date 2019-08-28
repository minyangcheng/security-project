package com.min.app.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mUserNameEt;
    private EditText mPasswordEt;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        findViewById(R.id.btn_fill_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDataToView();
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLoginBtn();
            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = add(100, 15);
                Utils.showToast(getApplicationContext(), "count=" + count);
            }
        });

        findViewById(R.id.btn_say).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(new User[]{new User(), new User()});
                mUserNameEt.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sayHello(new User("minych", "123"));
                    }
                }, 2000);
            }
        });
    }

    private void fillDataToView() {
        User user = DataManager.getLastUser();
        mUserNameEt.setText(user.getName());
        mPasswordEt.setText(user.getPassword());
    }

    private void initViews() {
        mUserNameEt = findViewById(R.id.et_user_name);
        mPasswordEt = findViewById(R.id.et_password);
    }

    private void clickLoginBtn() {
        String userName = mUserNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        if (login(userName, password).equals("success")) {
            Utils.showToast(getApplicationContext(), "登录成功");
        } else {
            Utils.showToast(getApplicationContext(), "登录失败");
        }
    }

    public void sayHello(User array[]) {
        Utils.showToast(this, array[0] + ":" + array[1]);
    }

    public void sayHello(User user) {
        Utils.showToast(this, "hello," + user.getName());
    }

    public native String login(String userName, String password);

    public native int add(int a, int b);

}
