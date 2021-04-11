package pt.ulisboa.tecnico.socialsoftware.common.utils;

import com.mifmif.common.regex.Generex;
import org.springframework.stereotype.Component;
import pt.ulisboa.tecnico.socialsoftware.common.dtos.user.UserDto;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CloudContractUtils {

    public static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9_]+";

    public static final String ALPHABETIC_PATTERN = "[a-zA-Z0]+";

    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})";

    public static final String[] roles = {"STUDENT", "TEACHER", "ADMIN", "DEMO_ADMIN"};

    public static Random random = new Random();

    public static Integer RANDOM_INTEGER = random.nextInt(Integer.MAX_VALUE);

    public String generateRandomAlphabeticString() {
        return new Generex(ALPHABETIC_PATTERN).random();
    }

    public String generateRandomAlphanumericString() {
        return new Generex(ALPHANUMERIC_PATTERN).random();
    }

    public String getRandomRole() {
        return roles[random.nextInt(roles.length)];
    }

    public String generateEmailAddress() {
        return new Generex(EMAIL_PATTERN).random();
    }

    public String getRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).toString();
    }

    /*public UserDto getRandomUserDto() {
        UserDto dto = new UserDto();
        dto.setId(RANDOM_INTEGER);
        dto.setUsername(generateRandomAlphanumericString());
        dto.setEmail(generateEmailAddress());
        dto.setName(generateRandomAlphabeticString());
        dto.setRole(getRandomRole());
        dto.setActive(random.nextBoolean());
        dto.setCreationDate(getRandomDate());
        dto.setLastAccess(getRandomDate());
        return dto;
    }*/
}
