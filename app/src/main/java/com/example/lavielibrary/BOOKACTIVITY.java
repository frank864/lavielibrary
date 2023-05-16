package com.example.lavielibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BOOKACTIVITY extends AppCompatActivity {
    private ImageView bookimage;
    private TextView txtName,txtAuthor,txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookactivity);
        bookimage = findViewById(R.id.bookimage);
        txtName = findViewById(R.id.txtName);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtDescription = findViewById(R.id.txtDescription);

        Intent intent = getIntent();
        if (null != intent){
            int bookId = intent.getIntExtra("book_id",-1);
            if (bookId != -1){
                new GetBookById().execute(bookId);
            }
        }

    }
    private class GetBookById extends AsyncTask<Integer,Void,Book>{

        private DatabaseHelper databaseHelper;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseHelper = new DatabaseHelper(BOOKACTIVITY.this);
        }

        @Override
        protected Book doInBackground(Integer... integers) {
           try {
               SQLiteDatabase db = databaseHelper.getReadableDatabase();
               Cursor cursor = db.rawQuery("SELECT * FROM books WHERE id=?",new String[]{String.valueOf(integers[0])});
               if (null != cursor){
                   if (cursor.moveToFirst()){
                       int idIndex = cursor.getColumnIndex("id");
                       int nameIndex = cursor.getColumnIndex("name");
                       int authorIndex = cursor.getColumnIndex("author");
                       int imageurlIndex = cursor.getColumnIndex("imageurl");
                       int descriptionIndex = cursor.getColumnIndex("description");

                       Book b = new Book();
                       b.setId(cursor.getInt(idIndex));
                       b.setName(cursor.getString(nameIndex));
                       b.setAuthor(cursor.getString(authorIndex));
                       b.setImageurl(cursor.getString(imageurlIndex));
                       b.setDescription(cursor.getString(descriptionIndex));

                       cursor.close();
                       db.close();
                       return b;
                   }else {
                       db.close();
                       cursor.close();
                   }
               }else {
                   db.close();
               }
           }catch (SQLException e){
               e.printStackTrace();
           }
            return null;
        }

        @Override
        protected void onPostExecute(Book book) {
            super.onPostExecute(book);
            if (null != book){
                txtName.setText(book.getName());
                txtAuthor.setText(book.getAuthor());
                txtDescription.setText(book.getDescription());
                Glide.with(BOOKACTIVITY.this)
                        .asBitmap()
                        .load(book.getImageurl())
                        .into(bookimage);
            }
        }
    }
}