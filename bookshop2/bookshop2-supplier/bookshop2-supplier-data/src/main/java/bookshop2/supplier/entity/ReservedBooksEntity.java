package bookshop2.supplier.entity;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;


@Entity(name = "ReservedBooks")
@Table(name = "reserved_books", uniqueConstraints = @UniqueConstraint(columnNames = "book_key"))
@Cache(usage = READ_WRITE)
public class ReservedBooksEntity extends AbstractBookEntity implements Serializable {
	
	@Column(name = "book_key", nullable = true, unique = true)
	private String bookKey;
	
	
	public String getBookKey() {
		return bookKey;
	}
	
	public void setBookKey(String bookKey) {
		this.bookKey = bookKey;
	}
}
