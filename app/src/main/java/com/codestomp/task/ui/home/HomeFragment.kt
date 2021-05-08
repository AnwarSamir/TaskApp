package com.codestomp.task.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codestomp.task.R
import com.codestomp.task.data.models.Article
import com.codestomp.task.data.network.Resource
import com.codestomp.task.data.network.apisInterfaces.HomeService
import com.codestomp.task.data.repository.HomeRepo
import com.codestomp.task.databinding.FragmentHomeBinding
import com.codestomp.task.ui.base.BaseFragment
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepo>(),
    ArticlesAdapter.IItemClick {


    override fun getViewModel() = HomeViewModel::class.java
    private lateinit var adapter: ArticlesAdapter
    var articleList=ArrayList<Article>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        b: Boolean
    )= FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): HomeRepo {
        val api = HomeRepo(
            remoteDataSource.buildApi(
                HomeService::class.java))

        return api
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        fetchData()
        observeArticlesData()
        observeArticleTwoData()
    }


    private fun fetchData() {
        if ( mViewModel.articlesResponse.value == null && mViewModel.articlesTwoResponse.value ==null)
        {
            mViewModel.getArticles()
        }
    }

    private fun observeArticleTwoData() {
        mViewModel.articlesTwoResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success  -> {
                    loading.cancel()
                    articleList.addAll(it.value.articles)
                    adapter.notifyDataSetChanged()
                }

                is  Resource.Failure  -> {
                    loading.cancel()
                }

                is Resource.Loading  -> {
                    loading.show()
                }



            }
        })
    }

    private fun observeArticlesData() {
        mViewModel.articlesResponse.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success  -> {
                    loading.cancel()
                    articleList.addAll(it.value.articles)
                    adapter.notifyDataSetChanged()
                }

                is  Resource.Failure  -> {
                    loading.cancel()
                }

                is Resource.Loading  -> {
                    loading.show()
                }



            }
        })
    }

    private fun initRv() {
        adapter=ArticlesAdapter(articleList,this)
        binding.rvArticles.adapter=adapter
    }

    override fun onItemClicked(data: Article) {
        val bundle=Bundle()
        bundle.putSerializable("data",data)
        findNavController().navigate(R.id.action_nav_home_to_detailsFragment,bundle)
    }
}