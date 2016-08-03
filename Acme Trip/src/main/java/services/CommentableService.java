package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Commentable;

import repositories.CommentableRepository;

@Service
@Transactional
public class CommentableService {
	
	///ManagedRepository ---------------------------
	@Autowired
	private CommentableRepository commentableRepository;
	
	//Supporting services -------------------------
	
	//Constructors --------------------------------
	public CommentableService() {
		super();
	}

	public Commentable findOne(int commentableId){
		Commentable result;
		Assert.notNull(commentableId);
		
		result = commentableRepository.findOne(commentableId);
		
		return result;
	}

}
