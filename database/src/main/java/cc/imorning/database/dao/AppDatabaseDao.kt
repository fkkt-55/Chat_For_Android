package cc.imorning.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cc.imorning.database.table.RecentMessageEntity
import cc.imorning.database.table.UserInfoEntity
import cc.imorning.database.utils.DatabaseHelper

@Dao
interface AppDatabaseDao {

    @Query("select * from ${DatabaseHelper.TABLE_CONTACT_INFO}")
    fun getAllContact(): LiveData<List<UserInfoEntity>>

    @Query("select * from ${DatabaseHelper.TABLE_RECENT_MESSAGE}")
    suspend fun getAllRecentMessages(): List<RecentMessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(userInfoEntity: UserInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentMessage(recentMessage: RecentMessageEntity)

    @Delete
    suspend fun deleteContact(userInfoEntity: UserInfoEntity)

    @Update
    suspend fun updateContact(userInfoEntity: UserInfoEntity)

    @Query("delete from ${DatabaseHelper.TABLE_CONTACT_INFO}")
    suspend fun deleteAllContact()

    @Query("delete from ${DatabaseHelper.TABLE_RECENT_MESSAGE}")
    suspend fun deleteAllRecentMessage()

}