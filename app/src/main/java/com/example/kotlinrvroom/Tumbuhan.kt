package com.example.kotlinrvroom

class Tumbuhan (id: String?, nama: String?, keterangan: String?, gambar: String?, status: String?) {

    private var id: String
    private var nama: String
    private var keterangan: String
    private var gambar: String
    private var status: String

    init {
        this.id = id!!
        this.nama = nama!!
        this.keterangan = keterangan!!
        this.gambar = gambar!!
        this.status = status!!
    }

    fun getId(): String? {
        return id
    }

    fun getNama(): String? {
        return nama
    }

    fun getKeterangan(): String? {
        return keterangan
    }

    fun getGambar(): String? {
        return gambar
    }

    fun getStatus(): String? {
        return status
    }

}