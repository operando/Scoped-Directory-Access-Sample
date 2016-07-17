package com.os.operando.scopeddirectoryaccess_sample;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String[] DIRECTORY_SELECTION = new String[]{
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_MIME_TYPE,
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StorageManager sm = getSystemService(StorageManager.class);
        StorageVolume volume = sm.getPrimaryStorageVolume();
        Intent intent = volume.createAccessIntent(Environment.DIRECTORY_PICTURES);
        startActivityForResult(intent, 0);

        for (StorageVolume storageVolume : sm.getStorageVolumes()) {
            Log.d("tag", storageVolume.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // content://com.android.externalstorage.documents/tree/primary%3APictures
        if (resultCode != RESULT_OK) {
            return;
        }
        Uri uri = data.getData();
        Log.d("tag", data.getData().toString());
        getContentResolver().takePersistableUriPermission(data.getData(),
                Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        ContentResolver contentResolver = getContentResolver();
        Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri,
                DocumentsContract.getTreeDocumentId(uri));

        try (Cursor docCursor = contentResolver
                .query(docUri, DIRECTORY_SELECTION, null, null, null)) {
            while (docCursor != null && docCursor.moveToNext()) {
                Log.d("tag", docCursor.getString(docCursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME)));
            }
        }

        try (Cursor childCursor = contentResolver
                .query(childrenUri, DIRECTORY_SELECTION, null, null, null)) {
            while (childCursor != null && childCursor.moveToNext()) {
                Log.d("tag", childCursor.getString(childCursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME)));
                Log.d("tag", childCursor.getString(childCursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_MIME_TYPE)));
            }
        }
    }
}
