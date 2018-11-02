package com.milwaukee.waether.baseui

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class GenericListAdapter<T : RecyclerItem<*>, S : RecyclerGenericView<*>>(
    private val listener: (String) -> Unit,
    private val factory: ViewFactory<S>
) : ListAdapter<T, RecyclerView.ViewHolder>(RecyclerItemDiffCallback<T>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(factory.getView(parent, viewType))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder.itemView as RecyclerGenericView<T>
        view.bind(getItem(position), position)
        view.setListener { listener }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).getType()
    }

}


interface RecyclerItem<R : Any> {

    fun getType() : Int

    fun getId(): Int

    fun getContent(): R

    fun getComparator() : Any
}

interface RecyclerGenericView<T : RecyclerItem<*>> {

    fun bind(item: T, position: Int)

    fun setListener(listener: (String) -> Unit)

}

class RecyclerItemDiffCallback<T : RecyclerItem<*>> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getId() == secondItem.getId()
    }

    override fun areContentsTheSame(firsteItem: T, secondItem: T): Boolean {
        return firsteItem.getComparator() == secondItem.getComparator()
    }

}

abstract class ViewFactory<S :  RecyclerGenericView<*>> {

    abstract fun getView(parent: ViewGroup, viewType: Int): S
}

private class ViewHolder<S : RecyclerGenericView<*>>(view: S) :
    RecyclerView.ViewHolder(view as View)
