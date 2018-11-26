package com.lidy.demo.motion;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lidy.demo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Motion example 实例类
 *
 * @author lideyou
 */
public class MotionExampleActivity extends AppCompatActivity {

    private RecyclerView view;

    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_list);
        view = findViewById(R.id.recyclerView);
        initData();
        initAdapter();
    }

    private void initAdapter() {
        ExampleListAdapter adapter = new ExampleListAdapter();
        adapter.submitList(mList);
        view.hasFixedSize();
        view.setNestedScrollingEnabled(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
    }

    /**
     *
     */
    private void initData() {

        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mList.add("A" + i);
        }

    }

}
