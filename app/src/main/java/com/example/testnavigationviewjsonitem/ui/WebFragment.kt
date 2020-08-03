package com.example.testnavigationviewjsonitem.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testnavigationviewjsonitem.R
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.fragment_web.view.*


class WebFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_web, container, false)
        val param = arguments?.getString("key")
        root.webViewItem.loadUrl(param!!)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}