package com.hoptb.library_management.ui.personal;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentPersonalBinding;

public class PersonalFragment extends BaseFragment<FragmentPersonalBinding, PersonalViewModel> {

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

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
