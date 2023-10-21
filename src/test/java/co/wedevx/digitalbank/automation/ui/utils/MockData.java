package co.wedevx.digitalbank.automation.ui.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.util.Random;

public class MockData {
    private FakeValuesService fakeValuesService = new FakeValuesService
            (new Locale("en-US"), new RandomService());
    public Map<String, String> generateRandomNameAndEmail(){
        String name = fakeValuesService.bothify(new Faker().name().firstName());
        String email =fakeValuesService.bothify(name+"####@gmail.com");

        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);

        return data;
    }

    public String generateRandomSSN(){
        String randomSSN = String.format("%09d", new Random().nextInt(100000000));
        return randomSSN;
    }

    public static void main(String[] args) {
        MockData mockData = new MockData();

        Map<String, String> data = mockData.generateRandomNameAndEmail();
        System.out.println(data.get("name"));
        System.out.println(data.get("email"));
    }
}
