package com.tenco.blog_v1.board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

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
