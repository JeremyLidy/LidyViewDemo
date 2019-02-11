package com.lidy.demo.viewpage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.lidy.demo.R;
import com.lidy.demo.viewpage.PageAdapter.PageViewHolder;
import java.util.List;

/**
 * PageAdapter 数据适配
 *
 * @author lideyou
 */
public class PageAdapter extends RecyclerView.Adapter<PageViewHolder> {

    public List<String> mStrings;

    public PageAdapter(List<String> mStrings) {
        this.mStrings = mStrings;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new PageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_page_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        holder.mTextView.setText(String.format("TextView = %s", mStrings.get(position)));
    }


    @Override
    public int getItemCount() {
        return mStrings == null ? 0 : mStrings.size();
    }

    public static class PageViewHolder extends ViewHolder {

        TextView mTextView;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
        }
    }
}
