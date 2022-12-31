package de.ollie.counter.ws.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ollie.counter.ws.persistence.entity.CounterDBO;
import lombok.Generated;
import java.util.List;

import de.ollie.counter.ws.persistence.entity.UserDBO;

/**
 * A generated JPA repository for counters.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Generated
@Repository
public interface CounterGeneratedDBORepository extends JpaRepository<CounterDBO, Long> {

	List<CounterDBO> findAllByUser(UserDBO user);

}