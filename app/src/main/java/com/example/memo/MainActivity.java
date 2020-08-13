package com.example.memo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.MemoryHandler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HttpAsyncTask().execute("https://goo.gl/eIXu9l");
//        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(MainActivity.this, MemoActivity.class), REQUEST_CODE_INSERT);
//            }
//        });
//
//        ListView listView = findViewById(R.id.memo_list);
//
//        mAdapter = new MemoAdapter(this, this.getMemoCursor());
//        listView.setAdapter(mAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
//
//                Cursor cursor = (Cursor) mAdapter.getItem(position);
//
//                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
//                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS));
//
//                intent.putExtra("id", id);
//                intent.putExtra("title", title);
//                intent.putExtra("contents", contents);
//
//                startActivityForResult(intent, REQUEST_CODE_INSERT);
//            }
//        });
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final long deleteId = id;
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("메모삭제");
//                builder.setMessage("메모를 삭제하시겠습니까");
//                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        SQLiteDatabase db = MemoDbHelper.getInstance(MainActivity.this).getWritableDatabase();
//                        int deletedCount = db.delete(MemoContract.MemoEntry.TABLE_NAME,
//                                MemoContract.MemoEntry._ID + " = " + deleteId, null);
//
//                        if(deletedCount == 0) {
//                            Toast.makeText(MainActivity.this, "삭제에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
//                        } else {
//                            mAdapter.swapCursor(getMemoCursor());
//                            Toast.makeText(MainActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                builder.setNegativeButton("취소", null);
//                builder.show();
//                return true;
//            }
//        });
    }

    private static class HttpAsyncTask extends AsyncTask<String, Void, String>{
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            ArrayList<Weather> weatherList = new ArrayList<>();
            String result = null;
            String strUrl = strings[0];

            try{
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();

                Response response = client.newCall(request).execute();
                Gson gson = new Gson();

                Type listType = new TypeToken<ArrayList<Weather>>(){}.getType();

                weatherList = gson.fromJson(response.body().string(), listType);

//                JSONArray jsonArray = new JSONArray(response.body().string());
//                for(int i = 0; i< jsonArray.length(); i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String country = jsonObject.getString("country");
//                    String weather = jsonObject.getString("weather");
//                    String temperature = jsonObject.getString("temperature");
//                    Weather w = new Weather(country, weather, temperature);
//                    weatherList.add(w);
//                }
                Log.d("@@", weatherList.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                Log.d("@@", s);
            }
        }
    }


    private Cursor getMemoCursor(){
        MemoDbHelper dbHelper = MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase()
                .query(MemoContract.MemoEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_INSERT && resultCode == RESULT_OK){
            mAdapter.swapCursor(this.getMemoCursor());
        }
    }

    private static class MemoAdapter extends CursorAdapter {

        public MemoAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
        }
    }

}