package com.jnu.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RelativeLayout relativeLayout = new RelativeLayout(this);
        //RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(
        //        RelativeLayout.LayoutParams.WRAP_CONTENT,
        //        RelativeLayout.LayoutParams.WRAP_CONTENT);
        //params.addRule(RelativeLayout.CENTER_IN_PARENT);  //设置布局中的控件居中显示
        //TextView textView = new TextView(this);                       //创建TextView控件
        //textView.setText(R.string.Hello_world);                     //设置TextView的文字内容
        //textView.setTextColor(Color.RED);                                  //设置TextView的文字颜色
        //textView.setTextSize(18);                                                //设置TextView的文字大小
        //relativeLayout.addView(textView, params);                  //添加TextView对象和TextView的布局属性
        //setContentView(relativeLayout);                            //设置在Activity中显示RelativeLayout



        //TextView textView = findViewById(R.id.text_view_hello_world);
        //String message = getString(R.string.hello_android);
        //textView.setText(message);


        //XML 中使用 onClick 属性：
        //在 XML 布局文件中，为按钮添加 onClick 属性，并指定一个对应的方法名来响应点击事件。
        final TextView textView1 = findViewById(R.id.textView1);
        final TextView textView2 = findViewById(R.id.textView2);
        Button btnChange = findViewById(R.id.btnChange);
        btnChange.setOnClickListener(v -> {
            String tempText = textView1.getText().toString();
            textView1.setText(textView2.getText().toString());
            textView2.setText(tempText);
            Toast.makeText(MainActivity.this, "交换成功", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("交换成功");
            builder.setMessage("文本已成功交换");
            builder.setPositiveButton("确定", (dialog, which) -> {
                // 可选的确定按钮点击事件
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });


        //这里让该文件实现 View.OnClickListener 接口，并在 onClick() 方法中编写按钮点击事件的处理代码。
        //Button button = findViewById(R.id.switchToNextActivity);
        //button.setOnClickListener(this);


        //
    }

    //public void onClick(View view) {
    //    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
    //    startActivity(intent);
    //}
}