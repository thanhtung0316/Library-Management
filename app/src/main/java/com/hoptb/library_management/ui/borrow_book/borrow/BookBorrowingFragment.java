package com.hoptb.library_management.ui.borrow_book.borrow;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBookBorrowingBinding;
import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;
import com.hoptb.library_management.ui.borrow_book.BorrowingContainerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookBorrowingFragment extends BaseFragment<FragmentBookBorrowingBinding, BookBorrowingViewModel> implements View.OnClickListener {

    public static final String TAG = "BookBorrowingFragment";
    private Integer bookId;
    private Reader reader;
    private String format = "dd/MM/yyyy"; //In which you need put here
    private String rtDate;
    private String brDate;
    private Book b;
    private int amount;

    @Override
    protected Class<BookBorrowingViewModel> getViewModelClass() {
        return BookBorrowingViewModel.class;
    }

    @Override
    protected void onCreateView() {
        binding.imBack.setOnClickListener(this);
        binding.cvAdd.setOnClickListener(this);
        binding.tvBorrowDate.setOnClickListener(this);
        binding.tvReturnDate.setOnClickListener(this);
        binding.btnBr.setOnClickListener(this);

        viewModel.bookInfo.observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                b = book;
                binding.tvBookName.setText(book.getBookName());
                binding.tvTitleBookCode.setText(getString(R.string.title_book_id, String.valueOf(book.getBookId())));
                binding.tvAmount.setText(getString(R.string.title_amount, String.valueOf(book.getAmount())));
                binding.tvAuthor.setText(book.getAuthor());
                binding.tvBookType.setText(book.getBookType());
                binding.tvPublisher.setText(book.getPublisher());
            }
        });
        viewModel.insertBrStatus.observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Toast.makeText(getContext(), "Cho mượn thành công", Toast.LENGTH_SHORT).show();
                BorrowingContainerFragment fm = (BorrowingContainerFragment) getParentFragment();
                if (fm != null) {
                    fm.showFragment(fm.getBookInfoFragment());
                    fm.getBookInfoFragment().setData(amount);
                }

            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_borrowing;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        final Calendar myCalendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.imBack:
                BorrowingContainerFragment fm = (BorrowingContainerFragment) getParentFragment();
                if (fm != null) {
                    fm.showFragment(fm.getBookInfoFragment());
                }
                break;
            case R.id.cvAdd:
                BorrowingContainerFragment fragments = (BorrowingContainerFragment) getParentFragment();
                if (fragments != null) {
                    fragments.showFragment(fragments.getReaderFragment());
                }
                break;
            case R.id.tvReturnDate:
                final DatePickerDialog.OnDateSetListener returnDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        view.setMinDate(System.currentTimeMillis() - 1000);
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                        rtDate = sdf.format(myCalendar.getTime());
                        binding.tvReturnDate.setText(rtDate);
                    }

                };
                DatePickerDialog returnDateDialog = new DatePickerDialog(getContext(), returnDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                returnDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                returnDateDialog.show();


                break;
            case R.id.tvBorrowDate:

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        view.setMinDate(System.currentTimeMillis() - 1000);
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                        brDate = sdf.format(myCalendar.getTime());
                        binding.tvBorrowDate.setText(brDate);
                    }

                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;


            case R.id.btnBr:
                if (binding.tvReaderName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn độc giả", Toast.LENGTH_SHORT).show();
                } else if (binding.tvBorrowDate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày mượn", Toast.LENGTH_SHORT).show();
                } else if (binding.tvReturnDate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày trả", Toast.LENGTH_SHORT).show();
                } else if (binding.edAmount.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(binding.edAmount.getText().toString()) > 5 ) {
                    Toast.makeText(getContext(), "Mỗi độc giả chỉ được mượn tối đa 5 quyển!", Toast.LENGTH_SHORT).show();
                }else if (b.getAmount() < Integer.parseInt(binding.edAmount.getText().toString())){
                    Toast.makeText(getContext(), "Số lượng sách trong kho không còn đủ!", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (bookId != null && reader != null) {
                        BorrowingModel br = new BorrowingModel();
                        br.setBookName(b.getBookName());
                        br.setBookId(bookId);
                        br.setReaderId(reader.getReaderId());
                        br.setReaderName(reader.getReaderName());
                        amount = Integer.parseInt(binding.edAmount.getText().toString());
                        br.setAmount(amount);
                        br.setBrDate(binding.tvBorrowDate.getText().toString());
                        br.setRtDate(binding.tvReturnDate.getText().toString());

                        viewModel.insertBrRecord(br);

                    } else {
                        Toast.makeText(getContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }
    }

    public void setData(int bookId) {
        this.bookId = bookId;
        binding.tvReturnDate.setText("");
        binding.tvBorrowDate.setText("");
        binding.edAmount.setText("");
        binding.tvReaderName.setText("");

        viewModel.getBookInfo(bookId);
    }

    public void setReader(Reader reader) {
        this.reader = reader;
        binding.tvReaderName.setText(reader.getReaderName());

    }

}
