package com.tenco.blog_v2.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    // 두가지 방식으로 연습 -  JPQL 사용, JPA API
    @Transactional
    public void updateById(int id, String title, String content){
        //JPQL
        String jpql = " UPDATE Board b SET b.title :title , b.content = :content WHERE b.id = :id";
        Query query = em.createQuery(jpql);
        query.setParameter("title", title);
        query.setParameter("content", content);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public void updateByIdJPA(int id, String title, String content){
        Board board = em.find(Board.class, id);
        if (board != null){
            board.setTitle(title);
            board.setContent(content);
        }
        // flush 명령, commit 명형 할 필요 없이
        // 트랜잭션을 선언하면 --> 더티 체킹

    }


    /**
     * 게시글 조회 메서드
     * @param id 조회할 게시글 ID
     * @return 조회된 Board 엔티티, 존재하지않으면 null
     */
    public Board findById(int id){
        return em.find(Board.class, id);
    }

    /**
     * JPQL의 FETCH 조인 사용 - 성능 최적화
     * 한반에 쿼리를 사용해서 즉, 직접 조인해서 데이터를 기져옵니다.
     * @param id
     * @return
     */
    public Board findByIdJoinUser(int id){
        // JPQL -> Fetch join
        String jpql = " SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :id ";
        return em.createQuery(jpql, Board.class).setParameter("id", id).getSingleResult();
    }




}
