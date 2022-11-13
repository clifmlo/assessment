package com.java.assessment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.assessment.model.Address;
import com.java.assessment.util.AddressUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
class AddressUtilTests {

	@Autowired
	AddressUtil addressUtil;
	@Autowired
	ObjectMapper mapper;

	@Before
	void setUp() {
		mapper.findAndRegisterModules();
	}

	@Test
	void testPrettyPrintAddress() throws JsonProcessingException {
		String jsonAddress = getJsonAddress();
		Address addressObject = mapper.readValue(jsonAddress, Address.class);
		String prettyAddress = addressUtil.prettyPrintAddress(addressObject);

		assertEquals("Physical Address: Address 1 Line 2 - City 1 - Eastern Cape - 1234 - South Africa", prettyAddress);
	}

	@Test
	void testPrintAllAddresses() throws JsonProcessingException {
		String jsonAddresses = getAllJsonAddresses();
		List<Address> addresses = mapper.readValue(jsonAddresses,  new TypeReference<List<Address>>(){});
		addresses = addresses.stream().filter(address ->(addressUtil.validateAddress(address).isEmpty())).collect(Collectors.toList()); //We validate and print only valid addresses because invalid addresses will cause exception and we cannot alter our address object to cater for invalid addresses
		addressUtil.printAllAddresses(addresses);
	}

	@Test
	 void  printSpecificTypeAddressTest() throws JsonProcessingException {
		String jsonAddresses = getDifferentTypesAddresses();
		List<Address> addresses =
				mapper.readValue(jsonAddresses,  new TypeReference<List<Address>>(){});
		String prettyAddresses = addressUtil.printSpecificTypeAddress(addresses, "Postal Address");
		String[] lines = prettyAddresses.split("\n");

		assertEquals(2, lines.length); //Only 2 (of type Postal Address) out of 3 addresses must qualify for print
		assertTrue( lines[0].contains("Postal Address")); //must be of type postal address
		assertTrue( lines[1].contains("Postal Address")); //must be of type postal address
	}

	@Test
	void testValidateAddress() throws JsonProcessingException {
		String JsonAddress1 = getAddress1();
		String JsonAddress2 = getAddress2();
		String JsonAddress3 = getAddress3();

		Address address1 = mapper.readValue(JsonAddress1, Address.class);
        List<String> errors1 = addressUtil.validateAddress(address1);

		Address address2 = mapper.readValue(JsonAddress2, Address.class);
		List<String> errors2 = addressUtil.validateAddress(address2);

		Address address3 = mapper.readValue(JsonAddress3, Address.class);
		List<String> errors3 = addressUtil.validateAddress(address3);

		if (!errors1.isEmpty()) {
			System.out.println("Address 1 Errors:" + errors1);
		} else {
			System.out.println("Address 1 is valid");
		}
		if (!errors2.isEmpty()) {
			System.out.println("Address 2 Errors:" + errors2);
		} else {
			System.out.println("Address 2 is valid");
		}
		if (!errors3.isEmpty()) {
			System.out.println("Address 3 Errors:" + errors3);
		} else {
			System.out.println("Address 3 is valid");
		}
		assertEquals(0, errors1.size()); // There must be no errors, address is valid
		assertEquals(1, errors2.size()); // There is address line error
		assertEquals(1, errors3.size()); // There is a province error
	}

	private String getJsonAddress() {
		return  "{\n" +
				"    \"id\": \"1\",\n" +
				"    \"type\": {\n" +
				"      \"code\": \"1\",\n" +
				"      \"name\": \"Physical Address\"\n" +
				"    },\n" +
				"    \"addressLineDetail\": {\n" +
				"      \"line1\": \"Address 1\",\n" +
				"      \"line2\": \"Line 2\"\n" +
				"    },\n" +
				"    \"provinceOrState\": {\n" +
				"      \"code\": \"5\",\n" +
				"      \"name\": \"Eastern Cape\"\n" +
				"    },\n" +
				"    \"cityOrTown\": \"City 1\",\n" +
				"    \"country\": {\n" +
				"      \"code\": \"ZA\",\n" +
				"      \"name\": \"South Africa\"\n" +
				"    },\n" +
				"    \"postalCode\": \"1234\",\n" +
				"    \"lastUpdated\": \"2015-06-21T00:00:00.000Z\"\n" +
				"  }";
	}

