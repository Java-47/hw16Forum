package telran.forum.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForumImplTest {

	Forum forum;
	LocalDateTime now = LocalDateTime.now();
	Post[] posts = new Post[6];

	Comparator<Post> comp = (p1, p2) -> {
		return Integer.compare(p1.getPostId(), p2.getPostId());
	};

	@BeforeEach
	void setUp() throws Exception {
		forum = new ForumImpl();
		posts[0] = new Post("author1", 1, "title1", "content1", now.minusDays(3), 0);
		posts[1] = new Post("author1", 5, "title5", "content5", now.minusDays(5), 0);
		posts[2] = new Post("author1", 4, "title4", "content4", now.minusDays(1), 0);
		posts[3] = new Post("author2", 3, "title3", "content3", now.minusDays(6), 0);
		posts[4] = new Post("author2", 2, "title2", "content2", now.minusDays(2), 0);

		for (int i = 0; i < posts.length - 1; i++) {
			forum.addPost(posts[i]);
		}
		posts[5] = new Post("author6", 6, "title6", "content6", now.minusDays(4), 0); // not added
	}

	@Test
	void testAddPost() {
		assertTrue(forum.addPost(posts[5]));
		assertEquals(posts[5], forum.getPostById(6));
		assertFalse(forum.addPost(posts[5]));
		assertEquals(6, forum.size());
	}

	@Test
	void testRemovePost() {
		assertTrue(forum.removePost(1));
		assertNull(forum.getPostById(1));
		assertFalse(forum.removePost(1));
		assertEquals(4, forum.size());
	}

	@Test
	void testUpdatePost() {
		assertTrue(forum.updatePost(2, "newContentPost2"));
		assertEquals("newContentPost2", posts[4].getContent());
	}

	@Test
	void testGetPostById() {
		assertNull(forum.getPostById(8));
		assertEquals(posts[0], forum.getPostById(1));
	}

	@Test
	void testGetPostsByAuthorString() {
		Post[] actual = forum.getPostsByAuthor("author2");
		Arrays.sort(actual, comp);
		Post[] expected = { posts[4], posts[3] };

		assertArrayEquals(expected, actual);
		;
	}

	@Test
	void testGetPostsByAuthorStringLocalDateLocalDate() {
		LocalDate dateNow = now.toLocalDate();
		Post[] actuals = forum.getPostsByAuthor("author1", dateNow.minusDays(4), dateNow);
		Arrays.sort(actuals, comp);
		Post[] expecteds = { posts[0], posts[2] };
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	void testSize() {
		assertEquals(5, forum.size());
	}

	@Test
	void addLike() {
		posts[0].addLike();
		assertEquals(1, posts[0].getLikes());
		posts[0].addLike();
		assertEquals(2, posts[0].getLikes());
	}

}
