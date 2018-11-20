package com.example.ipca02.pantry.Activitys;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipca02.pantry.Adapters.CursorAdapter_Produtos_Adicionados;
import com.example.ipca02.pantry.Database.DatabaseContract;
import com.example.ipca02.pantry.Database.DatabaseHelper;
import com.example.ipca02.pantry.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IPCA02 on 20-06-2017.
 */

public class SMS_Numbers extends FragmentActivity {
    // The ListView

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    String phoneNo;
    String message;
    Button sendBtn;
    String idlista;
    TextView textView;
    String mensagem = "";
    SQLiteDatabase db;
    DatabaseHelper db_h;
    private static final int PICK_CONTACT = 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_numbers);
        // Find the list view
        //this.lstNames = (ListView) findViewById(R.id.lstNames);

        db_h = new DatabaseHelper(this);
        db = db_h.getWritableDatabase();
        textView = (TextView) findViewById(R.id.textView_numero);

        idlista = getIntent().getStringExtra("ID_DESCONTO");
        Log.d("idlista", idlista);

        sendBtn = (Button) findViewById(R.id.btnSendSms);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
        // Read and show the contacts
        showContacts();
    }

    public String selectAllSupermercados(String id_supermecado) {

        String supermercado = null;
        Cursor cursor;
        String sql = "SELECT *FROM " + DatabaseContract.Supermercado.TB_NAME_SUPERMERCADO + " WHERE " + DatabaseContract.Supermercado.COL_SUPERMERCADO_ID + " = " + id_supermecado + ";";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            do {

                supermercado = cursor.getString(cursor.getColumnIndex(DatabaseContract.Supermercado.COL_SUPERMERCADO_NOME));
                //categorias.setCategorias_id(cursor.getInt(cursor.getColumnIndex(DatabaseContract.Categorias.COL_CATEGORIA_ID)));
            } while (cursor.moveToNext());
        }
        return supermercado;
    }

    public String myUpdateOperation() {
        DatabaseHelper db_h = new DatabaseHelper(this);
        final SQLiteDatabase db = db_h.getWritableDatabase();
        String sql = "SELECT * FROM " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO + " WHERE " + DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID + " = " + idlista + ";";
        Cursor todoCursor = db.rawQuery(sql, null);

        if (todoCursor.moveToFirst()) {
            do {

                String sql2 = "SELECT * FROM " + DatabaseContract.CampanhasDesconto.TB_NAME_CAMPANHASDESCONTO + " WHERE " + DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID + " = " + todoCursor.getString(todoCursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_CAMPANHAS_DESCONTO_ID)) + ";";

                Cursor cursor = db.rawQuery(sql2, null);
                cursor.moveToFirst();

                String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_SUPERMERCADO_ID));
                String supermercados = selectAllSupermercados(id);

                mensagem += "Desconto: " + cursor.getString(cursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_DESCONTO)) + System.getProperty("line.separator") + "Descrição: " + todoCursor.getString(todoCursor.getColumnIndex(DatabaseContract.CampanhasDesconto.COL_DESCRICAO)) + System.getProperty("line.separator") + "Supermercado: " + supermercados;
                cursor.close();
            } while (todoCursor.moveToNext());

            todoCursor.close();
            db.close();

        }


        return mensagem;
        //Toast.makeText(Lista_Produtos_Adicionado.this, nome_lista, Toast.LENGTH_SHORT).show();
    }

    public void pickAContactNumber(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                ContentResolver cr = getContentResolver();
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor phone = getContentResolver().query(contactData, null, null, null, null);
                    if (phone.moveToFirst()) {

                        String contactNumberName = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // Todo something when contact number selected
                        String contactId = phone.getString(phone.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
                            textView.setText(number);
                        }
                    }

                }
        }
    }

    /**
     * Show the contacts in the ListView.
     */
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            //List<String> contacts = getContactNames();
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            //lstNames.setAdapter(adapter);

            /*
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Toast.makeText(SMS_Numbers.this, "Name: " + name
                                    + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        }
                        pCur.close();
                    }
                }
            }*/
        }
    }

    public void sendSMSMessage() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                MY_PERMISSIONS_REQUEST_SEND_SMS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {

            case PERMISSIONS_REQUEST_READ_CONTACTS: {


                if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission is granted
                        showContacts();
                    } else {
                        Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                mensagem = myUpdateOperation();

                if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        SmsManager smsManager = SmsManager.getDefault();
                        phoneNo = textView.getText().toString();

                        smsManager.sendTextMessage(phoneNo, null, mensagem, null, null);
                        Toast.makeText(getApplicationContext(), "SMS enviado.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "SMS falhou, por favor tente novamente.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }


    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                /*String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                while (phones.moveToNext()) {
                    int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
                }*/
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }
}