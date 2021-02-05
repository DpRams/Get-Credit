package com.example.timer201;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,password;
    TextView txv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.edt_for_name);
        password = findViewById(R.id.edt_for_password);
        txv = findViewById(R.id.textView2);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    public void GotoMain()
    {
        Intent it =new Intent(this, Main2Activity.class);
        it.putExtra("name",String.valueOf(name.getText()));
        startActivity(it);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button) {
            // "=="沒用 要用"equals()"
            if (String.valueOf(password.getText()).equals("NPTUIM")) {
                GotoMain();
            }
        }
    }
}
