package com.assignment.ui.matchrequests

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.databinding.ActivityMatchRequestBinding
import com.assignment.utils.MatchReqStatus
import com.assignment.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MatchRequestActivity : AppCompatActivity(), UserListAdapter.UserItemListener, LifecycleOwner {

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserListAdapter
    private lateinit var binding: ActivityMatchRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(this)
        binding.usersRv.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            true
        )
        binding.usersRv.adapter = adapter
    }

    private fun setupObservers() {


        viewModel.users.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> {

                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })


    }


    override fun onAcceptClick(userId: String) {
        viewModel.updateUserStatus(userId, MatchReqStatus.ACCEPTED.VALUE)
        Toast.makeText(applicationContext, "Request Accepted", Toast.LENGTH_SHORT).show()

    }

    override fun onRejectClick(userId: String) {
        viewModel.updateUserStatus(userId, MatchReqStatus.REJECTED.VALUE)
        Toast.makeText(applicationContext, "Request Rejected", Toast.LENGTH_SHORT).show()
    }


}