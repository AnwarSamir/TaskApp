package com.codestomp.task.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.codestomp.task.R
import com.codestomp.task.data.models.Article
import com.codestomp.task.databinding.FragmentDetailsBinding
import com.codestomp.task.ui.utils.DateHelperUtils


class DetailsFragment : Fragment() {

   lateinit var  binding:FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val data = requireArguments().getSerializable("data") as Article
        binding= FragmentDetailsBinding.inflate(inflater,container,false)
        initUi(data)
        return binding.root
    }

    private fun initUi(data: Article) {
        binding.tvName.text=data.title
        binding.tvSite.text="By ${data.author}"
        binding.tvDesc.text=data.description
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

        openWebsite(data.url)
    }

    private fun openWebsite(url: String) {
        binding.btnOpenWebsite.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


    }

}