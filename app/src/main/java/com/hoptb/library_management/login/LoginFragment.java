package com.hoptb.library_management.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hoptb.library_management.MainActivity;
import com.hoptb.library_management.R;
import com.hoptb.library_management.utils.Constant;
import com.hoptb.library_management.utils.MyShared;

public class LoginFragment extends BaseLoginFragment<LoginActivity> implements View.OnClickListener {

    private final String TAG = "LoginFragment";

    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private MyShared myShared;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myShared = new MyShared(getContext());
        edtUserName = findViewById(R.id.edt_login_user_name);
        edtPassword = findViewById(R.id.edt_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void setData(String userName, String password) {
        edtUserName.setText(userName);
        edtPassword.setText(password);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (edtUserName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ các trường", Toast.LENGTH_SHORT).show();
                } else {
                    if (myShared.get(Constant.KEY_PASSWORD).equals(edtPassword.getText().toString()) &&
                            myShared.get(Constant.KEY_USER_NAME).equals(edtUserName.getText().toString())) {
                        Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getParentActivity(), MainActivity.class);
                        intent.putExtra(Constant.KEY_USER_NAME, edtUserName.getText().toString());
                        startActivity(intent);
                        getParentActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_register:
                getParentActivity().showFragment(
                        getParentActivity().getFmRegister());
                getParentActivity().getFmRegister().clear();
                break;
        }
    }
}