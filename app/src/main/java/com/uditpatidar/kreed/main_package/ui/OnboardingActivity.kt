package com.uditpatidar.kreed.main_package.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.uditpatidar.kreed.R
import com.uditpatidar.kreed.databinding.ActivityOnboardingBinding
import com.uditpatidar.kreed.main_package.adapter.OnboardingAdapter
import com.uditpatidar.kreed.main_package.model.OnboardingItem

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var dots: Array<ImageView>
    private lateinit var onboardingItems: List<OnboardingItem>
    private lateinit var sharedPreferences: SharedPreferences
    private var isOnboardingCompleted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        isOnboardingCompleted = sharedPreferences.getBoolean("onboarding_completed", false)

        if (isOnboardingCompleted) {
            // Redirect to the main activity or login
            redirectToLoginActivity()
            return
        }


        // Set up view binding

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize onboarding items
        onboardingItems = getOnboardingItems()

        // Set up the adapter
        onboardingAdapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = onboardingAdapter

        // Set up the custom dots
        setupDots(onboardingItems.size)

        // Set up page change callback
        binding.viewPager.registerOnPageChangeCallback(viewPagerCallback)

        // Set the next button click listener
        binding.mcvNext.setOnClickListener(this)
        binding.tvSkip.setOnClickListener(this)
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateDots(position)


            if (position == onboardingItems.size - 1) {
                // Last page: Show "Start" button and animate the card to full width
             //   animateCardToFullWidth(binding.mcvNext)
                binding.tvNext.text = "Ready Set Go!"
                binding.tvSkip.visibility = View.INVISIBLE

            } else {
                // Not the last page: Hide "Start" button and reset card width
                binding.tvNext.text = "Next"
                binding.tvSkip.visibility = View.VISIBLE

            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)


        }
    }

    private fun getOnboardingItems(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(
                R.drawable.illustration1,
                "Capture your Matches!",
                "Record scores and moments of your matches effortlessly",

            ),
            OnboardingItem(
                R.drawable.illustration2,
                "Craft the Speaking Profile",
                "Develop your profile and Showcase your Achievements and Scores. ",

            ),
            OnboardingItem(
                R.drawable.illustration3,
                "Complete and Connect",
                "Join the Thriving Community and Share your Passion for Sports!",

            )
        )
    }

    private fun setupDots(count: Int) {
        dots = Array(count) { ImageView(this) }
        for (i in dots.indices) {
            dots[i] = ImageView(this).apply {
                setImageDrawable(ContextCompat.getDrawable(this@OnboardingActivity, R.drawable.inactive_dot))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8
                    marginStart = 8
                }
            }
            binding.dotsLayout.addView(dots[i])
        }
        updateDots(0) // Set the first dot as active initially
    }

    private fun updateDots(position: Int) {
        dots.forEachIndexed { index, dot ->
            if (index == position) {
                dot.setImageResource(R.drawable.active_dot)
            } else {
                dot.setImageResource(R.drawable.inactive_dot)
            }
        }
    }



    override fun onClick(view: View) {
        when (view.id) {
            R.id.mcvNext -> {
                val currentPosition = binding.viewPager.currentItem
                if (currentPosition < onboardingItems.size - 1) {
                    binding.viewPager.currentItem = currentPosition + 1
                } else {
                    completeOnboarding()
                }
            }
            R.id.tvSkip ->{
                completeOnboarding()
            }
        }
    }










    private fun completeOnboarding() {
        // Set onboarding as completed
        isOnboardingCompleted = true
        sharedPreferences.edit().putBoolean("onboarding_completed", true).apply() // Save to SharedPreferences
        redirectToLoginActivity()
    }

    private fun redirectToLoginActivity() {
        // Start your main activity here
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish() // Finish the onboarding activity
    }

}
