package com.hoptb.library_management.ui.borrow_book.reader;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentListReaderBinding;
import com.hoptb.library_management.model.Reader;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;

import java.util.List;

public class ReaderFragment extends BaseFragment<FragmentListReaderBinding, ReaderViewModel> implements View.OnClickListener, ItemReaderListener {
    public static final String TAG = "ReaderFragment";
    private BaseAdapter<Reader> adapter;

    @Override
    protected Class<ReaderViewModel> getViewModelClass() {
        return ReaderViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.imBack.setOnClickListener(this);
        binding.btnAdd.setOnClickListener(this);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_reader);
        binding.rcReader.setAdapter(adapter);
        adapter.setListener(this);
        viewModel.getAllReader();
        viewModel.insertStatus.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Toast.makeText(getContext(), "Thêm mới độc giả thành công!", Toast.LENGTH_SHORT).show();
                viewModel.getAllReader();
            }
        });
        viewModel.readerList.observe(getViewLifecycleOwner(), new Observer<List<Reader>>() {
            @Override
            public void onChanged(List<Reader> readers) {
                for (int i = 0; i < readers.size(); i++) {
                    readers.get(i).setNumericalOrder(i + 1);
                }
                adapter.setData(readers);

                if (readers.size() == 0) {
                    binding.tvNoRecord.setVisibility(View.VISIBLE);
                } else {
                    binding.tvNoRecord.setVisibility(View.GONE);
                }

            }
        });
        viewModel.errorMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.deleteStatus.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), "Xóa độc giả thành công!", Toast.LENGTH_SHORT).show();
                viewModel.getAllReader();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_reader;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imBack:
                BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
                if (fragments != null) {
                    fragments.showFragment(fragments.getBorrowingFragment());
                }

                break;

            case R.id.btnAdd:
                if (binding.edName.getText().toString().isEmpty() || binding.edStudentCode.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Reader reader = new Reader();
                    reader.setReaderName(binding.edName.getText().toString());
                    reader.setStudentCode(binding.edStudentCode.getText().toString());
                    viewModel.addReader(reader);
                }
                break;
        }
    }

    @Override
    public void onItemClick(Reader reader) {
        BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
        if (fragments != null) {
            fragments.getBorrowingFragment().setReader(reader);
            fragments.showFragment(fragments.getBorrowingFragment());
        }
    }

    @Override
    public void onDeleteClick(Reader reader) {
        viewModel.deleteReader(reader);
    }
}