	private String getAllJsonAddresses () {
		return "[\n" +
				"    {\n" +
				"        \"id\": \"1\",\n" +
				"        \"type\": {\n" +
				"            \"code\": \"1\",\n" +
				"            \"name\": \"Physical Address\"\n" +
				"        },\n" +
				"        \"addressLineDetail\": {\n" +
				"            \"line1\": \"Address 1\",\n" +
				"            \"line2\": \"Line 2\"\n" +
				"        },\n" +
				"        \"provinceOrState\": {\n" +
				"            \"code\": \"5\",\n" +
				"            \"name\": \"Eastern Cape\"\n" +
				"        },\n" +
				"        \"cityOrTown\": \"City 1\",\n" +
				"        \"country\": {\n" +
				"            \"code\": \"ZA\",\n" +
				"            \"name\": \"South Africa\"\n" +
				"        },\n" +
				"        \"postalCode\": \"1234\",\n" +
				"        \"lastUpdated\": \"2015-06-21T00:00:00.000Z\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"id\": \"2\",\n" +
				"        \"type\": {\n" +
				"            \"code\": \"2\",\n" +
				"            \"name\": \"Postal Address\"\n" +
				"        },\n" +
				"        \"cityOrTown\": \"City 2\",\n" +
				"        \"country\": {\n" +
				"            \"code\": \"LB\",\n" +
				"            \"name\": \"Lebanon\"\n" +
				"        },\n" +
				"        \"postalCode\": \"2345\",\n" +
				"        \"lastUpdated\": \"2017-06-21T00:00:00.000Z\"\n" +
				"    },\n" +
				"    {\n" +
				"        \"id\": \"3\",\n" +
				"        \"type\": {\n" +
				"            \"code\": \"5\",\n" +
				"            \"name\": \"Business Address\"\n" +
				"        },\n" +
				"        \"addressLineDetail\": {\n" +
				"            \"line1\": \"Address 3\",\n" +
				"            \"line2\": \"\"\n" +
				"        },\n" +
				"        \"cityOrTown\": \"City 3\",\n" +
				"        \"country\": {\n" +
				"            \"code\": \"ZA\",\n" +
				"            \"name\": \"South Africa\"\n" +
				"        },\n" +
				"        \"postalCode\": \"3456\",\n" +
				"        \"suburbOrDistrict\": \"Suburb 3\",\n" +
				"        \"lastUpdated\": \"2018-06-13T00:00:00.000Z\"\n" +
				"    }\n" +
				"]";
	}


