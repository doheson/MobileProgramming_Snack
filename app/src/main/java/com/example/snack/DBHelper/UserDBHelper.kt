package com.example.snack.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.snack.data.UserIdData

// data class UserIdData (var userNum:Int, var userId:String){}
class UserDBHelper (val context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_VERSION) {
    companion object{
        val DB_Name = "userdb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lists"

        val USERNUM = "userNum"
        val USERID = "userId"
    }

    fun insertUser(userData: UserIdData):Boolean{
        val values = ContentValues()
        values.put(USERID, userData.userId)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values)>0
        db.close()
        return flag
    }

    fun findUser(userId: String): UserIdData? {
        val strsql = "select * from $TABLE_NAME where $USERID='$userId';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var temp: UserIdData? = null
        if(flag){
            temp= getUserData(cursor)
        }
        cursor.close()
        db.close()
        return temp
    }

    fun findProduct2(pname: String): ArrayList<UserIdData>{
        val strsql = "select * from $TABLE_NAME where $USERID like'$pname%';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        val list = ArrayList<UserIdData>()

        cursor.moveToFirst()
        if(cursor.count == 0) return list

        do{
            list.add(UserIdData(cursor.getInt(0), cursor.getString(1)))
        }while (cursor.moveToNext())

        cursor.close()
        db.close()
        return list
    }


    fun getUserData(cursor: Cursor):UserIdData{
        cursor.moveToFirst()
        return UserIdData(cursor.getInt(0), cursor.getString(1))
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$USERNUM integer primary key autoincrement, " +
                "$USERID text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}