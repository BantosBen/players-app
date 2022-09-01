package com.nerdpros.newshome.ui.activities.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nerdpros.newshome.R
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.data.remote.response.Player
import com.nerdpros.newshome.databinding.ActivityPlayerProfileBinding
import com.nerdpros.newshome.util.CustomDialog
import com.nerdpros.newshome.util.getProgressDrawable
import com.nerdpros.newshome.util.loadImage
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class PlayerProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerProfileBinding
    private val viewModel: PlayerProfileActivityViewModel by viewModels()
    private var playerId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playerId = intent.getStringExtra("id")!!
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationIcon(R.drawable.ic_close)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        setUpObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDelete -> {
                deletePlayer()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePlayer() {
        viewModel.deletePlayer(playerId)
    }

    override fun onStart() {
        super.onStart()
        fetchPlayer(playerId)
    }

    private fun fetchPlayer(playerId: String) {
        viewModel.getPlayerDetails(playerId)
    }

    private fun setUpObservers() {
        viewModel.detailsResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Fetching Player Details",
                        "Please wait...",
                        supportFragmentManager
                    )
                }
                else -> {
                    CustomDialog.dismiss()
                    when (response) {
                        is Resource.Success -> {
                            val value = response.value
                            if (!value.error) {
                                displayData(value.player)
                            } else {
                                CustomDialog.toast(value.message)
                                finish()
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) { fetchPlayer(playerId) }
                    }
                }
            }

        }

        viewModel.deleteResponse.observe(this) { response ->
            when (response) {
                is Resource.Loading -> {
                    CustomDialog.show(
                        "Remove player",
                        "Please wait...",
                        supportFragmentManager
                    )
                }
                else -> {
                    CustomDialog.dismiss()
                    when (response) {
                        is Resource.Success -> {
                            val value = response.value
                            CustomDialog.toast(value.message)
                            if (!value.error) {
                                finish()
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) { fetchPlayer(playerId) }
                    }
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayData(player: Player) {
        binding.profileImage.loadImage(player.image_url, getProgressDrawable(baseContext))
        binding.txtRank.text = player.rank
        binding.txtName.text = "${player.first} ${player.last}"
        binding.txtCountry.text = player.country
        binding.txtAge.text = "${player.age} yrs old (${player.gender})"
        binding.txtPoints.text = "${applyPattern(player.points.toInt())} points"
        binding.txtDescription.text = player.description
    }

    private fun applyPattern(d: Int): String? {
        val numberFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
        numberFormat.applyPattern("#,###")
        return numberFormat.format(d)
    }
}