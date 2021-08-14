package com.example.chatapp

import android.view.View

interface ChatInterface {
    fun chatClickListener(position: Int, view: View)
    fun onClick(view: View?)
}