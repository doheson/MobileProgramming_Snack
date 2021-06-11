package com.example.snack.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import com.example.snack.data.UserData
import com.example.snack.data.WorkSpaceList
import com.example.snack.view.WorkSpaceAddActivity

//data class UserData (var userNum:Int, var userId:String, var userName:String){

class UserDBHelper (val context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_VERSION) {
    companion object{
        val DB_Name = "userdb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lists"

        val USERNUM = "userNum"
        val USERID = "userId"
        val USERNAME = "userName"
    }

    fun insertUser(userData: UserData):Boolean{
        val values = ContentValues()
        values.put(USERID, userData.userId)
        values.put(USERNAME, userData.userName)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values)>0
        db.close()
        return flag
    }

    fun findUser(userId: String): UserData? {
        val strsql = "select * from $TABLE_NAME where $USERID='$userId';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        val flag = cursor.count!=0
        var temp: UserData? = null
        if(flag){
            temp= getUserData(cursor)
        }
        cursor.close()
        db.close()
        return temp
    }

    fun getUserData(cursor: Cursor):UserData{
        cursor.moveToFirst()
        return UserData(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$USERNUM integer primary key autoincrement, " +
                "$USERID text, "+
                "$USERNAME text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}