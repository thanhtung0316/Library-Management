package com.hoptb.library_management.ui.personal;

import android.content.Intent;
import android.view.View;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentPersonalBinding;
import com.hoptb.library_management.login.LoginActivity;

public class PersonalFragment extends BaseFragment<FragmentPersonalBinding, PersonalViewModel> implements View.OnClickListener {

    private static PersonalFragment INSTANCE;

    public static PersonalFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonalFragment();
        }
        return INSTANCE;
    }


    @Override
    protected Class<PersonalViewModel> getViewModelClass() {
        return PersonalViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.btnLogOut.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
            case R.id.tvLogout:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
