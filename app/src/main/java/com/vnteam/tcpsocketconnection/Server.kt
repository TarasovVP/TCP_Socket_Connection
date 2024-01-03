package com.vnteam.tcpsocketconnection

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class Server : Runnable {
    private var thread: Thread? = null
    private var serverSocket: ServerSocket? = null
    private var socket: Socket? = null
    private var dataInputStream: DataInputStream? = null
    private var dataOutputStream: DataOutputStream? = null

    init {
        thread = Thread(this)
        thread?.priority = Thread.NORM_PRIORITY
        thread?.start()
    }

    override fun run() {
        try {
            serverSocket = ServerSocket(12345)
        } catch (e: IOException) {
            println("failed to start server socket")
            e.printStackTrace()
        }

        println("waiting for connections...")
        try {
            socket = serverSocket?.accept()
        } catch (e: IOException) {
            println("failed to accept")
            e.printStackTrace()
        }
        println("client connected")

        try {
            dataInputStream = DataInputStream(BufferedInputStream(socket?.getInputStream()))
            dataOutputStream = DataOutputStream(BufferedOutputStream(socket?.getOutputStream()))
        } catch (e: IOException) {
            println("failed to create streams")
            e.printStackTrace()
        }

        try {
            dataOutputStream?.writeInt(123)
            dataOutputStream?.flush()
        } catch (e: IOException) {
            println("failed to send")
            e.printStackTrace()
        }

        while (true) {
            try {
                val test = dataInputStream?.readByte()
                println("byte received: $test")
                if (test?.toInt() == 42) break
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
        println("server thread stopped")
    }
}