package com.codestomp.task.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codestomp.task.R
import com.codestomp.task.data.models.Article
import com.codestomp.task.databinding.RowArticleBinding
import com.codestomp.task.ui.utils.DateHelperUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArticlesAdapter(private val items: ArrayList<Article>,
                      private val iItemClick: IItemClick) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =RowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.onBind(items[position])
    }

    override fun getItemCount() = items.size



    inner class ViewHolder(val binding:  RowArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Article) {


            binding.itemHolder.setOnClickListener { iItemClick.onItemClicked(data) }
            binding.tvName.text=data.title
            binding.tvSite.text="By ${data.author}"
            if (data.publishedAt !=null)
            binding.tvDate.text= DateHelperUtils.formatDateFromDateString(
                DateHelperUtils.DATE_FORMAT_12,
                DateHelperUtils.DATE_FORMAT_23,
                data.publishedAt
            )


            Glide.with(binding.root)
                .load(data.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.ivArticle);

        }
    }




    interface IItemClick{
        fun onItemClicked(data:Article)
    }
}