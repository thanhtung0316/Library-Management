package com.hoptb.library_management.ui.return_book;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentReturnBinding;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;

import java.util.List;

public class ReturnFragment extends BaseFragment<FragmentReturnBinding, ReturnViewModel> implements View.OnClickListener {

    private static ReturnFragment INSTANCE;

    public static ReturnFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReturnFragment();
        }
        return INSTANCE;
    }

    private String code;
    private Reader r;

    @Override
    protected Class<ReturnViewModel> getViewModelClass() {
        return ReturnViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.edStudentCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                code = editable.toString();
                if (editable.toString().length() >= 1) {
                    viewModel.searchReader(code);
                } else {
                    binding.tvNoReader.setVisibility(View.GONE);
                    viewModel.readerMutableLiveData.postValue(null);
                }
            }
        });
        viewModel.readerMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Reader>() {
            @Override
            public void onChanged(Reader reader) {
                r = reader;
                if (reader == null) {
                    binding.tvTitleName.setVisibility(View.GONE);
                    binding.tvNoReader.setVisibility(View.VISIBLE);
                    binding.tvNoReader.setText(getString(R.string.no_reader, code));
                    binding.tvReaderName.setVisibility(View.GONE);
                    binding.btnView.setVisibility(View.GONE);
                    if (code.length() == 0) {
                        binding.tvNoReader.setVisibility(View.GONE);
                    }
                } else {
                    binding.tvNoReader.setVisibility(View.GONE);
                    binding.tvTitleName.setVisibility(View.VISIBLE);
                    binding.tvReaderName.setVisibility(View.VISIBLE);
                    binding.tvReaderName.setText(reader.getReaderName());
                    binding.btnView.setVisibility(View.VISIBLE);
                }

            }
        });
        viewModel.brList.observe(getViewLifecycleOwner(), new Observer<List<BorrowingModel>>() {
            @Override
            public void onChanged(List<BorrowingModel> borrowingModels) {
                Toast.makeText(getContext(), "SIZE: "+borrowingModels.size(), Toast.LENGTH_SHORT).show();
            }
        });



        binding.btnView.setOnClickListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_return;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnView:
                if (r != null && !r.getStudentCode().isEmpty()) {
                    viewModel.getBrList(r.getStudentCode());
                }
                break;
        }
    }
}
