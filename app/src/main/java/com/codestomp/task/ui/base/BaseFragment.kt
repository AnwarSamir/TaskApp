package com.codestomp.task.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.codestomp.task.data.ViewModelFactory
import com.codestomp.task.data.network.BaseRepo
import com.codestomp.task.data.network.RemoteDataSource
import com.codestomp.task.ui.LoadingDialog

abstract class BaseFragment<VM : ViewModel, B : ViewBinding, R : BaseRepo> : Fragment() {
    protected lateinit var binding: B
    protected val remoteDataSource= RemoteDataSource()
    lateinit var mViewModel:VM
    lateinit var loading: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container,false)
        val factory= ViewModelFactory(getFragmentRepo())
        mViewModel=ViewModelProvider(this,factory).get(getViewModel())
        loading= LoadingDialog(requireActivity())
        return binding.root
    }


    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?, b: Boolean):B

    abstract fun getFragmentRepo():R

}