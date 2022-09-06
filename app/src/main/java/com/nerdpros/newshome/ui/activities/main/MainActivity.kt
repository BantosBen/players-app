package com.nerdpros.newshome.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nerdpros.newshome.R
import com.nerdpros.newshome.adapter.PlayerAdapter
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.data.remote.response.Player
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.databinding.ActivityMainBinding
import com.nerdpros.newshome.ui.activities.GenderActivity
import com.nerdpros.newshome.ui.activities.profile.ProfileActivity
import com.nerdpros.newshome.util.CustomDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private var playerAdapter = PlayerAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = playerAdapter
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            fetchPlayers()
        }

        setUpObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuPlayerGender -> {
                startActivity(Intent(baseContext, GenderActivity::class.java))
            }
            R.id.menuProfile -> {
                startActivity(Intent(baseContext, ProfileActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        fetchPlayers()
    }

    private fun fetchPlayers() {
        viewModel.getPlayers()
    }

    private fun setUpObservers() {
        viewModel.playerResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.txtErrorMessage.visibility = View.GONE
                    CustomDialog.show(
                        "Fetching Players",
                        "Please wait...",
                        supportFragmentManager
                    )
                }
                else -> {
                    CustomDialog.dismiss()
                    when (response) {
                        is Resource.Success -> {
                            val value = response.value
                            if (!value.error||value.players.isNullOrEmpty()) {
                                val players = arrayListOf<Player>()
                                players.addAll(value.players!!)
                                playerAdapter.updateDataset(players)
                            } else {
                                binding.txtErrorMessage.text = value.message
                                binding.txtErrorMessage.visibility = View.VISIBLE
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) { fetchPlayers() }
                    }
                }
            }
        }
    }
}