package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Activity;
import domain.Administrator;
import domain.Comment;

@Service
@Transactional
public class CommentService {
	
	//ManagedRepository ---------------------------
		@Autowired
		private CommentRepository commentRepository;
		
		//Supporting services -------------------------
		@Autowired
		private CommentableService commentableService;
		@Autowired
		private ActorService actorService;
		
		@Autowired
		private AdministratorService administratorService;
		
		//Constructors --------------------------------
		public CommentService() {
			super();
		}
		
		//Simple CRUD methods -------------------------
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
			//commentRepository.delete(comment);
			commentRepository.save(comment);
		}
		
		
		//Other business methods -----------------------
		
		public void flagCommentAsInappropriate(Comment comment) {
			Assert.notNull(comment);
			Administrator admin = administratorService.findByPrincipal();
			Assert.notNull(admin);
			
			comment.setIsAppropiate(false);
			
			
			commentRepository.saveAndFlush(comment);
		}


//		public Comment reconstruct(CommentForm commentForm, int commentableId) {
//			Comment result = new Comment();
//			result.setActor(actorService.findByPrincipal());
//			result.setCommentable(commentableService.findOne(commentableId));
//			result.setDeleted(false);
//			result.setMoment(new Date(System.currentTimeMillis()-1000));
//			result.setRating(commentForm.getRating());
//			result.setText(commentForm.getText());
//			return result;
//		}
		

}
