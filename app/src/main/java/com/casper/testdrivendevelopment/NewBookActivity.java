package com.casper.testdrivendevelopment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewBookActivity extends AppCompatActivity {

    private Button buttonOK,buttonCancel;
    private EditText editTextBookTitle,editTextBookPrice;
    private int editPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        buttonOK=(Button)findViewById(R.id.button_ok);
        buttonCancel=(Button)findViewById(R.id.button_cancel);
        editTextBookTitle=(EditText)findViewById(R.id.edit_text_book_title);
        editTextBookPrice=(EditText)findViewById(R.id.edit_text_book_price);
        editTextBookTitle.setText(getIntent().getStringExtra("title"));

        editPosition=getIntent().getIntExtra("insert_position",0);

        double bookPrice=getIntent().getDoubleExtra("price",-1);
        String bookTitle= getIntent().getStringExtra("title");
        if(bookTitle!=null) {
            editTextBookTitle.setText(bookTitle);
            editTextBookPrice.setText(bookPrice+"");
        }

        buttonOK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                intent.putExtra("insert_position", editPosition);
                intent.putExtra("title",editTextBookTitle.getText().toString().trim());
                intent.putExtra("price",editTextBookPrice.getText().toString().trim());
                setResult(RESULT_OK,intent);
                NewBookActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NewBookActivity.this.finish();
            }
        });
    }
}
