class Publication {

	private final String title;

	public Publication(String title) {
		this.title = title;
	}

	public final String getInfo() {
		return getType() + ": " + getDetails();
	}

	public String getType() {
		return "Publication";
	}

	public String getDetails() {
		return title;
	}

}

class Newspaper extends Publication {

	private final String source;

	public Newspaper(String title, String source) {
		super(title);
		this.source = source;
	}

	@Override
	public String getType() {
		return "Newspaper (source - " + source + ")";
	}

	@Override
	public String getDetails() {
		return super.getDetails();
	}
}

class Article extends Publication {

	private final String author;

	public Article(String title, String author) {
		super(title);
		this.author = author;
	}

	@Override
	public String getType() {
		return "Article (author - " + author + ")";
	}

	@Override
	public String getDetails() {
		return super.getDetails();
	}
}

class Announcement extends Publication {

	private final int daysToExpire;

	public Announcement(String title, int daysToExpire) {
		super(title);
		this.daysToExpire = daysToExpire;
	}

	@Override
	public String getType() {
		return "Announcement (days to expire - " + daysToExpire + ")";
	}

	@Override
	public String getDetails() {
		return super.getDetails();
	}
}