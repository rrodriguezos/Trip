package domain;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={
	@Index(name="COMMENTABLE_INDEX", columnList=("commentable_id")),
	@Index(name="ACTOR_INDEX", columnList=("actor_id"))})
public class Comment extends DomainEntity {
	
	//Constructors -----------------------------
			public Comment() {
				super();
			}
			
			//Attributes -------------------------------
			private Date moment;
			private String text;
			private String title;
			private Boolean isAppropiate;
			
			@Past
			@NotNull
			@Temporal(TemporalType.TIMESTAMP)
			@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
			public Date getMoment() {
				return moment;
			}
			public void setMoment(Date moment) {
				this.moment = moment;
			}
			
			@NotBlank
			public String getText() {
				return text;
			}
			public void setText(String text) {
				this.text = text;
			}			

			
			@NotNull
			public Boolean getIsAppropiate() {
				return isAppropiate;
			}
			public void setIsAppropiate(Boolean isAppropiate) {
				this.isAppropiate = isAppropiate;
			}
			
			public String getTitle() {
				return title;
			}
			public void setTitle(String title) {
				this.title = title;
			}
			
			
			//Relationships --------------------------
			
			private Actor actor;
			private Commentable commentable;
			
			@NotNull
			@Valid
			@ManyToOne(optional=false)
			public Actor getActor() {
				return actor;
			}

			public void setActor(Actor actor) {
				this.actor = actor;
			}

			@NotNull
			@Valid
			@ManyToOne(optional=false)
			public Commentable getCommentable() {
				return commentable;
			}
			public void setCommentable(Commentable commentable) {
				this.commentable = commentable;
			}
		


}
