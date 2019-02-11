package com.lidy.demo.motion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.lidy.demo.R;
import com.lidy.demo.motion.ExampleListAdapter.ExampleViewHolder;

/**
 * @author lideyou
 */
public class ExampleListAdapter extends ListAdapter<String, ExampleViewHolder> {

    protected ExampleListAdapter() {
        super(callback);
    }

    public static final ItemCallback<String> callback = new ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return false;
        }
    };


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_text_example, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

    }

    static class ExampleViewHolder extends ViewHolder {

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
