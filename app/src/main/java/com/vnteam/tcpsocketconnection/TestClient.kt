package com.vnteam.tcpsocketconnection

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket

class TestClient {
    private var socket: Socket? = null
    private var dataInputStream: DataInputStream? = null
    private var dataOutputStream: DataOutputStream? = null

    init {
        try {
            socket = Socket("127.0.0.1", 12345)
        } catch (e: IOException) {
            println("failed to create socket")
            e.printStackTrace()
        }
        println("connected")
        try {
            dataInputStream = DataInputStream(BufferedInputStream(socket?.getInputStream()))
            dataOutputStream = DataOutputStream(BufferedOutputStream(socket?.getOutputStream()))
        } catch (e: IOException) {
            println("failed to create streams")
            e.printStackTrace()
        }
        while (true) {
            try {
                val test = dataInputStream?.readInt()
                println("int received: $test")
                if (test == 42) break
            } catch (e: IOException) {
                println("failed to read data")
                e.printStackTrace()
                break
            }
        }
    }
}
