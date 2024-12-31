package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(
    private val files: List<File>,
    private val onItemClick: (File) -> Unit
) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.fileName)
        val iconImageView: ImageView = view.findViewById(R.id.fileIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        holder.nameTextView.text = file.name

        // Set icon based on file type
        holder.iconImageView.setImageResource(
            when {
                file.isDirectory -> R.drawable.ic_folder
                file.extension.toLowerCase() == "txt" -> R.drawable.ic_file_text
                else -> R.drawable.ic_file
            }
        )

        holder.itemView.setOnClickListener { onItemClick(file) }
    }

    override fun getItemCount() = files.size
}