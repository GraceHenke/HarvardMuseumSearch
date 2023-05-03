package com.example.dog

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

private const val ARG_QUERY = "dog"
class HomepageFragment() : Fragment() {

    private lateinit var birdButton: Button
    private lateinit var dogButton: Button
    private lateinit var catButton: Button
    private lateinit var homepageButton: Button

    private lateinit var beatBox: BeatBox
    private lateinit var mContext: Context

    private lateinit var dog: Sound
    private lateinit var bird: Sound
    private lateinit var cat: Sound

    private var query = "dog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(mContext.assets)

        dog = Sound("dog.wav", 3)
        bird = Sound("bird.wav", 1)
        cat = Sound("cat.wav", 2)




    }
    interface Callbacks {
        fun onButtonSelected(query: String)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage_fragment, container, false)

        birdButton = view.findViewById(R.id.bird_button) as Button
        dogButton = view.findViewById(R.id.dog_button) as Button
        catButton = view.findViewById(R.id.cat_button) as Button
        homepageButton = view.findViewById(R.id.harvard_homepage) as Button

        return view
    }

    override fun onStart() {
        super.onStart()


        birdButton.setOnClickListener {

            query = "bird"
            beatBox.play(bird)
            callbacks?.onButtonSelected(query)

        }
        dogButton.setOnClickListener {
            query = "dog"
            beatBox.play(dog)
            callbacks?.onButtonSelected(query)
        }
        catButton.setOnClickListener {
            query = "cat"
            beatBox.play(cat)
            callbacks?.onButtonSelected(query)

        }

        homepageButton.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://harvardartmuseums.org/")

            }.also{ intent -> startActivity(intent) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    companion object {
        fun newInstance(): HomepageFragment {
            return HomepageFragment()
        }
    }











    // pass api animal query



}