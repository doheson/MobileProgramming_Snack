package com.example.snack.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.snack.data.WorkSpaceList

//data class WorkSpaceList(var wsId : Int, var wsName: String, var cName: String) {}
class WorkSpaceDBHelper(val context: Context ) : SQLiteOpenHelper(context, DB_Name, null, DB_VERSION) {
    companion object{
        val DB_Name = "workspacelistdb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lists"

        val WSID = "wsid"
        val WSNAME = "wsname"
        val CNAME = "cname"
    }

    fun findChannel(workName : String): ArrayList<String>{
        val strsql = "select * from $TABLE_NAME where $WSNAME='$workName';"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)

        val list = ArrayList<String>()

        cursor.moveToFirst()
        if(cursor.count == 0) return list

        do{
            list.add(cursor.getString(2))
        }while (cursor.moveToNext())

        cursor.close()
        db.close()
        return list
    }

    fun insertName(workspace: WorkSpaceList):Boolean{
        val values = ContentValues()
        values.put(WSNAME, workspace.wsName)
        values.put(CNAME, workspace.cName)
        val db = writableDatabase
        val flag = db.insert(TABLE_NAME, null, values)>0
        db.close()
        return flag
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "create table if not exists $TABLE_NAME("+
                "$WSID integer primary key autoincrement, " +
                "$WSNAME text, "+
                "$CNAME text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}