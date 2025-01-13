package com.example.coffee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.coffee.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Menemukan tombol di layout
        val btnGoHome: Button = rootView.findViewById(R.id.btn_go_home)

        // Menangani klik tombol
        btnGoHome.setOnClickListener {
            // Membuka HomeActivity
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}
