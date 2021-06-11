package com.example.snack.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snack.adapter.NoticeAdapter
import com.example.snack.data.NoticeData
import com.example.snack.databinding.DialogNoticeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class NoticeDialog
constructor(context: Context) : Dialog(context) {
    lateinit var binding: DialogNoticeBinding
    lateinit var adapter: NoticeAdapter
    val url = "http://ecampus.konkuk.ac.kr/ilos/m/main/main_form.acl"
    val scope = CoroutineScope((Dispatchers.IO))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding = DialogNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.closeButton.setOnClickListener {
            dismiss()
        }

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getnews()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter = NoticeAdapter(ArrayList<NoticeData>())
        adapter.itemClickListener = object : NoticeAdapter.OnItemClickListener {
            override fun OnItemClick(
                    holder: NoticeAdapter.MyViewHolder,
                    view: View,
                    data: NoticeData,
                    position: Int
            ) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                context.startActivity(intent)
            }

        }
        binding.recyclerView.adapter = adapter
        getnews()
    }

    private fun getnews() {
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(url).get()
            val headlines = doc.select("li>em>a")
            for (news in headlines) {
                adapter.items.add(NoticeData(news.text(), news.absUrl("href")))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }
}