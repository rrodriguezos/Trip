package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Access(AccessType.PROPERTY)
public class BannerForm {

	private Integer campId;
	private String pho;
	private int maxTimes;
	private String keyWs;
	
	@NotBlank
	public String getPho() {
		return pho;
	}
	public void setPho(String pho) {
		this.pho = pho;
	}
	@NotNull
	public int getMaxTimes() {
		return maxTimes;
	}
	public void setMaxTimes(int maxTimes) {
		this.maxTimes = maxTimes;
	}
	@NotBlank
	public String getKeyWs() {
		return keyWs;
	}
	public void setKeyWs(String keyWs) {
		this.keyWs = keyWs;
	}
	public Integer getCampId() {
		return campId;
	}
	public void setCampId(Integer campId) {
		this.campId = campId;
	} 

}
