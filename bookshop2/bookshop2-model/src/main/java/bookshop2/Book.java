package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Book", namespace = "http://bookshop2", propOrder = {
	"id",
	"barCode",
	"author",
	"title",
	"price",
	"quantity"
})
@XmlRootElement(name = "book", namespace = "http://bookshop2")
public class Book implements Comparable<Book>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "id", namespace = "http://bookshop2")
	private Long id;
	
	@XmlElement(name = "barCode", namespace = "http://bookshop2", required = true)
	private Long barCode;
	
	@XmlElement(name = "author", namespace = "http://bookshop2", required = true)
	private String author;
	
	@XmlElement(name = "title", namespace = "http://bookshop2", required = true)
	private String title;
	
	@XmlElement(name = "price", namespace = "http://bookshop2", required = true)
	private Double price;
	
	@XmlElement(name = "quantity", namespace = "http://bookshop2")
	private Integer quantity;
	
	
	public Book() {
		//nothing for now
	}
	
	
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(Book other) {
		int status = compare(author, other.author);
		if (status != 0)
			return status;
		status = compare(title, other.title);
		if (status != 0)
			return status;
		return 0;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (!object.getClass().isAssignableFrom(this.getClass()))
			return false;
		Book other = (Book) object;
		if (id != null)
			return id.equals(other.id);
		int status = compareTo(other);
		return status == 0;
	}

	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		int hashCode = 0;
		if (barCode != null)
			hashCode += barCode.hashCode();
		if (author != null)
			hashCode += author.hashCode();
		if (title != null)
			hashCode += title.hashCode();
		if (hashCode == 0)
			return super.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Book: barCode="+barCode+", author="+author+", title="+title;
	}

}
