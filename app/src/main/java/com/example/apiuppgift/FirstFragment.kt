package com.example.apiuppgift

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.apiuppgift.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var UserName: String = ""
        var pass: String= ""





        binding.buttonFirst.setOnClickListener {

            UserName  = binding.editTextTextEmailAddress.text.toString().filter {  !it.isWhitespace()  }
            pass = binding.editTextTextPassword.text.toString().filter {  !it.isWhitespace()  }
            Log.d("user", pass)


            loginCheck(UserName, pass)

        }
    }
    fun loginCheck(UserName: String ,  pass: String){
        var auth: FirebaseAuth = Firebase.auth
        Firebase.auth.signOut()
        var main: MainActivity = getParentFragment()?.getActivity() as MainActivity
        auth.signInWithEmailAndPassword("zorpid@gmail.com", "test123")
            .addOnCompleteListener(main) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Robin", "signInWithEmail:success")

                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Robin", "signInWithEmail:failure", task.exception)
                    Toast.makeText(main, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}