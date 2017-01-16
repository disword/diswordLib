package com.disword.diswordlib.demo.pages.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.disword.diswordlib.R;
import com.disword.diswordlib.core.base.BaseActivity;
import com.disword.diswordlib.core.util.keyboard.KeyboardCallback;
import com.disword.diswordlib.core.util.keyboard.KeyboardListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.etUsername)
    EditText etUsername;
    @InjectView(R.id.etPassword)
    EditText etPassword;
    @InjectView(R.id.btnLogin)
    Button btnLogin;
    @InjectView(R.id.root)
    RelativeLayout root;
    @InjectView(R.id.textView)
    TextView textView;

    private KeyboardListener keyboardListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        /**
         * 防止键盘遮挡 隐藏标题
         * activity xml要设置属性 android:windowSoftInputMode="adjustResize|stateHidden"
         */
        keyboardListener = new KeyboardListener(root, new KeyboardCallback() {
            @Override
            public void keyboardShown() {
                textView.setVisibility(View.GONE);
            }

            @Override
            public void keyboardHidden() {
                textView.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            showToast("请输入用户名");return;
        }else if(TextUtils.isEmpty(password)){
            showToast("请输入密码");return;
        }

        System.out.println("准备登陆");
    }

    @Override
    protected void httpCallback(String result, int code, int taskId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(keyboardListener != null)
            keyboardListener.release();
    }
}
