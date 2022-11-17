package com.example.aop_part3_chapter06.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part3_chapter06.DBKey.Companion.DB_ARTICLES
import com.example.aop_part3_chapter06.R
import com.example.aop_part3_chapter06.databinding.FragmentHomeBinding
import com.example.aop_part3_chapter06.model.Article
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null

    //ArticleRecyclerView 관련
    private lateinit var articleAdapter: ArticleAdapter
    private val articleList = mutableListOf<Article>() //빈 자료

    //파이어베이스 인증
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    //파이어베이스 DB 관련
    private lateinit var articleDB: DatabaseReference
    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            //파이어베이스의 데이터를 어플에 추가 시,
            val article = snapshot.getValue(Article::class.java) //클래스 자체를 받아온다.
            article?.let { article ->
                articleList.add(article)
            }
            inputDataIntoAdapter(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

    //-----------------------------------------------------------------------------------라이프사이클
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //널러블을 막기 위해 임시 지역 변수 선언
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        //DB 초기화
        articleDB = Firebase.database.reference.child(DB_ARTICLES)

        //화면 초기화
        initView()

        //여기다가 listener를 설정하면 fragment를 옮겨다닐때마다 중복 생성의 위험이 있어서 listener를 전역 변수화 한다.
        articleDB.addChildEventListener(childEventListener)

        //플로팅 버튼
        fragmentHomeBinding.addFloatingBtn.setOnClickListener {
            //회원 가입한 경우 인증을 통해 회원만 글을 올리게 하겠다.
            if (auth.currentUser != null){
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
            } else{
                Snackbar.make(view,"로그인 후 사용해 주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        articleDB.removeEventListener(childEventListener)
    }

    //-------------------------------------------------------------------------------------사용자함수

    //프래그먼트 초기화
    private fun initView() {
        articleAdapter = ArticleAdapter()
        articleList.clear()
    }

    //어뎁터에 데이터 넣기
    private fun inputDataIntoAdapter(articleList: MutableList<Article>) {
        val articleAdapter = ArticleAdapter(articleList)
        binding?.articleRecyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.articleRecyclerView?.adapter = articleAdapter
    }

    //임시 데이터 생성
    private fun createTestData(): MutableList<Article> {
        return mutableListOf(
            Article("1234", "호롤1", 12345, "3000", ""),
            Article("1232", "호롤2", 12345, "5000", "")
        )
    }
}

