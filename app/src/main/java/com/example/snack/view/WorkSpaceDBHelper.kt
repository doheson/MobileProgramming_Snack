package com.example.snack.view

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView

class WorkSpaceDBHelper(val context: Context ) : SQLiteOpenHelper(context, DB_Name, null, DB_VERSION) {
    companion object{
        val DB_Name = "workspacelistdb.db"
        val DB_VERSION = 1
        val TABLE_NAME = "Lists"
        val WSID = "wsid"
        val WSNAME = "wsname"
        val CNAME = "cname"
        val UNAME = "uname"
    }
    fun getList(){
        val strsql = "select * from $TABLE_NAME;"
        val db = readableDatabase
        val cursor = db.rawQuery(strsql, null)
        showList(cursor)
        cursor.close()
        db.close()
    }

    private fun showList(cursor: Cursor){
        cursor.moveToFirst()
        val attrcount = cursor.columnCount
        val activity = context as WorkSpaceAddActivity
        activity.binding.recyclerView.removeAllViewsInLayout() // 레이아웃에 있는 모든 뷰를 지움

        val tablerow = TableRow(activity)
        val rowParam = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tablerow.layoutParams = rowParam
        val viewParam = TableRow.LayoutParams(0, 200, 1f)
        for(i in 0 until attrcount){
            val textView = TextView(activity)
            textView.layoutParams = viewParam
            textView.text = cursor.getColumnName(i)
            textView.setBackgroundColor(Color.LTGRAY)
            textView.textSize = 15.0f
            textView.gravity = Gravity.CENTER
            tablerow.addView(textView)
        }
        activity.binding.recyclerView.addView(tablerow)
        // 레코드 추가 부분
        do{
            val row = TableRow(activity)
            row.layoutParams = rowParam
            for(i in 0 until attrcount){
                val textView = TextView(activity)
                textView.layoutParams = viewParam
                textView.text = cursor.getString(i)
                textView.textSize = 13.0f
                textView.gravity = Gravity.CENTER
                row.addView(textView)
            }
            activity.binding.recyclerView.addView(row)
        }while(cursor.moveToNext())
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
                "$WSID String primary key autoincrement, " +
                "$WSNAME text, "+
                "$CNAME text, "+
                "$UNAME text);"
        db!!.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "drop table if exists $TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

}