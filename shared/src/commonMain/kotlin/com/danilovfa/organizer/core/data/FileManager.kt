package com.danilovfa.organizer.core.data

expect class FileManager {
    suspend fun writeToFile(dirName: String, fileName: String, bytes: ByteArray)
    suspend fun readFromFile(dirName: String, fileName: String): ByteArray
    suspend fun getAllFiles(dirName: String): List<String>
    suspend fun deleteAllFiles(dirName: String)
}