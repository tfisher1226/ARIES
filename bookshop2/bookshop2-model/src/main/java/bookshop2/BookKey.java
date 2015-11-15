package bookshop2;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BookKey", namespace = "http://bookshop2", propOrder = {
	"author",
	"title"
})
@XmlRootElement(name = "bookKey", namespace = "http://bookshop2")
public class BookKey implements Comparable<BookKey>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name = "author", namespace = "http://bookshop2", required = true)
	private String author;
	
	@XmlElement(name = "title", namespace = "http://bookshop2", required = true)
	private String title;
	
	
	public BookKey() {
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
	
	protected <T extends Comparable<T>> int compare(T value1, T value2) {
		if (value1 == null && value2 == null) return 0;
		if (value1 != null && value2 == null) return 1;
		if (value1 == null && value2 != null) return -1;
		int status = value1.compareTo(value2);
		return status;
	}
	
	@Override
	public int compareTo(BookKey other) {
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
		BookKey other = (BookKey) object;
		int status = compareTo(other);
		return status == 0;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
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
		return "BookKey: author="+author+", title="+title;
	}
	
}
