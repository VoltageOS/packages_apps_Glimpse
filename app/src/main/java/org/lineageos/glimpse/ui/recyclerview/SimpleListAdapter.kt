/*
 * SPDX-FileCopyrightText: 2024 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.ui.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * A very basic ListAdapter that holds only one type of item.
 * @param diffCallback A [DiffUtil.ItemCallback] provided by the derived class
 * @param factory The factory of the [View]
 */
abstract class SimpleListAdapter<T, V : View>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val factory: (parent: ViewGroup) -> V,
) : ListAdapter<T, SimpleListAdapter<T, V>.ViewHolder>(diffCallback) {
    abstract fun ViewHolder.onBindView(item: T)

    open fun ViewHolder.onPrepareView() {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        factory(parent),
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @CallSuper
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        holder.onViewAttachedToWindow()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.onViewDetachedFromWindow()

        super.onViewDetachedFromWindow(holder)
    }

    inner class ViewHolder(val view: V) : RecyclerView.ViewHolder(view), LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        override val lifecycle = lifecycleRegistry

        var item: T? = null

        val itemDetails = object : ItemDetailsLookup.ItemDetails<T>() {
            override fun getPosition() = bindingAdapterPosition
            override fun getSelectionKey() = item
        }

        init {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

            onPrepareView()
        }

        fun bind(item: T) {
            this.item = item

            onBindView(item)
        }

        fun onViewAttachedToWindow() {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }

        fun onViewDetachedFromWindow() {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }
}
