package com.example.testroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testroom.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val postsDao = PostsDatabase.getDatabase(requireContext()).dao
    val model:PostsModel by viewModels(){
        PostsModelFactory(Repository(postsDao))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PostsAdapter()
        binding.postList.adapter = adapter
        binding.postList.layoutManager = LinearLayoutManager(requireContext())
        /*binding.addBtn.setOnClickListener {
            val userId = binding.useridEt.text.toString().toInt()
            val title = binding.postTitleET.text.toString()
            val desc = binding.descEt.text.toString()
            val post = Post(userId = userId , title = title , body = desc)
            model.AddOnePost(post)
            model.GetPosts()
        }*/
        model.postsList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
}