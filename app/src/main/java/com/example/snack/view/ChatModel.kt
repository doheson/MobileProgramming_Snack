package com.example.snack.view

class ChatModel {
    var users=HashMap<String?,Boolean>() //채팅방 유저
    var comments: Map<String, Comment> = HashMap() //채팅 메시지

    class Comment {
        var uid: String? = null
        var message: String? = null
        var timestamp: Any? = null
    }
}