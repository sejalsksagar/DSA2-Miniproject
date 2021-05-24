package miniProject;

public class Tweet
{
	String createdAt; // UTC time when this Tweet was created.
	String text;
	User U; // The user who posted this Tweet.
	Tweet retweetedStatus; // This attribute contains a representation of the original Tweet that was
							// retweeted.
	int retweetCount; // Number of times this Tweet has been retweeted.
	int favoriteCount; // Indicates approximately how many times this Tweet has been liked by Twitter users.
	boolean favorited; // Indicates whether this Tweet has been liked by the authenticating user.
	boolean retweeted; // Indicates whether this Tweet has been Retweeted by the authenticating user.
}