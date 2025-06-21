package cz.muni.fi.pv168.seminar01.delta;

import cz.muni.fi.pv168.seminar01.delta.data.validator.RideValidator;
import cz.muni.fi.pv168.seminar01.delta.data.validator.Validator;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.FuelType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTests {
    private final String validName = "name";
    private final Destination validDestination = new Destination(5L, validName);
    private final String validStringInteger = "356";
    private final String validStringDouble = "19.46";
    private final List<String> validCategories = new ArrayList<>(Collections.singleton(validName));
    private final Auto validAuto = new Auto(45L, "skjozef", validName, FuelType.PETROL);
    private final Locale locale = new Locale("");


    @Test
    void isParsingIntegerTest() {
        assertFalse(Validator.isParsingInteger(""));
        assertFalse(Validator.isParsingInteger(validName));
        assertFalse(Validator.isParsingInteger(validStringDouble));
        assertFalse(Validator.isParsingInteger("50<"));
        assertFalse(Validator.isParsingInteger("<50"));
        assertFalse(Validator.isParsingInteger("<50<"));
        assertTrue(Validator.isParsingDouble(validStringInteger));
    }

    @Test
    void isParsingDoubleTest() {
        assertFalse(Validator.isParsingDouble(""));
        assertFalse(Validator.isParsingDouble(validName));
        assertFalse(Validator.isParsingDouble("50<"));
        assertFalse(Validator.isParsingDouble("<50"));
        assertFalse(Validator.isParsingDouble("<50<"));
        assertTrue(Validator.isParsingDouble(validStringInteger));
        assertTrue(Validator.isParsingDouble(validStringDouble));
    }

    @Test
    void validRideTest() {
        assertNull(RideValidator.validate(validName, validDestination, validDestination, validStringInteger, validStringDouble, validStringDouble, validCategories, validAuto, locale));
    }
    
    @Test
    void invalidNameTest() {
        assertNotEquals(RideValidator.validate("", validDestination, validDestination, validStringInteger, validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
    }

    @Test
    void invalidFromDestinationTest() {
        assertNotEquals(RideValidator.validate(validName, null, validDestination, validStringInteger, validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
    }

    @Test
    void invalidToDestinationTest() {
        assertNotEquals(RideValidator.validate(validName, validDestination, null, validStringInteger, validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
    }

    @Test
    void invalidPassengerCountTest() {
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, "50.5", validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validName, validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, "50<", validStringDouble, validStringDouble, validCategories, validAuto, locale), null);
    }

    @Test
    void invalidFuelCostTest() {
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validStringInteger, validName, validStringDouble, validCategories, validAuto, locale), null);
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validStringInteger, "50<", validStringDouble, validCategories, validAuto, locale), null);
    }

    @Test
    void invalidDistanceTest() {
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validStringInteger, validStringDouble, validName, validCategories, validAuto, locale), null);
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validStringDouble, validStringDouble, "50<", validCategories, validAuto, locale), null);
    }

    @Test
    void invalidAutoTest() {
        assertNotEquals(RideValidator.validate(validName, validDestination, validDestination, validStringInteger, validStringDouble, validStringDouble, validCategories, null, locale), null);
    }
}
