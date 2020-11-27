package com.hoptb.library_management.ui.return_book;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseAdapter;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentReturnBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;

import java.util.ArrayList;
import java.util.List;

public class ReturnFragment extends BaseFragment<FragmentReturnBinding, ReturnViewModel> implements View.OnClickListener, ItemRtListener {

    private static ReturnFragment INSTANCE;

    public static ReturnFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReturnFragment();
        }
        return INSTANCE;
    }

    private String code;
    private Reader r;
    private BaseAdapter<BorrowingModel> adapter;
    private List<BorrowingModel> borrowingModelList;
    private BorrowingModel borrowingModel;
    private int amount;

    @Override
    protected Class<ReturnViewModel> getViewModelClass() {
        return ReturnViewModel.class;
    }

    @Override
    protected void onCreateView() {
        adapter = new BaseAdapter<>(getContext(), R.layout.item_rt_record);
        binding.rcRt.setAdapter(adapter);
        adapter.setListener(this);
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
                    viewModel.brList.setValue(new ArrayList<BorrowingModel>());
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
                    viewModel.brList.setValue(new ArrayList<BorrowingModel>());
                    binding.tvReaderName.setVisibility(View.GONE);
                    binding.btnView.setVisibility(View.GONE);
                    binding.clBottom.setVisibility(View.GONE);
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
                borrowingModelList = borrowingModels;

                if (borrowingModels.size() > 0) {
                    binding.clTitle.setVisibility(View.VISIBLE);
                } else {
                    binding.clTitle.setVisibility(View.GONE);

                }
                for (int i = 0; i < borrowingModelList.size(); i++) {
                    borrowingModelList.get(i).setNumericalOrder(i + 1);
                }
                adapter.setData(borrowingModelList);
            }
        });

        viewModel.updateStatus.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), "Trả sách thành công", Toast.LENGTH_SHORT).show();
                amount = Integer.parseInt(binding.edAmount.getText().toString());
                viewModel.getBrList(r.getReaderId());
                if (borrowingModel != null) {
                    viewModel.getBook(borrowingModel.getBookId());
                }
                binding.clBottom.setVisibility(View.GONE);
            }
        });

        viewModel.book.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                if (book != null) {
                    viewModel.updateBook(book.getBookId(), book.getAmount() + amount);
                }
            }
        });

        binding.btnView.setOnClickListener(this);
        binding.btnReturn.setOnClickListener(this);

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
                    viewModel.getBrList(r.getReaderId());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (borrowingModelList.size() == 0) {
                                Toast.makeText(getContext(), "Độc giả này chưa mượn cuốn sách nào!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, 500);
                }
                break;
            case R.id.btnReturn:
                if (borrowingModel != null) {
                    if (borrowingModel.getAmount() == 0) {
                        Toast.makeText(getContext(), "Độc giả " + borrowingModel.getReaderName() +
                                " đã trả hết cuốn sách này", Toast.LENGTH_SHORT).show();
                    } else if (borrowingModel.getAmount() < Integer.parseInt(binding.edAmount.getText().toString())) {
                        Toast.makeText(getContext(), "Số lượng trả vượt quá số lượng mượn!", Toast.LENGTH_SHORT).show();
                    }else if ( Integer.parseInt(binding.edAmount.getText().toString())<=0){
                        Toast.makeText(getContext(), "Vui lòng nhập số lượng hợp lệ!", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        viewModel.updateRecord(borrowingModel, Integer.parseInt(binding.edAmount.getText().toString()));
                    }
                }
                break;
        }
    }

    @Override
    public void onItemRtClick(BorrowingModel borrowingModel) {
        this.borrowingModel = borrowingModel;
        binding.clBottom.setVisibility(View.VISIBLE);
        binding.tvBookName.setText("Tên sách:\n" + borrowingModel.getBookName());
        binding.edAmount.setText(String.valueOf(borrowingModel.getAmount()));
    }
}
