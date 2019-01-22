package com.lidy.demo.motion;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.lidy.demo.R;
/**
 * @author lideyou
 */
public class ConstraintLayoutStatesExampleActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    boolean changed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cl_states_start);
        ConstraintLayout stateConstraintLayout = findViewById(R.id.stateConstraintLayout);
        stateConstraintLayout.loadLayoutDescription(R.xml.constraint_layout_states_example);
        Button button = findViewById(R.id.buttonBakeCake);
        button.setOnClickListener(v -> stateConstraintLayout.setState(R.id.loading, 0, 0));
        handler.postDelayed(() -> {
            stateConstraintLayout.setState(changed ? R.id.start : R.id.end, 0, 0);
            changed = !changed;
        }, 1000);
    }
}
