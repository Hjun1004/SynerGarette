package com.ll.synergarette.boundedContext.mypage.repository;

import com.ll.synergarette.boundedContext.mypage.entity.MyPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPage, Long> {
}
