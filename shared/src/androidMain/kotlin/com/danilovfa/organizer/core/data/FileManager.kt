package com.danilovfa.organizer.core.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.coroutines.CoroutineContext

actual class FileManager(
    private val context: Context,
    private val ioDispatcher: CoroutineContext = Dispatchers.IO
) {

    private val filesMutexes = mutableMapOf<String, Mutex>()

    private fun getMutex(dirName: String, fileName: String): Mutex {
        return filesMutexes.getOrPut("$dirName/$fileName") {
            Mutex()
        }
    }

    actual suspend fun writeToFile(dirName: String, fileName: String, bytes: ByteArray) {
        getMutex(dirName, fileName).withLock(fileName) {
            withContext(ioDispatcher) {
                val dir = File(context.filesDir, dirName)
                dir.mkdirs()

                val file = File(context.filesDir, "$dirName/$fileName")
                FileOutputStream(file).use { fileOutputStream ->
                    fileOutputStream.write(bytes)
                }
            }
        }
    }

    actual suspend fun readFromFile(dirName: String, fileName: String): ByteArray {
        return getMutex(dirName, fileName).withLock {
            withContext(ioDispatcher) {
                val dir = File(context.filesDir, dirName)
                dir.mkdirs()

                val file = File(context.filesDir, "$dirName/$fileName")
                FileInputStream(file).use { fileInputStream ->
                    fileInputStream.readBytes()
                }
            }
        }
    }

    actual suspend fun getAllFiles(dirName: String): List<String> {
        val dir = File(context.filesDir, dirName)
        return dir.list()?.toList() ?: listOf()
    }

    actual suspend fun deleteAllFiles(dirName: String) {
        val dir = File(context.filesDir, dirName)
        dir.listFiles()?.forEach { file ->
            getMutex(dirName, file.name).withLock {
                file.delete()
            }
        }
    }
}