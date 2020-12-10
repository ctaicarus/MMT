package com.example.demomess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MessActivity extends AppCompatActivity {
    private DatabaseReference mData;
    EditText editText;
    Button btnsend;
    ListView lv;
    ArrayList<String>mess;
    String id; // ten dang nhap
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);
        mData = FirebaseDatabase.getInstance().getReference(); // not goc
        Intent intent = getIntent();
        id = intent.getStringExtra("key"); // nhan ten
        anhxa();
        addMess();
        nhandulieu();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = id + " : " + editText.getText().toString().trim();
                mData.child("status").setValue(s); // cap nhap du lieu len firebase
                editText.setText("");

            }
        });
 
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                profile();
            }
        });
        // xoa tin nhan
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MessActivity.this, "", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        });
        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MessActivity.this, "", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void addMess(){
        mess = new ArrayList<>();
    }
    private void anhxa(){
        lv = findViewById(R.id.listview);
        editText = findViewById(R.id.editmess);
        btnsend = findViewById(R.id.btnsend);
    }
    private void nhandulieu(){ // nhận tự nút Họ Tên
        mData.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { // dữ liệu có sự thay đổi trên firebase , lấy dữ liệu về ngay lập tức
                String newmess = dataSnapshot.getValue().toString();
                mess.add(newmess);
                ArrayAdapter adapter = new ArrayAdapter(MessActivity.this , android.R.layout.simple_expandable_list_item_1 , mess);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void profile(){ // gọi ra dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_costom);
        EditText edtName = dialog.findViewById(R.id.edit_name);
        Button btnYes , btnNo , btnPick;
        btnYes = dialog.findViewById(R.id.button_yes);
        btnNo = dialog.findViewById(R.id.button_no);
        btnPick = dialog.findViewById(R.id.button_choice);
        final ImageView imageView = dialog.findViewById(R.id.imageViewPerson);
        // bắt sự liện
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent , 123 ); // 123 mã lấy ảnh mặc định
                imageView.setImageBitmap(bitmap);


            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(bitmap);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        edtName.setText(id);
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            Uri uri = data.getData(); // chon anh
            InputStream inputStream = null; // alt + enter
            try {
                inputStream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(inputStream); // l?y d? li?u , ??a vào imageVieư


        super.onActivityResult(requestCode, resultCode, data);
    }

}
