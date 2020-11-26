package com.hoptb.library_management.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hoptb.library_management.model.Book;
import com.hoptb.library_management.model.BorrowingModel;
import com.hoptb.library_management.model.Reader;

import java.util.ArrayList;
import java.util.List;

public class LibraryManagementOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "LibraryManagementOpenHe";
    private static final String DATABASE_NAME = "libraryManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Book Table
    private static final String BOOK_TABLE_NAME = "book";
    private static final String BOOK_ID = "bookId";
    private static final String AMOUNT = "amount";
    private static final String BOOK_NAME = "name";
    private static final String BOOK_TYPE = "bookType";
    private static final String PUBLISHER = "publisher";
    private static final String AUTHOR = "author";
    private static final String IMAGE = "image";
    private static final String DESCRIPTION = "description";

    // Reader Table
    private static final String READER_TABLE_NAME = "reader";
    private static final String READER_ID = "readerId";
    private static final String READER_NAME = "readerName";
    private static final String STUDENT_CODE = "studentCode";

    // Borrowing  Table
    private static final String ID_BR = "id_br";
    private static final String BR_TABLE_NAME = "borrowing";
    private static final String BR_DATE = "borrowing";
    private static final String RT_DATE = "returnDate";


    public LibraryManagementOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBookTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s TEXT, %s TEXT,%s TEXT,%s TEXT,%s TEXT)",
                BOOK_TABLE_NAME, BOOK_ID, BOOK_NAME, AMOUNT, BOOK_TYPE, PUBLISHER, AUTHOR, IMAGE, DESCRIPTION);

        String createReaderTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)",
                READER_TABLE_NAME, READER_ID, STUDENT_CODE, READER_NAME);

        String createBrTable = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                BR_TABLE_NAME, ID_BR, BOOK_ID, READER_ID, AMOUNT, BR_DATE, RT_DATE, READER_NAME,BOOK_NAME);

        db.execSQL(createBookTable);
        db.execSQL(createReaderTable);
        db.execSQL(createBrTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropBookTable = String.format("DROP TABLE IF EXISTS %s", BOOK_TABLE_NAME);
        String dropReaderTable = String.format("DROP TABLE IF EXISTS %s", READER_TABLE_NAME);
        String dropBrTable = String.format("DROP TABLE IF EXISTS %s", BR_TABLE_NAME);
        db.execSQL(dropBookTable);
        db.execSQL(dropReaderTable);
        db.execSQL(dropBrTable);
        onCreate(db);
    }


    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + BOOK_TABLE_NAME + " ORDER BY " + " LOWER(" + BOOK_NAME + ")";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setAmount(cursor.getInt(2));
            book.setBookType(cursor.getString(3));
            book.setPublisher(cursor.getString(4));
            book.setAuthor(cursor.getString(5));
            book.setImage(cursor.getString(6));
            book.setDescription(cursor.getString(7));
            bookList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }


    public long insertBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, book.getAmount());
        values.put(BOOK_NAME, book.getBookName());
        values.put(BOOK_TYPE, book.getBookType());
        values.put(AUTHOR, book.getAuthor());
        values.put(PUBLISHER, book.getPublisher());
        values.put(IMAGE, book.getImage());
        values.put(DESCRIPTION, book.getDescription());
        long rowInserted = database.insert(BOOK_TABLE_NAME, null, values);
        database.close();
        return rowInserted;
    }

    public int deleteBook(int bookId) {
        SQLiteDatabase database = getWritableDatabase();
        int deleteStatus = database.delete(BOOK_TABLE_NAME, BOOK_ID + " = ?",
                new String[]{String.valueOf(bookId)});
        database.close();
        return deleteStatus;
    }

    public Book selectBook(int bookId) {
        Book book = new Book();
        String query = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE " + BOOK_ID + " = " + bookId;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setAmount(cursor.getInt(2));
            book.setBookType(cursor.getString(3));
            book.setPublisher(cursor.getString(4));
            book.setAuthor(cursor.getString(5));
            book.setImage(cursor.getString(6));
            book.setDescription(cursor.getString(7));
            cursor.close();
        }
        database.close();
        return book;
    }

    public int updateBook(Book book) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, book.getAmount());
        values.put(BOOK_NAME, book.getBookName());
        values.put(BOOK_TYPE, book.getBookType());
        values.put(AUTHOR, book.getAuthor());
        values.put(PUBLISHER, book.getPublisher());
        values.put(IMAGE, book.getImage());
        values.put(DESCRIPTION, book.getDescription());
        int updateStatus = database.update(BOOK_TABLE_NAME, values, BOOK_ID + " = "
                + book.getBookId(), null);
        database.close();
        return updateStatus;
    }

    public List<Book> search(String keySearch) {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM " + BOOK_TABLE_NAME + " WHERE " + BOOK_NAME + " LIKE " + "'"
                + keySearch + "%'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = new Book();
            book.setBookId(cursor.getInt(0));
            book.setBookName(cursor.getString(1));
            book.setAmount(cursor.getInt(2));
            book.setBookType(cursor.getString(3));
            book.setPublisher(cursor.getString(4));
            book.setAuthor(cursor.getString(5));
            book.setImage(cursor.getString(6));
            book.setDescription(cursor.getString(7));
            bookList.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return bookList;
    }


    public List<BorrowingModel> getBrListThroughBookId(String bookId) {
        List<BorrowingModel> brList = new ArrayList<>();
        String query = "SELECT * FROM " + BR_TABLE_NAME + " WHERE " + BOOK_ID + " = " + "'"
                + bookId + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BorrowingModel br = new BorrowingModel();
                br.setBrId(cursor.getInt(0));
                br.setBookId(cursor.getInt(1));
                br.setReaderId(cursor.getInt(2));
                br.setAmount(cursor.getInt(3));
                br.setBrDate(cursor.getString(4));
                br.setRtDate(cursor.getString(5));
                br.setReaderName(cursor.getString(6));
                br.setBookName(cursor.getString(7));
                brList.add(br);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return brList;
    }

    public List<BorrowingModel> getBrListThroughCode(String code) {
        List<BorrowingModel> brList = new ArrayList<>();
        String query = "SELECT * FROM " + BR_TABLE_NAME + " WHERE " + READER_ID + " = " + "'"
                + code + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BorrowingModel br = new BorrowingModel();
                br.setBrId(cursor.getInt(0));
                br.setBookId(cursor.getInt(1));
                br.setReaderId(cursor.getInt(2));
                br.setAmount(cursor.getInt(3));
                br.setBrDate(cursor.getString(4));
                br.setRtDate(cursor.getString(5));
                br.setReaderName(cursor.getString(6));
                br.setBookName(cursor.getString(7));
                brList.add(br);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return brList;
    }

    public Reader selectReader(String code) {

        String query = "SELECT * FROM " + READER_TABLE_NAME + " WHERE " + STUDENT_CODE + " = " + code;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            Reader reader = new Reader();
            reader.setReaderId(cursor.getInt(0));
            reader.setStudentCode(cursor.getString(1));
            reader.setReaderName(cursor.getString(2));
            cursor.close();
            return reader;
        }
        database.close();
        return null;
    }

    public long insertReader(Reader reader) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(READER_NAME, reader.getReaderName());
        values.put(STUDENT_CODE, reader.getStudentCode());
        long rowInserted = database.insert(READER_TABLE_NAME, null, values);
        database.close();
        return rowInserted;
    }

    public int deleteReader(int readerId) {
        SQLiteDatabase database = getWritableDatabase();
        int deleteStatus = database.delete(READER_TABLE_NAME, READER_ID + " = ?",
                new String[]{String.valueOf(readerId)});
        database.close();
        return deleteStatus;
    }

    public List<Reader> getAllReaders() {
        List<Reader> readerList = new ArrayList<>();
        String query = "SELECT * FROM " + READER_TABLE_NAME + " ORDER BY " + " LOWER(" + READER_NAME + ")";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reader reader = new Reader();
            reader.setReaderId(cursor.getInt(0));
            reader.setStudentCode(cursor.getString(1));
            reader.setReaderName(cursor.getString(2));
            readerList.add(reader);
            cursor.moveToNext();
        }
        cursor.close();
        return readerList;
    }


    public long insertBorrowingRecord(BorrowingModel br) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT, br.getAmount());
        values.put(BOOK_ID, br.getBookId());
        values.put(READER_ID, br.getReaderId());
        values.put(BOOK_NAME, br.getBookName());
        values.put(READER_NAME, br.getReaderName());
        values.put(BR_DATE, br.getBrDate());
        values.put(RT_DATE, br.getRtDate());
        long rowInserted = database.insert(BR_TABLE_NAME, null, values);
        database.close();
        return rowInserted;
    }

    public List<BorrowingModel> getAllBrRecords() {
        List<BorrowingModel> brList = new ArrayList<>();
        String query = "SELECT * FROM " + BR_TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BorrowingModel br = new BorrowingModel();
                br.setBrId(cursor.getInt(0));
                br.setBookId(cursor.getInt(1));
                br.setReaderId(cursor.getInt(2));
                br.setAmount(cursor.getInt(3));
                br.setBrDate(cursor.getString(4));
                br.setRtDate(cursor.getString(5));
                br.setReaderName(cursor.getString(6));
                brList.add(br);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return brList;
    }

}
