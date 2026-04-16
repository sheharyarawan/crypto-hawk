package com.example.demo.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo.Model.CryptoResponseItem

@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFav(crypto: CryptoResponseItem)

    @Delete
    suspend fun delFromFav(crypto: CryptoResponseItem)

    @Query("SELECT *FROM cryptoTable ")
     fun getFavCrypto(): LiveData<List<CryptoResponseItem>>
}