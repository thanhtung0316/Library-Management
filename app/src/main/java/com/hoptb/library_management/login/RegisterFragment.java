package com.hoptb.library_management.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hoptb.library_management.R;
import com.hoptb.library_management.utils.Constant;
import com.hoptb.library_management.utils.MyShared;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "RegisterFragment";

    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtRePassword;
    private Button btnOk;
    private MyShared myShared;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register,
                container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        myShared = new MyShared(getContext());
        super.onActivityCreated(savedInstanceState);
        edtPassword = getActivity().findViewById(R.id.edt_register_password);
        edtUserName = getActivity().findViewById(R.id.edt_register_user_name);
        edtRePassword = getActivity().findViewById(R.id.edt_re_password);
        btnOk = getActivity().findViewById(R.id.btn_register_ok);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_ok:
                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();

                if (password.isEmpty() || userName.isEmpty() || edtRePassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ các trường!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(edtRePassword.getText().toString())) {
                    Toast.makeText(getContext(), "Mật khẩu không trùng khớp, vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity main = (LoginActivity) getActivity();

                    if (myShared.get(Constant.KEY_USER_NAME).equals(userName)) {
                        Toast.makeText(getContext(), "Tài khoản đã tồn tại, vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                        main.showFragment(main.getFmLogin());
                    } else {
                        myShared.put(Constant.KEY_USER_NAME, userName);
                        myShared.put(Constant.KEY_PASSWORD, password);
                        main.getFmLogin().setData(userName, password);
                        main.showFragment(main.getFmLogin());
                        Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    }


                }
                break;
        }


    }

    public void clear() {
        edtPassword.setText("");
        edtUserName.setText("");
        edtRePassword.setText("");
    }
}