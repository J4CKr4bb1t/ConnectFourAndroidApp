/*
    SeSe Allerheiligen
    2/24/24
    Connect Four App
    Splash screen for the game. Allows user to input their name before starting
 */
package com.hfad.connectfourapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //inflates the view of this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        val startButton = view.findViewById<Button>(R.id.start)
        val playerName = view.findViewById<EditText>(R.id.playerName)

        //if pressed, perform action
        startButton.setOnClickListener {
            val playerNameText = playerName.text.toString()
            val action = SplashFragmentDirections.actionSplashFragmentToBoardFragment(playerNameText)
            view.findNavController().navigate(action)
        }

        return view;
    }
}