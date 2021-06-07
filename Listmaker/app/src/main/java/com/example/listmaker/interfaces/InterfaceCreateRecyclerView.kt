package com.example.listmaker.interfaces

import androidx.recyclerview.widget.RecyclerView

interface InterfaceCreateRecyclerView {
    fun createAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>
    fun setupRecyclerView()
    fun setupFloatActionButton()
}