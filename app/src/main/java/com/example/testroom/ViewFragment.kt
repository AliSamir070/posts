package com.example.testroom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testroom.databinding.FragmentViewBinding
import com.example.testroom.util.CheckNetwork
import com.example.testroom.util.NetworkState
import com.example.testroom.util.PostsState
import kotlin.math.log


class ViewFragment : Fragment() {
    lateinit var binding:FragmentViewBinding
    lateinit var postsDao:PostsDatabaseDao
    lateinit var checkNetwork:CheckNetwork
    val model:PostsModel by viewModels(){
        PostsModelFactory(Repository(postsDao))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetwork = CheckNetwork(requireActivity().application)
        postsDao = PostsDatabase.getDatabase(requireContext()).dao
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter()
        binding.postList.adapter = adapter
        binding.postList.layoutManager = LinearLayoutManager(requireContext())
        checkNetwork.observe(viewLifecycleOwner){
            Log.d("TAG", "check ")
            model.GetPosts(it)
        }
        model.postsList.observe(viewLifecycleOwner){
            Log.d("TAG", "submit ")
            adapter.submitList(it)
        }
        model.state.observe(viewLifecycleOwner){
            if(it == PostsState.Loading){
                Log.d("TAG", "Loading ")
                binding.loadingLayout.root.visibility = View.VISIBLE
            }else if (it == PostsState.Done){
                Log.d("TAG", "Done")
                binding.loadingLayout.root.visibility = View.INVISIBLE
            }else{
                Log.d("TAG", "Error ")
                binding.loadingLayout.root.visibility = View.INVISIBLE
            }
        }

    }
}