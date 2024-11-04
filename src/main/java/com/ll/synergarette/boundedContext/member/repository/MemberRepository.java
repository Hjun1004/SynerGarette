package com.ll.synergarette.boundedContext.member.repository;

import com.ll.synergarette.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

//    @Query("SELECT m FROM Member m JOIN FETCH m.myPage JOIN FETCH m.deliveryAddressesList WHERE m.username = :username")
//    Optional<Member> findByUsername(@Param("username") String username);

}
