package com.example.apiuppgift

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apiuppgift.databinding.FragmentSecondBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firestore.v1.Value

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    val db = Firebase.firestore
    private lateinit var ub : FirebaseFirestore
    val users = db.collection("users")
    var te :String =""
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var myAdapter: MyAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventChangeListener()



        users
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("robin", "${document.id} => ${document.data}")
                    te =document.data.toString()
                    Log.d("Robin", te)

                }
            }
            .addOnFailureListener { exception ->
                Log.w("robin", "Error getting documents.", exception)
            }
        Log.d("Robin","test")
        Log.d("Joel", "detta körs")

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.ButtonA.setOnClickListener {
            findNavController().navigate(R.id.FragmentA)
        }
        binding.ButtonB.setOnClickListener {
            findNavController().navigate(R.id.FragmentB)
        }
        recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()
        myAdapter = MyAdapter(userArrayList)
        recyclerView.adapter =myAdapter


        


    }

    private fun EventChangeListener() {
        Log.d("Joel", "detta körs")
        ub = FirebaseFirestore.getInstance()
        ub.collection("users").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error !=null){
                    Log.e("Firestore Error", error.message.toString())
                    return
                }

                for (dc : DocumentChange in value?.documentChanges!!){
                    Log.d("Joel", "detta körs loop")
                    if (dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(User::class.java))
                    }

                }
                myAdapter.notifyDataSetChanged()
            }


        })



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}