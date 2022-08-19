package cc.imorning.common.utils

import android.content.Context
import android.media.*
import android.net.Uri
import cc.imorning.common.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jivesoftware.smack.packet.Message

private const val TAG = "RingUtils"

object RingUtils {

    fun playNewMessage(
        context: Context,
        type: Message.Type? = Message.Type.chat
    ) {
        var audioAttributes =
            AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_RING)
        var soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes.build())
            .build()
        val loadId: Int = when (type) {
            Message.Type.chat -> {
                soundPool.load(context, R.raw.chat_msg, 1)
            }
            else -> {
                soundPool.load(context, R.raw.group_msg, 1)
            }
        }
        soundPool.setOnLoadCompleteListener { _soundPool, _, _ ->
            _soundPool.play(loadId, 1.0f, 1.0f, 1, 0, 1.0f)
        }
        MainScope().launch(Dispatchers.IO) {
            soundPool.unload(loadId)
            soundPool.release()
            soundPool = null
            audioAttributes = null
        }
    }

    // Play system ring
    fun playSystemRingtone(context: Context?) {
        val soundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mMediaPlayer: MediaPlayer = MediaPlayer.create(context, soundUri)
        mMediaPlayer.isLooping = false
        mMediaPlayer.start()
    }
}