package com.example.mazay.fruit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mazay.fruit.models.FeedResponse
import com.example.mazay.fruit.models.ProfileResponse
import com.example.mazay.fruit.service.apiClient
import com.example.mazay.fruit.viewmodels.FeedViewModel
import kotlinx.android.synthetic.main.feed_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.provider.ContactsContract.CommonDataKinds.Note
import android.content.Intent
import android.widget.*
import java.io.Serializable
import android.widget.Spinner


class FeedFragment: Fragment() {
    //var userList: MutableList<ProfileResponse.User>? = mutableListOf()
    private lateinit var model: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


        model = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        model.loadUsers().observe(this, Observer{ users ->
            // update UI
            recyclerView.apply {
                // set a LinearLayoutManager to handle Android
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                // set the custom adapter to the RecyclerView
                Log.d("MainActivity",users.toString())
                adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
            }
        })

    }

/*
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.feed_layout, container, false)
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
    val v = inflater.inflate(R.layout.feed_layout, container, false)

    val majors = arrayOf("Computer Science", "Mathematics", "Biology", "Music", "Physics", "Statistics", "Communications")
    val classes = arrayOf("2019", "2020", "2021", "2022")
    val spinner = v.findViewById(R.id.spinner_major) as Spinner
    val spinnerClass = v.findViewById(R.id.spinner_class) as Spinner
    spinner.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,majors)
    spinnerClass.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,classes)

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {
            model.loadUsers().observe(viewLifecycleOwner, Observer{ users ->
                // update UI
                recyclerView.apply {
                    // set a LinearLayoutManager to handle Android
                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    // set the custom adapter to the RecyclerView
                    Log.d("MainActivity",users.toString())
                    adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                }
            })

        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if(p2 != 0){
                Toast.makeText(context,majors[p2], Toast.LENGTH_LONG).show()
                model.loadSelectedUsers().observe(viewLifecycleOwner, Observer{ users ->
                    // update UI
                    recyclerView.apply {
                        // set a LinearLayoutManager to handle Android
                        layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                        // set the custom adapter to the RecyclerView
                        Log.d("MainActivity",users.toString())
                        adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                    }
                })
            }
        }

    }

    /*
    val users = model.loadUsers().observe(this, Observer { users->


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    // set the custom adapter to the RecyclerView
                    //Log.d("MainActivity",users.toString())
                    adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                }
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context,majors[p2], Toast.LENGTH_LONG).show()
                recyclerView.apply {
                    // set a LinearLayoutManager to handle Android
                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    // set the custom adapter to the RecyclerView
                    //Log.d("MainActivity",users.toString())
                    adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                }
            }

        }

    })
    */
    /*
    spinner.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,majors)
    spinnerClass.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,classes)

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(p0: AdapterView<*>?) {

        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            Toast.makeText(context,majors[p2], Toast.LENGTH_LONG).show()
            recyclerView.apply {
                // set a LinearLayoutManager to handle Android
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                // set the custom adapter to the RecyclerView
                //Log.d("MainActivity",users.toString())
                adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
            }
        }

    }
    */

    return v
    }



    private fun userClicked(user : ProfileResponse.User) {
        //Toast.makeText(context, "Clicked: ${user.name}", Toast.LENGTH_LONG).show()
        val intent : Intent = Intent(context, PopupActivity::class.java)
        intent.putExtra("user",user as Serializable)
        startActivity(intent)
    }

}