	private String getDifferentTypesAddresses() {
		return "[\n" +
				"  {\n" +
				"    \"id\": \"1\",\n" +
				"    \"type\": {\n" +
				"      \"code\": \"1\",\n" +
				"      \"name\": \"Physical Address\"\n" +
				"    },\n" +
				"    \"addressLineDetail\": {\n" +
				"      \"line1\": \"Address 1\",\n" +
				"      \"line2\": \"Line 2\"\n" +
				"    },\n" +
				"    \"provinceOrState\": {\n" +
				"      \"code\": \"5\",\n" +
				"      \"name\": \"Eastern Cape\"\n" +
				"    },\n" +
				"    \"cityOrTown\": \"City 1\",\n" +
				"    \"country\": {\n" +
				"      \"code\": \"ZA\",\n" +
				"      \"name\": \"South Africa\"\n" +
				"    },\n" +
				"    \"postalCode\": \"1234\",\n" +
				"    \"lastUpdated\": \"2015-06-21T00:00:00.000Z\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"id\": \"2\",\n" +
				"    \"type\": {\n" +
				"      \"code\": \"2\",\n" +
				"      \"name\": \"Postal Address\"\n" +
				"    },\n" +
				"    \"addressLineDetail\": {\n" +
				"      \"line1\": \"Test 1\",\n" +
				"      \"line2\": \"\"\n" +
				"    },\n" +
				"    \"provinceOrState\": {\n" +
				"      \"code\": \"5\",\n" +
				"      \"name\": \"Gauteg\"\n" +
				"    },\n" +
				"    \"cityOrTown\": \"City 2\",\n" +
				"    \"country\": {\n" +
				"      \"code\": \"LB\",\n" +
				"      \"name\": \"Lebanon\"\n" +
				"    },\n" +
				"    \"postalCode\": \"2345\",\n" +
				"    \"lastUpdated\": \"2017-06-21T00:00:00.000Z\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"id\": \"3\",\n" +
				"    \"type\": {\n" +
				"      \"code\": \"5\",\n" +
				"      \"name\": \"Postal Address\"\n" +
				"    },\n" +
				"    \"addressLineDetail\": {\n" +
				"      \"line1\": \"Addres test\",\n" +
				"      \"line2\": \"\"\n" +
				"    },\n" +
				"    \"provinceOrState\": {\n" +
				"      \"code\": \"5\",\n" +
				"      \"name\": \"Free State\"\n" +
				"    },\n" +
				"    \"cityOrTown\": \"City 3\",\n" +
				"    \"country\": {\n" +
				"      \"code\": \"ZA\",\n" +
				"      \"name\": \"South Africa\"\n" +
				"    },\n" +
				"    \"postalCode\": \"3456\",\n" +
				"    \"suburbOrDistrict\": \"Suburb 3\",\n" +
				"    \"lastUpdated\": \"2018-06-13T00:00:00.000Z\"\n" +
				"  }\n" +
				"]";
	}

	private  String getAddress1() {
     return "{\n" +
			 "        \"id\": \"1\",\n" +
			 "        \"type\": {\n" +
			 "            \"code\": \"1\",\n" +
			 "            \"name\": \"Physical Address\"\n" +
			 "        },\n" +
			 "        \"addressLineDetail\": {\n" +
			 "            \"line1\": \"Address 1\",\n" +
			 "            \"line2\": \"Line 2\"\n" +
			 "        },\n" +
			 "        \"provinceOrState\": {\n" +
			 "            \"code\": \"5\",\n" +
			 "            \"name\": \"Eastern Cape\"\n" +
			 "        },\n" +
			 "        \"cityOrTown\": \"City 1\",\n" +
			 "        \"country\": {\n" +
			 "            \"code\": \"ZA\",\n" +
			 "            \"name\": \"South Africa\"\n" +
			 "        },\n" +
			 "        \"postalCode\": \"1234\",\n" +
			 "        \"lastUpdated\": \"2015-06-21T00:00:00.000Z\"\n" +
			 "    }";
	}

	private  String getAddress2() {
       return "{\n" +
			   "        \"id\": \"2\",\n" +
			   "        \"type\": {\n" +
			   "            \"code\": \"2\",\n" +
			   "            \"name\": \"Postal Address\"\n" +
			   "        },\n" +
			   "        \"cityOrTown\": \"City 2\",\n" +
			   "        \"country\": {\n" +
			   "            \"code\": \"LB\",\n" +
			   "            \"name\": \"Lebanon\"\n" +
			   "        },\n" +
			   "        \"postalCode\": \"2345\",\n" +
			   "        \"lastUpdated\": \"2017-06-21T00:00:00.000Z\"\n" +
			   "    }";
	}

	private  String getAddress3() {
		return  " {\n" +
				"        \"id\": \"3\",\n" +
				"        \"type\": {\n" +
				"            \"code\": \"5\",\n" +
				"            \"name\": \"Business Address\"\n" +
				"        },\n" +
				"        \"addressLineDetail\": {\n" +
				"            \"line1\": \"Address 3\",\n" +
				"            \"line2\": \"\"\n" +
				"        },\n" +
				"        \"cityOrTown\": \"City 3\",\n" +
				"        \"country\": {\n" +
				"            \"code\": \"ZA\",\n" +
				"            \"name\": \"South Africa\"\n" +
				"        },\n" +
				"        \"postalCode\": \"3456\",\n" +
				"        \"suburbOrDistrict\": \"Suburb 3\",\n" +
				"        \"lastUpdated\": \"2018-06-13T00:00:00.000Z\"\n" +
				"    }";
	}
}

