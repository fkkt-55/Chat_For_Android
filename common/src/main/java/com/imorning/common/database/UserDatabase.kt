package com.imorning.common.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imorning.chat.App
import com.imorning.common.constant.UserDatabaseConstant
import com.imorning.common.database.dao.UserInfoDao
import com.imorning.common.database.table.MessageEntity
import com.imorning.common.database.table.UserInfoEntity

@Database(
    entities = [UserInfoEntity::class, MessageEntity::class],
    version = 1,
    exportSchema = false
)

abstract class UserDatabase : RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

    companion object {
        private lateinit var userDatabase: UserDatabase

        fun getInstance(): UserDatabase {
            if (!this::userDatabase.isInitialized) {
                //创建的数据库的实例
                userDatabase = Room.databaseBuilder(
                    App.getContext(),
                    UserDatabase::class.java,
                    UserDatabaseConstant.DATABASE_NAME
                )
                    .build()
            }
            return userDatabase
        }
    }
}