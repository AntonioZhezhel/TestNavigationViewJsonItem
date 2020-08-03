package com.example.testnavigationviewjsonitem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testnavigationviewjsonitem.R
import kotlinx.android.synthetic.main.fragment_text.view.*


class TextFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_text, container, false)
        val param = arguments?.getString("key")
        root.textViewParamText.text =param

        return root
    }


}