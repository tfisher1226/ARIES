package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.aries.common.AbstractCriteria;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookCriteria", namespace = "http://bookshop2", propOrder = {
	"author",
	"title",
	"price",
	"quantity"
})
@XmlRootElement(name = "bookCriteria", namespace = "http://bookshop2")
public class BookCriteria extends AbstractCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "author", namespace = "http://bookshop2", required = true)
	private String author;
	
	@XmlElement(name = "title", namespace = "http://bookshop2", required = true)
	private String title;
	
	@XmlElement(name = "price", namespace = "http://bookshop2", required = true)
	private Double price;
	
	@XmlElement(name = "quantity", namespace = "http://bookshop2")
	private Integer quantity;
	
	
	public BookCriteria() {
		//nothing for now
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
}
