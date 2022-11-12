package com.java.assessment.util;

import com.java.assessment.model.Address;
import com.java.assessment.model.AddressLineDetail;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressUtil {
    public String prettyPrintAddress(final Address address) {
        StringBuilder sb = new StringBuilder();

        return  sb.append(address.getType().getName())
                .append(": ")
                .append(address.getAddressLineDetail().getLine1())
                .append(" ")
                .append(address.getAddressLineDetail().getLine2())
                .append(" - ")
                .append(address.getCityOrTown())
                .append(" - ")
                .append(address.getProvinceOrState().getName())
                .append(" - ")
                .append(address.getPostalCode())
                .append(" - ")
                .append(address.getCountry().getName())
                .toString();
    }

    public String printAllAddresses (final List<Address> addresses) {
        StringBuilder prettyAddresses = new StringBuilder();
        for (Address address : addresses) {
            prettyAddresses.append(prettyPrintAddress(address)).append("\n"); //re-uses prettyPrintAddress()
        }
        System.out.println("Addresses: \n" + prettyAddresses);  //print addresses on console

        return prettyAddresses.toString();
    }

    public String printSpecificTypeAddress(final List<Address> addresses, final  String addressType) {
        final List<Address> addressesToPrint = addresses.stream().filter(address -> address.getType().getName().equals(addressType)).collect(Collectors.toList());
        return printAllAddresses(addressesToPrint); //re-uses printAllAddresses()
    }

    public  List<String> validateAddress(final Address address) {
        List<String> errors = new ArrayList<>();

        if (!isNumeric(address.getPostalCode())) {
            errors.add("Postal Code must be numeric");
        }
        if (address.getCountry() == null || address.getCountry().getName().isEmpty()) {
           errors.add("Country must not be null or empty");
        }
        if (!isValidAddressLineDetail(address.getAddressLineDetail())) {
            errors.add("Address line detail must have at least one line");
        }
        if (!isValidProvince(address)) {
            errors.add("Province is required if country code is ZA");
        }

        return errors;
    }

    private boolean isValidAddressLineDetail(final AddressLineDetail addressLineDetail) {
        return
                addressLineDetail != null
                && (addressLineDetail.getLine1() != null || addressLineDetail.getLine2() != null)
                && (!addressLineDetail.getLine1().isEmpty() || !addressLineDetail.getLine2().isEmpty());

    }

    private boolean isValidProvince(final Address address) {
        if (address.getCountry() != null && address.getCountry().getCode() != null && !address.getCountry().getCode().equals("ZA")) {
            return true;
        }
        return address.getProvinceOrState() != null && address.getProvinceOrState().getName() != null && !address.getProvinceOrState().getName().isEmpty();
    }
    private boolean isNumeric(final String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
