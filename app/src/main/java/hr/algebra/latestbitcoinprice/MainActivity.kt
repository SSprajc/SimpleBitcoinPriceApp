package hr.algebra.latestbitcoinprice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import hr.algebra.latestbitcoinprice.api.API_URL
import hr.algebra.latestbitcoinprice.api.PriceApi
import hr.algebra.latestbitcoinprice.api.PriceItem
import hr.algebra.latestbitcoinprice.databinding.ActivityMainBinding
import hr.algebra.latestbitcoinprice.model.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var items = mutableListOf<String>()
    private var itemsObj = mutableListOf<Item>()
    private var counter = 0

    private var priceApi: PriceApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        priceApi = retrofit.create(PriceApi::class.java)
    }

    private val executor = Executors.newSingleThreadScheduledExecutor()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listView = binding.listView
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items
        )
        listView.adapter = arrayAdapter


        executor.scheduleAtFixedRate({
            getData(arrayAdapter)
        }, 0, 60, TimeUnit.SECONDS)


    }

    private fun getData(arrayAdapter: ArrayAdapter<String>) {
        val request = priceApi.fetchItems()

        request.enqueue(object : Callback<PriceItem> {
            override fun onResponse(
                call: Call<PriceItem>,
                response: Response<PriceItem>
            ) {
                response.body()?.let {
                    populateList(it, arrayAdapter)
                }
            }

            override fun onFailure(call: Call<PriceItem>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateList(it: PriceItem, arrayAdapter: ArrayAdapter<String>) {
        if (counter < 50) {
            itemsObj.add(
                Item(
                    "${it.bpi.uSD.rate} ${it.bpi.uSD.code}",
                    it.time.updateduk,
                    it.time.updatedISO.substring(0, 19)
                )
            )
            itemsObj.sort()
            items.clear()
            itemsObj.forEach {
                items.add(it.toString())
            }
            arrayAdapter.notifyDataSetChanged()
            counter++
        } else {
            itemsObj.removeLast()
            itemsObj.add(
                Item(
                    "${it.bpi.uSD.rate} ${it.bpi.uSD.code}  $counter",
                    it.time.updateduk,
                    it.time.updatedISO.substring(0, 19)
                )
            )
            itemsObj.sort()
            items.clear()
            itemsObj.forEach {
                items.add(it.toString())
            }
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        executor.shutdown()
        super.onDestroy()
    }
}