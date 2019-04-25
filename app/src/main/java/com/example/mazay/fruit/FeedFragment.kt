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

    val majors = arrayOf("All", "Accounting", "Actuarial Science", "Africana Studies", "American Literature", "Anthropology", "Art: Electronic or Studio", "Art History", "Biology", "Business Administration", "Chemistry", "Childhood Studies", "Communications", "Computational and Integrative Biology", "Computer Science", "Criminal Justice", "Digital Studies", "Economics", "Education", "Engineering", "English", "English Literature", "Ethics", "European Studies", "Film Studies", "Finance", "French", "Gender Studies", "General Science", "German", "Health Sciences", "History", "Human Resource Management", "International Business", "Journalism", "Latin American and Latino Studies", "Learning Abroad", "Legal Studies", "Liberal Studies", "Management", "Management Information Systems and Ecommerce", "Marketing", "Mathematics", "Museum Studies", "Music", "National Security Studies", "Nursing", "Nursing-School Nurse", "Philosophy", "Physics", "Political Science", "Pre-Dental", "Pre-Law", "Pre-Medicine", "Pre-Veterinary", "Psychology", "Religion", "Social Work", "Sociology", "Spanish", "Statistics", "Student-proposed", "Teacher Preparation", "Theater Arts", "Urban Studies", "Womens and Gender Studies")
    //val majors = model.loadMajors()
    val classes = arrayOf("All", "2019", "2020", "2021", "2022", "2023", "2024", "2025")
    //val classes = model.loadYears()
    Log.d("FeedFragment", classes.toString())
    val spinner = v.findViewById(R.id.spinner_major) as Spinner
    val spinnerClass = v.findViewById(R.id.spinner_class) as Spinner
    val filterButton = v.findViewById(R.id.button_filter) as Button
    spinner.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,majors)
    spinnerClass.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,classes)

    filterButton.setOnClickListener {
        val major = spinner.selectedItem.toString()
        val year = spinnerClass.selectedItem.toString()

        if(year == "All" && major != "All"){
            model.loadUsersByMajors(major).observe(viewLifecycleOwner, Observer{ users ->
                // update UI
                if(users!!.isEmpty()){
                    Toast.makeText(context, "No users in the chosen category.", Toast.LENGTH_LONG).show()
                }
                recyclerView.apply {
                    // set a LinearLayoutManager to handle Android
                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    // set the custom adapter to the RecyclerView
                    Log.d("MainActivity",users.toString())
                    adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                }
            })
        }else if(major == "All" && year != "All"){
            model.loadUsersByYears(year).observe(viewLifecycleOwner, Observer{ users ->
                // update UI
                if(users!!.isEmpty()){
                    Toast.makeText(context, "No users in the chosen category.", Toast.LENGTH_LONG).show()
                }
                recyclerView.apply {
                    // set a LinearLayoutManager to handle Android
                    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    // set the custom adapter to the RecyclerView
                    Log.d("MainActivity",users.toString())
                    adapter = CustomAdapter(users, { user : ProfileResponse.User -> userClicked(user) })
                }
            })
        }else if(major == "All" && year == "All"){
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
        }else{
            model.loadSelectedUsers(major, year).observe(viewLifecycleOwner, Observer{ users ->
                // update UI
                if(users!!.isEmpty()){
                    Toast.makeText(context, "No users in the chosen category.", Toast.LENGTH_LONG).show()
                }
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

    return v
    }



    private fun userClicked(user : ProfileResponse.User) {
        //Toast.makeText(context, "Clicked: ${user.name}", Toast.LENGTH_LONG).show()
        val intent : Intent = Intent(context, PopupActivity::class.java)
        intent.putExtra("user",user as Serializable)
        startActivity(intent)
    }

}