package cc.imorning.common.action.account

import android.util.Log
import cc.imorning.common.BuildConfig
import cc.imorning.common.CommonApp
import cc.imorning.common.constant.ResultCode
import com.orhanobut.logger.Logger
import org.jivesoftware.smackx.iqregister.AccountManager
import org.jxmpp.jid.parts.Localpart

private const val TAG = "Register"

object RegisterAction {

    private val connection = CommonApp.getTCPConnection()

    fun run(
        nickName: String,
        account: String,
        password: String
    ): ResultCode {
        if (!connection.isConnected) {
            return ResultCode.ERROR_NETWORK
        }
        val accountManager = AccountManager.getInstance(connection)
        accountManager.sensitiveOperationOverInsecureConnection(true)
        try {
            if (!accountManager.supportsAccountCreation()) {
                return ResultCode.ERROR_NOT_SUPPORT_OPERATION
            }
            val attributes = mutableMapOf<String, String>()
            attributes["nickname"] = nickName
            accountManager.createAccount(Localpart.from(account), password, attributes)
            return ResultCode.OK
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "register new user failed: ${e.localizedMessage}", e)
            }
            return ResultCode.ERROR
        }
    }

}