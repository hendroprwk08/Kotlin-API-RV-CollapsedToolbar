package com.example.kotlinrvroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlinrvroom.databinding.FragmentHewanBinding
import com.example.kotlinrvroom.databinding.FragmentTumbuhanBinding
import org.json.JSONObject

class TumbuhanFragment : Fragment() {

    private lateinit var tumbuhanAdapter: TumbuhanAdapter
    private val tumbuhanList = ArrayList<Tumbuhan>()

    private var _binding: FragmentTumbuhanBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTumbuhanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onStart() {
        super.onStart()

        setRV()
    }

    companion object {
        fun newInstance() = HewanFragment()
    }

    public fun setRV(){
        tumbuhanAdapter = TumbuhanAdapter(tumbuhanList)
        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvTumbuhan.layoutManager = layoutManager
        binding.rvTumbuhan.itemAnimator = DefaultItemAnimator()
        binding.rvTumbuhan.adapter = tumbuhanAdapter

        getData()
    }

    private fun getData() {
        val queue = Volley.newRequestQueue(activity)
        val url = "https://endemikdb.site/tbh"

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

                    tumbuhanList.add(Tumbuhan(id, judul, keterangan, foto, status ))
                }

                tumbuhanAdapter.notifyDataSetChanged()

                _binding!!.progressBarTumbuhan.visibility = View.GONE
                _binding!!.rvTumbuhan.visibility = View.VISIBLE
            },
            {
                Toast.makeText(activity, "Koneksi: Gagal terhubung", Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}