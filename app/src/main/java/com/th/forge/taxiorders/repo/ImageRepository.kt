package com.th.forge.taxiorders.repo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

import com.th.forge.taxiorders.App

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

import okhttp3.ResponseBody
import retrofit2.Response

object ImageRepository {
    private val MINUTE = (1000 * 60).toLong()
    private var cachedImage: File? = null

    /* ToDo need background service*/
    private val isImageExistsAndNotExpired: Boolean
        get() {
            val currentTime = System.currentTimeMillis()
            return cachedImage!!.exists() && currentTime - cachedImage!!.lastModified() < 10 * MINUTE
        }

    fun getImage(fileDir: File, imagePath: String): Bitmap {
        Log.d("ImageRepo", "getImage")
        cachedImage = File(fileDir, imagePath)
        if (isImageExistsAndNotExpired) {
            return BitmapFactory.decodeFile(cachedImage!!.absolutePath)
        }
        cachedImage!!.delete()
        getResponseAndWriteImage(imagePath)
        return BitmapFactory.decodeFile(cachedImage!!.absolutePath)
    }

    private fun getResponseAndWriteImage(imagePath: String) {
        try {
            val response = App.apiService!!.getImage(imagePath).execute()
            if (response.body() != null) {
                writeImageToDisk(response)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    private fun writeImageToDisk(response: Response<ResponseBody>) {
        val os: OutputStream
        os = FileOutputStream(cachedImage!!)
        os.write(response.body()!!.bytes())
        os.flush()
        os.close()
    }
}
