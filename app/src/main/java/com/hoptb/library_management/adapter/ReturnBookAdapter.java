package com.hoptb.library_management.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.hoptb.library_management.R;
import com.hoptb.library_management.databinding.ItemBookBinding;
import com.hoptb.library_management.databinding.ItemBookHideOptionBinding;
import com.hoptb.library_management.model.Book;

import java.util.List;

public class ReturnBookAdapter extends RecyclerView.Adapter<ReturnBookAdapter.ReturnBookHolder> {

    private List<Book> data;
    private Context context;
    private LayoutInflater inflater;


    public ReturnBookAdapter(List<Book> data, Context context) {
        this.data = data;
        this.context = context;
        inflater  = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReturnBookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookHideOptionBinding binding = ItemBookHideOptionBinding.inflate(inflater,parent,false);
        return new ReturnBookHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnBookHolder holder, int position) {
        Book book = data.get(position);

        holder.binding.setBook(book);
        if (book.isSelected()){
            holder.binding.clMain.setBackgroundColor(Color.BLUE);
        }else {
            holder.binding.clMain.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ReturnBookHolder extends RecyclerView.ViewHolder {
        ItemBookHideOptionBinding binding;

        public ReturnBookHolder(@NonNull ItemBookHideOptionBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
        }
    }
}
