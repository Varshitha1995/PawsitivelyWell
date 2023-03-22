package com.wedotech.pawsitivelywell.service;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

@Service
public class InformationServiceImpl implements InformationService {

	@Override
	public String getInformation(String type) {
		// TODO Auto-generated method stub
		JsonObject information = new JsonObject();
		String vetDetails = "[{\"Name\":\"Huron Church Animal Hospital\",\"Address\":\"1797 Huron Church Road\",\"Contact\":\"(519)977-1797\"}, {\"Name\":\"Branton Animal Hospital\",\"Address\":\"2525 Tecumseh Road West\",\"Contact\":\"(519)253-3556\"}, {\"Name\":\"Brack Animal Hospital\",\"Address\":\"2621 Howard Avenue\",\"Contact\":\"(519)966-1020\"}, {\"Name\":\"Clearwater Animal Hospital\",\"Address\":\"1117 Walker Road\",\"Contact\":\"(519)253-7204\"}, {\"Name\":\"Downtown Veterinary Hospital\",\"Address\":\"154 Tuscarora Street\",\"Contact\":\"(519)258-9963\"}]";
		information.addProperty("vet", vetDetails);
		String daycare = "[{\"Name\":\"Smoochie Poochies\",\"Address\":\"4921 Malden Road\",\"Contact\":\"(519)915-3649\"}, {\"Name\":\"Animal Antics Behaviour Centre\",\"Address\":\"3966 North Service Rd E\",\"Contact\":\"(519)564-3682\"}, {\"Name\":\"K9 to 5 Paws & Play Dog Daycare & Boarding\",\"Address\":\"1797 St Luke Road\",\"Contact\":\"(519)915-4444\"}, {\"Name\":\"Windsor Pet Nanny Ltd\",\"Address\":\"861 Esdras Pl\",\"Contact\":\"(519)990-8989\"}]";
		information.addProperty("daycare", daycare);
		String grooming = "[{\"Name\":\"Puppy Charm Dog Grooming\",\"Address\":\"3210 Jefferson Blvd\",\"Contact\":\"(519)919-2426\"}, {\"Name\":\"Scruffy To Fluffy\",\"Address\":\"4929 Malden Road\",\"Contact\":\"(519)915-0262\"}, {\"Name\":\"Clippingdale's Salon For Pets\",\"Address\":\"13255 Tecumseh Rd E\",\"Contact\":\"(519)979-8822\"}, {\"Name\":\"Loyal Companions Dog Grooming Inc\",\"Address\":\"2501 Tecumseh Rd W\",\"Contact\":\"(519)258-4588\"}]";
		information.addProperty("grooming", grooming);
		String dogpark = "[{\"Name\":\"Malden Dog Park\",\"Address\":\"4200 Malden Road\"}, {\"Name\":\"Optimist Memorial Park\",\"Address\":\"1075 Ypres Ave\"}, {\"Name\":\"Black Oak Heritage Park\",\"Address\":\"Ojibway Parkway\"}, {\"Name\":\"Tecumseh Dog Park\",\"Address\":\"Tecumseh\"}]";
		information.addProperty("dogpark", dogpark);
		return information.get(type).getAsString();
	}

}
