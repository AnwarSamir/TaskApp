package com.codestomp.task.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codestomp.task.databinding.DrawerItemLayoutBinding

class DrawerMenuAdapter(private val items: ArrayList<DrawerMenuItem>,
                        private val iItemClick: IItemClick) :
    RecyclerView.Adapter<DrawerMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =DrawerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.onBind(items[position],position)
    }

    override fun getItemCount() = items.size



    private var lastPosition: Int=0
    inner class ViewHolder(val binding:  DrawerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


        fun onBind(drawerMenuItem: DrawerMenuItem, position: Int) {

            binding.icon.setImageResource(drawerMenuItem.icon)
            binding.description.text = drawerMenuItem.description
            binding.itemHolder.setOnClickListener {
                lastPosition=position
                iItemClick.onItemClicked(drawerMenuItem)
            }
            binding.ivSelected.showView(drawerMenuItem.selected)
            getLastClickedPosition()




        }
    }


    interface IItemClick{
        fun onItemClicked(data:DrawerMenuItem)
    }

    fun getLastClickedPosition():Int{
        return lastPosition
    }


}