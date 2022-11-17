package com.example.aop_part3_chapter06.model

data class Article (
    val sellerId : String,
    val title : String,
    val createdAt : Long,
    val price : String,
    val imgUrl : String
){  //파이어베이스 임의 값을 주어 DB에 저장되려면 빈 생성자가 있어야함. 이유는 잘 모르겠음..
    constructor():this("","",0,"","")
}