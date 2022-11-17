package com.example.aop_part3_chapter06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment //안드로이드 x인지 확인할 것
import com.example.aop_part3_chapter06.chatList.ChatListFragment
import com.example.aop_part3_chapter06.home.HomeFragment
import com.example.aop_part3_chapter06.myPage.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val bottomNavigationView: BottomNavigationView by lazy {findViewById(R.id.bottom_navigation_view)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val chatListFragment = ChatListFragment()
        val myPageFragment = MyPageFragment()
        //초기 화면
        replaceFragment(homeFragment)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.chat_list -> replaceFragment(chatListFragment)
                R.id.my_page -> replaceFragment(myPageFragment)
            }
            true
//            true //왜 트루를 반환? 규칙처럼 해주자, 없으면 실행이 안됨.
        }
    }

    //프래그먼트 바꿔주는 함수
    private fun replaceFragment(fragment: Fragment) {
        //매니져 사용법 기억할 것
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragment_container, fragment)
                commit()
            }
    }
}