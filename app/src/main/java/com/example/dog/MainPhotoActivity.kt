package com.example.dog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainPhotoActivity : AppCompatActivity(), HomepageFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main_photo)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if (currentFragment == null) {
            val fragment = HomepageFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }


    }

    override fun onButtonSelected(query: String) {
        val fragment = DogPhotoFragment.newInstance(query)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}