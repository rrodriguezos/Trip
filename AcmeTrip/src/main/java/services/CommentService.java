package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import security.Authority;
import domain.Actor;
import domain.Comment;
import domain.Commentable;

@Transactional
@Service
public class CommentService {

	// Constructor
	// ---------------------------------------------------------------
	public CommentService() {
		super();
	}

	// Managed
	// Repository-----------------------------------------------------------
	@Autowired
	private CommentRepository commentRepository;

	// Supported
	// Services------------------------------------------------------------
	@Autowired
	private CommentableService commentableService;

	@Autowired
	private ActorService actorService;

	// CRUD methods-------------------------------------------------------------
	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = commentRepository.findAll();

		return result;
	}

	public Comment create(int commentableId) {
		Comment result;
		Commentable commentable;
		Actor principal;

		commentable = commentableService.findOne(commentableId);
		principal = actorService.findByPrincipal();

		result = new Comment();
		result.setCommentable(commentable);
		result.setActor(principal);
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		result.setAppropriated(true);

		return result;
	}

	public Comment findOne(int commentId) {
		Assert.isTrue(commentId != 0);

		Comment result;

		result = commentRepository.findOne(commentId);
		Assert.notNull(result);

		return result;
	}

	public Comment save(Comment comment) {
		Assert.notNull(comment);
		Comment result;
		if (comment.getId() != 0) {
			Comment commentCheck = commentRepository.findOne(comment.getId());
			Assert.isTrue(comment.getVersion() == commentCheck.getVersion());
		}

		if (comment.getId() == 0) {
			comment.setMoment(new Date(System.currentTimeMillis() - 100));
		}

		result = commentRepository.saveAndFlush(comment);
		return result;
	}

	// Other Business Methods
	// ------------------------------------------------------

	public Collection<Comment> findCommentsByCommentableId(int commentableId) {
		Collection<Comment> result;

		result = commentRepository.findCommentsByCommentableId(commentableId);
		Assert.notNull(result);

		return result;
	}

	public void changeFlagComment(int commentId) {
		Assert.notNull(commentId);
		checkPrincipalAdministrator();
		Comment comment;

		comment = findOne(commentId);

		comment.setAppropriated(!comment.getAppropriated());

		save(comment);
	}

	private void checkPrincipalAdministrator() {
		Actor actor;
		Authority authority;

		actor = actorService.findByPrincipal();
		Assert.isTrue(actor != null);

		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");

		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
	}
}
