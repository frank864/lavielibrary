package com.example.lavielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new BookAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        new GetAllBooks().execute();
    }
    private class GetAllBooks extends AsyncTask<Void,Void, ArrayList<Book>>{
        private DatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(MainActivity.this);
        }

        @Override
        protected ArrayList<Book> doInBackground(Void... voids) {
            try {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                Cursor cursor = db.query("books",null,null,null,null,null,null);
                if (null != cursor){
                    if (cursor.moveToFirst()){
                        ArrayList<Book> books= new ArrayList<>();

                        int idIndex = cursor.getColumnIndex("id");
                        int nameIndex = cursor.getColumnIndex("name");
                        int authorIndex = cursor.getColumnIndex("author");
                        int imageurlIndex = cursor.getColumnIndex("imageurl");
                        int descriptionIndex = cursor.getColumnIndex("description");

                        for (int i =0; i<cursor.getCount(); i++){
                            Book b = new Book();
                            b.setId(cursor.getInt(idIndex));
                            b.setName(cursor.getString(nameIndex));
                            b.setAuthor(cursor.getString(authorIndex));
                            b.setImageurl(cursor.getString(imageurlIndex));
                            b.setDescription(cursor.getString(descriptionIndex));

                            books.add(b);

                            cursor.moveToNext();
                        }
                        cursor.close();
                        db.close();
                        return books;
                    }else{
                        cursor.close();
                        db.close();
                    }
                }else{
                    db.close();
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            super.onPostExecute(books);
            if (null != books){
                adapter.setBooks(books);
            }else{
                adapter.setBooks(new ArrayList<Book>());
            }
        }
    }

}