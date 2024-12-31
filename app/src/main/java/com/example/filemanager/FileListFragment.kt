package com.example.filemanager

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File;
import java.util.ArrayList;

class FileListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    private var currentPath: String = ""

    companion object {
        private const val ARG_PATH = "path"

        fun newInstance(path: String): FileListFragment {
            return FileListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PATH, path)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentPath = arguments?.getString(ARG_PATH) ?: ""

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        loadFiles()
    }

    private fun loadFiles() {
        val fileList = ArrayList<File>()
        val directory = File(currentPath)

        directory.listFiles()?.let { files ->
            fileList.addAll(files.filter { it.isDirectory || it.isFile })
        }

        adapter = FileAdapter(fileList) { file ->
            when {
                file.isDirectory -> {
                    // Navigate to directory
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,
                            newInstance(file.absolutePath))
                        .addToBackStack(null)
                        .commit()
                }
                file.isFile && file.extension.toLowerCase() == "txt" -> {
                    // Open text file
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,
                            FileViewerFragment.newInstance(file.absolutePath))
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
        recyclerView.adapter = adapter
    }
}