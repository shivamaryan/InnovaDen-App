package com.droiddanger.innovaden1712;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EventActivity extends AppCompatActivity {
    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        title = (TextView) findViewById(R.id.title);
        description  = (TextView) findViewById(R.id.description);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        title.setText(b.getString("title"));
        description.setText(b.getString("description"));
    }
}