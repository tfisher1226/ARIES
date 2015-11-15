package bookshop2.supplier.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;


@Entity(name = "BookOrders")
@Table(name = "book_orders")
@Cache(usage = READ_WRITE)
public class BookOrdersEntity extends AbstractOrderEntity<BookOrdersBookEntity> implements Serializable {
}
