package com.codestomp.task.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codestomp.task.data.models.ApiResponse
import com.codestomp.task.data.network.Resource
import com.codestomp.task.data.repository.HomeRepo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.stream.Stream

class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {

    // home vars
    private val _articlesResponse: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val articlesResponse: LiveData<Resource<ApiResponse>> get() = _articlesResponse


    private val _articlesTwoResponse: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val articlesTwoResponse: LiveData<Resource<ApiResponse>> get() = _articlesTwoResponse




    fun getArticles() = viewModelScope.launch {
        _articlesResponse.value = Resource.Loading

        val articleLinkOne = async { homeRepo.getArticles() }
        val articleLinkTwo = async { homeRepo.getArticlesTwo() }

        val data1 = articleLinkOne.await()
        val data2 = articleLinkTwo.await()

        _articlesResponse.postValue(data1)
        _articlesTwoResponse.postValue(data2)
    }




}