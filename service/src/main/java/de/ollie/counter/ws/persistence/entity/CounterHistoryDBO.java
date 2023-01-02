package de.ollie.counter.ws.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

/**
 * A DBO for counter_historys.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(chain = true)
@Data
@Generated
@Entity(name = "CounterHistory")
@Table(name = "COUNTER_HISTORY")
public class CounterHistoryDBO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;
	@JoinColumn(name = "COUNTER", nullable = false, referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private CounterDBO counter;
	@JoinColumn(name = "USER", nullable = false, referencedColumnName = "ID")
	@ManyToOne(fetch = FetchType.EAGER)
	private UserDBO user;
	@Column(name = "CURRENT_VALUE", nullable = false)
	private int currentValue;
	@Column(name = "LAST_COUNTER_EVENT")
	private LocalDateTime lastCounterEvent;
	@Column(name = "NAME", nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "PERIOD")
	private PeriodDBO period;
	@Column(name = "SORT_ORDER", nullable = false)
	private int sortOrder;
	@Column(name = "TIMESTAMP", nullable = false)
	private LocalDateTime timestamp;
	@Column(name = "VALUE_TO_DEVIDE")
	private Double valueToDevide;
	@Enumerated(EnumType.STRING)
	@Column(name = "VIEW_MODE", nullable = false)
	private ViewModeDBO viewMode;

}