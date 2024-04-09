package com.example.chat.chatear

class Message {
    var message: String? = null
    var senderId: String? = null

    constructor() {}

    constructor(message: String?, senderid: String?) {
        this.message = message
        this.senderId = senderid
    }
}