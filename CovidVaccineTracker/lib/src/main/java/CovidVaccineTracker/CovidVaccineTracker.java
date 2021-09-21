package CovidVaccineTracker;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;

@Contract(name = "CovidVaccineTracker", info = @Info(title = "CovidVaccineTracker contract", description = "Track Covid vaccine recipients so that no one is missed or receives any extra dose. "
		+ "One of the challenges that the government is facing is people are producing fake covid vaccine "
		+ "certificates and infected people are spreading this further.", version = "0.0.1-SNAPSHOT"))

@Default
public class CovidVaccineTracker implements ContractInterface {
	private final Genson genson = new Genson();

	private enum CovidVaccineErrors {
		FirstDose_Taken, SecondDose_Taken, NotVaccinated
	}

	/**
	 * Add some initial properties to the ledger
	 *
	 * @param ctx the transaction context
	 */
	@Transaction()
	public void initLedger(final Context ctx) {

		ChaincodeStub stub = ctx.getStub();

		CovidVaccineModel property = new CovidVaccineModel("Satish", "30", "Male", "12332145", "Moderna", "Sep21, 2020",
				"First");

		String PropertyState = genson.serialize(property);

		stub.putStringState("12332145", PropertyState);
	}

	/**
	 * Add new Property on the ledger.
	 *
	 * @param ctx         the transaction context
	 * @param name        the name of the person
	 * @param age         the age of the person
	 * @param gender      the gender of the person
	 * @param identity    the ssn/aadhar no of the person
	 * @param vaccineName the vaccine name to be given to the person
	 * @param date        the date at which person is getting vaccinated
	 * @param vaccineDose the vaccine dose person is going to receive.
	 * @return the created Property
	 */

	@Transaction()
	public CovidVaccineModel addNewVaccineDose(final Context ctx, final String name, final String age,
			final String gender, final String identity, final String vaccineName, final String date,
			final String vaccineDose) {

		ChaincodeStub stub = ctx.getStub();

		String PersonState = stub.getStringState(identity);
		System.out.println(PersonState);
		if (!PersonState.isEmpty()) {
			CovidVaccineModel vaccineModel = genson.deserialize(PersonState, CovidVaccineModel.class);
			String vaccineDoseType = vaccineModel.getVaccineDose();
			if (vaccineDoseType.equalsIgnoreCase("2")) {
				String errorMessage = String.format("Person already took %s dose of vaccine", vaccineDoseType);
				System.out.println(errorMessage);
				throw new ChaincodeException(errorMessage, CovidVaccineErrors.SecondDose_Taken.toString());
			}

			String errorMessage = String.format("Person already took %s dose of vaccine", vaccineDoseType);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, CovidVaccineErrors.FirstDose_Taken.toString());
		}

		CovidVaccineModel Property = new CovidVaccineModel(name, age, gender, identity, vaccineName, date, vaccineDose);

		PersonState = genson.serialize(Property);

		stub.putStringState(identity, PersonState);

		return Property;
	}

	/**
	 * Retrieves a Person vaccination status based upon Identity from the ledger.
	 *
	 * @param ctx      the transaction context
	 * @param identity the key
	 * @return the Vaccination status found on ledger
	 */
	@Transaction()
	public String queryVaccinationStatusByIdentity(final Context ctx, final String identity) {
		ChaincodeStub stub = ctx.getStub();
		String PersonState = stub.getStringState(identity);
		String vaccineStatusMessage = null;
		if (PersonState.isEmpty()) {
			vaccineStatusMessage = "The person is not vaccinated";
			return vaccineStatusMessage;
		}
		CovidVaccineModel vaccineModel = genson.deserialize(PersonState, CovidVaccineModel.class);
		String vaccineDoseType = vaccineModel.getVaccineDose();
		vaccineStatusMessage = String.format("Person is vaccinated with %s dose", vaccineDoseType);
		return vaccineStatusMessage;
	}

	/**
	 * Updates a Person vaccination status based upon Identity from the ledger.
	 *
	 * @param ctx      the transaction context
	 * @param identity the key
	 * @param date     of vaccination
	 * @return the new Vaccination model on ledger
	 */
	@Transaction()
	public CovidVaccineModel updateVaccinationStatusByIdentity(final Context ctx, final String identity,
			final String dateOfVaccination) {
		ChaincodeStub stub = ctx.getStub();
		String PersonState = stub.getStringState(identity);

		if (PersonState.isEmpty()) {
			String errorMessage = String.format("Person vaccination status could not be found");
			throw new ChaincodeException(errorMessage, CovidVaccineErrors.NotVaccinated.toString());
		}

		CovidVaccineModel vaccineModel = genson.deserialize(PersonState, CovidVaccineModel.class);
		if (vaccineModel.getVaccineDose().equalsIgnoreCase("2")) {
			String errorMessage = String.format("Person is fully vaccinated");
			throw new ChaincodeException(errorMessage, CovidVaccineErrors.NotVaccinated.toString());
		}
		CovidVaccineModel newVaccinationStatus = new CovidVaccineModel(vaccineModel.getName(), vaccineModel.getAge(),
				vaccineModel.getGender(), identity, vaccineModel.getVaccineName(), dateOfVaccination, "2");

		String newPropertyState = genson.serialize(newVaccinationStatus);
		stub.putStringState(identity, newPropertyState);
		return newVaccinationStatus;
	}

}
