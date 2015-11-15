package bookshop2.supplier.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class AbstractBookEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(name = "bar_code", nullable = false, unique = true)
	private Long barCode;
	
	@Column(name = "author", nullable = false)
	private String author;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getBarCode() {
		return barCode;
	}
	
	public void setBarCode(Long barCode) {
		this.barCode = barCode;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		if (getId() != null)
			return getClass().getSimpleName()+"["+getId()+"] barCode="+getBarCode();
		return "getClass().getSimpleName(): barCode="+getBarCode();
	}
	
}
