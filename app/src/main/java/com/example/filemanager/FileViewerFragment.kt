package com.example.filemanager

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import androidx.fragment.app.Fragment;
import java.io.File;

class FileViewerFragment : Fragment() {
    companion object {
        private const val ARG_FILE_PATH = "file_path"

        fun newInstance(filePath: String): FileViewerFragment {
            return FileViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FILE_PATH, filePath)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filePath = arguments?.getString(ARG_FILE_PATH)
        val textView = view.findViewById<TextView>(R.id.textContent)

        filePath?.let { path ->
            try {
                val text = File(path).readText()
                textView.text = text
            } catch (e: Exception) {
                textView.text = "Error reading file: ${e.message}"
            }
        }
    }
}