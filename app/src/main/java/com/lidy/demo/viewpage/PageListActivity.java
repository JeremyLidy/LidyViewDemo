package com.lidy.demo.viewpage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnScrollChangeListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.OnFlingListener;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.lidy.demo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView 简单实现 viewPage 功能
 * 实现 https://github.com/rubensousa/RecyclerViewSnap.git
 * @author lideyou
 */
public class PageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_layout);
        recyclerView = findViewById(R.id.recycler_view);
        initRecycler();
        findViewById(R.id.float_action_button).setOnClickListener(v -> {

        });
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(manager);

        recyclerView.setHasFixedSize(true);
        List<String> strs = new ArrayList<>(3);
        strs.add("1");
        strs.add("2");
        strs.add("3");

        recyclerView.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 监听数据的
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(manager);
                    assert centerView != null;
                    int pos = manager.getPosition(centerView);
                    Log.i("PageListActivity",
                            "PageListActivity onScrollStateChanged position = " + pos);
                }
            }
        });

        PageAdapter adapter = new PageAdapter(strs);
        recyclerView.setAdapter(adapter);
    }
}
