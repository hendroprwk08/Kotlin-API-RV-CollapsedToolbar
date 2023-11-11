package com.example.kotlinrvroom

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.kotlinrvroom.databinding.ActivityDetilBinding
import org.json.JSONObject


class DetilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetilBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.extras
        val id = bundle?.getString("_id")
        val nama = bundle?.getString("_nama")

        loadData(id!!)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        supportActionBar?.title = nama

    }

    fun loadData(id: String): Any? {
        val queue = Volley.newRequestQueue(this)
        val url = "https://endemikdb.site/end/$id"

        Log.i("TAG", "loadData: $url")

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("hasil")

                Glide.with(applicationContext)
                    .load(jsonArray.get("foto").toString())
                    .placeholder(R.drawable.image)
                    .centerCrop()
                    .into(binding.image)

                binding.tvDeskripsi.text = jsonArray.get("deskripsi").toString() +
                        "\n\nAsal: " + jsonArray.get("asal").toString() +
                        "\n\nStatus: " + jsonArray.get("status").toString()
            },
            {
                Toast.makeText(this, "Koneksi: Gagal terhubung", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

        return null
    }
}