package com.teksystems.gdit.demo.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ApplicationsRepository /*TODO: Import SpringData's JPA dependencies. This is to
extend from CrusRepository <? extends @Entity, Long (usually PK type)>*/{

//  @Transactional(isolation = Isolation.READ_UNCOMMITTED) <--- This is needed for rollbacks and preserve Idempotence
//  Optional<? extends @Entity> findyByWhateverAttribute() {}
//
//  @Transactional(isolation = Isolation.READ_UNCOMMITTED) <--- This is needed for rollbacks and preserve Idempotence
//  @Query(value = "SQL statement with projection columns mapped to resulting interface (include getters/setters)")
//  Optional<interface Result> getResultByWhateverAttribute() {}

}
