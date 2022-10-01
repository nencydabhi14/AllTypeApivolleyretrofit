package com.example.apicalling.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apicalling.ModelDataItem
import com.example.apicalling.R
import com.example.apicalling.retrofit.MyAdaptor
import com.example.apicalling.databinding.ActivityMainBinding
import com.example.apicalling.retrofit.ApiClient
import com.example.apicalling.retrofit.ApiInterface
import com.example.apicalling.retrofit.ProductApiModelItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var URL_Post = "https://jsonplaceholder.typicode.com/posts"
    var Gson_URL = "https://jsonplaceholder.typicode.com/users"
    var Api_Post = "https://reqres.in/api/users"
    var requestQueue: RequestQueue? = null
    var list = arrayListOf<ApiModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        binding.btnResponse.setOnClickListener {
            binding.progressBar.isVisible = true
            retrofitcallapi()
        }
    }

    fun retrofitcallapi() {
        var apiInterface = ApiClient().getRetrofit().create(ApiInterface::class.java)
        apiInterface.getProduct().enqueue(object : Callback<List<ProductApiModelItem>> {
            override fun onResponse(
                call: Call<List<ProductApiModelItem>>,
                response: retrofit2.Response<List<ProductApiModelItem>>
            ) {
                var list = response.body()!!
                binding.responseTxt.text = list[0].description
                Log.e("TAG", "product: ${list}")
                binding.progressBar.isVisible = false

                setUPrv(list)
            }

            override fun onFailure(call: Call<List<ProductApiModelItem>>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })

        apiInterface.getCarts().enqueue(object : Callback<List<CartsApiModelItem>> {
            override fun onResponse(
                call: Call<List<CartsApiModelItem>>,
                response: retrofit2.Response<List<CartsApiModelItem>>
            ) {
                var list = response.body()
                Log.e("TAG", "cart: ${list}")
            }

            override fun onFailure(call: Call<List<CartsApiModelItem>>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }

    fun setUPrv(list: List<ProductApiModelItem>) {
        var adaptor = MyAdaptor(this@MainActivity, list)
        var lm = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.adapter = adaptor

        if (adaptor==null){
            binding.progressBar.isVisible = true
        }
    }

    fun Post_Api(name: String, Job: String) {
        requestQueue = Volley.newRequestQueue(this)

        var stringRequest = object : StringRequest(Request.Method.POST, Api_Post,
            Response.Listener { response ->
                Toast.makeText(this, "Successfully Create Job", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "Post_Api: $response")
            },
            Response.ErrorListener { error -> Log.e("TAG", "Post_Api: ${error.message}") })
        {
            override fun getParams(): MutableMap<String, String>? {

                var map = HashMap<String, String>()
                map["name"] = "$name"
                map["job"] = "$Job"

                return map
            }
        }
        requestQueue?.add(stringRequest)

    }

    fun apicallingpost() {

        requestQueue = Volley.newRequestQueue(this)

        var jsonArrayRequest = JsonArrayRequest(Request.Method.GET, URL_Post, null,
                { response ->
                    binding.responseTxt.text = response.toString()
                    Log.e("TAG", "apicalling: ${response}")

                    var i = 0
                    while (i < response.length()) {
                        var userid = response.getJSONObject(i).getString("userId")
                        var id = response.getJSONObject(i).getString("id")
                        var title = response.getJSONObject(i).getString("title")
                        var body = response.getJSONObject(i).getString("body")

                        var model = ApiModel(userid, id, title, body)
                        list.add(model)

                        Log.e("TAG", "======================apicalling: $userid  $id $title $body")
                        i++
                    }

                }, { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                })
        requestQueue?.add(jsonArrayRequest)

    }

    fun Api_Gson() {

        requestQueue = Volley.newRequestQueue(this)

        var jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            Gson_URL,
            null,
            { response ->
                Log.e("TAG", "Api_Gson: $response")
                var type = object : TypeToken<List<ModelDataItem>>() {}.type
                var data = Gson().fromJson<List<ModelDataItem>>(response.toString(), type)

                Log.e("TAG", "Api_Gson: $data")
                binding.responseTxt.text = data.toString()
            },
            { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            })
        requestQueue?.add(jsonArrayRequest)
    }


}