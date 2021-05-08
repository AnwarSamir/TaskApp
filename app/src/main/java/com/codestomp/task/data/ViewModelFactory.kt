package com.codestomp.task.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codestomp.task.data.network.BaseRepo
import com.codestomp.task.data.repository.HomeRepo
import com.codestomp.task.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val baseRepo: BaseRepo) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(baseRepo as HomeRepo) as T
            else -> throw IllegalArgumentException("view model class not found ")
        }

    }

}