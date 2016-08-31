package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentableRepository;
import domain.Commentable;

@Transactional
@Service
public class CommentableService {

	// Constructor
	// ---------------------------------------------------------------
	public CommentableService() {
		super();
	}

	// Managed
	// Repository-----------------------------------------------------------
	@Autowired
	private CommentableRepository commentableRepository;

	// CRUDs Methods
	// ---------------------------------------------------------------
	public Commentable findOne(int commentableId) {
		Commentable result;
		Assert.notNull(commentableId);

		result = commentableRepository.findOne(commentableId);

		return result;
	}

	public Collection<Commentable> findAll() {
		Collection<Commentable> commentables;
		commentables = commentableRepository.findAll();

		return commentables;
	}

}
