package kr.ac.green;

import java.io.File;
import java.io.Serializable;

public class Book implements Serializable{
	private File cover;
	private String title;
	private String author;
	private String company;
	private String pages;
	private boolean read;
	private String dateFrom;
	private String dateTo;
	private double rate;
	
	private String review = "";

	public Book(String title) {
		this.title = title;
	}
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Book(File cover, String title, String author, String company, String pages, boolean read, String dateFrom, String dateTo,
			double rate) {
		super();
		this.cover = cover;
		this.title = title;
		this.author = author;
		this.company = company;
		this.pages = pages;
		this.read = read;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.rate = rate;
	}

	public File getCover() {
		return cover;
	}

	public void setCover(File cover) {
		this.cover = cover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}
	public boolean getRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {
		return "Book [cover=" + cover + ", title=" + title + ", author=" + author + ", company=" + company + ", pages="
				+ pages + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", rate=" + rate + ", review=" + review
				+ "]";
	}

	@Override
	public boolean equals(Object o) {
		boolean flag = false;
		if(o == null || !(o instanceof Book)) {
			return false;
		}
		Book book = (Book)o;
		if(title.equals(book.getTitle()) && author.equals(book.getAuthor())) {
			flag = true;
		}
//		else if(title.equals(book.getTitle())) {
//			flag = true;
//		}
		return flag;
	}
	
	
}
