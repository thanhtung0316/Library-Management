package com.hoptb.library_management.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hoptb.library_management.R;

public class LoginActivity extends AppCompatActivity {
    private LoginFragment fmLogin = new LoginFragment();
    private RegisterFragment fmRegister = new RegisterFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        showFragment(fmLogin);
        initFragment();
        showFragment(fmLogin);
    }

//    public void showFragment(Fragment fmShow){
//        FragmentTransaction transaction =
//                getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//
//        transaction.replace(R.id.panel, fmShow);
//        transaction.commit();
//    }

    public void initFragment() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.panel, fmLogin);
        transaction.add(R.id.panel, fmRegister);
        transaction.commit();
    }

    public void showFragment(Fragment fmShow) {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        transaction.hide(fmRegister);
        transaction.hide(fmLogin);
        transaction.show(fmShow);

        transaction.commit();
    }


    public LoginFragment getFmLogin() {
        return fmLogin;
    }

    public RegisterFragment getFmRegister() {
        return fmRegister;
    }
}
