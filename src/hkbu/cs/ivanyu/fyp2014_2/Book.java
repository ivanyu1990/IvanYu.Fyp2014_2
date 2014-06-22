package hkbu.cs.ivanyu.fyp2014_2;

import java.util.ArrayList;

public class Book {
	public Book(String title, String summary, ArrayList<String> author,
			ArrayList<String> tag, String price, String imageLink) {
		this.setTitle(title);
		this.setSummary(summary);
		this.setAuthor(author);
		this.setTag(tag);
		this.setPrice(price);
		this.setImageLink(imageLink);
	}

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ArrayList<String> getAuthor() {
		return author;
	}

	public void setAuthor(ArrayList<String> author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public ArrayList<String> getTag() {
		return tag;
	}

	public void setTag(ArrayList<String> tag) {
		this.tag = tag;
	}

	private String title;
	private String summary;
	private ArrayList<String> author;
	private String price;
	private String imageLink;
	private ArrayList<String> tag;
}
