package CovidVaccineTracker;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Date;
import java.util.Objects;

@DataType()
public final class CovidVaccineModel {

	@Property()
	private final String name;
	@Property()
	private final String age;
	@Property()
	private final String gender;
	@Property()
	private final String identity;
	@Property()
	private final String vaccineName;
	@Property()
	private final String date;
	@Property()
	private final String vaccineDose;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the identity- In this case it will return Social security Number/ Aadhar number
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @return the vaccineName
	 */
	public String getVaccineName() {
		return vaccineName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the vaccineDose
	 */
	public String getVaccineDose() {
		return vaccineDose;
	}

	public CovidVaccineModel(@JsonProperty("name") String name, @JsonProperty("age") String age,
			@JsonProperty("gender") String gender, @JsonProperty("identity") String identity,
			@JsonProperty("vaccineName") String vaccineName, @JsonProperty("date") String date,
			@JsonProperty("vaccineDose") String vaccineDose) {

		this.name = name;
		this.age = age;
		this.gender = gender;
		this.identity = identity;
		this.vaccineName = vaccineName;
		this.date = date;
		this.vaccineDose = vaccineDose;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
 
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
 
		CovidVaccineModel other = (CovidVaccineModel) obj;
 
		return Objects.deepEquals(new String[] { 
				getName(), 
				getAge(), 
				getGender(), 
				getIdentity(),
				getVaccineName(), 
				getDate(),
				getVaccineDose()
				},
				new String[] { other.getName(), other.getAge(), other.getGender(), other.getIdentity(), 
						other.getVaccineName(), other.getDate(), other.getVaccineDose() });
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getAge(), getGender(), getIdentity(), getVaccineName(), getDate(), getVaccineDose());
	}
	
	@Override
	public String toString() {
		return "CovidVaccineModel [name=" + name + ", age=" + age + ", gender=" + gender + ", identity=" + identity
				+ ", vaccineName=" + vaccineName + ", date=" + date + ", vaccineDose=" + vaccineDose + "]";
	}

}
