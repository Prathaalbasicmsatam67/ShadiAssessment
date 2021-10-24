package com.assignment.ui.entities

data class UserUIModel(  val name: String,
                         val age : Int,
                         val city : String,
                         val picURL: String,
                         val serverUUID: String?,
                         val userApproval: Int)