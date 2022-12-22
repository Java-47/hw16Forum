package telran.forum.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.function.Predicate;

public class ForumImpl implements Forum {

	private Post[] posts;
	private int size;

	public ForumImpl() {
		posts = new Post[0];
	}

	@Override
	public boolean addPost(Post post) {
		posts = Arrays.copyOf(posts, posts.length + 1);

		if (getPostById(post.getPostId()) != null || post == null) {
			return false;
		}
		int index = Arrays.binarySearch(posts, 0, size, post);
		index = index >= 0 ? index : -index - 1;
		System.arraycopy(posts, index, posts, index + 1, size - index);
		posts[index] = post;
		size++;
		return true;
	}

	@Override
	public boolean removePost(int postId) {
		Post pattern = new Post(null, postId, null, null, null, 0);
		int index = search(pattern);
		if (index >= 0) {
			System.arraycopy(posts, index + 1, posts, index, posts.length - index - 1);
			posts[posts.length - 1] = null;
			size--;
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePost(int postId, String newContent) {
		Post post = getPostById(postId);
		if (post == null) {
			return false;
		}
		post.setContent(newContent);
		return true;
	}

	@Override
	public Post getPostById(int postId) {
		Post pattern = new Post(null, postId, null, null, null, 0);
		int index = search(pattern);
		if (index >= 0) {
			return posts[index];
		}
		return null;
	}

	@Override
	public Post[] getPostsByAuthor(String author) {
		return findByPredicate(p -> p.getAuthor() == author);
	}

	@Override
	public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
		Post[] authorsArray = getPostsByAuthor(author);

		Post pattern = new Post(author, Integer.MIN_VALUE, null, null, dateFrom.atStartOfDay(), 0);
		int from = -Arrays.binarySearch(authorsArray, 0, authorsArray.length, pattern) - 1;
		pattern = new Post(author, Integer.MAX_VALUE, null, null, LocalDateTime.of(dateTo, LocalTime.MAX), 0);
		int to = -Arrays.binarySearch(authorsArray, 0, authorsArray.length, pattern) - 1;
		return Arrays.copyOfRange(authorsArray, from, to);
	}

	@Override
	public int size() {

		return size;
	}

	private Post[] findByPredicate(Predicate<Post> predicate) {
		Post[] res = new Post[size];
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (predicate.test(posts[i])) {
				res[j++] = posts[i];
			}
		}
		return Arrays.copyOf(res, j);
	}

	private int search(Post post) {
		for (int i = 0; i < size; i++) {
			if (posts[i].equals(post)) {
				return i;
			}
		}
		return -1;
	}

}
