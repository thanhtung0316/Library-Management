package com.hoptb.library_management.ui.borrow_book;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hoptb.library_management.R;
import com.hoptb.library_management.base.BaseFragment;
import com.hoptb.library_management.databinding.FragmentBorrowContainerBinding;
import com.hoptb.library_management.ui.borrow_book.book_info.BookInfoFragment;
import com.hoptb.library_management.ui.borrow_book.borrow.BookBorrowingFragment;

public class BorrowingContainerFragment extends BaseFragment<FragmentBorrowContainerBinding, BorrowingContainerViewModel> {
    private static BorrowingContainerFragment INSTANCE;
    private BookInfoFragment bookInfoFragment;
    private BookBorrowingFragment borrowingFragment;
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
        addFragment(BookInfoFragment.newInstance());
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.brContainer, fragment);
        for (Fragment fm : getChildFragmentManager().getFragments()) {
            transaction.hide(fm);
        }
        transaction.show(fragment);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public void showFragment(Fragment fm) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(BookBorrowingFragment.newInstance());
        transaction.hide(BookInfoFragment.newInstance());
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
}
