package com.example.chengming.myapplication;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private FileHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new FileHelper(getApplicationContext());
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        List<Contact> contacts = new ArrayList<Contact>();
        try {
            helper.createSDFile("test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(name, number);
            contacts.add(contact);
            helper.writeSDFile(name + " " + number, "test.txt");
        }
        cursor.close();

        ListView listView = (ListView) findViewById(R.id.contact_list);
        listView.setAdapter(new MainActivity.ListViewAdapter(this, R.layout.layout_list_item, contacts));
    }

    private class ListViewAdapter extends ArrayAdapter<Contact> {

        //private List<Map<String, Object>> mContacts;

        public ListViewAdapter(Context context, int resource, List<Contact> contacts) {
            super(context, resource, contacts);
            // mContacts = contacts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                layout = (LinearLayout) layoutInflater.inflate(R.layout.layout_list_item, null);
            } else {
                layout = (LinearLayout) convertView;
            }
            TextView nameView = (TextView) layout.findViewById(R.id.name);
            TextView numberView = (TextView) layout.findViewById(R.id.number);
            Contact contact = getItem(position);
            nameView.setText(contact.getName());
            numberView.setText(contact.getNumber());
            return layout;
        }

    }
}
