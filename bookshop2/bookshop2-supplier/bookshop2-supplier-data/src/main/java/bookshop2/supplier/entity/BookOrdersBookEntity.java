package bookshop2.supplier.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.OnDelete;


@Entity(name = "BookOrdersBook")
@Table(name = "book_orders_book")
@Cache(usage = READ_WRITE)
public class BookOrdersBookEntity extends AbstractBookEntity implements Serializable {
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = true)
	@ForeignKey(name = "book_order_fk")
	@OnDelete(action = CASCADE)
	@Cache(usage = READ_WRITE)
	private BookOrdersEntity order;

	
	public BookOrdersEntity getOrder() {
		return order;
	}

	public void setOrder(BookOrdersEntity order) {
		this.order = order;
	}
}
