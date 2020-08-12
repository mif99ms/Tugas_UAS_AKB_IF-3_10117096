package com.Miftah.UAS_10117096.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.Miftah.UAS_10117096.R
import com.Miftah.UAS_10117096.activity.DetailActivity
import com.Miftah.UAS_10117096.model.WisataModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_wisata.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity


//NIM : 10117096
//Nama : Miftah Muhammad Sidqi
//Kelas : IF-3
//Waktu Pengerjaam : 11/08/2020
class WisataAdapter(val context: Context, private val list: ArrayList<WisataModel>) : RecyclerView.Adapter<WisataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.context,list[position])
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(context: Context, data: WisataModel) {
            Glide.with(context)
                    .load(data.lokasi.foto1)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.placeholder))
                    .into(itemView.foto)
            itemView.nama.text = data.lokasi.nama
            itemView.views.onClick {
                context.startActivity<DetailActivity>(
                        "id" to data.id
                )
            }
        }
    }
}