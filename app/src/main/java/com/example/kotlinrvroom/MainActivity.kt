package com.example.kotlinrvroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlinrvroom.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding
    private lateinit var hewanAdapter: HewanAdapter
    private val hewanList = ArrayList<Hewan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()

        setRV()
    }

    public fun setRV(){
        hewanAdapter = HewanAdapter(hewanList)
        val layoutManager = GridLayoutManager(this, 2)
        _binding.recyclerView.layoutManager = layoutManager
        _binding.recyclerView.itemAnimator = DefaultItemAnimator()
       _binding.recyclerView.adapter = hewanAdapter

        getData()
    }

    private fun getData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://endemikdb.site/hwn"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.optJSONArray("hasil")
                var id : String
                var judul: String
                var keterangan: String
                var foto: String
                var status: String

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    id = jsonObject.optString("id")
                    judul = jsonObject.optString("nama")
                    keterangan = jsonObject.optString("keterangan")
                    foto = jsonObject.optString("foto")
                    status = jsonObject.optString("status")

                    hewanList.add(Hewan(id, judul, keterangan, foto, status ))
                }

                hewanAdapter.notifyDataSetChanged()

                _binding!!.progressBar.visibility = View.GONE
                _binding!!.recyclerView.visibility = View.VISIBLE
            },
            {
                Toast.makeText(this, "Koneksi: Gagal terhubung", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}