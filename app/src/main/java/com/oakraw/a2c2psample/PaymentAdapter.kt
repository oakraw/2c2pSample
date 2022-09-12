package com.oakraw.a2c2psample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaymentAdapter(val onClick: (Int) -> Unit) : RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    private var items: List<String>? = null

    fun setItem(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.simple_list_item, parent, false), onClick
        )

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.get(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(val view: View, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) = with(view) {
            findViewById<TextView>(R.id.text).apply {
                this.text = item
                this.setOnClickListener {
                    onClick.invoke(layoutPosition)
                }
            }
        }
    }
}