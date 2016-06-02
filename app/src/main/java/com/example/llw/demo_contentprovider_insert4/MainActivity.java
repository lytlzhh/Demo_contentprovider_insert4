package com.example.llw.demo_contentprovider_insert4;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editName;
    private EditText editNumber;
    private Button btnRead;

    private void assignViews() {
        editName = (EditText) findViewById(R.id.edit_name);
        editNumber = (EditText) findViewById(R.id.edit_number);
        btnRead = (Button) findViewById(R.id.btn_read);
        btnRead.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read:
                fun(editName.getText().toString(), editNumber.getText().toString());
                break;
        }
    }


    public void fun(String name, String number) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        //获取uri
        Uri contacturi = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);

        //解析uri
        long content_id = ContentUris.parseId(contacturi);
        contentValues.clear();
        //添加联系人
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID, content_id);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contacturi = contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();

        contentValues.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, content_id);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        contacturi = contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        contentValues.clear();
    }
}
