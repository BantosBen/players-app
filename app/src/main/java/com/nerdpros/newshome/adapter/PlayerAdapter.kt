package com.nerdpros.newshome.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.nerdpros.newshome.R
import com.nerdpros.newshome.data.remote.response.Player
import com.nerdpros.newshome.ui.activities.player.PlayerProfileActivity
import com.nerdpros.newshome.util.getProgressDrawable
import com.nerdpros.newshome.util.loadImage
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @Author: Angatia Benson
 * @Date: 27/08/2022
 * Copyright (c) 2022 Bantechnis
 */
class PlayerAdapter(private val _context: Context, private val players: ArrayList<Player>) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var profileImage: CircleImageView
        private var txtName: TextView
        private var txtCountry: TextView
        private var playerLayout: ConstraintLayout

        init {
            profileImage = itemView.findViewById(R.id.profileImage)
            txtName = itemView.findViewById(R.id.txtName)
            txtCountry = itemView.findViewById(R.id.txtCountry)
            playerLayout = itemView.findViewById(R.id.playerLayout)
        }

        @SuppressLint("SetTextI18n")
        fun bind(player: Player, mContext: Context) {
            profileImage.loadImage(player.image_url, getProgressDrawable(mContext))
            txtName.text = "${player.first} ${player.last}"
            txtCountry.text = player.country
            playerLayout.setOnClickListener {
                val intent = Intent(mContext, PlayerProfileActivity::class.java)
                intent.putExtra("id", player.id)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_item,
                null,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(players[position], _context)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun updateDataset(_players: ArrayList<Player>) {
        players.clear()
        players.addAll(_players)
        notifyDataSetChanged()
    }
}