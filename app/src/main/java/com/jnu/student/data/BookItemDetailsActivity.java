package com.jnu.student.data;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import com.jnu.student.R;

public class BookItemDetailsActivity extends AppCompatActivity {

    private int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item_details);

        Intent intent=getIntent();
        if(intent!=null){

            //从Intent中获取传递的数据
            String name=intent.getStringExtra("name");
            if(null!=name){

                position =intent.getIntExtra("position",-1);

                EditText editTextItemName=findViewById(R.id.editTextItemName);
                editTextItemName.setText(name);
            }
        }

        Button button=findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                EditText editTextItemName=findViewById(R.id.editTextItemName);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                BookItemDetailsActivity.this.finish();
            }
        });
        Button myButton = findViewById(R.id.button_return);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在这里写入你想要执行的代码
                BookItemDetailsActivity.this.finish();
            }
        });
    }

}