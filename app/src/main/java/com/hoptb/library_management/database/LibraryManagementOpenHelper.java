package com.hoptb.library_management.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hoptb.library_management.model.Book;

import java.util.ArrayList;
import java.util.List;

public class LibraryManagementOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "libraryManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Book Table
    private static final String BOOK_TABLE_NAME = "book";
    private static final String BOOK_ID = "bookId";
    private static final String BOOK_NAME = "name";
    private static final String BOOK_TYPE = "bookType";
    private static final String PUBLISHER = "publisher";
    private static final String AUTHOR = "author";
    private static final String IMAGE = "image";

    // Reader Table:
    private static final String READER_TABLE_NAME = "reader";
    private static final String READER_ID = "readerId";
    private static final String READER_NAME = "readerName";
    private static final String STUDENT_CODE = "studentCode";


    public LibraryManagementOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBookTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT,%s TEXT,%s TEXT)",
                BOOK_TABLE_NAME, BOOK_ID, BOOK_NAME, BOOK_TYPE, PUBLISHER, AUTHOR, IMAGE);

        String createReaderTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                READER_TABLE_NAME, READER_ID, READER_NAME, STUDENT_CODE);

        db.execSQL(createBookTable);
        db.execSQL(createReaderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropBookTable = String.format("DROP TABLE IF EXISTS %s", BOOK_TABLE_NAME);
        String dropReaderTable = String.format("DROP TABLE IF EXISTS %s", READER_TABLE_NAME);
        db.execSQL(dropBookTable);
        db.execSQL(dropReaderTable);
        onCreate(db);
    }


    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + BOOK_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setBookType(cursor.getString(2));
            book.setPublisher(cursor.getString(3));
            book.setAuthor(cursor.getString(4));
            book.setImage(cursor.getString(5));
            bookList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }


}
