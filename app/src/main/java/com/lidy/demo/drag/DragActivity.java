package com.lidy.demo.drag;

import android.os.Bundle;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.lidy.demo.R;

/**
 * @author lideyou
 */
public class DragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drag_up_down);

    }
}
