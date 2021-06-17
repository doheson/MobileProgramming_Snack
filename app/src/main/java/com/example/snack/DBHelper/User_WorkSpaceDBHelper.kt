package com.example.snack.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.snack.data.User_WorkSpaceData

class User_WorkSpaceDBHelper (val context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_VERSION) {
    companion object{
        val DB_Name = "userworkspacedb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lists"

        val UNAME = "uname"
        val WSNAME = "wsname"
    }

    fun insertData(d: User_WorkSpaceData):Boolean{
        val values = ContentValues()
        values.put(UNAME, d.userEmail)
        values.put(WSNAME, d.workSpaceName)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values)>0
        db.close()
        return flag
    }

    fun findData(name : String): ArrayList<User_WorkSpaceData>{
        val strsql = "select * from $TABLE_NAME where $UNAME='$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        val list = ArrayList<User_WorkSpaceData>()

        cursor.moveToFirst()
        if(cursor.count == 0) return list

        do{
            list.add(User_WorkSpaceData(cursor.getString(0), cursor.getString(1)))
        }while (cursor.moveToNext())

        cursor.close()
        db.close()
        return list
    }

    fun findUser(name : String): ArrayList<String>{
        val strsql = "select * from $TABLE_NAME where $WSNAME='$name';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        val list = ArrayList<String>()

        cursor.moveToFirst()
        if(cursor.count == 0) return list

        do{
            list.add(cursor.getString(0))
        }while (cursor.moveToNext())

        cursor.close()
        db.close()
        return list
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$UNAME text, "+
                "$WSNAME text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}