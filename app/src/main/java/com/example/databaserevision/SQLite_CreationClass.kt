package com.example.databaserevision

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLite_CreationClass(content: Context) :
    SQLiteOpenHelper(content, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "employeeDatabase"
        private val DATABASE_VERSION = 1
        private val TABLE_CONTACT = "employeeTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACT_TABLE = (
                "CREATE TABLE " + TABLE_CONTACT + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        KEY_NAME + " TEXT, " +
                        KEY_EMAIL + " TEXT" +
                        ")"
                )
        if (db != null) {
            db.execSQL(CREATE_CONTACT_TABLE)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT)
            onCreate(db)
        }
    }


    fun insertData(name: String, email: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL, email)

        val success = db.insert(TABLE_CONTACT, null, contentValues)
        db.close()

        return success
    }

    fun updateData(updateId: Int?, updateName: String, updateEmail: String) {

        val db = writableDatabase

        val contentValues = ContentValues()

        contentValues.put(KEY_NAME, updateName)
        contentValues.put(KEY_EMAIL, updateEmail)
        val whereClause = "id = ?"
        val whereArgs = arrayOf(updateId.toString())

        db.update(TABLE_CONTACT, contentValues, whereClause, whereArgs)


    }

    fun viewEmployee(): List<Model_of_DataBase_class> {

        val employeeLits: ArrayList<Model_of_DataBase_class> =
            ArrayList()

        val selectquery = "SELECT * FROM $TABLE_CONTACT"

        val db = writableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectquery, null)
        } catch (e: Exception) {
            db.execSQL(selectquery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var email: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0)
                name = cursor.getString(1)
                email = cursor.getString(2)
                val emp = Model_of_DataBase_class(id = id, name = name, email = email)
                employeeLits.add(emp)
            } while (cursor.moveToNext())
        }
        return employeeLits
    }

    fun deleteEmployee(id: Int): Int {
        val db = this.writableDatabase
        val whereClause = "$KEY_ID = ?"

        val success = db.delete(TABLE_CONTACT, whereClause, arrayOf(id.toString()))
        db.close()
        return success
    }


}
