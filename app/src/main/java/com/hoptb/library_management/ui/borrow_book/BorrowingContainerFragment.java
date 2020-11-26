package com.hoptb.library_management.ui.borrow_book;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBorrowContainerBinding;
import com.hoptb.library_management.ui.borrow_book.book_info.BookInfoFragment;
import com.hoptb.library_management.ui.borrow_book.borrow.BookBorrowingFragment;
import com.hoptb.library_management.ui.borrow_book.reader.ReaderFragment;
import com.hoptb.library_management.ui.category.CategoryFragment;
import com.hoptb.library_management.ui.category.list_book.ListBookFragment;

public class BorrowingContainerFragment extends BaseFragment<FragmentBorrowContainerBinding, BorrowingContainerViewModel> {
    private static BorrowingContainerFragment INSTANCE;
    private BookInfoFragment bookInfoFragment = new BookInfoFragment();
    private BookBorrowingFragment borrowingFragment = new BookBorrowingFragment();
    private ReaderFragment readerFragment = new ReaderFragment();
    public static final String TAG = "BorrowingContainerFragm";

    public static BorrowingContainerFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BorrowingContainerFragment();
        }
        return INSTANCE;
    }

    @Override
    protected Class<BorrowingContainerViewModel> getViewModelClass() {
        return BorrowingContainerViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_borrow_container;
    }

    @Override
    public String getTitle() {
        return TAG;
    }

    @Override
    protected void onCreateView() {
        addAllFragment();
        showFragment(bookInfoFragment);
    }

    public void addAllFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.brContainer, bookInfoFragment);
        transaction.add(R.id.brContainer, borrowingFragment);
        transaction.add(R.id.brContainer, readerFragment);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fm) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.hide(bookInfoFragment);
        transaction.hide(borrowingFragment);
        transaction.hide(readerFragment);
        transaction.show(fm);
        transaction.commit();
    }

    public void hideFragment(Fragment fm) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(fm);
        transaction.commit();
    }

    public boolean isFragmentAdded(BaseFragment fm) {
        if (getChildFragmentManager().getFragments().contains(fm)) {
            return true;
        } else {
            return false;
        }
    }

    private void closeFragment(boolean reloadData) {
        CategoryFragment containerFragment = (CategoryFragment) getParentFragment();
        if (containerFragment != null) {
            containerFragment.removeFragment(this);
            containerFragment.showFragment(ListBookFragment.newInstance(), reloadData);
        }
    }

    public void popFragment(String tag) {
        getChildFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public ReaderFragment getReaderFragment() {
        return readerFragment;
    }

    public BookInfoFragment getBookInfoFragment() {
        return bookInfoFragment;
    }

    public BookBorrowingFragment getBorrowingFragment() {
        return borrowingFragment;
    }
}
