package tests;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormWithStepsTests extends TestBase
{

    @Test
    void successfulFillFormTest()
    {
        Faker faker = new Faker();

        int g = faker.number().numberBetween(1, 2);
        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                email = firstName.substring(0, 1).toLowerCase() + "." + faker.letterify("??????").toLowerCase() +
                        faker.numerify("###") + "." + "@gmail.com",
                gender = (g > 1 ? "Male" : "Female"),
                mobile = faker.number().digits(10),
                dayOfBirth = Integer.toString(faker.number().numberBetween(10, 28)),
                monthOfBirth = "June",
                yearOfBirth = Integer.toString(faker.number().numberBetween(1950, 2005)),
                subject1 = "Chemistry",
                subject2 = "Commerce",
                hobby1 = "Sports",
                hobby2 = "Reading",
                hobby3 = "Music",
                picture = (g > 1 ? "award1_700.jpg" : "1518521058110646316.jpg"),
                currentAddress = faker.address().fullAddress(),
                state = "Uttar Pradesh",
                city = "Merrut";

        step("Open student registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill student registration form", () -> {
            $("#firstName").val(firstName);
            $("#lastName").val(lastName);
            $("#userEmail").val(email);
            $("#genterWrapper").$(byText(gender)).click();
            $("#userNumber").val(mobile);
            // set date
            $("#dateOfBirthInput").clear();
            $(".react-datepicker__month-select").selectOption(monthOfBirth);
            $(".react-datepicker__year-select").selectOption(yearOfBirth);
            $(".react-datepicker__day--0" + dayOfBirth).click();
            // set subject
            $("#subjectsInput").val(subject1);
            $(".subjects-auto-complete__menu-list").$(byText(subject1)).click();
            $("#subjectsInput").val(subject2);
            $(".subjects-auto-complete__menu-list").$(byText(subject2)).click();
            // set hobbies
            $("#hobbiesWrapper").$(byText(hobby1)).click();
            $("#hobbiesWrapper").$(byText(hobby2)).click();
            $("#hobbiesWrapper").$(byText(hobby3)).click();
            // upload image
            $("#uploadPicture").uploadFromClasspath("img/" + picture);
            // set current address
            $("#currentAddress").val(currentAddress);
            // set state and city
            $("#state").click();
            $("#stateCity-wrapper").$(byText(state)).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText(city)).click();
            $("#submit").click();
        });

        step("Verify successful form submit", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

            $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").parent().shouldHave(text(email));
            $x("//td[text()='Gender']").parent().shouldHave(text(gender));
            $x("//td[text()='Mobile']").parent().shouldHave(text(mobile));
            $x("//td[text()='Date of Birth']").parent()
                    .shouldHave(text(dayOfBirth + " " + monthOfBirth + "," + yearOfBirth));
            $x("//td[text()='Subjects']").parent().shouldHave(text(subject1 + ", " + subject2));
            $x("//td[text()='Hobbies']").parent().shouldHave(text(hobby1 + ", " + hobby2 + ", " + hobby3));
            $x("//td[text()='Picture']").parent().shouldHave(text(picture));
            $x("//td[text()='Address']").parent().shouldHave(text(currentAddress));
            $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
        });
    }
}
