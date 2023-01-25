package de.ollie.counter.ws.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.counter.ws.persistence.entity.CounterHistoryDBO;
import lombok.Generated;
import java.util.List;

import de.ollie.counter.ws.persistence.entity.CounterDBO;
import de.ollie.counter.ws.persistence.entity.UserDBO;

/**
 * A generated JPA repository for counter_historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface CounterHistoryGeneratedDBORepository extends JpaRepository<CounterHistoryDBO, Long> {

	List<CounterHistoryDBO> findAllByCounter(CounterDBO counter);

	List<CounterHistoryDBO> findAllByUser(UserDBO user);

}