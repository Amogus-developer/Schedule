package com.example.tspk_raspisanie_exe

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tspk_raspisanie_exe.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

val listTspk = ArrayList<String>()

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private lateinit var secThread: Thread
    private lateinit var runnable: Runnable

    private lateinit var doc: Document
    private lateinit var url: String





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        binding.downloadRasp.setOnClickListener {
            update()
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Расписание_ТСПК")
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
        binding.seeRasp.setOnClickListener {
                val intent = Intent(this@MainActivity, WebView::class.java)
                update()
                intent.putExtra("url", url)
                startActivity(intent)
        }
    }
    private fun update(){
        url = binding.textDay.text.toString()


    }
    private fun init(){

        runnable = Runnable {
            run(){
                getWeb()
            }
        }
        secThread = Thread(runnable)
        secThread.start()
    }
    private fun getWeb(){

        try {
            doc = Jsoup.connect("https://tspk.org/studentam/novoe-raspisanie-demo.html").get()
            doc.getElementsByClass("slider-slides")
            val table_tspk = doc.getElementsByTag("tbody")
            val links = Jsoup.parse(table_tspk.toString()).getElementsByTag("a")
            val link = doc.select("td.topic.starter > a").first()
            for (link in links) {
                if (link.attr("href") != "#norasp") {
                    println((link.attr("href")))
                    listTspk.add(link.attr("href"))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}




