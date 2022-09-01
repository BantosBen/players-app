package com.nerdpros.newshome.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nerdpros.newshome.adapter.PlayerAdapter
import com.nerdpros.newshome.data.remote.network.handleApiError
import com.nerdpros.newshome.data.remote.response.Player
import com.nerdpros.newshome.data.remote.response.Resource
import com.nerdpros.newshome.databinding.FragmentFemaleBinding

class FemaleFragment : Fragment() {
    private lateinit var binding: FragmentFemaleBinding
    private val viewModel: GenderViewModel by viewModels()
    private lateinit var playerAdapter: PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFemaleBinding.inflate(inflater, container, false)
        playerAdapter = PlayerAdapter(requireContext(), arrayListOf())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playerAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.playerResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    binding.loader.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.txtErrorMessage.visibility = View.GONE
                }
                else -> {
                    binding.loader.visibility = View.GONE
                    when (response) {
                        is Resource.Success -> {
                            val value = response.value
                            if (!value.error || value.players.isNullOrEmpty()) {
                                binding.recyclerView.visibility = View.VISIBLE
                                val players = arrayListOf<Player>()
                                players.addAll(value.players!!)
                                playerAdapter.updateDataset(players)
                            } else {
                                binding.txtErrorMessage.text = value.message
                                binding.txtErrorMessage.visibility = View.VISIBLE
                            }
                        }
                        else -> handleApiError(response as Resource.Failure) {
                            viewModel.getPlayersByGender(
                                "W"
                            )
                        }
                    }
                }
            }
        }

        viewModel.getPlayersByGender("W")
    }


}