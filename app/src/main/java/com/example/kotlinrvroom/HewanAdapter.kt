package com.example.kotlinrvroom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinrvroom.databinding.RowItemLayoutBinding

class HewanAdapter (private var hewanList: List<Hewan>) :
    RecyclerView.Adapter<HewanAdapter.MyViewHolder>() {

    //binding layout: 1. ganti "binding: ItemRowLayoutBinding" dan "binding.root"
    class MyViewHolder(val _binding: RowItemLayoutBinding) : RecyclerView.ViewHolder(_binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HewanAdapter.MyViewHolder {
        //binding layout: 2. tarik layout
        val _binding = RowItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(_binding)
    }

    override fun onBindViewHolder(holder: HewanAdapter.MyViewHolder, position: Int) {
        //binding layout: 3. letakkan nilai pada layout
        holder._binding.tvNama.text = hewanList[position].getNama()

        Glide.with(holder.itemView.context)
            .load(hewanList[position].getGambar())
            .placeholder(R.drawable.baseline_broken_image_24)
            .into(holder._binding.gambar)

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("_id", hewanList[position].getId())
            bundle.putString("_nama", hewanList[position].getNama())

            val i = Intent(holder.itemView.context, DetilActivity::class.java)
            i.putExtras(bundle)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return hewanList.size
    }

}