package com.example.filemanager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File

class SampleDataCreator(private val context: Context) {
    private val baseDir = Environment.getExternalStorageDirectory().absolutePath

    fun createSampleData() {
        if (checkPermission()) {
            createDirectoryStructure()
            createSampleFiles()
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun createDirectoryStructure() {
        // Tạo cấu trúc thư mục
        createDirectory("Documents")
        createDirectory("Documents/Work")
        createDirectory("Documents/Personal")
        createDirectory("Pictures")
        createDirectory("Music")
        createDirectory("Downloads")
    }

    private fun createDirectory(relativePath: String) {
        val dir = File("$baseDir/$relativePath")
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    private fun createSampleFiles() {
        // Tạo các file văn bản mẫu
        createTextFile(
            "Documents/readme.txt",
            "Đây là thư mục Documents.\nDùng để lưu trữ tài liệu quan trọng."
        )

        createTextFile(
            "Documents/Work/project.txt",
            """
            Dự án Android:
            1. Xây dựng ứng dụng quản lý file
            2. Thiết kế giao diện
            3. Xử lý logic
            4. Kiểm thử
            """.trimIndent()
        )

        createTextFile(
            "Documents/Personal/notes.txt",
            """
            Ghi chú cá nhân:
            - Mua sắm cuối tuần
            - Họp nhóm thứ 3
            - Deadline dự án: 20/12
            """.trimIndent()
        )

        createTextFile(
            "Downloads/download_info.txt",
            "Thư mục Downloads chứa các file đã tải xuống."
        )
    }

    private fun createTextFile(relativePath: String, content: String) {
        try {
            val file = File("$baseDir/$relativePath")
            file.parentFile?.mkdirs()  // Tạo thư mục cha nếu chưa tồn tại
            file.writeText(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}