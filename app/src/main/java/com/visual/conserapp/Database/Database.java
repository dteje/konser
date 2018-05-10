package com.visual.conserapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.visual.conserapp.Model.Order;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


/**
 * Created by daniel on 26/04/2018.
 */

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "conserapp.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ID", "ProductName", "ProductID", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> res = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                res.add(new Order(
                        c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            } while (c.moveToNext());
        }
        return res;

    }

    public void addToCart(Order order) {

        Cursor pos = getOrderPositionInDatabase(order.getProductID());
        SQLiteDatabase db = getReadableDatabase();
        String query;
        if (pos.getPosition() == -1) {
            query = String.format("INSERT INTO OrderDetail(ProductID,ProductName,Quantity,Price,Discount) VALUES('%s','%s','%s','%s','%s');",
                    order.getProductID(), order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());
        } else {
            //pos.moveToFirst();
            pos.moveToFirst();
            int quantity = parseInt(pos.getString(pos.getColumnIndex("Quantity")));
            int id = pos.getInt(pos.getColumnIndex("ID"));
            quantity += parseInt(order.getQuantity());
            query = String.format("UPDATE OrderDetail SET Quantity= %s WHERE ID = %d", quantity, id);
        }
        db.execSQL(query);
    }

    public void cleanCart() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }

    public void updateCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("UPDATE OrderDetail SET Quantity= %s WHERE ID = %d", order.getQuantity(), order.getID());
        db.execSQL(query);
    }

    public void removeFromCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = "DELETE FROM OrderDetail WHERE ID =" + order.getID();
        db.execSQL(query);
    }

    public Cursor getOrderPositionInDatabase(String productid) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        try {
            String query = String.format("SELECT * FROM OrderDetail WHERE ProductId = " + productid);
            c = db.rawQuery(query, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}

