package com.lidy.demo.viewpage;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.lidy.demo.R;

/**
 * @author lideyou
 */
public class TwoPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_pager_layout);
    }
}
