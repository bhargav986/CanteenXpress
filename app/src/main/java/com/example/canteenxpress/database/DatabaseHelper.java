package com.example.canteenxpress.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.canteenxpress.Models.CartHorModel;
import com.example.canteenxpress.Models.FavouriteHorModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "databasecanteen.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLENAME_CART = "Cart";
    public static final String TABLENAME_FAV = "Favourite";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Favourite (id INTEGER PRIMARY KEY, name TEXT, image INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Cart (id INTEGER PRIMARY KEY, name TEXT, image INTEGER, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Favourite");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }

    public void insertUser(SQLiteDatabase db, FavouriteHorModel favouriteHorModel) {
        ContentValues values = new ContentValues();
        values.put("name", favouriteHorModel.getName());
        values.put("image", favouriteHorModel.getImage());
        db.insert(TABLENAME_FAV, null, values);

    }

    public void insertcartdata(SQLiteDatabase db,CartHorModel cartHomodel) {
        if (cartHomodel.getName() == null || cartHomodel.getImage() == 0 || cartHomodel.getPrice() == 0) {
            throw new IllegalArgumentException("Cart item data is incomplete.");
        }
        ContentValues values = new ContentValues();
        values.put("name", cartHomodel.getName());
        values.put("image", cartHomodel.getImage());
        values.put("price", cartHomodel.getPrice());
        db.insert(TABLENAME_CART, null, values);
    }

    public boolean isUserExists(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLENAME_FAV, new String[]{"id"},
                "name=?", new String[]{userName}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public boolean isUserExistsCart(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLENAME_CART, new String[]{"id"},
                "name=?", new String[]{userName}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return exists;
    }

    public int getUserIdByName(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default ID if not found
        Cursor cursor = db.query(TABLENAME_FAV, new String[]{"id"},
                "name=?", new String[]{userName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        return userId;
    }

    public int getUserIdByNameCart(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1; // Default ID if not found
        Cursor cursor = db.query(TABLENAME_CART, new String[]{"id"},
                "name=?", new String[]{userName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
        }
        return userId;
    }

    public void updateUser(int id, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        db.update(TABLENAME_FAV, values, "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteUser(SQLiteDatabase db, int id) {
        db.delete(TABLENAME_FAV, "id=?", new String[]{String.valueOf(id)});
    }

    public void deleteUserCart(SQLiteDatabase db, int id) {
        db.delete(TABLENAME_CART, "id=?", new String[]{String.valueOf(id)});
    }


    public ArrayList<FavouriteHorModel> getAllfavourite() {
        ArrayList<FavouriteHorModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLENAME_FAV, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                FavouriteHorModel user = new FavouriteHorModel();
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return userList;
    }

    public ArrayList<CartHorModel> getAllCart() {
        ArrayList<CartHorModel> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLENAME_CART, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                CartHorModel user = new CartHorModel();
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                user.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                userList.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return userList;
    }
}
