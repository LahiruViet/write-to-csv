package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @GetMapping("users/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        UserDTO userLahiru = new UserDTO();
        userLahiru.setId(1L);
        userLahiru.setEmail("lahiru@test.com");
        userLahiru.setFullName("Lahiru Sanity");
        userLahiru.setEnabled(true);
        Set<String> roles = new HashSet<>(Arrays.asList("Admin", "System"));
        userLahiru.setRoles(roles);

        UserDTO userEnvy = new UserDTO();
        userEnvy.setId(2L);
        userEnvy.setEmail("envy@test.com");
        userEnvy.setFullName("Envy Adorable");
        userEnvy.setEnabled(true);
        Set<String> role = Stream.of("User").collect(Collectors.toCollection(HashSet::new));
        userEnvy.setRoles(role);

        List<UserDTO> users = Arrays.asList(userLahiru, userEnvy);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"User ID", "E-mail", "Full Name", "Roles", "Enabled"};
        String[] nameMapping = {"id", "email", "fullName", "roles", "enabled"};

        csvWriter.writeHeader(csvHeader);

        for (UserDTO user : users) {
            csvWriter.write(user, nameMapping);
        }

        csvWriter.close();
    }

}
