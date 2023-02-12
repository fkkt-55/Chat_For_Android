package cc.imorning.chat.ui.view

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun showMessage(context: Context, message: String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, length).show()
    }

    fun showMessage(context: Context, messageId: Int, length: Int = Toast.LENGTH_LONG) {
        showMessage(context, context.getString(messageId), length)
    }
}