package com.example.chatapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.chatapp.databinding.FragmentLoginBinding
import io.realm.mongodb.Credentials

class LoginFragment : Fragment(R.layout.fragment_login), AdapterView.OnItemSelectedListener {

    private val loginViewmodel: LoginViewmodel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _partition = "temp"
        binding = FragmentLoginBinding.bind(view)
        binding.roomPasswordInput.visibility = View.INVISIBLE
//        loginViewmodel.delete()

        // Init the spinner
        val spinner = binding.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, loginViewmodel.roomList)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        // Connect button logic
        binding.confirmConnectButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                if (!binding.loginUsername.text.contains(" ")) {
                    if (isPrivate()) {
                        showPasswordInput()
                    } else {
                        connect()
                    }
                } else {
                    Toast.makeText(requireContext(), "Username cannot contain whitespace.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.makeNewRoomButton.setOnClickListener {
            if (binding.loginUsername.text.isNotEmpty()) {
                val action = LoginFragmentDirections.actionLoginFragmentToNewRoomFragment(binding.loginUsername.text.toString())
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "Username cannot be empty.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.roomPasswordInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val passwd = binding.roomPasswordInput.text.toString()
                binding.roomPasswordInput.text.clear()
                checkPassword(passwd)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun isPrivate(): Boolean {
        return loginViewmodel.roomList[loginViewmodel.index]!!.isPrivate
    }

    private fun showPasswordInput() {
        binding.roomPasswordInput.apply {
            showSoftKeyboard(this)
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    private fun connect() {
        chatApp.loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                _partition = loginViewmodel.roomList[loginViewmodel.index]!!.name // Change the chat room
                val username = binding.loginUsername.text.toString()
                val intent = Intent(this.requireContext(), ChatActivity::class.java)
                intent.putExtra("username", username)//send the username from input
                startActivity(intent)
            } else {
                Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPassword(password: String) {
        if (password == loginViewmodel.roomList[loginViewmodel.index]!!.password) {
            binding.roomPasswordInput.visibility = View.INVISIBLE
            hideSoftKeyboard(binding.roomPasswordInput)
            connect()
        } else {
            Toast.makeText(requireContext(), "Incorrect password.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSoftKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideSoftKeyboard(view: View) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        loginViewmodel.index = parent!!.selectedItemPosition
        if (!loginViewmodel.roomList[loginViewmodel.index]!!.isPrivate) {
            binding.roomPasswordInput.visibility = View.INVISIBLE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}