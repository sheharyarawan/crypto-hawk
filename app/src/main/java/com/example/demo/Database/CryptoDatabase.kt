package com.example.demo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo.Model.CryptoResponseItem

@Database(entities = [CryptoResponseItem::class], version = 1)
abstract class CryptoDatabase: RoomDatabase(){

    abstract fun getCryptoDao(): CryptoDao

    companion object{

        @Volatile
        var instance: CryptoDatabase?=null
        var LOCK=Any()

        operator fun invoke(context: Context)=instance?:
        synchronized(LOCK){
            instance?:createDb(context).also{
                instance=it
            }
        }

        fun createDb(context: Context)= Room.databaseBuilder(
            context.applicationContext,
            CryptoDatabase::class.java,
            name= "CryptoDB"

        ).build()
    }


}