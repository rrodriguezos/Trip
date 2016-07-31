package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Administrator;
import domain.Comment;
import domain.Commentable;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	// ManagedRepository ---------------------------
	@Autowired
	private CommentRepository commentRepository;

	// Supporting services -------------------------
	@Autowired
	private CommentableService commentableService;
	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors --------------------------------
	public CommentService() {
		super();
	}

	// Simple CRUD methods -------------------------
	public Collection<Comment> findAll() {
		return commentRepository.findAll();
	}

	public Comment findOne(int commentId) {
		Assert.isTrue(commentId != 0);
		Comment result = commentRepository.findOne(commentId);
		Assert.notNull(result);
		return result;
	}

	public void save(Comment comment) {
		Assert.notNull(comment);
		commentRepository.save(comment);
	}

	public void delete(Comment comment) {
		Assert.notNull(comment);
		Administrator admin = administratorService.findByPrincipal();
		Assert.notNull(admin);
		comment.setIsAppropiate(true);
		commentRepository.delete(comment);
	}

	public Comment create(int commentableId) {
		Comment result;
		Commentable commentable;
		Actor principal;

		commentable = commentableService.findOne(commentableId);
		principal = actorService.findByPrincipal();

		result = new Comment();
		result.setIsAppropiate(true);
		result.setCommentable(commentable);
		result.setMoment(new Date(System.currentTimeMillis() - 100));
		result.setActor(principal);

		return result;
	}

	// Other business methods -----------------------

	public void flagCommentAsInappropriate(int commentId) {
		Administrator admin = administratorService.findByPrincipal();
		Assert.notNull(admin);
		Comment comment = findOne(commentId);
		Assert.isTrue(comment.getIsAppropiate());
		comment.setIsAppropiate(false);
	}

	public Collection<Comment> findCommentsByCommentableId(int commentableId) {

		Collection<Comment> result = commentRepository
				.findCommentsByCommentableId(commentableId);
		Assert.notNull(result);

		return result;
	}

	public Comment reconstruct(CommentForm commentForm, int commentableId) {
		Assert.notNull(commentableId);
		Comment result = new Comment();
		result.setActor(actorService.findByPrincipal());
		result.setCommentable(commentableService.findOne(commentableId));
		result.setIsAppropiate(true);
		result.setTitle(commentForm.getTitle());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setText(commentForm.getText());
		return result;
	}

}
