package com.uditpatidar.kreed.main_package.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uditpatidar.kreed.R

import android.view.LayoutInflater
import com.uditpatidar.kreed.main_package.model.OnboardingItem


class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.image_onboarding)
        private val titleTextView = view.findViewById<TextView>(R.id.title_onboarding)
        private val descriptionTextView = view.findViewById<TextView>(R.id.description_onboarding)
        private val container = view.findViewById<LinearLayout>(R.id.onboardingContainer)

        fun bind(item: OnboardingItem) {
            imageView.setImageResource(item.imageResId)
            titleTextView.text = item.title
            descriptionTextView.text = item.description
            //container.setBackgroundColor(item.backgroundColor) // Set the background color
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboarding_screen, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}


