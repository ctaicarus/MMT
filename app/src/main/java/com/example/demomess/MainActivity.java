package com.example.demomess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btndangnhap;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                if(name.isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this , "Nhập tên" , Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if(name.equals("CTA") || name.equals("Tu") || name.equals("Kien") || name.equals("Tuan")){
                        Toast toast = Toast.makeText(MainActivity.this , "Thành công" , Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(MainActivity.this , MessActivity.class);
                        intent.putExtra("key" , name);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(MainActivity.this , "Sai thông tin" , Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }
    private void anhxa(){
        editText = findViewById(R.id.editText);
        btndangnhap = findViewById(R.id.button_dangnhap);
    }
}
