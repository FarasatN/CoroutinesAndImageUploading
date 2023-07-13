package com.example.updateimage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    val TAG = "TAGG"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.imageView)
//        val updatedImage = getSharedPreferences("updatedImage", MODE_PRIVATE).getString("url","")

        button.setOnClickListener {
                val intent = Intent(this, UpdateImageActivity::class.java)
                startActivity(intent)
        }


        //simplest way coroutine

        //default context is the "DefaultDispatcher"
        //Dispatchers.Unconfined - create current thread
        //newSingleThreadContext("NewThread") - for new personal thread
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(1000)
//
//            val answer = doNetworkCall()
//            withContext(Dispatchers.Main){
//                imageView.setImageResource(R.drawable.ic_launcher_background)
//            }
//            Log.v(TAG,"Coroutine from thread : ${Thread.currentThread().name}")
//        }
//
//        Log.v(TAG,"Coroutine from thread : ${Thread.currentThread().name}")

//        runBlocking {// = Thread.sleep(1000L) - blocking main thread
//            launch(Dispatchers.IO) {//scope u yazmaga ehtiyac yoxdur, cunki coroutine icindeyik
//                delay(2000L)
//                Log.v(TAG,"Finished IO coroutine 1 : ${Thread.currentThread().name}")
//            }
//            launch(Dispatchers.IO) {//scope u yazmaga ehtiyac yoxdur, cunki coroutine icindeyik
//                delay(2000L)
//                Log.v(TAG,"Finished IO coroutine 1 : ${Thread.currentThread().name}")
//            }
//            Log.v(TAG,"start of runblocking")
//            delay(5000L)
//            Log.v(TAG,"end of runblocking")
//
//        }



        //jobs, waiting, cancellation
//        val job  = GlobalScope.launch(Dispatchers.Default) {
////            repeat(5){
////                Log.v(TAG, "Coroutine is still working..")
////                delay(1000L)
////            }
//            Log.v(TAG, "Starting long running calculation ...")
//            withTimeout(3000L){
//                for (i in 90..100){
//                    if (isActive){
//                        Log.v(TAG, "Result for i = ${(i*i*i)}")
//                    }
//                }
//            }
//            Log.v(TAG, "Ending  long running calculation ...")
//        }

//        runBlocking {//manual cancelling
////            job.join()
//            job.cancel()//network call larda response gec geldikde ise yarayir(time-out zamani)
//            Log.v(TAG, "Main thread is continuing")
//
//        }


        //async, await
//        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis {
////                val answer1 = networkCall1()
////                val answer2 = networkCall2()
////                Log.v(TAG, "Answer1 is $answer1")
////                Log.v(TAG, "Answer2 is $answer2")
//
//                val answer1 = async{ networkCall1() }
//                val answer2 = async{ networkCall2() }
//
//                Log.v(TAG, "Answer1 is $answer1")
//                Log.v(TAG, "Answer2 is $answer2")
//            }
//
//            Log.v(TAG, "Request took $time ms.")
//        }

//        val btnStartCoroutineActivity = findViewById<Button>(R.id.btnStartCoroutineActivity)
//        btnStartCoroutineActivity.setOnClickListener {
//            lifecycleScope.launch {
//                while (true){
//                    delay(1000L)
//                    Log.v(TAG,"Still running ....")
//                }
//            }
//            GlobalScope.launch {
//                    delay(1000L)
//                    Intent(this@MainActivity, CoroutineActivity::class.java).also {
//                        startActivity(it)
//                        finish()
//                    }
//            }
//        }


        //for exception handling
//        val handler = CoroutineExceptionHandler { _, throwable ->
//            println("Caught exception: $throwable")
//        }
//
//        lifecycleScope.launch(handler) {
//            launch {
//                throw Exception("inside coroutine exception..")
//            }
//
////            throw Exception("Something went wrong..")
//        }


        //CoroutineScope -> Cancel whenever any of its children fail.
        //SupervisorScope -> If we want to continue with the other tasks even when one fails, we go with the supervisorScope. A supervisorScope won’t cancel other children when one of them fails.

//        val handler = CoroutineExceptionHandler { _, throwable ->
//            println("Caught exception: $throwable")
//        }
//        CoroutineScope(Dispatchers.Main+handler).launch(handler) {
//            supervisorScope {//catch all coroutines even if throw an exception (viewModelScope da arxa planda bundan ist. edir)
//                launch {
//                    delay(300L)
//                    throw Exception("Coroutine 1 failed")
//                }
//                launch {
//                    delay(400L)
//                    println("Coroutine 2 finished")
//                }
//            }
//        }



//        lifecycleScope.launch {
//            val job = launch {
//                try {
//                    delay(500L)
//                }catch (e: Exception){
//                    e.printStackTrace()
//                }
//                println("Coroutine 1 finished")
//            }
//            delay(300L)
//            job.cancel()
//        }


    }

//    suspend fun networkCall1():String{
//        delay(30000L)
//        return "Answer 1"
//    }
//    suspend fun networkCall2():String{
//        delay(50000L)
//        return "Answer 2"
//    }



//    suspend fun doNetworkCall(): String{
//        delay(3000L)
//        Log.v(TAG, "\"This is the answer\"")
//        return "This is the answer"
//    }





    override fun onResume() {
        super.onResume()
        val imageView = findViewById<ImageView>(R.id.imageView)
        val updatedImage = getSharedPreferences("updatedImage", Context.MODE_PRIVATE).getString("url","")

        val byteArray = Base64.decode(updatedImage, Base64.DEFAULT)
        Glide
            .with(this)
//            .asBitmap()
//            .load(updatedImage)
            .load(byteArray)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)

        Log.v("TAG", "onResume : $updatedImage")
    }
}