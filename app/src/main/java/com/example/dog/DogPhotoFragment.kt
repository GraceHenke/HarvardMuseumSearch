package com.example.dog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dog.api.DogApi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "DogPhotoFragment"
private const val ARG_QUERY = "dog"
class DogPhotoFragment: Fragment() {
    private lateinit var dogRecyclerView: RecyclerView
    private lateinit var dogPhotoViewModel: DogPhotoViewModel
    private var adapter: PhotoAdapter? = PhotoAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sentQuery: String = arguments?.getSerializable(ARG_QUERY) as String

       dogPhotoViewModel = ViewModelProviders.of(this, DogViewModelFactory(sentQuery)).get(DogPhotoViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val sentQuery: String = arguments?.getSerializable(ARG_QUERY) as String


        //dogPhotoViewModel = ViewModelProviders.of(this, DogViewModelFactory(sentQuery)).get(DogPhotoViewModel::class.java)

        dogPhotoViewModel.dogItemLiveData.observe(
            viewLifecycleOwner,
            Observer { dogItems ->
                dogItems?.let {  uI(dogItems) }

                //dogRecyclerView.adapter = PhotoAdapter(dogItems)
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dog_photos, container, false)


        dogRecyclerView = view.findViewById(R.id.photo_recycler_view)
        dogRecyclerView.layoutManager= GridLayoutManager(context, 2)
        dogRecyclerView.adapter = adapter

        return view
    }



    private inner class PhotoHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
            private lateinit var photo: DogPhotoItem
            val titleView: TextView = itemView.findViewById(R.id.art_title)
            val descriptionView: TextView = itemView.findViewById(R.id.description)
            val animalImage: ImageView = itemView.findViewById(R.id.art_image)




        init {
            itemView.setOnClickListener(this)


        }


        fun bind (item: DogPhotoItem)
         {
             this.photo = item

             if (item.title.isNullOrBlank()) {
                 item.title = "Unknown Title"
             }

             titleView.text = item.title


             descriptionView.text = item.description



            Picasso.get()
                .load(item.imageURL)
                //.resize(600,600)
                .resize(125, 125)
                .into(animalImage)
        }

        override fun onClick(p0: View?) {

            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(photo.url)

            }.also{ intent -> startActivity(intent) }


        }





    }
        private fun uI (dogs: List<DogPhotoItem>) {
            adapter = PhotoAdapter(dogs)
            dogRecyclerView.adapter = adapter
        }


    private inner class PhotoAdapter(private val dogItems: List<DogPhotoItem>)
        : RecyclerView.Adapter<PhotoHolder> () {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
           val view = layoutInflater.inflate(R.layout.photo_layout, parent, false)
            return PhotoHolder(view)
        }

        override fun getItemCount(): Int = dogItems.size

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val dogItem = dogItems[position]
            holder.bind(dogItem)

        }




        }







    companion object {
        fun newInstance(query: String): DogPhotoFragment {
        val args = Bundle().apply { putSerializable(ARG_QUERY, query) }
        return DogPhotoFragment().apply {arguments = args}
    }
    }

}